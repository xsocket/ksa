package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CargoContainerValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        return obj.getCargoContainer();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "箱类箱量";
    }

}
