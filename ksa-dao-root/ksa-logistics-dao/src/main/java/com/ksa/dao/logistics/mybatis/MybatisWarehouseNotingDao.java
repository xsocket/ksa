package com.ksa.dao.logistics.mybatis;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.logistics.WarehouseNotingDao;
import com.ksa.model.logistics.WarehouseNoting;


public class MybatisWarehouseNotingDao extends AbstractMybatisDao implements WarehouseNotingDao {

    @Override
    public int insertLogisticsModel( WarehouseNoting bill ) throws RuntimeException {
        return this.session.insert( "insert-logistics-warehousenoting", bill );
    }

    @Override
    public int updateLogisticsModel( WarehouseNoting bill ) throws RuntimeException {
        return this.session.update( "update-logistics-warehousenoting", bill );
    }

    @Override
    public int deleteLogisticsModel( WarehouseNoting bill ) throws RuntimeException {
        return this.session.delete( "delete-logistics-warehousenoting", bill );
    }

    @Override
    public WarehouseNoting selectLogisticsModelById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-warehousenoting-byid", id );
    }
    
    @Override
    public WarehouseNoting selectLogisticsModelByBookingNoteId( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-warehousenoting-bybnid", id );
    }

}
