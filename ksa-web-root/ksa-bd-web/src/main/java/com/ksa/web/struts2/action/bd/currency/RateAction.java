package com.ksa.web.struts2.action.bd.currency;

import com.ksa.model.bd.CurrencyRate;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RateAction extends DefaultActionSupport implements ModelDriven<CurrencyRate> {

    private static final long serialVersionUID = -6133119525401391420L;
    
    protected CurrencyRate rate = new CurrencyRate();
    
    @Override
    public String execute() throws Exception {
        // 权限校验
        if( ! SecurityUtils.isPermitted( "bd:currency" ) ) {
            return NO_PERMISSION;
        }
        return super.execute();
    }

    @Override
    public CurrencyRate getModel() {
        return getRate();
    }
    
    public CurrencyRate getRate() {
        return rate;
    }
}
