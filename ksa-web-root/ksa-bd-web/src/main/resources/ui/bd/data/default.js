$(function(){
    var $CURRENCY_ID = '00-currency';
    var $grid = null;
    var $typeId = TYPE_ID;
    var $column = [
          { field:'code',  title:'代码', width:50,   sortable:true },
          { field:'name', title:'中文名称', width:100, sortable:true },
          { field:'alias',   title:'英文名称', width:100, sortable:true},
          { field:'extra',  title:'汇率', width:100, sortable:true, 
                 hidden:true, align:'right',styler:function(){return 'color:blue;';}},
          { field:'note',   title:'备注', width:200,  sortable:true},
          { field:'rank',   title:'排序', width:30,  sortable:true, align:'right'}
      ];
    
    // 点击数据种类 过滤列表
    $("#type_list > li").click(function(){
        $typeId = $(this).attr("id");
        var typeName = $("span.type-name", $(this)).text();
        if( $typeId == $CURRENCY_ID ) {
            $column[3].hidden = false;
        } else {
            $column[3].hidden = true;
        }
        initDataGrid( $typeId, typeName, $column );
        ksa.menu.deactivate( "#type_list > li" );
        $("#type_list li .label-warning").addClass("label-info").removeClass("label-warning");
        ksa.menu.activate( this );
        $(".label", $(this)).addClass("label-warning").removeClass("label-info");
    });
    
    // 添加事件
    $("#btn-add").click( function() {
        $.open({
            width:600,
            height:440,
            title:"新建基本代码 - " + $("#type_name").text( ),
            iconCls : "icon-book",
            src : ksa.buildUrl( "/dialog/bd/data", "create", { 'type.id' : $typeId } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    // 编辑事件
    $("#btn-edit").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行编辑操作。");
            return;
        }
        
        // 打开编辑页面
        $.open({
            width:600,
            height:440,
            title:"编辑基本代码：" + row.name,
            iconCls : "icon-book",
            src : ksa.buildUrl( "/dialog/bd/data", "edit", { id : row.id } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    
    });
    
    // 删除事件
    $("#btn-delete").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行删除操作。");
            return;
        }
        
        $.messager.confirm( "确定删除基本代码 '" + row.name + "' 吗？", function( ok ){
            if( ok ) { 
                $.ajax({
                    url: ksa.buildUrl( "/dialog/bd/data", "delete" ),
                    data: { id : row.id },
                    success: function( result ) {
                        try {
                            if (result.status == "success") { 
                                $.messager.success( result.message );
                                $grid.datagrid( "reload" );
                            } 
                            else { $.messager.error( result.message ); }
                        } catch (e) { }
                    }
                }); 
            }
        } );
        
    });
    
    // 初始化列表
    function initDataGrid( typeId, typeName, column ) {
        $grid = $('#data-grid').datagrid({
            url: ksa.buildUrl( "/data/grid", "bd-data-bytype", { typeId : typeId } ),
            pagination:true,
            height : $(window).height() - 100,
            columns:[ column ],
            onDblClickRow : function() {
                $("#btn-edit").click();
            }
        });
        
        $("#type_name").text( typeName );
    }

    // 初始化 激活第一个基本菜单项
    $("li#" + $typeId).click();
    // 绑定热键事件
    ksa.hotkey.bindButton( $(".toolbar button.btn") );
    //ksa.hotkey.bindDatagrid( $grid );
    // 激活菜单
    ksa.menu.activate( "#_sidebar_bd_data" );
    
    $("#btn_search").click( function() {
        var search = $.trim( $("#txt_search").val() );
        if( !search ) {
            $("#btn_clear").click();
        } else {
            $grid.datagrid("reload", { "search":search } );
        }
    } );
    
    $("#btn_clear").click( function() {
        $grid.datagrid("reload", { "search":undefined });
        $("#txt_search").val("");
    } );
});