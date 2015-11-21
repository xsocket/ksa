package com.ksa.web.struts2.action.system.backup;

import java.io.*;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class RestoreAction extends ManagerAction implements JsonAction {

    private static final long serialVersionUID = -4521218538424130178L;

    private static Logger logger = LoggerFactory.getLogger( RestoreAction.class );
    
    private String filename;
    private JsonResult result;
    
    private static final String MYSQL_PROTOCAL = "jdbc:mysql://";

    @Override
    protected String doExecute() throws Exception {
        File file = new File(super.getBackupDirectory(), filename );
        if( !file.exists() || file.isDirectory() ) {
            this.addActionError( "名称为 '" + filename + "' 的数据备份文件不存在！" );
            return SUCCESS;
        } 
        OutputStream out = null;
        InputStream in = null;
        try {
            BasicDataSource db = ServiceContextUtils.getService( BasicDataSource.class );
            
            // 解析数据库链接字符串
            String url = db.getUrl().toLowerCase();
            if( !url.startsWith( MYSQL_PROTOCAL ) ) {
                throw new Exception( "数据备份失败，目前数据备份操作仅支持 Mysql 数据库。" );
            }
            
            url = url.substring( MYSQL_PROTOCAL.length() );
            String[] hostAndDb = url.split( "/" );
            // FIXME 这里没有检查url的合法性，直接使用了
            StringBuilder sb = new StringBuilder();
            sb.append( "mysql -h" ).append( hostAndDb[0] )  // 一定要加 -h localhost(或是服务器IP地址)
                .append( " -u" ).append( db.getUsername() )
                .append( " -p" ).append( db.getPassword() )
                .append( " -f " ).append( hostAndDb[1] );
            String commandStr = sb.toString();
            
            logger.debug( "Mysql登录命令：" + commandStr );
            // 执行Mysql登录
            Runtime rt = Runtime.getRuntime();
            Process child = rt.exec( commandStr  );
            out = child.getOutputStream();// 控制台的输入信息作为输出流 
            in = new FileInputStream( file );
            byte[] buffer = new byte[1024*1024];    //1mb 缓存
            int length;
            while( (length = in.read( buffer ) ) >= 0 ) {
                out.write( buffer, 0, length );
                out.flush();
            }
            this.result = new JsonResult( SUCCESS, "数据还原成功。" );
        } catch( Exception e ) {
            logger.warn( "数据库还原发生异常。", e );
            this.addActionError( e.getMessage() );
        } finally {
            if( out != null ) {
                out.close();
                out = null;
            }
            if( in != null ) {
                in.close();
                in = null;
            }
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
    
    public String getFilename() {
        return filename;
    }

    public void setFilename( String filename ) {
        this.filename = filename;
    }
}
