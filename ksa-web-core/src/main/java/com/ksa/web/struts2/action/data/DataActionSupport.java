package com.ksa.web.struts2.action.data;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Map.Entry;

import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ActionContext;

public class DataActionSupport extends DefaultActionSupport {

    private static final long serialVersionUID = -2812109970898284160L;

    protected Map<String, Object> getParameters() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> paras = context.getParameters();

        // 格式化参数
        for( Entry<String, Object> entry : paras.entrySet() ) {
            Object value = entry.getValue();
            if( value.getClass().isArray()  ) {
                entry.setValue( Array.get( value, 0 ) );
            }
        }
        
        return paras;
    }

    protected String getActionName() {
        return ActionContext.getContext().getName();
    }
}
