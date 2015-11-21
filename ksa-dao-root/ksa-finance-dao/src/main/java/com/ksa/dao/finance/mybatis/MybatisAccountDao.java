package com.ksa.dao.finance.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.finance.AccountDao;
import com.ksa.model.finance.Account;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;

/**
 * 基于 Mybaits 的 AccountDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisAccountDao extends AbstractMybatisDao implements AccountDao {

    @Override
    public int insertAccount( Account account ) throws RuntimeException {
        return this.session.insert( "insert-finance-account", account );
    }
    

    @Override
    public int insertAccountCharges( Account account, List<Charge> charges ) throws RuntimeException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put( "accountId", account.getId() );
        paras.put( "charges", charges );
        return this.session.update( "update-finance-account-charges", paras );
    }

    @Override
    public int deleteAccountCharges( Account account, List<Charge> charges ) throws RuntimeException {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put( "accountId", null );
        paras.put( "charges", charges );
        return this.session.update( "update-finance-account-charges", paras );
    }

    @Override
    public int updateAccount( Account account ) throws RuntimeException {
        return this.session.update( "update-finance-account", account );
    }
    
    @Override
    public int updateAccountState( Account account ) throws RuntimeException {
        return this.session.update( "update-finance-account-state", account );
    }

    @Override
    public int deleteAccount( Account account ) throws RuntimeException {
        return this.session.delete( "delete-finance-account", account );
    }

    @Override
    public Account selectAccountById( String id ) throws RuntimeException {        
        return this.session.selectOne( "select-finance-account-byid", id );
    }
    
    @Override
    public List<BookingNote> selectBookingNoteByAccountId( String accountId ) throws RuntimeException {
        return this.session.selectList( "select-finance-bookingnote-byaccount", accountId );
    }


    @Override
    public int querySimilarAccountCodeCount( String code ) throws RuntimeException {
        Map<String, String> para = new HashMap<String, String>();
        para.put( "code", code );
        Integer count = this.session.selectOne( "select-finance-account-similar-code-count", para );
        return count.intValue();
    }

    
}
