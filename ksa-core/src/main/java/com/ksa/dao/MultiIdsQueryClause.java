package com.ksa.dao;

import java.util.Collection;

public class MultiIdsQueryClause extends AbstractQueryClause implements QueryClause {

  public MultiIdsQueryClause(String columnName) {
    super(columnName);
  }
  
  @Override
  public Collection<String> compute(String[] values) {
      Collection<String> clauses = super.compute( values );
      if( clauses != null && clauses.size() > 0 ) {
          StringBuilder sb = new StringBuilder( 16 * clauses.size() );
          sb.append( " ( " );
          int i = 0;
          for( String clause : clauses ) {
              if( i++ > 0 ) {
                  sb.append( " OR " );
              }
              sb.append( clause );
          }
          sb.append( " ) " );
          clauses.clear();
          clauses.add( sb.toString() );
      }
      return clauses;
  };

}
