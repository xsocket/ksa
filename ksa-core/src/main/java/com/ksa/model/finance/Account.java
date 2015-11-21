package com.ksa.model.finance;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.ksa.model.bd.Partner;
import com.ksa.model.security.User;

/**
 * 结账单数据模型。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class Account extends FinanceModel {

    private static final long serialVersionUID = 889478497102794302L;
    
    /** 结算单/对账单标识 */
    protected String code;

    /** 结算对象 */
    protected Partner target;

    /** 结账单所含全部费用 */
    protected List<Charge> charges;

    /** 结账单所对应的所有销账发票/单据列表 */
    protected List<Invoice> invoices;

    /** 创建人 */
    protected User creator = new User();

    /** 创建日期 */
    protected Date createdDate = new Date();

    /** 付款截止日期 */
    protected Date deadline;

    /** 结清日期 */
    protected Date paymentDate;

    /** 结账单当前状态 */
    protected int state;

    /** 备注 */
    protected String note;

    public Partner getTarget() {
        if( target == null ) {
            target = new Partner();
        }
        return target;
    }

    public void setTarget( Partner target ) {
        this.target = target;
    }

    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges( List<Charge> charges ) {
        this.charges = charges;
        if( this.charges != null && this.charges.size() > 0 ) {
            Collections.sort( this.charges );
        }
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices( List<Invoice> invoices ) {
        this.invoices = invoices;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator( User creator ) {
        this.creator = creator;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate( Date createdDate ) {
        this.createdDate = createdDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline( Date deadline ) {
        this.deadline = deadline;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate( Date paymentDate ) {
        this.paymentDate = paymentDate;
    }

    public int getState() {
        return state;
    }

    public void setState( int state ) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

}
