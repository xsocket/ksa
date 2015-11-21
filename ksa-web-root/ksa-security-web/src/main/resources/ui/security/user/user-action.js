(function($){
    $.extend( ksa, {
        doAddRole : function( userId, roleId  ) {
            $.ajax({
                url: ksa.buildUrl( "/dialog/security/user", "insert-role" ),
                data: { id : userId, roleIds : roleId }
            });
        }, 
        doDeleteRole : function( userId, roleId ) {
            $.ajax({
                url: ksa.buildUrl( "/dialog/security/user", "delete-role" ),
                data: { id : userId, roleIds : roleId }
            });
        }
    } );
})(jQuery);