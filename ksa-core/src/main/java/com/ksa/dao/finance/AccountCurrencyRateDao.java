package com.ksa.dao.finance;

import java.util.List;

import com.ksa.dao.bd.CurrencyRateDao;
import com.ksa.model.finance.AccountCurrencyRate;

/**
 * 基于结算单的货币汇率 DAO
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface AccountCurrencyRateDao extends CurrencyRateDao {

    int insertRate( AccountCurrencyRate rate ) throws RuntimeException;
    int updateRate( AccountCurrencyRate rate ) throws RuntimeException;
    int deleteRate( AccountCurrencyRate rate ) throws RuntimeException;
    List<AccountCurrencyRate> selectRatesByAccountId( String accountId ) throws RuntimeException;
}
