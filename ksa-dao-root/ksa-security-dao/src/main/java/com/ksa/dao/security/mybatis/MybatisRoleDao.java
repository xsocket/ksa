package com.ksa.dao.security.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.security.RoleDao;
import com.ksa.model.security.Role;

/**
 * 基于 Mybaits 的 RoleDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisRoleDao extends AbstractMybatisDao implements RoleDao {

    @Override
    public int insertRole( Role role ) throws RuntimeException {
        return this.session.insert( "insert-security-role", role );
    }

    @Override
    public int updateRole( Role role ) throws RuntimeException {
        return this.session.update( "update-security-role", role );
    }

    @Override
    public int deleteRole( Role role ) throws RuntimeException {
        return this.session.delete( "delete-security-role", role );
    }

    @Override
    public List<Role> selectAllRole() throws RuntimeException {
        return this.session.selectList( "select-security-role-all" );
    }

    @Override
    public Role selectRoleById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-security-role-byid", id );
    }

    @Override
    public int insertRolePermission( String roleId, String permissionId ) throws RuntimeException {
        Map<String, String> params = new HashMap<String, String>();
        params.put( "roleId", roleId );
        params.put( "permissionId", permissionId );
        return this.session.insert( "insert-security-rolepermission", params );
    }

    @Override
    public int deleteRolePermission( String roleId, String permissionId ) throws RuntimeException {
        Map<String, String> params = new HashMap<String, String>();
        params.put( "roleId", roleId );
        params.put( "permissionId", permissionId );
        return this.session.delete( "delete-security-rolepermission", params );
    }

}
