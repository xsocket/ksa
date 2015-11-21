package com.ksa.dao.mybatis.plugin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.ksa.dao.mybatis.dialect.LimitDialect;
import com.ksa.dao.mybatis.util.ReflectUtils;

@Intercepts( { @Signature( type = StatementHandler.class, method = "prepare", args = { Connection.class } ) } )  //$NON-NLS-1$ 
/**
 * 改进 Mybatis 分页查询的插件。<p>
 * Mybatis的分页是基于内存分页（查找出所有记录再取出偏移量的记录，如果 jdbc 驱动支持 absolute 定位或者 rs.next() 到指定偏移位置），
 * 这样的分页实现性能不佳。<p>
 * 通过插件将原查询语句进行必要的封转，从而可以利用 jdbc 内置的分页查询支持来提高分页查询的效率。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class PaginationPlugin implements Interceptor {

    private static Log log = LogFactory.getLog( PaginationPlugin.class );

    /** 长 query 方法中参数的数量。*/
    public static final int LONG_QUERY_PARAMETER_COUNT = 6;
    
    private Properties properties = new Properties();
    private Map<String, LimitDialect> cache = new HashMap<String, LimitDialect>(4);
    
    public PaginationPlugin() {
        // 初始化分页方言的实现
        URL url = PaginationPlugin.class.getResource("pagination.properties");  //$NON-NLS-1$ 
        try {
            this.properties.load( url.openStream() );
        } catch( IOException e ) {
            log.error( "Initialize pagination dialect implements fail", e );  //$NON-NLS-1$ 
        }
        if( log.isDebugEnabled() ) {
            log.debug( "Initialize pagination dialect implements success." );  //$NON-NLS-1$ 
        }
    }

    @Override
    public Object intercept( Invocation invocation ) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        BaseStatementHandler baseHandler = null;
        if( statementHandler instanceof RoutingStatementHandler ) {
            baseHandler = (BaseStatementHandler)ReflectUtils.getFieldValue( statementHandler, "delegate" );  //$NON-NLS-1$ 
        } else if( statementHandler instanceof BaseStatementHandler ) {
            baseHandler = (BaseStatementHandler)statementHandler;
        } else {
            // 无法获取 BaseStatementHandler 不进行分页设置
            return invocation.proceed();
        }

        RowBounds rowBounds = (RowBounds)ReflectUtils.getFieldValue( baseHandler, "rowBounds" );  //$NON-NLS-1$ 

        if( needPagination( rowBounds ) ) {
            BoundSql boundSql = baseHandler.getBoundSql();
            Configuration configuration = (Configuration)ReflectUtils.getFieldValue( baseHandler, "configuration" );  //$NON-NLS-1$ 
            LimitDialect dialect = getLimitDialect( configuration );
            if( dialect != null && dialect.supportsLimit() ) {
                String pageSql = dialect.getLimitString( boundSql.getSql(), rowBounds.getOffset(), rowBounds.getLimit() );
                if( log.isDebugEnabled() ) {
                    log.debug( "==> Paginating: " + pageSql );  //$NON-NLS-1$ 
                }
                // 将分页sql语句反射回BoundSql
                ReflectUtils.setFieldValue( boundSql, "sql", pageSql );  //$NON-NLS-1$ 
                // 不需要内存分页操作 
                ReflectUtils.setFieldValue( rowBounds, "limit", RowBounds.NO_ROW_LIMIT );  //$NON-NLS-1$ 
                ReflectUtils.setFieldValue( rowBounds, "offset", RowBounds.NO_ROW_OFFSET );  //$NON-NLS-1$ 
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin( Object target ) {
        return Plugin.wrap( target, this );
    }

    @Override
    public void setProperties( Properties properties ) {
        if( log.isDebugEnabled() ) {
            log.debug( "Set PaginationPlugin properties." );  //$NON-NLS-1$ 
        }
        this.properties.putAll( properties );       
    }

    private boolean needPagination( RowBounds rowBounds ) {
        return !( rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT && rowBounds.getOffset() == RowBounds.NO_ROW_OFFSET );
    }
    
    private LimitDialect getLimitDialect( Configuration config ) {
        String databaseId = getDatabaseId( config );
        if( cache.containsKey( databaseId ) ) {
            if( log.isDebugEnabled() ) {
                log.debug( "Get cached LimitDialect object for database type [ "+databaseId+" ]." );  //$NON-NLS-1$  //$NON-NLS-2$ 
            }
            return cache.get( databaseId );
        }
        String dialectType = this.properties.getProperty( databaseId );
        if( dialectType == null ) {
            log.error( "No LimitDialect is defined for database type [ "+databaseId+" ]." );//$NON-NLS-1$  //$NON-NLS-2$ 
        } else {
            try {
                LimitDialect dialect = (LimitDialect)Class.forName( dialectType ).newInstance();
                if( log.isDebugEnabled() ) {
                    log.debug( "Construct LimitDialect object [ " + dialect.toString() + " ] successful." );//$NON-NLS-1$  //$NON-NLS-2$ 
                }
                cache.put( databaseId, dialect );
                return dialect;
            } catch( Exception e ) {
                log.error( "Error occured when creating LimitDialect [ " + dialectType + " ] for database type [ " + databaseId + " ]. ", e );//$NON-NLS-1$  //$NON-NLS-2$ //$NON-NLS-3$  
            } 
        }
        return null;
    }

    private String getDatabaseId( Configuration config ) {
        // config.getDatabaseId() since mybatis-3.1.1
        String databaseId = config.getDatabaseId();
        if( databaseId == null ) {
            try {
                databaseId = getDatabaseId( config.getEnvironment().getDataSource() );
            } catch( SQLException e ) {
                  
            }
        }
        if( databaseId != null ) {
            databaseId = databaseId.toLowerCase();
        }
        return databaseId;
    }
    
    private String getDatabaseProductName( DataSource dataSource ) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            DatabaseMetaData metaData = con.getMetaData();
            return metaData.getDatabaseProductName();
        } finally {
            if( con != null ) {
                try { con.close(); } 
                catch( SQLException e ) { 
                    /* ignored */ 
                }
            }
        }
    }
    
    private String getDatabaseId( DataSource dataSource ) throws SQLException {
        String productName = getDatabaseProductName( dataSource );
        for( Map.Entry<Object, Object> property : this.properties.entrySet() ) {
            if( productName.toLowerCase().contains( property.getKey().toString().toLowerCase() ) ) {
                return (String)property.getKey();
            }
        }
        return null;
    }
}
