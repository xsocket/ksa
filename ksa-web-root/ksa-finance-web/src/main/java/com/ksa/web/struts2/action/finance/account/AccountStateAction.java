package com.ksa.web.struts2.action.finance.account;

import java.util.Date;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.finance.Account;
import com.ksa.model.finance.AccountState;
import com.ksa.service.finance.AccountService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;


public class AccountStateAction extends AccountAction implements JsonAction {

    private static final long serialVersionUID = -1301276137468934314L;
    
    private JsonResult result;
    
    @Override
    public String execute() throws Exception {
        int state = account.getState();
        if( AccountState.isChecked( state ) ) {
            if( ! SecurityUtils.isPermitted( "finance:account-check" ) ) {
                result = new JsonResult( ERROR, "对不起，您没有权限进行结算/对账单审核操作！" );
                return ERROR;
            }
        } else if( AccountState.isSettled( state ) ) {
            if( ! SecurityUtils.isPermitted( "finance:account-settle" ) ) {
                result = new JsonResult( ERROR, "对不起，您没有权限进行结算/对账单结算完毕操作！" );
                return ERROR;
            }
        }
        return super.execute();
    }

    @Override
    public String doExecute() throws Exception {
        AccountService service = ServiceContextUtils.getService( AccountService.class );
        
        // 结算完毕状态，则必须首先保存结算完毕日期
        if( AccountState.isSettled( account.getState() ) ) {
            Account temp = service.loadAccountById( account.getId() );
            if( temp == null ) {
                result = new JsonResult( SUCCESS, "%s不存在，请确认提交数据的正确性。",( account.getDirection() == 1 ? "结算单" : "对账单" ) );
            }
            
            if( temp.getPaymentDate() == null ) {
                if( account.getPaymentDate() != null ) {
                    temp.setPaymentDate( account.getPaymentDate() );
                } else {
                    temp.setPaymentDate( new Date() );
                }
                service.saveAccount( temp, null );
            }
        }
        
        account = service.updateAccountState( account );
        String message = String.format( "成功更新%s '%s' 的状态。",( account.getDirection() == 1 ? "结算单" : "对账单" ), account.getCode() );        
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message );
        return SUCCESS;
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next() );
        }
        return this.result;
    }
}
