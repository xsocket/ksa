package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import java.text.DateFormat;

import com.ksa.model.logistics.BookingNote;
import com.ksa.web.struts2.action.finance.account.map.ValueGetter;

public abstract class AbstractBookingNoteValueGetter implements ValueGetter<BookingNote> {

    protected DateFormat DATE_FORMAT = DateFormat.getDateInstance();

    @Override
    public String getValue( BookingNote obj ) {
        if( obj == null ) {
            return "";
        } else {
            try {
                return doGetValue( obj );
            } catch( Exception e ) {
                return "";
            }
        }
    }

    protected abstract String doGetValue( BookingNote obj );
}
