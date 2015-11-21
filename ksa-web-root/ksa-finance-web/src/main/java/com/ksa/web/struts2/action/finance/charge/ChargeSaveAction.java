package com.ksa.web.struts2.action.finance.charge;

import java.util.Date;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.finance.ChargeService;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.service.security.util.SecurityUtils;

public class ChargeSaveAction extends ChargeEditAction {

    private static final long serialVersionUID = -3776802985971150958L;

    @Override
    protected String doExecute() throws Exception {
        Date chargeDate = bookingNote.getChargeDate();
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        bookingNote = service.loadBookingNoteById( bookingNote.getId() );
        bookingNote.setChargeDate( chargeDate );
        ChargeService chargeService = ServiceContextUtils.getService( ChargeService.class );
        chargeService.saveBookingNoteCharges( bookingNote, incomes, expenses );
        
        // 这里利用 bookingNote.creator 属性显示当前用户信息。
        bookingNote.setCreator( SecurityUtils.getCurrentUser() );
        return SUCCESS;
    }
}
