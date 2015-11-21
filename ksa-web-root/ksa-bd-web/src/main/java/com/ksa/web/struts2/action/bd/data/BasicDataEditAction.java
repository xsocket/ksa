package com.ksa.web.struts2.action.bd.data;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.bd.BasicDataService;

public class BasicDataEditAction extends BasicDataAction {

    private static final long serialVersionUID = 5219022222818831078L;

    @Override
    public String doExecute() throws Exception {
        BasicDataService service = ServiceContextUtils.getService( BasicDataService.class );
        data = service.loadBasicDataById( data.getId() );
        return SUCCESS;
    }
    
    @Override
    protected void updateBasicDataCache() {
        // 不需要更新缓存
    }
}
