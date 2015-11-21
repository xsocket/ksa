package com.ksa.web.struts2.action.logistics.bookingnote.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.dao.AbstractQueryClause;
import com.ksa.dao.QueryClause;

@Deprecated
public class AccountStateQueryClause extends AbstractQueryClause implements QueryClause {
    
    private static final Logger logger = LoggerFactory.getLogger( AccountStateQueryClause.class );
    
    public AccountStateQueryClause() {
        super( "bn.ID" );
    }
    
    @Override
    public Collection<String> compute(String[] values) {
        if( values == null || values.length <= 0 ) {
            return Collections.emptyList();
        }
        Set<Integer> notPositiveState = new HashSet<Integer>(); // 0 表示已开单；-1 表示未开单
        Set<Integer> positiveState = new HashSet<Integer>();    // 0x2 表示开票中；0x8 表示收款中；0x20 表示结算完毕
        
        for( String value : values ) {
            try {
                int v = Integer.parseInt( value );
                if( v > 0 ) {
                    positiveState.add( v );
                } else {
                    notPositiveState.add( v );
                }
            } catch( Exception e ) {
                logger.warn( "状态值无法解析为整数，忽略不计。", e );
            }
        }
        
        StringBuilder sb = new StringBuilder( 200 * ( notPositiveState.size() + positiveState.size() ) );
        if( notPositiveState.size() >= 2 ) {
            // 既有0，又有-1，两个过滤条件取并集为全部结果，不需加入过滤条件
            return Collections.emptyList();
        } else if( notPositiveState.size() > 0 ) {
            Integer v = notPositiveState.iterator().next();
            sb.append( " ( bn.ID IN ( SELECT KSA_FINANCE_CHARGE.BOOKINGNOTE_ID FROM KSA_FINANCE_CHARGE WHERE KSA_FINANCE_CHARGE.DIRECTION=1 AND KSA_FINANCE_CHARGE.ACCOUNT_ID IS " )
            .append( (v == 0 ? " NOT " : " ") ).append( " NULL  ) )" );
        }
        if( positiveState.size() > 0 ) {
            if( notPositiveState.size() > 0 ) {
                sb.append( " OR " );
            }
            sb.append( " ( bn.ID IN ( SELECT KSA_FINANCE_CHARGE.BOOKINGNOTE_ID FROM KSA_FINANCE_CHARGE JOIN KSA_FINANCE_ACCOUNT ON KSA_FINANCE_CHARGE.ACCOUNT_ID = KSA_FINANCE_ACCOUNT.ID WHERE " );
            int index = 0;
            for( Integer state : positiveState ) {
                if( index > 0 ) {
                    sb.append( " OR " );
                }
                sb.append( "( KSA_FINANCE_ACCOUNT.STATE &" ).append( state ).append( " > 0  )" );
                index++;
            }
            sb.append( " ) )" );
        }
        
        return Arrays.asList( sb.toString() );
    };
}
