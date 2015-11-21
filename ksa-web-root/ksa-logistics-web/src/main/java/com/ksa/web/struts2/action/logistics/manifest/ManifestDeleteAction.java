package com.ksa.web.struts2.action.logistics.manifest;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.ManifestDao;
import com.ksa.model.bd.BasicData;
import com.ksa.model.bd.BasicDataType;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.logistics.Manifest;
import com.ksa.service.bd.util.BasicDataUtils;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.logistics.LogisticsModelDeleteAction;

public class ManifestDeleteAction extends LogisticsModelDeleteAction<Manifest> {

    private static final long serialVersionUID = 7690675628193302779L;

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
    

    @Override
    protected void postInitalModel( BookingNote note ) {
        model.setDestinationPort( getPortAlias( model.getDestinationPort() ) );
        model.setLoadingPort( getPortAlias( model.getLoadingPort() ) );
    }
    
    private String getPortAlias( String portName ) {
        if( StringUtils.hasText( portName ) ) {
            BasicData dPort = BasicDataUtils.getDataFromName( portName, BasicDataType.PORT_SEA.getId(), BasicDataType.PORT_AIR.getId() );
            if( dPort != null ) {
                return model.getAlias( dPort );
            }
        }
        return portName;
    }
}
