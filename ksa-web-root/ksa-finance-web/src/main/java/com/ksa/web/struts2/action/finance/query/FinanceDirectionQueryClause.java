package com.ksa.web.struts2.action.finance.query;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.dao.AbstractQueryClause;
import com.ksa.dao.QueryClause;


public class FinanceDirectionQueryClause extends AbstractQueryClause implements QueryClause {
    
    private static final Logger logger = LoggerFactory.getLogger( FinanceDirectionQueryClause.class );
    
    public FinanceDirectionQueryClause( String columnName) {
        super( columnName );
    }
    
    @Override
    protected String getCompareCondition( String value, int index ) {
        try {
            int v = Integer.parseInt( value );
            StringBuilder sb = new StringBuilder( 64 );
            sb.append( " ( " )
                .append( getColumnName() )
                .append( " = " )
                .append( v )
                .append( " ) " );
            return sb.toString();
        } catch( Exception e ) {
            logger.warn( "获取查询比较片段时发生异常，忽略此比较。", e );
        }
        return null;
    }
    
    @Override
    public Collection<String> compute(String[] values) {
        Collection<String> clauses = super.compute( values );
        if( clauses != null && clauses.size() > 0 ) {
            StringBuilder sb = new StringBuilder( 16 * clauses.size() );
            sb.append( " ( " );
            int i = 0;
            for( String clause : clauses ) {
                if( i++ > 0 ) {
                    sb.append( " OR " );
                }
                sb.append( clause );
            }
            sb.append( " ) " );
            clauses.clear();
            clauses.add( sb.toString() );
        }
        return clauses;
    };
}