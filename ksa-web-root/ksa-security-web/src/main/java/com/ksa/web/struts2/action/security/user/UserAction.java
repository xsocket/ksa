package com.ksa.web.struts2.action.security.user;

import java.util.Map;

import com.ksa.model.security.User;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends DefaultActionSupport implements ModelDriven<User> {

    private static final long serialVersionUID = -3165373387119028521L;
    
    protected User user = new User();
    
    @Override
    public String execute() throws Exception {
        if( ! SecurityUtils.isPermitted( "security:user" ) ) {
            return NO_PERMISSION;
        }
        String result = super.execute();
        if( SUCCESS == result ) {
            Map<String, User> cache = SecurityUtils.getCache();
            if( cache.containsKey( user.getId() ) ) {
                SecurityUtils.getCache().remove( user.getId() );
            }
        }
        return result;
    }

    @Override
    public User getModel() {
        return getUser();
    }
    
    public User getUser() {
        return user;
    }

}
