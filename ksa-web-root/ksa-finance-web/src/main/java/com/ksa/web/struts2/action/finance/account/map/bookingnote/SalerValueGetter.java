package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class SalerValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getSaler() == null ) {
            return "";
        }
        return obj.getSaler().getName();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "销售担当";
    }

}
