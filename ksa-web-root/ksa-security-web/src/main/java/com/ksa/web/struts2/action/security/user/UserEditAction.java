package com.ksa.web.struts2.action.security.user;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.security.SecurityService;

public class UserEditAction extends UserAction {

    private static final long serialVersionUID = -9072525696383435396L;

    @Override
    public String doExecute() throws Exception {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        this.user = service.loadUserById( user.getId() );
        return SUCCESS;
    }
}
