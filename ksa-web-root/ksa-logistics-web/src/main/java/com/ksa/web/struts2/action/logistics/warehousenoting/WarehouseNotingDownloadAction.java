package com.ksa.web.struts2.action.logistics.warehousenoting;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.WarehouseNotingDao;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.logistics.WarehouseNoting;
import com.ksa.web.struts2.action.logistics.LogisticsModelDownloadAction;

public class WarehouseNotingDownloadAction extends LogisticsModelDownloadAction<WarehouseNoting> {

    private static final long serialVersionUID = 7803232560310274863L;

    @Override
    public WarehouseNoting getModel() {
        if( model == null ) {
            model = new WarehouseNoting();   
        }
        return model;
    }
    
    @Override
    protected void postInitalModel( BookingNote note ) {
        model.setSaler( note.getSaler().getName() );
        model.setCustomer( note.getCustomer().getName() );
    }

    @Override
    protected WarehouseNotingDao getDao() {
        return ServiceContextUtils.getService( WarehouseNotingDao.class );
    }

    @Override
    protected String doGetFilename() {
        return "进仓通知书 - " + model.getBookingNote().getCode();
    }
}
