package com.ksa.service.security.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.ksa.model.security.Permission;
import com.ksa.model.security.Role;
import com.ksa.model.security.User;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;

public class ShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo( PrincipalCollection principals ) {
        User user = ( User ) principals.getPrimaryPrincipal();
        
        if( user != null ) {
            user = SecurityUtils.getUser( user.getId() );
            List<Role> roles = user.getRoles();
            if( roles != null && roles.size() > 0 ) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();            
                for( Role role : roles ) {    
                    info.addRole( role.getName() );
                    for( Permission p : role.getPermissions() ) {
                        info.addStringPermission( p.getId() );
                    }
                }
                return info;
            }
        }
        
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken authcToken ) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        String userId = token.getUsername();
        // 用户名密码验证
        if( StringUtils.hasText( userId ) ) {
            User user = SecurityUtils.getUser( userId );
            if( user != null ) {
                if( user.isLocked() ) {
                    throw new LockedAccountException( String.format( "用户 '%s' 已被锁定。", userId ) );
                }
                return new SimpleAuthenticationInfo( user, user.getPassword(), getName() );
            }
        }
        return null;
    }

}