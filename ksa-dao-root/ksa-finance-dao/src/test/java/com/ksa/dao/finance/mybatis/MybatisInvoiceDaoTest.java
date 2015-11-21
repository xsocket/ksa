package com.ksa.dao.finance.mybatis;

import java.util.Date;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.finance.InvoiceDao;
import com.ksa.model.bd.Currency;
import com.ksa.model.bd.Partner;
import com.ksa.model.finance.Invoice;
import com.ksa.model.security.User;


public class MybatisInvoiceDaoTest extends MybatisDaoTest {
    
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
    public void testCrudInvoice() throws RuntimeException {
        InvoiceDao dao = CONTEXT.getBean( "invoiceDao", InvoiceDao.class );
        
        Invoice invoice = new Invoice();
        String id = UUID.randomUUID().toString();
        Date now = new Date( 1999, 1, 1 );
        invoice.setId( id );
        invoice.setCode( "code1" );
        invoice.setNumber( "number1" );
        invoice.setTaxNumber( "taxNumber1" );
        invoice.setAmount( 1.0f );
        invoice.setType( "type1" );
        invoice.setNote( "note1" );
        invoice.setCreatedDate( now );
        invoice.setCreator( TEST_USER1 );
        invoice.setTarget( TEST_PARTNER1 );
        invoice.setCurrency( Currency.USD );
        
        // 测试插入是否成功
        dao.insertInvoice( invoice );
        Invoice temp = dao.selectInvoiceById( id );
        Assert.assertEquals( 1.0f, temp.getAmount(), 0.001 );
        Assert.assertEquals( "code1", temp.getCode() );
        Assert.assertEquals( "number1", temp.getNumber() );
        Assert.assertEquals( "taxNumber1", temp.getTaxNumber() );
        Assert.assertEquals( "type1", temp.getType() );
        Assert.assertEquals( "note1", temp.getNote() );
        Assert.assertEquals( now, temp.getCreatedDate() );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getTarget().getId() );
        Assert.assertEquals( TEST_USER1.getId(), temp.getCreator().getId() );
        Assert.assertEquals( Currency.USD.getId(), temp.getCurrency().getId() );
        
        
        now = new Date( 2000, 2, 2 );
        invoice.setCode( "code2" );
        invoice.setNumber( "number2" );
        invoice.setTaxNumber( "taxNumber2" );
        invoice.setAmount( 2.0f );
        invoice.setType( "type2" );
        invoice.setNote( "note2" );
        invoice.setCreatedDate( now );
        invoice.setCreator( TEST_USER2 );
        invoice.setTarget( TEST_PARTNER2 );
        invoice.setCurrency( Currency.RMB );
        
        // 测试更新是否成功
        dao.updateInvoice( invoice );
        temp = dao.selectInvoiceById( id );
        Assert.assertEquals( 2.0f, temp.getAmount(), 0.001 );
        Assert.assertEquals( "code2", temp.getCode() );
        Assert.assertEquals( "number2", temp.getNumber() );
        Assert.assertEquals( "taxNumber2", temp.getTaxNumber() );
        Assert.assertEquals( "type2", temp.getType() );
        Assert.assertEquals( "note2", temp.getNote() );
        Assert.assertEquals( now, temp.getCreatedDate() );
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getTarget().getId() );
        Assert.assertEquals( TEST_USER2.getId(), temp.getCreator().getId() );
        Assert.assertEquals( Currency.RMB.getId(), temp.getCurrency().getId() );
        
        // 测试删除
        dao.deleteInvoice( invoice );
        temp = dao.selectInvoiceById( id );
        Assert.assertNull( temp );
    }
}
