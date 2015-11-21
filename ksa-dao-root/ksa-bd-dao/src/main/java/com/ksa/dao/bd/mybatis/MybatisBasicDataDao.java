package com.ksa.dao.bd.mybatis;

import java.util.List;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.bd.BasicDataDao;
import com.ksa.model.bd.BasicData;

/**
 * 基于 Mybaits 的 BasicDataDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisBasicDataDao extends AbstractMybatisDao implements BasicDataDao {

    @Override
    public int insertBasicData( BasicData data ) throws RuntimeException {
        return this.session.insert( "insert-bd-data", data );
    }

    @Override
    public int updateBasicData( BasicData data ) throws RuntimeException {
        return this.session.update( "update-bd-data", data );
    }

    @Override
    public int deleteBasicData( BasicData data ) throws RuntimeException {
        return this.session.delete( "delete-bd-data", data );
    }

    @Override
    public BasicData selectBasicDataById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-bd-data-byid", id );
    }

    @Override
    public List<BasicData> selectBasicDataByType( String typeId ) throws RuntimeException {
        return this.session.selectList( "select-bd-data-bytype", typeId );
    }
    
    @Override
    public List<BasicData> selectAllBasicData() throws RuntimeException {
        return this.session.selectList( "select-bd-data-all" );
    }

}
