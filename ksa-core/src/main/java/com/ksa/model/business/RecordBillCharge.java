package com.ksa.model.business;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.ksa.model.bd.Currency;

/**
 * 面单中使用的费用。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class RecordBillCharge implements Serializable, Comparable<RecordBillCharge> {

    private static final long serialVersionUID = -9073037770695093352L;

    /** 费用类型 */
    protected String type;

    /** 结算对象 */
    protected String customer;

    /** 结算货币 */
    protected Currency incomeCurrency;

    /** 单价 */
    protected Float income;

    /** 数量 */
    protected Float incomeAmount;

    /** 费用金额 = 单价 * 数量 */
    protected String incomeTotal;

    /** 结算货币 */
    protected Currency expenseCurrency;

    /** 单价 */
    protected Float expense;

    /** 数量 */
    protected Float expenseAmount;

    /** 费用金额 = 单价 * 数量 */
    protected String expenseTotal;

    /** 结算对象 */
    protected String agent;

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer( String customer ) {
        this.customer = customer;
    }

    public Currency getIncomeCurrency() {
        return incomeCurrency;
    }

    public void setIncomeCurrency( Currency incomeCurrency ) {
        this.incomeCurrency = incomeCurrency;
    }

    public Float getIncome() {
        return income;
    }

    public void setIncome( Float income ) {
        this.income = income;
    }

    public Float getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount( Float incomeAmount ) {
        this.incomeAmount = incomeAmount;
    }

    public String getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal( String incomeTotal ) {
        this.incomeTotal = incomeTotal;
    }

    public Currency getExpenseCurrency() {
        return expenseCurrency;
    }

    public void setExpenseCurrency( Currency expenseCurrency ) {
        this.expenseCurrency = expenseCurrency;
    }

    public Float getExpense() {
        return expense;
    }

    public void setExpense( Float expense ) {
        this.expense = expense;
    }

    public Float getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount( Float expenseAmount ) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal( String expenseTotal ) {
        this.expenseTotal = expenseTotal;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent( String agent ) {
        this.agent = agent;
    }

    @Override
    public int compareTo( RecordBillCharge o ) {
        // 按客户排列
        if( o == null ) {
            return -1;
        }
        int result = compareString( this.customer, o.customer );

        if( result == 0 ) {
            // 客户相同就按币种继续排列
            result = compareCurrency( this.incomeCurrency, o.incomeCurrency );
        }
        
        if( result == 0 ) {
            result = compareString( this.agent, o.agent );
        }
        
        if( result == 0 ) {
            result = compareCurrency( this.expenseCurrency, o.expenseCurrency );
        }

        return result;
    }

    private int compareString( String c1, String c2 ) {
        return ( StringUtils.isEmpty( c2 ) ? "" : c2 ).compareTo( StringUtils.isEmpty( c1 ) ? "" : c1 );
    }

    private int compareCurrency( Currency c1, Currency c2 ) {
        if( c1 == null ) {
            return 1;
        }
        if( c2 == null ) {
            return -1;
        }
        // 美元排前
        if( Currency.USD.getId().equals( c1.getId() ) ) {
            return -1;
        }
        if( Currency.USD.getId().equals( c2.getId() ) ) {
            return 1;
        }

        // 人民币次之
        if( Currency.RMB.getId().equals( c1.getId() ) ) {
            return -1;
        }
        if( Currency.RMB.getId().equals( c2.getId() ) ) {
            return 1;
        }

        return compareString( c1.getName(), c2.getName() );
    }

}
