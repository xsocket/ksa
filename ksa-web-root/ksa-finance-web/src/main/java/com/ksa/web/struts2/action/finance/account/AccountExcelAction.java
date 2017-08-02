package com.ksa.web.struts2.action.finance.account;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.finance.AccountService;


public class AccountExcelAction extends AccountAction {

    private static final long serialVersionUID = -2968592667293828024L;

    private static final Logger logger = LoggerFactory.getLogger( AccountExcelAction.class );
    
    protected static final Map<String, Integer> TYPE_SORTED_MAP;
    static {
        TYPE_SORTED_MAP = new HashMap<String, Integer>();
        TYPE_SORTED_MAP.put( BookingNote.TYPE_SEA_EXPORT, 0 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_SEA_IMPORT, 1 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_AIR_EXPORT, 2 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_AIR_IMPORT, 3 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_NATIVE, 4 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_KB, 5 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_CC, 6 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_BC, 7 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_RH, 8 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_TL, 9 );
        TYPE_SORTED_MAP.put( BookingNote.TYPE_ZJ, 10 );
    }
    
    protected List<BookingNote> bookingNotes;
    protected List<CurrencyRate> rates;

    // key : currency.name, value : type's set
    protected Map<String, Set<String>> chargeHeader = new LinkedHashMap<String, Set<String>>();
    protected Map<String, Map<String, Float>> chargeData = new LinkedHashMap<String, Map<String, Float>>();
    protected Map<String, Float> chargeRate = new LinkedHashMap<String, Float>();
    protected Float totalSum = 0f;    

    @SuppressWarnings( "unchecked" )
    @Override
    protected String doExecute() throws Exception {
        AccountService service = ServiceContextUtils.getService( AccountService.class );
        account = service.loadAccountById( account.getId() );
        bookingNotes = service.loadAccountBookingNotes( account );
        
        // 对业务进行排序
        Collections.sort( bookingNotes, new Comparator<BookingNote>() {
            
            @Override
            public int compare( BookingNote b1, BookingNote b2 ) {
                String t1 = b1.getType();
                String t2 = b2.getType();
                if( t1.equals( t2 ) ) {
                    return b1.getSerialNumber() - b2.getSerialNumber();
                } else {
                    return TYPE_SORTED_MAP.get( t1 ).compareTo( TYPE_SORTED_MAP.get( t2 ) );
                }
            }
        } );
        
        rates = (List<CurrencyRate>) service.loadAccountCurrencyRates( account );
        
        List<Charge> charges = account.getCharges();
        Collections.sort( charges, new Comparator<Charge>() {
            @Override
            public int compare( Charge c1, Charge c2 ) {    // 按币种排序
                int rank1 = c1.getCurrency().getRank();
                int rank2 = c2.getCurrency().getRank();
                if( rank1 > rank2 ) {
                    return 1;
                } else if( rank1 < rank2 ) {
                    return -1;
                } 
                return 0;
            }
        } );
        
        // 处理汇率map
        for( CurrencyRate rate : rates ) {
            String name = rate.getCurrency().getName();
            chargeRate.put( name, rate.getRate() );
        }
        
        for( Charge c : account.getCharges() ) {
            String name = c.getCurrency().getName();
            if( chargeHeader.containsKey( name ) ) {
                chargeHeader.get( name ).add( getDataFieldId( name, c.getType() ) );
            } else {
                Set<String> chargeTypes = new LinkedHashSet<String>();
                chargeTypes.add( getDataFieldId( name, c.getType() ) );
                chargeHeader.put( name, chargeTypes );
            }
        }
        
        for( String name : chargeHeader.keySet() ) {
            chargeHeader.get( name ).add(  getAggregateFieldId( name ) );
        }
        
        // 准备数据
        for(BookingNote bn : bookingNotes ) {
            Map<String, Float> data = new LinkedHashMap<String, Float>();
            chargeData.put( bn.getId(), data );      
            for( String name : chargeHeader.keySet() ) {
                data.put( getAggregateFieldId( name ), new Float( 0 ) );
            }
        }

        Map<String, Float> total = new LinkedHashMap<String, Float>();
        chargeData.put( "TOTAL", total );    // 加入最后的统计行
        for( Set<String> set : chargeHeader.values() ) {
            for( String name : set ) {
                total.put( name, new Float( 0 ) );
            }
        }
        
        for( Charge c : account.getCharges() ) {
            String name = c.getCurrency().getName();
            Map<String, Float> data = chargeData.get( c.getBookingNote().getId() );
            String fieldId = getDataFieldId( name, c.getType() );
            data.put( fieldId, c.getAmount() );
            String sumField = getAggregateFieldId( name );
            data.put( sumField, data.get( sumField ) + c.getAmount() );
            
            total.put( fieldId, c.getAmount() + total.get( fieldId ) );
            total.put( sumField, c.getAmount() + total.get( sumField ) );
            totalSum += c.getAmount() * chargeRate.get( name ); // 按汇率折算
        }
        
        return SUCCESS;
    }
    
    private String getAggregateFieldId( String currencyName ) {
        return currencyName + "-小计";
    }
    private String getDataFieldId( String currencyName, String chargeType ) {
        return currencyName + "-" + chargeType;
    }
    
    public String getJsonBookingNotes() {
        try {
            return JSONUtil.serialize( bookingNotes, null, null, false, true );
        } catch( JSONException e ) {
            logger.warn( "序列化数据列表发生异常！", e );
            return "[]";
        }
    }
    
    public List<BookingNote> getBookingNotes() {
        return bookingNotes;
    }    
    
    public Map<String, Set<String>> getChargeHeader() {
        return chargeHeader;
    }
    
    public Map<String, Map<String, Float>> getChargeData() {
        return chargeData;
    }
    
    public Map<String, Float> getChargeRate() {
        return chargeRate;
    }
    
    public Float getTotal() {
        return totalSum;
    }

    public static class Pair<K, V> implements Serializable {

        private static final long serialVersionUID = 5310839690899689547L;
        
        public Pair( K key, V value ) {
            this.key = key;
            this.value = value;
        }
        
        protected K key;
        protected V value;
        
        public K getKey() {
            return key;
        }
        
        public void setKey( K key ) {
            this.key = key;
        }
        
        public V getValue() {
            return value;
        }
        
        public void setValue( V value ) {
            this.value = value;
        }
        
        @Override
        public boolean equals( Object obj ) {
            if( obj == null || ! (obj instanceof Pair ) ) {
                return false;
            }
            Pair<?, ?> temp = ( Pair<?, ?> ) obj;
            return key.equals( temp.getKey() ) && value.equals( temp.getValue() );
        }                
        
        @Override
        public int hashCode() {
            return 7 * key.hashCode() + 23 * value.hashCode();
        }
        
    }
}
