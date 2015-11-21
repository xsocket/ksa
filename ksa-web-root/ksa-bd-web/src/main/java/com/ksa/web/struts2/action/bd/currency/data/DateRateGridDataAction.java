package com.ksa.web.struts2.action.bd.currency.data;

import java.util.Calendar;
import java.util.List;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.service.bd.CurrencyRateService;
import com.ksa.web.struts2.action.data.GridDataActionSupport;

public class DateRateGridDataAction extends GridDataActionSupport {

    private static final long serialVersionUID = -7565072588650494148L;
    
    protected int year;
    protected int month;
    
    protected Object[] gridDataArray = new Object[0]; 
    
    @Override
    protected String doExecute() throws Exception {
        CurrencyRateService service = ServiceContextUtils.getService( CurrencyRateService.class );
        Calendar c = Calendar.getInstance();
        c.set( year, month - 1, 1 );
        List<CurrencyRate> rates = service.loadLatestCurrencyRates( c.getTime() );
        gridDataArray = rates.toArray();
        return SUCCESS;
    }

    @Override
    protected Object[] queryGridData() {
        return gridDataArray;
    }

    @Override
    protected int queryGridDataCount() {
        return gridDataArray.length;
    }
    
    public void setYear( int year ) {
        this.year = year;
    }
    
    public void setMonth( int month ) {
        this.month = month;
    }

    @Override
    public void validate() {
        super.validate();

        Calendar d = Calendar.getInstance();
        int currentYear = d.get( Calendar.YEAR );
        int currentMonth = d.get( Calendar.MONTH ) + 1;
       
        // 未设置年份和月份，则默认为当前月份
        if( year == 0 ) {
            this.year = currentYear;
        }
        if( month == 0 ) {
            this.month = currentMonth;
        }
        
        // 不允许查询未来的汇率，因为未来无法预知！
        if( year > currentYear ) {
            this.year = currentYear;
            this.month = currentMonth;
        } else if( year == currentYear && month > currentMonth ) {
            this.month = currentMonth;
        }
        
        // 验证 设置非法的年份和月份
        if( month < 1 ) {
            month = 1;
        }
        if( month > 12 ) {
            month = 12;
        }
        if( year < 1980 ) {
            year = 1980;
        }
        if( year > 2100 ) {
            year = 2100;
        }
    }
}
