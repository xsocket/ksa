package com.ksa.system.initialize.model;

/**
 * 对应SQLServer2005中的 UserList 表数据
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class YongHu {

    protected String id;
    protected String username;
    protected String password;
    protected String telephone;
    protected String email;
    protected String role;
    
    public String getId() {
        return id;
    }
    
    public void setId( String id ) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername( String username ) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword( String password ) {
        this.password = password;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone( String telephone ) {
        this.telephone = telephone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail( String email ) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole( String role ) {
        this.role = role;
    }    
    
}
