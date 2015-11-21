package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CargoNameValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        return obj.getCargoName();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "品名";
    }

}
