package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CustomsBrokerValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getCustomsBroker() == null ) {
            return "";
        }
        return obj.getCustomsBroker().getName();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "报关行";
    }

}
