package com.ksa.web.struts2.action.finance.charge;

import java.util.Date;
import java.util.List;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.finance.Account;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.security.User;
import com.ksa.service.bd.CurrencyRateService;
import com.ksa.service.finance.AccountService;
import com.ksa.service.finance.ChargeService;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;
import com.opensymphony.xwork2.ModelDriven;


public class ChargeEditAction extends ChargeAction implements ModelDriven<BookingNote> {

    private static final long serialVersionUID = -2776151163922960571L;

    @Override
    protected String doExecute() throws Exception {
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        bookingNote = service.loadBookingNoteById( bookingNote.getId() );
        if( StringUtils.hasText( template ) ) {
            //  以模板的方式添加
            ChargeService chargeService = ServiceContextUtils.getService( ChargeService.class );
            List<Charge> charges = chargeService.loadBookingNoteCharges( template, incomes, expenses );
            User currentUser = SecurityUtils.getCurrentUser();
            for( Charge charge : charges ) {
                charge.setId( "" );
                charge.setCreatedDate( new Date() );
                charge.setCreator( currentUser );
            }
            
        } else /* if( ! BookingNoteState.isNone( bookingNote.getState() ) ) */{
            // 如果 BookingNote 已经存在状态（说明已经录入了费用） 则读取费用列表
            ChargeService chargeService = ServiceContextUtils.getService( ChargeService.class );
            chargeService.loadBookingNoteCharges( bookingNote.getId(), incomes, expenses );            
        }
        // 这里利用 bookingNote.creator 属性显示当前用户信息。
        bookingNote.setCreator( SecurityUtils.getCurrentUser() );
        
        if( bookingNote.getChargeDate() == null ) {
            bookingNote.setChargeDate( new Date() );
        }
        return SUCCESS;
    }
    
    @Override
    public String getJsonRates() {
        if( rates == null ) {
            Account account = null;
            if( incomes != null && !incomes.isEmpty() ) {
                for( Charge income : incomes ) {
                    if( income.getAccountState() >= 0 ) {
                        account = income.getAccount();
                    }
                }
            }
            // 按结算单的汇率计算
            if( account != null ) {
                rates = ServiceContextUtils.getService( AccountService.class ).loadAccountCurrencyRates( account );
            } else {
                CurrencyRateService rateService = ServiceContextUtils.getService( CurrencyRateService.class );
                rates = rateService.loadLatestCurrencyRates(bookingNote.getChargeDate());
            }
        }
        return serializeList( rates );
    }
    
}
