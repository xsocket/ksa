package com.ksa.web.struts2.action.bd.currency;

import org.springframework.util.StringUtils;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.bd.Partner;
import com.ksa.service.bd.PartnerService;

public class PartnerRateAction extends RateAction {

    private static final long serialVersionUID = -8059608682104160166L;
    
    @Override
    protected String doExecute() throws Exception {
        if( StringUtils.hasText( rate.getPartner().getId() ) ) {
            PartnerService s = ServiceContextUtils.getService( PartnerService.class );
            try {
                rate.setPartner( s.loadPartnerById( rate.getPartner().getId() ) );
                return SUCCESS;
            } catch( RuntimeException e ) { }
        }
        // 设置partner为空
        rate.setPartner( new Partner() );
        return SUCCESS;
    }
}
