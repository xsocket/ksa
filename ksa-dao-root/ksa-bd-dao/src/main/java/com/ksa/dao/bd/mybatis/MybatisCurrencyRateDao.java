package com.ksa.dao.bd.mybatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.bd.CurrencyRateDao;
import com.ksa.model.bd.Currency;
import com.ksa.model.bd.CurrencyRate;

/**
 * 基于 Mybaits 的 CurrencyRateDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisCurrencyRateDao extends AbstractMybatisDao implements CurrencyRateDao {

    @Override
    public int insertRate( CurrencyRate rate ) throws RuntimeException {
        if( rate.getMonth() == null ) {
            return this.session.insert( "insert-bd-rate-bypartner", rate );
        } else {
            return this.session.insert( "insert-bd-rate-bydate", rate );
        }
    }

    @Override
    public int updateRate( CurrencyRate rate ) throws RuntimeException {
        // 先尝试按日期更新
        int count = this.session.update( "update-bd-rate-bydate", rate );
        if( count == 0 ) {
            return this.session.update( "update-bd-rate-bypartner", rate );
        } 
        return count;
    }

    @Override
    public int deleteRate( CurrencyRate rate ) throws RuntimeException {
        CurrencyRate c = this.session.selectOne( "select-bd-rate-bydate-byid", rate.getId() );
        if( c == null ) {
            return this.session.delete( "delete-bd-rate-bypartner", rate );
        }
        return this.session.delete( "delete-bd-rate-bydate", rate );
    }
    
    @Override
    public Currency selectCurrencyById( String currencyId ) throws RuntimeException {
        return this.session.selectOne( "select-bd-currency", currencyId );
    }
    
    @Override
    public List<Currency> selectAllCurrency() throws RuntimeException {
        return this.session.selectList( "select-bd-currency" );
    }
    
    @Override
    public List<CurrencyRate> selectLatestRates() throws RuntimeException {
        return this.selectLatestRates(  new Date() );
    }

    @Override
    public List<CurrencyRate> selectLatestRates( Date date ) throws RuntimeException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put( "date", date );
        return this.session.selectList( "select-bd-rate-latest", paras );
    }
    
    @Override
    public CurrencyRate selectLatestRate( String currencyId, Date date ) throws RuntimeException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put( "date", date );
        paras.put( "currencyId", currencyId );
        return this.session.selectOne( "select-bd-rate-latest", paras );
    }
    
    @Override
    public CurrencyRate selectLatestRate( String currencyId ) throws RuntimeException {
        return this.selectLatestRate( currencyId, new Date() /* now */ );
    }

    @Override
    public List<CurrencyRate> selectRateByDate( Date start, Date end ) throws RuntimeException {
        return selectRateByDate( start, end, null );
    }

    @Override
    public List<CurrencyRate> selectRateByDate( Date start, Date end, String currencyId ) throws RuntimeException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put( "startDate", start );
        paras.put( "endDate", end );
        if( StringUtils.hasText( currencyId ) ) {
            paras.put( "currencyId", currencyId );
        }
        return this.session.selectList( "select-bd-rate-bydate", paras );
    }

    @Override
    public List<CurrencyRate> selectRateByPartner( String partnerId ) throws RuntimeException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put( "partnerId", partnerId );
        return this.session.selectList( "select-bd-rate-bypartner", paras );
    }

    @Override
    public CurrencyRate selectRateByPartner( String partnerId, String currencyId ) throws RuntimeException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put( "partnerId", partnerId );
        paras.put( "currencyId", currencyId );
        return this.session.selectOne( "select-bd-rate-bypartner", paras );
    }

    @Override
    public CurrencyRate selectRateById( String id ) throws RuntimeException {
        CurrencyRate rate = this.session.selectOne( "select-bd-rate-bydate-byid", id );
        if( rate == null ) {
            return this.session.selectOne( "select-bd-rate-bypartner-byid", id );
        }
        return rate;
    }

    @Override
    public List<CurrencyRate> selectAllRates() throws RuntimeException {
        return this.session.selectList( "select-bd-rate-all" );
    }

}
