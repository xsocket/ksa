package com.ksa.dao.logistics.mybatis;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.logistics.WarehouseBookingDao;
import com.ksa.model.logistics.WarehouseBooking;

public class MybatisWarehouseBookingDaoTest extends MybatisDaoTest {
    
    @Test
    public void testCrudWarehouseBooking() throws RuntimeException {
        WarehouseBookingDao dao = CONTEXT.getBean( "warehouseBookingDao", WarehouseBookingDao.class );
        
        WarehouseBooking bill = new WarehouseBooking();        
        String id = UUID.randomUUID().toString();
        bill.setId( id );
        bill.getBookingNote().setId( "test-bookingnote-1" );
        
        bill.setSaler( "saler" );
        bill.setShipper( "shipper" );
        bill.setConsignee( "consignee" );
        bill.setNotify( "notify" );
        
        bill.setCode( "code" );
        bill.setCreatedDate( "createdDate" );
        bill.setSwitchShip( "switchShip" );
        bill.setGrouping( "grouping" );
        bill.setDeparturePort( "departurePort" );
        bill.setDestinationPort( "destinationPort" );
        bill.setFreightCharge( "freightCharge" );
        bill.setTransportMode( "transportMode" );
        bill.setPaymentMode( "paymentMode" );
        
        bill.setCargoContainer( "cargoContainer" );
        bill.setShippingMark( "shippingMark" );
        bill.setCargoName( "cargoName" );
        bill.setCargoQuantity( "cargoQuantity" );
        bill.setCargoVolumn( "cargoVolumn" );
        bill.setCargoWeight( "cargoWeight" );
        bill.setTotalQuantity( "totalQuantity" );
        bill.setTotalVolumn( "totalVolumn" );
        bill.setTotalWeight( "totalWeight" );
        
        bill.setNote( "note" );
        
        // 测试插入是否成功
        dao.insertLogisticsModel( bill );        
        WarehouseBooking temp = ( WarehouseBooking ) ( dao.selectLogisticsModelById(id) );
        Assert.assertEquals( "test-bookingnote-1", temp.getBookingNote().getId() );
        

        Assert.assertEquals( "saler", temp.getSaler() );
        Assert.assertEquals( "shipper", temp.getShipper() );
        Assert.assertEquals( "consignee", temp.getConsignee() );
        Assert.assertEquals( "notify", temp.getNotify() );
        
        Assert.assertEquals( "code", temp.getCode() );
        Assert.assertEquals( "createdDate", temp.getCreatedDate() );
        Assert.assertEquals( "switchShip", temp.getSwitchShip() );
        Assert.assertEquals( "grouping", temp.getGrouping() );
        Assert.assertEquals( "freightCharge", temp.getFreightCharge() );
        Assert.assertEquals( "transportMode", temp.getTransportMode() );
        Assert.assertEquals( "paymentMode", temp.getPaymentMode() );
        Assert.assertEquals( "departurePort", temp.getDeparturePort() );
        Assert.assertEquals( "destinationPort", temp.getDestinationPort() );
        
        Assert.assertEquals( "cargoContainer", temp.getCargoContainer() );
        Assert.assertEquals( "shippingMark", temp.getShippingMark() );
        Assert.assertEquals( "cargoName", temp.getCargoName() );
        Assert.assertEquals( "cargoQuantity", temp.getCargoQuantity() );
        Assert.assertEquals( "cargoVolumn", temp.getCargoVolumn() );
        Assert.assertEquals( "cargoWeight", temp.getCargoWeight() );
        Assert.assertEquals( "totalQuantity", temp.getTotalQuantity() );
        Assert.assertEquals( "totalVolumn", temp.getTotalVolumn() );
        Assert.assertEquals( "totalWeight", temp.getTotalWeight() );
        
        Assert.assertEquals( "note", temp.getNote() );
        
        // 开始更新
        bill.getBookingNote().setId( "test-bookingnote-2" );
        bill.setSaler( "saler1" );
        bill.setShipper( "shipper1" );
        bill.setConsignee( "consignee1" );
        bill.setNotify( "notify1" );
        
        bill.setCode( "code1" );
        bill.setCreatedDate( "createdDate1" );
        bill.setSwitchShip( "switchShip1" );
        bill.setGrouping( "grouping1" );
        bill.setDeparturePort( "departurePort1" );
        bill.setDestinationPort( "destinationPort1" );
        bill.setFreightCharge( "freightCharge1" );
        bill.setTransportMode( "transportMode1" );
        bill.setPaymentMode( "paymentMode1" );
        
        bill.setCargoContainer( "cargoContainer1" );
        bill.setShippingMark( "shippingMark1" );
        bill.setCargoName( "cargoName1" );
        bill.setCargoQuantity( "cargoQuantity1" );
        bill.setCargoVolumn( "cargoVolumn1" );
        bill.setCargoWeight( "cargoWeight1" );
        bill.setTotalQuantity( "totalQuantity1" );
        bill.setTotalVolumn( "totalVolumn1" );
        bill.setTotalWeight( "totalWeight1" );
        
        bill.setNote( "note1" );
        
        // 测试插入是否成功
        dao.updateLogisticsModel( bill );        
        temp = ( WarehouseBooking ) ( dao.selectLogisticsModelById(id) );
        Assert.assertEquals( "test-bookingnote-1", temp.getBookingNote().getId() );
        

        Assert.assertEquals( "saler1", temp.getSaler() );
        Assert.assertEquals( "shipper1", temp.getShipper() );
        Assert.assertEquals( "consignee1", temp.getConsignee() );
        Assert.assertEquals( "notify1", temp.getNotify() );
        
        Assert.assertEquals( "code1", temp.getCode() );
        Assert.assertEquals( "createdDate1", temp.getCreatedDate() );
        Assert.assertEquals( "switchShip1", temp.getSwitchShip() );
        Assert.assertEquals( "grouping1", temp.getGrouping() );
        Assert.assertEquals( "freightCharge1", temp.getFreightCharge() );
        Assert.assertEquals( "transportMode1", temp.getTransportMode() );
        Assert.assertEquals( "paymentMode1", temp.getPaymentMode() );
        Assert.assertEquals( "departurePort1", temp.getDeparturePort() );
        Assert.assertEquals( "destinationPort1", temp.getDestinationPort() );
        
        Assert.assertEquals( "cargoContainer1", temp.getCargoContainer() );
        Assert.assertEquals( "shippingMark1", temp.getShippingMark() );
        Assert.assertEquals( "cargoName1", temp.getCargoName() );
        Assert.assertEquals( "cargoQuantity1", temp.getCargoQuantity() );
        Assert.assertEquals( "cargoVolumn1", temp.getCargoVolumn() );
        Assert.assertEquals( "cargoWeight1", temp.getCargoWeight() );
        Assert.assertEquals( "totalQuantity1", temp.getTotalQuantity() );
        Assert.assertEquals( "totalVolumn1", temp.getTotalVolumn() );
        Assert.assertEquals( "totalWeight1", temp.getTotalWeight() );
        
        Assert.assertEquals( "note1", temp.getNote() );
    }

}
