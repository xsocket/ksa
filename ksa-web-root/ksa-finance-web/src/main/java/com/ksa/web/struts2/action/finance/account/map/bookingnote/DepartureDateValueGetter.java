package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class DepartureDateValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getDepartureDate() == null ) {
            return "";
        }
        return DATE_FORMAT.format( obj.getDepartureDate() );
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "开航日";
    }

}
