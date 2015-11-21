package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class RouteValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        return obj.getRoute();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "航线";
    }

}
