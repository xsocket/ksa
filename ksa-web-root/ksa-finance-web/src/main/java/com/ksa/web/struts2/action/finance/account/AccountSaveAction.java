package com.ksa.web.struts2.action.finance.account;

import java.util.List;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.finance.AccountCurrencyRate;
import com.ksa.service.finance.AccountService;
import com.ksa.util.StringUtils;


public class AccountSaveAction extends AccountEditAction {

    private static final long serialVersionUID = -1519773982733347627L;
    
    /** 结算单对应的汇率 */
    protected List<AccountCurrencyRate> rates;

    @Override
    protected String doExecute() throws Exception {
        AccountService service = ServiceContextUtils.getService( AccountService.class );
        
        boolean isNew = ! StringUtils.hasText( account.getId() );
        account = service.saveAccount( account, rates );
        if( isNew ) { 
            return INPUT;
        }
        return SUCCESS;
    }

    public List<AccountCurrencyRate> getRates() {
        return rates;
    }

    public void setRates( List<AccountCurrencyRate> rates ) {
        this.rates = rates;
    }

}
