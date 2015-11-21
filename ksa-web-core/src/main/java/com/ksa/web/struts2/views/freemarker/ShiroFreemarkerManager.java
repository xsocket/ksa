package com.ksa.web.struts2.views.freemarker;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.ksa.shiro.freemarker.ShiroTags;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;


public class ShiroFreemarkerManager extends FreemarkerManager {

    @Override
    protected Configuration createConfiguration( ServletContext servletContext ) throws TemplateException {        
        Configuration cfg = super.createConfiguration( servletContext );
        cfg.setSharedVariable("shiro", new ShiroTags());
        return cfg;
    }
}
