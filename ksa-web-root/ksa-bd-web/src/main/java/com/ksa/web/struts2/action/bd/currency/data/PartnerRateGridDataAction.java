package com.ksa.web.struts2.action.bd.currency.data;

import java.util.List;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.bd.Partner;
import com.ksa.service.bd.CurrencyRateService;
import com.ksa.service.bd.PartnerService;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.data.GridDataActionSupport;

public class PartnerRateGridDataAction extends GridDataActionSupport {

    private static final long serialVersionUID = 426472765049926642L;

    protected String partnerId;
    
    protected Object[] gridDataArray = new Object[0]; 
    
    @Override
    protected String doExecute() throws Exception {
        if( StringUtils.hasText( partnerId ) ) {
            PartnerService s = ServiceContextUtils.getService( PartnerService.class );
            try {
                // FIXME : 这个验证似乎应该放入 service 层！！ 确认传入的用户标识合法
                Partner partner = s.loadPartnerById( partnerId );
                
                // 获取数据
                CurrencyRateService service = ServiceContextUtils.getService( CurrencyRateService.class );
                List<CurrencyRate> rates = service.loadPartnerCurrencyRates( partner.getId() );
                gridDataArray = rates.toArray();
                return SUCCESS;
            } catch( RuntimeException e ) { }
        }
        
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
    
    public void setPartnerId( String partnerId ) {
        this.partnerId = partnerId;
    }

    
}
