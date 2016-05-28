package com.ksa.web.struts2.action.bd.partner;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.bd.PartnerService;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class PartnerDeleteAction extends PartnerAction implements JsonAction {

    private static final long serialVersionUID = 8812278179336055682L;
    
    private JsonResult result;
    
    @Override
    public String doExecute() throws Exception {
        PartnerService service = ServiceContextUtils.getService( PartnerService.class );
        partner = service.removePartner( partner );
        String message = String.format( "成功冻结合作伙伴：'%s'。", partner.getName() );
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message, partner );
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();
        // 合作伙伴标识
        if( !StringUtils.hasText( partner.getId() ) ) {
            this.addActionError( "请输入合作伙伴信息的标识信息。" );
        } 
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next(), partner );
        }
        return this.result;
    }

}
