package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class RouteNameValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        StringBuilder sb = new StringBuilder();
        if( obj.getRouteName() != null ) {
            sb.append( obj.getRouteName() ).append( " " );
        }
        if( obj.getRouteCode() != null ) {
            sb.append( obj.getRouteCode() );
        }
        return sb.toString();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        String type = obj.getType();
        if( BookingNote.TYPE_SEA_EXPORT.equalsIgnoreCase( type ) || BookingNote.TYPE_SEA_IMPORT.equalsIgnoreCase( type ) ) {
            return "船名/航次";
        } else {
            return "航班";
        }
    }

}
