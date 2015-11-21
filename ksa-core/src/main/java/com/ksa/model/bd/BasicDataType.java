package com.ksa.model.bd;

import com.ksa.model.BaseModel;

/**
 * 基础数据分类模型。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class BasicDataType extends BaseModel {

    private static final long serialVersionUID = -7778666427563739441L;
    /** 币种 */
    public static final BasicDataType CURRENCY = new NativeBasicDataType( "00-currency", "结算货币" );
    /** 数量单位 */
    public static final BasicDataType UNITS = new NativeBasicDataType( "01-units", "数量单位" );
    /** 车辆类型 */
    public static final BasicDataType VEHICLE = new NativeBasicDataType( "08-vehicle", "车辆类型" );
    /** 报关类型 */
    //public static final BasicDataType CUSTOM = new NativeBasicDataType( "09-custom", "报关类型" );
    /** 费用类型 */
    public static final BasicDataType CHARGE = new NativeBasicDataType( "10-charge", "费用类型" );
    /** 贸易条款 */
    //public static final BasicDataType TRADE_CLAUSE = new NativeBasicDataType( "11-trade-clause", "贸易条款" );
    /** 运费条款 */
    //public static final BasicDataType FREIGHT_CLAUSE = new NativeBasicDataType( "12-freight-clause", "运费条款" );
    /** 附加费条款 */
    //public static final BasicDataType SURCHARGE_CLAUSE = new NativeBasicDataType( "13-surcharge-clause", "附加费条款" );
    /** 来往单位类型 */
    public static final BasicDataType DEPARTMENT = new NativeBasicDataType( "20-department", "来往单位类型" );
    /** 国家地区 */
    public static final BasicDataType STATE = new NativeBasicDataType( "30-state", "国家地区" );
    /** 海运港口 */
    public static final BasicDataType PORT_SEA = new NativeBasicDataType( "31-port-sea", "海运港口" );
    /** 空运港口 */
    public static final BasicDataType PORT_AIR = new NativeBasicDataType( "32-port-air", "空运港口" );
    /** 海运航线 */
    public static final BasicDataType ROUTE_SEA = new NativeBasicDataType( "33-route-sea", "海运航线" );
    /** 空运航线 */
    public static final BasicDataType ROUTE_AIR = new NativeBasicDataType( "34-route-air", "空运航线" );
    
    /** 所有的基础数据类型 */
    public static final BasicDataType[] ALL_TYPE = new BasicDataType[] {
        CURRENCY,
        UNITS,
        VEHICLE,
        // CUSTOM,
        CHARGE,
        // TRADE_CLAUSE,
        // FREIGHT_CLAUSE,
        // SURCHARGE_CLAUSE,
        DEPARTMENT,
        STATE,
        PORT_SEA,
        PORT_AIR,
        ROUTE_SEA,
        ROUTE_AIR
    };

    public BasicDataType() {

    }

    public BasicDataType( String id, String name ) {
        this.id = id;
        this.name = name;
    }

    /** 类型名称 */
    protected String name;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    private static class NativeBasicDataType extends BasicDataType {

        private static final long serialVersionUID = -1209493492764946816L;
        
        public NativeBasicDataType( String id, String name ) {
            this.id = id;
            this.name = name;
        }
        @Override
        public void setId( String id ) {
            throw new UnsupportedOperationException( "不能修改系统保留基本数据类型的标识。" );
        }
        @Override
        public void setName( String name ) {
            throw new UnsupportedOperationException( "不能修改系统保留基本数据类型的名称。" );
        }
    }
}
