package com.ksa.web.struts2.action.finance.account;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ksa.dao.DateQueryClause;
import com.ksa.dao.QueryClause;
import com.ksa.dao.TextQueryClause;
import com.ksa.web.struts2.action.finance.query.AccountStateQueryClause;
import com.ksa.web.struts2.action.logistics.bookingnote.BookingNoteQueryAction;
import com.opensymphony.xwork2.ActionContext;

public class AccountQueryAction extends BookingNoteQueryAction {

    private static final long serialVersionUID = 3940356902474565176L;
    
    private static final String QUERY_CLAUSE_KEY_PREFIX = "ACCOUNT_";
    private static final String DIRECTION_QUERY_PARAMETER = "direction";
    private static final String NATURE_QUERY_PARAMETER = "nature";
    
    protected static final Map<String, QueryClause> accountQueryClauses = new HashMap<String, QueryClause>( 32 ); 
    
    static {
        for( String key : preparedQueryClauses.keySet() ) {
            accountQueryClauses.put( key, preparedQueryClauses.get( key ) );
        }
        accountQueryClauses.put( "ACCOUNT_STATE", new AccountStateQueryClause() );
        accountQueryClauses.put( "ACCOUNT_CODE", new TextQueryClause( "a.CODE" ) );
        accountQueryClauses.put( "ACCOUNT_TARGET", new TextQueryClause( "a.TARGET_ID" ) );
        accountQueryClauses.put( "ACCOUNT_DATE", new DateQueryClause( "a.CREATED_DATE" ) );
        accountQueryClauses.put( "ACCOUNT_DEADLINE", new DateQueryClause( "a.DEADLINE" ) );
        accountQueryClauses.put( "ACCOUNT_PAYMENT", new DateQueryClause( "a.PAYMENT_DATE" ) ); 
        accountQueryClauses.put( "ACCOUNT_INPUTOR", new TextQueryClause( "a.CREATOR_ID" ) );
    }
    
    @Override
    protected Map<String, Object> getParameters() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> paras = context.getParameters();
        Collection<String> noteClauseList = new ArrayList<String>( 16 );    // 针对 bookingnote 表所做的查询
        Collection<String> accountClauseList = new ArrayList<String>( 16 );
        
        Set<String> keys = paras.keySet();
        for( String key : keys ) {
            String queryKey = key.toUpperCase();
            if( accountQueryClauses.containsKey( queryKey ) ) {
                QueryClause qc = accountQueryClauses.get( queryKey );
                Object value = paras.get( key );
                if( value.getClass().isArray() ) {
                    Collection<String> clauses = qc.compute( ( String[] ) value );
                    if( clauses != null && clauses.size() > 0 ) {
                        if( queryKey.startsWith( QUERY_CLAUSE_KEY_PREFIX ) ) {
                            accountClauseList.addAll( clauses );
                        } else {
                            noteClauseList.addAll( clauses );
                        }
                    }
                }
            } else if( DIRECTION_QUERY_PARAMETER.equalsIgnoreCase( key ) ) {
                Object value = paras.get( key );
                if( value.getClass().isArray()  ) {
                    paras.put( DIRECTION_QUERY_PARAMETER, Array.get( value, 0 ) );
                }
            } else if( NATURE_QUERY_PARAMETER.equalsIgnoreCase( key ) ) {
                Object value = paras.get( key );
                if( value.getClass().isArray()  ) {
                    paras.put( NATURE_QUERY_PARAMETER, Array.get( value, 0 ) );
                }
            }
        }
        if( noteClauseList.size() > 0 ) {
            paras.put( "noteQueryClauses", noteClauseList.toArray() );
        }
        if( accountClauseList.size() > 0 ) {
            paras.put( "queryClauses", accountClauseList.toArray() );
        }
        
        return paras;
    }
    
    @Override
    protected String getQueryCountStatement() {
        return "count-finance-account-query";
    }
    
    @Override
    protected String getQueryDataStatement() {
        return "grid-finance-account-query";
    }

}
