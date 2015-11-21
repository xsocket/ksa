(function($){
    /* KSA - finance utils 初始化命名空间 */
    ksa.finance = ksa.finance || {};
    $.extend( ksa.finance, {
        /**
         * 弹出选择费用窗口
         */
        selectCharges : function( callback, paras, title ) {
            paras = paras || {};
            top.$.open( {
                width : 1000,
                height : 600,
                src : ksa.buildUrl( "/component/finance", "charge-selection", paras ),
                title : title || "选择费用"
            }, callback );
        },
        /**
         * 弹出选择费用窗口
         */
        selectInvoices : function( callback, paras, title ) {
            paras = paras || {};
            top.$.open( {
                width : 1000,
                height : 600,
                src : ksa.buildUrl( "/component/finance", "invoice-selection", paras ),
                title : title || "选择发票"
            }, callback );
        },
        /**
         * 弹出选择托单窗口
         */
        selectBookingNotes : function( callback, paras, title ) {
            paras = paras || {};
            top.$.open( {
                width : 1000,
                height : 600,
                src : ksa.buildUrl( "/component/finance", "bookingnote-selection", paras ),
                title : title || "选择托单"
            }, callback );
        },
        /**
         * 弹出选择托单窗口
         */
        selectChargeTemplates : function( callback, paras, title ) {
            paras = paras || {};
            top.$.open( {
                width : 1000,
                height : 600,
                src : ksa.buildUrl( "/component/finance", "charge-template-selection", paras ),
                title : title || "选择费用模板"
            }, callback );
        }
    } );    
})(jQuery);