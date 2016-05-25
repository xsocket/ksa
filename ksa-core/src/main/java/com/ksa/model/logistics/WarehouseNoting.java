package com.ksa.model.logistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 进仓通知单数据模型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class WarehouseNoting extends BaseLogisticsModel {

    private static final long serialVersionUID = -3414203243419839677L;
    
    /** 销售电话 */
    protected String salerTel = "88473507";
    /** 销售传真 */
    protected String salerFax = "88473508";
    /** 销售邮箱 */
    protected String salerEmail = "hangzhou@ksa.co.jp";
    
    /** 销售担当 */
    protected String saler; 
    /** TO */
    protected String to;
    /** 进仓编号 */
    protected String code;
    /** 进仓时间 */
    protected String createdDate;
    /** 品名 */
    protected String cargoName;
    /** 货物毛重 */
    protected String cargoWeight;
    /** 货物体积 */
    protected String cargoVolumn;
    /** 货物数量 */
    protected String cargoQuantity;
    /** 委托客户 */
    protected String customer;
    /** 起运港 */
    protected String loadingPort;
    /** 卸货港 */
    protected String dischargePort;
    /** 船名航次 */
    protected String vesselVoyage = "";
    /** 目的地 */
    protected String destination;
    /** 出航日 */
    protected String departureDate;
    /** 提单号 */
    protected String mawb;
    /** 最晚入仓时间 */
    protected String entryDate;
    /** 通知时间 */
    protected String informDate;

    /**  进仓地址 */
    protected String address;
    /** 联系人 */
    protected String contact;
    /** 电话 */
    protected String telephone;
    /** 传真 */
    protected String fax;

    @Override
    public void initialModel( BookingNote bookingNote ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.bookingNote = bookingNote;
        
        this.createdDate = dateFormat.format( new Date() );
        this.saler = bookingNote.getSaler().getId();
        this.code = bookingNote.getCode();
        this.cargoName = bookingNote.getCargoName();
        if( bookingNote.getQuantity() != null ) {
            this.cargoQuantity = bookingNote.getQuantity().toString();
            if( bookingNote.getUnit() != null ) {
                this.cargoQuantity += ( " " + bookingNote.getUnit() );
            }
        }
        
        if( bookingNote.getWeight() != null ) {
            this.cargoWeight = bookingNote.getWeight().toString() + " KGS";
        }
        
        if( bookingNote.getVolumn() != null ) {
            this.cargoVolumn = bookingNote.getVolumn().toString() + " CBM";
        }
        
        this.customer = bookingNote.getCustomer().getId();
        this.loadingPort = bookingNote.getLoadingPort();
        this.dischargePort = bookingNote.getDischargePort();
        this.destination = bookingNote.getDestination();
        if( bookingNote.getDepartureDate() != null ) {
            this.departureDate = dateFormat.format( bookingNote.getDepartureDate() );
        }
        this.mawb = bookingNote.getMawb();
        
        if( bookingNote.getRouteName() != null ) {
            this.vesselVoyage = bookingNote.getRouteName() + " ";
        }
        if( bookingNote.getRouteCode() != null ) {
            this.vesselVoyage += bookingNote.getRouteCode();
        }
    }

    
    public String getSaler() {
        return saler;
    }

    
    public void setSaler( String saler ) {
        this.saler = saler;
    }

    
    public String getTo() {
        return to;
    }

    
    public void setTo( String to ) {
        this.to = to;
    }

    
    public String getCode() {
        return code;
    }

    
    public void setCode( String code ) {
        this.code = code;
    }

    
    public String getCreatedDate() {
        return createdDate;
    }

    
    public void setCreatedDate( String createdDate ) {
        this.createdDate = createdDate;
    }

    
    public String getCargoName() {
        return cargoName;
    }

    
    public void setCargoName( String cargoName ) {
        this.cargoName = cargoName;
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

    
    public String getCargoQuantity() {
        return cargoQuantity;
    }

    
    public void setCargoQuantity( String cargoQuantity ) {
        this.cargoQuantity = cargoQuantity;
    }

    
    public String getCustomer() {
        return customer;
    }

    
    public void setCustomer( String customer ) {
        this.customer = customer;
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

    
    public String getVesselVoyage() {
        return vesselVoyage;
    }

    
    public void setVesselVoyage( String vesselVoyage ) {
        this.vesselVoyage = vesselVoyage;
    }

    
    public String getDestination() {
        return destination;
    }

    
    public void setDestination( String destination ) {
        this.destination = destination;
    }

    
    public String getDepartureDate() {
        return departureDate;
    }

    
    public void setDepartureDate( String departureDate ) {
        this.departureDate = departureDate;
    }

    
    public String getMawb() {
        return mawb;
    }

    
    public void setMawb( String mawb ) {
        this.mawb = mawb;
    }

    
    public String getEntryDate() {
        return entryDate;
    }

    
    public void setEntryDate( String entryDate ) {
        this.entryDate = entryDate;
    }

    
    public String getInformDate() {
        return informDate;
    }

    
    public void setInformDate( String informDate ) {
        this.informDate = informDate;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress( String address ) {
        this.address = address;
    }

    
    public String getContact() {
        return contact;
    }

    
    public void setContact( String contact ) {
        this.contact = contact;
    }

    
    public String getTelephone() {
        return telephone;
    }

    
    public void setTelephone( String telephone ) {
        this.telephone = telephone;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax( String fax ) {
        this.fax = fax;
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
