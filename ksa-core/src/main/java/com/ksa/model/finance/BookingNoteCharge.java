package com.ksa.model.finance;

import java.util.List;

import com.ksa.model.logistics.BookingNote;

public class BookingNoteCharge extends BookingNote {

    private static final long serialVersionUID = -1899021508699930132L;
    
    /** 结账单所含全部费用 */
    protected List<Charge> charges;

    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges( List<Charge> charges ) {
        this.charges = charges;
    }
}
