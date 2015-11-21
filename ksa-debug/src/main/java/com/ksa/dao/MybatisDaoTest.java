package com.ksa.dao;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public abstract class MybatisDaoTest {
    
    public static final String[] PATHS = { "classpath:test/mybatis-test-context.xml" };
    
    protected static ApplicationContext CONTEXT;
    
    @BeforeClass
    public static void init() {
        // 初始化应用环境
        CONTEXT = new ClassPathXmlApplicationContext( PATHS );
    }
}
