package com.ksa.dao.security;

import java.util.List;

import com.ksa.model.security.Permission;

/**
 * 权限数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface PermissionDao {
    
    /**
     * 从数据存储库中读取所有权限数据。
     * @return 所有权限数据列表
     * @throws RuntimeException 数据操作异常
     */
    List<Permission> selectAllPermission() throws RuntimeException;
    
    /**
     * 通过权限标识从数据存储库中查询权限数据。
     * @param id 权限标识
     * @return 当特定标识的权限存在时返回对应的权限模型，否则返回 null
     * @throws RuntimeException 数据操作异常
     */
   Permission selectPermissionById( String id ) throws RuntimeException;
   
   /**
    * 通过角色标识从数据存储库中查询角色所具有的权限数据。
    * @param roleId 角色标识
    * @return 角色所含权限数据列表
    * @throws RuntimeException 数据操作异常
    */
   List<Permission> selectPermissionByRoleId( String roleId ) throws RuntimeException;
}
