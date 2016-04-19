$(function(){
    var $winWidth = 1000;
    var $winHeight = 600;
    
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();
    
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/logistics/bookingnote", "query" ),
        fit : true,
        border:false,
        pageSize: 20,
        fitColumns: false,
        columns:[ GET_BOOKINGNOTE_TABLE_COLUMN() ],
        onDblClickRow:function() { edit(); },
        rowStyler:function(index,row,css){
           if ( row.state == -1 ){
               return 'color:#F00;text-decoration: line-through;';
           }
        }
    });
    
    // 变更业务类型事件
    $("#btn_modify_type ul a").click( function() {
    	var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行类型变更操作。");
            return;
        }
        var type = $(this).attr("name");
        $.messager.confirm( "确定变更托单 '" + row.code + "' 的业务类型为【" + $(this).text() + "】吗？", function( ok ){
            if( ok ) { 
                $.ajax({
                    url: ksa.buildUrl( "/dialog/logistics/bookingnote", "change-type" ),
                    data: { "id" : row.id, "type" : type },
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
    
    // 添加事件
    $("#btn_add ul a").click( function() {
        $.open({
            width:$winWidth,
            height:$winHeight,
            modal : false,
            collapsible : true,
            title:"新建托单信息",
            src : ksa.buildUrl( "/dialog/logistics", "create-" + $(this).attr("name") )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    // 编辑事件
    $("#btn_edit").click( function(){ edit(); } );
    
    function edit(){
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行编辑操作。");
            return;
        }
        // 打开编辑页面
        $.open({
            width:$winWidth,
            height:$winHeight,
            modal : false,
            collapsible : true,
            
            title:"编辑托单信息：" + row.code,
            src : ksa.buildUrl( "/dialog/logistics", "edit-" + row.type.toLowerCase(), { id : row.id, code : row.code } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    }
    
    // 删除事件
    $("#btn_delete").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行删除操作。");
            return;
        }
        
        $.messager.confirm( "确定删除托单 '" + row.code + "' 吗？", function( ok ){
            if( ok ) { 
                $.ajax({
                    url: ksa.buildUrl( "/dialog/logistics/bookingnote", "delete" ),
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
    
    // 初始化托单查询组件
    $("#query").compositequery({
        onClear:function() {
            $grid.datagrid( "reload", {} );
        },
        onQuery:function( queryString ) {
            $grid.datagrid( "load", queryString );
        },
        conditions : BOOKINGNOTE_QUERY_CONDITION
    });
    
 // 新增导出默认结算单的功能。
    $("#btn_download").click(function() {
        var form = $("form.compositequery-form").clone()
                .attr("class", "")
                .attr("method", "POST")
                .attr("action", ksa.buildUrl( "/dialog/logistics/bookingnote", "download") )
                .attr("style", "display:none;top:-100px;left:-100px;height:0;width:0").appendTo( $("body") );
        var pager = $("#data_grid").datagrid("getPager").pagination("options");
        
        //var form = $( "<form action='"+ksa.buildUrl( "/dialog/logistics/bookingnote", "download")+"' method='POST' style='display:none;top:-100px;left:-100px;height:0;width:0'></form>" ).appendTo( $("body") );
        form.form("submit", {  
            url: ksa.buildUrl( "/dialog/logistics/bookingnote", "download"),  
            onSubmit: function() {
                // 导出默认列
                $("<input type='hidden' name='page' value='"+pager.pageNumber+"' />").appendTo( form );
                $("<input type='hidden' name='rows' value='"+pager.pageSize+"' />").appendTo( form );
            }
        }); 
        return false;
    });
});