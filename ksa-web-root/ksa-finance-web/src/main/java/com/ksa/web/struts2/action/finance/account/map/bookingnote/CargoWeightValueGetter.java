package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CargoWeightValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getWeight() == null ) {
            return "";
        }
        if( obj.getWeight() > 0 ) {
            return obj.getWeight().toString() + " kg";
        }
        return "";
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "毛重";
    }

}
