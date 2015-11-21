package com.ksa.web.struts2.action.system.backup;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.data.GridDataAction;
import com.ksa.web.struts2.action.model.GridDataModel;

public class QueryBackupFileListAction extends ManagerAction implements GridDataAction {

    private static final long serialVersionUID = -5217508701222252074L;

    /** 页码，起始页码为 1。 */
    protected int page = 1;

    /** 单页数据量 - 默认不限制单页数量。 */
    protected int rows = Integer.MAX_VALUE;

    @Override
    public GridDataModel getGridData() {
        // System.out.println( DateFormatUtils.format( new Date(), "yyyyMMddHHmmss" ) );
        if( !SecurityUtils.isPermitted( "system:backup" ) ) {
            // 没有权限
            return new GridDataModel( 0, new Object[0] );
        }

        File dir = getBackupDirectory();
        File[] files = dir.listFiles( new BackupFilenameFilter() );

        Arrays.sort( files, new Comparator<File>() {
            @Override
            public int compare( File o1, File o2 ) {
                return (int)( o2.lastModified() - o1.lastModified() );
            }
        } );

        // 总数量
        int total = files.length;

        int count = 0;
        int start = ( page - 1 ) * rows;
        int end = page * rows;
        if( start < total ) {
            count = ( end > total ? total - start : end - start );
        }
        FileWrapper[] results = new FileWrapper[count];
        for( int i = 0; i < count; i++ ) {
            results[i] = new FileWrapper( files[i + start] );
        }
        return new GridDataModel( total, results );
    }

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
    public void setSort( String sort ) {}

    @Override
    public void setOrder( String order ) {}

}
