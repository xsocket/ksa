package com.ksa.web.struts2.action.security.user;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.security.UserDao;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

/**
 * 为用户添加角色的操作
 *
 * @author 麻文强
 */
public class UserRoleInsertAction extends ClearUserCacheAction implements JsonAction {

    private static final long serialVersionUID = 2170574975239914873L;

    private String[] roleIds;
    
    private JsonResult result;
    
    @Override
    protected String doExecute() throws Exception {
        if( roleIds != null && roleIds.length > 0 ) {
            UserDao dao = ServiceContextUtils.getService( UserDao.class );
            String userId = user.getId();
            for( String roleId : roleIds ) {
                dao.insertUserRole( userId, roleId );
            }
            this.result = new JsonResult( SUCCESS, "成功为用户添加角色。", this.user );
        }
        else {
            this.result = new JsonResult( SUCCESS, "没有指定需要添加的角色。");
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

    public void setRoleIds( String[] roleIds ) {
        this.roleIds = roleIds;
    }

}
