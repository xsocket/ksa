package com.ksa.dao.security.mybatis;

import java.util.List;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.security.PermissionDao;
import com.ksa.model.security.Permission;

/**
 * 基于 Mybaits 的 PermissionDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisPermissionDao extends AbstractMybatisDao implements PermissionDao {

    @Override
    public List<Permission> selectAllPermission() throws RuntimeException {
        return this.session.selectList( "select-security-permission-all" );
    }

    @Override
    public Permission selectPermissionById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-security-permission-byid", id );
    }

    @Override
    public List<Permission> selectPermissionByRoleId( String roleId ) throws RuntimeException {
        return this.session.selectList( "select-security-permission-byroleid", roleId );
    }
}
