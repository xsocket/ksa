package com.ksa.service.security.impl;

import java.util.UUID;

import com.ksa.dao.security.RoleDao;
import com.ksa.dao.security.UserDao;
import com.ksa.model.security.Role;
import com.ksa.model.security.User;
import com.ksa.service.security.SecurityService;
import com.ksa.service.security.util.SecurityUtils;

/**
 * 系统安全服务默认实现。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class SecurityServiceImpl implements SecurityService {
    
    private UserDao userDao;
    private RoleDao roleDao;
    
    @Override
    public User loadUserById( String userId ) throws RuntimeException {
        User user = userDao.selectUserById( userId );
        if( user == null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的用户不存在。", userId ) );
        }
        return user;
    }

    @Override
    public User createUser( User user ) throws RuntimeException {
        return createUser( user, null );
    }
    
    @Override
    public User createUser( User user, String[] roleIds ) throws RuntimeException {
        User temp = userDao.selectUserById( user.getId() );
        if( temp != null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的用户已经存在。", user.getId() ) );
        }
        
        // 散列保存密码
        user.setPassword( SecurityUtils.digestPassword( user.getPassword() ) );
        
        int length = userDao.insertUser( user );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "新增用户数据发生异常，期望新增 1 条数据，实际新增了 %d 条数据。", user.getId() ) );
        }
        
        String userId = user.getId();
        // 插入初始角色
        if( roleIds != null && roleIds.length > 0 ) {
            for( String roleId : roleIds ) {
                userDao.insertUserRole( userId, roleId );
            }
        }
        
        return user;
    }
    
    @Override
    public User lockUser( User user ) throws RuntimeException {
        return doLockUser( user, true );
    }

    @Override
    public User unlockUser( User user ) throws RuntimeException {
        return doLockUser( user, false );
    }
    
    private User doLockUser( User user, boolean isLock ) throws RuntimeException {
        User temp = userDao.selectUserById( user.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的用户不存在。", user.getId() ) );
        }
        
        temp.setLocked( isLock );
        int length = userDao.updateUser( temp );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "锁定/激活用户数据发生异常，期望操作 1 条数据，实际操作了 %d 条数据。", user.getId() ) );
        }

        return temp;
    }
    
    @Override
    public User modifyUser( User user ) throws RuntimeException {
        User temp = userDao.selectUserById( user.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的用户不存在。", user.getId() ) );
        }
        
        // 不更新密码
        user.setPassword( temp.getPassword() );
        int length = userDao.updateUser( user );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "更新用户数据发生异常，期望更新 1 条数据，实际更新了 %d 条数据。", user.getId() ) );
        }

        return user;
    }
    
    @Override
    public User modifyUser( String userId, String oldPassword, String newPassword ) throws RuntimeException {
        User temp = userDao.selectUserById( userId );
        
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的用户不存在。", userId ) );
        }
        
        if( ! temp.getPassword().equals( SecurityUtils.digestPassword( oldPassword ) ) ) {
            throw new IllegalArgumentException( "用户原密码输入有误。" );
        }
        
        // 新密码
        temp.setPassword( SecurityUtils.digestPassword( newPassword ) );
        int length = userDao.updateUser( temp );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "更新用户密码发生异常，期望更新 1 条数据，实际更新了 %d 条数据。", userId ) );
        }

        return temp;
    }

    @Override
    public User modifyUser( User user, String oldPassword, String newPassword ) throws RuntimeException {
        User temp = userDao.selectUserById( user.getId() );
        
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的用户不存在。", user.getId() ) );
        }
        
        if( ! temp.getPassword().equals( SecurityUtils.digestPassword( oldPassword ) ) ) {
            throw new IllegalArgumentException( "用户原密码输入有误。" );
        }
        
        // 新密码
        user.setPassword( SecurityUtils.digestPassword( newPassword ) );
        int length = userDao.updateUser( user );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "更新用户数据发生异常，期望更新 1 条数据，实际更新了 %d 条数据。", user.getId() ) );
        }

        return user;
    }
    
    @Override
    public User removeUser( User user ) throws RuntimeException {
        User temp = userDao.selectUserById( user.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的用户不存在。", user.getId() ) );
        }
        
        // TODO 其他判断用户是否可以被删除的逻辑
        int length = userDao.deleteUser( user );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "删除用户数据发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", user.getId() ) );
        }
        
        return temp;
    }
    
    //------------------- 角色相关操作 -------------------------------//
    
    @Override
    public Role loadRoleById( String roleId ) throws RuntimeException {
        Role role = roleDao.selectRoleById( roleId );
        if( role == null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的角色不存在。", roleId ) );
        }
        return role;
    }
    
    @Override
    public Role createRole( Role role ) throws RuntimeException {
        return createRole( role, null, null );
    }
    
    @Override
    public Role createRole( Role role, String[] userIds, String[] permissionIds ) throws RuntimeException {

        // 初始化角色的标识
        String roleId = UUID.randomUUID().toString();
        role.setId( roleId );
        
        int length = roleDao.insertRole( role );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "新增角色数据发生异常，期望新增 1 条数据，实际新增了 %d 条数据。", role.getId() ) );
        }
        
        // 插入初始用户
        if( userIds != null && userIds.length > 0 ) {
            for( String userId : userIds ) {
                userDao.insertUserRole( userId, roleId );
            }
        }
        // 插入初始权限
        if( permissionIds != null && permissionIds.length > 0 ) {
            for( String permissionId : permissionIds ) {
                roleDao.insertRolePermission( roleId, permissionId );
            }
        }
        
        return role;
    }

    @Override
    public Role modifyRole( Role role ) throws RuntimeException {
        Role temp = roleDao.selectRoleById( role.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的角色不存在。", role.getId() ) );
        }
        
        int length = roleDao.updateRole( role );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "更新角色数据发生异常，期望更新 1 条数据，实际更新了 %d 条数据。", role.getId() ) );
        }

        return role;
    }
    
    @Override
    public Role removeRole( Role role ) throws RuntimeException {
        Role temp = roleDao.selectRoleById( role.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "编号为 '%s' 的角色不存在。", role.getId() ) );
        }
        
        int length = roleDao.deleteRole( role );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "删除角色数据发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", role.getId() ) );
        }
        
        return temp;
    }
    


    public void setUserDao( UserDao userDao ) {
        this.userDao = userDao;
    }
    
    public void setRoleDao( RoleDao roleDao ) {
        this.roleDao = roleDao;
    }
}
