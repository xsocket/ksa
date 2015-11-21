package com.ksa.dao.mybatis.dialect;

/**
 * Oracle 数据源的分页查询方言。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class OracleLimitDialect implements LimitDialect {

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString( String query, int offset, int limit ) {
        if( offset < 0 ) { offset = 0; }
        if( limit < 0 ) { limit = 0; }
        query = query.trim();
        boolean hasOffset = ( offset > 0 );
        boolean isForUpdate = false;
        if( query.toLowerCase().endsWith( " for update" ) ) {
            query = query.substring( 0, query.length() - 11 );
            isForUpdate = true;
        }

        StringBuffer pagingSelect = new StringBuffer( query.length() + 100 );
        if( hasOffset ) {
            pagingSelect.append( "select * from ( select row_.*, rownum rownum_ from ( " );
        }
        else {
            pagingSelect.append( "select * from ( " );
        }
        pagingSelect.append( query );
        // Integer越界检测
        int upperBound = ( Integer.MAX_VALUE - limit < offset ) ? Integer.MAX_VALUE : offset + limit;
        if( hasOffset ) {
            pagingSelect.append( " ) row_ ) where rownum_ <= " ).append( upperBound ).append( " and rownum_ > " )
                    .append( offset );
        }
        else {
            pagingSelect.append( " ) where rownum <= " ).append( upperBound );
        }

        if( isForUpdate ) {
            pagingSelect.append( " for update" );
        }

        return pagingSelect.toString();
    }

}
