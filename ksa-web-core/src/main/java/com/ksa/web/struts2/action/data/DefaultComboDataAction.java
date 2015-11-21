package com.ksa.web.struts2.action.data;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;

// FIXME 改进与 mybatis dao 的强耦合
public class DefaultComboDataAction extends DataActionSupport implements ComboDataAction {

    private static final long serialVersionUID = 2776771313956095764L;

    private static final Logger log = LoggerFactory.getLogger( DefaultComboDataAction.class );
    
    private static final String QUERY_DATA_STATEMENT_PREFIX = "combo-";
    
    protected Object[] comboDataArray = new Object[0]; 
    
    @Override
    public String execute() throws Exception {
        SqlSession sqlSession = ServiceContextUtils.getService( "sqlSession", SqlSession.class );
        if( sqlSession != null ) {
            String statement = getActionName();
            try {
                List<Object> gridDataList = sqlSession.selectList( QUERY_DATA_STATEMENT_PREFIX + statement, getParameters() );
                if( gridDataList != null && !gridDataList.isEmpty() ) {
                    comboDataArray = gridDataList.toArray();
                }
            } catch( Throwable e ) {
                log.warn( "列表数据获取失败，ActionName：" + statement, e );
            }
        }
        return SUCCESS;
    }

    @Override
    public Object[] getComboData() {
        return comboDataArray;
    }

}
