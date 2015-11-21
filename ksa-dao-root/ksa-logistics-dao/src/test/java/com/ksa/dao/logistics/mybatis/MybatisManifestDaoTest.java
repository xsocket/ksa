package com.ksa.dao.logistics.mybatis;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.ksa.dao.MybatisDaoTest;
import com.ksa.dao.logistics.ManifestDao;
import com.ksa.model.logistics.Manifest;

public class MybatisManifestDaoTest extends MybatisDaoTest {
    
    @Test
    public void testCrudManifest() throws RuntimeException {
        ManifestDao dao = CONTEXT.getBean( "manifestDao", ManifestDao.class );
        
        Manifest bill = new Manifest();        
        String id = UUID.randomUUID().toString();
        bill.setId( id );
        bill.getBookingNote().setId( "test-bookingnote-1" );
        
        bill.setSaler( "saler" );
        bill.setCode( "code" );
        bill.setFlightDate( "createdDate" );
        bill.setLoadingPort( "departurePort" );
        bill.setDestinationPort( "destinationPort" );
        bill.setAgent( "agent" );

        bill.setHawb( "hawb" );
        bill.setCargoName( "cargoName" );
        bill.setCargoWeight( "cargoWeight" );
        bill.setFinalDestination( "finalDestination" );
        bill.setShipper( "shipper" );
        bill.setConsignee( "consignee" );
        bill.setRe( "re" );
        
        bill.setTotalHawb( "totalVolumn" );
        bill.setTotalPackages( "totalWeight" );
        
        // 测试插入是否成功
        dao.insertLogisticsModel( bill );        
        Manifest temp = ( Manifest ) ( dao.selectLogisticsModelById(id) );
        Assert.assertEquals( "test-bookingnote-1", temp.getBookingNote().getId() );

        Assert.assertEquals( "saler", temp.getSaler() );
        Assert.assertEquals( "code", temp.getCode() );
        Assert.assertEquals( "createdDate", temp.getFlightDate() );
        Assert.assertEquals( "departurePort", temp.getLoadingPort() );
        Assert.assertEquals( "destinationPort", temp.getDestinationPort() );
        Assert.assertEquals( "agent", temp.getAgent() );
        
        Assert.assertEquals( "shipper", temp.getShipper() );
        Assert.assertEquals( "consignee", temp.getConsignee() );
        Assert.assertEquals( "re", temp.getRe() );
        
        Assert.assertEquals( "hawb", temp.getHawb() );
        Assert.assertEquals( "cargoName", temp.getCargoName() );
        Assert.assertEquals( "cargoWeight", temp.getCargoWeight() );
        Assert.assertEquals( "finalDestination", temp.getFinalDestination() );
        Assert.assertEquals( "totalVolumn", temp.getTotalHawb() );
        Assert.assertEquals( "totalWeight", temp.getTotalPackages() );
        
        // 开始更新
        bill.getBookingNote().setId( "test-bookingnote-2" );
        
        bill.setSaler( "saler1" );
        bill.setCode( "code1" );
        bill.setFlightDate( "createdDate1" );
        bill.setLoadingPort( "departurePort1" );
        bill.setDestinationPort( "destinationPort1" );
        bill.setAgent( "agent1" );

        bill.setHawb( "hawb1" );
        bill.setCargoName( "cargoName1" );
        bill.setCargoWeight( "cargoWeight1" );
        bill.setFinalDestination( "finalDestination1" );
        bill.setShipper( "shipper1" );
        bill.setConsignee( "consignee1" );
        bill.setRe( "re1" );
        
        bill.setTotalHawb( "totalVolumn1" );
        bill.setTotalPackages( "totalWeight1" );
        
        // 测试插入是否成功
        dao.updateLogisticsModel( bill );        
        temp = ( Manifest ) ( dao.selectLogisticsModelById(id) );
        Assert.assertEquals( "test-bookingnote-1", temp.getBookingNote().getId() );

        Assert.assertEquals( "saler1", temp.getSaler() );
        Assert.assertEquals( "code1", temp.getCode() );
        Assert.assertEquals( "createdDate1", temp.getFlightDate() );
        Assert.assertEquals( "departurePort1", temp.getLoadingPort() );
        Assert.assertEquals( "destinationPort1", temp.getDestinationPort() );
        Assert.assertEquals( "agent1", temp.getAgent() );
        
        Assert.assertEquals( "shipper1", temp.getShipper() );
        Assert.assertEquals( "consignee1", temp.getConsignee() );
        Assert.assertEquals( "re1", temp.getRe() );
        
        Assert.assertEquals( "hawb1", temp.getHawb() );
        Assert.assertEquals( "cargoName1", temp.getCargoName() );
        Assert.assertEquals( "cargoWeight1", temp.getCargoWeight() );
        Assert.assertEquals( "finalDestination1", temp.getFinalDestination() );
        Assert.assertEquals( "totalVolumn1", temp.getTotalHawb() );
        Assert.assertEquals( "totalWeight1", temp.getTotalPackages() );
    }

}
