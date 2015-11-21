package com.ksa.model.logistics;

import com.ksa.model.ModelState;


public class BookingNoteState extends ModelState {

    /** 表示托单已经被删除。*/
    public static final int DELETED = -1;
    
    public static final int RETURNED = 0x10000000;  // 十进制为 268435456
    
    /***
     * 判断业务对象是否已经完成退单
     * @param state
     * @return
     */
    public static boolean isReturned( int state ) {
        return ( RETURNED & state ) > 0;
    }
    
    /***
     * 将当前业务对象的状态设置为 <b>已退单</b>。
     * @param state
     * @return
     */
    public static int setReturned( int state ) {
        return  state | RETURNED;
    }
    
    /***
     * 将当前业务对象的状态设置为 <b>未退单</b>。
     * @param state
     * @return
     */
    public static int setUnreturned( int state ) {
        return state & ~RETURNED ;
    }
    
    
    /**
     * 判断托单对象状态是否为 <b>已删除</b>。
     * @param state 待判断状态
     */
    public static boolean isDeleted( int state ) {
        return state == DELETED;
    }
    
    /**
     * 将当前托单状态设置为 <b>已删除</b>。
     * @param state 待设置状态
     * @return 表示 <b>已删除</b> 的状态
     */
    public static int setDeleted( int state ) {
        return DELETED; 
    }
}
