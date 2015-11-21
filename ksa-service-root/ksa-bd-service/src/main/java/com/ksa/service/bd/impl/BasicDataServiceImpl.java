package com.ksa.service.bd.impl;

import com.ksa.dao.bd.BasicDataDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.bd.BasicData;
import com.ksa.service.bd.BasicDataService;


public class BasicDataServiceImpl implements BasicDataService {
    
    private BasicDataDao basicDataDao;

    @Override
    public BasicData loadBasicDataById( String dataId ) throws RuntimeException {
        BasicData data = basicDataDao.selectBasicDataById( dataId );
        if( data == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的基本代码不存在。", dataId ) );
        }
        return data;
    }

    @Override
    public BasicData createBasicData( BasicData data ) throws RuntimeException {
        // 设置 id
        data.setId( ModelUtils.generateRandomId() );
        
        int length = basicDataDao.insertBasicData( data );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "新增基本代码发生异常，期望新增 1 条数据，实际新增了 %d 条数据。", length ) );
        }
        
        return data;
    }

    @Override
    public BasicData modifyBasicData( BasicData data ) throws RuntimeException {
        BasicData temp = basicDataDao.selectBasicDataById( data.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的基本代码不存在。", data.getId() ) );
        }
        int length = basicDataDao.updateBasicData( data );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "更新基本代码发生异常，期望更新 1 条数据，实际更新了 %d 条数据。", length ) );
        }
        return data;
    }

    @Override
    public BasicData removeBasicData( BasicData data ) throws RuntimeException {
        BasicData temp = basicDataDao.selectBasicDataById( data.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的基本代码不存在。", data.getId() ) );
        }
        
        if( ModelUtils.isReservedObject( temp ) ) {
            throw new IllegalArgumentException( String.format( "名称为 '%s' 的基本代码是系统保留数据，无法删除。", temp.getName() ) );
        }
        
        int length = basicDataDao.deleteBasicData( temp );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "删除基本代码发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", length ) );
        }
        return temp;
    }

    public void setBasicDataDao( BasicDataDao basicDataDao ) {
        this.basicDataDao = basicDataDao;
    }
}
