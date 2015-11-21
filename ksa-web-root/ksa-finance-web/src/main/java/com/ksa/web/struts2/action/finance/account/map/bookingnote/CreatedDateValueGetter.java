package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CreatedDateValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getCreatedDate() == null ) {
            return "";
        }
        return DATE_FORMAT.format( obj.getCreatedDate() );
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "接单日期";
    }

}
