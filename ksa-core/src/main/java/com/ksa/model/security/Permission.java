package com.ksa.model.security;

import java.io.Serializable;

import com.ksa.model.BaseModel;

/**
 * KSA 系统的权限信息。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class Permission extends BaseModel implements Serializable {

    private static final long serialVersionUID = 2108049421789476504L;
    
    /** 权限名称 */
    protected String name;
    /** 权限说明 */
    protected String description;
    
    public String getName() {
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription( String description ) {
        this.description = description;
    }
    
}
