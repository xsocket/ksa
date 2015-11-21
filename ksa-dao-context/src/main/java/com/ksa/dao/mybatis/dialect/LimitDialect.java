package com.ksa.dao.mybatis.dialect;

/**
 * SQL分页方言。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface LimitDialect {
    
    /**
     * Does this dialect support some form of limiting query results via a SQL clause?
     *
     * @return True if this dialect supports some form of LIMIT.
     */
    boolean supportsLimit();
    
    /**
     * Given a limit and an offset, apply the limit clause to the query.
     *
     * @param query The query to which to apply the limit.
     * @param offset The offset of the limit
     * @param limit The limit of the limit
     * @return The modified query statement with the limit applied.
     */
    String getLimitString(String query, int offset, int limit);
}
