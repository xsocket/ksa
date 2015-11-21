package com.ksa.model.bd;

import java.util.Date;

import com.ksa.model.BaseModel;

/**
 * 货币汇率数据模型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class CurrencyRate extends BaseModel {

    private static final long serialVersionUID = 8280004532527090297L;
    
    /** 汇率值 */
    protected float rate;
    /** 汇率所属货币 */
    protected Currency currency = new Currency();
    /** 汇率所属日期 */
    protected Date month;
    /** 汇率所属客户 */
    protected Partner partner = new Partner();
    
    public Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency( Currency currency ) {
        this.currency = currency;
    }
    
    public Date getMonth() {
        return month;
    }
    
    public void setMonth( Date month ) {
        this.month = month;
    }
    
    public Partner getPartner() {
        return partner;
    }
    
    public void setPartner( Partner partner ) {
        this.partner = partner;
    }

    public float getRate() {
        return rate;
    }

    public void setRate( float rate ) {
        this.rate = rate;
    }
    
}
