package com.ksa.web.struts2.action.logistics.bookingnote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.AbstractQueryClause;
import com.ksa.dao.DateQueryClause;
import com.ksa.dao.MultiIdsQueryClause;
import com.ksa.dao.QueryClause;
import com.ksa.dao.TextQueryClause;
import com.ksa.dao.mybatis.session.RowBounds;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.data.GridDataActionSupport;
import com.ksa.web.struts2.action.logistics.bookingnote.query.BookingNoteStateQueryClause;
import com.opensymphony.xwork2.ActionContext;


public class BookingNoteQueryAction extends GridDataActionSupport {

    private static final long serialVersionUID = -6326097142733661609L;

    private static final Logger log = LoggerFactory.getLogger( BookingNoteQueryAction.class );
    
    protected static final Map<String, QueryClause> preparedQueryClauses = new HashMap<String, QueryClause>( 32 );    
    // 初始化 queryClause
    static {
        preparedQueryClauses.put( "ACCOUNT_CODE", new AbstractQueryClause( "bn.ID" ) {
            @Override
            public Collection<String> compute( String[] values ) {
                
                if( values == null || values.length <= 0 ) {
                    return Collections.emptyList();
                }
                
                Set<String> params = new HashSet<String>();
                for( String v : values ) {
                    if( StringUtils.hasText( v ) ) {
                        params.add( " aa.CODE LIKE '%" + v.trim() + "%'" );
                    }
                }
                
                if( params.size() <= 0 ) {
                    return Collections.emptyList();
                }
                
                StringBuilder sb = new StringBuilder( 200 + 20 * params.size() );
                sb.append( " ( bn.ID IN ( SELECT cc.BOOKINGNOTE_ID FROM KSA_FINANCE_CHARGE cc JOIN KSA_FINANCE_ACCOUNT aa ON cc.ACCOUNT_ID = aa.ID WHERE " );
                int i = 0;
                for( String param : params ) {
                    if( i > 0 ) sb.append( " OR " );
                    sb.append( param );
                    i++;
                }
                sb.append( " ) ) " );
                Collection<String> result = new ArrayList<String>( 1 );
                result.add( sb.toString() );
                return result;
            }
        } );
        preparedQueryClauses.put( "CHARGE_TYPE_4BN", new AbstractQueryClause( "bn.ID" ) {
          @Override
          public Collection<String> compute( String[] values ) {
              
              if( values == null || values.length <= 0 ) {
                  return Collections.emptyList();
              }
              
              Set<String> params = new HashSet<String>();
              for( String v : values ) {
                  if( StringUtils.hasText( v ) ) {
                      params.add( "'" + v.trim() + "'" );
                  }
              }
              
              if( params.size() <= 0 ) {
                  return Collections.emptyList();
              }
              
              StringBuilder sb = new StringBuilder( 200 + 20 * params.size() );
              sb.append( " ( bn.ID IN (SELECT cc.BOOKINGNOTE_ID FROM KSA_FINANCE_CHARGE cc WHERE cc.TYPE IN (" );
              int i = 0;
              for( String param : params ) {
                  if( i > 0 ) sb.append( "," );
                  sb.append( param );
                  i++;
              }
              sb.append( ") ) ) " );
              Collection<String> result = new ArrayList<String>( 1 );
              result.add( sb.toString() );
              return result;
          }
      } );
        //preparedQueryClauses.put( "ACCOUNT_STATE", new AccountStateQueryClause() );  
        preparedQueryClauses.put( "STATE", new BookingNoteStateQueryClause() );        
        preparedQueryClauses.put( "CODE", new TextQueryClause( "bn.CODE" ) { 
            @Override
            protected String getParsedValue( String value ) {
                return super.getParsedValue( value.toUpperCase() );
            }
        } );
        preparedQueryClauses.put( "TYPE", new MultiIdsQueryClause( "bn.TYPE" ) );
        preparedQueryClauses.put( "TYPE_SUB", new MultiIdsQueryClause( "bn.TYPE_SUB" ) );
        preparedQueryClauses.put( "CUSTOMER_ID", new TextQueryClause( "bn.CUSTOMER_ID" ) );
        preparedQueryClauses.put( "INVOICE_NUMBER", new TextQueryClause( "bn.INVOICE_NUMBER" ) );
        preparedQueryClauses.put( "CREATED_DATE", new DateQueryClause( "bn.CREATED_DATE" ) );
        preparedQueryClauses.put( "CHARGE_DATE", new DateQueryClause( "bn.CHARGE_DATE" ) );
        preparedQueryClauses.put( "SALER_ID", new MultiIdsQueryClause( "bn.SALER_ID" ) );
        preparedQueryClauses.put( "CREATOR_ID", new MultiIdsQueryClause( "bn.CREATOR_ID" ) );
        preparedQueryClauses.put( "CARRIER_ID", new TextQueryClause( "bn.CARRIER_ID" ) );
        preparedQueryClauses.put( "SHIPPING_AGENT_ID", new TextQueryClause( "bn.SHIPPING_AGENT_ID" ) );
        preparedQueryClauses.put( "AGENT_ID", new TextQueryClause( "bn.AGENT_ID" ) );
        preparedQueryClauses.put( "CARGO_NAME", new TextQueryClause( "bn.CARGO_NAME" ) );
        preparedQueryClauses.put( "CARGO_CONTAINER", new TextQueryClause( "bn.CARGO_CONTAINER" ) );
        preparedQueryClauses.put( "SHIPPING_MARK", new TextQueryClause( "bn.SHIPPING_MARK" ) );        
        preparedQueryClauses.put( "BL_NO", new TextQueryClause( "bn.MAWB" ) {  // 主提单号和 副提单号都考虑
            @Override
            protected String getCompareCondition(String value, int index) {
                String parsedValue = getParsedValue( value ) ;
                StringBuilder sb = new StringBuilder( 64 );
                sb.append( " ( " )
                        .append( "bn.MAWB" )
                        .append( getCompareSign() )
                        .append( parsedValue )
                        .append( " OR " )
                        .append( "bn.HAWB" )
                        .append( getCompareSign() )
                        .append( parsedValue )
                        .append( " ) " );
                return sb.toString();
            };
        } );
        preparedQueryClauses.put( "SHIPPER_ID", new TextQueryClause( "bn.SHIPPER_ID" ) );
        preparedQueryClauses.put( "CONSIGNEE_ID", new TextQueryClause( "bn.CONSIGNEE_ID" ) );
        
        preparedQueryClauses.put( "DEPARTURE", new TextQueryClause( "bn.DEPARTURE" ) );
        preparedQueryClauses.put( "DESTINATION", new TextQueryClause( "bn.DESTINATION" ) );
        preparedQueryClauses.put( "DEPARTURE_PORT", new TextQueryClause( "bn.DEPARTURE_PORT" ) );
        preparedQueryClauses.put( "DESTINATION_PORT", new TextQueryClause( "bn.DESTINATION_PORT" ) );
        preparedQueryClauses.put( "LOADING_PORT", new TextQueryClause( "bn.LOADING_PORT" ) );
        preparedQueryClauses.put( "DISCHARGE_PORT", new TextQueryClause( "bn.DISCHARGE_PORT" ) );
        preparedQueryClauses.put( "DEPARTURE_DATE", new DateQueryClause( "bn.DEPARTURE_DATE" ) );
        preparedQueryClauses.put( "DESTINATION_DATE", new DateQueryClause( "bn.DESTINATION_DATE" ) );
        preparedQueryClauses.put( "DELIVER_DATE", new DateQueryClause( "bn.DELIVER_DATE" ) );
        
        preparedQueryClauses.put( "ROUTE", new TextQueryClause( "bn.ROUTE" ) );
        preparedQueryClauses.put( "ROUTE_NAME", new TextQueryClause( "bn.ROUTE_NAME" ) );
        preparedQueryClauses.put( "ROUTE_CODE", new TextQueryClause( "bn.ROUTE_CODE" ) );
        
        preparedQueryClauses.put( "CUSTOMS_BROKER_ID", new TextQueryClause( "bn.CUSTOMS_BROKER_ID" ) );
        preparedQueryClauses.put( "CUSTOMS_CODE", new TextQueryClause( "bn.CUSTOMS_CODE" ) );
        preparedQueryClauses.put( "CUSTOMS_DATE", new DateQueryClause( "bn.CUSTOMS_DATE" ) );
        preparedQueryClauses.put( "RETURN_CODE", new TextQueryClause( "bn.RETURN_CODE" ) );
        preparedQueryClauses.put( "RETURN_DATE", new DateQueryClause( "bn.RETURN_DATE" ) );
        preparedQueryClauses.put( "EXPRESS_CODE", new TextQueryClause( "bn.EXPRESS_CODE" ) );
        preparedQueryClauses.put( "TAX_CODE", new TextQueryClause( "bn.TAX_CODE" ) );
        preparedQueryClauses.put( "TAX_DATE1", new DateQueryClause( "bn.TAX_DATE1" ) );
        preparedQueryClauses.put( "TAX_DATE2", new DateQueryClause( "bn.TAX_DATE2" ) );
        
        preparedQueryClauses.put( "VEHICLE_TEAM_ID", new TextQueryClause( "bn.VEHICLE_TEAM_ID" ) );
        preparedQueryClauses.put( "VEHICLE_TYPE", new TextQueryClause( "bn.VEHICLE_TYPE" ) );
        preparedQueryClauses.put( "VEHICLE_NUMBER", new TextQueryClause( "bn.VEHICLE_NUMBER" ) );
    }
    
