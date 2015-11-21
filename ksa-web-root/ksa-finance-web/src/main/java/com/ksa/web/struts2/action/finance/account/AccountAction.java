package com.ksa.web.struts2.action.finance.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.model.finance.Account;
import com.ksa.model.finance.FinanceModel;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class AccountAction extends DefaultActionSupport implements ModelDriven<Account> {

    private static final long serialVersionUID = 7722492556451459849L;
    private static Logger logger = LoggerFactory.getLogger( AccountAction.class );
    
    protected Account account = new Account();
    
    protected Map<String, String> params = new HashMap<String, String>();
    protected String title;
    protected int selected = 0;
    
    @Override
    public String execute() throws Exception {
        int direction = account.getDirection();
        if( ! SecurityUtils.isPermitted( "finance:account" + direction ) ) {
            return NO_PERMISSION;
        }
        if( title == null ) {
            if ( FinanceModel.isIncome( direction ) ) {
                title = "结算单管理";
            } else {
                title = "对账单管理";
            }
        }
        return super.execute();
    }

    @Override
    public Account getModel() {
        return account;
    }
    
    
    public String getParamsMap() {
        try {
            return JSONUtil.serialize( params );
        } catch( JSONException e ) {
            logger.warn( "序列化初始查询参数表发生异常！", e );
            return "{}";
        }
    }
    
    public Map<String, String> getParams() {
        return this.params;
    }
    
    public void setParams( Map<String, String> queryParams ) {
        this.params = queryParams;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle( String title ) {
        this.title = title;
    }
    
    public int getSelected() {
        return selected;
    }
    
    public void setSelected( int selected ) {
        this.selected = selected;
    }

}
