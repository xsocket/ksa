package com.ksa.dao.security;

import java.util.List;

import com.ksa.model.security.User;

/**
 * 用户数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface UserDao {

    /**
     * 向数据存储库中插入一个用户数据。
     * @param user 用户模型
     * @return 插入操作影响的数据数量
     * @throws RuntimeException 数据操作异常
     */
    int insertUser( User user ) throws RuntimeException;
    
    /**
     * 从数据存储库中更新一个用户数据。
     * @param user 用户模型
     * @return 更新操作影响的数据数量
     * @throws RuntimeException 数据操作异常
     */
    int updateUser( User user ) throws RuntimeException;
    
    /**
     * 从数据存储库中删除一个用户数据。
     * @param user 用户模型
     * @return 删除操作影响的数据数量
     * @throws RuntimeException 数据操作异常
     */
    int deleteUser( User user ) throws RuntimeException;
    
    /**
     * 从数据存储库中读取所有用户数据。
     * @return 所有用户模型列表
     * @throws RuntimeException 数据操作异常
     */
    List<User> selectAllUser() throws RuntimeException;
    
    /**
     * 通过用户标识从数据存储库中查询用户数据。
     * @param id 用户标识
     * @return 当特定标识的用户存在时返回对应的用户模型，否则返回 null
     * @throws RuntimeException 数据操作异常
     */
   User selectUserById( String id ) throws RuntimeException;
   
   /**
    * 向数据存储库中插入一个用户的角色。
    * @param userId 用户标识
    * @param roleId 角色标识
    * @return 插入操作影响的数据数量
    * @throws RuntimeException 数据操作异常
    */
   int insertUserRole( String userId, String roleId ) throws RuntimeException;
   
   /**
    * 从数据存储库中删除一个用户的角色。
    * @param userId 用户标识
    * @param roleId 角色标识
    * @return 删除操作影响的数据数量
    * @throws RuntimeException 数据操作异常
    */
   int deleteUserRole( String userId, String roleId ) throws RuntimeException;
}
