var UNLOCK_DES = "用户已经锁定，点击此链接可以激活用户。";
var LOCK_DES = "用户已经激活，点击此链接可以锁定用户。";
function lockUser( obj, lock, i ) {
    var actionName = lock ? "锁定" : "激活";
    $.messager.confirm( "确定" + actionName + "用户吗？", function( ok ){
        if( ok ) { 
            if( lock ) {
                $( obj ).text( "已锁定" ).attr("title", UNLOCK_DES );
                $( obj ).attr("onclick", "return lockUser(this, false, " + i + ");");
            } else {
                $( obj ).text( "已激活" ).attr("title", LOCK_DES );
                $( obj ).attr( "onclick", "return lockUser(this, true, " + i + ");" );
            }
            // 更新样式
            var user = $("#user_grid").datagrid("getData").rows[i];
            user.locked = lock;
            $("#user_grid").datagrid( "updateRow", {index:i,row:user} );
            // 向后台提交更新
            $.ajax({
                url: ksa.buildUrl( "/dialog/security/user", "lock" ),
                data: { id : user.id, locked : lock },
                success: function( result ) {
                    try {
                        if (result.status == "success") { 
                            $.messager.success( result.message );
                        } 
                        else { $.messager.error( result.message ); }
                    } catch (e) { }
                }
            });
        }
    } );
    return false;
}

$(function(){  
    // 初始化布局
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();
    
    // 初始化列表
    var $grid = $('#user_grid').datagrid({
        url: ksa.buildUrl( "/data/grid", "security-user-all" ),
        height: $(window).height() - 105,
        pageSize: 20,
        columns:[[
            { field:'id',             title:'用户标识', width:50,   sortable:true },
            { field:'name',       title:'用户姓名', width:50,   sortable:true },
            { field:'email',        title:'电子邮箱', width:100, sortable:true},
            { field:'telephone', title:'联系电话', width:50,   sortable:true},
            { field:'is_locked',      title:'状态',      width:30, align:"center",
                formatter : function(v, data, i ) {
                    if( data.locked ) {
                        return "<a href=''javascript:void' onclick='return lockUser(this, false, "+i+");' title='" + UNLOCK_DES + "'>已锁定</a>";
                    } else {
                        return "<a href='javascript:void' onclick='return lockUser(this, true, "+i+");' title='" + LOCK_DES + "'>已激活</a>";
                    } 
                }  
            }
        ]],
        onSelect : function(i,user) {
            showUserRoles(user);
        },
        onClickRow : function(i,user) {
            showUserRoles(user);
        },
        onDblClickRow : function() {
            $("#btn_edit").click();
        },
        rowStyler:function(index,data,css){
            if ( data.locked ){
                return 'color:red;';
            }
        }
    });
    
    // 用户所含角色表
    var $roleGrid = $('#role_grid').datagrid({
        url: ksa.buildUrl( "/data/grid", "security-role-byuser" ),
        pagination : false,
        singleSelect: false,
        hotkey: false,
        columns:[[
            { field:'id', checkbox:true },
            { field:'name',           title:'角色名称', width:200,   sortable:true }
        ]],
        toolbar:[{
            text:'添加角色...',
            cls : 'btn-primary',
            iconCls:'icon-plus icon-white',
            handler:function(){
                var currentUser = $grid.datagrid( "getSelected" );
                if( !currentUser ) {
                    $.messager.warning("请选择一个用户数据后，再进行添加角色的操作。");
                    return;
                }
                var userId = currentUser.id;
                ksa.security.selectRoles( function( data ) {
                    var rowsCount = $roleGrid.datagrid("getData").total;
                    var rows = $roleGrid.datagrid("getData").rows;
                    // 后台添加用户操作
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
                            callback( userId, row.id );
                        }
                    }
                } );
            }
        },'-',{
            text:'移除角色',
            cls : 'btn-danger',
            iconCls:'icon-trash icon-white',
            handler:function(){
                var currentUser = $grid.datagrid( "getSelected" );
                if( !currentUser ) {
                    $.messager.warning("请选择一个用户数据后，再进行删除角色的操作。");
                    return;
                }
                var userId = currentUser.id;
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
    
    // 展示用户所属角色列表
    function showUserRoles( user ) {
        $('#role_grid').datagrid( "load", { "userId" : user.id } );
    }
    
    // 刷新列表
    function reloadData() {
        $grid.datagrid( "reload" );
        $('#role_grid').datagrid( "reload" );
    }
    
    
    // 添加用户事件
    $("#btn_add").click( function() {
        $.open({
            title:"新建用户",
            iconCls : "icon-user",
            
            src : ksa.buildUrl( "/dialog/security/user", "create" )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    // 编辑用户事件
    $("#btn_edit").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一个用户数据后，再进行编辑操作。");
            return;
        }
        
        // 打开编辑页面
        $.open({
            title:"编辑用户：" + row.name,
            
            iconCls : "icon-user",
            src : ksa.buildUrl( "/dialog/security/user", "edit", { id : row.id } )
        }, function(){
            reloadData();
        });
    
    });
    
    // 绑定热键事件
    ksa.hotkey.bindButton( $(".toolbar button.btn") );
    // 激活菜单
    ksa.menu.activate( "#_sidebar_system_user" );
});