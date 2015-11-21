package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class DeparturePortValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        return obj.getDeparturePort();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        String type = obj.getType();
        if( BookingNote.TYPE_SEA_EXPORT.equalsIgnoreCase( type ) || BookingNote.TYPE_SEA_IMPORT.equalsIgnoreCase( type ) ) {
            return "起运港";
        } else {
            return "起飞港";
        }
    }

}
