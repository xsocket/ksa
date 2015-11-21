package com.ksa.model.business;

import com.ksa.model.bd.Currency;


public class RecordBillChargeGather implements Comparable<RecordBillChargeGather> {

    protected Float income = 0f;
    protected Float expense = 0f;
    protected Float rate = 0f;
    protected Currency currency;
    
    public RecordBillChargeGather() { }
    
    public RecordBillChargeGather addIncome( float value ) {
        this.income += value;
        return this;
    }
    
    public RecordBillChargeGather addExpense( float value ) {
        this.expense += value;
        return this;
    }
    
    public Float getIncome() {
        return income;
    }
    
    public void setIncome( Float income ) {
        this.income = income;
    }
    
    public Float getExpense() {
        return expense;
    }
    
    public void setExpense( Float expense ) {
        this.expense = expense;
    }
    
    public Float getRate() {
        return rate;
    }
    
    public void setRate( Float rate ) {
        this.rate = rate;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency( Currency currency ) {
        this.currency = currency;
    }

    @Override
    public int compareTo( RecordBillChargeGather o ) {
        return this.currency.getRank() - o.currency.getRank();
    }
    
    
}
