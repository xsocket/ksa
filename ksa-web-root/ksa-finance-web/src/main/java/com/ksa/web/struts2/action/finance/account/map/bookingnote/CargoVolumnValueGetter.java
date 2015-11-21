package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CargoVolumnValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getVolumn() == null ) {
            return "";
        }
        if( obj.getVolumn() > 0 ) {
            return obj.getVolumn().toString() + " m3";
        }
        return "";
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "体积";
    }

}
