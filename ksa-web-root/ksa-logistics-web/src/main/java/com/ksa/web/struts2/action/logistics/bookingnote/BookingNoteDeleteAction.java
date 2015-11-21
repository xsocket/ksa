package com.ksa.web.struts2.action.logistics.bookingnote;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class BookingNoteDeleteAction extends BookingNoteAction implements JsonAction {

    private static final long serialVersionUID = -5617398177214186014L;
    
    private JsonResult result;
    
    @Override
    public String execute() throws Exception {
        if( ! SecurityUtils.isPermitted( "bookingnote:delete" ) ) {
            result = new JsonResult( ERROR, "对不起，您没有权限进行托单删除操作！" );
            return ERROR;
        }
        return super.execute();
    }

    @Override
    public String doExecute() throws Exception {
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        bookingNote = service.removeBookingNote( bookingNote );
        String message = String.format( "成功删除托单：'%s'。", bookingNote.getCode() );
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message, bookingNote );
        return SUCCESS;
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next(), bookingNote );
        }
        return this.result;
    }
}
