package com.ksa.dao.logistics.mybatis;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.logistics.BookingNoteCargoDao;
import com.ksa.dao.logistics.BookingNoteDao;
import com.ksa.model.bd.Partner;
import com.ksa.model.logistics.BookingNote;
import com.ksa.model.logistics.BookingNoteCargo;
import com.ksa.model.logistics.BookingNoteState;
import com.ksa.model.security.User;

public class MybatisBookingNoteDaoTest extends MybatisDaoTest {
    
    private static User TEST_USER1 = new User();
    private static User TEST_USER2 = new User();
    private static Partner TEST_PARTNER1 = new Partner();
    private static Partner TEST_PARTNER2 = new Partner();
    static {
        TEST_USER1.setId( "test-user-1" );
        TEST_USER2.setId( "test-user-2" );
        TEST_PARTNER1.setId( "test-partner-1" );
        TEST_PARTNER2.setId( "test-partner-2" );
    }
    
    @Test
    public void testCrudBookingNote() throws RuntimeException {
        BookingNoteDao noteDao = CONTEXT.getBean( "bookingNoteDao", BookingNoteDao.class );
        BookingNoteCargoDao cargoDao = CONTEXT.getBean( "bookingNoteCargoDao", BookingNoteCargoDao.class );
        
        BookingNote note = new BookingNote();        
        String id = UUID.randomUUID().toString();
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        note.setId( id );
        note.setCode( "code" );
        note.setType( "se" );
        note.setSubType( "fcl" );
        note.setSerialNumber( 1 );
        note.setCustomer( TEST_PARTNER1 );
        note.setInvoiceNumber( "invoiceNumber" );
        note.setCreatedDate( now );
        
        note.setSaler( TEST_USER1 );
        note.setCreator( TEST_USER1 );
        note.setCarrier( TEST_PARTNER1 );
        note.setShippingAgent( TEST_PARTNER1 );
        note.getAgent().setId( "aaa" );
        
        note.setCargoName( "cargoName" );
        note.setCargoNote( "cargoNote" );
        note.setCargoDescription( "cargoDescription" );
        note.setCargoContainer( "cargoContainer" );
        note.setShippingMark( "shippingMark" );
        note.setVolumn( 1.0f );
        note.setWeight( 2.0f );
        note.setQuantity( 3 );
        note.setUnit( "unit" );
        note.setQuantityDescription( "quantityDescription" );
        
        note.setTitle( "title" );
        note.setMawb( "mawb" );
        note.setHawb( "hawb" );
        note.setShipper( TEST_PARTNER1 );
        note.setConsignee( TEST_PARTNER1 );
        note.setNotify( TEST_PARTNER1 );
        
        note.setDeparture( "departure" );
        note.setDepartureDate( now );
        note.setDeparturePort( "departurePort" );
        note.setDestination("destination" );
        note.setDestinationDate( now );
        note.setDestinationPort( "destinationPort" );
        note.setLoadingPort( "loadingPort" );
        note.setDischargePort( "dischargePort" );
        note.setDeliverDate( now );
        note.setRoute( "route" );
        note.setRouteCode( "routeCode" );
        note.setRouteName( "routeName" );
        
        note.setCustomsBroker( TEST_PARTNER1 );
        note.setCustomsCode( "customsCode" );
        note.setCustomsDate( now );
        note.setReturnCode( "returnCode" );
        note.setReturnDate( now );
        note.setReturnDate2( now );
        note.setTaxCode( "taxCode" );
        note.setTaxDate1( now );
        note.setTaxDate2( now );
        note.setExpressCode( "expressCode" );
        
        note.setVehicleType( "vehicleType" );        
        note.setVehicleNumber( "vehicleNumber" );
        note.setVehicleTeam( TEST_PARTNER1 );
        
        note.setState( 4 );
        
        // 测试插入是否成功
        noteDao.insertBookingNote( note );        
        BookingNote temp = noteDao.selectBookingNoteById( id );
        Assert.assertEquals( "code", temp.getCode() );
        Assert.assertEquals( "se", temp.getType() );
        Assert.assertEquals( "fcl", temp.getSubType() );
        Assert.assertEquals( 1, temp.getSerialNumber() );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getCustomer().getId() );
        Assert.assertEquals( "invoiceNumber", temp.getInvoiceNumber() );
        Assert.assertEquals( df.format( now ), df.format( temp.getCreatedDate() ) );
        
