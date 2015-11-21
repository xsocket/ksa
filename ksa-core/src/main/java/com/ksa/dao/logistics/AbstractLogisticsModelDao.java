package com.ksa.dao.logistics;

import com.ksa.model.logistics.BaseLogisticsModel;


public interface AbstractLogisticsModelDao<T extends BaseLogisticsModel> {
    
    int insertLogisticsModel( T model ) throws RuntimeException;
    int updateLogisticsModel( T model ) throws RuntimeException;
    int deleteLogisticsModel( T model ) throws RuntimeException;
    T selectLogisticsModelById( String id ) throws RuntimeException;
    T selectLogisticsModelByBookingNoteId( String bookingNoteId ) throws RuntimeException;
}
