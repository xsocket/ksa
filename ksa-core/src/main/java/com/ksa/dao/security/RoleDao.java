package com.ksa.dao.security;

import java.util.List;

import com.ksa.model.security.Role;

/**
 * 角色数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface RoleDao {

    /**
     * 向数据存储库中插入一个角色数据。
     * @param role 角色模型
     * @return 插入操作影响的数据数量
     * @throws RuntimeException 数据操作异常
     */
    int insertRole( Role role ) throws RuntimeException;
    
    /**
     * 从数据存储库中更新一个角色数据。
     * @param role 角色模型
     * @return 更新操作影响的数据数量
     * @throws RuntimeException 数据操作异常
     */
    int updateRole( Role role ) throws RuntimeException;
    
    /**
     * 从数据存储库中删除一个角色数据。
     * @param role 角色模型
     * @return 删除操作影响的数据数量
     * @throws RuntimeException 数据操作异常
     */
    int deleteRole( Role role ) throws RuntimeException;
    
    /**
     * 从数据存储库中读取所有角色数据。
     * @return 所有角色模型列表
     * @throws RuntimeException 数据操作异常
     */
    List<Role> selectAllRole() throws RuntimeException;
    
    /**
     * 通过角色标识从数据存储库中查询角色数据。
     * @param id 角色标识
     * @return 当特定标识的角色存在时返回对应的角色模型，否则返回 null
     * @throws RuntimeException 数据操作异常
     */
   Role selectRoleById( String id ) throws RuntimeException;
   
   /**
    * 向数据存储库中插入一个角色的权限。
    * @param roleId 角色标识
    * @param permissionId 权限标识
    * @return 插入操作影响的数据数量
    * @throws RuntimeException 数据操作异常
    */
   int insertRolePermission( String roleId, String permissionId ) throws RuntimeException;
   
   /**
    * 从数据存储库中删除一个角色的权限。
    * @param roleId 角色标识
    * @param permissionId 权限标识
    * @return 删除操作影响的数据数量
    * @throws RuntimeException 数据操作异常
    */
   int deleteRolePermission( String roleId, String permissionId ) throws RuntimeException;
}
