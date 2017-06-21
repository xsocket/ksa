package com.ksa.service.logistics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ksa.dao.logistics.BookingNoteCargoDao;
import com.ksa.dao.logistics.BookingNoteDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.logistics.BookingNoteCargo;
import com.ksa.model.logistics.BookingNoteState;
import com.ksa.service.logistics.BookingNoteService;


public class BookingNoteServiceImpl implements BookingNoteService {
    
    private static final String BOOKING_NOTE_CODE_PREFIX = "KH";
    
    protected BookingNoteDao bookingNoteDao;
    protected BookingNoteCargoDao bookingNoteCargoDao;

    @Override
    public BookingNote loadBookingNoteById( String id ) throws RuntimeException {
        BookingNote note = bookingNoteDao.selectBookingNoteById( id );
        if( note == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的托单不存在。", id ) );
        }
        return note;
    }

    @Override
    public BookingNote getNewBookingNote( String type ) throws RuntimeException {
        BookingNote bn = new BookingNote();
        if( BookingNote.TYPE_SEA_EXPORT.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_SEA_EXPORT );
            bn.setSerialNumber( queryNotNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_SEA_IMPORT.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_SEA_IMPORT );
            bn.setSerialNumber( queryNotNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_AIR_EXPORT.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_AIR_EXPORT );
            bn.setSerialNumber( queryNotNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_AIR_IMPORT.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_AIR_IMPORT );
            bn.setSerialNumber( queryNotNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_NATIVE.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_NATIVE );
            bn.setSerialNumber( queryNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_ZJ.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_ZJ );
            bn.setSerialNumber( queryNotNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_KB.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_KB );
            bn.setSerialNumber( queryNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_BC.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_BC );
            bn.setSerialNumber( queryNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_CC.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_CC );
            bn.setSerialNumber( queryNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_RH.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_RH );
            bn.setSerialNumber( queryNativeBookingNoteCount() + 1 );
        } else if( BookingNote.TYPE_TL.equalsIgnoreCase( type ) ) {
            bn.setType( BookingNote.TYPE_TL );
            bn.setSerialNumber( queryNativeBookingNoteCount() + 1 );
        } else {
            throw new IllegalArgumentException( String.format( "不存在类型为 '%s' 的托单。", type ) );
        }
        bn.setCode( BOOKING_NOTE_CODE_PREFIX + bn.getType() + bn.getSerialNumber() );
        
