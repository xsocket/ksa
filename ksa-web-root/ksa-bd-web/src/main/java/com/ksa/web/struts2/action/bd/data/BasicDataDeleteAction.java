package com.ksa.web.struts2.action.bd.data;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.bd.BasicDataService;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class BasicDataDeleteAction extends BasicDataAction implements JsonAction {

    private static final long serialVersionUID = 1698109281585224800L;
    
    private JsonResult result;
    
    @Override
    public String doExecute() throws Exception {
        BasicDataService service = ServiceContextUtils.getService( BasicDataService.class );
        data = service.removeBasicData( data );
        String message = String.format( "成功删除基本代码：'%s'。", data.getName() );
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message, data );
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();
        // 基本代码标识
        if( !StringUtils.hasText( data.getId() ) ) {
            this.addActionError( "请输入基本代码的标识信息。" );
        } 
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next(), data );
        }
        return this.result;
    }

}
