package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class ShipperValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getShipper() == null ) {
            return "";
        }
        return obj.getShipper().getName();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "发货人";
    }

}