        Assert.assertEquals( TEST_USER1.getId(), temp.getSaler().getId() );
        Assert.assertEquals( TEST_USER1.getId(), temp.getCreator().getId() );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getCarrier().getId() );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getShippingAgent().getId() );
        Assert.assertEquals( "aaa", temp.getAgent().getId() );
        
        Assert.assertEquals( "cargoName", temp.getCargoName() );
        Assert.assertEquals( "cargoNote", temp.getCargoNote() );
        Assert.assertEquals( "cargoDescription", temp.getCargoDescription() );
        Assert.assertEquals( "cargoContainer", temp.getCargoContainer() );
        Assert.assertEquals( "shippingMark", temp.getShippingMark() );
        Assert.assertEquals( 1.0f, temp.getVolumn(), 0.001 );
        Assert.assertEquals( 2.0f, temp.getWeight(), 0.001 );
        Assert.assertEquals( 3, temp.getQuantity().intValue() );
        Assert.assertEquals( "unit", temp.getUnit() );
        Assert.assertEquals( "quantityDescription", temp.getQuantityDescription() );

        Assert.assertEquals( "title", temp.getTitle() );
        Assert.assertEquals( "mawb", temp.getMawb() );
        Assert.assertEquals( "hawb", temp.getHawb() );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getShipper().getId() );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getConsignee().getId() );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getNotify().getId() );
        
        Assert.assertEquals( "departure", temp.getDeparture() );
        Assert.assertEquals( "departurePort", temp.getDeparturePort() );
        Assert.assertEquals( df.format( now ), df.format( temp.getDepartureDate() ) );
        Assert.assertEquals( "destination", temp.getDestination() );
        Assert.assertEquals( "destinationPort", temp.getDestinationPort() );
        Assert.assertEquals( df.format( now ), df.format( temp.getDestinationDate() ) );
        Assert.assertEquals( "loadingPort", temp.getLoadingPort() );
        Assert.assertEquals( "dischargePort", temp.getDischargePort() );
        Assert.assertEquals( df.format( now ), df.format( temp.getDeliverDate() ) );
        
        Assert.assertEquals( "route", temp.getRoute() );
        Assert.assertEquals( "routeName", temp.getRouteName() );
        Assert.assertEquals( "routeCode", temp.getRouteCode() );
        
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getCustomsBroker().getId() );
        Assert.assertEquals( "customsCode", temp.getCustomsCode() );
        Assert.assertEquals( df.format( now ), df.format( temp.getCustomsDate() ) );
        Assert.assertEquals( "returnCode", temp.getReturnCode() );
        Assert.assertEquals( df.format( now ), df.format( temp.getReturnDate() ) );
        Assert.assertEquals( df.format( now ), df.format( temp.getReturnDate2() ) );
        Assert.assertEquals( "taxCode", temp.getTaxCode() );
        Assert.assertEquals( df.format( now ), df.format( temp.getTaxDate2() ) );
        Assert.assertEquals( "expressCode", temp.getExpressCode() );
        Assert.assertEquals( df.format( now ), df.format( temp.getTaxDate1() ) );

        Assert.assertEquals( "vehicleType", temp.getVehicleType() );
        Assert.assertEquals( "vehicleNumber", temp.getVehicleNumber() );
        Assert.assertEquals( TEST_PARTNER1.getId(), temp.getVehicleTeam().getId() );
        
        Assert.assertEquals( 4, temp.getState() );
        
        
        // 开始更新
        now = new Date();
        note.setCode( "code1" );
        note.setType( "ae" );
        note.setSubType( "lcl" );
        note.setSerialNumber( 2 );
        note.setCustomer( TEST_PARTNER2 );
        note.setInvoiceNumber( "invoiceNumber1" );
        note.setCreatedDate( now );
        
        note.setSaler( TEST_USER2 );
        note.setCreator( TEST_USER2 );
        note.setCarrier( TEST_PARTNER2 );
        note.setShippingAgent( TEST_PARTNER2 );
        note.getAgent().setId( "bbb" );
        
        note.setCargoName( "cargoName1" );
        note.setCargoNote( "cargoNote1" );
        note.setCargoDescription( "cargoDescription1" );
        note.setCargoContainer( "cargoContainer1" );
        note.setShippingMark( "shippingMark1" );
        note.setVolumn( 2.0f );
        note.setWeight( 3.0f );
        note.setQuantity( 4 );
        note.setUnit( "unit1" );
        note.setQuantityDescription( "quantityDescription1" );
        
        note.setTitle( "title1" );
        note.setMawb( "mawb1" );
        note.setHawb( "hawb1" );
        note.setShipper( TEST_PARTNER2 );
        note.setConsignee( TEST_PARTNER2 );
        note.setNotify( TEST_PARTNER2 );
        
        note.setDeparture( "departure1" );
        note.setDepartureDate( now );
        note.setDeparturePort( "departurePort1" );
        note.setDestination("destination1" );
        note.setDestinationDate( now );
        note.setDestinationPort( "destinationPort1" );
        note.setLoadingPort( "loadingPort1" );
        note.setDischargePort( "dischargePort1" );
        note.setDeliverDate( now );
        note.setRoute( "route1" );
        note.setRouteCode( "routeCode1" );
        note.setRouteName( "routeName1" );
        
        note.setCustomsBroker( TEST_PARTNER2 );
        note.setCustomsCode( "customsCode1" );
        note.setCustomsDate( now );
        note.setReturnCode( "returnCode1" );
        note.setReturnDate( now );
        note.setReturnDate2( now );
        note.setTaxCode( "taxCode1" );
        note.setTaxDate1( now );
        note.setTaxDate2( now );
        note.setExpressCode( "expressCode1" );
        
        note.setVehicleType( "vehicleType1" );        
        note.setVehicleNumber( "vehicleNumber1" );
        note.setVehicleTeam( TEST_PARTNER2 );
        
        note.setState( 5 );
        
        // 测试更新是否成功
        noteDao.updateBookingNote( note );        
        temp = noteDao.selectBookingNoteById( id );
        Assert.assertEquals( "code", temp.getCode() );
        Assert.assertEquals( "se", temp.getType() );
        Assert.assertEquals( "lcl", temp.getSubType() );
        Assert.assertEquals( 1, temp.getSerialNumber() );
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getCustomer().getId() );
        Assert.assertEquals( "invoiceNumber1", temp.getInvoiceNumber() );
        Assert.assertEquals( df.format( now ), df.format( temp.getCreatedDate() ) );
        
        Assert.assertEquals( TEST_USER2.getId(), temp.getSaler().getId() );
        Assert.assertEquals( TEST_USER1.getId(), temp.getCreator().getId() );   // 创建人不能变更
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getCarrier().getId() );
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getShippingAgent().getId() );
        Assert.assertEquals( "bbb", temp.getAgent().getId() );
        
        Assert.assertEquals( "cargoName1", temp.getCargoName() );
        Assert.assertEquals( "cargoNote1", temp.getCargoNote() );
        Assert.assertEquals( "cargoDescription1", temp.getCargoDescription() );
        Assert.assertEquals( "cargoContainer1", temp.getCargoContainer() );
        Assert.assertEquals( "shippingMark1", temp.getShippingMark() );
        Assert.assertEquals( 2.0f, temp.getVolumn(), 0.001 );
        Assert.assertEquals( 3.0f, temp.getWeight(), 0.001 );
        Assert.assertEquals( 4, temp.getQuantity().intValue() );
        Assert.assertEquals( "unit1", temp.getUnit() );
        Assert.assertEquals( "quantityDescription1", temp.getQuantityDescription() );

        Assert.assertEquals( "title1", temp.getTitle() );
        Assert.assertEquals( "mawb1", temp.getMawb() );
        Assert.assertEquals( "hawb1", temp.getHawb() );
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getShipper().getId() );
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getConsignee().getId() );
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getNotify().getId() );
        
        Assert.assertEquals( "departure1", temp.getDeparture() );
        Assert.assertEquals( "departurePort1", temp.getDeparturePort() );
        Assert.assertEquals( df.format( now ), df.format( temp.getDepartureDate() ) );
        Assert.assertEquals( "destination1", temp.getDestination() );
        Assert.assertEquals( "destinationPort1", temp.getDestinationPort() );
        Assert.assertEquals( df.format( now ), df.format( temp.getDestinationDate() ) );
        Assert.assertEquals( "loadingPort1", temp.getLoadingPort() );
        Assert.assertEquals( "dischargePort1", temp.getDischargePort() );
        Assert.assertEquals( df.format( now ), df.format( temp.getDeliverDate() ) );
        
        Assert.assertEquals( "route1", temp.getRoute() );
        Assert.assertEquals( "routeName1", temp.getRouteName() );
        Assert.assertEquals( "routeCode1", temp.getRouteCode() );
        
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getCustomsBroker().getId() );
        Assert.assertEquals( "customsCode1", temp.getCustomsCode() );
        Assert.assertEquals( df.format( now ), df.format( temp.getCustomsDate() ) );
        Assert.assertEquals( "returnCode1", temp.getReturnCode() );
        Assert.assertEquals( df.format( now ), df.format( temp.getReturnDate() ) );
        Assert.assertEquals( df.format( now ), df.format( temp.getReturnDate2() ) );
        Assert.assertEquals( "taxCode1", temp.getTaxCode() );
        Assert.assertEquals( df.format( now ), df.format( temp.getTaxDate2() ) );
        Assert.assertEquals( "expressCode1", temp.getExpressCode() );
        Assert.assertEquals( df.format( now ), df.format( temp.getTaxDate1() ) );

        Assert.assertEquals( "vehicleType1", temp.getVehicleType() );
        Assert.assertEquals( "vehicleNumber1", temp.getVehicleNumber() );
        Assert.assertEquals( TEST_PARTNER2.getId(), temp.getVehicleTeam().getId() );
        
        // 更新时不修改托单状态，在 updateBookingNoteState 中单独进行更改
        Assert.assertEquals( 4, temp.getState() );
        
        
        // 测试删除
        noteDao.deleteBookingNote( note );
        BookingNote temp3 = noteDao.selectBookingNoteById( id );
        Assert.assertTrue( temp3.getState() == BookingNoteState.DELETED );
        
    
