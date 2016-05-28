package com.ksa.dao.finance.mybatis;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.finance.AccountDao;
import com.ksa.model.bd.Partner;
import com.ksa.model.finance.Account;
import com.ksa.model.security.User;

import junit.framework.Assert;


public class MybatisAccountDaoTest extends MybatisDaoTest {
    
    private static User TEST_USER1 = new User();
    private static User TEST_USER2 = new User();
    private static Partner TEST_PARTNER1 = new Partner();
    private static Partner TEST_PARTNER2 = new Partner();
    static {
        TEST_USER1.setId( "test-user-1" );
        TEST_USER2.setId( "test-user-2" );
        TEST_PARTNER1.setId( "test-partner-1" );
        TEST_PARTNER2.setId( "test-partner-2" );
        
    }
    
    @SuppressWarnings( "deprecation" )
    @Test
    public void testCrudAccount() throws RuntimeException {
        AccountDao dao = CONTEXT.getBean( "accountDao", AccountDao.class );
        
        Account account = new Account();
        Date now = new Date( 2000, 1, 2);
        String id = UUID.randomUUID().toString();
        account.setId( id );
        account.setCode( "code1" );
        account.setTarget( TEST_PARTNER1 );        
        account.setCreator( TEST_USER1 );
        account.setCreatedDate( now );
        account.setDeadline( now );
        account.setPaymentDate( now );
        account.setNote( "note1" );
        account.setState( 1 );
        
        // 测试插入是否成功
        dao.insertAccount( account );
        Account temp = dao.selectAccountById( id );
        Assert.assertEquals( "code1", temp.getCode() );
        Assert.assertEquals( TEST_USER1.getId(), temp.getCreator().getId() );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getTarget().getId() );
        Assert.assertEquals( now, temp.getCreatedDate() );
        Assert.assertEquals( now, temp.getDeadline() );
        Assert.assertEquals( now, temp.getPaymentDate() );
        Assert.assertEquals( "note1", temp.getNote() );
        Assert.assertEquals( 1, temp.getState() );
        
        now = new Date( 2012, 11, 20 );
        account.setCode( "code2" );
        account.setTarget( TEST_PARTNER2 );        
        account.setCreator( TEST_USER2 );
        account.setCreatedDate( now );
        account.setDeadline( now );
        account.setPaymentDate( now );
        account.setNote( "note2" );
        account.setState( 2 );
        
        // 测试更新是否成功
        dao.updateAccount( account );
        temp = dao.selectAccountById( id );
        Assert.assertEquals( "code1", temp.getCode() ); // code 不能修改
        Assert.assertEquals( TEST_USER2.getId(), temp.getCreator().getId() );
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getTarget().getId() );
        Assert.assertEquals( now, temp.getCreatedDate() );
        Assert.assertEquals( now, temp.getDeadline() );
        Assert.assertEquals( now, temp.getPaymentDate() );
        Assert.assertEquals( "note2", temp.getNote() );
        Assert.assertEquals( 1, temp.getState() );  // 状态单独修改
        
        // 测试删除
        dao.deleteAccount( account );
        temp = dao.selectAccountById( id );
        Assert.assertNull( temp );
    }
}
