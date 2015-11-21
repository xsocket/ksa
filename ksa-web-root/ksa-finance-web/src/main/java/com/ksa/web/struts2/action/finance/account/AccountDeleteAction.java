package com.ksa.web.struts2.action.finance.account;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.finance.AccountService;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class AccountDeleteAction extends AccountAction implements JsonAction {

    private static final long serialVersionUID = -8547400686834977166L;
    private JsonResult result;

    @Override
    public String doExecute() throws Exception {
        AccountService service = ServiceContextUtils.getService( AccountService.class );
        account = service.removeInvoice( account );
        String message = String.format( "成功删除账单：'%s'。", account.getCode() );
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message, account );
        return SUCCESS;
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next(), account );
        }
        return this.result;
    }
}
