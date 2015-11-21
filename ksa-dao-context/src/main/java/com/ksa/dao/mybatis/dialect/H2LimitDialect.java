package com.ksa.dao.mybatis.dialect;

/**
 * H2 数据源的分页查询方言。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class H2LimitDialect implements LimitDialect {

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString( String query, int offset, int limit ) {
        if( offset < 0 ) { offset = 0; }
        if( limit < 0 ) { limit = 0; }
        
        StringBuffer sb = new StringBuffer( query.length() + 20 ).append( query ).append( " limit " ).append( limit );   //$NON-NLS-1$
        if( offset > 0 ) {
            sb.append( " offset " ).append( offset ); //$NON-NLS-1$ 
        }
        return sb.append( " " ).toString(); //$NON-NLS-1$
    }

}
