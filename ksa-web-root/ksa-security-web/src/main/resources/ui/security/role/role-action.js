(function($){
    $.extend( ksa, {
        doAddUser : function( roleId, userId ) {
            $.ajax({
                url: ksa.buildUrl( "/dialog/security/role", "insert-user" ),
                data: { id : roleId, userIds : userId }
            });
        }, 
        doDeleteUser : function( roleId, userId ) {
            $.ajax({
                url: ksa.buildUrl( "/dialog/security/role", "delete-user" ),
                data: { id : roleId, userIds : userId }
            });
        }, 
        doAddPermission : function( roleId, permissionId ) {
            $.ajax({
                url: ksa.buildUrl( "/dialog/security/role", "insert-permission" ),
                data: { id : roleId, permissionIds : permissionId }
            });
        }, 
        doDeletePermission : function( roleId, permissionId ) {
            $.ajax({
                url: ksa.buildUrl( "/dialog/security/role", "delete-permission" ),
                data: { id : roleId, permissionIds : permissionId }
            });
        }
    } );
})(jQuery);