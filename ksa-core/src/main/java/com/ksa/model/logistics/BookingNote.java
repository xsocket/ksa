package com.ksa.model.logistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ksa.model.BaseModel;
import com.ksa.model.bd.Partner;
import com.ksa.model.security.User;

/**
 * 托单数据模型。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 * 
 * 
ALTER TABLE ksa_logistics_bookingnote Add column HS_CODE varchar(200) AFTER `CARGO_NAME`;
ALTER TABLE ksa_logistics_bookingnote Add column KEY_CONTENT varchar(1000) AFTER `CARGO_NAME`;
ALTER TABLE ksa_logistics_bookingnote Add column CARGO_PRICE varchar(200) AFTER `CARGO_NAME`; 
ALTER TABLE ksa_logistics_bookingnote Add column CARGO_NAME_ENG varchar(1000) AFTER `CARGO_NAME`;
 */
public class BookingNote extends BaseModel implements Comparable<BookingNote> {

    private static final long serialVersionUID = 6565906853578546538L;
    
    // 分隔符为 "全角空格/全角空格"
    private static final String EXPRESS_CODE_SPLITTER = "　/　";

    /** 海运出口类型 托单 */
    public static final String TYPE_SEA_EXPORT = "SE";

    /** 海运进口类型 托单 */
    public static final String TYPE_SEA_IMPORT = "SI";

    /** 空运出口类型 托单 */
    public static final String TYPE_AIR_EXPORT = "AE";

    /** 空运进口类型 托单 */
    public static final String TYPE_AIR_IMPORT = "AI";
    
    /** 国内运输类型 托单*/
    public static final String TYPE_NATIVE = "LY";
    
    /** 捆包业务类型 托单*/
    public static final String TYPE_KB = "KB";
    
    /** 内航线类型 托单*/
    public static final String TYPE_RH = "RH";
    
    /** 搬场业务类型 托单*/
    public static final String TYPE_BC = "BC";
    
    /** 仓储业务类型 托单*/
    public static final String TYPE_CC = "CC";
    
    /** 公铁联运类型 托单*/
    public static final String TYPE_TL = "TL";

    /** 证件代办类型 托单*/
    public static final String TYPE_ZJ = "ZJ";
    
    /** 子类型 FCL */
    public static final String TYPE_SUB_FCL = "FCL";
    
    /** 子类型 LCL */
    public static final String TYPE_SUB_LCL = "LCL";

    // -------------- 基本信息 -------------- //
    /** 提单类型：海运/空运、出口/进口 */
    protected String type;
    
    /** 提单子类型：FCL / LCL */
    protected String subType;

    /** 流水号 */
    protected int serialNumber;

    /** 编号 */
    protected String code;

    /** 客户 */
    protected Partner customer = new Partner();

    /** 发票编号 */
    protected String invoiceNumber;

    /** 接单日期 */
    protected Date createdDate = new Date();
    
    /** 记账日期 */
    protected Date chargeDate;

    /** 销售人员 */
    protected User saler = new User();

    /** 操作员 */
    protected User creator = new User();

    /** 代理 */
    protected Partner agent = new Partner();

    /** 承运人 */
    protected Partner carrier = new Partner();

    /** 船代 */
    protected Partner shippingAgent = new Partner();

    // -------------- 货物信息 -------------- //
    /** 品名 */
    protected String cargoName;
    
    /** 英文品名 */
    protected String cargoNameEng;
       
    protected String hsCode;
    
    /** 关键要素 */
    protected String keyContent;
    
    /** 价格 */
    protected String cargoPrice;

    /** 备注 */
    protected String cargoNote;

    /** 箱号封号 */
    protected String cargoDescription;

    /** 货物的箱类箱型箱量描述 */
    protected String cargoContainer;

    /** 唛头 */
    protected String shippingMark;

    /** 体积 */
    protected Float volumn;

    /** 毛重 */
    protected Float weight;

    /** 数量 */
    protected Integer quantity;

    /** 数量单位 */
    protected String unit;

    /** 数量英文描述 */
    protected String quantityDescription;

    // -------------- 提单信息 -------------- //
    /** 开票抬头 */
    protected String title;

    /** 主提单编号 */
    protected String mawb;

    /** 副提单编号 */
    protected String hawb;

    /** 发货人 */
    protected Partner shipper = new Partner();

    /** 收货人 */
    protected Partner consignee = new Partner();

    /** 通知人 */
    protected Partner notify = new Partner();

    // -------------- 航线信息 -------------- //
    /** 出发地 */
    protected String departure;

