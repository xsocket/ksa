package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CustomsDateValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getCustomsDate() == null ) {
            return "";
        }
        return DATE_FORMAT.format( obj.getCustomsDate() );
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "报关日期";
    }

}
