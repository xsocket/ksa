package com.ksa.dao.finance.mybatis;

import java.util.List;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.finance.InvoiceDao;
import com.ksa.model.finance.Invoice;

/**
 * 基于 Mybaits 的 InvoiceDaoDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisInvoiceDao extends AbstractMybatisDao implements InvoiceDao {

    @Override
    public int insertInvoice( Invoice invoice ) throws RuntimeException {
        return this.session.insert( "insert-finance-invoice", invoice );
    }

    @Override
    public int updateInvoice( Invoice invoice ) throws RuntimeException {
        return this.session.update( "update-finance-invoice", invoice );
    }

    @Override
    public int deleteInvoice( Invoice invoice ) throws RuntimeException {
        return this.session.delete( "delete-finance-invoice", invoice );
    }

    @Override
    public int updateInvoiceAccount( Invoice invoice ) throws RuntimeException {
        return this.session.update( "update-finance-invoice-account", invoice );
    }
    
    @Override
    public Invoice selectInvoiceById( String id ) throws RuntimeException {        
        return this.session.selectOne( "select-finance-invoice-byid", id );
    }

    @Override
    public List<Invoice> selectInvoiceByAccountId( String id ) throws RuntimeException {
        return this.session.selectList( "select-finance-invoice-byaccount", id );
    }

}
