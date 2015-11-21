package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class DestinationPortValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        return obj.getDestinationPort();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        /*String type = obj.getType();
        if( BookingNote.TYPE_SEA_EXPORT.equalsIgnoreCase( type ) || BookingNote.TYPE_SEA_IMPORT.equalsIgnoreCase( type ) ) {
            return "目的港";
        } else {
            return "起飞港";
        }*/
        return "目的港";
    }

}
