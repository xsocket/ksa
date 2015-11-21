package com.ksa.web.struts2.action.finance.account.map;


public interface ValueGetter<T> {
    /** 获取对象值 */
    String getValue( T obj );
    /** 获取对象值的说明 */
    String getLabel( T obj );
}
