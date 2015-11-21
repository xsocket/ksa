package com.ksa.web.struts2.action.bd.currency;

import java.util.Calendar;

public class DateRateAction extends RateAction {

    private static final long serialVersionUID = -5973328793188457985L;
    
    protected int yyyy;
    protected int mm;
    
    public int getYyyy() {
        return this.yyyy;
    }
    
    public void setYyyy( int yyyy ) {
        this.yyyy = yyyy;
    }
    
    // 月份用 month 表示 freemarker 不认
    public int getMm() {
        return this.mm;
    }
    
    public void setMm( int mm ) {
        this.mm = mm;
    }

    @Override
    public void validate() {
        super.validate();

        Calendar d = Calendar.getInstance();
        int currentYear = d.get( Calendar.YEAR );
        int currentMonth = d.get( Calendar.MONTH ) + 1;
       
        // 未设置年份和月份，则默认为当前月份
        if( yyyy == 0 ) {
            this.yyyy = currentYear;
        }
        if( mm == 0 ) {
            this.mm = currentMonth;
        }
        
        // 不允许查询未来的汇率，因为未来无法预知！
        if( yyyy > currentYear ) {
            this.yyyy = currentYear;
            this.mm = currentMonth;
        } else if( yyyy == currentYear && mm > currentMonth ) {
            this.mm = currentMonth;
        }
        
        // 验证 设置非法的年份和月份
        if( mm < 1 ) {
            mm = 1;
        }
        if( mm > 12 ) {
            mm = 12;
        }
        if( yyyy < 1980 ) {
            yyyy = 1980;
        }
        if( yyyy > 2100 ) {
            yyyy = 2100;
        }
    }
}
