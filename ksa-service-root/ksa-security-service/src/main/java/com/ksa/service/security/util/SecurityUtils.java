package com.ksa.service.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.security.UserDao;
import com.ksa.model.security.User;
import com.ksa.util.codec.Base64;


public class SecurityUtils {
    
    private static final Logger log = LoggerFactory.getLogger( SecurityUtils.class );
    
    private static UserDao userDao;
    private static Map<String, User> cache = new HashMap<String, User>();   // 用户缓存

    static {
        userDao = ServiceContextUtils.getService( UserDao.class );
    }

    /**
     * 获取当前登录的用户
     * @return
     */
    public static User getCurrentUser() {
        return org.apache.shiro.SecurityUtils.getSubject().getPrincipals().oneByType( User.class );
    }
    
    /**
     * 通过用户标识获取用户信息。
     * @param id 用户标识
     * @return 返回对应标识的用户，如果用户不存在则返回 null 。
     */
    public static User getUser( String id ) {
        if( cache.containsKey( id ) ) {
            return cache.get( id );
        }
        User user = userDao.selectUserById( id );
        if( user != null ) {
            cache.put( id, user );
            return user;
        }
        return null;
    }
    
    /**
     * 获取用户缓存
     * @return
     */
    public static Map<String, User> getCache() {
        return cache;
    }
    
    public static String digestPassword( String password ) {
        try {
            MessageDigest md = MessageDigest.getInstance( "SHA-1" );
            byte[] passBytes = md.digest( Base64.convertToBytes( password ) );
            return Base64.convertToString( Base64.encode( passBytes ) );
        } catch( NoSuchAlgorithmException e ) {
            log.warn( "获取消息散列算法工具失败。", e );
            return password;
        }
    }
    
    /**
     * Returns {@code true} if this Subject is permitted to perform an action or access a resource summarized by the
     * specified permission string.
     * <p/>
     * This is an overloaded method for the corresponding type-safe {@link Permission Permission} variant.
     * Please see the class-level JavaDoc for more information on these String-based permission methods.
     *
     * @param permission the String representation of a Permission that is being checked.
     * @return true if this Subject is permitted, false otherwise.
     * @see #isPermitted(Permission permission)
     * @since 0.9
     */
    public static boolean isPermitted( String permission ) {
        return org.apache.shiro.SecurityUtils.getSubject().isPermitted( permission );
    }
}
