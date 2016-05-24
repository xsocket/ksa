package com.ksa.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.util.StringUtils;

public class DateQueryClause extends AbstractQueryClause implements QueryClause {
    
    private static final Logger logger = LoggerFactory.getLogger( DateQueryClause.class );

    public DateQueryClause( String columnName ) {
        super( columnName );
    }
    
    @Override
    protected String getParsedValue( String value ) throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse( value );
        } catch (ParseException ex) {
            value = value + "-01";
            try { date = format.parse( value ); }
            catch( ParseException e ) {
                logger.warn( "解析日期字符串时发生异常。", e );
                throw e;
            }
        }
        StringBuilder sb = new StringBuilder( 16 );
        sb.append( " '" )
            .append( format.format( date ) )
            .append( "' " );
        return sb.toString();
    }
    
    @Override
    protected String getCompareCondition( String value, int index ) {
        if( ! StringUtils.hasText( value ) ) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder( 64 );
            sb.append( " ( " )
                    .append( getColumnName() )
                    .append( ( index % 2 == 0 ) ? " >= " : " <= " )
                    .append( getParsedValue( value ) )
                    .append( " ) " );
            return sb.toString();
        } catch( Exception e ) {
            logger.warn( "获取查询比较片段时发生异常，忽略此比较。", e );
        }
        return null;
    }

}
