package com.ksa.web.struts2.action.finance.charge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.bd.CurrencyRateService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class ChargeAction extends DefaultActionSupport implements ModelDriven<BookingNote> {

    private static final long serialVersionUID = -1007892464889907411L;
    
    private static Logger logger = LoggerFactory.getLogger( ChargeAction.class );
    private static final Collection<Pattern> excludeProperties = new ArrayList<Pattern>( 8 );
    static {
        //excludeProperties.add( Pattern.compile( ".*account.*" ) );
        excludeProperties.add( Pattern.compile( ".*bookingNote.*" ) );
        excludeProperties.add( Pattern.compile( ".*partner.*" ) );
    }
    
    protected String template;
    protected BookingNote bookingNote = new BookingNote();
    protected List<Charge> incomes = new ArrayList<Charge>();
    protected List<Charge> expenses = new ArrayList<Charge>();
    protected List<? extends CurrencyRate> rates;
    protected int nature = 1;
    
    @Override
    public String execute() throws Exception {
        if( ! SecurityUtils.isPermitted( "finance:charge" ) ) {
            return NO_PERMISSION;
        }
        return super.execute();
    }
    
    /***
     * 将费用列表序列化为 JSON 字符串。
     */
    protected String serializeList( List<?> list ) {
        try {
            return JSONUtil.serialize( list, excludeProperties, null, false, true );
        } catch( JSONException e ) {
            logger.warn( "序列化数据列表发生异常！", e );
            return "[]";
        }
    }
    
    /** 获取记账日期 */
    protected Date computeChargeDate() {
        // 以第一笔资金的记录时间为记账日期
        if( incomes.size() <= 0 && expenses.size() <=0 ) {
            return null;
        }
        
        Date chargeDate = new Date();
        for( Charge charge : incomes ) {
            if( charge.getCreatedDate().before( chargeDate ) ) {
                chargeDate = charge.getCreatedDate();
            }
        }
        for( Charge charge : expenses ) {
            if( charge.getCreatedDate().before( chargeDate ) ) {
                chargeDate = charge.getCreatedDate();
            }
        }
        return chargeDate;
    }

    @Override
    public BookingNote getModel() {
        return bookingNote;
    }
    
    public List<Charge> getIncomes() {
        return incomes;
    }

    public void setIncomes( List<Charge> incomes ) {
        this.incomes = incomes;
    }
    
    public List<Charge> getExpenses() {
        return expenses;
    }
    
    public void setExpenses( List<Charge> expenses ) {
        this.expenses = expenses;
    }
    
    public String getJsonIncomes() {
        return serializeList( incomes );
    }
    
    public String getJsonExpenses() {
        return serializeList( expenses );
    }
    
    public String getTemplate() {
        return template;
    }
    
    public void setTemplate( String template ) {
        this.template = template;
    }

    public String getJsonRates() {
        if( rates == null ) {
            CurrencyRateService rateService = ServiceContextUtils.getService( CurrencyRateService.class );
            //rates = rateService.loadLatestCurrencyRates();
            rates = rateService.loadAllCurrencyRates();
        }
        return serializeList( rates );
    }
    
    public void setCustomChargeDate( String date ) {
        if( StringUtils.hasText( date ) ) {
            try {
                date += "-01";
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse( date );
                getModel().setChargeDate( d );
            } catch( ParseException e ) {
                logger.warn( "提交的记账月份格式错误，忽略这个值。", e );
            }
        } else {
            getModel().setChargeDate( null );
        }
    }
    
    public int getNature() {
        return nature;
    }
    
    public void setNature( int nature ) {
        this.nature = nature;
    }
    
}
