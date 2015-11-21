package com.ksa.web.struts2.action.statistics.cargo;

import com.ksa.web.struts2.action.logistics.bookingnote.BookingNoteQueryAction;


public class CargoQueryAction extends BookingNoteQueryAction {

    private static final long serialVersionUID = -4874765555406772070L;

    @Override
    protected String getQueryCountStatement() {
        return "count-statistics-cargo-query";
    }
    
    @Override
    protected String getQueryDataStatement() {
        return "grid-statistics-cargo-query";
    }

}
