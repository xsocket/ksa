package com.ksa.web.struts2.action.system.backup;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.system.backup.BackupSchedule;
import com.ksa.system.backup.BackupTask;


public class StrategySave extends StrategyView {

    private static final long serialVersionUID = -2983070564056409125L;
    
    private static Logger logger = LoggerFactory.getLogger( StrategySave.class );

    @Override
    protected String doExecute() throws Exception {
        BackupSchedule instance = BackupSchedule.getInstance( ServletActionContext.getServletContext() );
        
        instance.setAutoBackupOn( on );
        instance.setNextExecuteDate( next );
        instance.setIntervalDay( interval );
        instance.save();
        instance.schedule();
        this.addActionMessage( "自动备份策略保存成功。"  );
        
        if( doJustNow ) {
            BackupTask task = new BackupTask( ServletActionContext.getServletContext() );
            try {
                if( 0 == task.doBackup() ) {
                    this.addActionMessage( "数据备份成功。"  );
                } else {
                    this.addActionError("数据库备份操作未成功执行，请确认 'mysqldump' 命令所在目录已被添加至 'Windows PATH' 环境变量中。");
                }
            } catch( Throwable e ) {
                logger.error( "数据库备份发生异常。", e );
                this.addActionError( e.getMessage() );
            }
        }
        
        this.next = instance.getNextExecuteDate();
        this.interval = instance.getIntervalDay();

        return SUCCESS;
    }
}
