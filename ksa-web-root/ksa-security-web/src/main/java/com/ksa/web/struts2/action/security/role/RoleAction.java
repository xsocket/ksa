package com.ksa.web.struts2.action.security.role;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ksa.model.security.Role;
import com.ksa.model.security.User;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RoleAction extends DefaultActionSupport implements ModelDriven<Role> {

    private static final long serialVersionUID = 1043814543367840831L;
    
    protected Role role = new Role();
    
    @Override
    public String execute() throws Exception {
        if( ! SecurityUtils.isPermitted( "security:role" ) ) {
            return NO_PERMISSION;
        }
        String result = super.execute();
        if( SUCCESS == result ) {
            Map<String, User> cache = SecurityUtils.getCache();
            Set<String> clearUserIds = new HashSet<String>();
            for( User user : cache.values() ) {
                for( Role r : user.getRoles() ) {
                    if( r.getId().equals( role.getId() ) ) {
                        clearUserIds.add( user.getId() );
                    }
                }
            }
            if( ! clearUserIds.isEmpty() ) {
                Iterator<String> ids = clearUserIds.iterator();
                while( ids.hasNext() ) {
                    String userId = ids.next();
                    if( cache.containsKey( userId ) ) {
                        SecurityUtils.getCache().remove( userId );
                    }
                }
            }
            
        }
        return result;
    }

    @Override
    public Role getModel() {
        return getRole();
    }
    
    public Role getRole() {
        return role;
    }

}
