package com.ksa.model.business;

import java.io.Serializable;

import com.ksa.model.bd.Currency;
import com.ksa.model.finance.Charge;
import com.ksa.model.finance.FinanceModel;

public class DebitNoteCharge extends FinanceModel implements Serializable {

    private static final long serialVersionUID = 2772543734062428868L;
    
    public DebitNoteCharge() {}
    
    public DebitNoteCharge( Charge charge ) {
        this.item = charge.getType();
        this.unit = charge.getPrice() != null ? Float.toString( charge.getPrice() ) : "";
        this.note = charge.getNote();
        this.amount = charge.getAmount();
        this.currency = charge.getCurrency();
        this.direction = charge.getDirection();
        this.nature = charge.getNature();
    }
    
    protected String item;
    protected String unit;
    protected String note;
    protected Float amount;
    protected Currency currency;
    
    public String getItem() {
        return item;
    }
    
    public void setItem( String item ) {
        this.item = item;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit( String unit ) {
        this.unit = unit;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote( String note ) {
        this.note = note;
    }
    
    public Float getAmount() {
        return amount;
    }
    
    public void setAmount( Float amount ) {
        this.amount = amount;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency( Currency currency ) {
        this.currency = currency;
    }
    
    
}
