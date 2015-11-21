(function($){
    /* KSA - security utils 初始化命名空间 */
    ksa.security = ksa.security || {};
    $.extend( ksa.security, {
        /**
         * 弹出选择用户窗口
         */
        selectUsers : function( callback ) {
            top.$.open( {
                src : ksa.buildUrl( "/component/security", "user-selection" ),
                title : "选择用户"
            }, callback );
        },
        /**
         * 弹出选择用户窗口
         */
        selectRoles : function( callback ) {
            top.$.open( {
                src : ksa.buildUrl( "/component/security", "role-selection" ),
                title : "选择角色"
            }, callback );
        },
        /**
         * 弹出选择权限窗口
         */
        selectPermissions : function( callback ) {
            top.$.open( {
                src : ksa.buildUrl( "/component/security", "permission-selection" ),
                title : "选择权限"
            }, callback );
        }
    } );    
})(jQuery);