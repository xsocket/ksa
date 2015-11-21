package com.ksa.web.struts2.action.statistics.profit;

public class ChartAction extends ProfitAction {

    private static final long serialVersionUID = -6182750165696704330L;
    
    protected String chart;    
    
    public String getChart() {
        return chart;
    }
    
    public void setChart( String chart ) {
        this.chart = chart;
    }
}
