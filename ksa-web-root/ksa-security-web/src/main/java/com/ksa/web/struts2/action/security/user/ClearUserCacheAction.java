package com.ksa.web.struts2.action.security.user;

import java.util.Map;

import com.ksa.model.security.User;
import com.ksa.service.security.util.SecurityUtils;

public class ClearUserCacheAction extends UserAction {

    private static final long serialVersionUID = -1927950658081965492L;

    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if( SUCCESS == result ) {
            Map<String, User> cache = SecurityUtils.getCache();
            if( cache.containsKey( user.getId() ) ) {
                SecurityUtils.getCache().remove( user.getId() );
            }
        }
        return result;
    }

}
