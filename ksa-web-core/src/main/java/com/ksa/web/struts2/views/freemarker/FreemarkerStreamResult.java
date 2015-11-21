package com.ksa.web.struts2.views.freemarker;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerResult;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

import freemarker.template.Template;
import freemarker.template.TemplateModel;

/**
 * FreeMarkerStreamResult 结合了 StreamResult 和 FreemarkerResult，将通过 Freemarker 生成的下载文件发送到客户端。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class FreemarkerStreamResult extends FreemarkerResult {

    private static final long serialVersionUID = -7160610996561043631L;
    
    protected String contentDisposition = "inline";
    protected boolean allowCaching = true;
    
    @Override
    protected boolean preTemplateProcess( Template template, TemplateModel model ) throws IOException {
        // Override any parameters using values on the stack
        resolveParamsFromStack(invocation.getStack(), invocation);
        
        // Find the Response in context
        HttpServletResponse oResponse = ServletActionContext.getResponse();
        
        // Set the content-disposition
        if (contentDisposition != null) {
            oResponse.addHeader("Content-Disposition", conditionalParse(contentDisposition, invocation));
        }

        // Set the cache control headers if neccessary
        if (!allowCaching) {
            oResponse.addHeader("Pragma", "no-cache");
            oResponse.addHeader("Cache-Control", "no-cache");
        }
        
        return super.preTemplateProcess( template, model );
    }
    
    /**
     * Tries to lookup the parameters on the stack.  Will override any existing parameters
     *
     * @param stack The current value stack
     */
    protected void resolveParamsFromStack(ValueStack stack, ActionInvocation invocation) {
        String disposition = stack.findString("contentDisposition");
        if (disposition != null) {
            setContentDisposition(disposition);
        }

        String contentType = stack.findString("contentType");
        if (contentType != null) {
            setContentType(contentType);
        }
    }    
    
    /**
     * @return Returns the whether or not the client should be requested to allow caching of the data stream.
     */
    public boolean getAllowCaching() {
        return allowCaching;
    }

    /**
     * Set allowCaching to <tt>false</tt> to indicate that the client should be requested not to cache the data stream.
     * This is set to <tt>false</tt> by default
     *
     * @param allowCaching Enable caching.
     */
    public void setAllowCaching(boolean allowCaching) {
        this.allowCaching = allowCaching;
    }

    /**
     * @return Returns the Content-disposition header value.
     */
    public String getContentDisposition() {
        return contentDisposition;
    }

    /**
     * @param contentDisposition the Content-disposition header value to use.
     */
    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }
}
