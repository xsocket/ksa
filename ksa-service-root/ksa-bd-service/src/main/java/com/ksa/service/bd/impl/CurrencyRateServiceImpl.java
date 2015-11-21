package com.ksa.service.bd.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.ksa.dao.bd.CurrencyRateDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.bd.Currency;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.bd.Partner;
import com.ksa.service.bd.CurrencyRateService;
import com.ksa.util.Assert;


public class CurrencyRateServiceImpl implements CurrencyRateService {

    private CurrencyRateDao currencyRateDao;
    
    public void setCurrencyRateDao( CurrencyRateDao currencyRateDao ) {
        this.currencyRateDao = currencyRateDao;
    }

    @Override
    public CurrencyRate saveCurrencyRate( CurrencyRate rate ) throws RuntimeException {
        int length = 0;
        if( StringUtils.hasText( rate.getId() ) ) {
            CurrencyRate temp = currencyRateDao.selectRateById( rate.getId() );
            if( temp != null && temp.getMonth() != null && temp.getMonth().equals( rate.getMonth() ) ) {
                length = currencyRateDao.updateRate( rate );
            } else {
                rate.setId( ModelUtils.generateRandomId() );
                length = currencyRateDao.insertRate( rate );
            }
        } else {
            rate.setId( ModelUtils.generateRandomId() );
            length = currencyRateDao.insertRate( rate );
        }
        
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "保存货币汇率发生异常，期望保存 1 条数据，实际保存了 %d 条数据。", length ) );
        }
        return rate;
    }
    
    @Override
    public List<CurrencyRate> loadLatestCurrencyRates() throws RuntimeException {
        return loadLatestCurrencyRates( new Date() );
    }

    @Override
    public List<CurrencyRate> loadLatestCurrencyRates( Date date ) throws RuntimeException {
        // 获取系统已有的货币清单，为清单中的货币查询最新汇率
        List<Currency> currencies = currencyRateDao.selectAllCurrency();
        if( currencies == null || currencies.size() <= 0 ) {
            return Collections.emptyList();
        }
        List<CurrencyRate> rates = currencyRateDao.selectLatestRates( date );
        
        // 按货币标识分组
        Map<String, CurrencyRate> map = new HashMap<String, CurrencyRate>();
        if( rates != null && rates.size() > 0 ) {
            for( CurrencyRate rate : rates ) {
                map.put( rate.getCurrency().getId(), rate );
            }
        }
        
        List<CurrencyRate> result = new ArrayList<CurrencyRate>( currencies.size() );
        for( Currency c : currencies ) {
            if( map.containsKey( c.getId() ) ) {
                result.add( map.get( c.getId() ) );
            } else {
                // 没有对货币设定任何汇率值，沿用默认值
                CurrencyRate rate = new CurrencyRate();
                rate.setCurrency( c );
                rate.setRate( c.getDefaultRate() );
                rate.setMonth( new Date() );
                result.add( rate );
            }
        }
        return result;
    }

    @Override
    public CurrencyRate loadLatestCurrencyRate( String currencyId ) throws RuntimeException {
        return loadLatestCurrencyRate( currencyId, new Date() );
    }
    
    @Override
    public CurrencyRate loadLatestCurrencyRate( String currencyId, Date date ) throws RuntimeException {
        CurrencyRate rate = currencyRateDao.selectLatestRate( currencyId, date );
        if( rate == null ) {
            Currency currency = currencyRateDao.selectCurrencyById( currencyId );
            Assert.notNull( currency, 
                    String.format( "标识为 'currencyId' 的结算货币不存在！请先添加相应的货币，再获取其汇率值。", currencyId ) );
            rate = new CurrencyRate();
            rate.setCurrency( currency );
            rate.setRate( currency.getDefaultRate() );
            rate.setMonth( new Date() );
        }
        return rate;
    }

    @Override
    public List<CurrencyRate> loadPartnerCurrencyRates( String customerId ) throws RuntimeException {
        // 获取系统已有的货币清单，为清单中的货币查询相应的汇率
        List<Currency> currencies = currencyRateDao.selectAllCurrency();
        if( currencies == null || currencies.size() <= 0 ) {
            return Collections.emptyList();
        }
        
        List<CurrencyRate> partnerRates = currencyRateDao.selectRateByPartner( customerId );
        
        // 按货币标识分组
        Map<String, CurrencyRate> partnerRateMap = new HashMap<String, CurrencyRate>();
        if( partnerRates != null && partnerRates.size() > 0 ) {
            for( CurrencyRate rate : partnerRates ) {
                partnerRateMap.put( rate.getCurrency().getId(), rate );
            }
        }
        
        List<CurrencyRate> latestRates = currencyRateDao.selectLatestRates( new Date() );
        
        // 按货币标识分组
        Map<String, CurrencyRate> latestRateMap = new HashMap<String, CurrencyRate>();
        if( latestRates != null && latestRates.size() > 0 ) {
            for( CurrencyRate rate : latestRates ) {
                latestRateMap.put( rate.getCurrency().getId(), rate );
            }
        }
        
        Partner customer = new Partner();
        customer.setId( customerId );
        
        List<CurrencyRate> result = new ArrayList<CurrencyRate>( currencies.size() );
        for( Currency c : currencies ) {
            if( partnerRateMap.containsKey( c.getId() ) ) {
                result.add( partnerRateMap.get( c.getId() ) );
            } else if( latestRateMap.containsKey( c.getId() ) ) {
                CurrencyRate latestRate = latestRateMap.get( c.getId() );
                latestRate.setId( "" );
                latestRate.setPartner( customer );
                result.add( latestRate );
            } else {
                // 没有对货币设定任何汇率值，沿用默认值
                CurrencyRate rate = new CurrencyRate();
                rate.setCurrency( c );
                rate.setRate( c.getDefaultRate() );
                rate.setPartner( customer );
                result.add( rate );
            }
        }
        return result;
    }

    @Override
    public CurrencyRate loadPartnerCurrencyRate( String customerId, String currencyId ) throws RuntimeException {
        CurrencyRate rate = currencyRateDao.selectRateByPartner( customerId, currencyId );
        if( rate == null ) {
            Currency currency = currencyRateDao.selectCurrencyById( currencyId );
            Assert.notNull( currency, 
                    String.format( "标识为 'currencyId' 的结算货币不存在！请先添加相应的货币，再获取其汇率值。", currencyId ) );
            rate = new CurrencyRate();
            rate.setCurrency( currency );
            rate.setRate( currency.getDefaultRate() );
            rate.setPartner( new Partner() );
            rate.getPartner().setId( customerId );
        }
        return rate;
    }

    @Override
    public List<CurrencyRate> loadAllCurrencyRates() throws RuntimeException {
     // 获取系统已有的货币清单，为清单中的货币查询最新汇率
        List<Currency> currencies = currencyRateDao.selectAllCurrency();
        if( currencies == null || currencies.size() <= 0 ) {
            return Collections.emptyList();
        }
        List<CurrencyRate> rates = currencyRateDao.selectAllRates();
        
        // 按货币标识分组
        Map<String, CurrencyRate> map = new HashMap<String, CurrencyRate>();
        if( rates != null && rates.size() > 0 ) {
            for( CurrencyRate rate : rates ) {
                map.put( rate.getCurrency().getId(), rate );
            }
        }
        
        List<CurrencyRate> result = new ArrayList<CurrencyRate>( rates ); 
        for( Currency c : currencies ) {
            if( ! map.containsKey( c.getId() ) ) {
                // 没有对货币设定任何汇率值，沿用默认值
                CurrencyRate rate = new CurrencyRate();
                rate.setCurrency( c );
                rate.setRate( c.getDefaultRate() );
                rate.setMonth( new Date() );
                result.add( rate );
            }
        }
        return result;
    }
}
