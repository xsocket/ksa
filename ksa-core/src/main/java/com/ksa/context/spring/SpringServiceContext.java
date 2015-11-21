package com.ksa.context.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.ksa.context.ContextException;
import com.ksa.context.ServiceContext;

public class SpringServiceContext implements ServiceContext {

    protected ApplicationContext context;

    public SpringServiceContext( ApplicationContext context ) {
        this.context = context;
    }

    @Override
    public Object getService( String name ) throws ContextException {
        try {
            return this.context.getBean( name );
        } catch( BeansException ex ) {
            throw new ContextException( ex );
        }
    }

    @Override
    public <T> T getService( String name, Class<T> requiredType ) throws ContextException {
        try {
            return this.context.getBean( name, requiredType );
        } catch( BeansException ex ) {
            throw new ContextException( ex );
        }
    }

    @Override
    public <T> T getService( Class<T> requiredType ) throws ContextException {
        try {
            return this.context.getBean( requiredType );
        } catch( BeansException ex ) {
            throw new ContextException( ex );
        }
    }

    @Override
    public boolean containsService( String name ) {
        return this.context.containsBean( name );
    }

}
