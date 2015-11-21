package com.ksa.web.struts2.action.statistics.profit.model;

import java.text.DateFormat;

import com.ksa.model.finance.BookingNoteProfit;


public class FusionChartModelGroupByDate extends FusionChartModel {
    
    private static DateFormat format = DateFormat.getDateInstance();

    @Override
    protected String getCategoryValue( BookingNoteProfit bn ) {
        return format.format( bn.getCreatedDate() );
    }

}
