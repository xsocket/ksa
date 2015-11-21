package com.ksa.dao.finance;

import java.util.List;

import com.ksa.model.finance.Charge;

/**
 * 费用数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface ChargeDao {
    int insertCharge( Charge charge ) throws RuntimeException;
    int updateCharge( Charge charge ) throws RuntimeException;
    int deleteCharge( Charge charge ) throws RuntimeException;
    
    Charge selectChargeById( String id ) throws RuntimeException;
    List<Charge> selectChargeByBookingNoteId( String bnId ) throws RuntimeException;
    List<Charge> selectChargeByBookingNoteId( String bnId, int direction, int nature ) throws RuntimeException;
    List<Charge> selectChargeByAccountId( String accountId ) throws RuntimeException;
}
