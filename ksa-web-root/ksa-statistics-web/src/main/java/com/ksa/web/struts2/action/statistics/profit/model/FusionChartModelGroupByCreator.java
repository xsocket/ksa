package com.ksa.web.struts2.action.statistics.profit.model;

import com.ksa.model.finance.BookingNoteProfit;


public class FusionChartModelGroupByCreator extends FusionChartModel {

    @Override
    protected String getCategoryValue( BookingNoteProfit bn ) {
        return bn.getCreator().getName();
    }

}
