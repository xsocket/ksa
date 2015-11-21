package com.ksa.web.struts2.action.logistics.bookingnote;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.mybatis.session.RowBounds;
import com.ksa.model.logistics.BookingNote;
import com.ksa.util.StringUtils;


public class BookingNoteDownloadAction extends BookingNoteQueryAction {

    private static final long serialVersionUID = -1120043592127643875L;
    
    private static final Logger log = LoggerFactory.getLogger( BookingNoteDownloadAction.class );
    
    protected String filename = "导出托单.xls";
    protected List<BookingNote> bookingNotes;
    
    @Override
    public String execute() throws Exception {
        SqlSession sqlSession = ServiceContextUtils.getService( SqlSession.class );
        if( sqlSession != null ) {
            Map<String, Object> paras = getParameters();
            if( StringUtils.hasText( this.sort ) ) {
                paras.put( "_sort", this.sort );
                paras.put( "_order", this.order );
            }
            
            try {
                bookingNotes = sqlSession.selectList( getQueryDataStatement(), paras, new RowBounds( this.page, this.rows ) );
            } catch( Throwable e ) {
                log.warn( "导出托单失败", e );
                bookingNotes = Collections.emptyList();
            }
        }
        return SUCCESS;
    }
    
    public List<BookingNote> getBookingNotes() {
        if( bookingNotes == null ) {
            bookingNotes = Collections.emptyList();
        }
        return bookingNotes;
    }

    public String getFilename() {
        try {
            return new String(filename.getBytes("GBK"), "ISO8859-1");    // 修复下载文件名为乱码的bug
        } catch( UnsupportedEncodingException e ) {
            return filename;
        }
    }
    
    public void setFilename( String fileame ) {
        this.filename = fileame;
    }
}
