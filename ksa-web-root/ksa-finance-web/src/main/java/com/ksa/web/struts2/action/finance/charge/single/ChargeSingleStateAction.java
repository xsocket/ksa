package com.ksa.web.struts2.action.finance.charge.single;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.finance.BookingNoteChargeState;
import com.ksa.service.finance.ChargeService;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

/**
 * 修改业务费用状态的操作。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class ChargeSingleStateAction extends ChargeSingleEditAction implements JsonAction {
    
    private static final long serialVersionUID = -3776802985971150958L;
    
    private JsonResult result;
    
    @Override
    public String execute() throws Exception {
        int state = bookingNote.getState();
        if( BookingNoteChargeState.isChecked( state ) ) {
            if( ! SecurityUtils.isPermitted( "finance:charge-check" ) ) {
                result = new JsonResult( ERROR, "对不起，您没有权限进行费用审核操作！" );
                return ERROR;
            }
        }
        return super.execute();
    }

    @Override
    public String doExecute() throws Exception {
        ChargeService service = ServiceContextUtils.getService( ChargeService.class );
        
        bookingNote = service.updateBookingNoteChargeState( bookingNote, direction, nature );
        String message = String.format( "成功更新业务 '%s' 的费用状态。", bookingNote.getCode() );
        addActionMessage( message );
        result = new JsonResult( SUCCESS, message );
        return SUCCESS;
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next() );
        }
        return this.result;
    }
}
