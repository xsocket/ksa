package com.ksa.web.struts2.action.system.initialize;

import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class CheckConnectionAction extends BaseAction implements JsonAction {

    private static final long serialVersionUID = 1924965317258234841L;
    
    private static Logger logger = LoggerFactory.getLogger( CheckConnectionAction.class );

    @Override
    public Object getJsonResult() {
        try {
            Class.forName( DRIVER_NAME );
            DriverManager.getConnection( getConnectionUrl(), getUsername(), getPassword() );
            return new JsonResult( SUCCESS, "SQLServer2005 数据库连接成功！" );
        } catch( Exception e ) {
            logger.warn( "SQLServer2005 数据库连接失败！", e );
            return new JsonResult( ERROR, "SQLServer2005 数据库连接失败！失败原因：" + e.getMessage() );
        }
    }

}
