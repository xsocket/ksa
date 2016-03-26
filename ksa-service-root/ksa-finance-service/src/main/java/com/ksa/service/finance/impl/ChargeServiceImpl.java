package com.ksa.service.finance.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.ksa.dao.finance.ChargeDao;
import com.ksa.dao.logistics.BookingNoteDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.finance.BookingNoteChargeState;
import com.ksa.model.finance.Charge;
import com.ksa.model.finance.FinanceModel;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.logistics.BookingNoteState;
import com.ksa.service.finance.ChargeService;


public class ChargeServiceImpl implements ChargeService {
    
    private ChargeDao chargeDao;
    private BookingNoteDao bookingNoteDao;  // 改变托单状态

    @Override
    public List<Charge> loadBookingNoteCharges( String bookingNoteId ) throws RuntimeException {
        return  chargeDao.selectChargeByBookingNoteId( bookingNoteId );
    }
    
    @Override
    public List<Charge> loadBookingNoteCharges( String bookingNoteId, int direction, int nature ) throws RuntimeException {
        return  chargeDao.selectChargeByBookingNoteId( bookingNoteId, direction, nature );
    }

    @Override
    public List<Charge> loadBookingNoteCharges( String bookingNoteId, List<Charge> incomes, List<Charge> expenses ) throws RuntimeException {
        List<Charge> charges = chargeDao.selectChargeByBookingNoteId( bookingNoteId );
        if( charges == null || charges.isEmpty() ) {
            return Collections.emptyList();
        }

        if( incomes != null ) {
            for( Charge charge : charges ) {
                if( Charge.isIncome( charge ) ) {
                    incomes.add( charge );
                }
            }
            // 已通过SQL进行排序
            //Collections.sort( incomes );
        }
        
        if( expenses != null ) {
            for( Charge charge : charges ) {
                if( Charge.isExpense( charge ) ) {
                    expenses.add( charge );
                }
            }
            // 已通过SQL进行排序
            //Collections.sort( expenses );
        }
        return charges;
    }
    
    @Override
    public List<Charge> loadBookingNoteCharges( String bookingNoteId, List<Charge> incomes, List<Charge> expenses, int nature ) throws RuntimeException {
        
        List<Charge> charges = chargeDao.selectChargeByBookingNoteId( bookingNoteId );
        if( charges == null || charges.isEmpty() ) {
            return Collections.emptyList();
        }
        
        List<Charge> filteredCharges = new ArrayList<Charge>( charges.size() );
        for( Charge charge : charges ) {
            if( charge.getNature() == nature ) {
                filteredCharges.add( charge );
            }
        }

        if( incomes != null ) {
            for( Charge charge : filteredCharges ) {
                if( Charge.isIncome( charge ) ) {
                    incomes.add( charge );
                }
            }
            Collections.sort( incomes );
        }
        
        if( expenses != null ) {
            for( Charge charge : filteredCharges ) {
                if( Charge.isExpense( charge ) ) {
                    expenses.add( charge );
                }
            }
            Collections.sort( expenses );
        }
        return filteredCharges;
    }
    
    @Override
    public BookingNote updateBookingNoteChargeState( BookingNote note ) {
        BookingNote temp = bookingNoteDao.selectBookingNoteById( note.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的托单不存在。", note.getId() ) );
        }
        int oldState = temp.getState();
        int newState = note.getState();
        // 15 = 0x0000000F
        // 将 对应费用的 4位状态位置0， 然后再赋予新值
        int tempState = ( oldState & ~0xF );
        newState = tempState | newState;
       
        // FIXME 需要判断状态改变的合理性
        temp.setState( newState );
        note.setState( newState );
        bookingNoteDao.updateBookingNoteState( temp );
        return temp;
    }
    
    @Override
    public BookingNote updateBookingNoteChargeState( BookingNote note, int nature ) {
        BookingNote temp = bookingNoteDao.selectBookingNoteById( note.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的托单不存在。", note.getId() ) );
        }
        int oldState = temp.getState();
        int newState = note.getState();
        // 15 = 0x0000000F
        // 将 对应费用的 4位状态位置0， 然后再赋予新值
        int shift = BookingNoteChargeState.computeShift( nature );
        int tempState = ( oldState & ~( 0xF << shift  ) );
        newState = tempState | ( newState << shift );
       
        // FIXME 需要判断状态改变的合理性
        temp.setState( newState );
        note.setState( newState );
        bookingNoteDao.updateBookingNoteState( temp );
        return temp;
    }
    
