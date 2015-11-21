package com.ksa.web.struts2.action.model;

import java.io.Serializable;

/**
 * Json 数据模型。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -1773008502579676366L;

    private String status;

    private String message;

    private Object data;
    
    public JsonResult() {
        this( "unknow", "", null );
    }
    
    public JsonResult( String status ) {
        this( status, "", null );
    }

    public JsonResult( String status, String message ) {
        this( status, message, null );
    }

    public JsonResult( String status, String message, Object data ) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData( Object data ) {
        this.data = data;
    }

}
