package com.ksa.model.finance;

import java.util.Date;
import java.util.List;

import com.ksa.model.bd.Partner;
import com.ksa.model.finance.Account;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.security.User;

public class BookingNoteProfit extends BookingNote {

    private static final long serialVersionUID = -3114022945617180552L;

    /** 结账单所含全部费用 */
    protected List<ProfitCharge> charges;

    public List<ProfitCharge> getCharges() {
        return charges;
    }

    public void setCharges( List<ProfitCharge> charges ) {
        this.charges = charges;
    }
    
    // ---------------- 隐藏不必要的属性
    
    @Override
    public Partner getNotify() {
        return null;
    }
    
    @Override
    public Partner getShippingAgent() {
        return null;
    }
    
    @Override
    public Partner getVehicleTeam() {
        return null;
    }
    
    public static class ProfitCharge extends Charge {
        
        // --------- 隐藏不必要的属性

        private static final long serialVersionUID = -4086743282835712532L;
        
        public  String getAccountId() {
            return super.account.getId();
        }
        
        public void setAccountId( String accountId ) {
            super.account.setId( accountId ); 
        }

        @Override
        public Date getCreatedDate() {
            return null;
        }
        
        @Override
        public Account getAccount() {
            return null;
        }
        
        @Override
        public BookingNote getBookingNote() {
            return null;
        }
        
        @Override
        public User getCreator() {
            return null;
        }
        
        @Override
        public Partner getTarget() {
            return null;
        }
    }

}
