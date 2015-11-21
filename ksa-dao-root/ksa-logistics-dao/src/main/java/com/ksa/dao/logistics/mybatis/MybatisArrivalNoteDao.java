package com.ksa.dao.logistics.mybatis;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.logistics.ArrivalNoteDao;
import com.ksa.model.logistics.ArrivalNote;


public class MybatisArrivalNoteDao extends AbstractMybatisDao implements ArrivalNoteDao {

    @Override
    public int insertLogisticsModel( ArrivalNote bill ) throws RuntimeException {
        return this.session.insert( "insert-logistics-arrivalnote", bill );
    }

    @Override
    public int updateLogisticsModel( ArrivalNote bill ) throws RuntimeException {
        return this.session.update( "update-logistics-arrivalnote", bill );
    }
    
    @Override
    public int deleteLogisticsModel( ArrivalNote bill ) throws RuntimeException {
        return this.session.delete( "delete-logistics-arrivalnote", bill );
    }

    @Override
    public ArrivalNote selectLogisticsModelById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-arrivalnote-byid", id );
    }
    
    @Override
    public ArrivalNote selectLogisticsModelByBookingNoteId( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-arrivalnote-bybnid", id );
    }

}
