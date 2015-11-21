package com.ksa.web.struts2.action.logistics.billoflading;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.BillOfLadingDao;
import com.ksa.model.logistics.BillOfLading;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.logistics.LogisticsModelSaveAction;

public class BillOfLadingSaveAction extends LogisticsModelSaveAction<BillOfLading> {

    private static final long serialVersionUID = 3001174063961787256L;

    @Override
    public BillOfLading getModel() {
        if( model == null ) {
            model = new BillOfLading();
            model.setCreator( SecurityUtils.getCurrentUser().getName() );
        }
        return model;
    }

    @Override
    protected BillOfLadingDao getDao() {
        return ServiceContextUtils.getService( BillOfLadingDao.class );
    }
}
