package com.ksa.web.struts2.action.system.backup;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.system.backup.BackupTask;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class BackupAction extends ManagerAction implements JsonAction {

    private static final long serialVersionUID = -4521218538424130178L;

    private static Logger logger = LoggerFactory.getLogger( BackupAction.class );

    private JsonResult result;

    @Override
    protected String doExecute() throws Exception {
        BackupTask task = new BackupTask( ServletActionContext.getServletContext() );
        try {
            if( 0 == task.doBackup() ) {
                this.result = new JsonResult( SUCCESS, "数据备份成功。" );
            } else {
                throw new RuntimeException("数据库备份操作未成功执行，请确认 'mysqldump' 命令所在目录已被添加至 'Windows PATH' 环境变量中。");
            }
        } catch( Throwable e ) {
            logger.error( "数据库备份发生异常。", e );
            this.addActionError( e.getMessage() );
        }
        
        return SUCCESS;
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next() );
        }
        return this.result;
    }
}
