$(function() {
    var $winWidth = 1000;
    var $winHeight = 600;
    
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();
    
    
    var $grid = $('#data_grid').datagrid({
        url : ksa.buildUrl( "/data/statistics/cargo", "query" ),
        fit : true,
        border : false,
        pagination : true,
        fitColumns : false,
        columns : [GET_CARGO_TABLE_COLUMN()],
        onDblClickRow : function() {
            $("#btn_edit").click();
        },
        onLoadSuccess : function( data ) {
            if( data.total <= 0 ) {
                $("#title").html("");
            } else {
                var details = gatherContainer( data );
                var gather = "";
                for( var k in details ) {
                    gather += "，箱类<b>"+k+"</b>共计 <span class='label label-important'>"+details[k]+"</span> 箱";
                }
                $("#title").html( "箱量统计：" + gather.substring(1) );
            }
            
            function gatherContainer( d ) {
                var containerTypes = [ "20", "40" ];
                var map = { "20" : 0, "40" : 0, "其他" : 0, "合计" : 0 };
                $.each( d.rows, function(j,row) {
                    var cargos = row.cargos;
                    if( !cargos || cargos.length <= 0 ) return;
                    map["合计"] += cargos.length;
                    $.each( cargos, function(i,v){
                        if( map[v.category] != null ) {
                            map[v.category]++;
                        } else {
                            map["其他"]++;
                        }
                    } );
                } );
                return map;
            };
        },
        rowStyler : function( index, row, css ) { }        
    });
    
    // 业务信息查看
    $("#btn_edit").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行编辑操作。");
            return;
        }
        // 打开编辑页面
        $.open({
            width:$winWidth,
            height:$winHeight,
            
            title:"编辑托单信息：" + row.code,
            src : ksa.buildUrl( "/dialog/logistics", "edit-" + row.type.toLowerCase(), { id : row.id, code : row.code } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    
    // 绑定热键事件
    ksa.hotkey.bindButton( $(".tool-bar button.btn") );
    
    // 初始化账单查询组件
    $("#query").compositequery({
        queryLabel : "统计汇总",
        onClear:function() {
            $grid.datagrid( "reload", {} );
        },
        onQuery:function( queryString ) {
            $grid.datagrid( "load", queryString );
        },
        conditions : BOOKINGNOTE_QUERY_CONDITION
    });
});