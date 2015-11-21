package com.ksa.web.struts2.action.finance.invoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.AbstractQueryClause;
import com.ksa.dao.DateQueryClause;
import com.ksa.dao.QueryClause;
import com.ksa.dao.TextQueryClause;
import com.ksa.dao.mybatis.session.RowBounds;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.data.GridDataActionSupport;
import com.ksa.web.struts2.action.finance.query.FinanceDirectionQueryClause;
import com.ksa.web.struts2.action.finance.query.InvoiceStateQueryClause;
import com.opensymphony.xwork2.ActionContext;


public class InvoiceQueryAction extends GridDataActionSupport {

    private static final long serialVersionUID = 5486180868391240224L;

    private static final Logger log = LoggerFactory.getLogger( InvoiceQueryAction.class );
    
    protected static final Map<String, QueryClause> preparedQueryClauses = new HashMap<String, QueryClause>( 32 );    
    // 初始化 queryClause
    static {
        preparedQueryClauses.put( "INVOICE_STATE", new InvoiceStateQueryClause() );        
        preparedQueryClauses.put( "CODE", new TextQueryClause( "i.CODE" ) );
        preparedQueryClauses.put( "TYPE", new TextQueryClause( "i.TYPE" ) );
        preparedQueryClauses.put( "TARGET_ID", new TextQueryClause( "i.TARGET_ID" ) );
        preparedQueryClauses.put( "DIRECTION", new FinanceDirectionQueryClause( "i.DIRECTION" ) );
        preparedQueryClauses.put( "CURRENCY", new TextQueryClause( "i.CURRENCY_ID" ) );
        preparedQueryClauses.put( "CREATED_DATE", new DateQueryClause( "i.CREATED_DATE" ) );
        preparedQueryClauses.put( "INPUTOR", new TextQueryClause( "i.CREATOR_ID" ) );
        preparedQueryClauses.put( "ACCOUNT_CODE", new TextQueryClause( "a.CODE" ) );
        preparedQueryClauses.put( "ACCOUNT_ID", new TextQueryClause( "i.ACCOUNT_ID" ) );     
        preparedQueryClauses.put( "SETTLE", new AbstractQueryClause( "i.ACCOUNT_ID" ) {
            protected String getCompareCondition( String value, int index ) {
                try {
                    StringBuilder sb = new StringBuilder( 64 );
                    sb.append( " ( (" )
                            .append( getColumnName() )
                            .append( Boolean.parseBoolean( value ) ? " IS NOT NULL  ) AND ( " : " IS NULL  ) OR ( "  )
                            .append( getColumnName() )
                            .append( Boolean.parseBoolean( value ) ? " <> '' " : " = '' "  )
                            .append( " ) ) " );
                    return sb.toString();
                } catch( Exception e ) { }
                return null;
            }
        });
    }
    
    protected Object[] gridDataArray = new Object[0]; 
    protected int gridDataCount = 0;
    
    private static final String QUERY_DATA_STATEMENT = "grid-finance-invoice-query";
    private static final String QUERY_COUNT_STATEMENT= "count-finance-invoice-query";
    
    @Override
    public String execute() throws Exception {
        SqlSession sqlSession = ServiceContextUtils.getService( SqlSession.class );
        if( sqlSession != null ) {
            Map<String, Object> paras = getParameters();
            if( StringUtils.hasText( this.sort ) ) {
                paras.put( "_sort", this.sort );
                paras.put( "_order", this.order );
            }
            
            try {
                List<Object> gridDataList = sqlSession.selectList( getQueryDataStatement(), paras, new RowBounds( this.page, this.rows ) );
                if( gridDataList != null && !gridDataList.isEmpty() ) {
                    gridDataArray = gridDataList.toArray();
                    Integer queryCount = sqlSession.selectOne( getQueryCountStatement(), paras );
                    gridDataCount = queryCount.intValue();
                }
            } catch( Throwable e ) {
                log.warn( "查询数据失败。", e );
            }
        }
        return SUCCESS;
    }
    
    protected String getQueryDataStatement() {
        return QUERY_DATA_STATEMENT;
    }
    
    protected String getQueryCountStatement() {
        return QUERY_COUNT_STATEMENT;
    }
    
    protected Map<String, Object> getParameters() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> paras = context.getParameters();
        Collection<String> clauseList = new ArrayList<String>( 16 );
        
        Set<String> keys = paras.keySet();
        for( String key : keys ) {
            if( preparedQueryClauses.containsKey( key.toUpperCase() ) ) {
                QueryClause qc = preparedQueryClauses.get( key.toUpperCase() );
                Object value = paras.get( key );
                if( value.getClass().isArray() ) {
                    Collection<String> clauses = qc.compute( ( String[] ) value );
                    if( clauses != null && clauses.size() > 0 ) {
                        clauseList.addAll( clauses );
                    }
                }
            }
        }
        if( clauseList.size() > 0 ) {
            paras.put( "queryClauses", clauseList.toArray() );
        }
        
        return paras;
    }
    

    @Override
    protected Object[] queryGridData() {
        return gridDataArray;
    }
    
    @Override
    protected int queryGridDataCount() {
        return gridDataCount;
    }

}
