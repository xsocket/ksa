package com.ksa.web.struts2.action.finance.profit;

import com.ksa.web.struts2.action.logistics.bookingnote.BookingNoteQueryAction;


public class ProfitQueryAction extends BookingNoteQueryAction {

    private static final long serialVersionUID = -7568580059497504302L;

    @Override
    protected String getQueryCountStatement() {
        return "count-finance-profit-query";
    }
    
    @Override
    protected String getQueryDataStatement() {
        return "grid-finance-profit-query";
    }

}
