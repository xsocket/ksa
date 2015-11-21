$(function(){
    var $winWidth = 650;
    var $winHeight = 400;
    
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();
    
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/finance/invoice", "query",  { "DIRECTION" : DIRECTION } ),
        fit : true,
        border:false,
        pageSize: 20,
        fitColumns: false,
        columns:[ GET_INVOICE_TABLE_COLUMN() ],
        onDblClickRow:function() {
            $("#btn_edit").click();
        }
    });
    
    // 新建事件
    $("#btn_add").click( function() {
        // 打开新建页面
        $.open({
            width:$winWidth,
            height:$winHeight,
            
            title: "新建发票",
            src : ksa.buildUrl( "/dialog/finance/invoice", "create", { direction: DIRECTION } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    // 编辑事件
    $("#btn_edit").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行编辑/查看操作。");
            return;
        }
        // 打开编辑页面
        $.open({
            width:$winWidth,
            height:$winHeight,
            
            title: "发票【" + row.code + "】",
            src : ksa.buildUrl( "/dialog/finance/invoice", "edit", { id : row.id } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    // 删除事件
    $("#btn_delete").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行删除操作。");
            return;
        }
        
        $.messager.confirm( "确定删除发票 '" + row.code + "' 吗？", function( ok ){
            if( ok ) { 
                $.ajax({
                    url: ksa.buildUrl( "/dialog/finance/invoice", "delete" ),
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
    ksa.hotkey.bindButton( $(".tool-bar button.btn") );
    
    // 初始化账单查询组件
    $("#query").compositequery({
        onClear:function() {
            $grid.datagrid( "reload", {} );
        },
        onQuery:function( queryString ) {
            $grid.datagrid( "load", queryString );
        },
        conditions : INVOICE_QUERY_CONDITION
    });
});