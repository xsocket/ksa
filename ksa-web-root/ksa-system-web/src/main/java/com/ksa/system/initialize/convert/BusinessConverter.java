package com.ksa.system.initialize.convert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.ksa.context.ServiceContextUtils;
import com.ksa.model.bd.Partner;
import com.ksa.model.finance.BookingNoteChargeState;
import com.ksa.model.finance.FinanceModel;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.logistics.BookingNoteCargo;
import com.ksa.model.logistics.BookingNoteState;
import com.ksa.service.logistics.BookingNoteService;
import com.ksa.system.initialize.model.TuoDan;
import com.ksa.util.StringUtils;

/**
 * 业务数据迁移：托单，费用，结算单的迁移
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class BusinessConverter {

    public static List<String> doConvert( SqlSession session ) {
        Collection<TuoDan> list = getTuoDanList( session );
        convertTuoDan( session, list );
        return null;
    }
    
    private static Collection<TuoDan> getTuoDanList( SqlSession session ) {
        List<TuoDan> list = session.selectList( "select-init-tuodan" );
        Collections.sort( list );
        return list;
    }

    // 传入的托单列表保证是已经排序好的
    private static void convertTuoDan( SqlSession session, Collection<TuoDan> list ) {
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        Map<String, Partner> map = PartnerConverter.getPartnerMap();
        
        initializeBookingNoteTable();
        
        // FIXME 这个在年初使用会有问题
        Calendar c = Calendar.getInstance();
        // 两个月前的旧数据就不做业务处理了，统一标记为过时
        c.set( Calendar.MONTH, c.get(Calendar.MONTH) - 2 );
        Date oldDate = c.getTime();
        
        int nextNodeNumber = 1; // 表示下一个托单的流水号
        for( TuoDan td : list ) {   // list必须是按序号排列好的
            BookingNote note = new BookingNote();
            int bianhao = Integer.parseInt( td.getBianHao().substring( 4 ) );
            
            while( nextNodeNumber < bianhao ) {
                createDeletedBookingNote( nextNodeNumber, td.getCreatedDate() );
                nextNodeNumber++;   // 处理原数据库流水号不一致的问题
            }
            
            //note.setId( id );
            //KHSE***
            note.setCode( td.getBianHao() );
            note.setType( td.getBianHao().substring( 2, 4 ) );
            //note.setSubType( "fcl" );
            note.setSerialNumber( bianhao );
            Partner  customer = map.get( td.getKeHuDaiMa() );
            if( customer == null ) {
                note.getCustomer().setId( "" );
            } else {
                note.setCustomer( customer );
            }
            note.setInvoiceNumber( td.getFaPiaoBH() );
            note.setCreatedDate( td.getCreatedDate() );            
            note.setChargeDate( td.getYueFen() );
            
            note.setSaler( UserConverter.getUser( td.getXiaoShouRY() ) );
            note.setCreator( UserConverter.getUser( td.getCaoZuoY() ) );
            note.setCarrier( map.get( td.getChengYunRen() ) );
            note.setShippingAgent( map.get( td.getChuanDai() ) );
            note.setAgent( map.get( td.getDaiLi() ) );
            
            note.setCargoName( td.getPinMing() );
            note.setCargoNote( td.getBeiZhu() );
            //note.setCargoDescription( "cargoDescription" );
            //note.setCargoContainer( "cargoContainer" );
            note.setShippingMark( td.getMaiTou() );
            try {
                note.setVolumn( StringUtils.hasText( td.getTiJi() ) ? Float.parseFloat( td.getTiJi() ) : 0.0f );
                note.setWeight( StringUtils.hasText( td.getMaoZhong() ) ? Float.parseFloat( td.getMaoZhong() ) : 0.0f );
                note.setQuantity( td.getShuLiang() );
            } catch( Exception e ) {}
            note.setUnit( td.getShuLiangDW() );
            note.setQuantityDescription( td.getShuLiangEnglish() );
            
            note.setTitle( td.getKaiPiaoTT() );
            
            if( note.getType().equals( BookingNote.TYPE_AIR_EXPORT ) || note.getType().equals( BookingNote.TYPE_AIR_IMPORT  ) ) {
                // 空运托单的主副提单颠倒
                note.setMawb( td.getFuTiDan() );
                note.setHawb( td.getZhuTiDan() );
            } else {
                note.setMawb( td.getZhuTiDan() );
                note.setHawb( td.getFuTiDan() );
            }
            note.setShipper( map.get( td.getFaHuoRen() ) );
            note.setConsignee( map.get( td.getShouHuoRen() ) );
            note.setNotify( map.get( td.getTongZhiRen() ) );
            processCargos( note, td );      // 处理货物列表
            
            // 港口、时间、航线信息
            note.setDeparture( td.getDeparture() );
            note.setDepartureDate( td.getDepartureDate() );
            note.setDeparturePort( td.getDeparturePort() );
            note.setDestination( td.getDestination() );
            note.setDestinationDate( td.getDestinationDate() );
            note.setDestinationPort( td.getDestinationPort() );
            
            if( note.getType().equals( BookingNote.TYPE_AIR_IMPORT ) ) {
                // 空运进口托单 装货港卸货港 数据颠倒
                note.setLoadingPort( td.getDischargePort() );
                note.setDischargePort( td.getLoadingPort() );
            } else {
                note.setLoadingPort( td.getLoadingPort() );
                note.setDischargePort( td.getDischargePort() );
            }
            note.setDeliverDate( td.getDeliverDate() );
            
            note.setRoute( td.getHangXian() );
            note.setRouteCode( td.getHangci() );
            note.setRouteName( td.getChuanMing() );
            
            // 报关行
            note.setCustomsBroker( map.get( td.getBaoGuanHang() ) );
            note.setCustomsCode( td.getBaoGuanDanHao() );
            note.setCustomsDate( td.getBaoGuanSJ() );
            //note.setReturnCode( "returnCode" );
            //note.setReturnDate( now );
            //note.setTaxCode( "taxCode" );
            //note.setTaxDate1( now );
            //note.setTaxDate2( now );
            //note.setExpressCode( "expressCode" );
            
            note.setVehicleType( td.getCheXing() );                    
            //车牌号和车队名称颠倒了
            //note.setVehicleNumber( td.getChePaiHao() );            
            note.setVehicleTeam( map.get( td.getChePaiHao() ) );
            
            // 设置托单状态
            if( td.isDeleted() ) {
                note.setState( BookingNoteState.DELETED );
            } else if( oldDate.after( note.getCreatedDate() ) ) {
                // 两个月之前的数据，统一标记为已完成
                note.setState( getSettledState() );
            } else {
                note.setState( getProcessingState() );
            }
            note = service.createBookingNote( note );
            
            FinanceConverter.convert( session, note, td );
            
            nextNodeNumber = note.getSerialNumber() + 1;
        }
    }
    
    private static void processCargos( BookingNote note, TuoDan td ) {
        // 处理托单的货物数据
        try {
            String xiangxing = td.getXiangXing();
            if( StringUtils.hasText( xiangxing ) ) {
                int count = xiangxing.length() / 2;
                List<BookingNoteCargo> cargos = new ArrayList<BookingNoteCargo>( count );
                StringBuilder cargoContainer = new StringBuilder();
                for( int i = 0; i < count; i++ ) {
                    BookingNoteCargo cargo = new BookingNoteCargo();
                    cargo.setBookingNote( note );
                    String[] xiangleis = td.getXiangLei().split( " " );
                    String[] xiangliangs = td.getXiangLiang().split( "   " );
                    cargo.setType( xiangxing.substring( i * 2, i * 2 + 2 ) );
                    cargo.setCategory( xiangleis[i] );
                    cargo.setAmount( Integer.parseInt( xiangliangs[i].substring( 2 ) ) );
                    cargos.add( cargo );
                    cargoContainer.append( ( i > 0 ) ? "+" : "" )
                            .append( cargo.getCategory() )
                            .append( cargo.getType() )
                            .append( "*" )
                            .append( cargo.getAmount() );
                }
                note.setCargos( cargos );
                note.setCargoContainer( cargoContainer.toString() );
            }
        } catch( Exception e ) {}
    }
    
    private static int settled = 0;
    private static int getSettledState() {
        if( settled > 0 ) {
            return settled;
        } else {
            settled = BookingNoteChargeState.setChecked( settled, FinanceModel.INCOME, FinanceModel.NATIVE );
            settled = BookingNoteChargeState.setChecked( settled, FinanceModel.EXPENSE, FinanceModel.NATIVE );
            settled = BookingNoteChargeState.setChecked( settled, FinanceModel.NATIVE  );
            settled = BookingNoteChargeState.setReturned( settled );
            return settled;
        }
    }
    
    private static int processing = 0;
    private static int getProcessingState() {
        if( processing > 0 ) {
            return processing;
        } else {
            processing = BookingNoteChargeState.setEntering( processing, FinanceModel.INCOME, FinanceModel.NATIVE );
            processing = BookingNoteChargeState.setEntering( processing, FinanceModel.EXPENSE, FinanceModel.NATIVE );
            processing = BookingNoteChargeState.setEntering( processing, FinanceModel.NATIVE );
            processing = BookingNoteChargeState.setUnreturned( processing );
            return processing;
        }
    }
    
    private static void initializeBookingNoteTable() {
        SqlSession mysqlSession = ServiceContextUtils.getService( "sqlSession", SqlSession.class );
        // 首先清空托单表数据
        mysqlSession.delete( "clear-logistics-bookingnote" );
        mysqlSession.delete( "clear-finance-charge" );
        mysqlSession.delete( "clear-finance-invoice" );
        mysqlSession.delete( "clear-finance-account" );
        
        // 其次改变唛头列的宽度，以适应原数据
        mysqlSession.update( "alter-logistics-bookingnote-shippingmark" );
        mysqlSession.update( "alter-logistics-bookingnote-cargoname" );
    }
    
    private static void createDeletedBookingNote( int index, Date createdDate ) {
        BookingNote note = new BookingNote();
        note.setType( BookingNote.TYPE_SEA_EXPORT );
        note.getCustomer().setId( "" );
        note.setCreatedDate( createdDate );
        note.setState( BookingNoteState.setDeleted( 0 ) );
        BookingNoteService service = ServiceContextUtils.getService( BookingNoteService.class );
        service.createBookingNote( note );
    }
}
