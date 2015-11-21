package com.ksa.model.bd;

import org.springframework.util.StringUtils;

/**
 * 货币数据模型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class Currency extends BasicData {

    private static final long serialVersionUID = -3395552568108234099L;
    
    public static Currency RMB = new ReservedCurrency( "00-currency-RMB", "RMB", "人民币", 1.0f );
    public static Currency USD = new ReservedCurrency( "00-currency-USD", "USD", "美元", 6.8f );

    public float getDefaultRate() {
        if( !StringUtils.hasText( extra ) ) {
            return 1.0f;
        }
        return Float.parseFloat( this.extra );
    }

    @Override
    public BasicDataType getType() {
        return BasicDataType.CURRENCY;
    }
    
    /**
     * 系统保留的默认货币类型，只读。
     *
     * @author 麻文强
     *
     * @since v0.0.1
     */
    private static class ReservedCurrency extends Currency {

        private static final long serialVersionUID = -4404235952475840023L;

        ReservedCurrency( String id, String code, String name, float rate ) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.extra = Float.toString( rate );
        }
        
        @Override
        public void setId( String id ) { }
        
        @Override
        public void setCode( String code ) { }
        
        @Override
        public void setName( String name ) { }
        
        @Override
        public void setExtra( String extra ) { }
    }
}
