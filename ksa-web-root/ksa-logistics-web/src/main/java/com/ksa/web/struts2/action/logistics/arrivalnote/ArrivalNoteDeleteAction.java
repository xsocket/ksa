package com.ksa.web.struts2.action.logistics.arrivalnote;

import org.springframework.util.StringUtils;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.ArrivalNoteDao;
import com.ksa.model.bd.BasicData;
import com.ksa.model.bd.BasicDataType;
import com.ksa.model.logistics.ArrivalNote;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.bd.util.BasicDataUtils;
import com.ksa.web.struts2.action.logistics.LogisticsModelDeleteAction;

public class ArrivalNoteDeleteAction extends LogisticsModelDeleteAction<ArrivalNote> {

    private static final long serialVersionUID = -5898294504334520067L;

    @Override
    public ArrivalNote getModel() {
        if( model == null ) {
            model = new ArrivalNote();
        }
        return model;
    }

    @Override
    protected ArrivalNoteDao getDao() {
        return ServiceContextUtils.getService( ArrivalNoteDao.class );
    }
    
    @Override
    protected void postInitalModel( BookingNote note ) {
        model.setLoadingPort( getPortAlias( model.getLoadingPort() ) );
        model.setDischargePort( getPortAlias( model.getDischargePort() ) );
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
