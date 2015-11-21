package com.ksa.web.struts2.action.logistics.warehousebooking;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.WarehouseBookingDao;
import com.ksa.model.logistics.WarehouseBooking;
import com.ksa.web.struts2.action.logistics.LogisticsModelSaveAction;

public class WarehouseBookingSaveAction extends LogisticsModelSaveAction<WarehouseBooking> {

    private static final long serialVersionUID = 3001174063961787256L;

    @Override
    public WarehouseBooking getModel() {
        if( model == null ) {
            model = new WarehouseBooking();
        }
        return model;
    }

    @Override
    protected WarehouseBookingDao getDao() {
        return ServiceContextUtils.getService( WarehouseBookingDao.class );
    }
}
