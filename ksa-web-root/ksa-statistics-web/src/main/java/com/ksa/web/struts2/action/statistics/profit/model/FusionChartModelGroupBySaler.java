package com.ksa.web.struts2.action.statistics.profit.model;

import com.ksa.model.finance.BookingNoteProfit;


public class FusionChartModelGroupBySaler extends FusionChartModel {

    @Override
    protected String getCategoryValue( BookingNoteProfit bn ) {
        return bn.getSaler().getName();
    }

}
