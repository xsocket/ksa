package com.ksa.model.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class RecordBillProfit implements Serializable {

    private static final long serialVersionUID = 1736560648911429449L;
    
    protected Collection<RecordBillChargeGather> gathers;

    protected Float incomeRMB = 0f;
    protected Float expenseRMB = 0f;

    public Float getIncomeRMB() {
        return incomeRMB;
    }
    
    public void setIncomeRMB( Float incomeRMB ) {
        this.incomeRMB = incomeRMB;
    }
    
    public int getGatherCount() {
        if( gathers == null ) {
            return 0;
        } else {
            return gathers.size();
        }
    }
    
    public Float getIncome() {
        Float income = incomeRMB;
        if( gathers != null && gathers.size() > 0 ) {
            for( RecordBillChargeGather gather : gathers ) {
                income += ( gather.getIncome() * gather.getRate() );
            }
        }
        return income;
    }
    
    public Float getExpense() {
        Float expense = expenseRMB;
        if( gathers != null && gathers.size() > 0 ) {
            for( RecordBillChargeGather gather : gathers ) {
                expense += ( gather.getExpense() * gather.getRate() );
            }
        }
        return expense;
    }
    
    public Float getExpenseRMB() {
        return expenseRMB;
    }
    
    public void setExpenseRMB( Float expenseRMB ) {
        this.expenseRMB = expenseRMB;
    }
    
    public Collection<RecordBillChargeGather> getGathers() {
        if( gathers == null ) {
            return Collections.emptyList();
        }
        return gathers;
    }
    
    public void setGathers( Collection<RecordBillChargeGather> gathers ) {        
        List<RecordBillChargeGather> list = new ArrayList<RecordBillChargeGather>( gathers );
        Collections.sort( list );
        this.gathers = list;
    }
    
    
}
