package com.ksa.web.struts2.action.logistics;

import org.springframework.util.StringUtils;

import com.ksa.dao.logistics.AbstractLogisticsModelDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.logistics.BaseLogisticsModel;

public abstract class LogisticsModelSaveAction<T extends BaseLogisticsModel>  extends LogisticsModelAction<T> { 

    private static final long serialVersionUID = -5491857729106847800L;

    @Override
    protected String doExecute() throws Exception {      
        AbstractLogisticsModelDao<T>  dao = getDao();
        if( StringUtils.hasText( model.getId() ) ) {   
            dao.updateLogisticsModel( model );
            model = dao.selectLogisticsModelById( model.getId() );
        } else {
            String id = ModelUtils.generateRandomId();
            model.setId( id );
            getDao().insertLogisticsModel( model );
            model = dao.selectLogisticsModelById( id );
        }
        return SUCCESS;
    }
    
}

