package com.ksa.model.security;

import java.io.Serializable;
import java.util.List;

import com.ksa.model.BaseModel;

/**
 * KSA 系统的用户。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class User extends BaseModel implements Serializable {

    private static final long serialVersionUID = 715060664977488527L;

    /** 用户是否为锁定状态 */
    protected boolean locked = false;
    /** 用户姓名 */
    protected String name;
    /** 用户登录密码 */
    protected transient String password;
    /** 用户邮箱 */
    protected String email;
    /** 用户电话 */
    protected String telephone;
    /** 用户所属角色 */
    protected List<Role> roles;

    public boolean isLocked() {
        return locked;
    }

    public boolean getLocked() {
        return locked;
    }
    
    public void setLocked( boolean locked ) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword( String password ) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail( String email ) {
        this.email = email;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone( String telephone ) {
        this.telephone = telephone;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles( List<Role> roles ) {
        this.roles = roles;
    }

}
