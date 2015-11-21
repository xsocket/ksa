package com.ksa.web.struts2.action.finance.query;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.dao.AbstractQueryClause;
import com.ksa.dao.QueryClause;


public class ChargeStateQueryClause extends AbstractQueryClause implements QueryClause {
    
    private static final Logger logger = LoggerFactory.getLogger( ChargeStateQueryClause.class );
    
    public ChargeStateQueryClause() {
        super( "bn.STATE" );
    }
    
    @Override
    protected String getCompareCondition( String value, int index ) {
        try {
            int v = Integer.parseInt( value );
            StringBuilder sb = new StringBuilder( 64 );
            if( v > 0 ) {
                sb.append( " ( " )
                    .append( getColumnName() )
                    .append( " & " )
                    .append( v )
                    .append( " > 0 ) " );
            } else if( v == 0 ) {   // 已开单
                sb.append( " ( c.ACCOUNT_ID IS NOT NULL AND c.ACCOUNT_ID <> '' ) " );
            } else if( v == -1 ) {
                sb.append( " ( c.ACCOUNT_ID IS NULL OR c.ACCOUNT_ID = '' ) " );
            } else {
                return null;
            }
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
