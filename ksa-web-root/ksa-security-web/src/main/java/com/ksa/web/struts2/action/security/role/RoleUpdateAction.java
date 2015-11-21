package com.ksa.web.struts2.action.security.role;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.security.SecurityService;
import com.ksa.util.StringUtils;


public class RoleUpdateAction extends ClearRoleCacheAction {

    private static final long serialVersionUID = 4663445849890599963L;

    @Override
    protected String doExecute() throws Exception {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        this.role = service.modifyRole( this.role );
        
        this.addActionMessage( "成功更新角色。" );
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();

        final int inputLength = 200;
        final int textLength = 2000;
        
        // 角色名称
        if( !StringUtils.hasText( role.getName() ) ) {
            this.addActionError( "请输入角色名称。" );
        } else {
            if( role.getName().length() > inputLength ) {
                this.addActionError( "角色名称过长，请控制在 <span class='badge badge-success'>" + inputLength + "</span> 个字符之内。" );
            }
        }
        // 角色说明
        if( StringUtils.hasText( role.getDescription() ) && role.getDescription() .length() > textLength ) {
            this.addActionError( "角色说明过长，请控制在 <span class='badge badge-success'>" + textLength + "</span> 个字符之内。" );
        }
    }
}
