package com.ksa.dao.finance.mybatis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.finance.ChargeDao;
import com.ksa.model.bd.ChargeType;
import com.ksa.model.bd.Currency;
import com.ksa.model.bd.Partner;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.security.User;

import junit.framework.Assert;


public class MybatisChargeDaoTest extends MybatisDaoTest {
    
    private static User TEST_USER = new User();
    private static Partner TEST_PARTNER1 = new Partner();
    private static Partner TEST_PARTNER2 = new Partner();
    private static BookingNote TEST_BN = new BookingNote();
    private static ChargeType TEST_CHARGE_TYPE1 = new ChargeType();
    private static ChargeType TEST_CHARGE_TYPE2 = new ChargeType();
    static {
        TEST_PARTNER1.setId( "test-partner-1" );
        TEST_PARTNER2.setId( "test-partner-2" );
        TEST_USER.setId( "test-user-1" );
        TEST_BN.setId( "test-bookingnote-1" );
        TEST_CHARGE_TYPE1.setId( "10-charge-001" );
        TEST_CHARGE_TYPE2.setId( "10-charge-002" );
    }
    
    @SuppressWarnings( "deprecation" )
    @Test
    public void testCrudCharge() throws RuntimeException {
        ChargeDao dao = CONTEXT.getBean( "chargeDao", ChargeDao.class );
        
        Charge charge = new Charge();
        String id = UUID.randomUUID().toString();
        Date now = new Date( 2000, 2, 2 );
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        charge.setId( id );
        charge.setDirection( 1 );
        charge.setTarget( TEST_PARTNER1 );
        charge.setPrice( 3.0f );
        charge.setQuantity( 2.0f );
        charge.setAmount( 6.0f );
        charge.setBookingNote( TEST_BN );
        charge.setCreator( TEST_USER );
        charge.setType( TEST_CHARGE_TYPE1.getId() );
        charge.setCurrency( Currency.USD );
        charge.setCreatedDate( now );
        charge.setNote( "note1" );
        
        // 测试插入是否成功
        dao.insertCharge( charge );
        Charge temp = dao.selectChargeById( id );
        Assert.assertEquals( df.format( now ), df.format( temp.getCreatedDate() ) );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getTarget().getId() );
        Assert.assertEquals( 1, temp.getDirection() );
        Assert.assertEquals( 3.0f, temp.getPrice(), 0.001 );
        Assert.assertEquals( 2, temp.getQuantity().intValue() );
        Assert.assertEquals( 6.0f, temp.getAmount(), 0.001 );
        Assert.assertEquals( "note1", temp.getNote() );
        Assert.assertEquals( TEST_CHARGE_TYPE1.getId(), temp.getType() );
        Assert.assertEquals( TEST_USER.getId(), temp.getCreator().getId() );
        Assert.assertEquals( Currency.USD.getId(), temp.getCurrency().getId() );
        Assert.assertEquals( Currency.USD.getName(), temp.getCurrency().getName() );
        
        Date now2 = new Date( 2011, 3, 3 );
        charge.setCreatedDate( now2 );
        charge.setDirection( -1 );
        charge.setTarget( TEST_PARTNER2 );
        charge.setPrice( 4.0f );
        charge.setQuantity( 3.0f );
        charge.setAmount( 12.0f );
        charge.setType( TEST_CHARGE_TYPE2.getId() );
        charge.setCurrency( Currency.RMB );
        charge.setNote( "note2" );
        
        // 测试更新是否成功
        dao.updateCharge( charge );
        temp = dao.selectChargeById( id );
        Assert.assertEquals( df.format( now ), df.format( temp.getCreatedDate() ) );    // 创建时间不更新
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getTarget().getId() );
        Assert.assertEquals( -1, temp.getDirection() );
        Assert.assertEquals( 4.0f, temp.getPrice(), 0.001 );
        Assert.assertEquals( 3, temp.getQuantity().intValue() );
        Assert.assertEquals( 12.0f, temp.getAmount(), 0.001 );
        Assert.assertEquals( "note2", temp.getNote() );
        Assert.assertEquals( TEST_CHARGE_TYPE2.getId(), temp.getType() );
        Assert.assertEquals( Currency.RMB.getId(), temp.getCurrency().getId() );
        Assert.assertEquals( Currency.RMB.getName(), temp.getCurrency().getName() );
        
        // 测试删除
        dao.deleteCharge( charge );
        temp = dao.selectChargeById( id );
        Assert.assertNull( temp );
    }
}
