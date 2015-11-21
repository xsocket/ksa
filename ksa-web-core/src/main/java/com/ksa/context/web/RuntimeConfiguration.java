package com.ksa.context.web;

import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class RuntimeConfiguration {

    private static FileConfiguration config;

    private static ServletContext context;

    public static FileConfiguration getConfiguration() {
        return config;
    }

    static void init( ServletContext servletContext ) {
        context = servletContext;
        try {
            URL url = servletContext.getResource( "/WEB-INF/runtime.properties" );
            config = new PropertiesConfiguration( url );
        } catch( Exception e ) {
            throw new RuntimeException( "运行时配置初始化失败。", e );
        }
    }

    public static void save() {
        try {
            URL url = context.getResource( "/WEB-INF/runtime.properties" );
            config.save( url );
        } catch( Exception e ) {
            throw new RuntimeException( "运行时配置初始化失败。", e );
        }
    }
}