    /** 目的地 */
    protected String destination;

    /** 起运港 */
    protected String departurePort;

    /** 目的港 */
    protected String destinationPort;

    /** 装货港 （起运中转） */
    protected String loadingPort;

    /** 卸货港（到港中转） */
    protected String dischargePort;

    /** 出航日 */
    protected Date departureDate;

    /** 到港日 */
    protected Date destinationDate;

    /** 货物 接收/派送 日期 */
    protected Date deliverDate;

    /** 航线：海运航线 or 空运航线 */
    protected String route;

    /** 船名 or 航班 */
    protected String routeName;

    /** 航次 or 航班 */
    protected String routeCode;

    // -------------- 报关信息 -------------- //
    /** 报关行：customs house broker */
    protected Partner customsBroker = new Partner();

    /** 报关单号/核销单号 */
    protected String customsCode;

    /** 报关日 */
    protected Date customsDate;

    /** 退单号 */
    protected String returnCode;

    /** 退单日期 */
    protected Date returnDate;
    
    /** 退单寄送日期 */
    protected Date returnDate2;

    /** 税单号 */
    protected String taxCode;

    /** 收到税单日期 */
    protected Date taxDate1;

    /** 寄出税单日期 */
    protected Date taxDate2;

    /** 快递单号 */
    protected String expressCode;

    // -------------- 车队信息 -------------- //
    /** 车型 */
    protected String vehicleType;

    /** 车牌号 */
    protected String vehicleNumber;

    /** 车队 */
    protected Partner vehicleTeam = new Partner();

    /** 托单货物信息 */
    protected List<BookingNoteCargo> cargos = new ArrayList<BookingNoteCargo>();

    /** 托单状态 */
    protected int state;

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public void setSubType( String subType ) {
        this.subType = subType;
    }
    
