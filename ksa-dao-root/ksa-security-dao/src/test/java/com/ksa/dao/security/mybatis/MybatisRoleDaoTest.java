package com.ksa.dao.security.mybatis;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.security.RoleDao;
import com.ksa.model.security.Permission;
import com.ksa.model.security.Role;

public class MybatisRoleDaoTest extends MybatisDaoTest {
    
    @Test
    public void testSelectRoleById() throws RuntimeException {
        RoleDao dao = CONTEXT.getBean( "roleDao", RoleDao.class );
        Role role = dao.selectRoleById( "test-role-1" );
        Assert.assertEquals( "系统管理员", role.getName() );
        Assert.assertEquals( "系统管理员描述", role.getDescription() );
        
        Permission[] ps = role.getPermissions();
        Assert.assertNotNull( ps );
        Assert.assertEquals( 4, ps.length );
    }
    
    @Test
    public void testSelectAllRole() throws RuntimeException {
        RoleDao dao = CONTEXT.getBean( "roleDao", RoleDao.class );
        List<Role> list = dao.selectAllRole();
        Assert.assertEquals( 2, list.size() );
        for( Role r : list ) {
            Assert.assertNull( r.getPermissions() );
        }
    }
    
    @Test
    public void testCrudRole() throws RuntimeException {
        RoleDao dao = CONTEXT.getBean( "roleDao", RoleDao.class );
        
        Role role = new Role();
        role.setId( "custom-role" );
        role.setName( "角色" );
        role.setDescription( "角色描述" );
        
        // 测试插入是否成功
        dao.insertRole( role );        
        Role temp = dao.selectRoleById( "custom-role" );
        Assert.assertEquals( "角色", temp.getName() );
        Assert.assertEquals( "角色描述", temp.getDescription() );
        
        role.setName( "角色1" );
        role.setDescription( "角色描述1" );
        
        // 测试更新是否成功
        dao.updateRole( role );
        Role temp2 = dao.selectRoleById( "custom-role" );
        Assert.assertEquals( "角色1", temp2.getName() );
        Assert.assertEquals( "角色描述1", temp2.getDescription() );
        
        // 测试权限的增删
        dao.insertRolePermission( "custom-role", "bookingnote:edit" );
        dao.insertRolePermission( "custom-role", "bookingnote:delete" );
        temp = dao.selectRoleById( "custom-role" );
        Permission[] ps = temp.getPermissions();
        Assert.assertNotNull( ps );
        Assert.assertEquals( 2, ps.length );
        
        dao.deleteRolePermission( "custom-role", "bookingnote:edit" );
        temp = dao.selectRoleById( "custom-role" );
        ps = temp.getPermissions();
        Assert.assertNotNull( ps );
        Assert.assertEquals( 1, ps.length );
        
        // 测试删除
        dao.deleteRole( role );
        Role temp3 = dao.selectRoleById( "custom-id" );
        Assert.assertNull( temp3 );
    }

}
