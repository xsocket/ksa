package com.ksa.dao.logistics;

import com.ksa.model.logistics.BookingNote;

/**
 * 托单数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface BookingNoteDao {
    int insertBookingNote( BookingNote note ) throws RuntimeException;
    int updateBookingNote( BookingNote note ) throws RuntimeException;
    int updateBookingNoteState( BookingNote note ) throws RuntimeException;
    int updateBookingNoteType( BookingNote note ) throws RuntimeException;
    int updateBookingNoteChargeDate( BookingNote note ) throws RuntimeException;
    int deleteBookingNote( BookingNote note ) throws RuntimeException;
    BookingNote selectBookingNoteById( String id ) throws RuntimeException;
    /**
     * 通过提单号查询相关的托单
     * @param note
     * @return
     * @throws RuntimeException
     */
    BookingNote selectBookingNoteByLading( BookingNote note ) throws RuntimeException;
    int selectBookingNoteCount() throws RuntimeException;
    int selectBookingNoteCount( String queryString ) throws RuntimeException;
}
