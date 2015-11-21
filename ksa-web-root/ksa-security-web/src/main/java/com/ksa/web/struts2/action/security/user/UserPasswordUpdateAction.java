package com.ksa.web.struts2.action.security.user;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.security.SecurityService;
import com.ksa.util.StringUtils;


public class UserPasswordUpdateAction extends UserAction {

    private static final long serialVersionUID = -3347025203163133758L;
    
    private String passwordAgain;
    private String passwordOld;
    
    @Override
    public String doExecute() throws Exception {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        this.user = service.modifyUser( user.getId(), passwordOld, user.getPassword() );
        this.addActionMessage( "密码修改成功。" );
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
        
        if( ! user.getPassword().equals( passwordAgain ) ) {
            this.addActionError( "两次密码输入不一致，请重新确认。" );
            user.setPassword( "" );
        }
        
        if( ! StringUtils.hasText( passwordOld ) ) {
            this.addActionError( "请输入原登录密码。" );
            user.setPassword( "" );
        }
    }
    
    public void setPasswordAgain( String passwordAgain ) {
        this.passwordAgain = passwordAgain;
    }
    public void setPasswordOld( String passwordOld ) {
        this.passwordOld = passwordOld;
    }
}
