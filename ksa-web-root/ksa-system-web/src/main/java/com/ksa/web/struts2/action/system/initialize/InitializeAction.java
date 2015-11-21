package com.ksa.web.struts2.action.system.initialize;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.web.RuntimeConfiguration;
import com.ksa.system.initialize.util.InitializeUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class InitializeAction extends BaseAction implements JsonAction {
  
    private static final long serialVersionUID = -1291861750764575169L;

    private static Logger logger = LoggerFactory.getLogger( InitializeAction.class );
    
    private JsonResult result;
    
    @Override
    protected String doExecute() throws Exception {
        try {
            // 初始化 DataSource
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName( DRIVER_NAME );
            ds.setUrl( getConnectionUrl() );
            ds.setUsername( getUsername() );
            ds.setPassword( getPassword() );
            
            InitializeUtils.convertDatabase( ds );
            this.result = new JsonResult( SUCCESS, "数据迁移执行成功。" );
            RuntimeConfiguration.getConfiguration().setProperty( "initialized", true );
            RuntimeConfiguration.save();
        } catch(Exception e) {
            logger.warn( "数据迁移失败。", e );
            this.result = new JsonResult( ERROR, "数据迁移失败，失败原因：" + e.getMessage() );
        }
        return SUCCESS;
    }

    @Override
    public Object getJsonResult() {
        return this.result;
    }

}
