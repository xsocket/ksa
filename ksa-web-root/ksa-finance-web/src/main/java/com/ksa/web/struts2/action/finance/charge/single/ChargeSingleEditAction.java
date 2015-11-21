package com.ksa.web.struts2.action.finance.charge.single;

import java.util.Date;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.logistics.BookingNoteState;
import com.ksa.model.security.User;
import com.ksa.service.finance.ChargeService;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;
import com.opensymphony.xwork2.ModelDriven;


public class ChargeSingleEditAction extends ChargeSingleAction implements ModelDriven<BookingNote> {

    private static final long serialVersionUID = -2776151163922960571L;

    @Override
    protected String doExecute() throws Exception {
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        bookingNote = service.loadBookingNoteById( bookingNote.getId() );
        
        if( StringUtils.hasText( template ) ) {
            //  以模板的方式添加
            ChargeService chargeService = ServiceContextUtils.getService( ChargeService.class );
            this.charges = chargeService.loadBookingNoteCharges( template, direction, nature );
            User currentUser = SecurityUtils.getCurrentUser();
            for( Charge charge : charges ) {
                charge.setId( "" );
                charge.setCreatedDate( new Date() );
                charge.setCreator( currentUser );
            }
            
        } else if( ! BookingNoteState.isNone( bookingNote.getState() ) ) {
            // 如果 BookingNote 已经存在状态（说明已经录入了费用） 则读取费用列表
            ChargeService chargeService = ServiceContextUtils.getService( ChargeService.class );
            this.charges = chargeService.loadBookingNoteCharges( bookingNote.getId(), direction, nature );
        }
        bookingNote.setCreator( SecurityUtils.getCurrentUser() );
        
        if( bookingNote.getChargeDate() == null ) {
            bookingNote.setChargeDate( new Date() );
        }
        return SUCCESS;
    }
    
}
