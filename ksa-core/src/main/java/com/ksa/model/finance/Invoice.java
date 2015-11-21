package com.ksa.model.finance;

import java.util.Date;

import com.ksa.model.bd.Currency;
import com.ksa.model.bd.Partner;
import com.ksa.model.security.User;

/**
 * 发票/付款单据模型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class Invoice extends FinanceModel {

    private static final long serialVersionUID = -1154012608385382024L;
    
    /** 发票代码 */
    protected String code;
    /** 发票号码 */
    protected String number;
    /** 税号 */
    protected String taxNumber;
    
    /** 票据结算单位 */
    protected Partner target = new Partner();
    
    /** 票据类型 */
    protected String type;
    /** 结算货币 */    
    protected Currency currency = new Currency();
    /** 费用金额 */
    protected float amount = 0.0f;
    
    /** 创建日期 */
    protected Date createdDate = new Date();
    /** 创建人 */
    protected User creator = new User();
    
    /** 备注 */
    protected String note;
    /** 所属对账单 */
    protected Account account = new Account();
    
    public String getCode() {
        return code;
    }
    
    public void setCode( String code ) {
        this.code = code;
    }
    
    public String getNumber() {
        return number;
    }
    
    public void setNumber( String number ) {
        this.number = number;
    }
    
    public String getTaxNumber() {
        return taxNumber;
    }
    
    public void setTaxNumber( String taxNumber ) {
        this.taxNumber = taxNumber;
    }    
    
    public String getType() {
        return type;
    }
    
    public void setType( String type ) {
        this.type = type;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency( Currency currency ) {
        this.currency = currency;
    }
    
    public float getAmount() {
        return amount;
    }
    
    public void setAmount( float amount ) {
        this.amount = amount;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate( Date createdDate ) {
        this.createdDate = createdDate;
    }
    
    public User getCreator() {
        return creator;
    }
    
    public void setCreator( User creator ) {
        this.creator = creator;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote( String note ) {
        this.note = note;
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount( Account account ) {
        this.account = account;
    }

    public Partner getTarget() {
        return target;
    }
    
    public void setTarget( Partner target ) {
        this.target = target;
    }
    
}
