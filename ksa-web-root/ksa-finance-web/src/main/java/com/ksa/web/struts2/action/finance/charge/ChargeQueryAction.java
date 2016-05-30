package com.ksa.web.struts2.action.finance.charge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.AbstractQueryClause;
import com.ksa.dao.DateQueryClause;
import com.ksa.dao.QueryClause;
import com.ksa.dao.TextQueryClause;
import com.ksa.dao.mybatis.session.RowBounds;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.finance.query.ChargeStateQueryClause;
import com.ksa.web.struts2.action.finance.query.FinanceDirectionQueryClause;
import com.ksa.web.struts2.action.logistics.bookingnote.BookingNoteQueryAction;
import com.opensymphony.xwork2.ActionContext;

public class ChargeQueryAction extends BookingNoteQueryAction {
 
    private static final long serialVersionUID = -2820784442841536220L;
    private static final Logger log = LoggerFactory.getLogger( ChargeQueryAction.class );
    
    protected Integer nature;
    protected Integer direction;
    protected Boolean settle;   // 是否开单

    protected static final Map<String, QueryClause> chargeQueryClauses = new HashMap<String, QueryClause>( 32 ); 
    
    static {
        for( String key : preparedQueryClauses.keySet() ) {
            chargeQueryClauses.put( key, preparedQueryClauses.get( key ) );
        }
        
        chargeQueryClauses.put( "CHARGE_STATE", new ChargeStateQueryClause() );
        
        chargeQueryClauses.put( "TARGET_ID", new TextQueryClause( "c.TARGET_ID" ) );
        chargeQueryClauses.put( "CHARGE_TYPE", new TextQueryClause( "c.TYPE" ) );
        chargeQueryClauses.put( "CURRENCY", new TextQueryClause( "c.CURRENCY_ID" ) );
        chargeQueryClauses.put( "DIRECTION", new FinanceDirectionQueryClause( "c.DIRECTION" ) );
        chargeQueryClauses.put( "NATURE", new FinanceDirectionQueryClause( "c.NATURE" ) );   
        chargeQueryClauses.put( "INPUTOR", new TextQueryClause( "c.CREATOR_ID" ) );
        chargeQueryClauses.put( "INPUT_DATE", new DateQueryClause( "c.CREATED_DATE" ) );
        chargeQueryClauses.put( "ACCOUNT_CODE", new TextQueryClause( "a.code" ) );
        chargeQueryClauses.put( "SETTLE", new AbstractQueryClause( "c.ACCOUNT_ID" ) {
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
                List<Charge> gridDataList = sqlSession.selectList( getQueryDataStatement(), paras, new RowBounds( this.page, 1000 ) );
                if( gridDataList != null && !gridDataList.isEmpty() ) {
                    Set<String> idSet = new HashSet<String>();  // 不重复添加 BookingNote
                    TreeSet<BookingNote> noteSet = new TreeSet<BookingNote>();
                    
                    for( Charge charge : gridDataList ) {
                        if( ! idSet.contains( charge.get_parentId() ) ) {
                            idSet.add( charge.get_parentId() );
                            noteSet.add( charge.getBookingNote() );
                        }
                    }

                    List<Object> result = new LinkedList<Object>( gridDataList );
                    for( BookingNote note : noteSet ) {
                        result.add( 0, note );
                    }
                    
                    // gridDataArray = gridDataList.toArray();
                    // gridDataCount = ( Integer ) sqlSession.selectOne( getQueryCountStatement(), paras );
                    gridDataArray = result.toArray();
                    gridDataCount = ( Integer ) sqlSession.selectOne( getQueryCountStatement(), paras ) + noteSet.size();
                }
            } catch( Throwable e ) {
                log.warn( "查询数据失败。", e );
            }
        }
        return SUCCESS;
    }
    
    @Override
    protected Map<String, Object> getParameters() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> paras = context.getParameters();
        if( direction != null && ! paras.containsKey( "direction" ) ) {
            paras.put( "DIRECTION", new String[] { direction.toString() } );
            
            // 收入结算单需要费用经过审核
            if(direction == 1) {
              paras.put( "CHARGE_STATE", new String[] { "8" } );
            }
        }
        if( nature != null && ! paras.containsKey( "nature" ) ) {
            paras.put( "NATURE", new String[] { nature.toString() } );
        }
        if( settle != null && ! paras.containsKey( "settle" ) ) {
            paras.put( "SETTLE", new String[] { settle.booleanValue() ? "true" : "false" }   );
        }
        
        if( ! StringUtils.hasText( this.sort ) ) {
            this.sort = "bn.serial_number";
            this.order = "desc";
        } 
        
        return super.getParameters();
    }
    
    @Override
    protected Map<String, QueryClause> getQueryClauseMap() {
        return chargeQueryClauses;
    }
    
    @Override
    protected String getQueryCountStatement() {
        return "count-finance-charge-query";
    }
    
    @Override
    protected String getQueryDataStatement() {
        return "grid-finance-charge-query";
    }
    
    public Integer getNature() {
        return nature;
    }
    
    public void setNature( Integer nature ) {
        this.nature = nature;
    }
    
    public Integer getDirection() {
        return direction;
    }
    
    public void setDirection( Integer direction ) {
        this.direction = direction;
    }
    
    public Boolean isSettle() {
        return settle;
    }
    
    public void setSettle( Boolean settle ) {
        this.settle = settle;
    }
    

}
