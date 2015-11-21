package com.ksa.dao;

import java.util.Collection;

/**
 * 符合查询条件生成统一接口。用于将传入的查询参数转换为 SQL 语句中 WHERE 子句所需的查询格式。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface QueryClause {
    
    /**
     * 将每个参数转换为 WHERE 子句中的查询片段。
     * @param values 参数数组
     * @return SQL WHERE 子句的查询片段数组
     */
    Collection<String> compute( String[] values );
}
