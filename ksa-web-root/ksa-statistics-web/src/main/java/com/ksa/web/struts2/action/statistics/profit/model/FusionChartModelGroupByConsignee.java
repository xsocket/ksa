package com.ksa.web.struts2.action.statistics.profit.model;

import com.ksa.model.finance.BookingNoteProfit;


public class FusionChartModelGroupByConsignee extends FusionChartModel {

    @Override
    protected String getCategoryValue( BookingNoteProfit bn ) {
        return bn.getConsignee().getName();
    }

}
