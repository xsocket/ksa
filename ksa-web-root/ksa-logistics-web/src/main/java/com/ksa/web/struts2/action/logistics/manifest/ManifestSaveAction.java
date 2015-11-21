package com.ksa.web.struts2.action.logistics.manifest;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.ManifestDao;
import com.ksa.model.logistics.Manifest;
import com.ksa.web.struts2.action.logistics.LogisticsModelSaveAction;

public class ManifestSaveAction extends LogisticsModelSaveAction<Manifest> {

    private static final long serialVersionUID = 3001174063961787256L;

    @Override
    public Manifest getModel() {
        if( model == null ) {
            model = new Manifest();
        }
        return model;
    }

    @Override
    protected ManifestDao getDao() {
        return ServiceContextUtils.getService( ManifestDao.class );
    }
}