    @Override
    public BookingNote updateBookingNoteChargeState( BookingNote note, int direction, int nature ) {
        BookingNote temp = bookingNoteDao.selectBookingNoteById( note.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的托单不存在。", note.getId() ) );
        }
        int oldState = temp.getState();
        int newState = note.getState();
        // 15 = 0x0000000F
        // 将 对应费用的 4位状态位置0， 然后再赋予新值
        int shift = BookingNoteChargeState.computeShift( direction, nature );
        int tempState = ( oldState & ~( 0xF << shift  ) );
        newState = tempState | ( newState << shift );
       
        // FIXME 需要判断状态改变的合理性
        temp.setState( newState );
        note.setState( newState );
        bookingNoteDao.updateBookingNoteState( temp );
        return temp;
    }
    
    @Override
    public List<Charge> saveBookingNoteCharges( BookingNote note, List<Charge> charges, int direction, int nature ) throws RuntimeException {
        if( BookingNoteState.isChecking( note.getState() ) ) {
            throw new IllegalStateException( "托单费用信息已经提交审核，暂时不能进行更改。" );
        }
     // 获取托单的原有费用列表
        List<Charge> oldCharges = chargeDao.selectChargeByBookingNoteId( note.getId(), direction, nature );
        Map<String, Charge> oldChargeMap = getChargeMap( oldCharges );
        
        // 托单的新费用列表
        List<Charge> newCharges = new ArrayList<Charge>();
        if( charges != null && charges.size() > 0 ) {
            setRank( charges );
            newCharges.addAll( charges );
        }
        Map<String, Charge> newChargeMap = getChargeMap( newCharges );  // 过滤掉了新增费用( 没有 id 属性的费用 )
        
     // 首先处理新增的费用
        for( Charge charge : newCharges ) {
            if( charge == null ) {
                continue;
            }
            if( ! StringUtils.hasText( charge.getId() ) ) {
                charge.setId( ModelUtils.generateRandomId() );
                charge.getBookingNote().setId( note.getId() );
                int length = chargeDao.insertCharge( charge );
                if( length != 1 ) {
                    throw new IllegalStateException( String.format( "新增单条费用数据发生异常，期望新增 1 条数据，实际新增了 %d 条数据。", length ) );
                }
            }
        }
        
        // 接着处理删除的费用: 新列表中没有, 但旧列表中有的数据
        // 和 更新费用
        for( String oldChargeId : oldChargeMap.keySet() ) {
            if( ! newChargeMap.containsKey( oldChargeId ) ) {
                // 新列表中不存在则需要删除
                int length = chargeDao.deleteCharge( oldChargeMap.get( oldChargeId ) );
                if( length != 1 ) {
                    throw new IllegalStateException( String.format( "删除单条费用数据发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", length ) );
                }
            } else {
                // 新列表中存在, 需要更新则更新
                Charge newCharge = newChargeMap.get( oldChargeId );
                if( needUpdate( oldChargeMap.get( oldChargeId ), newCharge ) ) {
                    newCharge.getBookingNote().setId( note.getId() );
                    int length = chargeDao.updateCharge( newCharge );
                    if( length != 1 ) {
                        throw new IllegalStateException( String.format( "更新单条费用数据发生异常，期望更新 1 条数据，实际更新了 %d 条数据。", length ) );
                    }
                }
            }
        }
        
        // 保存完毕, 读取最新的数据并返回
        charges.clear();     
        newCharges = loadBookingNoteCharges( note.getId(), direction, nature );
        
        if( oldCharges.size() == 0 && newCharges.size() > 0 ) {
            // 状态改为录入中
            note.setState( BookingNoteChargeState.ENTERING );
            this.updateBookingNoteChargeState( note, direction, nature );
        } else if( oldCharges.size() > 0 && newCharges.size() == 0 )  {
            // 状态改为还未录入
            note.setState( BookingNoteChargeState.NONE );
            this.updateBookingNoteChargeState( note, direction, nature );
        }
        
        // 更新记账月份
        bookingNoteDao.updateBookingNoteChargeDate( note );
        
        return newCharges;
    }
    
    /*@Override
    public List<Charge> saveBookingNoteCharges( BookingNote note, List<Charge> incomes, List<Charge> expenses ) throws RuntimeException {
        return saveBookingNoteCharges( note, incomes, expenses, 0 );
    }    */
    
