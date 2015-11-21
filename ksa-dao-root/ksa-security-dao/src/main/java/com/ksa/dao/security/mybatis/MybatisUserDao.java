package com.ksa.dao.security.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.security.UserDao;
import com.ksa.model.security.User;

/**
 * 基于 Mybaits 的 UserDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisUserDao extends AbstractMybatisDao implements UserDao {

    @Override
    public int insertUser( User user ) throws RuntimeException {
        return this.session.insert( "insert-security-user", user );
    }

    @Override
    public int updateUser( User user ) throws RuntimeException {
        return this.session.update( "update-security-user", user );
    }

    @Override
    public int deleteUser( User user ) throws RuntimeException {
        return this.session.delete( "delete-security-user", user );
    }

    @Override
    public List<User> selectAllUser() throws RuntimeException {
        return this.session.selectList( "select-security-user-all" );
    }

    @Override
    public User selectUserById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-security-user-byid", id );
    }

    @Override
    public int insertUserRole( String userId, String roleId ) throws RuntimeException {
        Map<String, String> params = new HashMap<String, String>();
        params.put( "userId", userId );
        params.put( "roleId", roleId );
        return this.session.insert( "insert-security-userrole", params );
    }

    @Override
    public int deleteUserRole( String userId, String roleId ) throws RuntimeException {
        Map<String, String> params = new HashMap<String, String>();
        params.put( "userId", userId );
        params.put( "roleId", roleId );
        return this.session.delete( "delete-security-userrole", params );
    }

}
