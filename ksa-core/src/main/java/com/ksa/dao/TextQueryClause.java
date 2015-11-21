package com.ksa.dao;

import com.ksa.dao.QueryClause;
import com.ksa.util.StringUtils;

public class TextQueryClause extends AbstractQueryClause implements QueryClause {
    
    public TextQueryClause( String columnName ) {
        super( columnName );
    }

    @Override
    protected String getCompareSign() {
        return " LIKE ";
    }
    
    @Override
    protected String getParsedValue( String value ) {
        StringBuilder sb = new StringBuilder( 64 );
        if( StringUtils.hasText( value ) ) {
            sb.append( " '%" ).append( value.replace( "'", "''" ) ).append( "%' " );  // 格式化字符串
        } else {
            sb.append( " '' OR " )
                    .append( getColumnName() )
                    .append( " IS NULL " );
        }
        return sb.toString();
    }
}
