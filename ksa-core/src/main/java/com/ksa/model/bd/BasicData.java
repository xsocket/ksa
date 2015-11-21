package com.ksa.model.bd;

import com.ksa.model.BaseModel;

/**
 * 基础数据模型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class BasicData extends BaseModel {

    private static final long serialVersionUID = -657250627734478396L;
    
    /** 基础数据编码 */
    protected String code;
    /** 基础数据名称 */
    protected String name;
    /** 基础数据别名 */
    protected String alias;
    /** 基础数据备注 */
    protected String note;
    /** 基础数据类型 */
    protected BasicDataType type;
    
    /** 排序 */
    protected int rank;
    
    /** 额外属性 */
    protected String extra;
    
    public BasicData() {
        
    }
    
    public BasicData( String id ) {
        this.id = id;
    }

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
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias( String alias ) {
        this.alias = alias;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote( String note ) {
        this.note = note;
    }
    
    public BasicDataType getType() {
        if( type == null ) {
            type = new BasicDataType();
        }
        return type;
    }

    public void setType( BasicDataType type ) {
        this.type = type;
    }
    
    public String getExtra() {
        return extra;
    }
    
    public void setExtra( String extra ) {
        this.extra = extra;
    }

    public int getRank() {
        return rank;
    }

    public void setRank( int rank ) {
        this.rank = rank;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
