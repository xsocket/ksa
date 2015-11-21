package com.ksa.dao.logistics.mybatis;

import java.util.List;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.logistics.BookingNoteCargoDao;
import com.ksa.model.logistics.BookingNoteCargo;

/**
 * 基于 Mybaits 的 CargoDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisBookingNoteCargoDao extends AbstractMybatisDao implements BookingNoteCargoDao {

    @Override
    public int insertCargo( BookingNoteCargo cargo ) throws RuntimeException {
        return this.session.insert( "insert-logistics-bookingnote-cargo", cargo );
    }

    @Override
    public int updateCargo( BookingNoteCargo cargo ) throws RuntimeException {
        return this.session.update( "update-logistics-bookingnote-cargo", cargo );
    }

    @Override
    public int deleteCargo( BookingNoteCargo cargo ) throws RuntimeException {
        return this.session.delete( "delete-logistics-bookingnote-cargo", cargo );
    }

    @Override
    public BookingNoteCargo selectCargoById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-bookingnote-cargo-byid", id );
    }

    @Override
    public List<BookingNoteCargo> selectCargoByBookingNoteId( String noteId ) throws RuntimeException {
        return this.session.selectList( "select-logistics-bookingnote-cargo-bynoteid", noteId );
    }

}
