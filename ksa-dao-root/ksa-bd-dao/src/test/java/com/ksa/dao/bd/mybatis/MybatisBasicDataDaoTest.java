package com.ksa.dao.bd.mybatis;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.bd.BasicDataDao;
import com.ksa.model.bd.BasicData;
import com.ksa.model.bd.BasicDataType;

public class MybatisBasicDataDaoTest extends MybatisDaoTest {
    
    @Test
    public void testSelectBasicDataById() throws RuntimeException {
        BasicDataDao dao = CONTEXT.getBean( "basicDataDao", BasicDataDao.class );
        BasicData data = dao.selectBasicDataById( "00-currency-RMB" );
        Assert.assertEquals( "RMB", data.getCode() );
        Assert.assertEquals( "人民币", data.getName() );
        Assert.assertEquals( "", data.getAlias() );
        Assert.assertEquals( "", data.getNote() );
        Assert.assertEquals( "1.000", data.getExtra() );
        Assert.assertEquals( BasicDataType.CURRENCY.getId(), data.getType().getId() );
        Assert.assertEquals( BasicDataType.CURRENCY.getName(), data.getType().getName() );
    }
    
    @Test
    public void testSelectBasicDataByType() throws RuntimeException {
        BasicDataDao dao = CONTEXT.getBean( "basicDataDao", BasicDataDao.class );
        dao.selectBasicDataByType( BasicDataType.CURRENCY.getId() );
    }
    
    @Test
    public void testCrudBasicData() throws RuntimeException {
        BasicDataDao dao = CONTEXT.getBean( "basicDataDao", BasicDataDao.class );
        
        BasicData data = new BasicData();
        String id = UUID.randomUUID().toString();
        data.setId( id );
        data.setCode( "code" );
        data.setName( "name" );
        data.setAlias( "alias" );
        data.setExtra( "extra" );
        data.setNote( "note" );
        data.setType( BasicDataType.UNITS );
        
        // 测试插入是否成功
        dao.insertBasicData( data );        
        BasicData temp = dao.selectBasicDataById( id );
        Assert.assertEquals( "code", temp.getCode() );
        Assert.assertEquals( "name", temp.getName() );
        Assert.assertEquals( "alias", temp.getAlias() );
        Assert.assertEquals( "note", temp.getNote() );
        Assert.assertEquals( "extra", temp.getExtra() );
        Assert.assertEquals( BasicDataType.UNITS.getId(), temp.getType().getId() );
        Assert.assertEquals( BasicDataType.UNITS.getName(), temp.getType().getName() );
        
        data.setCode( "code1" );
        data.setName( "name1" );
        data.setAlias( "alias1" );
        data.setExtra( "extra1" );
        data.setNote( "note1" );

        // 测试更新是否成功
        dao.updateBasicData( data );
        BasicData temp2 = dao.selectBasicDataById( id );
        Assert.assertNotNull( temp2 );
        Assert.assertEquals( "code1", temp2.getCode() );
        Assert.assertEquals( "name1", temp2.getName() );
        Assert.assertEquals( "alias1", temp2.getAlias() );
        Assert.assertEquals( "note1", temp2.getNote() );
        Assert.assertEquals( "extra1", temp2.getExtra() );
        
        // 测试删除
        dao.deleteBasicData( data );
        BasicData temp3 = dao.selectBasicDataById( id );
        Assert.assertNull( temp3 );
    }

}
