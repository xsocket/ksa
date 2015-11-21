package com.ksa.web.struts2.action.system.initialize;

import com.ksa.context.web.RuntimeConfiguration;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;

public abstract class BaseAction extends DefaultActionSupport {

    private static final long serialVersionUID = -1814282354603662780L;

    public static String DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    private static String KEY_SERVER = "sqlserver-server";
    private static String KEY_PORT = "sqlserver-port";
    private static String KEY_USERNAME = "sqlserver-username";
    private static String KEY_PASSWORD = "sqlserver-password";
    private static String KEY_DBNAME = "sqlserver-dbname";

    protected String server = "localhost";

    protected String port = "1433";

    protected String username = "ksa-admin";

    protected String password = "";

    protected String dbName = "HuoYun";

    protected String getConnectionUrl() {
        //String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=sample";
        StringBuilder sb = new StringBuilder();
        sb.append( "jdbc:sqlserver://" )
            .append( server )
            .append( ":" )
            .append( port )
            .append( "; DatabaseName=" )
            .append( dbName );
        return sb.toString();

    }

    public String getDbName() {
        return RuntimeConfiguration.getConfiguration().getString( KEY_DBNAME, dbName );
    }

    public void setDbName( String dbName ) {
        this.dbName = dbName;
        RuntimeConfiguration.getConfiguration().setProperty( KEY_DBNAME, dbName );
    }

    public String getServer() {
        return RuntimeConfiguration.getConfiguration().getString( KEY_SERVER, server );
    }

    public void setServer( String server ) {
        this.server = server;
        RuntimeConfiguration.getConfiguration().setProperty( KEY_SERVER, server );
    }

    public String getPort() {
        return RuntimeConfiguration.getConfiguration().getString( KEY_PORT, port );
    }

    public void setPort( String port ) {
        this.port = port;
        RuntimeConfiguration.getConfiguration().setProperty( KEY_PORT, port );
    }

    public String getUsername() {
        return RuntimeConfiguration.getConfiguration().getString( KEY_USERNAME, username );
    }

    public void setUsername( String username ) {
        this.username = username;
        RuntimeConfiguration.getConfiguration().setProperty( KEY_USERNAME, username );
    }

    public String getPassword() {
        return RuntimeConfiguration.getConfiguration().getString( KEY_PASSWORD, password );
    }

    public void setPassword( String password ) {
        if( StringUtils.hasText( password ) ) {
            this.password = password;
            RuntimeConfiguration.getConfiguration().setProperty( KEY_PASSWORD, password );
        }
    }

}
