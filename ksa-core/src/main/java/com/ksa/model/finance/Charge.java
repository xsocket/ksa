package com.ksa.model.finance;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ksa.model.ModelUtils;
import com.ksa.model.bd.Currency;
import com.ksa.model.bd.Partner;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.security.User;

/**
 * 通用收支费用模型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class Charge extends FinanceModel implements Comparable<Charge> {

    private static final long serialVersionUID = 5354349025984427982L;
    
    /** 结算对象 */
    protected Partner target = new Partner();
    /** 费用类型 */
    protected String type;
    /** 结算货币 */    
    protected Currency currency = new Currency();
    /** 单价 */
    protected Float price;
    /** 数量 */
    protected Float quantity;
    /** 费用金额 = 单价 * 数量 */
    protected float amount = 0.0f;
    /** 创建日期 */
    protected Date createdDate = new Date();
    /** 备注 */
    protected String note;
    /** 创建人 */
    protected User creator = new User();
    /** 所属对账单 */
    protected Account account = new Account();
    /** 所属托单 */
    protected BookingNote bookingNote = new BookingNote();
    /** 费用录入的序号 */
    protected int rank = 0;
    
    /** 是否已经汇总入结算对账单 */
    public boolean isSettle() {
        // 所属对账单对象 Account 是持久化对象 则表明本条费用已经开出结算单。
        return ModelUtils.isPersistentObject( account );
    }
    
    public Partner getTarget() {
        return target;
    }
    
    public void setTarget( Partner target ) {
        this.target = target;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate( Date createdDate ) {
        this.createdDate = createdDate;
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
    
    public String getNote() {
        return note;
    }
    
    public void setNote( String note ) {
        this.note = note;
    }
    
    public User getCreator() {
        return creator;
    }
    
    public void setCreator( User creator ) {
        this.creator = creator;
    }
    
    public int getAccountState() {
        if( account == null ) {
            return -1;
        } else {
            if( StringUtils.isEmpty( account.getId() ) ) {
                return -1;
            }
            return account.getState();
        }
    } 
    
    public void setAccountState( Integer state ) {
        if( state != null ) {
            if( account == null ) {
                account = new Account();
            } 
            account.setState( state.intValue() );
        }
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount( Account account ) {
        this.account = account;
    }
    
    public BookingNote getBookingNote() {
        return bookingNote;
    }
    
    public void setBookingNote( BookingNote bookingNote ) {
        this.bookingNote = bookingNote;
    }
    
    public Float getPrice() {
        return price;
    }
    
    public void setPrice( Float price ) {
        this.price = price;
    }
    
    public Float getQuantity() {
        return quantity;
    }
    
    public void setQuantity( Float quantity ) {
        this.quantity = quantity;
    }
    
    /**
     * TODO 费用按托单展示的 treegrid 模型所需属性
     * 
     * @return
     */
    public String get_parentId() {
        if( bookingNote != null ) {
            return bookingNote.getId();
        } else {
            return "";
        }
    }
    /**
     * TODO 费用按托单展示的 treegrid 模型所需属性
     * 所属业务托单的业务代码
     * 
     * @return
     */
    public String getCode() {
        return type;
    }

    public int getRank() {
      return rank;
    }

    public void setRank(int rank) {
      this.rank = rank;
    }

    @Override
    public int compareTo( Charge c ) {
        int rtn = 0;
        if( c.bookingNote != null && this.bookingNote != null ) {
            rtn = c.bookingNote.getSerialNumber() - this.bookingNote.getSerialNumber();
        }
        if( rtn != 0 ) {
            return rtn;
        }
        
        String n1 = this.target.getName();
        if( n1 == null ) { n1 = ""; }
        String n2 = c.target.getName();
        if( n2 == null ) { n2 = ""; }
        
        rtn = n1.compareTo( n2 );
        if( rtn == 0 ) {
            rtn = this.currency.getRank() - c.currency.getRank();
        }
        return rtn;
    }
}
