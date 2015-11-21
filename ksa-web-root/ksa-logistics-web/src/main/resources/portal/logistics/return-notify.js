$(function(){
    var $winWidth = 1000;
    var $winHeight = 600;
    
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/logistics/bookingnote", "query-return"  ),
        queryParams : { "checkDate" : computeCheckDate() },
        fit : true,
        border:false,
        pageSize: 5,
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
    
    // 编辑事件
    $("#btn_edit").click( function(){ edit(); } );
    
    function edit(){
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行退单操作。");
            return;
        }
        // 打开编辑页面
        top.$.open({
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
});