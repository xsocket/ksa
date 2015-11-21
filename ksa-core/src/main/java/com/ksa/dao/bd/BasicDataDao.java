package com.ksa.dao.bd;

import java.util.List;

import com.ksa.model.bd.BasicData;

/**
 * 基础数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface BasicDataDao {

    int insertBasicData( BasicData data ) throws RuntimeException;
    int updateBasicData( BasicData data ) throws RuntimeException;
    int deleteBasicData( BasicData data ) throws RuntimeException;
    
    List<BasicData> selectAllBasicData() throws RuntimeException;
    List<BasicData> selectBasicDataByType( String typeId ) throws RuntimeException;
    BasicData selectBasicDataById( String id ) throws RuntimeException;
}
