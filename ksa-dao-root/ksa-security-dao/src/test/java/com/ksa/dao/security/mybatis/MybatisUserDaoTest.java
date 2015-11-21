package com.ksa.dao.security.mybatis;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.security.UserDao;
import com.ksa.model.security.User;

public class MybatisUserDaoTest extends MybatisDaoTest {
    
    @Test
    public void testSelectUserById() throws RuntimeException {
        UserDao dao = CONTEXT.getBean( "userDao", UserDao.class );
        User user = dao.selectUserById( "test-user-1" );
        Assert.assertEquals( "麻文强", user.getName() );
        Assert.assertEquals( "a@a.a", user.getEmail() );
        Assert.assertEquals( "123456", user.getTelephone() );
        Assert.assertEquals( "fEqNCco3Yq9h5ZUglD3CZJT4lBs", user.getPassword() );
        Assert.assertFalse( user.isLocked() );
    }
    
    @Test
    public void testSelectAllUser() throws RuntimeException {
        UserDao dao = CONTEXT.getBean( "userDao", UserDao.class );
        List<User> list = dao.selectAllUser();
        Assert.assertEquals( 2, list.size() );
    }
    
    @Test
    public void testCrudUser() throws RuntimeException {
        UserDao dao = CONTEXT.getBean( "userDao", UserDao.class );
        
        User user = new User();
        user.setId( "custom-id" );
        user.setName( "麻文强" );
        user.setPassword( "333" );
        user.setEmail( "b@b.b" );
        user.setTelephone( "444" );
        user.setLocked( false );
        
        // 测试插入是否成功
        dao.insertUser( user );        
        User temp = dao.selectUserById( "custom-id" );
        Assert.assertEquals( "麻文强", temp.getName() );
        Assert.assertEquals( "b@b.b", temp.getEmail() );
        Assert.assertEquals( "444", temp.getTelephone() );
        Assert.assertEquals( "333", temp.getPassword() );
        Assert.assertFalse( temp.isLocked() );
        
        user.setName( "强文麻" );
        user.setPassword( "555" );
        user.setEmail( "c@c.c" );
        user.setTelephone( "666" );
        user.setLocked( true );
        
        // 测试更新是否成功
        dao.updateUser( user );
        User temp2 = dao.selectUserById( "custom-id" );
        Assert.assertEquals( "强文麻", temp2.getName() );
        Assert.assertEquals( "c@c.c", temp2.getEmail() );
        Assert.assertEquals( "666", temp2.getTelephone() );
        Assert.assertEquals( "555", temp2.getPassword() );
        Assert.assertTrue( temp2.isLocked() );
        
        // 测试删除
        dao.deleteUser( user );
        User temp3 = dao.selectUserById( "custom-id" );
        Assert.assertNull( temp3 );
    }

}
