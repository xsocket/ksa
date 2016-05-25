package com.ksa.context.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
