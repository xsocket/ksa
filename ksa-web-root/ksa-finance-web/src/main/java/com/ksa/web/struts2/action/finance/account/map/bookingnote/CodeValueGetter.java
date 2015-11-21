package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CodeValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        return obj.getCode();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "业务编号";
    }

}
