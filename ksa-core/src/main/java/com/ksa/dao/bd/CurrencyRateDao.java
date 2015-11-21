package com.ksa.dao.bd;

import java.util.Date;
import java.util.List;

import com.ksa.model.bd.Currency;
import com.ksa.model.bd.CurrencyRate;

/**
 * 结算货币数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface CurrencyRateDao {

    int insertRate( CurrencyRate rate ) throws RuntimeException;
    int updateRate( CurrencyRate rate ) throws RuntimeException;
    int deleteRate( CurrencyRate rate ) throws RuntimeException;
    
    /**
     * 获取所有结算货币。
     * @return 所有结算货币的列表
     * @throws RuntimeException
     */
    List<Currency> selectAllCurrency() throws RuntimeException;
    
    /**
     * 获取特定货币。
     * @param id 给定的货币标识
     * @return 返回给定标识的货币。
     * @throws RuntimeException
     */
    Currency selectCurrencyById( String id ) throws RuntimeException;
    
    /**
     * 获取当前所有货币的最新汇率。
     * @return 
     * @throws RuntimeException 数据获取失败
     */
    List<CurrencyRate> selectLatestRates() throws RuntimeException;
    
    /**
     * 获取距离 date（包含） 最近的所有货币汇率。
     * @param date 限定日期
     * @return 
     * @throws RuntimeException 数据获取失败
     */
    List<CurrencyRate> selectLatestRates( Date date ) throws RuntimeException;
    
    /**
     * 获取特定货币最近的汇率（按记录汇率的日期倒序排列的第一条记录），如果没有记录过汇率则返回 null 。
     * @param currencyId 货币类型标识
     * @return 特定货币最近的汇率，如果不存在则返回 null 。
     * @throws RuntimeException
     */
    CurrencyRate selectLatestRate( String currencyId ) throws RuntimeException;
    
    /**
     * 获取特定货币具体日期之前（包含）最近的汇率（按记录汇率的日期倒序排列的小于等于给定日期的第一条记录），
     * 如果没有记录过汇率则返回 null 。
     * @param currencyId 货币类型标识
     * @param date 限定日期
     * @return 特定货币最近的汇率，如果不存在则返回 null 。
     * @throws RuntimeException
     */
    CurrencyRate selectLatestRate( String currencyId, Date date ) throws RuntimeException;
    
    /**
     * 获取某一日期范围内的货币汇率信息。
     * @param start 开始日期（包含）
     * @param end 结束日期（不包含）
     * @return 
     * @throws RuntimeException 数据获取失败
     */
    List<CurrencyRate> selectRateByDate( Date start, Date end ) throws RuntimeException;
    
    /**
     * 获取具体货币某一日期范围内的汇率信息。
     * @param start 开始日期（包含）标识
     * @param end 结束日期（不包含）
     * @param currencyId 货币类型
     * @return 
     * @throws RuntimeException 数据获取失败
     */
    List<CurrencyRate> selectRateByDate( Date start, Date end, String currencyId ) throws RuntimeException;
    
    /**
     * 获取某一用户特有的结算货币汇率信息。
     * @param customerId 客户标识
     * @return
     * @throws RuntimeException
     */
    List<CurrencyRate> selectRateByPartner( String customerId ) throws RuntimeException;
    
    /**
     * 获取具体货币某一用户特有的结算货币汇率信息。
     * @param customerId 客户标识
     * @param currencyId 货币类型标识
     * @return
     * @throws RuntimeException
     */
    CurrencyRate selectRateByPartner( String customerId, String currencyId ) throws RuntimeException;
    
    /**
     * 通过唯一标识获取汇率数据。
     * @param id 汇率数据的标识
     * @return
     * @throws RuntimeException
     */
    CurrencyRate selectRateById( String id ) throws RuntimeException;
    
    /**
     * 获取所有汇率设置。
     * @return
     * @throws RuntimeException
     */
    List<CurrencyRate> selectAllRates() throws RuntimeException;
    
}
