package com.ksa.web.struts2.action.data;

import com.ksa.web.struts2.action.model.GridDataModel;
import com.opensymphony.xwork2.Action;

public interface GridDataAction extends Action {
    
    /** 降序排序 */
    public static final String SORT_ORDER_DESC = "desc";
    
    /** 升序排序 */
    public static final String SORT_ORDER_ASC = "asc";

    /**
     * 设置所获取的分页数据的页码，起始页码为 1 。
     * @param page 页码，起始页码为 1 
     */
    void setPage( int page );
    
    /**
     * 设置所获取的分页数据的单页数据量。
     * @param rows 单页数据量
     */
    void setRows( int rows );
    
    /**
     * 设置所获取的分页数据的排序列名称。
     * @param sort 排序列名称
     */
    void setSort( String sort );
    
    /**
     * 设置所获取的分页数据的排序顺序，'asc' 表示顺序，'desc' 表示逆序。
     * @param order 'asc' 表示顺序，'desc' 表示逆序
     */
    void setOrder( String order );
    
    /**
     * 获取分页数据结果。
     * @return 列表数据
     */
    GridDataModel getGridData();
    
}