    protected Object[] gridDataArray = new Object[0]; 
    protected Integer gridDataCount = 0;
    
    private static final String QUERY_DATA_STATEMENT = "grid-logistics-bookingnote-query";
    private static final String QUERY_COUNT_STATEMENT= "count-logistics-bookingnote-query";
    
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
                    gridDataCount = ( Integer ) sqlSession.selectOne( getQueryCountStatement(), paras );
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
        
        Map<String, QueryClause> queryMap = getQueryClauseMap();
        Set<String> keys = paras.keySet();
        for( String key : keys ) {
            if( queryMap.containsKey( key.toUpperCase() ) ) {
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
        
        if( clauseList.size() > 0 ) {
            paras.put( "queryClauses", clauseList.toArray() );
        }
        
        return paras;
    }
    
    /**
     * 根据权限过滤查询参数
     * @param paras
     * @return
     */
    protected void permissionFilter(Collection<String> clauseList) {
      
      if(SecurityUtils.isPermitted( "bookingnote:viewall" )) {
        // 如果具有查看所有数据的权限，则不进行参数过滤
        return;
      } else {
        if(SecurityUtils.isPermitted( "bookingnote:edit:view" )) {
          // 如果仅能查看自己的单子，则进行个人过滤
          // 只能查看自己创建的 或者 自己负责销售 的单子
          String userId = SecurityUtils.getCurrentUser().getId();
          String query = " bn.CREATOR_ID = '" + userId + "' OR bn.SALER_ID = '" + userId + "'";
          // 可以查看广州事务所共享出来的托单
          if(SecurityUtils.isPermitted( "bookingnote:viewshare:gz" )) {
            query += " OR bn.creator_id in (select u.id from ksa_security_user u join ksa_security_userrole ur on ur.USER_ID = u.ID join ksa_security_rolepermission rp on rp.ROLE_ID = ur.ROLE_ID where rp.PERMISSION_ID = 'bookingnote:share:gz') ";
          }
          clauseList.add(" ( " + query + " ) ");
        }
        
        if(SecurityUtils.isPermitted( "bookingnote:viewshare:gz" )) {
          // 可以查看广州事务所共享出来的托单
          String query = " ( bn.creator_id in (select u.id from ksa_security_user u join ksa_security_userrole ur on ur.USER_ID = u.ID join ksa_security_rolepermission rp on rp.ROLE_ID = ur.ROLE_ID where rp.PERMISSION_ID = 'bookingnote:share:gz') ) ";
          clauseList.add(query);
        }
      }
    }
    
    protected  Map<String, QueryClause> getQueryClauseMap() {
        return preparedQueryClauses;
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
