package com.ksa.web.struts2.action.logistics.bookingnote.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.dao.AbstractQueryClause;
import com.ksa.dao.QueryClause;
import com.ksa.model.logistics.BookingNoteState;

public class BookingNoteStateQueryClause extends AbstractQueryClause implements QueryClause {
    
    private static final Logger logger = LoggerFactory.getLogger( BookingNoteStateQueryClause.class );
    
    public BookingNoteStateQueryClause() {
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
            } else if ( v == -2 ) {
                sb.append( " ( " )
                .append( getColumnName() )
                .append( " & " )
                .append( BookingNoteState.RETURNED )
                .append( " = 0 ) " );
            } else {
                // 处理状态值为 0 和 -1 的特殊情况
                sb.append( " ( " )
                    .append( getColumnName() )
                    .append( " = " )
                    .append( v )
                    .append( " ) " );
            }
            return sb.toString();
        } catch( Exception e ) {
            logger.warn( "获取查询比较片段时发生异常，忽略此比较。", e );
        }
        return null;
    }
    
    @Override
    public Collection<String> compute(String[] values) {
        Set<String> bookingnoteState = new HashSet<String>(4);
        Set<String> accountState = new HashSet<String>(4);
        for( String v : values ) {
            if( v.startsWith( "-" ) ) {
                if( v.equals( "-" ) ) {
                    accountState.add( "" );
                } else {
                    accountState.add( v.substring( 1 ) );
                }
            } else {
                bookingnoteState.add( v );
            }
        }
        
        String bookingnoteStateClause = computeBookingNoteState( bookingnoteState.toArray( new String[]{} ) );
        String accountStateClause = computeAccountState( accountState.toArray( new String[]{} ) );
        
        if( !StringUtils.isEmpty( bookingnoteStateClause ) && !StringUtils.isEmpty( accountStateClause ) ) {
            StringBuilder sb = new StringBuilder( 10 + bookingnoteStateClause.length() + accountStateClause.length() );
            sb.append( " ( " ).append( bookingnoteStateClause ).append( " OR " ).append( accountStateClause ).append( "  ) " );
            return Arrays.asList( sb.toString() );
        } else if( !StringUtils.isEmpty( bookingnoteStateClause ) ) {
            return Arrays.asList( bookingnoteStateClause );
        } else if( !StringUtils.isEmpty( accountStateClause ) ) {
            return Arrays.asList( accountStateClause );
        } else {
            return Collections.emptyList();
        }
    };
    
    public String computeAccountState(String[] values) {
        if( values == null || values.length <= 0 ) {
            return "";
        }
        //Set<Integer> notPositiveState = new HashSet<Integer>(); // 0 表示已开单；-1 表示未开单
        boolean computeNullAccount = false; // 是否查询未开单的费用
        Set<Integer> stateSet = new HashSet<Integer>();    // 0x2 表示开票中；0x8 表示收款中；0x20 表示结算完毕
        
        for( String value : values ) {
            try {
                if( StringUtils.isEmpty( value ) ) {
                    computeNullAccount = true;
                } else {
                    stateSet.add( Integer.parseInt( value ) );
                }
            } catch( Exception e ) {
                logger.warn( "状态值无法解析为整数，忽略不计。", e );
            }
        }
        
        StringBuilder sb = new StringBuilder( 200 * ( stateSet.size() ) );
        if( computeNullAccount ) {
            sb.append( " ( bn.ID IN ( SELECT KSA_FINANCE_CHARGE.BOOKINGNOTE_ID FROM KSA_FINANCE_CHARGE WHERE KSA_FINANCE_CHARGE.DIRECTION=1 AND KSA_FINANCE_CHARGE.ACCOUNT_ID IS NULL ) )" );
        }
        if( stateSet.size() > 0 ) {
            if( computeNullAccount ) {
                sb.append( " OR " );
            }
            sb.append( " ( bn.ID IN ( SELECT KSA_FINANCE_CHARGE.BOOKINGNOTE_ID FROM KSA_FINANCE_CHARGE JOIN KSA_FINANCE_ACCOUNT ON KSA_FINANCE_CHARGE.ACCOUNT_ID = KSA_FINANCE_ACCOUNT.ID WHERE " );
            int index = 0;
            for( Integer state : stateSet ) {
                if( index > 0 ) {
                    sb.append( " OR " );
                } 
                if( state == 0 ) {
                    sb.append( "( KSA_FINANCE_ACCOUNT.STATE = 0 )" );
                } else {
                    sb.append( "( KSA_FINANCE_ACCOUNT.STATE &" ).append( state ).append( " > 0  )" );
                }
                index++;
            }
            sb.append( " ) )" );
        }
        
        return sb.toString();
    }
    
    public String computeBookingNoteState(String[] values) {
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
            return sb.toString();
        }
        return "";
    }
}
