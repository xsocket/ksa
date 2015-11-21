package com.ksa.web.struts2.action.statistics.cargo;

import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;


public class CargoAction extends DefaultActionSupport {

    private static final long serialVersionUID = 8893035224879940025L;

    @Override
    public String execute() throws Exception {
        if( !SecurityUtils.isPermitted( "statistics:cargo" ) ) {
            return NO_PERMISSION;
        }
        return super.execute();
    }

}
