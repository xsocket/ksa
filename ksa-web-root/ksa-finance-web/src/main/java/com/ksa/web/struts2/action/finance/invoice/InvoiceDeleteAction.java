package com.ksa.web.struts2.action.finance.invoice;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.finance.InvoiceService;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;


public class InvoiceDeleteAction extends InvoiceAction implements JsonAction {

    private static final long serialVersionUID = -52336492842713670L;
    
    private JsonResult result;

    @Override
    public String doExecute() throws Exception {
        InvoiceService service = ServiceContextUtils.getService( InvoiceService.class );
        invoice = service.removeInvoice( invoice );
        String message = String.format( "成功删除发票：'%s'。", invoice.getCode() );
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message, invoice );
        return SUCCESS;
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next(), invoice );
        }
        return this.result;
    }

}