    // 设置录入顺序，解决排序问题
    private void setRank(List<Charge> charges) {
      int rank = 1;
      for(Charge c : charges) {
        c.setRank(rank++);
      }
    }
    
    @Override
    public List<Charge> saveBookingNoteCharges( BookingNote note, List<Charge> incomes, List<Charge> expenses ) throws RuntimeException {
        if( BookingNoteChargeState.isChecking( note.getState() ) ) {
            throw new IllegalStateException( "托单费用信息已经提交审核，暂时不能进行更改。" );
        }
        // 获取托单的原有费用列表
        List<Charge> oldCharges = loadBookingNoteCharges( note.getId(), null, null );
        Map<String, Charge> oldChargeMap = getChargeMap( oldCharges );
        
        // 托单的新费用列表
        List<Charge> newCharges = new ArrayList<Charge>();
        if( incomes != null && incomes.size() > 0 ) {
            setRank( incomes );
            newCharges.addAll( incomes );
        }
        if( expenses != null && expenses.size() > 0 ) {
            setRank( expenses );
            newCharges.addAll( expenses );
        }
        Map<String, Charge> newChargeMap = getChargeMap( newCharges );  // 过滤掉了新增费用( 没有 id 属性的费用 )
        
        // 首先处理新增的费用
        for( Charge charge : newCharges ) {
            if( charge == null ) {
                continue;
            }
            if( ! StringUtils.hasText( charge.getId() ) ) {
                charge.setId( ModelUtils.generateRandomId() );
                charge.getBookingNote().setId( note.getId() );
                int length = chargeDao.insertCharge( charge );
                if( length != 1 ) {
                    throw new IllegalStateException( String.format( "新增单条费用数据发生异常，期望新增 1 条数据，实际新增了 %d 条数据。", length ) );
                }
            }
        }
        
        // 接着处理删除的费用: 新列表中没有, 但旧列表中有的数据
        // 和 更新费用
        for( String oldChargeId : oldChargeMap.keySet() ) {
            if( ! newChargeMap.containsKey( oldChargeId ) ) {
                // 新列表中不存在则需要删除
                int length = chargeDao.deleteCharge( oldChargeMap.get( oldChargeId ) );
                if( length != 1 ) {
                    throw new IllegalStateException( String.format( "删除单条费用数据发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", length ) );
                }
            } else {
                // 新列表中存在, 需要更新则更新
                Charge newCharge = newChargeMap.get( oldChargeId );
                if( needUpdate( oldChargeMap.get( oldChargeId ), newCharge ) ) {
                    newCharge.getBookingNote().setId( note.getId() );
                    int length = chargeDao.updateCharge( newCharge );
                    if( length != 1 ) {
                        throw new IllegalStateException( String.format( "更新单条费用数据发生异常，期望更新 1 条数据，实际更新了 %d 条数据。", length ) );
                    }
                }
            }
        }
        
        // 保存完毕, 读取最新的数据并返回
        incomes.clear();
        expenses.clear();        
        newCharges = loadBookingNoteCharges( note.getId(), incomes, expenses );
        
        if( newCharges.size() > 0 ) {
            // 状态改为录入中
            note.setState( BookingNoteChargeState.setEntering( note.getState() ) );
            bookingNoteDao.updateBookingNoteState( note );
        } else if( oldCharges.size() > 0 && newCharges.size() == 0 )  {
            // 状态改为还未录入
            note.setState( BookingNoteChargeState.setUnentering( note.getState() ) );
            bookingNoteDao.updateBookingNoteState( note );
        }
        
        // 更新记账月份
        bookingNoteDao.updateBookingNoteChargeDate( note );
        
        return newCharges;
    }    
    
