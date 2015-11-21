package com.ksa.web.struts2.action.finance.charge;

import com.ksa.web.struts2.action.logistics.bookingnote.BookingNoteQueryAction;

public class ForeignChargeQueryAction extends BookingNoteQueryAction {

    private static final long serialVersionUID = -5728962722156241776L;

    @Override
    protected String getQueryCountStatement() {
        return "count-finance-profit-query";
    }
    
    @Override
    protected String getQueryDataStatement() {
        return "grid-finance-profit-query-foreign";
    }

}
