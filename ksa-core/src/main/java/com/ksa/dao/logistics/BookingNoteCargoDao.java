package com.ksa.dao.logistics;

import java.util.List;

import com.ksa.model.logistics.BookingNoteCargo;

/**
 * 货物数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface BookingNoteCargoDao {
    int insertCargo( BookingNoteCargo cargo ) throws RuntimeException;
    int updateCargo( BookingNoteCargo cargo ) throws RuntimeException;
    int deleteCargo( BookingNoteCargo cargo ) throws RuntimeException;
    BookingNoteCargo selectCargoById( String id ) throws RuntimeException;
    List<BookingNoteCargo> selectCargoByBookingNoteId( String noteId ) throws RuntimeException;
}
