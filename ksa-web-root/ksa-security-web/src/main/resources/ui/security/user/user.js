//验证两次密码输入相同
function validatePassword() {
    return ( $("input[name='password']").val() == $("input[name='passwordAgain']").val() ); 
}
// 修改密码时，验证信息完整
function validateOldPassword() {
    var old = $("input[name='passwordOld']").val();
    var pass = $("input[name='password']").val();
    return !( pass != "" && old == "" );
}
function validateNewPassword() {
    var old = $("input[name='passwordOld']").val();
    var pass = $("input[name='password']").val();
    return !( pass == "" && old != "" );
}

$(function(){
  //用户所含角色表
    var $roleGrid = $('#role_grid').datagrid({
        url: ksa.buildUrl( "/data/grid", "security-role-byuser", { userId : $("#user_id").val() } ),
        pagination : false,
        singleSelect: false,
        columns:[[
            { field:'dump', checkbox:true },
            { field:'name',           title:'角色名称', width:200,   sortable:true, formatter:function(v,data,i) {
                return v + "<input type='hidden' name='roleIds' value='" + data.id + "'/>";
            } }
        ]],
        toolbar:[{
            text:'添加角色...',
            cls : 'btn-primary',
            iconCls:'icon-plus icon-white',
            handler:function(){
                ksa.security.selectRoles( function( data ) {
                    var rowsCount = $roleGrid.datagrid("getData").total;
                    var rows = $roleGrid.datagrid("getData").rows;
                    // 后台添加用户操作
                    var userId = $("#user_id").val();
                    var callback = ksa.doAddRole || ( function(){} ); 
                    for( var i = 0; i < data.length; i++ ) {
                        var duplicate = false;
                        for( var j = 0; j < rowsCount; j++ ) {
                            if( data[i].value == rows[j].id ) {
                                duplicate = true; break;
                            }
                        }
                        if( !duplicate ) {
                            // 将选择的角色 加入列表
                            var role = { id: data[i].value, name: data[i].text };
                            $roleGrid.datagrid( "appendRow", role );
                            callback( userId, role.id );
                        }
                    }
                } );
            }
        },'-',{
            text:'移除角色',
            cls : 'btn-danger',
            iconCls:'icon-trash icon-white',
            handler:function(){
                var userId = $("#user_id").val();
                var selectedRows = $roleGrid.datagrid("getSelections");
                // 后台移除用户操作
                var callback = ksa.doDeleteRole || ( function(){} ); 
                $.each( selectedRows, function(i,row) {
                    var index = $roleGrid.datagrid('getRowIndex', row);
                    $roleGrid.datagrid('deleteRow', index);
                    callback( userId, row.id );
                } );
            }
        }]
    });
});