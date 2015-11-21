package com.ksa.web.struts2.action.statistics.profit.model;

import java.util.HashMap;

public class DataSet extends HashMap<String, DataValue> {        

    private static final long serialVersionUID = -320590783160156785L;
    
    public DataSet() { }
    
    public DataSet( String label ) {
        this.label = label;
    }
    
    protected String label;

    public String getLabel() {
        return label;
    }
    
    public void setLabel( String label ) {
        this.label = label;
    }
    
}
