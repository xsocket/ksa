package com.ksa.web.struts2.action.logistics.bookingnote;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.logistics.BookingNoteState;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.util.StringUtils;


public class BookingNoteSaveAction extends BookingNoteAction {

    private static final long serialVersionUID = 1912217534839054639L;
    
    protected boolean returned = false;
    
    @Override
    public String execute() throws Exception {
        if( ! SecurityUtils.isPermitted( "bookingnote:edit" ) ) {
            return NO_PERMISSION;
        }
        return super.execute();
    }

    @Override
    public String doExecute() throws Exception {        
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        if( returned ) {
            // 将托单的状态置为已完成退单
            bookingNote.setState( BookingNoteState.RETURNED );
        }
        if( StringUtils.hasText( bookingNote.getId() ) ) {
            // update
            service.modifyBookingNote( bookingNote );
            addActionMessage( String.format( "成功更新托单：'%s'。", bookingNote.getCode() ) );
        } else {
            // insert
            int serialNumber = bookingNote.getSerialNumber();
            service.createBookingNote( bookingNote );
            if( serialNumber == bookingNote.getSerialNumber() ) {
                addActionMessage( String.format( "成功创建托单：'%s'。", bookingNote.getCode() ) );
            } else {
                addActionMessage( String.format( "成功创建托单，但原托单编号已经被占用，更新托单编号为：'%s'。", bookingNote.getCode() ) );
            }
        }
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();

        if( !StringUtils.hasText( bookingNote.getCode() ) ) {
            this.addActionError( "请输入托单编号。" );
        }
        if( !StringUtils.hasText( bookingNote.getCustomer().getId() ) ) {
            this.addActionError( "请输入客户信息。" );
        }
        if( !StringUtils.hasText( bookingNote.getSaler().getId() ) ) {
            this.addActionError( "请输入销售担当。" );
        }
        if( !StringUtils.hasText( bookingNote.getCargoName() ) ) {
            this.addActionError( "请输入货物品名。" );
        }
        
        if( returned ) {
            if( !StringUtils.hasText( bookingNote.getReturnCode() ) ) {
                this.addActionError( "请输入退单号。" );
            }
            if( bookingNote.getReturnDate() == null ) {
                this.addActionError( "请输入退单日期。" );
            }
        }
    }
    
    public boolean getReturned() {
        return returned;
    }
    
    public boolean isReturned() {
        return returned;
    }

    public void setReturned( boolean returned ) {
        this.returned = returned;
    }
}
