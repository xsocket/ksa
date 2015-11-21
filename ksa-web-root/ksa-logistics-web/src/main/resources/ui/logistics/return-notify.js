$(function(){
    var $winWidth = 1000;
    var $winHeight = 600;
    
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();
    
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/logistics/bookingnote", "query-return"  ),
        queryParams : { "checkDate" : computeCheckDate() },
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
    
    function computeCheckDate() {
        if( $("#checkDateCheck").attr("checked") ) {
            var checkDate = new Date();
            checkDate.setDate( checkDate.getDate() - $("#checkDate").val() );
            return ksa.utils.dateFormatter( checkDate );
        }
        return undefined;
    }
    
    $("#checkDateCheck").click(function(){
        if( $("#checkDateCheck").attr("checked") ) {
            $("#checkDate").attr( "readonly", false );
        } else {
            $("#checkDate").attr( "readonly", "readonly" );
        }
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
    
    // 绑定热键事件
    ksa.hotkey.bindButton( $(".tool-bar button.btn") );
 
    // 初始化托单查询组件
    $("#query").compositequery({
        onClear:function() {
            $grid.datagrid( "reload", { "checkDate" : computeCheckDate() } );
        },
        onQuery:function( queryString ) {
            var checkDate = computeCheckDate();
            if( checkDate ) {
                queryString = ( "checkDate=" + checkDate + "&" + queryString );
            } else {
                queryString = ( "checkDate=&" + queryString );
            }
            $grid.datagrid( "load", queryString );
        },
        conditions : BOOKINGNOTE_QUERY_CONDITION
    });
});