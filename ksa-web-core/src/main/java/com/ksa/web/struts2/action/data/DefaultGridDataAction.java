package com.ksa.web.struts2.action.data;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.mybatis.session.RowBounds;
import com.ksa.util.StringUtils;

// FIXME 改进与 mybatis dao 的强耦合
public class DefaultGridDataAction extends GridDataActionSupport {

    private static final long serialVersionUID = -3569929952546726658L;

    private static final Logger log = LoggerFactory.getLogger( DefaultGridDataAction.class );
    
    private static final String QUERY_DATA_STATEMENT_PREFIX = "grid-";
    private static final String QUERY_COUNT_STATEMENT_PREFIX= "count-";
    
    protected Object[] gridDataArray = new Object[0]; 
    protected int gridDataCount = 0;
    
    @Override
    public String execute() throws Exception {
        SqlSession sqlSession = ServiceContextUtils.getService( SqlSession.class );
        if( sqlSession != null ) {
            String statement = getActionName();
            Map<String, Object> paras = getParameters();
            if( StringUtils.hasText( this.sort ) ) {
                paras.put( "_sort", this.sort );
                paras.put( "_order", this.order );
            }
            
            try {
                List<Object> gridDataList = sqlSession.selectList( QUERY_DATA_STATEMENT_PREFIX + statement, paras, new RowBounds( this.page, this.rows ) );
                if( gridDataList != null && !gridDataList.isEmpty() ) {
                    gridDataArray = gridDataList.toArray();
                    Integer count = (Integer) sqlSession.selectOne( QUERY_COUNT_STATEMENT_PREFIX + statement, paras );
                    if( count != null ) {
                        gridDataCount = count.intValue();
                    }
                }
            } catch( Throwable e ) {
                log.warn( "表格数据获取失败，ActionName：" + statement, e );
            }
        }
        return SUCCESS;
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
