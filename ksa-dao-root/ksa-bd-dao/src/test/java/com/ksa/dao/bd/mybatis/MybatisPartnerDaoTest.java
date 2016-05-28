package com.ksa.dao.bd.mybatis;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.bd.PartnerDao;
import com.ksa.model.bd.Partner;
import com.ksa.model.bd.PartnerType;

public class MybatisPartnerDaoTest extends MybatisDaoTest {

    @Test
    public void testSelectPartnerById() throws RuntimeException {
        PartnerDao dao = CONTEXT.getBean( "partnerDao", PartnerDao.class );
        Partner data = dao.selectPartnerById( "test-partner-1" );
        Assert.assertEquals( "test-partner-1", data.getCode() );
        Assert.assertEquals( "测试合作伙伴1", data.getName() );
        Assert.assertEquals( "Alias1-1", data.getAlias() );
        Assert.assertEquals( "", data.getAddress() );
        Assert.assertEquals( "", data.getNote() );
        Assert.assertEquals( 30, data.getPp() );
        Assert.assertEquals( 1, data.getRank() );
        Assert.assertTrue( data.getImportant() == 1 );
        Assert.assertEquals( "test-user-1", data.getSaler().getId() );
        String[] extras = data.getExtras();
        Assert.assertEquals( 2, extras.length );
        Assert.assertEquals( "extra1-1", extras[0] );
        Assert.assertEquals( "extra1-2", extras[1] );
        
        PartnerType[] types = data.getTypes();
        Assert.assertEquals( 1, types.length );
        Assert.assertEquals( "20-department-bgh", types[0].getId() );
        Assert.assertEquals( "BGH", types[0].getCode() );
        Assert.assertEquals( "报关行", types[0].getName() );
    }
    
    @Test
    public void testCrudPartner() throws RuntimeException {
        PartnerDao dao = CONTEXT.getBean( "partnerDao", PartnerDao.class );
        
        Partner partner = new Partner();
        String id = UUID.randomUUID().toString();
        partner.setId( id );
        partner.setCode( "code" );
        partner.setName( "name" );
        partner.setAlias( "alias" );
        partner.setAddress( "address" );
        partner.setNote( "note" );
        partner.setPp( 30 );
        partner.setRank( 100 );
        partner.setImportant( 0 );
        partner.getSaler().setId( "test-user-1" );
        
        PartnerType type1 = new PartnerType();
        type1.setId( "20-department-dls" );
        PartnerType type2 = new PartnerType();
        type2.setId( "20-department-gys" );
        
        // 测试插入是否成功
        dao.insertPartner( partner );        
        dao.insertPartnerExtra( partner, "extra1" );
        dao.insertPartnerExtra( partner, "extra2" );
        dao.insertPartnerType( partner, type1 );
        dao.insertPartnerType( partner, type2 );
        
        Partner temp = dao.selectPartnerById( id );
        Assert.assertEquals( "code", temp.getCode() );
        Assert.assertEquals( "name", temp.getName() );
        Assert.assertEquals( "alias", temp.getAlias() );
        Assert.assertEquals( "address", temp.getAddress() );
        Assert.assertEquals( "note", temp.getNote() );
        Assert.assertEquals( 30, temp.getPp() );
        Assert.assertEquals( 100, temp.getRank() );
        Assert.assertTrue( temp.getImportant() == 0 );
        Assert.assertEquals( "test-user-1", temp.getSaler().getId() );
        
        String[] extras = temp.getExtras();
        Assert.assertEquals( 2, extras.length );
        Assert.assertEquals( "extra1", extras[0] );
        Assert.assertEquals( "extra2", extras[1] );
        
        PartnerType[] types = temp.getTypes();
        Assert.assertEquals( 2, types.length );
        Assert.assertEquals( "20-department-dls", types[0].getId() );
        Assert.assertEquals( "DLS", types[0].getCode() );
        Assert.assertEquals( "代理商", types[0].getName() );
        Assert.assertEquals( "20-department-gys", types[1].getId() );
        Assert.assertEquals( "GYS", types[1].getCode() );
        Assert.assertEquals( "供应商", types[1].getName() );
        
        
        // 开始更新
        partner.setCode( "code1" );
        partner.setName( "name1" );
        partner.setAlias( "alias1" );
        partner.setAddress( "address1" );
        partner.setNote( "note1" );
        partner.setImportant( 1 );
        partner.setPp( 300 );
        partner.setRank( 1000 );
        partner.getSaler().setId( "test-user-2" );
        
        // 测试更新是否成功
        dao.updatePartner( partner );
        dao.deletePartnerExtra( partner, "extra2" );
        dao.deletePartnerType( partner, type2 );
        
        temp = dao.selectPartnerById( id );
        Assert.assertEquals( "code1", temp.getCode() );
        Assert.assertEquals( "name1", temp.getName() );
        Assert.assertEquals( "alias1", temp.getAlias() );
        Assert.assertEquals( "address1", temp.getAddress() );
        Assert.assertEquals( "note1", temp.getNote() );
        Assert.assertEquals( 300, temp.getPp() );
        Assert.assertEquals( 1000, temp.getRank() );
        Assert.assertTrue( temp.getImportant() == 1 );
        Assert.assertEquals( "test-user-2", temp.getSaler().getId() );
        
        extras = temp.getExtras();
        Assert.assertEquals( 1, extras.length );
        Assert.assertEquals( "extra1", extras[0] );
        
        types = temp.getTypes();
        Assert.assertEquals( 1, types.length );
        Assert.assertEquals( "20-department-dls", types[0].getId() );
        Assert.assertEquals( "DLS", types[0].getCode() );
        Assert.assertEquals( "代理商", types[0].getName() );
        
        // 测试删除
        dao.deletePartner( partner );
        Partner temp3 = dao.selectPartnerById( id );
        Assert.assertTrue(temp3.getImportant() == -1 );
    }

}