    public String getSubType() {
        return subType;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber( int serialNumber ) {
        this.serialNumber = serialNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public Partner getCustomer() {
        if( customer == null ) {
            customer = new Partner();
        }
        return customer;
    }

    public void setCustomer( Partner customer ) {
        this.customer = customer;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber( String invoiceNumber ) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate( Date createdDate ) {
        this.createdDate = createdDate;
    }
    
    public Date getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate( Date chargeDate ) {
        this.chargeDate = chargeDate;
    }

    public User getSaler() {
        return saler;
    }

    public void setSaler( User saler ) {
        this.saler = saler;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator( User creator ) {
        this.creator = creator;
    }

    public Partner getAgent() {
        if( agent == null ) {
            agent = new Partner();
        }
        return agent;
    }

    public void setAgent( Partner agent ) {        
        this.agent = agent;
    }

    public Partner getCarrier() {
        if( carrier == null ) {
            carrier = new Partner();
        }
        return carrier;
    }

    public void setCarrier( Partner carrier ) {
        this.carrier = carrier;
    }

    public Partner getShippingAgent() {
        if( shippingAgent == null ) {
            shippingAgent = new Partner();
        }
        return shippingAgent;
    }

    public void setShippingAgent( Partner shippingAgent ) {
        this.shippingAgent = shippingAgent;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName( String cargoName ) {
        this.cargoName = cargoName;
    }

    public String getCargoNote() {
        return cargoNote;
    }

    public void setCargoNote( String cargoNote ) {
        this.cargoNote = cargoNote;
    }

    public String getCargoDescription() {
        return cargoDescription;
    }

    public void setCargoDescription( String cargoDescription ) {
        this.cargoDescription = cargoDescription;
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

    public Float getVolumn() {
        return volumn;
    }

    public void setVolumn( Float volumn ) {
        this.volumn = volumn;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight( Float weight ) {
        this.weight = weight;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity( Integer quantity ) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit( String unit ) {
        this.unit = unit;
    }

    public String getQuantityDescription() {
        return quantityDescription;
    }

    public void setQuantityDescription( String quantityDescription ) {
        this.quantityDescription = quantityDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
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

    public Partner getShipper() {
        if( shipper == null ) {
            shipper = new Partner();
        }
        return shipper;
    }

    public void setShipper( Partner shipper ) {
        this.shipper = shipper;
    }

    public Partner getConsignee() {
        if( consignee == null ) {
            consignee = new Partner();
        }
        return consignee;
    }

    public void setConsignee( Partner consignee ) {
        this.consignee = consignee;
    }

    public Partner getNotify() {
        if( notify == null ) {
            notify = new Partner();
        }
        return notify;
    }

    public void setNotify( Partner notify ) {
        this.notify = notify;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture( String departure ) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination( String destination ) {
        this.destination = destination;
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

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate( Date departureDate ) {
        this.departureDate = departureDate;
    }

    public Date getDestinationDate() {
        return destinationDate;
    }

    public void setDestinationDate( Date destinationDate ) {
        this.destinationDate = destinationDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate( Date deliverDate ) {
        this.deliverDate = deliverDate;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute( String route ) {
        this.route = route;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName( String routeName ) {
        this.routeName = routeName;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode( String routeCode ) {
        this.routeCode = routeCode;
    }

    public Partner getCustomsBroker() {
        if( customsBroker == null ) {
            customsBroker = new Partner();
        }
        return customsBroker;
    }

    public void setCustomsBroker( Partner customsBroker ) {
        this.customsBroker = customsBroker;
    }

    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode( String customsCode ) {
        this.customsCode = customsCode;
    }

    public Date getCustomsDate() {
        return customsDate;
    }

    public void setCustomsDate( Date customsDate ) {
        this.customsDate = customsDate;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode( String returnCode ) {
        this.returnCode = returnCode;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate( Date returnDate ) {
        this.returnDate = returnDate;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode( String taxCode ) {
        this.taxCode = taxCode;
    }

    public Date getTaxDate1() {
        return taxDate1;
    }

    public void setTaxDate1( Date taxDate1 ) {
        this.taxDate1 = taxDate1;
    }

    public Date getTaxDate2() {
        return taxDate2;
    }

    public void setTaxDate2( Date taxDate2 ) {
        this.taxDate2 = taxDate2;
    }
    
    public String getTaxExpressCode() {
        if( StringUtils.isEmpty( expressCode ) ) {
            return "";
        }
        String[] express = expressCode.split( EXPRESS_CODE_SPLITTER );
        if( express == null || express.length <= 0 ) {
            return "";
        } else {
            return express[0];
        }
    }
    
    public void setTaxExpressCode( String tec ) {
        StringBuilder sb = new StringBuilder( 64 );
        sb.append( tec == null ? "" : tec.trim() ).append( EXPRESS_CODE_SPLITTER ).append( getReturnExpressCode() );
        this.expressCode = sb.toString();
    }
    
    public String getReturnExpressCode() {
        if( StringUtils.isEmpty( expressCode ) ) {
            return "";
        }
        String[] express = expressCode.split( EXPRESS_CODE_SPLITTER );
        if( express == null || express.length <= 1 ) {
            return "";
        } else {
            return express[1];
        }
    }
    
    public void setReturnExpressCode( String tec ) {
        StringBuilder sb = new StringBuilder( 64 );
        sb.append( getTaxExpressCode() ).append( EXPRESS_CODE_SPLITTER ).append( tec == null ? "" : tec.trim() );
        this.expressCode = sb.toString();
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode( String expressCode ) {
        this.expressCode = expressCode;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType( String vehicleType ) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber( String vehicleNumber ) {
        this.vehicleNumber = vehicleNumber;
    }

    public Partner getVehicleTeam() {
        return vehicleTeam;
    }

    public void setVehicleTeam( Partner vehicleTeam ) {
        this.vehicleTeam = vehicleTeam;
    }

    public List<BookingNoteCargo> getCargos() {
        return cargos;
    }

    public void setCargos( List<BookingNoteCargo> cargos ) {
        this.cargos = cargos;
    }

    public int getState() {
        return state;
    }

    public void setState( int state ) {
        this.state = state;
    }
    
    public Date getReturnDate2() {
        return returnDate2;
    }
    
    public void setReturnDate2( Date returnDate2 ) {
        this.returnDate2 = returnDate2;
    }

    public String getCargoNameEng() {
      return cargoNameEng;
    }

    public void setCargoNameEng(String cargoNameEng) {
      this.cargoNameEng = cargoNameEng;
    }

    public String getHsCode() {
      return hsCode;
    }

    public void setHsCode(String hsCode) {
      this.hsCode = hsCode;
    }

    public String getKeyContent() {
      return keyContent;
    }

    public void setKeyContent(String keyContent) {
      this.keyContent = keyContent;
    }

    public String getCargoPrice() {
      return cargoPrice;
    }

    public void setCargoPrice(String cargoPrice) {
      this.cargoPrice = cargoPrice;
    }

    @Override
    public int compareTo( BookingNote o ) {
        return this.serialNumber - o.serialNumber;
    }
}
