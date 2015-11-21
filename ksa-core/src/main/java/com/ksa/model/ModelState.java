package com.ksa.model;


/**
 * 业务数据模型的基本状态常量描述。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class ModelState {
    
    /** 表示业务对象没有任何状态，或者可以认为其处于最原始状态。*/
    public static final int NONE = 0x0;
    /** 表示业务对象状态为 <b>处理中</b>。*/
    public static final int PROCESSING = 0x1;
    /** 表示业务对象状态为 <b>审核中</b>。*/
    public static final int CHECKING = 0x2;
    /** 表示业务对象状态为 <b>审核通过</b>。*/
    public static final int CHECKED = 0x8;
    
    /**
     * 判断业务对象当前状态是否为 NONE 。
     * @param state 业务模型的状态
     * @return
     */
    public static boolean isNone( int state ) {
        return state == NONE;
    }
    
    /**
     * 将当前状态设置为空状态。
     * @param state 待设置状态
     * @return 空状态
     */
    public static int setNone( int state ) {
        return 0;
    }
    
    /**
     * 判断业务对象状态是否为 <b>处理中</b>。
     * @param state 待判断状态
     */
    public static boolean isProcessing( int state ) {
        return ( PROCESSING & state ) > 0;
    }
    /**
     * 将当前状态设置为 <b>处理中</b>。
     * @param state 待设置状态
     * @return 表示 <b>处理中</b> 的状态
     */
    public static int setProcessing( int state ) {
        return state | PROCESSING;
    }
    
    /**
     * 将当前状态设置为 <b>非处理中</b>。
     * @param state 待设置状态
     * @return 表示 <b>非处理中</b> 的状态
     */
    public static int setUnprocessing( int state ) {
        return state & ~PROCESSING ;
    }
    
    /**
     * 判断业务对象状态是否为 <b>审核中</b>。
     * @param state 待判断状态
     */
    public static boolean isChecking( int state ) {
        return ( CHECKING & state ) > 0;
    }
    
    /**
     * 判断业务对象状态是否为 <b>审核通过</b>。
     * @param state 待判断状态
     */
    public static boolean isChecked( int state ) {
        return ( CHECKED & state ) > 0;
    }
    
    /**
     * 将当前状态设置为 <b>审核中</b>。
     * @param state 待设置状态
     * @return 表示 <b>审核中</b> 的状态
     */
    public static int setChecking( int state ) {
        return state | CHECKING;
    }
    
    /**
     * 将当前状态设置为 <b>非审核中</b>。
     * @param state 待设置状态
     * @return 表示 <b>非审核中</b> 的状态
     */
    public static int setUnchecking( int state ) {
        return state & ~CHECKING ;
    }
    
    /**
     * 将当前状态设置为 <b>审核通过</b>。
     * @param state 待设置状态
     * @return 表示 <b>审核通过</b> 的状态
     */
    public static int setChecked( int state ) {
        return state | CHECKED;
    }
    
    /**
     * 将当前状态设置为 <b>未审核通过</b>。
     * @param state 待设置状态
     * @return 表示 <b>未审核通过</b> 的状态
     */
    public static int setUnchecked( int state ) {
        return state & ~CHECKED;
    }

    /**
     * 构造函数。 <br />
     * 状态常量可以通过继承的方式进行扩展，但是类本身不允许实例化。
     */
    protected ModelState() {  }
}
