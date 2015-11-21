package com.ksa.web.struts2.action.logistics.warehousenoting;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.WarehouseNotingDao;
import com.ksa.model.logistics.WarehouseNoting;
import com.ksa.web.struts2.action.logistics.LogisticsModelSaveAction;

public class WarehouseNotingSaveAction extends LogisticsModelSaveAction<WarehouseNoting> {

    private static final long serialVersionUID = 3001174063961787256L;

    @Override
    public WarehouseNoting getModel() {
        if( model == null ) {
            model = new WarehouseNoting();
        }
        return model;
    }

    @Override
    protected WarehouseNotingDao getDao() {
        return ServiceContextUtils.getService( WarehouseNotingDao.class );
    }
}
