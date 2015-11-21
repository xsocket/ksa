package com.ksa.service.security;

import com.ksa.model.security.Role;
import com.ksa.model.security.User;

/**
 * 系统安全管理服务接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface SecurityService {
    
    /**
     * 根据用户标识获取用户数据。
     * @param userId 用户标识
     * @return 对应标识的用户数据
     * @throws RuntimeException 对应标识的用户不存在
     */
    User loadUserById( String userId ) throws RuntimeException;

    /**
     * 创建新用户。
     * @param user 将要被创建的用户数据
     * @return 创建成功后的用户数据
     * @throws RuntimeException
     */
    User createUser( User user ) throws RuntimeException;
    
    /**
     * 创建新用户。
     * @param user 将要被创建的用户数据
     * @param roleIds 新用户初始所属角色标识的列表
     * @return 创建成功后的用户数据
     * @throws RuntimeException
     */
    User createUser( User user, String[] roleIds ) throws RuntimeException;
    
    /**
     * 锁定用户。
     * @param user 需要被锁定的用户数据
     * @return 锁定后的用户数据
     * @throws RuntimeException
     */
    User lockUser( User user ) throws RuntimeException;
    
    /**
     * 激活用户。
     * @param user 需要被激活的用户数据
     * @return 激活后的用户数据
     * @throws RuntimeException
     */
    User unlockUser( User user ) throws RuntimeException;
    
    /**
     * 更新用户基本信息（不更新密码）。
     * @param user 新的用户数据
     * @return 更新成功后的用户数据
     * @throws RuntimeException
     */
    User modifyUser( User user ) throws RuntimeException;
    
    /**
     * 更新用户密码。
     * @param userId 用户标识
     * @param oldPassword 用户原登录密码
     * @param newPassword 用户新登录密码
     * @return 更新成功后的用户数据
     * @throws RuntimeException
     */
    User modifyUser( String userId, String oldPassword, String newPassword ) throws RuntimeException;
    
    /**
     * 更新用户基本信息（包含更新密码）。
     * @param user 新的用户数据
     * @param oldPassword 用户原登录密码
     * @param newPassword 用户新登录密码
     * @return 更新成功后的用户数据
     * @throws RuntimeException
     */
    User modifyUser( User user, String oldPassword, String newPassword ) throws RuntimeException;
    
    /**
     * 移除用户数据。
     * @param user 需要被移除的用户数据
     * @return 移除成功后的用户数据
     * @throws RuntimeException
     */
    User removeUser( User user ) throws RuntimeException;
    
    /**
     * 根据角色标识获取角色数据。
     * @param roleId 角色标识
     * @return 对应标识的角色数据
     * @throws RuntimeException 对应标识的角色不存在
     */
    Role loadRoleById( String roleId ) throws RuntimeException;
    
    
    /**
     * 创建新角色。
     * @param role 将要被创建的角色数据
     * @return 创建成功后的角色数据
     * @throws RuntimeException
     */
    Role createRole( Role role ) throws RuntimeException;
    
    /**
     * 创建新角色。
     * @param role 将要被创建的角色数据
     * @param userIds 角色初始所包含的用户标识列表
     * @param permissionIds 角色初始所包含的权限标识列表
     * @return 创建成功后的角色数据
     * @throws RuntimeException
     */
    Role createRole( Role role, String[] userIds, String[] permissionIds ) throws RuntimeException;
    
    /**
     * 更新角色基本信息（不更新密码）。
     * @param role 新的角色数据
     * @return 更新成功后的角色数据
     * @throws RuntimeException
     */
    Role modifyRole( Role role ) throws RuntimeException;
    
    /**
     * 移除角色数据。
     * @param role 需要被移除的角色数据
     * @return 移除成功后的角色数据
     * @throws RuntimeException
     */
    Role removeRole( Role role ) throws RuntimeException;
    
}
