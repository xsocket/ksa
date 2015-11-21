package com.ksa.dao.logistics.mybatis;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.logistics.BillOfLadingDao;
import com.ksa.model.logistics.BillOfLading;


public class MybatisBillOfLadingDao extends AbstractMybatisDao implements BillOfLadingDao {

    @Override
    public int insertLogisticsModel( BillOfLading bill ) throws RuntimeException {
        return this.session.insert( "insert-logistics-billoflading", bill );
    }

    @Override
    public int updateLogisticsModel( BillOfLading bill ) throws RuntimeException {
        return this.session.update( "update-logistics-billoflading", bill );
    }
    
    @Override
    public int deleteLogisticsModel( BillOfLading bill ) throws RuntimeException {
        return this.session.delete( "delete-logistics-billoflading", bill );
    }

    @Override
    public BillOfLading selectLogisticsModelById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-billoflading-byid", id );
    }
    
    @Override
    public BillOfLading selectLogisticsModelByBookingNoteId( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-billoflading-bybnid", id );
    }

}
