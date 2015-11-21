package com.ksa.web.struts2.action.finance.component;

import com.ksa.web.struts2.action.DefaultActionSupport;


public class FinanceSelectionAction extends DefaultActionSupport {

    private static final long serialVersionUID = -8520764711769003125L;
    
    protected Integer nature;
    protected Integer direction;
    protected Boolean settle;   // 是否开单
    
    public Integer getNature() {
        return nature;
    }
    
    public void setNature( Integer nature ) {
        this.nature = nature;
    }
    
    public Integer getDirection() {
        return direction;
    }
    
    public void setDirection( Integer direction ) {
        this.direction = direction;
    }
    
    public Boolean getSettle() {
        return settle;
    }
    
    public void setSettle( Boolean settle ) {
        this.settle = settle;
    }
    
    

}
