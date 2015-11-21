package com.ksa.web.struts2.action.bd.partner;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.bd.PartnerService;

public class PartnerEditAction extends PartnerAction {

    private static final long serialVersionUID = -8451721824534508563L;

    @Override
    public String doExecute() throws Exception {
        PartnerService service = ServiceContextUtils.getService( PartnerService.class );
        partner = service.loadPartnerById( partner.getId() );
        return SUCCESS;
    }
}
