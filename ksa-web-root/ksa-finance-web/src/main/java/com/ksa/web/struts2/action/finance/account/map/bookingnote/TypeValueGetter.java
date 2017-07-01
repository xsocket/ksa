package com.ksa.web.struts2.action.finance.account.map.bookingnote;

import com.ksa.model.logistics.BookingNote;


public class TypeValueGetter extends AbstractBookingNoteValueGetter {

    @Override
    public String doGetValue( BookingNote obj ) {
        if( obj != null ) {
            String type = obj.getType();
            if( BookingNote.TYPE_SEA_EXPORT.equalsIgnoreCase( type ) ) {
                return "海运出口";
            } else if( BookingNote.TYPE_SEA_IMPORT.equalsIgnoreCase( type ) ) {
                return "海运进口";
            } else if( BookingNote.TYPE_AIR_EXPORT.equalsIgnoreCase( type ) ) {
                return "空运出口";
            } else if( BookingNote.TYPE_AIR_IMPORT.equalsIgnoreCase( type ) ) {
                return "空运进口";
            } else if( BookingNote.TYPE_NATIVE.equalsIgnoreCase( type ) ) {
                return "国内运输";
            } else if( BookingNote.TYPE_KB.equalsIgnoreCase( type ) ) {
                return "捆包业务";
            } else if( BookingNote.TYPE_BC.equalsIgnoreCase( type ) ) {
                return "搬场业务";
            } else if( BookingNote.TYPE_CC.equalsIgnoreCase( type ) ) {
                return "仓储业务";
            } else if( BookingNote.TYPE_RH.equalsIgnoreCase( type ) ) {
                return "内航线";
            } else if( BookingNote.TYPE_TL.equalsIgnoreCase( type ) ) {
                return "公铁联运";
            } else if( BookingNote.TYPE_ZJ.equalsIgnoreCase( type ) ) {
              return "证件代办";
            } 
        }
        return "其他";
    }

    @Override
    public String getLabel( BookingNote obj ) {
        return "业务类型";
    }

}
