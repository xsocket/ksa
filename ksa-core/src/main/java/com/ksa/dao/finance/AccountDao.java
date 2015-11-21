package com.ksa.dao.finance;

import java.util.List;

import com.ksa.model.finance.Account;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;

/**
 * 费用结算对账单数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface AccountDao {
    int insertAccount( Account account ) throws RuntimeException;
    int insertAccountCharges( Account account, List<Charge> charges ) throws RuntimeException;
    int deleteAccountCharges( Account account, List<Charge> charges ) throws RuntimeException;
    int updateAccount( Account account ) throws RuntimeException;
    int updateAccountState( Account account ) throws RuntimeException;
    int deleteAccount( Account account ) throws RuntimeException;    
    int querySimilarAccountCodeCount( String code ) throws RuntimeException;
    
    Account selectAccountById( String id ) throws RuntimeException;
    /** 获取结算单相关的业务托单信息 */
    List<BookingNote> selectBookingNoteByAccountId( String accountId ) throws RuntimeException;
}
