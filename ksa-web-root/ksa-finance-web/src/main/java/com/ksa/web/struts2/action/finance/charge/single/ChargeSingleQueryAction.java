package com.ksa.web.struts2.action.finance.charge.single;

import java.util.ArrayList;
import java.util.List;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.finance.BookingNoteCharge;
import com.ksa.model.finance.Charge;
import com.ksa.model.finance.FinanceModel;
import com.ksa.service.finance.ChargeService;
import com.ksa.web.struts2.action.logistics.bookingnote.BookingNoteQueryAction;

public class ChargeSingleQueryAction extends BookingNoteQueryAction {

    private static final long serialVersionUID = 7210295997960367238L;
    
    protected int nature;
    protected int direction;
    // true：费用列表为空时也显示；false：费用列表为空时不显示
    protected boolean showEmpty = true;

    @Override
    public String execute() throws Exception {
        String result = super.execute();
        ChargeService service = ServiceContextUtils.getService( ChargeService.class );
        if( showEmpty ) {   // 不论有没费用 均显示
            for( Object obj : gridDataArray ) {
                BookingNoteCharge bn = ( BookingNoteCharge ) obj;
                bn.setCharges( service.loadBookingNoteCharges( bn.getId(), direction, nature ) );
            }
        } else {
            List<BookingNoteCharge> bnList = new ArrayList<BookingNoteCharge>( gridDataArray.length );
            for( Object obj : gridDataArray ) {
                BookingNoteCharge bn = ( BookingNoteCharge ) obj;
                List<Charge> charges = service.loadBookingNoteCharges( bn.getId(), direction, nature );
                if( charges != null && charges.size() > 0 ) {
                    bn.setCharges( charges );
                    bnList.add( bn );
                }
            }
            gridDataArray = bnList.toArray();
        }
        return result;
    }
    
    @Override
    protected String getQueryCountStatement() {
        return "count-finance-charge-fetch";
    }
    
    @Override
    protected String getQueryDataStatement() {
        return "grid-finance-charge-fetch";
    }
    

    public int getNature() {
        return nature;
    }

    public void setNature( int nature ) {
        if( nature >= 0 ) {
            this.nature = FinanceModel.NATIVE;
        } else {
            this.nature = FinanceModel.FOREIGN;
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection( int direction ) {
        if( direction >= 0 ) {
            this.direction = FinanceModel.INCOME;
        } else {
            this.direction = FinanceModel.EXPENSE;
        }
    }
    
    public boolean isShowEmpty() {
        return showEmpty;
    }
    
    public boolean getShowEmpty() {
        return showEmpty;
    }

    public void setShowEmpty( boolean showEmpty ) {
        this.showEmpty = showEmpty;
    }
    
}
