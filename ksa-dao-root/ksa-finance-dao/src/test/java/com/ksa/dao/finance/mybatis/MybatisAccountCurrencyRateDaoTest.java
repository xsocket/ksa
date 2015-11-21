package com.ksa.dao.finance.mybatis;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.finance.AccountCurrencyRateDao;
import com.ksa.model.bd.Currency;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.finance.Account;
import com.ksa.model.finance.AccountCurrencyRate;

public class MybatisAccountCurrencyRateDaoTest extends MybatisDaoTest {
    
    private static final Currency TEST_CURRENCY = new Currency();
    private static final Account TEST_ACCOUNT = new Account();
    static {
        TEST_CURRENCY.setId( "00-currency-USD" );
        TEST_CURRENCY.setCode( "USD" );
        TEST_CURRENCY.setName( "美元" );
        TEST_CURRENCY.setExtra( "6.800" );
        
        TEST_ACCOUNT.setId( "test-account-1" );
        TEST_ACCOUNT.setCode( "test-account-1" );
    }
    
    @Test
    public void testCrudCurrencyRateByDate() throws RuntimeException {
        AccountCurrencyRateDao dao = CONTEXT.getBean( "accountCurrencyRateDao", AccountCurrencyRateDao.class );
        
        AccountCurrencyRate rate = new AccountCurrencyRate();
        String id = UUID.randomUUID().toString();
        rate.setId( id );
        rate.setCurrency( TEST_CURRENCY );
        rate.setRate( 123.456f );
        rate.setAccount( TEST_ACCOUNT );
        
        
        // 测试插入是否成功
        dao.insertRate( rate );        
        AccountCurrencyRate temp = (AccountCurrencyRate) dao.selectRateById( id );
        Assert.assertEquals( 123.456f, temp.getRate(), 0.001 );
        Assert.assertEquals( TEST_CURRENCY.getId(), temp.getCurrency().getId() );
        Assert.assertEquals( TEST_ACCOUNT.getId(), temp.getAccount().getId() );
        
        rate.setRate( 654.321f );
        // 测试更新是否成功
        Assert.assertEquals( 1, dao.updateRate( rate ) );
        temp = (AccountCurrencyRate) dao.selectRateById( id );
        Assert.assertNotNull( temp );
        Assert.assertEquals( 654.321f, temp.getRate(), 0.001 );
        
        // 测试删除
        Assert.assertEquals( 1, dao.deleteRate( rate ) );
        CurrencyRate temp3 = dao.selectRateById( id );
        Assert.assertNull( temp3 );
    }
    
    

}
