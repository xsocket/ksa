package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class CreatorValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj.getCreator() == null ) {
            return "";
        }
        return obj.getCreator().getName();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "操作员";
    }

}
