package com.ksa.h2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class H2DataSourceFactoryBean implements FactoryBean<DataSource>, InitializingBean {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    protected String url;

    protected String username;

    protected String password;

    protected List<Resource> initSqlScripts;

    @Override
    public synchronized void afterPropertiesSet() throws Exception {
        if( this.initSqlScripts != null && this.initSqlScripts.size() > 0 ) {
            StringBuilder sb = new StringBuilder( this.url );
            File scriptDir = getWorkingDir();
            for( Resource res : initSqlScripts ) {
                // 将初始化脚本转换成本地文件。
                File script = toLocalFile( res, scriptDir );
                if( sb.length() == this.url.length() ) {
                    // 第一个初始化文件, 其中【;】是 H2 数据源连接字符串中，附加参数的分隔符。 */
                    sb.append( ";INIT=" ); //$NON-NLS-1$ 
                } else {
                    // 第二个到最后一个初始化文件，其中【\\;】是多个初始化SQL脚本文件之间的分隔符。
                    sb.append( "\\;" ); //$NON-NLS-1$ 
                }

                sb.append( "RUNSCRIPT FROM '" ) //$NON-NLS-1$ 
                        .append( script.getAbsolutePath().replace( '\\', '/' ) ) //$NON-NLS-1$  //$NON-NLS-2$ 
                        .append( "'" ); //$NON-NLS-1$ 
            }
            this.url = sb.toString();
            
            this.ds = JdbcConnectionPool.create( getUrl(), getUsername(), getPassword() );
        }
    }
    
    private DataSource ds;

    @Override
    public DataSource getObject() throws Exception {
        return this.ds;
    }

    @Override
    public Class<? extends DataSource> getObjectType() {
        return DataSource.class;
    }

    @Override
    public final boolean isSingleton() {
        return true;
    }

    /**
     * 返回 H2 数据源连接字符串。
     * 
     * @return 建立 H2 数据源所需的连接字符串
     */
    public synchronized String getUrl() {
        return this.url;
    }

    /**
     * 设置 H2 数据源连接字符串。
     * 
     * @param url
     *        建立 H2 数据源所需的连接字符串
     */
    public synchronized void setUrl( String url ) {
        this.url = url;
    }

    /**
     * 返回 H2 数据源连接的用户名。
     * 
     * @return 建立 H2 数据源所需的用户名
     */
    public String getUsername() {
        if( username == null ) {
            return ""; //$NON-NLS-1$
        }
        return username;
    }

    /**
     * 设置 H2 数据源连接的用户名。
     * 
     * @param username
     *        建立 H2 数据源所需的用户名
     */
    public void setUsername( String username ) {
        this.username = username;
    }

    /**
     * 返回 H2 数据源连接的密码。
     * 
     * @return 建立 H2 数据源所需的密码
     */
    public String getPassword() {
        if( password == null ) {
            return ""; //$NON-NLS-1$
        }
        return password;
    }

    /**
     * 设置 H2 数据源连接密码。
     * 
     * @param password
     *        建立 H2 数据源所需的连接密码
     */
    public void setPassword( String password ) {
        this.password = password;
    }

    /**
     * 返回 H2 数据源所需的初始化SQL脚本列表。
     * 
     * @return 建立 H2 数据源所需的初始化SQL脚本列表
     */
    public List<Resource> getInitSqlScripts() {
        return initSqlScripts;
    }

    /**
     * 设置 H2 数据源所需的初始化SQL脚本列表。
     * 
     * @param initSqlScripts
     *        建立 H2 数据源所需的初始化SQL脚本列表
     */
    public void setInitSqlScripts( List<Resource> initSqlScripts ) {
        this.initSqlScripts = initSqlScripts;
    }

    /** 将初始化脚本转化为本地文件 */
    private File toLocalFile( Resource sqlScript, File dir ) throws IOException {
        if( !sqlScript.exists() ) {
            throw new FileNotFoundException( String.format( "初始化脚本文件 '%s' 不存在。", sqlScript.getFilename() ) ); //$NON-NLS-1$ 
        }

        File scriptFile = new File( dir, new Date().getTime() + ".sql" ); //$NON-NLS-1$

        InputStream in = sqlScript.getInputStream();
        OutputStream out = new BufferedOutputStream( new FileOutputStream( scriptFile ) );

        try {
            this.copy( in, out );
            return scriptFile;
        } finally {
            if( in != null )
                in.close();
            if( out != null )
                out.close();
        }
    }

    /** 拷贝流。 */
    private long copy( InputStream input, OutputStream output ) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while( -1 != ( n = input.read( buffer ) ) ) {
            output.write( buffer, 0, n );
            count += n;
        }
        return count;
    }

    /** 获取初始化脚本文件所在本地目录。 */
    private File getWorkingDir() {
        File workingDir = new File( System.getProperty( "java.io.tmpdir" ), "h2_init_sql_scripts" ); //$NON-NLS-1$ //$NON-NLS-2$
        workingDir.mkdir();
        return workingDir;
    }
}
