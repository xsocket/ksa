package com.ksa.model.logistics;

import org.springframework.util.StringUtils;

import com.ksa.model.BaseModel;
import com.ksa.model.bd.BasicData;

/**
 * 物流管理抽象基类模型，基于托单数据模型生成的各类业务数据模型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class BaseLogisticsModel extends BaseModel {

    private static final long serialVersionUID = -2573872052916999869L;
    
    /** 模型所属托单。 */
    protected BookingNote bookingNote = new BookingNote();

    /**
     * 根据给定的托单数据，生成相应的业务初始化数据。
     * @param bookingnote 托单模型数据
     */
    public void initialModel( BookingNote bookingNote ) {
        
    }

    public BookingNote getBookingNote() {
        return bookingNote;
    }

    public void setBookingNote( BookingNote bookingNote ) {
        this.bookingNote = bookingNote;
    }
    
    public String getAlias( BasicData data ) {
        if( data == null ) {
            return "";
        } else {
            String alias = data.getAlias();
            if( StringUtils.hasText( alias ) ) {
                return alias;
            }
            return data.getCode();
        }
    }
}
