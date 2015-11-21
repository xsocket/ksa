package com.ksa.context.web;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.ksa.context.ServiceContextUtils;
import com.ksa.context.spring.SpringServiceContext;

/**
 *  重写spring原生的ContextLoaderListener类。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class SpringServiceContextListener extends ContextLoader implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger( SpringServiceContextListener.class );
    
    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized( ServletContextEvent event ) {
        ApplicationContext context = this.initWebApplicationContext( event.getServletContext() );
        ServiceContextUtils.init( new SpringServiceContext( context ) );
        
        RuntimeConfiguration.init( event.getServletContext() );
        
        checkDatabase( context );
    }
    
    private void checkDatabase( ApplicationContext context ) {
        BasicDataSource ds = context.getBean( BasicDataSource.class );
        try { 
            DatabaseMetaData data = ds.getConnection().getMetaData();
            StringBuilder sb = new StringBuilder();
            sb.append( "数据库连接成功：" ).append( data.getDatabaseProductName() ).append( "-" ).append( data.getDatabaseProductVersion() );
            sb.append( "；用户名：" ).append( data.getUserName() );
            LOGGER.info( sb.toString() );
        } catch( SQLException e ) {
            LOGGER.warn( "数据库连接失败！", e );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed( ServletContextEvent event ) {
        this.closeWebApplicationContext( event.getServletContext() );
    }

}
