package com.ksa.web.struts2.action.finance.business;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.business.DebitNoteCharge;
import com.ksa.model.finance.Charge;
import com.ksa.model.finance.FinanceModel;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.finance.ChargeService;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DebitNoteAction extends DefaultActionSupport implements ModelDriven<BookingNote> {

    private static final long serialVersionUID = 8576498803387130735L;
    
    private static int INIT_ARRAY_LENGTH = 12;
    
    protected BookingNote bookingNote = new BookingNote();
    protected DebitNoteCharge[] charge;
    protected Float total = 0f;
    
    @Override
    protected String doExecute() throws Exception {
        // 首先获取托单对象 和 托单的费用清单
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        bookingNote = service.loadBookingNoteById( bookingNote.getId() );
        
        ChargeService chargeService = ServiceContextUtils.getService( ChargeService.class );
        List<Charge> foreignIncomes = new ArrayList<Charge>();
        List<Charge> foreignExpense = null; //new ArrayList<Charge>();
        
        // DEBIT NOTE 仅针对收入费用
        /*List<Charge> noteCharges = */chargeService.loadBookingNoteCharges( bookingNote.getId(), foreignIncomes, foreignExpense, FinanceModel.FOREIGN );
        /*CurrencyRateService rateService = ServiceContextUtils.getService( CurrencyRateService.class );
        CurrencyRate usdCurrencyRate = rateService.loadLatestCurrencyRate( Currency.USD.getId() );
        total.setRate( usdCurrencyRate.getRate() );*/
        
        if( foreignIncomes.size() > 0 ) {
            initCharge( foreignIncomes.size() > INIT_ARRAY_LENGTH ? foreignIncomes.size() : INIT_ARRAY_LENGTH );
            for( int i = 0; i < foreignIncomes.size(); i++ ) {
                charge[i] = new DebitNoteCharge( foreignIncomes.get( i ) );
                total += ( charge[i].getAmount() != null ? charge[i].getAmount() : 0f );
            }
        } else {
            initCharge( INIT_ARRAY_LENGTH );
        }
        
        return SUCCESS;
    }
    
    private void initCharge( int length ) {
        charge = new DebitNoteCharge[ length ];
        for( int i = 0; i < length; i++ ) {
            charge[i] = new DebitNoteCharge();
        }
    }
    
    public DebitNoteCharge[] getCharge() {
        return charge;
    }
    
    public void setCharge( DebitNoteCharge[] charge ) {
        this.charge = charge;
    }
    
    public Float getTotal() {
        return total;
    }

    public void setTotal( Float total ) {
        this.total = total;
    }

    @Override
    public BookingNote getModel() {
        return bookingNote;
    }
    
    public BookingNote getBookingNote() {
        return bookingNote;
    }
    
    public String getFilename() {
        String filename = "DEBIT NOTE";
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
    
}
