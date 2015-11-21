package com.ksa.web.struts2.action.data;

import com.ksa.web.struts2.action.model.GridDataModel;

public abstract class GridDataActionSupport extends DataActionSupport implements GridDataAction {

    private static final long serialVersionUID = -894387782806926084L;

    /** 页码，起始页码为 1。 */
    protected int page = 1;
    /** 单页数据量 - 默认不限制单页数量。 */
    protected int rows = Integer.MAX_VALUE;
    /** 排序列名称 */
    protected String sort;
    /** 'asc' 表示顺序，'desc' 表示逆序 。*/
    protected String order = SORT_ORDER_ASC;

    @Override
    public void setPage( int page ) {
        if( page >= 1 ) {
            this.page = page;
        }
    }

    @Override
    public void setRows( int rows ) {
        if( rows > 0 ) {
            this.rows = rows;
        }
    }

    @Override
    public void setSort( String sort ) {
        this.sort = sort;
    }

    @Override
    public void setOrder( String order ) {
        if( SORT_ORDER_DESC.equalsIgnoreCase( order ) ) {
            this.order = SORT_ORDER_DESC;
        }
    }

    /**
     * 获取列表数据结果。
     * 
     * @return 结果数据数组
     */
    protected abstract Object[] queryGridData();

    /**
     * 获取所有数据的数量。
     * 
     * @return 数据量
     */
    protected abstract int queryGridDataCount();

    @Override
    public GridDataModel getGridData() {
        Object[] rows = queryGridData();
        if( rows == null ) {
            rows = new Object[0];
        }
        int total = queryGridDataCount();
        if( total < rows.length ) {
            total = rows.length;
        }
        return new GridDataModel( total, rows );
    }
}
