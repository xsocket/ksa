package com.ksa.web.struts2.action.security.user;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.security.SecurityService;
import com.ksa.util.StringUtils;

public class UserUpdateAction extends ClearUserCacheAction {

    private static final long serialVersionUID = 3554278544934444979L;
    
    private String passwordAgain;
    private String passwordOld;
    
    @Override
    public String doExecute() throws Exception {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        if( needUpdatePassword() ) {
            this.user = service.modifyUser( user, passwordOld, user.getPassword() );
        } else {
            this.user = service.modifyUser( user );
        }
        this.addActionMessage( "成功更新用户。" );
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();
        // 用户标识
        if( !StringUtils.hasText( user.getId() ) ) {
            this.addActionError( "请输入用户标识信息。" );
        } else {
            if( user.getId().length() > 36 ) {
                this.addActionError( "用户标识过长，请控制在 <span class='badge badge-success'>36</span> 个字符之内。" );
            }
        }
        // 用户姓名
        if( !StringUtils.hasText( user.getName() ) ) {
            this.addActionError( "请输入用户姓名。" );
        } else {
            if( user.getName().length() > 256 ) {
                this.addActionError( "用户姓名过长，请控制在 <span class='badge badge-success'>256</span> 个字符之内。" );
            }
        }
        // 电子邮箱
        if( StringUtils.hasText( user.getEmail() ) && user.getEmail().length() > 256 ) {
            this.addActionError( "电子邮箱过长，请控制在 <span class='badge badge-success'>256</span> 个字符之内。" );
        }
        // 联系电话
        if( StringUtils.hasText( user.getTelephone() ) && user.getTelephone().length() > 256 ) {
            this.addActionError( "联系电话过长，请控制在 <span class='badge badge-success'>256</span> 个字符之内。" );
        }
        // 需要更新用户密码
        if( needUpdatePassword() ) {
            if( ! user.getPassword().equals( passwordAgain ) ) {
                this.addActionError( "两次密码输入不一致，请重新确认。" );
                user.setPassword( "" );
            }
            
            if( ! StringUtils.hasText( passwordOld ) ) {
                this.addActionError( "请输入原登录密码。" );
                user.setPassword( "" );
            }
        } 
    }
    
    private boolean needUpdatePassword() {
        return StringUtils.hasText( user.getPassword() );
    }
    
    public void setPasswordAgain( String passwordAgain ) {
        this.passwordAgain = passwordAgain;
    }
    public void setPasswordOld( String passwordOld ) {
        this.passwordOld = passwordOld;
    }
}
