package com.ksa.service.bd;

import java.util.Date;
import java.util.List;

import com.ksa.model.bd.CurrencyRate;


public interface CurrencyRateService {

    /**
     * 保存结算货币汇率。
     * @param rate 结算货币汇率
     * @return 创建成功后的汇率
     * @throws RuntimeException
     */
    CurrencyRate saveCurrencyRate( CurrencyRate rate ) throws RuntimeException;
    
    /**
     * 获取当前系统已有货币的最新汇率。
     * @return 已有货币的最新汇率
     * @throws RuntimeException
     */
    List<CurrencyRate> loadLatestCurrencyRates() throws RuntimeException;
    
    /**
     * 获取当前系统所有的汇率记录。
     * @return 当前系统所有的汇率记录
     * @throws RuntimeException
     */
    List<CurrencyRate> loadAllCurrencyRates() throws RuntimeException;
    
    /**
     * 获取给定时间（包含）之前设定的最近汇率。
     * @param date 给定的时间界限
     * @return 给定时间（包含）之前的最近汇率
     * @throws RuntimeException
     */
    List<CurrencyRate> loadLatestCurrencyRates( Date date ) throws RuntimeException;
    
    /**
     * 获取具体货币的最新汇率
     * @param currencyId 具体货币的标识
     * @return 具体货币的最新汇率
     * @throws RuntimeException
     */
    CurrencyRate loadLatestCurrencyRate( String currencyId ) throws RuntimeException;
    
    /**
     * 获取具体货币给定时间（包含）之前设定的最近汇率
     * @param currencyId 具体货币的标识
     * @param date 给定的时间界限
     * @return 具体货币的最新汇率
     * @throws RuntimeException
     */
    CurrencyRate loadLatestCurrencyRate( String currencyId, Date date ) throws RuntimeException;
        
    /**
     *  获取特定客户的汇率。
     * @param customerId 特定客户的标识
     * @return 特定客户的全部汇率
     * @throws RuntimeException
     */
    List<CurrencyRate> loadPartnerCurrencyRates( String customerId ) throws RuntimeException;
    
    /**
     * 获取特定客户具体货币的汇率。
     * @param customerId 特定客户的标识
     * @param currencyId 具体货币的标识
     * @return 特定客户的汇率
     * @throws RuntimeException
     */
    CurrencyRate loadPartnerCurrencyRate( String customerId, String currencyId ) throws RuntimeException;
}
