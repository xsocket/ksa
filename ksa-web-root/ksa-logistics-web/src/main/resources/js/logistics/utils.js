(function($){
    /* KSA - logistics utils 初始化命名空间 */
    ksa.logistics = ksa.logistics || {};
    $.extend( ksa.logistics, {
        /**
         * 弹出选择托单窗口
         */
        selectBookingNotes : function( callback, paras, title ) {
            paras = paras || {};
            top.$.open( {
                width : 1000,
                height : 600,
                src : ksa.buildUrl( "/component/logistics", "bookingnote-selection", paras ),
                title : title || "选择托单"
            }, callback );
        }
    } );    
})(jQuery);