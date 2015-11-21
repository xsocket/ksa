$(function(){
    var $winWidth = 1000;
    var $winHeight = 600;

    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/logistics/bookingnote", "query" ),
        queryParams : { 'CREATOR_ID' : CURRENT_USER_ID },
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

    $("#btn_add ul a").click( function() { create( $(this).attr("name") ); });
    function create( type ) {
        top.$.open({
            width:$winWidth,
            height:$winHeight,
            
            modal : false,
            collapsible : true,
            title:"新建托单信息",
            src : ksa.buildUrl( "/dialog/logistics", "create-" + type )
        }, function(){
            $grid.datagrid( "reload" );
        });
    }

    
    $("#btn_edit").click(function(){ edit(); });    
    function edit(){
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            top.$.messager.warning("请选择一条数据后，再进行删除操作。");
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
    
    $("#edit_income").click(function(){ editCharge( 1, 1 ); });
    $("#edit_expense").click(function(){ editCharge( -1, 1 ); });
    $("#edit_foreign").click(function(){ editCharge( 1, -1 ); });
    function editCharge( direction, nature ){
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            top.$.messager.warning("请选择一条数据后，再进行费用输入操作。");
            return;
        }
        // 打开编辑页面
        top.$.open({
            width:$winWidth,
            height:$winHeight,
            
            modal : false,
            collapsible : true,
            title:"费用信息：" + row.code,            
            src : ksa.buildUrl( "/dialog/finance/charge" + (nature==1?"/single":""), "view", { id : row.id, 'direction':direction, 'nature':nature } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    }
});