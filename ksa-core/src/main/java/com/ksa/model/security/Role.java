package com.ksa.model.security;

import java.io.Serializable;

import com.ksa.model.BaseModel;

/**
 * KSA 系统的用户角色。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class Role extends BaseModel implements Serializable {

    private static final long serialVersionUID = -6531662775846984570L;
    
    /** 角色名称 */
    protected String name;
    /** 角色说明 */
    protected String description;
    /** 角色权限 */
    protected Permission[] permissions;
    
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
    
    public Permission[] getPermissions() {
        return permissions;
    }
    
    public void setPermissions( Permission[] permissions ) {
        this.permissions = permissions;
    }
    
    
}
