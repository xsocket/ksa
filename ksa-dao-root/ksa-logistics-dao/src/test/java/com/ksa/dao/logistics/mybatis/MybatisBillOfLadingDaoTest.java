package com.ksa.dao.logistics.mybatis;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.logistics.BillOfLadingDao;
import com.ksa.model.logistics.BillOfLading;

public class MybatisBillOfLadingDaoTest extends MybatisDaoTest {
    
    @Test
    public void testCrudBillOfLading() throws RuntimeException {
        BillOfLadingDao dao = CONTEXT.getBean( "billOfLadingDao", BillOfLadingDao.class );
        
        BillOfLading bill = new BillOfLading();        
        String id = UUID.randomUUID().toString();
        bill.setId( id );
        bill.setAgent( "agent" );
        bill.setBillType( "type" );
        bill.getBookingNote().setId( "test-bookingnote-1" );
        bill.setCargoDescription( "cargoDescription" );
        bill.setCargoMark( "cargoMark" );
        bill.setCargoName( "cargoName" );
        bill.setCargoQuantity( "cargoQuantity" );
        bill.setCargoVolumn( "cargoVolumn" );
        bill.setCargoWeight( "cargoWeight" );
        bill.setCode( "code" );
        bill.setConsignee( "consignee" );
        bill.setCreator( "creator" );
        bill.setCustomerCode( "customerCode" );
        bill.setDeliverType( "deliverType" );
        bill.setDestinationPort( "destinationPort" );
        bill.setDischargePort( "dischargePort" );
        bill.setLoadingPort( "loadingPort" );
        bill.setNote( "note" );
        bill.setNotify( "notify" );
        bill.setPublishDate( "publishDate" );
        bill.setSelfCode( "selfCode" );
        bill.setShipper( "shipper" );
        bill.setVesselVoyage( "vesselVoyage" );
        bill.setTo("to");
        
        // 测试插入是否成功
        dao.insertLogisticsModel( bill );        
        BillOfLading temp = ( BillOfLading ) ( dao.selectLogisticsModelById(id) );
        Assert.assertEquals( "agent", temp.getAgent() );
        Assert.assertEquals( "type", temp.getBillType() );
        Assert.assertEquals( "test-bookingnote-1", temp.getBookingNote().getId() );
        Assert.assertEquals( "cargoDescription", temp.getCargoDescription() );
        Assert.assertEquals( "cargoMark", temp.getCargoMark() );
        Assert.assertEquals( "cargoName", temp.getCargoName() );
        Assert.assertEquals( "cargoQuantity", temp.getCargoQuantity() );
        Assert.assertEquals( "cargoVolumn", temp.getCargoVolumn() );
        Assert.assertEquals( "cargoWeight", temp.getCargoWeight() );
        Assert.assertEquals( "code", temp.getCode() );
        Assert.assertEquals( "consignee", temp.getConsignee() );
        Assert.assertEquals( "creator", temp.getCreator() );
        Assert.assertEquals( "customerCode", temp.getCustomerCode() );
        Assert.assertEquals( "deliverType", temp.getDeliverType() );
        Assert.assertEquals( "destinationPort", temp.getDestinationPort() );
        Assert.assertEquals( "dischargePort", temp.getDischargePort() );
        Assert.assertEquals( "loadingPort", temp.getLoadingPort() );
        Assert.assertEquals( "note", temp.getNote() );
        Assert.assertEquals( "notify", temp.getNotify() );
        Assert.assertEquals( "publishDate", temp.getPublishDate() );
        Assert.assertEquals( "selfCode", temp.getSelfCode() );
        Assert.assertEquals( "shipper", temp.getShipper() );
        Assert.assertEquals( "vesselVoyage", temp.getVesselVoyage() );
        Assert.assertEquals( "to", temp.getTo() );
        
        
        // 开始更新
        bill.setAgent( "agent1" );
        bill.setBillType( "type1" );
        bill.getBookingNote().setId( "test-bookingnote-2" );
        bill.setCargoDescription( "cargoDescription1" );
        bill.setCargoMark( "cargoMark1" );
        bill.setCargoName( "cargoName1" );
        bill.setCargoQuantity( "cargoQuantity1" );
        bill.setCargoVolumn( "cargoVolumn1" );
        bill.setCargoWeight( "cargoWeight1" );
        bill.setCode( "code1" );
        bill.setConsignee( "consignee1" );
        bill.setCreator( "creator1" );
        bill.setCustomerCode( "customerCode1" );
        bill.setDeliverType( "deliverType1" );
        bill.setDestinationPort( "destinationPort1" );
        bill.setDischargePort( "dischargePort1" );
        bill.setLoadingPort( "loadingPort1" );
        bill.setNote( "note1" );
        bill.setNotify( "notify1" );
        bill.setPublishDate( "publishDate1" );
        bill.setSelfCode( "selfCode1" );
        bill.setShipper( "shipper1" );
        bill.setVesselVoyage( "vesselVoyage1" );
        bill.setTo("to1");
        
        // 测试更新是否成功
        dao.updateLogisticsModel( bill );        
        temp = ( BillOfLading ) ( dao.selectLogisticsModelById(id) );
        Assert.assertEquals( "agent1", temp.getAgent() );
        Assert.assertEquals( "type1", temp.getBillType() );
        Assert.assertEquals( "test-bookingnote-1", temp.getBookingNote().getId() );
        Assert.assertEquals( "cargoDescription1", temp.getCargoDescription() );
        Assert.assertEquals( "cargoMark1", temp.getCargoMark() );
        Assert.assertEquals( "cargoName1", temp.getCargoName() );
        Assert.assertEquals( "cargoQuantity1", temp.getCargoQuantity() );
        Assert.assertEquals( "cargoVolumn1", temp.getCargoVolumn() );
        Assert.assertEquals( "cargoWeight1", temp.getCargoWeight() );
        Assert.assertEquals( "code1", temp.getCode() );
        Assert.assertEquals( "consignee1", temp.getConsignee() );
        Assert.assertEquals( "creator1", temp.getCreator() );
        Assert.assertEquals( "customerCode1", temp.getCustomerCode() );
        Assert.assertEquals( "deliverType1", temp.getDeliverType() );
        Assert.assertEquals( "destinationPort1", temp.getDestinationPort() );
        Assert.assertEquals( "dischargePort1", temp.getDischargePort() );
        Assert.assertEquals( "loadingPort1", temp.getLoadingPort() );
        Assert.assertEquals( "note1", temp.getNote() );
        Assert.assertEquals( "notify1", temp.getNotify() );
        Assert.assertEquals( "publishDate1", temp.getPublishDate() );
        Assert.assertEquals( "selfCode1", temp.getSelfCode() );
        Assert.assertEquals( "shipper1", temp.getShipper() );
        Assert.assertEquals( "vesselVoyage1", temp.getVesselVoyage() );
        Assert.assertEquals( "to1", temp.getTo() );
    }

}
