package com.ksa.web.struts2.action.logistics.billoflading;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.Resource;

import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.logistics.BillOfLadingDao;
import com.ksa.model.bd.BasicData;
import com.ksa.model.bd.BasicDataType;
import com.ksa.model.logistics.BillOfLading;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.bd.util.BasicDataUtils;
import com.ksa.util.ResourceUtils;
import com.ksa.util.StringUtils;
import com.ksa.web.struts2.action.logistics.LogisticsModelDownloadAction;

public class BillOfLadingPoiDownloadAction extends LogisticsModelDownloadAction<BillOfLading> {

    private static final long serialVersionUID = 2111047252416186265L;

    protected String getWorkbookTemplateLocation() {
        return "classpath:ui/logistics/tmpl/billoflading.xls";
    }
    
    @Override
    protected String doExecute() throws Exception {
        String result = super.doExecute();
        if( !SUCCESS.equals( result ) ) {
            return result;
        }
        
        Resource tmpl = ResourceUtils.getResource( getWorkbookTemplateLocation() );
        Workbook workbook = new HSSFWorkbook( new POIFSFileSystem( tmpl.getInputStream() ) );
        
        processWorkbook( workbook, getModel() );
        
        outputWorkbook( workbook );
        
        return NONE;
    }
    
    protected void processWorkbook( Workbook workbook, BillOfLading model ) {
        Sheet sheet = workbook.getSheetAt( 0 );
        BookingNote note = model.getBookingNote();
        
        String hawb_mawb = "";
        if( StringUtils.hasText( note.getHawb() ) ) {
            hawb_mawb += note.getHawb();
        }
        if( StringUtils.hasText( note.getMawb() ) ) {
            hawb_mawb += ( "\n\r" + note.getMawb() );
        }
        
        sheet.getRow( 2 ).getCell( 34 ).setCellValue( hawb_mawb );    // 主分单导出后希望如下 两行显示
        sheet.getRow( 3 ).getCell( 4 ).setCellValue( model.getShipper() );
        sheet.getRow( 8 ).getCell( 4 ).setCellValue( model.getConsignee() );
        sheet.getRow( 16 ).getCell( 4 ).setCellValue( model.getNotify() );
        if( StringUtils.hasText( note.getDeparturePort() ) ) {
            sheet.getRow( 21 ).getCell( 17 ).setCellValue( getPortAlias( note.getDeparturePort() ) );
        }
        sheet.getRow( 23 ).getCell( 4 ).setCellValue( model.getVesselVoyage() );
        sheet.getRow( 23 ).getCell( 17 ).setCellValue( model.getLoadingPort() );
        sheet.getRow( 25 ).getCell( 4 ).setCellValue( model.getDischargePort() );
        sheet.getRow( 25 ).getCell( 17 ).setCellValue( model.getDestinationPort() );
        sheet.getRow( 21 ).getCell( 28 ).setCellValue( model.getAgent() );  // 此处需显示目的港代理信息。
        sheet.getRow( 25 ).getCell( 28 ).setCellValue( model.getDestinationPort() );
        
        sheet.getRow( 28 ).getCell( 4 ).setCellValue( (StringUtils.hasText( model.getCargoMark() ) ? model.getCargoMark() : "") + "\n\r\n\r\n\r" + (StringUtils.hasText( model.getCargoDescription() ) ? model.getCargoDescription() : "") );
        sheet.getRow( 28 ).getCell( 17 ).setCellValue( model.getCargoQuantity() );
        sheet.getRow( 28 ).getCell( 22 ).setCellValue( model.getCargoName() );
        if( StringUtils.hasText( model.getCargoWeight() ) ) {
            sheet.getRow( 28 ).getCell( 38 ).setCellValue( model.getCargoWeight() + " KGS" );
        }
        if( StringUtils.hasText( model.getCargoVolumn() ) ) {
            sheet.getRow( 28 ).getCell( 46 ).setCellValue( model.getCargoVolumn() + " M3" );
        }
        sheet.getRow( 42 ).getCell( 17 ).setCellValue( model.getCargoQuantityDescription() );
    }
    
    protected void outputWorkbook( Workbook wb ) throws IOException {
        HttpServletResponse oResponse = ServletActionContext.getResponse();

        oResponse.setContentType( "application/vnd.ms-excel" );
        oResponse.addHeader( "Content-Disposition", "attachment;filename=\"" + getFilename() + "\"" );
        
        OutputStream oOutput = null;
        try {
            oOutput = oResponse.getOutputStream();
            wb.write( oOutput );
            oOutput.flush();
        }
        finally {           
            if (oOutput != null) oOutput.close();
        }
    }

    @Override
    public BillOfLading getModel() {
        if( model == null ) {
            model = new BillOfLading();   
        }
        return model;
    }
    
    @Override
    protected void postInitalModel( BookingNote note ) {
        model.setDestinationPort( getPortAlias( model.getDestinationPort() ) );
        model.setLoadingPort( getPortAlias( model.getLoadingPort() ) );
        model.setDischargePort( getPortAlias( model.getDischargePort() ) );
        //model.set
        if( note.getQuantity() != null ) {
            model.setCargoQuantity( note.getQuantity() + " " );
        }
        if( StringUtils.hasText( note.getUnit() ) ) {
            model.setCargoQuantity( model.getCargoQuantity() + getUnitAlias( note.getUnit() ) );
        }
        
        
    }
    
    private String getPortAlias( String portName ) {
        if( StringUtils.hasText( portName ) ) {
            BasicData dPort = BasicDataUtils.getDataFromName( portName, BasicDataType.PORT_SEA.getId(), BasicDataType.PORT_AIR.getId() );
            if( dPort != null ) {
                return model.getAlias( dPort );
            }
        }
        return portName;
    }
    
    private String getUnitAlias( String unit ) {
        if( StringUtils.hasText( unit ) ) {
            BasicData dUnit = BasicDataUtils.getDataFromName( unit, BasicDataType.UNITS.getId()  );
            if( dUnit != null ) {
                return model.getAlias( dUnit );
            }
        }
        return unit;
    }

    @Override
    protected BillOfLadingDao getDao() {
        return ServiceContextUtils.getService( BillOfLadingDao.class );
    }

    @Override
    protected String doGetFilename() {
        if( ! StringUtils.hasText( customFilename ) ) {
            customFilename = "提单确认通知书";
        }
        return customFilename + " - " + model.getBookingNote().getCode();
    }
}
