package com.ksa.web.struts2.action.logistics;

import org.springframework.util.StringUtils;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.AbstractLogisticsModelDao;
import com.ksa.model.logistics.BaseLogisticsModel;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.web.struts2.action.DefaultActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public abstract class LogisticsModelAction<T extends BaseLogisticsModel>  extends DefaultActionSupport implements ModelDriven<T> { 

    private static final long serialVersionUID = 7107088449843372595L;

    protected T model;
    
    @Override
    protected String doExecute() throws Exception {            
        T temp = getDao().selectLogisticsModelByBookingNoteId( getModel().getBookingNote().getId() );
        if( temp != null ) {
            model = temp;
            return SUCCESS;
        }
        
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        BookingNote note = service.loadBookingNoteById( getModel().getBookingNote().getId() );
        getModel().initialModel( note );
        postInitalModel( note );
        return SUCCESS;
    }
    
    protected abstract AbstractLogisticsModelDao<T> getDao();
    
    protected void postInitalModel( BookingNote note ) {
        
    }
    
    @Override
    public void validate() {
        if( !StringUtils.hasText( model.getBookingNote().getId() ) ) {
            this.addActionError( "请输入业务托单标识。" );
        }
    }
}

