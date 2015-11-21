package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class MawbValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( BookingNote.TYPE_SEA_EXPORT.equalsIgnoreCase( obj.getType() ) ) {
            // 海运出口提单仅统计分单号
            if( obj.getHawb() != null ) {
                return obj.getHawb();
            }
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        if( obj.getMawb() != null ) {
            sb.append( obj.getMawb() ).append( " " );
        }
        if( obj.getHawb() != null ) {
            sb.append( obj.getHawb() );
        }
        return sb.toString();
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "提单号";
    }

}
