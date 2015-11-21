package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class InvoiceValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        return obj.getInvoiceNumber();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "发票号";
    }

}
