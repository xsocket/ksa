package com.ksa.web.struts2.action.logistics;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 业务信息展示跳转的操作。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class DefaultAction extends DefaultActionSupport implements ModelDriven<BookingNote> {

    private static final long serialVersionUID = 1100896624633259516L;
    
    protected String template;

    protected BookingNote bookingNote = new BookingNote();
    
    @Override
    public String execute() throws Exception {
        if( ! SecurityUtils.isPermitted( "bookingnote:edit:view" ) ) {
            return NO_PERMISSION;
        }
        return super.execute();
    }
    
    @Override
    public String doExecute() throws Exception {
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        
        if( StringUtils.hasText( template ) ) { // 按照 template 来新建
            BookingNote newBn = service.getNewBookingNote( getModel().getType() );
            bookingNote = service.loadBookingNoteById( template );
            bookingNote.setId( "" );
            bookingNote.setSerialNumber( newBn.getSerialNumber() );
            bookingNote.setCode( newBn.getCode() );
            bookingNote.setState( newBn.getState() );
            bookingNote.setType( newBn.getType() );
            bookingNote.setCreatedDate( new Date() );
        } else if( ! StringUtils.hasText( getModel().getId() ) && StringUtils.hasText( getModel().getType() ) ) {
            bookingNote = service.getNewBookingNote( getModel().getType() );
        }
        bookingNote.setCreator( SecurityUtils.getCurrentUser() );
        return SUCCESS;
    }

    @Override
    public BookingNote getModel() {
        return bookingNote;
    }
    
    public String getTemplate() {
        return template;
    }
    
    public void setTemplate( String template ) {
        this.template = template;
    }
}
