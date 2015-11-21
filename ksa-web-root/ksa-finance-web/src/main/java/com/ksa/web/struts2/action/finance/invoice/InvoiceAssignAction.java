package com.ksa.web.struts2.action.finance.invoice;

import org.springframework.util.StringUtils;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.finance.InvoiceService;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;


public class InvoiceAssignAction extends InvoiceAction implements JsonAction {

    private static final long serialVersionUID = 35148775615646492L;
    
    private JsonResult result;

    @Override
    public String doExecute() throws Exception {
        InvoiceService service = ServiceContextUtils.getService( InvoiceService.class );
        invoice = service.assignInvoiceToAccount( invoice, invoice.getAccount() );
        String message = "";
        if( invoice.getAccount() == null || ! StringUtils.hasText( invoice.getAccount().getId() ) ) {
            message = String.format( "销账还原成功。发票号：'%s'。", invoice.getCode() );
        } else {
            message = String.format( "销账成功。发票号：'%s'，结算/对账单号：'%s'。", invoice.getCode(), invoice.getAccount().getCode() );
        }
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
