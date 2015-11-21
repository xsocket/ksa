package com.ksa.web.struts2.action.bd.currency;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.bd.CurrencyRateService;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class RateSaveAction extends RateAction implements JsonAction {

    private static final long serialVersionUID = 605636145713159434L;
    
    private JsonResult result;
    
    @Override
    protected String doExecute() throws Exception {
        CurrencyRateService service = ServiceContextUtils.getService( CurrencyRateService.class );
        service.saveCurrencyRate( rate );
        String message = String.format( "成功设置货币汇率。");
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message, rate );
        return SUCCESS;
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next(), rate );
        }
        return this.result;
    }
}
