package com.ksa.dao.bd.mybatis;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.bd.CurrencyRateDao;
import com.ksa.model.bd.Currency;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.bd.Partner;

public class MybatisCurrencyRateDaoTest extends MybatisDaoTest {
    
    private static final Currency TEST_CURRENCY = new Currency();
    private static final Currency TEST_CURRENCY2 = new Currency();
    private static final Partner TEST_PARTNER = new Partner();
    static {
        TEST_CURRENCY.setId( "00-currency-USD" );
        TEST_CURRENCY.setCode( "USD" );
        TEST_CURRENCY.setName( "美元" );
        TEST_CURRENCY.setExtra( "6.800" );
        
        TEST_CURRENCY2.setId( "00-currency-HKD" );
        TEST_CURRENCY2.setCode( "HKD" );
        TEST_CURRENCY2.setName( "港币" );
        TEST_CURRENCY2.setExtra( "1.3" );
        
        TEST_PARTNER.setId( "test-partner-1" );
        TEST_PARTNER.setCode( "test-partner-1" );
        TEST_PARTNER.setName( "测试合作伙伴1" );
    }
    
    @SuppressWarnings( "deprecation" )
    @Test
    public void testSelectLatestRate() throws RuntimeException {
        CurrencyRateDao dao = CONTEXT.getBean( "currencyRateDao", CurrencyRateDao.class );
        
        // 美元 10月1日
        CurrencyRate rate = new CurrencyRate();
        rate.setId( UUID.randomUUID().toString() );
        rate.setCurrency( TEST_CURRENCY );
        rate.setMonth( new Date( 2012, 9, 1 ) );
        rate.setRate( 10.1f );
        dao.insertRate( rate ); 
        // 美元 11月1日
        rate = new CurrencyRate();
        rate.setId( UUID.randomUUID().toString() );
        rate.setCurrency( TEST_CURRENCY );
        rate.setMonth( new Date( 2012, 10, 1 ) );
        rate.setRate( 11.1f );
        dao.insertRate( rate );
        // 美元 12月1日
        rate = new CurrencyRate();
        rate.setId( UUID.randomUUID().toString() );
        rate.setCurrency( TEST_CURRENCY );
        rate.setMonth( new Date( 2012, 11, 1 ) );
        rate.setRate( 12.1f );
        dao.insertRate( rate );
        
        // 港币 9月1日
        rate = new CurrencyRate();
        rate.setId( UUID.randomUUID().toString() );
        rate.setCurrency( TEST_CURRENCY2 );
        rate.setMonth( new Date( 2012, 8, 1 ) );
        rate.setRate( 0.81f );
        dao.insertRate( rate ); 
        // 港币 10月1日
        rate = new CurrencyRate();
        rate.setId( UUID.randomUUID().toString() );
        rate.setCurrency( TEST_CURRENCY2 );
        rate.setMonth( new Date( 2012, 9, 1 ) );
        rate.setRate( 0.91f );
        dao.insertRate( rate );
        
        // 港币 11月1日
        rate = new CurrencyRate();
        rate.setId( UUID.randomUUID().toString() );
        rate.setCurrency( TEST_CURRENCY2 );
        rate.setMonth( new Date( 2012, 10, 1 ) );
        rate.setRate( 1.01f );
        dao.insertRate( rate );
        
        // 开始测试
        // 1.  11月（包含）之前最近的所有汇率
        List<CurrencyRate> list = dao.selectLatestRates( new Date( 2012, 10, 1 ) );
        Assert.assertEquals( 2, list.size() );
        Assert.assertEquals( 11.1f, list.get( 0 ).getRate(), 0.001 );   // 美元11月的汇率
        Assert.assertEquals( 1.01f, list.get( 1 ).getRate(), 0.001 );   // 港币11月的汇率
        
        // 2.  12月（包含）之前最近的所有汇率
        list = dao.selectLatestRates( new Date( 2012, 11, 1 ) );
        Assert.assertEquals( 2, list.size() );
        Assert.assertEquals( 12.1f, list.get( 0 ).getRate(), 0.001 );   // 美元12月的汇率
        Assert.assertEquals( 1.01f, list.get( 1 ).getRate(), 0.001 );   // 港币11月的汇率
        
        // 3.  9月（包含）之前最近的所有汇率
        list = dao.selectLatestRates( new Date( 2012, 8, 1 ) );
        Assert.assertEquals( 1, list.size() );
        Assert.assertEquals( 0.81f, list.get( 0 ).getRate(), 0.001 );   // 港币9月的汇率
    }
    
    @Test
    public void testCrudCurrencyRateByDate() throws RuntimeException {
        CurrencyRateDao dao = CONTEXT.getBean( "currencyRateDao", CurrencyRateDao.class );
        
        CurrencyRate rate = new CurrencyRate();
        String id = UUID.randomUUID().toString();
        @SuppressWarnings( "deprecation" )
        Date now = new Date( 1985, 10, 28 );
        rate.setId( id );
        rate.setCurrency( TEST_CURRENCY );
        rate.setRate( 123.456f );
        rate.setMonth( now );
        
        
        // 测试插入是否成功
        dao.insertRate( rate );        
        CurrencyRate temp = dao.selectRateById( id );
        Assert.assertEquals( now, temp.getMonth() );
        Assert.assertEquals( 123.456f, temp.getRate(), 0.001 );
        Assert.assertEquals( TEST_CURRENCY.getId(), temp.getCurrency().getId() );
        
        rate.setRate( 654.321f );

        // 测试更新是否成功
        Assert.assertEquals( 1, dao.updateRate( rate ) );
        CurrencyRate temp2 = dao.selectRateById( id );
        Assert.assertNotNull( temp2 );
        Assert.assertEquals( 654.321f, temp2.getRate(), 0.001 );
        
        // 测试删除
        Assert.assertEquals( 1, dao.deleteRate( rate ) );
        CurrencyRate temp3 = dao.selectRateById( id );
        Assert.assertNull( temp3 );
    }
    
    @Test
    public void testCrudCurrencyRateByPartner() throws RuntimeException {
        CurrencyRateDao dao = CONTEXT.getBean( "currencyRateDao", CurrencyRateDao.class );
        
        CurrencyRate rate = new CurrencyRate();
        String id = UUID.randomUUID().toString();
        rate.setId( id );
        rate.setCurrency( TEST_CURRENCY );
        rate.setRate( 123.456f );
        rate.setPartner( TEST_PARTNER );
        
        
        // 测试插入是否成功
        dao.insertRate( rate );        
        CurrencyRate temp = dao.selectRateById( id );
        Assert.assertEquals( TEST_PARTNER.getId(), temp.getPartner().getId() );
        Assert.assertEquals( 123.456f, temp.getRate(), 0.001 );
        Assert.assertEquals( TEST_CURRENCY.getId(), temp.getCurrency().getId() );
        
        rate.setRate( 654.321f );

        // 测试更新是否成功
        Assert.assertEquals( 1, dao.updateRate( rate ) );
        CurrencyRate temp2 = dao.selectRateById( id );
        Assert.assertNotNull( temp2 );
        Assert.assertEquals( 654.321f, temp2.getRate(), 0.001 );
        
        // 测试删除
        Assert.assertEquals( 1, dao.deleteRate( rate ) );
        CurrencyRate temp3 = dao.selectRateById( id );
        Assert.assertNull( temp3 );
    }

}
