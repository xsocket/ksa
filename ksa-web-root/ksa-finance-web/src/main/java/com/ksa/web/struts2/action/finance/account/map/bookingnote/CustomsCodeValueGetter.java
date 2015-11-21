package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CustomsCodeValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        return obj.getCustomsCode();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "报关单号";
    }

}
