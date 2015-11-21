package com.ksa.web.struts2.action.finance.invoice;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.finance.InvoiceService;


public class InvoiceEditAction extends InvoiceAction {

    /**
     * 
     */
    private static final long serialVersionUID = 3380407353975122628L;

    @Override
    protected String doExecute() throws Exception {
        InvoiceService service = ServiceContextUtils.getService( InvoiceService.class );
        invoice = service.loadInvoiceById( invoice.getId() );
        return SUCCESS;
    }
}
