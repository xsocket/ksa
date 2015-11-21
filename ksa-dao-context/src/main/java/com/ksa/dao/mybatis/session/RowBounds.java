package com.ksa.dao.mybatis.session;

public class RowBounds extends org.apache.ibatis.session.RowBounds {

    public RowBounds( int page, int rows ) {
        super( page * rows - rows, rows );
    }
}
