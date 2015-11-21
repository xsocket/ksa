package com.ksa.web.struts2.action.model;

import java.io.Serializable;

/**
 * 表格数据模型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class GridDataModel implements Serializable {

    private static final long serialVersionUID = -207550381178775037L;
    
    private Object[] rows;
    private Object[] footer;
    private int total;

    public GridDataModel( Object[] rows ) {
        this( rows.length, rows );
    }
    
    public GridDataModel( Object[] rows, Object[] footer ) {
        this( rows.length, rows, footer );
    }
    
    public GridDataModel(int total, Object[] rows) {
        this( total, rows, null );
    }
        
    public GridDataModel( int total, Object[] rows, Object[] footer ) {
        this.total = total;
        this.rows = rows;
        this.footer = footer;
    }
    
    public int getTotal() {
        return total;
    }

    
    public void setTotal( int total ) {
        this.total = total;
    }

    public Object[] getRows() {
        return rows;
    }
    
    public void setRows( Object[] rows ) {
        this.rows = rows;
    }
    
    public Object[] getFooter() {
        return footer;
    }
    
    public void setFooter( Object[] footer ) {
        this.footer = footer;
    }
}
