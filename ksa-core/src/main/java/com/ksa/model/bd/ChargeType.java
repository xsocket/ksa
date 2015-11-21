package com.ksa.model.bd;

/**
 * 费用类型模型
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class ChargeType extends BasicData {

    private static final long serialVersionUID = -219864488802023204L;

    @Override
    public BasicDataType getType() {
        return BasicDataType.CHARGE;
    }
}
