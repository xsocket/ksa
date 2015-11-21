package com.ksa.service.finance;

import java.util.List;

import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.finance.Account;
import com.ksa.model.finance.AccountCurrencyRate;
import com.ksa.model.logistics.BookingNote;

/**
 * 结算单管理服务接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface AccountService {
    
    int querySimilarAccountCodeCount( String partnerCode ) throws RuntimeException;

    /**
     * 根据标识获取结算单数据。
     * @param accountId 结算单标识
     * @return 标识为 accountId 的结算单
     * @throws RuntimeException
     */
    Account loadAccountById( String accountId ) throws RuntimeException;
    
    /***
     * 保存结算单。
     * @param account 需要保存的结算单对象
     * @return
     * @throws RuntimeException
     */
    Account saveAccount( Account account, List<AccountCurrencyRate> rates ) throws RuntimeException;
    
    /***
     * 更新结算单的状态
     * @param account 需要更新的结算单，且已经设置好了状态值
     * @return
     * @throws RuntimeException
     */
    Account updateAccountState( Account account ) throws RuntimeException;
    
    Account removeInvoice( Account account ) throws RuntimeException;
    
    /**
     * 获取结算单对应货币汇率列表
     * @param account 结算单
     * @return
     * @throws RuntimeException
     */
    List<? extends CurrencyRate> loadAccountCurrencyRates( Account account ) throws RuntimeException;
    
    /**
     * 获取结算单对应的业务托单信息
     * @param account 结算单
     * @return
     * @throws RuntimeException
     */
    List<BookingNote> loadAccountBookingNotes( Account account ) throws RuntimeException;
}
