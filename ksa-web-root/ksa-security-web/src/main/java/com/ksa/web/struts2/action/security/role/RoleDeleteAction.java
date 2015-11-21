package com.ksa.web.struts2.action.security.role;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.security.SecurityService;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;


public class RoleDeleteAction extends ClearRoleCacheAction implements JsonAction {

    private static final long serialVersionUID = 7679010486858616124L;
    private JsonResult result;
    
    @Override
    public String doExecute() throws Exception {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        this.role = service.removeRole( role );
        this.addActionMessage( "成功删除角色。" );
        this.result = new JsonResult( SUCCESS, "成功删除角色。", this.role );
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();
        // 角色标识
        if( !StringUtils.hasText( role.getId() ) ) {
            this.addActionError( "请输入角色标识信息。" );
        } 
    }
    
    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next(), this.role );
        }
        return this.result;
    }

}
