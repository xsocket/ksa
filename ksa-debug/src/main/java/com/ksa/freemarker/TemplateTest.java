package com.ksa.freemarker;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.junit.BeforeClass;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;


public abstract class TemplateTest {
    
    protected static Configuration CONFIG;
    
    @BeforeClass
    public static void init() {
        // 初始化应用环境
        CONFIG = new Configuration();
        CONFIG.setObjectWrapper(new DefaultObjectWrapper());
        CONFIG.setClassForTemplateLoading( TemplateTest.class, "/" );
    }
    
    protected Writer getDefaultOutputWriter() {
        return new OutputStreamWriter( System.out );
    }

    protected Template  getTemplate( String name ) throws IOException {
        return CONFIG.getTemplate( name, "UTF-8" );
    }
}
