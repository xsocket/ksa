package com.ksa.web.struts2.action.statistics.profit.model;

import com.ksa.model.finance.BookingNoteProfit;
import com.ksa.model.logistics.BookingNote;


public class FusionChartModelGroupByType extends FusionChartModel {

    @Override
    protected String getCategoryValue( BookingNoteProfit bn ) {
        String type = bn.getType();
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
        } else {
            return "其他类型";
        }
    }

}
