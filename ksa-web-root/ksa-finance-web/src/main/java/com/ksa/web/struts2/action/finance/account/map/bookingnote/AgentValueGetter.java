package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class AgentValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getAgent() == null ) {
            return "";
        }
        return obj.getAgent().getName();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "代理商";
    }

}
