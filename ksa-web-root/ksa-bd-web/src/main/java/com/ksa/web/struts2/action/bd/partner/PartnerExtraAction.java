package com.ksa.web.struts2.action.bd.partner;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.bd.PartnerDao;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;


public class PartnerExtraAction extends PartnerAction implements JsonAction {

    private static final long serialVersionUID = -9215324416967989426L;
    
    protected static final String ACTION_INSERT = "insert";
    protected static final String ACTION_DELETE = "delete";
    
    private JsonResult result;
    protected String extra;
    protected String action;
    
    @Override
    protected String doExecute() throws Exception {
        if( ACTION_INSERT.equalsIgnoreCase( action ) ) {
            return insert();
        } else if( ACTION_DELETE.equalsIgnoreCase( action ) ) {
            return delete();
        } else {
            return super.doExecute();
        }
    }
    
    protected String insert() throws Exception  {
        PartnerDao dao = ServiceContextUtils.getService( PartnerDao.class );      
        dao.insertPartnerExtra( partner, extra );
        String message = "成功添加抬头信息。";
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message );
        return SUCCESS;
    }
    
    protected String delete() throws Exception  {
        PartnerDao dao = ServiceContextUtils.getService( PartnerDao.class );      
        dao.deletePartnerExtra( partner, extra );
        String message = "成功删除抬头信息。";
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message );
        return SUCCESS;
    }
    
    public String getExtra() {
        return extra;
    }
    
    public void setExtra( String extra ) {
        this.extra = extra;
    }

    public String getAction() {
        return action;
    }
    
    public void setAction( String action ) {
        this.action = action;
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next(), partner );
        }
        return this.result;
    }
}
