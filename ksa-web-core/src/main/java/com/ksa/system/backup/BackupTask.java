package com.ksa.system.backup;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.context.web.RuntimeConfiguration;


public class BackupTask extends TimerTask {
    
    private static final String MYSQL_PROTOCAL = "jdbc:mysql://";
    private static final String DEFAULT_BACKUP_PATH = "D:/KSA-BACKUP";
    
    private static Logger logger = LoggerFactory.getLogger( BackupTask.class );
    
    protected BackupSchedule schedule;
    protected ServletContext context;   // 废弃备份到 ServletContext 下
    
    
    public BackupTask( ServletContext sc ) {
        this.context = sc;
    }
    
    public BackupTask( BackupSchedule schedule, ServletContext sc ) {
        this.context = sc;
        this.schedule = schedule;
    }
    
    public static String generateBackupFilename() {
        return "ksa-" + DateFormatUtils.format( new Date(), "yyyyMMddHHmmss" ) + ".bak";
    }
    
    public static File getBackupDirectory() {
        String backupPath = RuntimeConfiguration.getConfiguration().getString( "backup.path", DEFAULT_BACKUP_PATH );
        File dir = new File(backupPath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
    
    public int doBackup() {
        try {
            BasicDataSource db = ServiceContextUtils.getService( BasicDataSource.class );
            
            // 解析数据库链接字符串
            String url = db.getUrl().toLowerCase();
            if( !url.startsWith( MYSQL_PROTOCAL ) ) {
                throw new RuntimeException( "数据备份失败，目前数据备份操作仅支持 Mysql 数据库。" );
            }
            
            url = url.substring( MYSQL_PROTOCAL.length() );
            String[] hostAndDb = url.split( "/" );
            // FIXME 这里没有检查url的合法性，直接使用了
            StringBuilder sb = new StringBuilder();
            String backupFilename = generateBackupFilename();
            sb.append( "mysqldump -h" ).append( hostAndDb[0] )  // 一定要加 -h localhost(或是服务器IP地址)
                .append( " -u" ).append( db.getUsername() )
                .append( " -p" ).append( db.getPassword() )
                .append( " " ).append( hostAndDb[1] )
                .append( " -r " ).append( new File( getBackupDirectory(), backupFilename ).getCanonicalPath() );
            String commandStr = sb.toString();
            
            // 查看数据库备份日志
            logger.warn( "执行数据库备份：" + commandStr );
            
            // 执行备份操作
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec( commandStr );
            
            return process.waitFor();
            
        } catch( Exception e ) {
            throw new RuntimeException( "数据库自动备份发生异常。", e );
        }
    }

    @Override
    public void run() {
        Date now = new Date();
        try {
            if( 0 == doBackup() ) {
                logger.warn( "数据库自动备份成功。" );
            } else {
                logger.error("数据库自动备份失败。");
            }
            
            if( this.schedule != null ) {
                long interval = ( (long)schedule.getIntervalDay() ) * 24L * 60L * 60L * 1000L;
                schedule.setNextExecuteDate( new Date( now.getTime() + interval ) );
                schedule.save();
            }
        } catch( Throwable e ) {
            logger.error( "数据库自动备份发生异常。", e );
        }
    }

}
