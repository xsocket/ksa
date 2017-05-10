package com.ksa.web.struts2.action.statistics.profit.model;


public class Category {
    
    public Category(){}
    public Category( String label ) {
        this.label = label;
    }

    protected String label;

    public String getLabel() {
        // filter
        return label == null ? "" : label.replaceAll("&", "");
    }

    public void setLabel( String label ) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
