$(function(){
    var $winWidth = 1000;
    var $winHeight = 600;
    
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/finance/profit", "query" ),
        queryParams : { 'CREATOR_ID' : CURRENT_USER_ID },
        fit : true,
        border:false,
        pageSize: 5,
        fitColumns: false,
        columns:[ GET_PROFIT_TABLE_COLUMN( { 'cargo_container':true, 'profit_gather':false, 'income_gather':false, 'expense_gather':false } ) ],
       onDblClickRow:function() { edit(); },
        rowStyler:function(index,row,css){
           if ( row.state == -1 ){
               return 'text-decoration: line-through;';
           }
        },  // jira KSA-17
        toolbar:[ {
            text:'编辑/查看...',
            cls: 'btn-warning',
            float: 'right',
            iconCls:'icon-edit icon-white',
            handler:function(){ edit(); }
        } ]
    });
    
    // 编辑事件
    function edit() {
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
            
            title:"费用信息：" + row.code,
            src : ksa.buildUrl( "/dialog/finance/charge", "view", { id : row.id } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    }
    
    var STATE_CHECKED = "审核通过";
    var STATE_CHECKING = "审核中";
    var STATE_ENTERING = "录入中";
    var STATE_NONE = "暂未录入";
    var STATE_DELETED = "业务作废";
    // 解析托单的状态 返回可读的状态值
    function parseState( state ) {
        if( state == -1 ) {
            return STATE_DELETED;
        } else if( state & 0x8 ) {
            return STATE_CHECKED;
        } else if( state & 0x2 ) {
            return STATE_CHECKING;
        } else if( state & 0x1 ) {
            return STATE_ENTERING;
        } else {
            return STATE_NONE;
        }
    };
});