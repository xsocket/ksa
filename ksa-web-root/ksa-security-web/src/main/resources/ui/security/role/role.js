$(function(){
    // 角色所含用户表
    var $userGrid = $('#user_grid').datagrid({
        url: ksa.buildUrl( "/data/grid", "security-user-byrole", { roleId : $("#role_id").val() } ),
        pagination : false,
        singleSelect: false,
        columns:[[
            { field:'dump', checkbox:true },
            { field:'id',       title:'用户标识', width:50,   sortable:true, formatter:function(v,data,i) {
                return v + "<input type='hidden' name='userIds' value='" + v + "'/>";
            } },
            { field:'name', title:'用户姓名', width:50,   sortable:true }
        ]],
        toolbar:[{
            text:'添加用户...',
            cls: 'btn-primary',
            iconCls:'icon-plus icon-white',
            handler:function(){
                ksa.security.selectUsers( function( data ) {
                    var rowsCount = $userGrid.datagrid("getData").total;
                    var rows = $userGrid.datagrid("getData").rows;
                 // 后台添加用户操作
                    var callback = ksa.doAddUser || ( function(){} ); 
                    var roleId = $("#role_id").val();
                    for( var i = 0; i < data.length; i++ ) {
                        var duplicate = false;
                        for( var j = 0; j < rowsCount; j++ ) {
                            if( data[i].value == rows[j].id ) {
                                duplicate = true; break;
                            }
                        }
                        if( !duplicate ) {
                            // 将选择的用户 加入列表
                            var user = { id: data[i].value, name: data[i].text };
                            $userGrid.datagrid( "appendRow", user );
                            callback( roleId, user.id );
                        }
                    }
                } );
            }
        },'-',{
            text:'移除用户',
            cls:'btn-danger',
            iconCls:'icon-trash icon-white',
            handler:function(){
                var selectedRows = $userGrid.datagrid("getSelections");
                // 后台移除用户操作
                var callback = ksa.doDeleteUser || ( function(){} ); 
                var roleId = $("#role_id").val();
                $.each( selectedRows, function(i,row) {
                    var index = $userGrid.datagrid('getRowIndex', row);
                    $userGrid.datagrid('deleteRow', index);
                    callback( roleId, row.id );
                } );
            }
        }]
    });
    
    // 角色所含权限表
    var $permissionGrid = $('#permission_grid').datagrid({
        url: ksa.buildUrl( "/data/grid", "security-permission-byrole", { roleId : $("#role_id").val() } ),
        pagination : false,
        singleSelect: false,
        columns:[[
              { field:'dump', checkbox:true },
              { field:'name',           title:'权限', width:100,   sortable:true, formatter:function(v,data,i) {
                  return v + "<input type='hidden' name='permissionIds' value='" + data.id + "'/>";
              } },
              { field:'description',  title:'说明', width:200,    sortable:true}
          ]],
          toolbar:[{
              text:'添加权限...',
              cls:'btn-primary',
              iconCls:'icon-plus icon-white',
              handler:function(){
                  ksa.security.selectPermissions( function( data ) {
                      var rowsCount = $permissionGrid.datagrid("getData").total;
                      var rows = $permissionGrid.datagrid("getData").rows;
                   // 后台添加用户操作
                      var callback = ksa.doAddPerssion || ( function(){} ); 
                      var roleId = $("#role_id").val();
                      for( var i = 0; i < data.length; i++ ) {
                          var duplicate = false;
                          for( var j = 0; j < rowsCount; j++ ) {
                              if( data[i].value == rows[j].id ) {
                                  duplicate = true; break;
                              }
                          }
                          if( !duplicate ) {
                              // 将选择的用户 加入列表
                              var permission = { id: data[i].value, name: data[i].text, description:data[i].data.description };
                              $permissionGrid.datagrid( "appendRow", permission );
                              callback( roleId, permission.id );
                          }
                      }
                  } );
              }
          },'-',{
              text:'移除权限',
              cls:'btn-danger',
              iconCls:'icon-trash icon-white',
              handler:function(){
                  var selectedRows = $permissionGrid.datagrid("getSelections");
                  // 后台移除权限操作
                  var callback = ksa.doDeletePermission || ( function(){} ); 
                  var roleId = $("#role_id").val();
                  $.each( selectedRows, function(i,row) {
                      var index = $permissionGrid.datagrid('getRowIndex', row);
                      $permissionGrid.datagrid('deleteRow', index);
                      callback( roleId, row.id );
                  } );
              }
          }]
    });
});