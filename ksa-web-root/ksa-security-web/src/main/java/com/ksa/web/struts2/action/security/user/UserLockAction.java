package com.ksa.web.struts2.action.security.user;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.security.SecurityService;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

/**
 * 锁定/解锁用户的操作
 *
 * @author 麻文强
 */
public class UserLockAction extends UserAction implements JsonAction {

    private static final long serialVersionUID = 6248170054899641078L;
    
    private JsonResult result;
    
    @Override
    public String doExecute() throws Exception {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        if( user.isLocked() ) {
            service.lockUser( user );
            this.addActionMessage( "成功锁定用户。" );
            this.result = new JsonResult( SUCCESS, "成功锁定用户。", this.user );
        } else {
            service.unlockUser( user );
            this.addActionMessage( "成功激活用户。" );
            this.result = new JsonResult( SUCCESS, "成功激活用户。", this.user );
        }
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();
        // 用户标识
        if( !StringUtils.hasText( user.getId() ) ) {
            this.addActionError( "请输入用户标识信息。" );
        } 
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next(), this.user );
        }
        return this.result;
    }

}
