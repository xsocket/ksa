package com.ksa.web.struts2.action.security.role;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.security.RoleDao;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

/**
 * 为角色添加权限的操作
 *
 * @author 麻文强
 */
public class RolePermissionInsertAction extends ClearRoleCacheAction implements JsonAction {

    private static final long serialVersionUID = 6466318401891766029L;

    private String[] permissionIds;
    
    private JsonResult result;
    
    @Override
    protected String doExecute() throws Exception {
        if( permissionIds != null && permissionIds.length > 0 ) {
            RoleDao dao = ServiceContextUtils.getService( RoleDao.class );
            String roleId = role.getId();
            for( String permissionId : permissionIds ) {
                dao.insertRolePermission( roleId, permissionId );
            }
            this.result = new JsonResult( SUCCESS, "成功为角色添加权限。", this.role );
        }
        else {
            this.result = new JsonResult( SUCCESS, "没有指定需要添加的权限。");
        }
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

    public void setPermissionIds( String[] userIds ) {
        this.permissionIds = userIds;
    }

}
