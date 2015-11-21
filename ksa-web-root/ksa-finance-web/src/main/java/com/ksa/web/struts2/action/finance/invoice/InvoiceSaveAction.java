package com.ksa.web.struts2.action.finance.invoice;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.finance.InvoiceService;


public class InvoiceSaveAction extends InvoiceAction {

    private static final long serialVersionUID = 3806493572595278852L;

    @Override
    protected String doExecute() throws Exception {
        InvoiceService service = ServiceContextUtils.getService( InvoiceService.class );
        invoice = service.saveInvoice( invoice );
        addActionMessage( String.format( "发票号为 '%s' 的发票信息保存成功。", invoice.getCode() ) );
        return SUCCESS;
    }

}
