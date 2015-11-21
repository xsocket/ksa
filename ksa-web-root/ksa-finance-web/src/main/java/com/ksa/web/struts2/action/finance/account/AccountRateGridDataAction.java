package com.ksa.web.struts2.action.finance.account;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.finance.Account;
import com.ksa.service.finance.AccountService;
import com.ksa.web.struts2.action.data.GridDataActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class AccountRateGridDataAction extends GridDataActionSupport implements ModelDriven<Account> {

    private static final long serialVersionUID = -7173546634356708920L;
    
    protected Account account = new Account();
    protected Object[] gridDataArray = new Object[0]; 
    
    @Override
    protected String doExecute() throws Exception {
        AccountService service = ServiceContextUtils.getService( AccountService.class );
        gridDataArray = service.loadAccountCurrencyRates( account ).toArray();
        return SUCCESS;
    }

    @Override
    protected Object[] queryGridData() {
        return gridDataArray;
    }

    @Override
    protected int queryGridDataCount() {
        return gridDataArray.length;
    }

    @Override
    public Account getModel() {
        return account;
    }

}
