package com.ksa.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 业务数据模型抽象基类。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 1484025868680981064L;
    
    /** 模型标识 */
    protected String id;

    /**
     * 获取模型标识。
     * @return 模型标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置模型标识。
     * @param id 模型标识
     */
    public void setId( String id ) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString( this );
    }
}
