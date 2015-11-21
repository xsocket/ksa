package com.ksa.model.bd;

/**
 * 合作伙伴类型。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class PartnerType extends BasicData {

    private static final long serialVersionUID = -6742807466154803029L;

    @Override
    public BasicDataType getType() {
        return BasicDataType.DEPARTMENT;
    }
}
