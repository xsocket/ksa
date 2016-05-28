package com.ksa.model.bd;

import com.ksa.model.BaseModel;
import com.ksa.model.security.User;

/**
 * 合作伙伴数据模型，是 客户( Customer )、供应商( Supplier )的基础模型。
 * 
 * 2012-11-26修改，将 Cusotmer 和 Supplier 模型合并为 Partner 统一管理。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class Partner extends BaseModel {

    private static final long serialVersionUID = -2263012417836719243L;
    
    /** 编码 */
    protected String code;
    /** 名称 */
    protected String name;
    /** 别名 */
    protected String alias;
    /** 地址 */
    protected String address;
    /** 付款周期，单位：天 （payment periods 简写为 pp） */
    protected int pp = 30;
    /** 销售代表 */
    protected User saler;
    /** 合作伙伴类型 */
    protected PartnerType[] types;
    /** 备注 */
    protected String note;
    /** 排序 */
    protected int rank;
    /** 是否常用 */
    protected int important;
    /** 提单中常用的附加信息 */
    protected String[] extras;
    
    public String getCode() {
        return code;
    }
    
    public void setCode( String code ) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress( String address ) {
        this.address = address;
    }
    
    public int getPp() {
        return pp;
    }
    
    public void setPp( int pp ) {
        this.pp = pp;
    }
    
    public User getSaler() {
        if( saler == null ) {
            saler = new User();
        }
        return saler;
    }
    
    public void setSaler( User saler ) {
        this.saler = saler;
    }
        
    public String getNote() {
        return note;
    }
    
    public void setNote( String note ) {
        this.note = note;
    }    
    
    
    public String getAlias() {
        return alias;
    }

    
    public void setAlias( String alias ) {
        this.alias = alias;
    }

    public PartnerType[] getTypes() {
        return types;
    }

    public void setTypes( PartnerType[] types ) {
        this.types = types;
    }
    
    public void setTypeIds( String[] ids ) {
        if( ids != null && ids.length > 0 ) {
            this.types = new PartnerType[ ids.length ];
            for( int i = 0; i < ids.length; i++ ) {
                types[i] = new PartnerType();
                types[i].setId( ids[i] );
            }
        }
    }

    public String[] getExtras() {
        return extras;
    }
    
    public void setExtras( String[] extras ) {
        this.extras = extras;
    }

    public int getRank() {
        return rank;
    }
    
    public void setRank( int rank ) {
        this.rank = rank;
    }
    
    public int getImportant() {
        return important;
    }
    
    public void setImportant( int important ) {
        this.important = important;
    }
    
}
