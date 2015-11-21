package com.ksa.dao.logistics.mybatis;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.logistics.ManifestDao;
import com.ksa.model.logistics.Manifest;


public class MybatisManifestDao extends AbstractMybatisDao implements ManifestDao {

    @Override
    public int insertLogisticsModel( Manifest bill ) throws RuntimeException {
        return this.session.insert( "insert-logistics-manifest", bill );
    }

    @Override
    public int updateLogisticsModel( Manifest bill ) throws RuntimeException {
        return this.session.update( "update-logistics-manifest", bill );
    }
    
    @Override
    public int deleteLogisticsModel( Manifest bill ) throws RuntimeException {
        return this.session.delete( "delete-logistics-manifest", bill );
    }

    @Override
    public Manifest selectLogisticsModelById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-manifest-byid", id );
    }
    
    @Override
    public Manifest selectLogisticsModelByBookingNoteId( String id ) throws RuntimeException {
        return this.session.selectOne( "select-logistics-manifest-bybnid", id );
    }

}
