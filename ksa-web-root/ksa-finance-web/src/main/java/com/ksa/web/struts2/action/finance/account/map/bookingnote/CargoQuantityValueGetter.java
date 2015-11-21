package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CargoQuantityValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getQuantity() == null ) {
            return "";
        }
        if( obj.getQuantity() > 0 ) {
            StringBuilder sb = new StringBuilder();
            sb.append( obj.getQuantity() );
            if( obj.getUnit() != null ) {
                sb.append( " " ).append( obj.getUnit() );
            }
            return sb.toString();
        }
        return "";
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "数量";
    }

}