    @Override
    @Deprecated
    public List<Charge> saveBookingNoteCharges( BookingNote note, List<Charge> incomes, List<Charge> expenses, int nature ) throws RuntimeException {
        if( BookingNoteChargeState.isChecking( note.getState(), nature ) ) {
            throw new IllegalStateException( "托单费用信息已经提交审核，暂时不能进行更改。" );
        }
        // 获取托单的原有费用列表
        List<Charge> oldCharges = loadBookingNoteCharges( note.getId(), null, null );
        Map<String, Charge> oldChargeMap = getChargeMap( oldCharges );
        
        // 托单的新费用列表
        List<Charge> newCharges = new ArrayList<Charge>();
        if( incomes != null && incomes.size() > 0 ) {
            setRank( incomes );
            newCharges.addAll( incomes );
        }
        if( expenses != null && expenses.size() > 0 ) {
            setRank( expenses );
            newCharges.addAll( expenses );
        }
        Map<String, Charge> newChargeMap = getChargeMap( newCharges );  // 过滤掉了新增费用( 没有 id 属性的费用 )
        
        // 首先处理新增的费用
        for( Charge charge : newCharges ) {
            if( charge == null ) {
                continue;
            }
            if( ! StringUtils.hasText( charge.getId() ) ) {
                charge.setId( ModelUtils.generateRandomId() );
                charge.getBookingNote().setId( note.getId() );
                int length = chargeDao.insertCharge( charge );
                if( length != 1 ) {
                    throw new IllegalStateException( String.format( "新增单条费用数据发生异常，期望新增 1 条数据，实际新增了 %d 条数据。", length ) );
                }
            }
        }
        
        // 接着处理删除的费用: 新列表中没有, 但旧列表中有的数据
        // 和 更新费用
        for( String oldChargeId : oldChargeMap.keySet() ) {
            if( ! newChargeMap.containsKey( oldChargeId ) ) {
                // 新列表中不存在则需要删除
                int length = chargeDao.deleteCharge( oldChargeMap.get( oldChargeId ) );
                if( length != 1 ) {
                    throw new IllegalStateException( String.format( "删除单条费用数据发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", length ) );
                }
            } else {
                // 新列表中存在, 需要更新则更新
                Charge newCharge = newChargeMap.get( oldChargeId );
                if( needUpdate( oldChargeMap.get( oldChargeId ), newCharge ) ) {
                    newCharge.getBookingNote().setId( note.getId() );
                    int length = chargeDao.updateCharge( newCharge );
                    if( length != 1 ) {
                        throw new IllegalStateException( String.format( "更新单条费用数据发生异常，期望更新 1 条数据，实际更新了 %d 条数据。", length ) );
                    }
                }
            }
        }
        
        // 保存完毕, 读取最新的数据并返回
        incomes.clear();
        expenses.clear();        
        newCharges = loadBookingNoteCharges( note.getId(), incomes, expenses, nature );
        
        if( newCharges.size() > 0 ) {
            // 状态改为录入中
            note.setState( BookingNoteChargeState.setEntering( note.getState(), nature ) );
            bookingNoteDao.updateBookingNoteState( note );
        } else if( oldCharges.size() > 0 && newCharges.size() == 0 )  {
            // 状态改为还未录入
            note.setState( BookingNoteChargeState.setUnentering( note.getState(), nature ) );
            bookingNoteDao.updateBookingNoteState( note );
        }
        
        // 更新记账月份
        if( FinanceModel.isNative( nature ) ) {
            // 国内费用才更改记账日期
            bookingNoteDao.updateBookingNoteChargeDate( note );
        }
        
        return newCharges;
    }
    
    private boolean needUpdate( Charge oldCharge, Charge newCharge ) {
        if( newCharge.getAmount() - oldCharge.getAmount() > 0.001 ) {
            return true;    // 金额发生变动
        } else if( oldCharge.getAmount() - newCharge.getAmount() > 0.001 ) {
            return true;    // 金额发生变动
        } else if( ! oldCharge.getCurrency().getId().equals( newCharge.getCurrency().getId() ) ) {
            return true;    // 币种发生变动
        } else if( ! oldCharge.getNote().equals( newCharge.getNote() ) ) {
            return true;    // 备注发生变动
        } else if( ! oldCharge.getType().equals( newCharge.getType() ) ) {
            return true;    // 费用项目变动
        } else if( ! oldCharge.getTarget().getId().equals( newCharge.getTarget().getId() ) ) {
            return true;    // 结算对象变动
        } else if( oldCharge.getNature() != newCharge.getNature() ) {
            return true;    // 境内/境外变动
        }
        return false;
    }
    
    private Map<String, Charge> getChargeMap( List<Charge> list ) {
        Map<String, Charge> map = new HashMap<String, Charge>();
        for( Charge charge : list ) {
            if( charge != null && StringUtils.hasText( charge.getId() ) ) {
                map.put( charge.getId(), charge );
            }
        }
        return map;
    }

    public void setChargeDao( ChargeDao chargeDao ) {
        this.chargeDao = chargeDao;
    }
    
    public void setBookingNoteDao( BookingNoteDao bookingNoteDao ) {
        this.bookingNoteDao = bookingNoteDao;
    }
}
