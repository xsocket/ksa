package com.ksa.web.struts2.action.statistics.profit;

import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.finance.charge.ChargeAction;


public class ProfitAction extends ChargeAction {

    private static final long serialVersionUID = 4479268324836347314L;

    @Override
    public String execute() throws Exception {
        if( !SecurityUtils.isPermitted( "statistics:profit" ) ) {
            return NO_PERMISSION;
        }
        return super.execute();
    }

}
