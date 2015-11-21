package com.ksa.web.struts2.action.finance.invoice;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.finance.Invoice;
import com.ksa.service.finance.AccountService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class InvoiceAction extends DefaultActionSupport implements ModelDriven<Invoice> {

    private static final long serialVersionUID = -8482885003379839294L;
    
    protected Invoice invoice = new Invoice();
    
    @Override
    public String execute() throws Exception {
        if( ! SecurityUtils.isPermitted( "finance:invoice" ) ) {
            return NO_PERMISSION;
        }
        return super.execute();
    }
    
    @Override
    protected String doExecute() throws Exception {
        invoice.setCreator( SecurityUtils.getCurrentUser() );
        String accountId = invoice.getAccount().getId();
        if( StringUtils.hasText( accountId ) ) {
            AccountService service = ServiceContextUtils.getService( AccountService.class );
            invoice.setAccount( service.loadAccountById( accountId ) );
        }
        return SUCCESS;
    }

    @Override
    public Invoice getModel() {
        return invoice;
    }

}
