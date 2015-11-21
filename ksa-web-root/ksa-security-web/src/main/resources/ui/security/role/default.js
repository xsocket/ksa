$(function(){  
    // 初始化布局
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();

    // 初始化列表
    var $grid = $('#role_grid').datagrid({
        url: ksa.buildUrl( "/data/grid", "security-role-all" ),
        pageSize: 20,
        border:false,
        columns:[[
            { field:'name',           title:'角色名称', width:100,   sortable:true },
            { field:'description',  title:'角色说明', width:200, sortable:true}
        ]],
        onDblClickRow : function() {
            $("#btn_edit").click();
        },
        onClickRow : function(i,role) {
            showRoleDetail(role);
        },
        onSelect : function(i,role) {
            showRoleDetail(role);
        }
    });    
    
 // 角色所含用户表
    var $userGrid = $('#user_grid').datagrid({
        url: ksa.buildUrl( "/data/grid", "security-user-byrole" ),
        pagination : false,
        singleSelect: false,
        hotkey: false,
        columns:[[
            { field:'dump', checkbox:true },
            { field:'id',       title:'用户标识', width:50,   sortable:true, formatter:function(v,data,i) {
                return v + "<input type='hidden' name='userIds' value='" + v + "'/>";
            } },
            { field:'name', title:'用户姓名', width:50,   sortable:true }
        ]],
        toolbar:[{
            text:'添加用户...',
            cls : 'btn-primary',
            iconCls:'icon-plus icon-white',
            handler:function(){
                var currentRole = $grid.datagrid( "getSelected" );
                if( !currentRole ) {
                    $.messager.warning("请选择一个角色数据后，再进行添加用户的操作。");
                    return;
                }
                var roleId = currentRole.id;
                ksa.security.selectUsers( function( data ) {
                    var rowsCount = $userGrid.datagrid("getData").total;
                    var rows = $userGrid.datagrid("getData").rows;
                    // 后台添加用户操作
                    var callback = ksa.doAddUser || ( function(){} ); 
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
            cls : 'btn-danger',
            iconCls:'icon-trash icon-white',
            handler:function(){
                var currentRole = $grid.datagrid( "getSelected" );
                if( !currentRole ) {
                    $.messager.warning("请选择一个角色数据后，再进行移除用户的操作。");
                    return;
                }
                var roleId = currentRole.id;
                var selectedRows = $userGrid.datagrid("getSelections");
                // 后台移除用户操作
                var callback = ksa.doDeleteUser || ( function(){} ); 
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
        url: ksa.buildUrl( "/data/grid", "security-permission-byrole" ),
        pagination : false,
        singleSelect: false,
        hotkey: false,
        columns:[[
              { field:'dump', checkbox:true },
              { field:'name',           title:'权限', width:100,   sortable:true, formatter:function(v,data,i) {
                  return v + "<input type='hidden' name='permissionIds' value='" + data.id + "'/>";
              } },
              { field:'description',  title:'说明', width:200,    sortable:true}
          ]],
          toolbar:[{
              text:'添加权限...',
              cls: 'btn-primary',
              iconCls:'icon-plus icon-white',
              handler:function(){
                  var currentRole = $grid.datagrid( "getSelected" );
                  if( !currentRole ) {
                      $.messager.warning("请选择一个角色数据后，再进行添加权限的操作。");
                      return;
                  }
                  var roleId = currentRole.id;
                  ksa.security.selectPermissions( function( data ) {
                      var rowsCount = $permissionGrid.datagrid("getData").total;
                      var rows = $permissionGrid.datagrid("getData").rows;
                      // 后台添加用户操作
                      var callback = ksa.doAddPermission || ( function(){} ); 
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
              cls : 'btn-danger',
              iconCls:'icon-trash icon-white',
              handler:function(){
                  var currentRole = $grid.datagrid( "getSelected" );
                  if( !currentRole ) {
                      $.messager.warning("请选择一个角色数据后，再进行移除权限的操作。");
                      return;
                  }
                  var roleId = currentRole.id;
                  var selectedRows = $permissionGrid.datagrid("getSelections");
                  // 后台移除权限操作
                  var callback = ksa.doDeletePermission || ( function(){} ); 
                  $.each( selectedRows, function(i,row) {
                      var index = $permissionGrid.datagrid('getRowIndex', row);
                      $permissionGrid.datagrid('deleteRow', index);
                      callback( roleId, row.id );
                  } );
              }
          }]
    });
    
    function showRoleDetail( role ) {
        $('#user_grid').datagrid( "load", { "roleId" : role.id } );
        $('#permission_grid').datagrid( "load", { "roleId" : role.id } );
    }
    
    function reloadData() {
        $grid.datagrid( "reload" );
        $('#user_grid').datagrid( "reload" );
        $('#permission_grid').datagrid( "reload" );
    }
    
    // 添加角色事件
    $("#btn_add").click( function() {
        $.open({
            title:"新建角色",
            src : ksa.buildUrl( "/dialog/security/role", "create" )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    // 编辑角色事件
    $("#btn_edit").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一个角色数据后，再进行编辑操作。");
            return;
        }
        
        // 打开编辑页面
        $.open({
            title:"编辑角色：" + row.name,
            src : ksa.buildUrl( "/dialog/security/role", "edit", { id : row.id } )
        }, function(){
            reloadData();
        });
    
    });
    // 删除角色事件
    $("#btn_delete").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一个角色数据后，再进行删除操作。");
            return;
        }
        
        $.messager.confirm( "确定删除角色 '" + row.name + "' 吗？", function( ok ){
            if( ok ) { 
                $.ajax({
                    url: ksa.buildUrl( "/dialog/security/role", "delete" ),
                    data: { id : row.id },
                    success: function( result ) {
                        try {
                            if (result.status == "success") { 
                                $.messager.success( result.message );
                                reloadData();
                            } 
                            else { $.messager.error( result.message ); }
                        } catch (e) { }
                    }
                });
            }
        } );
        
    });
    // 绑定热键事件
    ksa.hotkey.bindButton( $(".toolbar button.btn") );
    // 激活菜单
    ksa.menu.activate( "#_sidebar_system_role" );
});