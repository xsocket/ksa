package com.ksa.dao.logistics.mybatis;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.logistics.WarehouseNotingDao;
import com.ksa.model.logistics.WarehouseNoting;

public class MybatisWarehouseNotingDaoTest extends MybatisDaoTest {
    
    @Test
    public void testCrudBillOfLading() throws RuntimeException {
        WarehouseNotingDao dao = CONTEXT.getBean( "warehouseNotingDao", WarehouseNotingDao.class );
        
        WarehouseNoting bill = new WarehouseNoting();        
        String id = UUID.randomUUID().toString();
        bill.setId( id );
        bill.getBookingNote().setId( "test-bookingnote-1" );
        bill.setCargoName( "cargoName" );
        bill.setCargoQuantity( "cargoQuantity" );
        bill.setCargoVolumn( "cargoVolumn" );
        bill.setCargoWeight( "cargoWeight" );
        bill.setCode( "code" );
        bill.setContact( "contact" );
        bill.setCreatedDate( "createdDate" );
        bill.setCustomer( "customer" );
        bill.setDepartureDate( "departureDate" );
        bill.setDestination( "destination" );
        bill.setDischargePort( "dischargePort" );
        bill.setEntryDate( "entryDate" );
        bill.setFax( "fax" );
        bill.setInformDate( "informDate" );
        bill.setMawb( "mawb" );
        bill.setLoadingPort( "loadingPort" );
        bill.setSaler( "saler" );
        bill.setTelephone( "telephone" );
        bill.setVesselVoyage( "vesselVoyage" );
        bill.setTo("to");
        
        // 测试插入是否成功
        dao.insertLogisticsModel( bill );        
        WarehouseNoting temp = ( WarehouseNoting ) ( dao.selectLogisticsModelById(id) );
        Assert.assertEquals( "test-bookingnote-1", temp.getBookingNote().getId() );
        Assert.assertEquals( "cargoName", temp.getCargoName() );
        Assert.assertEquals( "cargoQuantity", temp.getCargoQuantity() );
        Assert.assertEquals( "cargoVolumn", temp.getCargoVolumn() );
        Assert.assertEquals( "cargoWeight", temp.getCargoWeight() );
        Assert.assertEquals( "code", temp.getCode() );
        Assert.assertEquals( "contact", temp.getContact() );
        Assert.assertEquals( "createdDate", temp.getCreatedDate() );
        Assert.assertEquals( "customer", temp.getCustomer() );
        Assert.assertEquals( "departureDate", temp.getDepartureDate() );
        Assert.assertEquals( "destination", temp.getDestination() );
        Assert.assertEquals( "dischargePort", temp.getDischargePort() );
        Assert.assertEquals( "loadingPort", temp.getLoadingPort() );
        Assert.assertEquals( "entryDate", temp.getEntryDate() );
        Assert.assertEquals( "fax", temp.getFax() );
        Assert.assertEquals( "informDate", temp.getInformDate() );
        Assert.assertEquals( "mawb", temp.getMawb() );
        Assert.assertEquals( "saler", temp.getSaler() );
        Assert.assertEquals( "telephone", temp.getTelephone() );
        Assert.assertEquals( "vesselVoyage", temp.getVesselVoyage() );
        Assert.assertEquals( "to", temp.getTo() );
        
        // 开始更新
        bill.getBookingNote().setId( "test-bookingnote-2" );
        bill.setCargoName( "cargoName1" );
        bill.setCargoQuantity( "cargoQuantity1" );
        bill.setCargoVolumn( "cargoVolumn1" );
        bill.setCargoWeight( "cargoWeight1" );
        bill.setCode( "code1" );
        bill.setContact( "contact1" );
        bill.setCreatedDate( "createdDate1" );
        bill.setCustomer( "customer1" );
        bill.setDepartureDate( "departureDate1" );
        bill.setDestination( "destination1" );
        bill.setDischargePort( "dischargePort1" );
        bill.setEntryDate( "entryDate1" );
        bill.setFax( "fax1" );
        bill.setInformDate( "informDate1" );
        bill.setMawb( "mawb1" );
        bill.setLoadingPort( "loadingPort1" );
        bill.setSaler( "saler1" );
        bill.setTelephone( "telephone1" );
        bill.setVesselVoyage( "vesselVoyage1" );
        bill.setTo("to1");
        
        // 测试插入是否成功
        dao.updateLogisticsModel( bill );        
        temp = ( WarehouseNoting ) ( dao.selectLogisticsModelById(id) );
        Assert.assertEquals( "test-bookingnote-1", temp.getBookingNote().getId() );
        Assert.assertEquals( "cargoName1", temp.getCargoName() );
        Assert.assertEquals( "cargoQuantity1", temp.getCargoQuantity() );
        Assert.assertEquals( "cargoVolumn1", temp.getCargoVolumn() );
        Assert.assertEquals( "cargoWeight1", temp.getCargoWeight() );
        Assert.assertEquals( "code1", temp.getCode() );
        Assert.assertEquals( "contact1", temp.getContact() );
        Assert.assertEquals( "createdDate1", temp.getCreatedDate() );
        Assert.assertEquals( "customer1", temp.getCustomer() );
        Assert.assertEquals( "departureDate1", temp.getDepartureDate() );
        Assert.assertEquals( "destination1", temp.getDestination() );
        Assert.assertEquals( "dischargePort1", temp.getDischargePort() );
        Assert.assertEquals( "loadingPort1", temp.getLoadingPort() );
        Assert.assertEquals( "entryDate1", temp.getEntryDate() );
        Assert.assertEquals( "fax1", temp.getFax() );
        Assert.assertEquals( "informDate1", temp.getInformDate() );
        Assert.assertEquals( "mawb1", temp.getMawb() );
        Assert.assertEquals( "saler1", temp.getSaler() );
        Assert.assertEquals( "telephone1", temp.getTelephone() );
        Assert.assertEquals( "vesselVoyage1", temp.getVesselVoyage() );
        Assert.assertEquals( "to1", temp.getTo() );
    }

}
