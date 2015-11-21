package com.ksa.dao.logistics.mybatis;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.logistics.WarehouseBookingDao;
import com.ksa.model.logistics.WarehouseBooking;


public class MybatisWarehouseBookingDao extends AbstractMybatisDao implements WarehouseBookingDao {

    @Override
    public int insertLogisticsModel( WarehouseBooking bill ) throws RuntimeException {
        return this.session.insert( "insert-logistics-warehousebooking", bill );
    }

    @Override
    public int updateLogisticsModel( WarehouseBooking bill ) throws RuntimeException {
        return this.session.update( "update-logistics-warehousebooking", bill );
    }
    
    @Override
    public int deleteLogisticsModel( WarehouseBooking bill ) throws RuntimeException {
        return this.session.delete( "delete-logistics-warehousebooking", bill );
    }

    @Override
    public WarehouseBooking selectLogisticsModelById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-warehousebooking-byid", id );
    }
    
    @Override
    public WarehouseBooking selectLogisticsModelByBookingNoteId( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-warehousebooking-bybnid", id );
    }

}
