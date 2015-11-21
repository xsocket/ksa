package com.ksa.model.logistics;

import com.ksa.model.BaseModel;

/**
 * 提单中货物信息模型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class BookingNoteCargo extends BaseModel {

    private static final long serialVersionUID = 1342906524991877423L;

    /** 名称 */
    protected String name;
    
    /** 箱类 */
    protected String category;

    /** 箱型 */
    protected String type;
    
    /** 数量 */
    protected int amount;
    
    /** 所属托单 */
    protected BookingNote bookingNote = new BookingNote();
    
    public String getName() {
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory( String category ) {
        this.category = category;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType( String type ) {
        this.type = type;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount( int amount ) {
        this.amount = amount;
    }
    
    public BookingNote getBookingNote() {
        return bookingNote;
    }
    
    public void setBookingNote( BookingNote bookingNote ) {
        this.bookingNote = bookingNote;
    }
}
