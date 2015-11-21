package com.ksa.dao.finance;

import java.util.List;

import com.ksa.model.finance.Invoice;

/**
 * 费用数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface InvoiceDao {
    int insertInvoice( Invoice invoice ) throws RuntimeException;
    int updateInvoice( Invoice invoice ) throws RuntimeException;
    int deleteInvoice( Invoice invoice ) throws RuntimeException;
    
    int updateInvoiceAccount( Invoice invoice ) throws RuntimeException;
    
    Invoice selectInvoiceById( String id ) throws RuntimeException;
    List<Invoice> selectInvoiceByAccountId( String accountId ) throws RuntimeException;
}
