package com.ksa.web.struts2.action.bd.partner;

import com.ksa.model.bd.Partner;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class PartnerAction extends DefaultActionSupport implements ModelDriven<Partner> {

    private static final long serialVersionUID = -5228129634318355749L;
    
    protected Partner partner = new Partner();
    
    @Override
    public String execute() throws Exception {
        // 权限校验
        if( ! SecurityUtils.isPermitted( "bd:partner" ) ) {
            return NO_PERMISSION;
        }
        return super.execute();
    }

    @Override
    public Partner getModel() {
        return getPartner();
    }
    
    public Partner getPartner() {
        return partner;
    }
}
