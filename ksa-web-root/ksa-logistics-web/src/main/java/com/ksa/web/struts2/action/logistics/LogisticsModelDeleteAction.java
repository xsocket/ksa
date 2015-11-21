package com.ksa.web.struts2.action.logistics;

import org.springframework.util.StringUtils;

import com.ksa.dao.logistics.AbstractLogisticsModelDao;
import com.ksa.model.logistics.BaseLogisticsModel;

public abstract class LogisticsModelDeleteAction<T extends BaseLogisticsModel>  extends LogisticsModelAction<T> { 

    private static final long serialVersionUID = -5491857729106847800L;

    @Override
    protected String doExecute() throws Exception {      
        AbstractLogisticsModelDao<T>  dao = getDao();
        if( StringUtils.hasText( model.getId() ) ) {   
            dao.deleteLogisticsModel( model );
            model.setId( null );
        } 
        return super.doExecute();
    }
    
}

