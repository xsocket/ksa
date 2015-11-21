package com.ksa.web.struts2.action.finance.account;

import java.util.Calendar;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.finance.AccountService;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;


public class AccountCodeComputeAction extends DefaultActionSupport implements JsonAction {
    private static final long serialVersionUID = -8547400686834977166L;
    private JsonResult result;
    // 结算对象编码
    private String code;
    
    @Override
    protected String doExecute() throws Exception {

        if( !StringUtils.hasText( code ) ) {
            result = new JsonResult( ERROR, "请输入结算对象的编号。" );
            return SUCCESS;
        }
        
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder( 6 + code.length() );
        sb.append( calendar.get( Calendar.YEAR ) % 100 );
        int month = calendar.get( Calendar.MONTH ) + 1;
        sb.append( month < 10 ? "0" : "" );
        sb.append( month );
        int day = calendar.get( Calendar.DATE );
        sb.append( day < 10 ? "0" : "" );
        sb.append( day );
        sb.append( code.toUpperCase() );
        String pattenCode = sb.toString();
        int count = ServiceContextUtils.getService( AccountService.class ).querySimilarAccountCodeCount( pattenCode ) + 1;
        result = new JsonResult( SUCCESS, "J" + pattenCode + count );
        return super.doExecute();
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    @Override
    public Object getJsonResult() {
        return result;
    }
}
