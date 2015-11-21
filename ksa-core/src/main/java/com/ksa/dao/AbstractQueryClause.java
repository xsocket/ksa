package com.ksa.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.util.StringUtils;

public abstract class AbstractQueryClause implements QueryClause {
    
    private static final Logger logger = LoggerFactory.getLogger( AbstractQueryClause.class );
    
    protected String columnName;
    
    public AbstractQueryClause( String columnName ) {
        this.columnName = columnName;
    }

    /**
     * 返回查询条件子句中所需的字段名称。
     * 
     * @return 字段列名
     */
    protected String getColumnName() {
        return columnName;
    }

    /**
     * 将字符串格式的值，解析为 SQL 条件语句右侧的合理值。默认为处理字符串中的单引号 "'" 字符。
     * 
     * @param value
     * @return
     */
    protected String getParsedValue( String value ) throws Exception {
        StringBuilder sb = new StringBuilder( 64 );
        if( StringUtils.hasText( value ) ) {
            sb.append( " '" ).append( value.replace( "'", "\'" ) ).append( "' " );  // 格式化字符串
        } else {
            sb.append( " '' OR " )
                    .append( getColumnName() )
                    .append( " IS NULL " );
        }
        return sb.toString();
    }
    
    /***
     * 获取比较操作的具体比较符号，默认为 " = "，也就是相等比较。
     * @return
     */
    protected String getCompareSign() {
        return " = ";
    }

    /**
     * 获取单个查询参数所需的比较判断语句。
     * 
     * @param value
     *        查询比较的参数
     * @param index
     *        参数所在参数数据的索引
     * @return SQL WHER 子句所需的比较判断语句
     */
    protected String getCompareCondition( String value, int index ) {
        try {
            StringBuilder sb = new StringBuilder( 64 );
            sb.append( " ( " )
                    .append( getColumnName() )
                    .append( getCompareSign() )
                    .append( getParsedValue( value ) )
                    .append( " ) " );
            return sb.toString();
        } catch( Exception e ) {
            logger.warn( "获取查询比较片段时发生异常，忽略此比较。", e );
        }
        return null;
    }

    @Override
    public Collection<String> compute( String[] values ) {
        if( values == null || values.length <= 0 ) {
            return Collections.emptyList();
        }
        Collection<String> result = new ArrayList<String>( values.length );
        for( int i = 0; i < values.length; i++ ) {
            String condition = getCompareCondition( values[i], i );
            if( StringUtils.hasText( condition ) ) {
                result.add( condition );
            }
        }
        return result;
    }
}
