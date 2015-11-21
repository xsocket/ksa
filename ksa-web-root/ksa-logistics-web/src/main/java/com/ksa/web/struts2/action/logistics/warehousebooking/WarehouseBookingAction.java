package com.ksa.web.struts2.action.logistics.warehousebooking;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.WarehouseBookingDao;
import com.ksa.model.bd.BasicData;
import com.ksa.model.bd.BasicDataType;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.logistics.WarehouseBooking;
import com.ksa.service.bd.util.BasicDataUtils;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.logistics.LogisticsModelAction;

public class WarehouseBookingAction extends LogisticsModelAction<WarehouseBooking> {

    private static final long serialVersionUID = 3367963478402977713L;

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
    
    @Override
    protected void postInitalModel( BookingNote note ) {
        super.postInitalModel( note );  
        // 订舱委托书中起运、目的港变英文
        model.setDeparturePort( getPortAlias( model.getDeparturePort() ) );
        model.setDestinationPort( getPortAlias( model.getDestinationPort() ) );
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
