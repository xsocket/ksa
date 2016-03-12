package com.ksa.web.struts2.action.finance.business;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.bd.Currency;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.business.RecordBillCharge;
import com.ksa.model.business.RecordBillChargeGather;
import com.ksa.model.business.RecordBillProfit;
import com.ksa.model.finance.Charge;
import com.ksa.model.finance.FinanceModel;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.bd.CurrencyRateService;
import com.ksa.service.finance.ChargeService;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class RecordBillAction extends DefaultActionSupport implements ModelDriven<BookingNote> {

    private static final long serialVersionUID = -7802092150934083971L;

    private static int INIT_ARRAY_LENGTH = 20;
    
    protected BookingNote bookingNote = new BookingNote();
    protected RecordBillCharge[] charge;
    protected int chargeExtraCount = 0;
    protected RecordBillProfit total = new RecordBillProfit();
    protected Map<String, RecordBillChargeGather> gatherMap = new HashMap<String, RecordBillChargeGather>();
    
    @Override
    protected String doExecute() throws Exception {
        // 首先获取托单对象 和 托单的费用清单
        BookingNoteService noteService = ServiceContextUtils.getService( BookingNoteService.class );
        ChargeService chargeService = ServiceContextUtils.getService( ChargeService.class );
        CurrencyRateService rateService = ServiceContextUtils.getService( CurrencyRateService.class );
        
        bookingNote = noteService.loadBookingNoteById( bookingNote.getId() );
        List<Charge> noteCharges = chargeService.loadBookingNoteCharges( bookingNote.getId() );
        
        if( noteCharges != null && noteCharges.size() > 0 ) {
            Map<String, Charge> incomeMap = new LinkedHashMap<String, Charge>();
            Map<String, Charge> expenseMap = new LinkedHashMap<String, Charge>();
            Set<String> chargeTypeSet = new LinkedHashSet<String>();
            for( Charge c : noteCharges ) {
                String key = c.getType();
                if( !chargeTypeSet.contains( key ) ) {
                    chargeTypeSet.add( key );
                }
                Map<String, Charge> map = c.getDirection() == FinanceModel.INCOME ? incomeMap : expenseMap;
                if( !map.containsKey( key ) ) {
                    map.put( key, c );
                } else {
                    // 已经存在同类型的费用，则费用总和相加
                    Charge mappedCharge = map.get( key );
                    mappedCharge.setAmount( c.getAmount() + mappedCharge.getAmount() );
                    // FIXME 同费用类型的单价和数量没有处理
                }
            }
            
            // 生成最后的费用清单
            initCharge( chargeTypeSet.size() > INIT_ARRAY_LENGTH ? chargeTypeSet.size() : INIT_ARRAY_LENGTH );
            int index = 0;
            for( String type : chargeTypeSet ) {
                RecordBillCharge rbCharge = charge[index];
                if( incomeMap.containsKey( type ) ) {
                    Charge income = incomeMap.get( type );
                    Currency incomeCurrency = income.getCurrency();
                    
                    rbCharge.setCustomer( income.getTarget().getName() );
                    rbCharge.setIncome( income.getPrice() );
                    rbCharge.setIncomeAmount( income.getQuantity() );
                    rbCharge.setIncomeCurrency( incomeCurrency );
                    rbCharge.setIncomeTotal( computeMoneyAndAggregateProfit( income ) );
                    rbCharge.setType( income.getType() );
                    
                    // 计算币种汇总
                    RecordBillChargeGather gather = computeGather( incomeCurrency, rateService );
                    if( gather != null ) {
                        gather.addIncome( income.getAmount() );
                    }
                    
                }
                if( expenseMap.containsKey( type ) ) {
                    Charge expense = expenseMap.get( type );
                    Currency expenseCurrency = expense.getCurrency();
                    
                    rbCharge.setAgent( expense.getTarget().getName() );
                    rbCharge.setExpense( expense.getPrice() );
                    rbCharge.setExpenseAmount( expense.getQuantity() );
                    rbCharge.setExpenseCurrency( expense.getCurrency() );
                    rbCharge.setExpenseTotal( computeMoneyAndAggregateProfit( expense ) );
                    rbCharge.setType( expense.getType() );
                    
                    // 计算币种汇总
                    RecordBillChargeGather gather = computeGather( expenseCurrency, rateService );
                    if( gather != null ) {
                        gather.addExpense( expense.getAmount() );
                    }
                }
                
                index++;
            }
            
            total.setGathers( gatherMap.values() );
            
            /*total.setIncome( total.getIncomeRMB() + total.getIncomeUSD() * total.getRate() + total.getIncomeOTHER() );
            total.setExpense( total.getExpenseRMB() + total.getExpenseUSD() * total.getRate() + total.getExpenseOTHER() );
            total.setProfit( total.getIncome() - total.getExpense() );
            if( total.getExpense() > 0.1f ) {
                total.setProfitRate( total.getProfit() / total.getIncome() );   // 由 利润/支出合计 改为 利润/收入合计
            }*/
            
        } else {
            initCharge( INIT_ARRAY_LENGTH );
        }
        
        // 将charge排序
      //  Arrays.sort( charge );
        
        return SUCCESS + bookingNote.getType();
    }
    
    private String computeMoneyAndAggregateProfit( Charge c ) {
        String money = Float.toString( c.getAmount() );
        Currency currency = c.getCurrency();
        if( Currency.RMB.getId().equals( currency.getId() ) ) {
            money = "￥" + money;
            if( c.getDirection() == FinanceModel.INCOME ) {
                total.setIncomeRMB( total.getIncomeRMB() + c.getAmount() );
            } else {
                total.setExpenseRMB( total.getExpenseRMB() + c.getAmount() );
            }
        } else if( Currency.USD.getId().equals( currency.getId() ) ) {
            money = "$" + money;
        } else {
            money = currency.getName() + " " + money;
        }
        return money;
    }

    private void initCharge( int length ) {
        charge = new RecordBillCharge[ length ];
        for( int i = 0; i < length; i++ ) {
            charge[i] = new RecordBillCharge();
        }
        if( length > INIT_ARRAY_LENGTH ) {
        	chargeExtraCount = length - INIT_ARRAY_LENGTH;
        }
    }
    
    @Override
    public BookingNote getModel() {
        return bookingNote;
    }
    
    public BookingNote getBookingNote() {
        return bookingNote;
    }
    
    public RecordBillCharge[] getCharge() {
        return charge;
    }
    
    public int getChargeExtraCount() {
    	return chargeExtraCount;
    }
    
    public RecordBillProfit getTotal() {
        return total;
    }
    
    public String getFilename() {
        String filename = "作业记录书";
        if( bookingNote != null ) {
            filename = filename + " - " + bookingNote.getCode();
        }
        filename += ".xls";
        try {
            return new String(filename.getBytes("GBK"), "ISO8859-1");    // 修复下载文件名为乱码的bug
        } catch( UnsupportedEncodingException e ) {
            return filename;
        }
    }
    
    private RecordBillChargeGather computeGather( Currency currency, CurrencyRateService rateService ) {
        if( currency.getId().equals( Currency.RMB.getId() ) ) {
            return null;
        }
        if( gatherMap.containsKey( currency.getId() ) ) {
            return gatherMap.get( currency.getId() );
        } else {
            RecordBillChargeGather gather = new RecordBillChargeGather();
            CurrencyRate currencyRate = rateService.loadLatestCurrencyRate( currency.getId() );
            gather.setCurrency( currency );
            gather.setRate( currencyRate.getRate() );
            gatherMap.put( currency.getId(), gather );
            return gather;
        }
    }
    
    // 计算除人民币外第二类币种，如果有美元则按美元计算，
    // 如果没有美元，且除人民币外只有另外一种外币，则选择此外币。
    /*private Currency computeSecondaryCurrency( List<Charge> charges ) {
        Map<String, Currency> otherCurrencyMap = new HashMap<String, Currency>();
        for( Charge charge : charges ) {
            String cId = charge.getCurrency().getId();
            if( Currency.RMB.getId().endsWith( cId ) ) {
                continue;
            } else if( Currency.USD.getId().endsWith( cId ) ) {
                return Currency.USD;    // 有美元则按美元计算
            } else {
                // 第三类货币
                otherCurrencyMap.put( cId, charge.getCurrency() );
            }
        }
        
        if( otherCurrencyMap.size() == 1 ) {    // 没有美元，且只有一种第三类外币
            return otherCurrencyMap.values().iterator().next();
        } else {    // 多种外币，则还是选用美元
            return Currency.USD;
        }
    }*/
    
}
