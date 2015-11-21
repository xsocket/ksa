package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class DestinationDateValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getDestinationDate() == null ) {
            return "";
        }
        return DATE_FORMAT.format( obj.getDestinationDate() );
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "到港日";
    }

}
