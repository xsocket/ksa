package com.ksa.model.logistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ksa.util.StringUtils;


public class ArrivalNote extends BaseLogisticsModel {

    private static final long serialVersionUID = 2741955471843294574L;

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    protected BookingNote bookingNote;

    protected String date;
    protected String code;

    protected String consignee;
    protected String shipper;
    
    protected String vessel;
    protected String voyage;
    protected String mawb;
    protected String hawb;
    protected String container;
    protected String seal;
    protected String eta;
    
    protected String cy;    
    protected String loadingPort;
    protected String dischargePort;
    protected String deliverPlace;
    
    protected String cargoMark;
    protected String cargoWeight;
    protected String cargoVolumn;
    protected String cargoDescription;
    protected String cargo;
    protected String pkg;
    protected String count;
    
    protected String freight;
    protected String charge;
    protected String rate;
    
    @Override
    public void initialModel( BookingNote bookingNote ) {
        this.bookingNote = bookingNote;
        this.date = format.format( new Date() );
        
        
        this.consignee = bookingNote.getConsignee().getAlias();
        this.shipper = bookingNote.getShipper().getAlias();
        
        this.vessel = bookingNote.getRouteName();
        this.voyage = bookingNote.getRouteCode();
        this.mawb = bookingNote.getMawb();
        this.hawb = bookingNote.getHawb();
        if( StringUtils.hasText( this.mawb ) ) {
            this.code = this.mawb;
            int length = this.code.length();
            if( length > 4 ) {
                this.code = code.substring( length - 4, length );
            }
        }
        if( bookingNote.getDestinationDate() != null ) {
            this.eta = format.format( bookingNote.getDestinationDate() );
        }
        
        // 装货港
        this.loadingPort = bookingNote.getLoadingPort();
        if( this.loadingPort == null ) {
            this.loadingPort = bookingNote.getDeparturePort();
        }
        this.dischargePort = bookingNote.getDischargePort();
        if( this.dischargePort == null ) {
            this.dischargePort = bookingNote.getDestinationPort();
        }
        
        
        this.cargoMark = bookingNote.getShippingMark();
        if( bookingNote.getVolumn() != null ) {
            this.cargoVolumn = bookingNote.getVolumn().toString();
        }
        if( bookingNote.getWeight() != null ) {
            this.cargoWeight = bookingNote.getWeight().toString();
        }
        
        
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate( String date ) {
        this.date = date;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode( String code ) {
        this.code = code;
    }
    
    public String getConsignee() {
        return consignee;
    }
    
    public void setConsignee( String consignee ) {
        this.consignee = consignee;
    }
    
    public String getShipper() {
        return shipper;
    }
    
    public void setShipper( String shipper ) {
        this.shipper = shipper;
    }
    
    public String getVessel() {
        return vessel;
    }
    
    public void setVessel( String vessel ) {
        this.vessel = vessel;
    }
    
    public String getVoyage() {
        return voyage;
    }
    
    public void setVoyage( String voyage ) {
        this.voyage = voyage;
    }
    
    public String getMawb() {
        return mawb;
    }
    
    public void setMawb( String mawb ) {
        this.mawb = mawb;
    }
    
    public String getHawb() {
        return hawb;
    }
    
    public void setHawb( String hawb ) {
        this.hawb = hawb;
    }
    
    public String getContainer() {
        return container;
    }
    
    public void setContainer( String container ) {
        this.container = container;
    }
    
    public String getSeal() {
        return seal;
    }
    
    public void setSeal( String seal ) {
        this.seal = seal;
    }
    
    public String getEta() {
        return eta;
    }
    
    public void setEta( String eta ) {
        this.eta = eta;
    }
    
    public String getCy() {
        return cy;
    }
    
    public void setCy( String cy ) {
        this.cy = cy;
    }
    
    public String getLoadingPort() {
        return loadingPort;
    }
    
    public void setLoadingPort( String loadingPort ) {
        this.loadingPort = loadingPort;
    }
    
    public String getDischargePort() {
        return dischargePort;
    }
    
    public void setDischargePort( String dischargePort ) {
        this.dischargePort = dischargePort;
    }
    
    public String getDeliverPlace() {
        return deliverPlace;
    }
    
    public void setDeliverPlace( String deliverPlace ) {
        this.deliverPlace = deliverPlace;
    }
    
    public String getCargoMark() {
        return cargoMark;
    }
    
    public void setCargoMark( String cargoMark ) {
        this.cargoMark = cargoMark;
    }
    
    public String getCargoWeight() {
        return cargoWeight;
    }
    
    public void setCargoWeight( String cargoWeight ) {
        this.cargoWeight = cargoWeight;
    }
    
    public String getCargoVolumn() {
        return cargoVolumn;
    }
    
    public void setCargoVolumn( String cargoVolumn ) {
        this.cargoVolumn = cargoVolumn;
    }
    
    public String getCargoDescription() {
        return cargoDescription;
    }
    
    public void setCargoDescription( String cargoDescription ) {
        this.cargoDescription = cargoDescription;
    }
    
    public String getCargo() {
        return cargo;
    }
    
    public void setCargo( String cargo ) {
        this.cargo = cargo;
    }
    
    public String getPkg() {
        return pkg;
    }
    
    public void setPkg( String pkg ) {
        this.pkg = pkg;
    }
    
    public String getCount() {
        return count;
    }
    
    public void setCount( String count ) {
        this.count = count;
    }

    
    public BookingNote getBookingNote() {
        return bookingNote;
    }
    
    public void setBookingNote( BookingNote bookingNote ) {
        this.bookingNote = bookingNote;
    }

    
    public String getFreight() {
        return freight;
    }

    
    public void setFreight( String freight ) {
        this.freight = freight;
    }

    
    public String getCharge() {
        return charge;
    }

    
    public void setCharge( String charge ) {
        this.charge = charge;
    }

    
    public String getRate() {
        return rate;
    }

    
    public void setRate( String rate ) {
        this.rate = rate;
    }
    
    
}
