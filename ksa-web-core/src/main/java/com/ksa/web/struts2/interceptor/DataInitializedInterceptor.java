package com.ksa.web.struts2.interceptor;

import com.ksa.context.web.RuntimeConfiguration;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 判断是否执行过数据迁移的拦截器。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class DataInitializedInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 274352543038390892L;
    
    private static String RESULT_TO_INITIALIZE = "to-initialize";

    @Override
    public String intercept( ActionInvocation invocation ) throws Exception {
        if( isInitialized() ) {
            return invocation.invoke();
        } else {
            return RESULT_TO_INITIALIZE;
        }
    }
    
    private boolean isInitialized() {
        return RuntimeConfiguration.getConfiguration().getBoolean( "initialized", false );
    }

}