// 测试 Cargo 详细信息的增删改查
        BookingNoteCargo cargo = new BookingNoteCargo();
        String cargoId = UUID.randomUUID().toString();
        cargo.setId( cargoId );
        cargo.setAmount( 1 );
        cargo.setCategory( "category" );
        cargo.setType( "type" );
        cargo.setName( "name" );
        cargo.setBookingNote( note );
            // 增
        cargoDao.insertCargo( cargo );
        BookingNoteCargo cargoTemp =cargoDao.selectCargoById( cargoId );
        Assert.assertEquals( "category", cargoTemp.getCategory() );
        Assert.assertEquals( "type", cargoTemp.getType() );
        Assert.assertEquals( "name", cargoTemp.getName() );
        Assert.assertEquals( 1, cargoTemp.getAmount() );
        Assert.assertEquals( id, cargoTemp.getBookingNote().getId() );
            // 更新
        cargo.setAmount( 2 );
        cargo.setCategory( "category1" );
        cargo.setType( "type1" );
        cargo.setName( "name1" );
        cargoDao.updateCargo( cargo );
        cargoTemp =cargoDao.selectCargoById( cargoId );
        Assert.assertEquals( "category1", cargoTemp.getCategory() );
        Assert.assertEquals( "type1", cargoTemp.getType() );
        Assert.assertEquals( "name1", cargoTemp.getName() );
        Assert.assertEquals( 2, cargoTemp.getAmount() );
        Assert.assertEquals( id, cargoTemp.getBookingNote().getId() );
        
        temp = noteDao.selectBookingNoteById( id );
        Assert.assertEquals( 1, temp.getCargos().size() );
        cargoTemp = temp.getCargos().get( 0 );
        Assert.assertEquals( "category1", cargoTemp.getCategory() );
        Assert.assertEquals( "type1", cargoTemp.getType() );
        Assert.assertEquals( "name1", cargoTemp.getName() );
        Assert.assertEquals( 2, cargoTemp.getAmount() );
        
            // 删除
        cargoDao.deleteCargo( cargo );
        cargoTemp =cargoDao.selectCargoById( cargoId );
        Assert.assertNull( cargoTemp );        
        
    }

}
