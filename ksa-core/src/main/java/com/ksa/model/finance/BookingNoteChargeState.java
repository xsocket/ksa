package com.ksa.model.finance;

import com.ksa.model.logistics.BookingNoteState;


public class BookingNoteChargeState extends BookingNoteState {
    
    // 底位到高位数：1到4保持不动
    // 收入+国内(d:1,n:1)：5到8  --> 移位 4
    // 支出+国内(d:-1,n:1)：9到12  --> 移位 8
    // 收入+境外(d:1,n:-1)：13到16  --> 移位 12
    // 支出+境外(d:-1,n:-1)：17到20  --> 移位 16
    public static int computeShift( int direction, int nature ) {
        int d = ( direction >= 0 ? 1 : -1 );
        int n = ( nature >= 0 ? 1 : -1 );
        return  10 - 2 * d - 4 * n;
    }
    
    // 只区别境内和境外
    //境内：1到4 --> 移位0
    //境外：21到24 --> 移动20
    public static int computeShift( int nature ) {
        // 大于等于0表示境内，小于0表示境外
        return ( nature >= 0 ? 0 : 20 );
    }
    
    /** 表示托单已经录入费用数据。*/
    public static final int ENTERING = 0x1;
    
    /**
     * 判断托单费用状态是否为 <b>已录入</b>。
     * @param state 待判断状态
     */
    public static boolean isEntering( int state ) {
        return ( ENTERING & state ) > 0;
    }
    /**
     * 将当前托单费用状态设置为 <b>已录入</b>。
     * @param state 待设置状态
     * @return 表示 <b>已录入</b> 的状态
     */
    public static int setEntering( int state ) {
        return state | ENTERING; // 录入中
    }
    
    /**
     * 将当前托单费用状态设置为 <b>未录入</b>。
     * @param state 待设置状态
     * @return 表示 <b>未录入</b> 的状态
     */
    public static int setUnentering( int state ) {
        return state & ~ENTERING; // 未录入
    }
    
    /**
     * 判断托单费用状态是否为 <b>已录入</b>。
     * @param state 待判断状态
     */
    public static boolean isEntering( int state, int direction, int nature ) {
        return ( ( ENTERING << computeShift( direction, nature ) ) & state ) > 0;
    }
    
    /**
     * 将当前托单费用状态设置为 <b>已录入</b>。
     * @param state 待设置状态
     * @return 表示 <b>已录入</b> 的状态
     */
    public static int setEntering( int state, int direction, int nature ) {
        return state | ( ENTERING << computeShift( direction, nature ) ) ; // 录入中
    }
    
    /**
     * 判断托单费用状态是否为 <b>已录入</b>。
     * @param state 待判断状态
     */
    public static boolean isEntering( int state, int nature ) {
        return ( ( ENTERING << computeShift( nature ) ) & state ) > 0;
    }
    
    /**
     * 将当前托单费用状态设置为 <b>未录入</b>。
     * @param state 待设置状态
     * @return 表示 <b>未录入</b> 的状态
     */
    public static int setUnentering( int state, int direction, int nature ) {
        return state & ~( ENTERING << computeShift( direction, nature ) ); // 未录入
    }
    
    /**
     * 将当前托单费用状态设置为 <b>已录入</b>。
     * @param state 待设置状态
     * @return 表示 <b>已录入</b> 的状态
     */
    public static int setEntering( int state, int nature ) {
        return state | ( ENTERING << computeShift( nature ) ) ; // 录入中
    }
    
    /**
     * 将当前托单费用状态设置为 <b>未录入</b>。
     * @param state 待设置状态
     * @return 表示 <b>未录入</b> 的状态
     */
    public static int setUnentering( int state, int nature ) {
        return state & ~( ENTERING << computeShift( nature ) ); // 未录入
    }    
    
    /**
     * 判断业务对象状态是否为 <b>审核中</b>。
     * @param state 待判断状态
     */
    public static boolean isChecking( int state, int direction, int nature ) {
        return ( ( CHECKING << computeShift( direction, nature ) ) & state ) > 0;
    }
    
    /**
     * 将当前状态设置为 <b>审核中</b>。
     * @param state 待设置状态
     * @return 表示 <b>审核中</b> 的状态
     */
    public static int setChecking( int state, int direction, int nature ) {
        return state | ( CHECKING << computeShift( direction, nature ) );
    }
    
    /**
     * 将当前状态设置为 <b>非审核中</b>。
     * @param state 待设置状态
     * @return 表示 <b>非审核中</b> 的状态
     */
    public static int setUnchecking( int state, int direction, int nature ) {
        return state & ~ ( CHECKING << computeShift( direction, nature ) );
    }
    
    /**
     * 判断业务对象状态是否为 <b>审核中</b>。
     * @param state 待判断状态
     */
    public static boolean isChecking( int state, int nature ) {
        return ( ( CHECKING << computeShift( nature ) ) & state ) > 0;
    }
    
    /**
     * 将当前状态设置为 <b>审核中</b>。
     * @param state 待设置状态
     * @return 表示 <b>审核中</b> 的状态
     */
    public static int setChecking( int state, int nature ) {
        return state | ( CHECKING << computeShift( nature ) );
    }
    
    /**
     * 将当前状态设置为 <b>非审核中</b>。
     * @param state 待设置状态
     * @return 表示 <b>非审核中</b> 的状态
     */
    public static int setUnchecking( int state, int nature ) {
        return state & ~ ( CHECKING << computeShift( nature ) );
    }
    
    /**
     * 判断业务对象状态是否为 <b>审核通过</b>。
     * @param state 待判断状态
     */
    public static boolean isChecked( int state, int direction, int nature ) {
        return ( ( CHECKED << computeShift( direction, nature ) ) & state ) > 0;
    }
    
    /**
     * 将当前状态设置为 <b>审核通过</b>。
     * @param state 待设置状态
     * @return 表示 <b>审核通过</b> 的状态
     */
    public static int setChecked( int state, int direction, int nature ) {
        return state | ( CHECKED << computeShift( direction, nature ) );
    }
    
    /**
     * 将当前状态设置为 <b>未审核通过</b>。
     * @param state 待设置状态
     * @return 表示 <b>未审核通过</b> 的状态
     */
    public static int setUnchecked( int state, int direction, int nature ) {
        return state & ~( CHECKED << computeShift( direction, nature ) );
    }
    
    /**
     * 判断业务对象状态是否为 <b>审核通过</b>。
     * @param state 待判断状态
     */
    public static boolean isChecked( int state, int nature ) {
        return ( ( CHECKED << computeShift( nature ) ) & state ) > 0;
    }
    
    /**
     * 将当前状态设置为 <b>审核通过</b>。
     * @param state 待设置状态
     * @return 表示 <b>审核通过</b> 的状态
     */
    public static int setChecked( int state, int nature ) {
        return state | ( CHECKED << computeShift( nature ) );
    }
    
    /**
     * 将当前状态设置为 <b>未审核通过</b>。
     * @param state 待设置状态
     * @return 表示 <b>未审核通过</b> 的状态
     */
    public static int setUnchecked( int state, int nature ) {
        return state & ~( CHECKED << computeShift( nature ) );
    }
}
