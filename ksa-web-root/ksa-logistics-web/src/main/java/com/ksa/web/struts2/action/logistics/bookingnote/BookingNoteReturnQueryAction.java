package com.ksa.web.struts2.action.logistics.bookingnote;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.ksa.dao.QueryClause;
import com.ksa.util.StringUtils;
import com.opensymphony.xwork2.ActionContext;


public class BookingNoteReturnQueryAction extends BookingNoteQueryAction {

    private static final long serialVersionUID = 3534648315175795775L;
    
    private static final String QUERY_RETURN_DATA_STATEMENT = "grid-logistics-bookingnote-unreturned";
    private static final String QUERY_RETURN_COUNT_STATEMENT= "count-logistics-bookingnote-unreturned";
    
    
    protected String getQueryDataStatement() {
        return QUERY_RETURN_DATA_STATEMENT;
    }
    
    protected String getQueryCountStatement() {
        return QUERY_RETURN_COUNT_STATEMENT;
    }
    
    @Override
    protected Map<String, Object> getParameters() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> paras = context.getParameters();
        Collection<String> clauseList = new ArrayList<String>( 16 );
        
        Map<String, QueryClause> queryMap = getQueryClauseMap();
        Set<String> keys = paras.keySet();
        String checkDate = null;
        for( String key : keys ) {
            if( "checkDate".equalsIgnoreCase( key ) ) {
                Object value = paras.get( key );
                if( value.getClass().isArray() ) {
                    checkDate = (String)Array.get( value, 0 );
                }
            }
            else if( queryMap.containsKey( key.toUpperCase() ) ) {
                QueryClause qc = queryMap.get( key.toUpperCase() );
                Object value = paras.get( key );
                if( value.getClass().isArray() ) {
                    Collection<String> clauses = qc.compute( ( String[] ) value );
                    if( clauses != null && clauses.size() > 0 ) {
                        clauseList.addAll( clauses );
                    }
                }
            }
        }
        
        //按权限过滤
        permissionFilter(clauseList);
        
        if( StringUtils.hasText( checkDate ) ) {
            paras.put( "checkDate", checkDate );
        } else {
            paras.remove( "checkDate" );
        }
        if( clauseList.size() > 0 ) {
            paras.put( "queryClauses", clauseList.toArray() );
        }
        
        return paras;
    }
    
}
