package com.ksa.web.struts2.action.logistics.arrivalnote;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.ArrivalNoteDao;
import com.ksa.model.logistics.ArrivalNote;
import com.ksa.web.struts2.action.logistics.LogisticsModelSaveAction;

public class ArrivalNoteSaveAction extends LogisticsModelSaveAction<ArrivalNote> {

    private static final long serialVersionUID = -948358079048177970L;

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
}
