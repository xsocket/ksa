package com.ksa.web.struts2.action.finance.account;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ksa.model.logistics.BookingNote;
import com.ksa.web.struts2.action.finance.account.map.ValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.AbstractBookingNoteValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.AgentValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CargoContainerValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CargoNameValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CargoQuantityValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CargoVolumnValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CargoWeightValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CodeValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.ConsigneeValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CreatedDateValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CreatorValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CustomerValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CustomsBrokerValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CustomsCodeValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.CustomsDateValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.DepartureDateValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.DeparturePortValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.DestinationDateValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.DestinationPortValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.InvoiceValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.MawbValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.RouteNameValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.RouteValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.SalerValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.ShipperValueGetter;
import com.ksa.web.struts2.action.finance.account.map.bookingnote.TypeValueGetter;


public class AccountDownloadAction extends AccountExcelAction {

    private static final long serialVersionUID = 904808495618217566L;
    
    private static ValueGetter<BookingNote> NOTETYPE_GETTER = new TypeValueGetter();
    private static List<String> DEFAULT_SHOW_COLUMNS = new ArrayList<String>(); // Excel 结算单中默认显示的列
    private static Map<String, AbstractBookingNoteValueGetter> VALUE_MAPPER = new HashMap<String, AbstractBookingNoteValueGetter>();
    static {
        DEFAULT_SHOW_COLUMNS.add( "serial_number" );
        DEFAULT_SHOW_COLUMNS.add( "cargo_container" );
        DEFAULT_SHOW_COLUMNS.add( "departure_port" );
        DEFAULT_SHOW_COLUMNS.add( "departure_date" );
        DEFAULT_SHOW_COLUMNS.add( "destination_port" );
        DEFAULT_SHOW_COLUMNS.add( "mawb" );
        DEFAULT_SHOW_COLUMNS.add( "route_name" );
        
        VALUE_MAPPER.put( "serial_number", new CodeValueGetter() );
        VALUE_MAPPER.put( "type", new TypeValueGetter() );
        VALUE_MAPPER.put( "created_date", new CreatedDateValueGetter() );
        VALUE_MAPPER.put( "customer_name", new CustomerValueGetter() );
        VALUE_MAPPER.put( "invoice_number", new InvoiceValueGetter() );
        VALUE_MAPPER.put( "creator_name", new CreatorValueGetter() );
        VALUE_MAPPER.put( "saler_name", new SalerValueGetter() );
        VALUE_MAPPER.put( "agent_name", new AgentValueGetter() );
        VALUE_MAPPER.put( "mawb", new MawbValueGetter() );
        VALUE_MAPPER.put( "shipper_name", new ShipperValueGetter() );
        VALUE_MAPPER.put( "consignee_name", new ConsigneeValueGetter() );
        VALUE_MAPPER.put( "cargo_name", new CargoNameValueGetter() );
        VALUE_MAPPER.put( "volumn", new CargoVolumnValueGetter() );
        VALUE_MAPPER.put( "weight", new CargoWeightValueGetter() );
        VALUE_MAPPER.put( "quantity", new CargoQuantityValueGetter() );
        VALUE_MAPPER.put( "cargo_container", new CargoContainerValueGetter() );
        VALUE_MAPPER.put( "customs_broker_name", new CustomsBrokerValueGetter() );
        VALUE_MAPPER.put( "customs_code", new CustomsCodeValueGetter() );
        VALUE_MAPPER.put( "customs_date", new CustomsDateValueGetter() );
        VALUE_MAPPER.put( "route", new RouteValueGetter() );
        VALUE_MAPPER.put( "route_name", new RouteNameValueGetter() );
        VALUE_MAPPER.put( "departure_port", new DeparturePortValueGetter() );
        VALUE_MAPPER.put( "departure_date", new DepartureDateValueGetter() );
        VALUE_MAPPER.put( "destination_port", new DestinationPortValueGetter() );
        VALUE_MAPPER.put( "destination_date", new DestinationDateValueGetter() );
    }
    
