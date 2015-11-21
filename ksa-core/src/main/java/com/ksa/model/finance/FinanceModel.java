package com.ksa.model.finance;

import com.ksa.model.BaseModel;

/**
 * 财务相关业务数据模型抽象基类。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public abstract class FinanceModel extends BaseModel {

    private static final long serialVersionUID = -4120373048344140760L;

    /** 收入相关数据类型标识 */
    public static final int INCOME = 1;
    
    /** 支出相关数据类型标识 */
    public static final int EXPENSE = -1;
    
    /** 财务相关数据的种类为：国内 */
    public static final int NATIVE = 1;
    
    /** 财务相关数据的种类为：境外 */
    public static final int FOREIGN = -1;
    
    /** 表示数据的收支方向：1 表示与收入相关的业务数据，-1表示与支出相关的业务数据。 */
    protected int direction = INCOME;
    
    /** 表示数据的类型：1表示国内数据，-1表示境外数据。*/
    protected int nature = NATIVE;
     
    public int getNature() {
        return nature;
    }

    public void setNature( int nature ) {
        if( nature >= 0 ) {
            this.nature = NATIVE;
        } else {
            this.nature = FOREIGN;
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection( int direction ) {
        if( direction >= 0 ) {
            this.direction = INCOME;
        } else {
            this.direction = EXPENSE;
        }
    }
    
    public static final boolean isIncome( FinanceModel model ) {
        return model.getDirection() == INCOME;
    }
    public static final boolean isIncome( int direction ) {
        return direction == INCOME;
    }
    public static final boolean isExpense( FinanceModel model ) {
        return model.getDirection() == EXPENSE;
    }
    public static final boolean isExpense( int direction ) {
        return direction == EXPENSE;
    }
    
    public static final boolean isNative( FinanceModel model ) {
        return model.getNature() == NATIVE;
    }
    public static final boolean isNative( int nature ) {
        return nature == NATIVE;
    }
    public static final boolean isForeign( FinanceModel model ) {
        return model.getNature() == FOREIGN;
    }
    public static final boolean isForeign( int nature ) {
        return nature == FOREIGN;
    }
    
}
