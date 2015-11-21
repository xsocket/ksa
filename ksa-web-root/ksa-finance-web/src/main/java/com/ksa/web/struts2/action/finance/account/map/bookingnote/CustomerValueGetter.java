package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CustomerValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getCustomer() == null ) {
            return "";
        }
        return obj.getCustomer().getName();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "客户";
    }

}