    protected List<String> columns;
    protected String filename = "test.xml";
    
    protected Set<String> bookingNoteHeader = new LinkedHashSet<String>();
    protected List<List<String>> bookingNoteData = new ArrayList<List<String>>();
    
    protected Calendar calendar = Calendar.getInstance();
    
    @Override
    protected String doExecute() throws Exception {
        // account.setDirection( -1 );
        String result = super.doExecute();
        
        calendar.setTime( account.getCreatedDate() );
        calendar.set( Calendar.DAY_OF_MONTH, 1 );
        // 账期暂定为设置结算单的前一个月
        // calendar.add( Calendar.DAY_OF_MONTH, -1 );  暂时取消这个"前一个月"的需求
        
        filename = ( account.getDirection() == 1 ? "结算单" : "对账单" ) + "-" + account.getCode() + ".xls";

        BookingNote note = null;
        List<String> showColumns = getColumns();
        for( int i = 0; i < bookingNotes.size(); i++ ) {
            note = bookingNotes.get( i );
            List<String> values = new ArrayList<String>( showColumns.size() + 1 );  // 第0列放入结算单类型
            values.add( getBookingNoteType( note ) );
            for( String columnName : showColumns ) {
                AbstractBookingNoteValueGetter getter = VALUE_MAPPER.get( columnName );
                values.add( getter.getValue( note ) );
            }
            bookingNoteData.add( values );
        }
        if( note != null ) {
            for( String columnName : showColumns ) {
                AbstractBookingNoteValueGetter getter = VALUE_MAPPER.get( columnName );
                bookingNoteHeader.add( getter.getLabel( note ) );
            }
        }
        
        return result;
    }
    
    protected String getBookingNoteType( BookingNote note ) {
        return NOTETYPE_GETTER.getValue( note );
    }
    
    @Deprecated
    public String getServiceType() {
        if( bookingNotes == null || bookingNotes.size() <= 0 ) {
            return "其他";
        }
        return NOTETYPE_GETTER.getValue( bookingNotes.get( 0 ) );
    }
    
    // 账期起始日
    public String getAccountBeginDate() {
        return calendar.get( Calendar.YEAR ) + "-" + ( calendar.get( Calendar.MONTH ) + 1 ) + "-1";
    }
    // 账期截止日
    public String getAccountEndDate() {
        return calendar.get( Calendar.YEAR ) + "-" + ( calendar.get( Calendar.MONTH ) + 1 ) + "-" + calendar.get( Calendar.DAY_OF_MONTH );
    }
    
    // 账单月份
    public String getAccountMonth() {
        return calendar.get( Calendar.YEAR ) + "-" + ( calendar.get( Calendar.MONTH ) + 1 );
    }
    
    public Set<String> getBookingNoteHeader() {
        return bookingNoteHeader;
    }
    
    // bookingnote 业务
    public List<List<String>> getBookingNoteData() {
        return bookingNoteData;
    }

    public List<String> getColumns() {
        if( columns == null || columns.size() == 0 ) {
            columns = DEFAULT_SHOW_COLUMNS;
        }
        return columns;
    }

    public void setColumns( List<String> columns ) {
        this.columns = columns;
    }
    
    public String getFilename() {
        try {
            return new String(filename.getBytes("GBK"), "ISO8859-1");    // 修复下载文件名为乱码的bug
        } catch( UnsupportedEncodingException e ) {
            return filename;
        }
    }
    
    public void setFilename( String fileame ) {
        this.filename = fileame;
    }
    
    // 2013-10-12 添加按类型分组的功能
    public Map<String, Float> getEmptyTotalCharge() {
        Map<String, Float> total = new HashMap<String, Float>();
        for( Set<String> set : chargeHeader.values() ) {
            for( String name : set ) {
                total.put( name, new Float( 0 ) );
            }
        }
        return total;
    }
    
    
}
