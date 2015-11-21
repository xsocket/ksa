package com.ksa.web.struts2.action.finance.charge.single;

import java.util.Date;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.finance.ChargeService;
import com.ksa.service.logistics.BookingNoteService;

public class ChargeSingleSaveAction extends ChargeSingleEditAction {

    private static final long serialVersionUID = -3776802985971150958L;

    @Override
    protected String doExecute() throws Exception {
        Date chargeDate = bookingNote.getChargeDate();
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        bookingNote = service.loadBookingNoteById( bookingNote.getId() );
        bookingNote.setChargeDate( chargeDate );
        
        ChargeService chargeService = ServiceContextUtils.getService( ChargeService.class );
        charges = chargeService.saveBookingNoteCharges( bookingNote, charges, direction, nature );
        return SUCCESS;
    }
}
