package com.ksa.web.struts2.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class DefaultActionSupport extends ActionSupport {

    private static final long serialVersionUID = 4414474717653162942L;
    private static final Logger logger = LoggerFactory.getLogger( DefaultActionSupport.class );
    
    public static final String NO_PERMISSION = "no-permission";
    
    // TODO 完成自定义的 ActionSupport 
    
    @Override
    public String execute() throws Exception {
        try {
            return doExecute();
        } catch( RuntimeException e ) {
            logger.warn( "请注意，操作发生异常！", e );
            this.addActionError( e.getMessage() );
            return ERROR;
        }
    }
    
    /**
     * 具体的操作执行逻辑，无需考虑 RuntimeException 的处理。
     * @return
     * @throws Exception
     */
    protected String doExecute() throws Exception {
        return SUCCESS;
    }

}
