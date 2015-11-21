package com.ksa.web.struts2.action.bd.data;

import org.springframework.util.StringUtils;

import com.ksa.model.bd.BasicData;
import com.ksa.model.bd.BasicDataType;
import com.ksa.service.bd.util.BasicDataUtils;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BasicDataAction extends DefaultActionSupport implements ModelDriven<BasicData> {

    private static final long serialVersionUID = 6210546520116254817L;
    
    protected BasicData data;
    
    public BasicDataAction() {
        data = new BasicData();
        if( BasicDataType.ALL_TYPE.length > 0 ) {
            data.setType( new BasicDataType( BasicDataType.ALL_TYPE[0].getId(), BasicDataType.ALL_TYPE[0].getName() ) );
        }
    }
    
    @Override
    public String execute() throws Exception {
        // 权限校验
        if( ! SecurityUtils.isPermitted( "bd:data" ) ) {
            return NO_PERMISSION;
        }
        try {
            String result = doExecute();
            updateBasicDataCache();
            return result;
        } catch( RuntimeException e ) {
            this.addActionError( e.getMessage() );
            return ERROR;
        }
    }
    
    protected void updateBasicDataCache() {
        if( StringUtils.hasText( data.getId() ) ) {
            BasicDataUtils.updateData( data.getId() );
        }
    }

    @Override
    public BasicData getModel() {
        return getData();
    }
    
    public BasicData getData() {
        return data;
    }

    public BasicDataType[] getAllTypes() {
        return BasicDataType.ALL_TYPE;
    }
}
