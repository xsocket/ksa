package com.ksa.service.finance.impl;

import com.ksa.dao.finance.AccountDao;
import com.ksa.dao.finance.InvoiceDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.finance.Account;
import com.ksa.model.finance.AccountState;
import com.ksa.model.finance.FinanceModel;
import com.ksa.model.finance.Invoice;
import com.ksa.service.finance.InvoiceService;
import com.ksa.util.StringUtils;


public class InvoiceServiceImpl implements InvoiceService {
    
    private InvoiceDao invoiceDao;
    private AccountDao accountDao;


    @Override
    public Invoice loadInvoiceById( String id ) throws RuntimeException {
        Invoice temp = invoiceDao.selectInvoiceById( id );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的发票不存在。", id ) );
        }
        return temp;
    }

    @Override
    public Invoice saveInvoice( Invoice invoice ) throws RuntimeException {
        if( StringUtils.hasText( invoice.getId() ) ) {
            Invoice temp = invoiceDao.selectInvoiceById( invoice.getId() );
            if( temp == null ) {
                throw new IllegalArgumentException( String.format( "标识为 '%s' 的发票不存在。", invoice.getId() ) );
            }
            invoiceDao.updateInvoice( invoice );
        } else {
            invoice.setId( ModelUtils.generateRandomId() );
            invoiceDao.insertInvoice( invoice );
        }
        return invoice;
    }

    @Override
    public Invoice assignInvoiceToAccount( Invoice invoice, Account account ) throws RuntimeException {
        Invoice temp = invoiceDao.selectInvoiceById( invoice.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的发票不存在。", invoice.getId() ) );
        }
        
        // 如果发票已经对账过，判断是否可以变更
        if( temp.getAccount() != null && AccountState.isSettled( temp.getAccount().getState() ) ) {
            throw new IllegalStateException( String.format( "编号为 '%s' 的结算单已经结算完毕，不能进行销账。", temp.getCode() ) );
        }
        
        // 取消对账
        if( account == null || !StringUtils.hasText( account.getId() ) ) {
            temp.setAccount( new Account() );    // 保证 account.id 为空
            invoiceDao.updateInvoiceAccount( temp );
        } else {    // 对账
            String accountId = account.getId();
            if( ! accountId.equals( temp.getAccount().getId() ) ) {           
                account = accountDao.selectAccountById( accountId );
                if( account == null ) {
                    throw new IllegalArgumentException( String.format( "标识为 '%s' 的结算单不存在，无法进行销账。", accountId ) );
                }
                if( temp.getDirection() == account.getDirection() ) {   
                    // 收到的发票只能与对账单(支出) 进行销账
                    // 反之，开出的发票只能与结算单进行销账
                    if( FinanceModel.isIncome( temp ) ) {
                        throw new IllegalArgumentException( "销账数据错误，收到的发票只能与对账单进行销账！" );
                    } else {
                        throw new IllegalArgumentException( "销账数据错误，开出的发票只能与结算单进行销账！" );
                    }
                }
                
                temp.setAccount( account );
                invoiceDao.updateInvoiceAccount( temp );
            }
        }
        
        return temp;
    }

    @Override
    public Invoice removeInvoice( Invoice invoice ) throws RuntimeException {
        Invoice temp = invoiceDao.selectInvoiceById( invoice.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的发票不存在。", invoice.getId() ) );
        }
        
        // 当发票拿去销账的对应结算单已经确认结算完毕后，不能删除
        if( ModelUtils.isPersistentObject( temp.getAccount() ) && AccountState.isSettled( temp.getAccount().getState() ) ) {
            throw new IllegalStateException( String.format( "发票号为 '%s' 的发票已经完成销账，无法删除。", temp.getCode() ) );
        }
        
        // 删除
        int length = invoiceDao.deleteInvoice( temp );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "删除发票发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", length ) );
        }
        
        return temp;
    }

    public void setInvoiceDao( InvoiceDao invoiceDao ) {
        this.invoiceDao = invoiceDao;
    }
    
    public void setAccountDao( AccountDao accountDao ) {
        this.accountDao = accountDao;
    }
    
}
