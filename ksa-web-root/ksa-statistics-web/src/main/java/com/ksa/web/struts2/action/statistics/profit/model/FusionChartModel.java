package com.ksa.web.struts2.action.statistics.profit.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ksa.model.bd.Currency;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.finance.BookingNoteProfit;
import com.ksa.model.finance.Charge;
import com.ksa.service.bd.CurrencyRateService;


public abstract class FusionChartModel {
    
    protected String labelX;
    protected String title;
    protected Collection<Category> categories;
    protected Collection<DataSet> datasets;   // 按币种分类

    public void init( List<?> dataList, CurrencyRateService rateService ) {
        categories = new ArrayList<Category>();
        datasets = new ArrayList<DataSet>();
        if( dataList != null && !dataList.isEmpty() ) {
            doInit( dataList, rateService );
        }
    }
    
    protected abstract String getCategoryValue( BookingNoteProfit bn );

    protected void doInit( List<?> dataList, CurrencyRateService rateService ) {
        Set<String> categoryNames = new HashSet<String>();
        Map<String, DataSet> setMap = new HashMap<String, DataSet>();
        for( Object obj : dataList ) {
            if( !( obj instanceof BookingNoteProfit ) ) { return; }
            BookingNoteProfit bn = (BookingNoteProfit) obj;
            String category = getCategoryValue( bn ); 
            categoryNames.add( category );
            Map<String, Float> values = getChargeGatherByRMB( bn, rateService );    // 按人民币统计汇率
            for( String currency : values.keySet() ) {
                if( setMap.containsKey( currency ) ) {
                    DataSet set = setMap.get( currency );
                    DataValue v = null;
                    if( set.containsKey( category ) ) {
                        v = set.get( category );
                    } else {
                        v = new DataValue();
                        v.setLabel( category );
                        v.setValue( "0" );
                        set.put( category, v );
                    }
                    Float f = Float.parseFloat( v.getValue() ) + values.get( currency );
                    v.setValue( f.toString() );
                } else {
                    DataSet set = new DataSet( currency );
                    DataValue v = new DataValue();
                    v.setLabel( category );
                    v.setValue( values.get( currency ).toString() );
                    set.put( category, v );
                    setMap.put( currency, set );
                }
            }
        }
        for( String label : categoryNames ) {
            categories.add( new Category( label ) );
        } 
        
        datasets = setMap.values();
    }
    
    /**
     * 按人民币统计利润
     * @param bn
     * @return
     */
    protected Map<String, Float> getChargeGatherByRMB( BookingNoteProfit bn, CurrencyRateService rateService  ) {
        // key: 货币Id, value: 利率
        Map<String, Float> rateMap = prepareCurrencyRate( rateService );
        float profit = 0.0f;
        if( bn.getCharges() != null && ! bn.getCharges().isEmpty() ) {
            for( Charge c : bn.getCharges() ) {
                String currencyId = c.getCurrency().getId();
                if( Currency.RMB.getId().equals( currencyId ) ) {
                    profit += c.getDirection() * c.getAmount();
                } else {
                    Float rate = rateMap.get( currencyId );
                    if( rate == null ) continue;
                    profit += c.getDirection() * c.getAmount() * rate;
                }
            }
        }
        // key: 货币名, value: 利润
        Map<String, Float> map = new HashMap<String, Float>();
        map.put( Currency.RMB.getName(), profit );
        return map;
    }
    
    // key: 货币Id, value: 利率
    protected Map<String, Float> prepareCurrencyRate( CurrencyRateService rateService  ) {
        List<CurrencyRate> rates = rateService.loadLatestCurrencyRates();
        Map<String, Float> rateMap = new HashMap<String, Float>();
        for( CurrencyRate rate : rates ) {
            rateMap.put( rate.getCurrency().getId(), rate.getRate() );
        }
        return rateMap;
    }
    
    protected Map<String, Float> getChargeGather( BookingNoteProfit bn ) {
        // key: 货币名, value: 利润
        Map<String, Float> map = new HashMap<String, Float>();
        if( bn.getCharges() != null && ! bn.getCharges().isEmpty() ) {
            for( Charge c : bn.getCharges() ) {
                String currency = c.getCurrency().getName();
                if( map.containsKey( currency ) ) {
                    map.put( currency, map.get( currency ) + new Float( c.getDirection() * c.getAmount() ) );   // 计算利润
                } else {
                    map.put( currency, new Float( c.getDirection() * c.getAmount() ) );
                }
            }
        }
        return map;
    }
    
    public String getTitle() {
        return title;
    }

    
    public void setTitle( String title ) {
        this.title = title;
    }
    
    public String getLabelX() {
        return labelX;
    }
    
    public void setLabelX( String labelX ) {
        this.labelX = labelX;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    
    public void setCategories( Collection<Category> categories ) {
        this.categories = categories;
    }

    
    public Collection<DataSet> getDatasets() {
        return datasets;
    }

    
    public void setDatasets( Collection<DataSet> datasets ) {
        this.datasets = datasets;
    }

    
    
}
