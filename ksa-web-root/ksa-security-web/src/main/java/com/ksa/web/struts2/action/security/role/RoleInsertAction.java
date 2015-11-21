package com.ksa.web.struts2.action.security.role;

import java.util.Map;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.security.User;
import com.ksa.service.security.SecurityService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;


public class RoleInsertAction extends ClearRoleCacheAction {

    private static final long serialVersionUID = -5312814052576650343L;
    
    /** 初始用户标识列表 */
    private String[] userIds;
    /** 初始权限标识列表 */
    private String[] permissionIds;
    
    @Override
    protected String doExecute() throws Exception {
        SecurityService service = ServiceContextUtils.getService( SecurityService.class );
        this.role = service.createRole( this.role, userIds, permissionIds );
        
        this.addActionMessage( "成功创建角色。" );
        return SUCCESS;
    }
    
    @Override
    protected void clearUserCache() {
        Map<String, User> cache = SecurityUtils.getCache();
        if( userIds != null && userIds.length > 0 ) {
            for( String userId : userIds ) {
                if( cache.containsKey( userId ) ) {
                    SecurityUtils.getCache().remove( userId );
                }
            }
        }
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
    
    public void setUserIds( String[] userIds ) {
        this.userIds = userIds;
    }
    
    public void setPermissionIds( String[] permissionIds ) {
        this.permissionIds = permissionIds;
    }
}
