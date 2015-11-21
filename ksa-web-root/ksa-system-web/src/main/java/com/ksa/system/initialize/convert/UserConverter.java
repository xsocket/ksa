package com.ksa.system.initialize.convert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.security.RoleDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.security.Role;
import com.ksa.model.security.User;
import com.ksa.service.security.SecurityService;
import com.ksa.system.initialize.model.YongHu;
import com.ksa.util.StringUtils;

public class UserConverter  {
    
    // 供别的转换器使用的 合作伙伴缓存
    private static Map<String, User> userMap = new HashMap<String, User>();
    public static Map<String, User> getUserMap() {
        return userMap;
    }
    
    public static void doConvert( SqlSession session ) {
        List<YongHu> list = getAllYongHu( session );
        Map<String, Role> map = getRoleMap();
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        
        // 保存用户
        for( YongHu yh : list ) {
            User user = new User();
            user.setId( yh.getId() );
            user.setEmail( yh.getEmail() );
            user.setName( yh.getUsername() );
            user.setPassword( yh.getPassword() );
            user.setTelephone( yh.getTelephone() );
            if( map.containsKey( yh.getRole() ) ) {
                service.createUser( user, new String[]{ map.get( yh.getRole() ).getId() } );
            } else {
                service.createUser( user );
            }
            userMap.put( user.getName(), user );
        }
    }
    
    /**
     *  插入已被删除但是业务中使用的用户
     */
    private static User createdDeletedUser( String userName ) {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        User user = new User();
        user.setId( ModelUtils.generateRandomId() );
        user.setName( userName );
        user.setEmail( "" );
        user.setLocked( true );
        user.setTelephone( "" );
        user.setPassword( "123456" );
        
        user = service.createUser( user );
        
        userMap.put( userName, user );
        return user;
    }
    
    public static User getUser( String name ) {
        if( ! StringUtils.hasText( name ) ) {
            return null;
        }
        User user = userMap.get( name );
        if( user != null ) {
            return user;
        } else {
            user = createdDeletedUser( name );
            return user;
        }
    }
    
    private static Map<String, Role> getRoleMap() {
        List<Role> roles = ServiceContextUtils.getService( RoleDao.class ).selectAllRole();
        Map<String, Role> map = new HashMap<String, Role>();
        for(Role role : roles) {
            map.put( role.getName(), role );
        }
        return map;
    }

    private static List<YongHu> getAllYongHu( SqlSession session ) {
        return session.selectList( "select-init-yonghu" );
    }

}
