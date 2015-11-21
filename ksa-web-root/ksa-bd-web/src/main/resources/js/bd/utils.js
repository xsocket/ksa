(function($){
    /* KSA - bd utils 初始化命名空间 */
    ksa.bd = ksa.bd || {};
    $.extend( ksa.bd, {
        /**
         * 弹出选择单位类型窗口
         */
        selectDepartments : function( callback ) {
            top.$.open( {
                src : ksa.buildUrl( "/component/bd", "department-selection" ),
                title : "选择单位类型"
            }, callback );
        },
        /**
         * 弹出选择单位抬头窗口
         */
        selectPartnerAlias : function( callback, partnerId ) {
            top.$.open( {
                width: 600,
                height: 400,
                src : ksa.buildUrl( "/component/bd", "partner-alias-selection", { "partner.id" : partnerId } ),
                title : "选择单位抬头"
            }, callback );
        },
        /**
         * 选择并替换单位抬头
         */
        replacePartnerAlias : function( partnerId, selector ) {
            ksa.bd.selectPartnerAlias( function( alias ) {
                try{
                    if( (typeof alias) == "string" && alias ) {
                        $( selector ).val( alias );
                    }
                }catch(e){}
            }, partnerId );
        },
    } );    
})(jQuery);