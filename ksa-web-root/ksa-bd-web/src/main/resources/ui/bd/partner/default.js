$(function(){
    var $winWidth = 780;
    var $winHeight = 450;
    var $grid = null;
    $grid = $('#data-grid').datagrid({
        url: ksa.buildUrl( "/data/grid", "bd-partner-all" ),
        height: $(window).height() - 105,        
        pageSize: 20,
        columns:[ [
            { field:'important', title:'状态',      width:25,   sortable:true, align:'center', 
                formatter: function(value) { 
                	if( value > 0 ) { return "<i class='icon-ok'></i>"; } 
                	else if( value < 0 ) { return "<i class='icon-remove'></i>"; } 
                	else { return ""; } 
                } 
            },
            { field:'code',         title:'代码',      width:100,   sortable:true },
            { field:'name',        title:'名称',      width:100, sortable:true },
            { field:'alias',          title:'抬头',      width:200, sortable:true },
            { field:'address',     title:'地址',      width:200, sortable:true},
            { field:'saler_name', title:'销售担当', width:50, sortable:true, align:'center',
                formatter: function(value, data ){ try{return data.saler.name;}catch(e){return "";} } },
            { field:'pp',            title:'付款周期', width:50,  sortable:true, align:'center', formatter: function(value){return "<b>" + value + "</b> 天付款";} },
            { field:'rank',         title:'排序',      width:25,      sortable:true, align:'right'}
       ] ],
       onDblClickRow : function() {
           $("#btn-edit").click();
       }
    });
    
    // 添加事件
    $("#btn-add").click( function() {
        $.open({
            width:$winWidth,
            height:$winHeight,
            
            title:"新建合作伙伴信息",
            src : ksa.buildUrl( "/dialog/bd/partner", "create" )
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
            width:$winWidth,
            height:$winHeight,
            
            title:"编辑合作伙伴信息：" + row.name,
            src : ksa.buildUrl( "/dialog/bd/partner", "edit", { id : row.id } )
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
        
        $.messager.confirm( "确定冻结合作伙伴 '" + row.name + "' 吗？", function( ok ){
            if( ok ) { 
                $.ajax({
                    url: ksa.buildUrl( "/dialog/bd/partner", "delete" ),
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

    // 绑定热键事件
    ksa.hotkey.bindButton( $(".toolbar button.btn") );
    // 激活菜单
    ksa.menu.activate( "#_sidebar_bd_partner" );
    
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