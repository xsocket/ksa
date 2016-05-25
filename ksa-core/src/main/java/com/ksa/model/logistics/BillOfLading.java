package com.ksa.model.logistics;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class BillOfLading extends BaseLogisticsModel {

    private static final long serialVersionUID = 7274595599352874513L;
    
    /** 提单对象 */
    protected String to;
    /** 发布时间 */
    protected String publishDate;
    /** 发货人 */
    protected String shipper;
    /** 收货人 */
    protected String consignee;
    /** 通知人 */
    protected String notify;
    /** 提单编号 */
    protected String code;
    /** 发送方式: 电放 or 正本 */
    protected String deliverType;
    /** 客户编号 */
    protected String customerCode;
    /** 本公司编号 */
    protected String selfCode;
    /** 发件人 */
    protected String creator;
    /** 提单类型 */
    protected String billType;
    /** 备注 */
    protected String note;
    /** 海外代理 */
    protected String agent;
    /** 船名航次 */
    protected String vesselVoyage = "";
    /** 装货港 */
    protected String loadingPort;
    /** 卸货港 */
    protected String dischargePort;
    /** 目的港 */
    protected String destinationPort;
    /** 货物标记 */
    protected String cargoMark;
    /** 货物数量 */
    protected String cargoQuantity;
    /** 货物名称 */
    protected String cargoName;
    /** 货物毛重 */
    protected String cargoWeight;
    /** 货物体积 */
    protected String cargoVolumn;
    /** 箱号封号 */
    protected String cargoDescription;
    /** 货物英文描述 */
    protected String cargoQuantityDescription;
    /** 付款方式 */
    protected String payMode = "FREIGHT PREPAID"; // FREIGHT PREPAID or FREIGHT COLLECT

    @Override
    public void initialModel( BookingNote bookingNote ) {
        this.bookingNote = bookingNote;
        this.cargoDescription = bookingNote.getCargoDescription();
        this.cargoQuantityDescription = bookingNote.getQuantityDescription();
        this.cargoMark = bookingNote.getShippingMark();
        this.cargoName = bookingNote.getCargoName();
        if( bookingNote.getQuantity() != null ) {
            this.cargoQuantity = bookingNote.getQuantity() + " ";
        }
        if( StringUtils.hasText( bookingNote.getUnit() ) ) {
            this.cargoQuantity = this.cargoQuantity + bookingNote.getUnit();
        }
        if( bookingNote.getVolumn() != null ) {
            this.cargoVolumn = bookingNote.getVolumn().toString();
        }
        if( bookingNote.getWeight() != null ) {
            this.cargoWeight = bookingNote.getWeight().toString();
        }
        this.code = "";
        if( StringUtils.hasText( bookingNote.getMawb() ) ) {
            this.code += bookingNote.getMawb();
        }
        if( StringUtils.hasText( bookingNote.getHawb() ) ) {
            this.code += ( "  " + bookingNote.getHawb() );
        }
        this.consignee = bookingNote.getConsignee().getAlias();
        this.shipper = bookingNote.getShipper().getAlias();
        //this.notify = bookingNote.getNotify().getAlias();
        if( StringUtils.hasText( bookingNote.getConsignee().getId() ) &&
                bookingNote.getConsignee().getId().equals( bookingNote.getNotify().getId() ) ) {
            this.notify = "SAME AS CONSIGNEE";
        } else {
            this.notify = bookingNote.getNotify().getAlias();
        }
        
        //this.creator = bookingNote.getCreator().getName();
        this.to = bookingNote.getCustomer().getName();
        this.customerCode = bookingNote.getCustomer().getCode();
        this.destinationPort = bookingNote.getDestinationPort();
        this.dischargePort = bookingNote.getDischargePort();
        
        // 装货港
        this.loadingPort = bookingNote.getDeparturePort();
        if( ! StringUtils.hasText( this.loadingPort )) {
            this.loadingPort = bookingNote.getLoadingPort();
        }
        
        this.publishDate = new SimpleDateFormat("yyyy-MM-dd").format( new Date() );
        // 船名航次
        if( StringUtils.hasText( bookingNote.getRouteName() ) ) {
            this.vesselVoyage = bookingNote.getRouteName() + " ";
        }
        if( StringUtils.hasText( bookingNote.getRouteCode() ) ) {
            this.vesselVoyage += bookingNote.getRouteCode();
        }
    }

    public String getCargoQuantityDescription() {
        return cargoQuantityDescription;
    }

    public void setCargoQuantityDescription( String cargoQuantityDescription ) {
        this.cargoQuantityDescription = cargoQuantityDescription;
    }

    public String getTo() {
        return to;
    }

    public void setTo( String to ) {
        this.to = to;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate( String publishDate ) {
        this.publishDate = publishDate;
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

    public String getNotify() {
        return notify;
    }

    public void setNotify( String notify ) {
        this.notify = notify;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode( String customerCode ) {
        this.customerCode = customerCode;
    }

    public String getSelfCode() {
        return selfCode;
    }

    public void setSelfCode( String selfCode ) {
        this.selfCode = selfCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator( String creator ) {
        this.creator = creator;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType( String type ) {
        this.billType = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent( String agent ) {
        this.agent = agent;
    }

    public String getVesselVoyage() {
        return vesselVoyage;
    }

    public void setVesselVoyage( String vesselVoyage ) {
        this.vesselVoyage = vesselVoyage;
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

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort( String destinationPort ) {
        this.destinationPort = destinationPort;
    }

    public String getCargoMark() {
        return cargoMark;
    }

    public void setCargoMark( String cargoMark ) {
        this.cargoMark = cargoMark;
    }

    public String getCargoQuantity() {
        return cargoQuantity;
    }

    public void setCargoQuantity( String cargoQuantity ) {
        this.cargoQuantity = cargoQuantity;
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

    public String getCargoDescription() {
        return cargoDescription;
    }

    public void setCargoDescription( String cargoDescription ) {
        this.cargoDescription = cargoDescription;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType( String deliverType ) {
        this.deliverType = deliverType;
    }
    
    public String getPayMode() {
        return payMode;
    }

    public void setPayMode( String payMode ) {
        this.payMode = payMode;
    }

}
