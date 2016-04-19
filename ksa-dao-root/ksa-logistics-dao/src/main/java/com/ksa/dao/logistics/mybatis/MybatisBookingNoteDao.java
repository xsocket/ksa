package com.ksa.dao.logistics.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.logistics.BookingNoteDao;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.logistics.BookingNoteState;

/**
 * 基于 Mybaits 的 BookingNoteDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisBookingNoteDao extends AbstractMybatisDao implements BookingNoteDao {

    @Override
    public int insertBookingNote( BookingNote note ) throws RuntimeException {
        return this.session.insert( "insert-logistics-bookingnote", note );
    }

    @Override
    public int updateBookingNote( BookingNote note ) throws RuntimeException {
        return this.session.update( "update-logistics-bookingnote", note );
    }
    
    @Override
    public int updateBookingNoteState( BookingNote note ) throws RuntimeException {
        return this.session.update( "update-logistics-bookingnote-state", note );
    }
    
    @Override
    public int updateBookingNoteType( BookingNote note ) throws RuntimeException {
      return this.session.update( "update-logistics-bookingnote-type", note );
    }
    
    @Override
    public int updateBookingNoteChargeDate( BookingNote note ) throws RuntimeException {
        return this.session.update( "update-logistics-bookingnote-chargedate", note );
    }

    @Override
    public int deleteBookingNote( BookingNote note ) throws RuntimeException {
        note.setState( BookingNoteState.DELETED );  // 将托单的状态设置为 '已删除'
        return updateBookingNoteState( note );
    }

    @Override
    public BookingNote selectBookingNoteById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-bookingnote-byid", id );
    }

    @Override
    public int selectBookingNoteCount() throws RuntimeException {
        return ( (Integer) this.session.selectOne( "count-logistics-bookingnote" ) ).intValue();
    }
    
    @Override
    public int selectBookingNoteCount( String queryString ) throws RuntimeException {
        Map<String, Object> para = new HashMap<String, Object>();
        // FIXME 对应sql中参数名是 queryClauses，这个参数设置无用！只能查到全部数据！
        para.put( "queryClause", new String[]{ queryString } );
        return ( (Integer) this.session.selectOne( "count-logistics-bookingnote-query", para ) ).intValue(); 
    }

    @Override
    public BookingNote selectBookingNoteByLading( BookingNote note ) throws RuntimeException {
        if( !StringUtils.hasText( note.getHawb() ) && !StringUtils.hasText( note.getMawb() ) ) {
            throw new IllegalArgumentException( "通过提单号查询托单时，主副提单号不能同时为空。" );
        }
        BookingNote param = new BookingNote();
        param.setId( note.getId() );
        if( StringUtils.hasText( note.getMawb() ) ) {
            param.setMawb( note.getMawb() );
        }
        if( StringUtils.hasText( note.getHawb() ) ) {
            param.setHawb( note.getHawb() );
        }
        return this.session.selectOne( "select-logistics-bookingnote-bylading", param );
    }
}
