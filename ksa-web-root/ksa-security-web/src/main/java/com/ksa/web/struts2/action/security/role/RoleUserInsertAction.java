package com.ksa.web.struts2.action.security.role;

import java.util.Map;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.security.UserDao;
import com.ksa.model.security.User;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

/**
 * 为角色添加用户的操作
 *
 * @author 麻文强
 */
public class RoleUserInsertAction extends ClearRoleCacheAction implements JsonAction {

    private static final long serialVersionUID = 1687962752336417701L;

    private String[] userIds;
    
    private JsonResult result;
    
    @Override
    protected String doExecute() throws Exception {
        if( userIds != null && userIds.length > 0 ) {
            UserDao dao = ServiceContextUtils.getService( UserDao.class );
            String roleId = role.getId();
            for( String userId : userIds ) {
                dao.insertUserRole( userId, roleId );
            }
            this.result = new JsonResult( SUCCESS, "成功为角色添加用户。", this.role );
        }
        else {
            this.result = new JsonResult( SUCCESS, "没有指定需要添加的用户。");
        }
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
        super.clearUserCache();
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

    public void setUserIds( String[] userIds ) {
        this.userIds = userIds;
    }

}
