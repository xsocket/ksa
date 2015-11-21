package com.ksa.system.initialize.convert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.finance.AccountDao;
import com.ksa.dao.finance.ChargeDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.bd.BasicData;
import com.ksa.model.bd.BasicDataType;
import com.ksa.model.bd.Currency;
import com.ksa.model.finance.Account;
import com.ksa.model.finance.AccountState;
import com.ksa.model.finance.Charge;
import com.ksa.model.finance.FinanceModel;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.security.User;
import com.ksa.service.bd.util.BasicDataUtils;
import com.ksa.system.initialize.model.FeiYong;
import com.ksa.system.initialize.model.JieSuanDan;
import com.ksa.system.initialize.model.TuoDan;
import com.ksa.util.StringUtils;

/**
 * 费用相关数据的转换
 *
 * @author 麻文强
 *
 * @since
 */
public class FinanceConverter {
    
    // 结算单 code 到结算单的映射
    private static Map<String, Account> jsdMap = new HashMap<String, Account>();

    public static void convert( SqlSession session, BookingNote note, TuoDan tuodan ) {
        Account account = new Account();
        // 先处理结算单
        if( StringUtils.hasText( tuodan.getJsdDaiMa() ) ) {
            // 托单已经开出结算单
            JieSuanDan jsd = session.selectOne( "select-init-jsd", tuodan.getJsdDaiMa() );
            if( jsd != null ) {
                account = convertJSD( session, jsd, note.getCreator() );
            }
        }
        
        List<FeiYong> list = session.selectList( "select-init-feiyong-bytuodan", tuodan.getBianHao() );
        if( list != null && list.size() > 0 ) {
            ChargeDao dao = ServiceContextUtils.getService( ChargeDao.class );
            for( FeiYong fy : list ) {
                Charge charge = new Charge();
                if( fy.getDirection() == FinanceModel.INCOME ) {
                    // 收入费用设置对应结算单
                    charge.setAccount( account );
                }
                if( fy.getShuLiang() != null ) {
                    charge.setQuantity( fy.getShuLiang() );
                }
                charge.setPrice( fy.getDanJia() );
                charge.setAmount( fy.getJinE() );
                charge.setBookingNote( note );
                charge.setCreatedDate( tuodan.getYueFen() );
                charge.setCreator( UserConverter.getUser( fy.getCaoZuoYuan() ) );
                // 设置比重
                String bizhong = fy.getBiZhong();
                if( Currency.RMB.getName().equals( bizhong ) ) {
                    charge.setCurrency( Currency.RMB );
                } else if( Currency.USD.getName().equals( bizhong ) ) {
                    charge.setCurrency( Currency.USD );
                } else {
                    BasicData currency = BasicDataUtils.getDataFromName( bizhong, BasicDataType.CURRENCY.getId() );
                    if( currency != null ) {
                        charge.getCurrency().setId( currency.getId() );
                    } else {
                        charge.setCurrency( Currency.RMB );
                    }
                }
                charge.setDirection( fy.getDirection() );
                charge.setId( ModelUtils.generateRandomId() );
                charge.setNote( StringUtils.hasText( fy.getBeiZhu()  ) ? fy.getBeiZhu() : "" );
                charge.setTarget( PartnerConverter.getPartner( fy.getJieSuanDX() ) );
                charge.setType( StringUtils.hasText( fy.getFeiYongXM() ) ? fy.getFeiYongXM() : "未知类型" );
                dao.insertCharge( charge );
            }
            return;
        }
        
    }
    
    private static Account convertJSD( SqlSession session, JieSuanDan jsd, User creator ) {
        if( jsdMap.containsKey( jsd.getDaiMa() ) ) {
            return jsdMap.get( jsd.getDaiMa() );
        }
        
        Account account = new Account();
        
        account.setId( ModelUtils.generateRandomId() );
        account.setCode( jsd.getDaiMa() );
        account.setCreatedDate( jsd.getKaiDanRQ() );
        account.setCreator( creator );
        account.setDeadline( jsd.getJieZhiRQ() );
        account.setNote( jsd.getBeiZhu() );
        account.setTarget( PartnerConverter.getPartner( jsd.getKeHu() ) );
        
        // 处理结算单状态
        Date now = new Date();
        // 超过结算日期的单子默认设置为“处理完毕”
        if( now.after(  jsd.getJieZhiRQ() ) ) {
            account.setState( AccountState.SETTLED );
            account.setPaymentDate( jsd.getJieZhiRQ() );
        } else {
            // 近期的单子不设置状态
            AccountState.setSettled( AccountState.NONE );
        }
        
        jsdMap.put( jsd.getDaiMa(), account );
        ServiceContextUtils.getService( AccountDao.class ).insertAccount( account );
        return account;
    }
}