        return bn;
    }
    
    private int queryNativeBookingNoteCount() {
        return bookingNoteDao.selectBookingNoteCount( " TYPE = 'LY' " );
    }
    
    private int queryNotNativeBookingNoteCount() {
        return bookingNoteDao.selectBookingNoteCount( " TYPE <> 'LY' " );
    }

    @Override
    public BookingNote createBookingNote( BookingNote note ) throws RuntimeException {
        BookingNote temp = getNewBookingNote( note.getType() );
        
        // 2013-09-19 v3.4.6 加入对提单号的唯一性验证        
        if( isLadingNoExist( note ) ) {
            note.setId( null );
            BookingNote lading = bookingNoteDao.selectBookingNoteByLading( note );
            if( lading != null ) {
                if( lading.getMawb() != null && lading.getMawb().equals( note.getMawb() ) ) {
                    throw new IllegalStateException( String.format( "主提单号 '%s' 已存在，详见托单 '%s'。请勿为同一提单重复创建托单。", lading.getMawb(), lading.getCode() ) );
                } else {
                    throw new IllegalStateException( String.format( "副提单号 '%s' 已存在，详见托单 '%s'。请勿为同一提单重复创建托单。", lading.getHawb(), lading.getCode() ) );
                }
            }
        }
        
        // 基本数据的初始化
        String id = ModelUtils.generateRandomId();
        note.setType( temp.getType() );
        note.setSerialNumber( temp.getSerialNumber() );
        note.setCode( temp.getCode() );
        note.setId( id );

        int length = bookingNoteDao.insertBookingNote( note );
        if( length != 1 ) {
            String errorMessage = "新增%s发生异常，期望新增 1 条数据，实际新增了 %d 条数据。";
            throw new IllegalStateException( String.format( errorMessage, "托单", length ) );
        }
        
        // 添加 Cargo
        List<BookingNoteCargo> cargos = note.getCargos();
        if( cargos != null && cargos.size() > 0 ) {
            for( BookingNoteCargo cargo : cargos ) {
                cargo.setId( ModelUtils.generateRandomId() );
                cargo.setBookingNote( note );
                bookingNoteCargoDao.insertCargo( cargo );
            }
        }
        
        return note;
    }

    @Override
    public BookingNote modifyBookingNote( BookingNote note ) throws RuntimeException {
        String id = note.getId();
        BookingNote temp = bookingNoteDao.selectBookingNoteById( id );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的托单不存在。", note.getId() ) );
        }
        
        // 2013-09-19 v3.4.6 加入对提单号的唯一性验证        
        if( isLadingNoExist( note ) ) {
            BookingNote lading = bookingNoteDao.selectBookingNoteByLading( note );
            if( lading != null ) {
                if( lading.getMawb() != null && lading.getMawb().equals( note.getMawb() ) ) {
                    throw new IllegalStateException( String.format( "主提单号 '%s' 已存在，详见托单 '%s'。请勿为同一提单重复创建托单。", lading.getMawb(), lading.getCode() ) );
                } else {
                    throw new IllegalStateException( String.format( "副提单号 '%s' 已存在，详见托单 '%s'。请勿为同一提单重复创建托单。", lading.getHawb(), lading.getCode() ) );
                }
            }
        }
        
        // 完成退单状态的设置
        if( note.getState() == BookingNoteState.RETURNED ) {
            temp.setState( BookingNoteState.setReturned( temp.getState() ) );
            bookingNoteDao.updateBookingNoteState( note );
        }
        
        int length = bookingNoteDao.updateBookingNote( note );
        if( length != 1 ) {
            String errorMessage = "更新%s发生异常，期望新增 1 条数据，实际更新了 %d 条数据。";
            throw new IllegalStateException( String.format( errorMessage, "托单", length ) );
        }
        

        
        // 更新 Cargo ：新增以前没有的，修改变更的，删除现在没有的。
        if( note.getCargos() != null && note.getCargos().size() > 0 ) {
            for( BookingNoteCargo cargo : note.getCargos() ) {
                if( ! StringUtils.hasText( cargo.getId() ) ) {
                 // 新增
                    BookingNoteCargo newCargo = new BookingNoteCargo();
                    
                    newCargo.setId( ModelUtils.generateRandomId() );
                    newCargo.setBookingNote( note );
                    newCargo.setName( cargo.getName() );
                    newCargo.setType( cargo.getType() );
                    newCargo.setCategory( cargo.getCategory() );
                    newCargo.setAmount( cargo.getAmount() );
                    bookingNoteCargoDao.insertCargo( newCargo );
                }
            }
        }
        
        Map<String, BookingNoteCargo> oldCargos = generateCargoMap( temp.getCargos() );
        Map<String, BookingNoteCargo> newCargos = generateCargoMap( note.getCargos() );

        for( String oldKey : oldCargos.keySet() ) {
            if( newCargos.containsKey( oldKey ) ) {
                // 更新
                BookingNoteCargo newCargo = newCargos.get( oldKey );
                if( needUpdate( newCargo, oldCargos.get( oldKey ) ) ) {
                    newCargo.setBookingNote( note );
                    bookingNoteCargoDao.updateCargo( newCargo );
                }
            } else {
                // 移除
                bookingNoteCargoDao.deleteCargo( oldCargos.get( oldKey ) );
            }
        }
        
        return note;
    }
    
    private boolean needUpdate( BookingNoteCargo cargo1, BookingNoteCargo cargo2 ) {
        if( cargo1.getAmount() != cargo2.getAmount() ) {
            return true;
        } else if( ObjectUtils.notEqual( cargo1.getCategory(), cargo2.getCategory() ) ) {
            return true;
        } else if( ObjectUtils.notEqual( cargo1.getName(), cargo2.getName() ) ) {
            return true;
        } else if( ObjectUtils.notEqual( cargo1.getType(), cargo2.getType() ) ) {
            return true;
        }
        return false;
    }
    
    private Map<String,  BookingNoteCargo> generateCargoMap( List<BookingNoteCargo> cargos ) {
        Map<String, BookingNoteCargo> cargoMap = new HashMap<String, BookingNoteCargo>();
        if( cargos != null && cargos.size() > 0 ) {
            for( BookingNoteCargo cargo : cargos ) {
                if( StringUtils.hasText( cargo.getId() ) ) {
                    cargoMap.put( cargo.getId(), cargo );
                }
            }
        }
        return cargoMap;
    }

    @Override
    public BookingNote removeBookingNote( BookingNote note ) throws RuntimeException {
        BookingNote temp = bookingNoteDao.selectBookingNoteById( note.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的托单不存在。", note.getId() ) );
        }
        // 将托单标记为 '已删除' 状态，实际并未真正删除。
        int length = bookingNoteDao.deleteBookingNote( temp );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "删除托单发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", length ) );
        }
        
        return temp;
    }
    
    public BookingNote changeBookingNoteType( BookingNote note ) throws RuntimeException {
      BookingNote bn = bookingNoteDao.selectBookingNoteById( note.getId() );
      if( bn == null ) {
          throw new IllegalArgumentException( String.format( "标识为 '%s' 的托单不存在。", note.getId() ) );
      }
      
      String type = note.getType();
      if( BookingNote.TYPE_SEA_EXPORT.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_SEA_EXPORT );
      } else if( BookingNote.TYPE_SEA_IMPORT.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_SEA_IMPORT );
      } else if( BookingNote.TYPE_AIR_EXPORT.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_AIR_EXPORT );
      } else if( BookingNote.TYPE_AIR_IMPORT.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_AIR_IMPORT );
      } else if( BookingNote.TYPE_NATIVE.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_NATIVE );
      } else if( BookingNote.TYPE_KB.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_KB );
      } else if( BookingNote.TYPE_BC.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_BC );
      } else if( BookingNote.TYPE_CC.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_CC );
      } else if( BookingNote.TYPE_RH.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_RH );
      } else if( BookingNote.TYPE_TL.equalsIgnoreCase( type ) ) {
          bn.setType( BookingNote.TYPE_TL );
      } else if( BookingNote.TYPE_ZJ.equalsIgnoreCase( type ) ) {
        bn.setType( BookingNote.TYPE_ZJ );
      } else {
          throw new IllegalArgumentException( String.format( "不存在类型为 '%s' 的托单。", type ) );
      }
      bn.setCode( BOOKING_NOTE_CODE_PREFIX + bn.getType() + bn.getSerialNumber() );
      
      // 变更托单类型
      int length = bookingNoteDao.updateBookingNoteType( bn );
      if( length != 1 ) {
          throw new IllegalStateException( String.format( "变更托单类型时发生异常，期望变更 1 条数据，实际变更了 %d 条数据。", length ) );
      }
      
      return bn;
  }
    
    
    public void setBookingNoteDao( BookingNoteDao bookingNoteDao ) {
        this.bookingNoteDao = bookingNoteDao;
    }
    
    public void setBookingNoteCargoDao( BookingNoteCargoDao bookingNoteCargoDao ) {
        this.bookingNoteCargoDao = bookingNoteCargoDao;
    }

    // 判断是否存在提单号，主副提单有其一即可
    private boolean isLadingNoExist( BookingNote note ) {
        return StringUtils.hasText( note.getMawb() ) || StringUtils.hasText( note.getHawb() ) ;
    }

}
