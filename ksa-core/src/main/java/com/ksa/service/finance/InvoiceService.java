package com.ksa.service.finance;

import com.ksa.model.finance.Account;
import com.ksa.model.finance.Invoice;


/**
 * 发票管理相关接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface InvoiceService {

    Invoice loadInvoiceById( String id ) throws RuntimeException;
    
    Invoice assignInvoiceToAccount( Invoice invoice, Account account ) throws RuntimeException;
    
    Invoice saveInvoice( Invoice invoice ) throws RuntimeException;
    
    Invoice removeInvoice( Invoice invoice ) throws RuntimeException;
}
