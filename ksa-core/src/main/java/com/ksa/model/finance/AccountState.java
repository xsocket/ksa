package com.ksa.model.finance;

import com.ksa.model.ModelState;


public class AccountState extends ModelState {

    /** 处理中，对于收入款的结算单是开票阶段，对于支出款的对账单是支付阶段。*/
    public static final int PROCESSING = 0x1;
    
    /** 结算完毕 */
    public static final int SETTLED = 0x20;
    
    /**
     * 判断结算单/对账单状态是否为 <b>处理中</b>。
     * @param state 待判断状态
     */
    public static boolean isProcessing( int state ) {
        return ( PROCESSING & state ) > 0;
    }
    
    /**
     * 将结算单/对账单状态设置为 <b>处理中</b>。
     * @param state 待设置状态
     * @return 表示 <b>处理中</b> 的状态
     */
    public static int setProcessing( int state ) {
        return state | PROCESSING; // 处理中
    }
    
    /**
     * 判断结算单/对账单状态是否为 <b>结算完毕</b>。
     * @param state 待判断状态
     */
    public static boolean isSettled( int state ) {
        return ( SETTLED & state ) > 0;
    }
    
    /**
     * 将结算单/对账单状态设置为 <b>结算完毕</b>。
     * @param state 待设置状态
     * @return 表示 <b>结算完毕</b> 的状态
     */
    public static int setSettled( int state ) {
        return state | SETTLED; // 结算完毕
    }
}
