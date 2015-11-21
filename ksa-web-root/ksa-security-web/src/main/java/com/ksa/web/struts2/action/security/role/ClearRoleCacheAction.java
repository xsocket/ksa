package com.ksa.web.struts2.action.security.role;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ksa.model.security.Role;
import com.ksa.model.security.User;
import com.ksa.service.security.util.SecurityUtils;

public class ClearRoleCacheAction extends RoleAction {

    private static final long serialVersionUID = 3589674958127087998L;

    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if( SUCCESS == result ) {
            clearUserCache();
        }
        return result;
    }
    
    protected void clearUserCache() {
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

}
