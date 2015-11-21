package com.ksa.dao.finance.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.finance.ChargeDao;
import com.ksa.model.finance.Charge;
import com.ksa.model.finance.FinanceModel;

/**
 * 基于 Mybaits 的 ChargeDaoDao 实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisChargeDao extends AbstractMybatisDao implements ChargeDao {
    
    protected String tableName;

    @Override
    public int insertCharge( Charge charge ) throws RuntimeException {
        return this.session.insert( "insert-finance-charge", charge );
    }

    @Override
    public int updateCharge( Charge charge ) throws RuntimeException {
        return this.session.update( "update-finance-charge", charge );
    }

    @Override
    public int deleteCharge( Charge charge ) throws RuntimeException {
        return this.session.delete( "delete-finance-charge", charge );
    }

    @Override
    public Charge selectChargeById( String id ) throws RuntimeException {        
        return this.session.selectOne( "select-finance-charge-byid", id );
    }

    @Override
    public List<Charge> selectChargeByBookingNoteId( String id ) throws RuntimeException {
        return selectChargeByBookingNoteId( id, 0, 0 );
    }
    
    @Override
    public List<Charge> selectChargeByBookingNoteId( String id, int direction, int nature ) throws RuntimeException {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put( "id", id );
        if( FinanceModel.isIncome( direction ) ) {
            param.put( "direction", FinanceModel.INCOME );
        } else if( FinanceModel.isExpense( direction ) ) {
            param.put( "direction", FinanceModel.EXPENSE );
        }
        
        if( FinanceModel.isNative( nature ) ) {
            param.put( "nature", FinanceModel.NATIVE );
        } else if( FinanceModel.isForeign( nature ) ) {
            param.put( "nature", FinanceModel.FOREIGN );
        }
        
        return this.session.selectList( "select-finance-charge-bybookingnote", param );
    }

    @Override
    public List<Charge> selectChargeByAccountId( String id ) throws RuntimeException {
        return this.session.selectList( "select-finance-charge-byaccount", id );
    }
}
