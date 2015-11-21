package com.ksa.web.struts2.action.finance.account;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.finance.Account;
import com.ksa.service.finance.AccountService;
import com.ksa.service.security.util.SecurityUtils;


public class AccountEditAction extends AccountAction {
    
    private static final Logger logger = LoggerFactory.getLogger( AccountEditAction.class );

    private static final long serialVersionUID = -2378526963715464865L;
    

    @Override
    protected String doExecute() throws Exception {
        if( StringUtils.hasText( account.getId() ) ) {
            AccountService service = ServiceContextUtils.getService( AccountService.class );
            try {
                account = service.loadAccountById( account.getId() );
            } catch( IllegalArgumentException e ) {
                logger.warn( "获取结算单发生异常", e );
                account = new Account();
                account.setCreator( SecurityUtils.getCurrentUser() );
            }
        } else {
            account.setCreator( SecurityUtils.getCurrentUser() );
        }
        return SUCCESS;
    }
    
    public String getJsonCharges() {
        try {
            return JSONUtil.serialize( account.getCharges(), null, null, false, true );
        } catch( JSONException e ) {
            logger.warn( "序列化数据列表发生异常！", e );
            return "[]";
        }
    }
    
    
}
