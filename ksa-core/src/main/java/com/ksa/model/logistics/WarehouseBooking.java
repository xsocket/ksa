package com.ksa.model.logistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ksa.util.StringUtils;

/**
 * 订仓通知单数据模型。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class WarehouseBooking extends BaseLogisticsModel {

    private static final long serialVersionUID = 65019373225306151L;
    
    /** 销售电话 */
    protected String salerTel = "88473507";
    /** 销售传真 */
    protected String salerFax = "88473508";
    /** 销售邮箱 */
    protected String salerEmail = "hangzhou@ksa.co.jp";

    /** 销售担当 */
    protected String saler;

    /** 销售担当 */
    protected String shipper;

    /** 销售担当 */
    protected String consignee;

    /** 销售担当 */
    protected String notify;

    /** 委托编号 */
    protected String code;

    /** 委托时间 */
    protected String createdDate;

    /** 起运港 */
    protected String departurePort;

    /** 目的港 */
    protected String destinationPort;

    /** 转船 */
    protected String switchShip;

    /** 分批 */
    protected String grouping;

    /** 运输方式 */
    protected String transportMode;

    /** 付款方式 */
    protected String paymentMode;

    /** 运费 */
    protected String freightCharge;

    /** 箱量 */
    protected String cargoContainer;

    /** 唛头 */
    protected String shippingMark;

    /** 品名 */
    protected String cargoName;

    /** 货物毛重 */
    protected String cargoWeight;

    /** 货物体积 */
    protected String cargoVolumn;

    /** 货物数量 */
    protected String cargoQuantity;

    /** 货物毛重 */
    protected String totalWeight;

    /** 货物体积 */
    protected String totalVolumn;

    /** 货物数量 */
    protected String totalQuantity;

    /** 注意事项 */
    protected String note;

    @Override
    public void initialModel( BookingNote bookingNote ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.bookingNote = bookingNote;

        this.saler = bookingNote.getSaler().getName();
        this.consignee = bookingNote.getConsignee().getAlias();
        this.shipper = bookingNote.getShipper().getAlias();
        //this.notify = bookingNote.getNotify().getAlias();
        if( StringUtils.hasText( bookingNote.getConsignee().getId() ) &&
                bookingNote.getConsignee().getId().equals( bookingNote.getNotify().getId() ) ) {
            this.notify = "SAME AS CONSIGNEE";
        } else {
            this.notify = bookingNote.getNotify().getAlias();
        }
        
        this.createdDate = dateFormat.format( new Date() );
        this.code = bookingNote.getCode();

        this.departurePort = bookingNote.getDeparturePort();
        this.destinationPort = bookingNote.getDestinationPort();
        
        this.transportMode = bookingNote.getType().startsWith( "A" ) ? "空运" : "海运"; 
        
        this.cargoContainer = bookingNote.getCargoContainer();
        if( !StringUtils.hasText( this.cargoContainer ) && bookingNote.cargos != null && bookingNote.cargos.size() > 0 ) {
            StringBuilder sb = new StringBuilder();
            for( BookingNoteCargo cargo : bookingNote.cargos ) {
                sb.append( " + ").append( cargo.getCategory() ).append( cargo.getType() ).append( "*" ).append( cargo.amount );
            }
            this.cargoContainer = sb.substring( 3 );
        }
        this.shippingMark = bookingNote.getShippingMark();
        this.cargoName = bookingNote.getCargoName();
        if( bookingNote.getQuantity() != null ) {
            this.cargoQuantity = bookingNote.getQuantity().toString();
            if( bookingNote.getUnit() != null ) {
                this.cargoQuantity += ( " " + bookingNote.getUnit() );
            }
            this.totalQuantity = this.cargoQuantity;
        }
        if( bookingNote.getWeight() != null ) {
            this.cargoWeight = bookingNote.getWeight().toString() + " KGS";
            this.totalWeight = this.cargoWeight;
        }
        if( bookingNote.getVolumn() != null ) {
            this.cargoVolumn = bookingNote.getVolumn().toString() + " CBM";
            this.totalVolumn = this.cargoVolumn;
        }
    }

    public String getSaler() {
        return saler;
    }

    public void setSaler( String saler ) {
        this.saler = saler;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate( String createdDate ) {
        this.createdDate = createdDate;
    }

    public String getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort( String departurePort ) {
        this.departurePort = departurePort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort( String destinationPort ) {
        this.destinationPort = destinationPort;
    }

    public String getSwitchShip() {
        return switchShip;
    }

    public void setSwitchShip( String switchShip ) {
        this.switchShip = switchShip;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping( String grouping ) {
        this.grouping = grouping;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode( String transportMode ) {
        this.transportMode = transportMode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode( String paymentMode ) {
        this.paymentMode = paymentMode;
    }

    public String getFreightCharge() {
        return freightCharge;
    }

    public void setFreightCharge( String freightCharge ) {
        this.freightCharge = freightCharge;
    }

    public String getCargoContainer() {
        return cargoContainer;
    }

    public void setCargoContainer( String cargoContainer ) {
        this.cargoContainer = cargoContainer;
    }

    public String getShippingMark() {
        return shippingMark;
    }

    public void setShippingMark( String shippingMark ) {
        this.shippingMark = shippingMark;
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

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight( String totalWeight ) {
        this.totalWeight = totalWeight;
    }

    public String getTotalVolumn() {
        return totalVolumn;
    }

    public void setTotalVolumn( String totalVolumn ) {
        this.totalVolumn = totalVolumn;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity( String totalQuantity ) {
        this.totalQuantity = totalQuantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
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
