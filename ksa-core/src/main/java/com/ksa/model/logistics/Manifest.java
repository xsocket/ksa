package com.ksa.model.logistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;


public class Manifest extends BaseLogisticsModel {

    private static final long serialVersionUID = 3056466545561955809L;
    
    /** 销售电话 */
    protected String salerTel = "88473507";
    /** 销售传真 */
    protected String salerFax = "88473508";
    /** 销售邮箱 */
    protected String salerEmail = "hangzhou@ksa.co.jp";
    
    protected String saler;
    protected String code;
    protected String loadingPort;
    protected String destinationPort;
    protected String flightDate;    // flight and date 
    protected String agent;    
    
    protected String hawb;
    protected String cargoWeight;
    protected String cargoName;
    protected String finalDestination;
    protected String shipper;
    protected String consignee;
    protected String re;
    
    protected String totalHawb;
    protected String totalPackages;

    @Override
    public void initialModel( BookingNote bookingNote ) {
        this.bookingNote = bookingNote;
        
        this.saler = bookingNote.getSaler().getName();
        // v3.4.2后 code 显示 mawb
        //this.code = bookingNote.getCode();
        this.code = bookingNote.getMawb();
//        this.loadingPort = bookingNote.getLoadingPort();
//        this.destinationPort = bookingNote.getDestinationPort();
        this.loadingPort = bookingNote.getDeparture();
        if(!StringUtils.hasText( this.loadingPort )) {
            this.loadingPort = bookingNote.getLoadingPort();
        }
        this.destinationPort = bookingNote.getDestination();
        if(!StringUtils.hasText( this.destinationPort )) {
            this.destinationPort = bookingNote.getDestinationPort();
        }
        // v3.4.2后 agent 属性存放 consigned to 属性
        // this.agent = bookingNote.getAgent().getName();
        
        StringBuilder sb = new StringBuilder();
        if( StringUtils.hasText( bookingNote.getRouteName() ) ) {
            sb.append( bookingNote.getRouteName() ).append( " " );
        }
        if( bookingNote.getDepartureDate() != null ) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            sb.append( df.format( bookingNote.getDepartureDate() ) );
        }
        this.flightDate = sb.toString();
        
        this.hawb = bookingNote.getHawb();
        this.cargoWeight = bookingNote.getWeight() != null ? ( bookingNote.getWeight().toString() + " KGS" ) : "";
        // v3.4.2新增
        if( bookingNote.getQuantity() != null ) {
            this.totalPackages = bookingNote.getQuantity() + " ";
        }
        if( StringUtils.hasText( bookingNote.getUnit() ) ) {
            this.totalPackages = this.totalPackages + bookingNote.getUnit();
        }
        this.cargoName = bookingNote.getCargoName();
        this.finalDestination = bookingNote.getDestination();
        this.shipper = bookingNote.getShipper().getAlias();
        this.consignee = bookingNote.getConsignee().getAlias();         
    }

    
    public String getSaler() {
        return saler;
    }

    
    public void setSaler( String saler ) {
        this.saler = saler;
    }

    
    public String getCode() {
        return code;
    }

    
    public void setCode( String code ) {
        this.code = code;
    }

    
    public String getLoadingPort() {
        return loadingPort;
    }

    
    public void setLoadingPort( String loadingPort ) {
        this.loadingPort = loadingPort;
    }

    
    public String getDestinationPort() {
        return destinationPort;
    }

    
    public void setDestinationPort( String destinationPort ) {
        this.destinationPort = destinationPort;
    }

    
    public String getFlightDate() {
        return flightDate;
    }

    
    public void setFlightDate( String flightDate ) {
        this.flightDate = flightDate;
    }

    
    public String getAgent() {
        return agent;
    }

    
    public void setAgent( String agent ) {
        this.agent = agent;
    }

    
    public String getHawb() {
        return hawb;
    }

    
    public void setHawb( String hawb ) {
        this.hawb = hawb;
    }

    
    public String getCargoWeight() {
        return cargoWeight;
    }

    
    public void setCargoWeight( String cargoWeight ) {
        this.cargoWeight = cargoWeight;
    }

    
    public String getCargoName() {
        return cargoName;
    }

    
    public void setCargoName( String cargoName ) {
        this.cargoName = cargoName;
    }

    
    public String getFinalDestination() {
        return finalDestination;
    }

    
    public void setFinalDestination( String finalDestination ) {
        this.finalDestination = finalDestination;
    }

    
    public String getShipper() {
        return shipper;
    }

    
    public void setShipper( String shipper ) {
        this.shipper = shipper;
    }

    
    public String getConsignee() {
        return consignee;
    }

    
    public void setConsignee( String consignee ) {
        this.consignee = consignee;
    }

    
    public String getRe() {
        return re;
    }

    
    public void setRe( String re ) {
        this.re = re;
    }

    
    public String getTotalHawb() {
        return totalHawb;
    }

    
    public void setTotalHawb( String totalHawb ) {
        this.totalHawb = totalHawb;
    }

    
    public String getTotalPackages() {
        return totalPackages;
    }

    
    public void setTotalPackages( String totalPackages ) {
        this.totalPackages = totalPackages;
    }

    public String getSalerTel() {
        return salerTel;
    }
    
    public void setSalerTel( String salerTel ) {
        this.salerTel = salerTel;
    }
    
    public String getSalerFax() {
        return salerFax;
    }
    
    public void setSalerFax( String salerFax ) {
        this.salerFax = salerFax;
    }
    
    public String getSalerEmail() {
        return salerEmail;
    }
    
    public void setSalerEmail( String salerEmail ) {
        this.salerEmail = salerEmail;
    }
    
}
