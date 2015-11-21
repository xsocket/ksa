package com.ksa.web.struts2.action.logistics.billoflading;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.BillOfLadingDao;
import com.ksa.model.bd.BasicData;
import com.ksa.model.bd.BasicDataType;
import com.ksa.model.logistics.BillOfLading;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.bd.util.BasicDataUtils;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.logistics.LogisticsModelAction;

public class BillOfLadingAction extends LogisticsModelAction<BillOfLading> {

    private static final long serialVersionUID = 3367963478402977713L;

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
    
    @Override
    protected void postInitalModel( BookingNote note ) {
        model.setDestinationPort( getPortAlias( model.getDestinationPort() ) );
        model.setLoadingPort( getPortAlias( model.getLoadingPort() ) );
        model.setDischargePort( getPortAlias( model.getDischargePort() ) );
        if( note.getQuantity() != null ) {
            model.setCargoQuantity( note.getQuantity() + " " );
        }
        if( StringUtils.hasText( note.getUnit() ) ) {
            model.setCargoQuantity( model.getCargoQuantity() + getUnitAlias( note.getUnit() ) );
        }
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
    
    private String getUnitAlias( String unit ) {
        if( StringUtils.hasText( unit ) ) {
            BasicData dUnit = BasicDataUtils.getDataFromName( unit, BasicDataType.UNITS.getId()  );
            if( dUnit != null ) {
                return model.getAlias( dUnit );
            }
        }
        return unit;
    }
}
