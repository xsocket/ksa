package com.ksa.web.struts2.action.security.user;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.security.SecurityService;
import com.ksa.util.StringUtils;

public class UserInsertAction extends ClearUserCacheAction {

    private static final long serialVersionUID = -667167929666945107L;
    
    private String passwordAgain;
    private String[] roleIds;

    @Override
    public String doExecute() throws Exception {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        this.user = service.createUser( this.user, roleIds );        
        this.addActionMessage( "成功创建用户。" );
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();
        
        final int inputLength = 200;
        
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
            if( user.getName().length() > inputLength ) {
                this.addActionError( "用户姓名过长，请控制在 <span class='badge badge-success'>" + inputLength + "</span> 个字符之内。" );
            }
        }
        // 电子邮箱
        if( StringUtils.hasText( user.getEmail() ) && user.getEmail().length() > inputLength ) {
            this.addActionError( "电子邮箱过长，请控制在 <span class='badge badge-success'>" + inputLength + "</span> 个字符之内。" );
        }
        // 联系电话
        if( StringUtils.hasText( user.getTelephone() ) && user.getTelephone().length() > inputLength ) {
            this.addActionError( "联系电话过长，请控制在 <span class='badge badge-success'>" + inputLength + "</span> 个字符之内。" );
        }
        // 登录密码
        if( !StringUtils.hasText( user.getPassword() ) ) {
            this.addActionError( "请输入登录密码。" );
        } else {
            if( ! user.getPassword().equals( passwordAgain ) ) {
                this.addActionError( "两次密码输入不一致，请重新确认。" );
                user.setPassword( "" );
            }
        }
    }

    public void setPasswordAgain( String passwordAgain ) {
        this.passwordAgain = passwordAgain;
    }

    public void setRoleIds( String[] roleIds ) {
        this.roleIds = roleIds;
    }
    
}
