package com.ksa.web.struts2.action.logistics.bookingnote;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class BookingNoteAction extends DefaultActionSupport implements ModelDriven<BookingNote> {

    private static final long serialVersionUID = 7352423474760850278L;
    
    protected BookingNote bookingNote = new BookingNote();
    
    @Override
    public String doExecute() throws Exception {
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        bookingNote = service.loadBookingNoteById( bookingNote.getId() );
        return SUCCESS;
    }

    @Override
    public BookingNote getModel() {
        return bookingNote;
    }
}
