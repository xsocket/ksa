package com.ksa.web.struts2.action.statistics.profit.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ksa.model.finance.BookingNoteProfit;


public class FusionChartModelGroupByDate extends FusionChartModel {
    
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected String getCategoryValue( BookingNoteProfit bn ) {
        return format.format( bn.getCreatedDate() );
    }

}
