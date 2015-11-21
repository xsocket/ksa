package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class ConsigneeValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getConsignee() == null ) {
            return "";
        }
        return obj.getConsignee().getName();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "收货人";
    }

}
