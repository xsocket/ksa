package com.ksa.web.struts2.action.security.role;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.security.SecurityService;


public class RoleEditAction extends RoleAction {

    private static final long serialVersionUID = 2230489602110541013L;

    @Override
    public String doExecute() throws Exception {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        this.role = service.loadRoleById( role.getId() );
        return SUCCESS;
    }
    
}
