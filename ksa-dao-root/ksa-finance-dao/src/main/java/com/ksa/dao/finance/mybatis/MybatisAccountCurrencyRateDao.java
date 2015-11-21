package com.ksa.dao.finance.mybatis;

import java.util.List;

import com.ksa.dao.bd.mybatis.MybatisCurrencyRateDao;
import com.ksa.dao.finance.AccountCurrencyRateDao;
import com.ksa.model.finance.AccountCurrencyRate;


public class MybatisAccountCurrencyRateDao extends MybatisCurrencyRateDao implements AccountCurrencyRateDao  {

    @Override
    public int insertRate( AccountCurrencyRate rate ) throws RuntimeException {
        return this.session.insert( "insert-finance-account-rate", rate );
    }

    @Override
    public int updateRate( AccountCurrencyRate rate ) throws RuntimeException {
        return this.session.update( "update-finance-account-rate", rate );
    }

    @Override
    public int deleteRate( AccountCurrencyRate rate ) throws RuntimeException {
        return this.session.delete( "delete-finance-account-rate", rate );
    }

    @Override
    public List<AccountCurrencyRate> selectRatesByAccountId( String accountId ) throws RuntimeException {
        return this.session.selectList( "select-finance-account-rates", accountId );
    }
    
    @Override
    public AccountCurrencyRate selectRateById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-finance-account-rate-byid", id );
    }

}
