$(function(){
    var $winWidth = 1000;
    var $winHeight = 600;
    
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();
    
    $("#go_checking, #go_checked, #go_entering").hide();
    
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/finance/profit", "query"/*(NATURE == 1 ? "query-native" : "query-foreign")*/ ),// 境内外统一
        fit : true,
        border:false,
        pageSize: 20,
        fitColumns: false,
        columns:[ GET_PROFIT_TABLE_COLUMN( { 'creator_name':false, 'cargo_container':true } ) ],
       onDblClickRow:function() {
           $("#btn_edit").click();
       },
       onClickRow:function(){
           $("#go_checking, #go_checked, #go_entering, #return_entering, #btn_download").hide();
           var row = $grid.datagrid( "getSelected" );
           var state = parseState( row.state );
           if( state == STATE_ENTERING ) { $("#go_checking").show(); } 
           else if( state == STATE_CHECKING ) { $("#go_checked, #go_entering").show(); }
           else if( state == STATE_CHECKED ) { $("#return_entering").show(); }
           if( state != STATE_NONE && state !=STATE_DELETED ) { $("#btn_download").show(); }
       },
        rowStyler:function(index,row,css){
           if ( row.state == -1 ){
               return 'text-decoration: line-through;';
           }
        }
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
            closable : true,
            modal : false,
            collapsible : true,
            title:"费用信息：" + row.code,
            src : ksa.buildUrl( "/dialog/finance/charge", "view", { id : row.id/*, nature: NATURE*/ } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    $("#go_checking").click(function(){ changeState(2, "确定将费用信息提交审核吗？"); });
    $("#go_checked").click(function(){ changeState(8, "确定费用信息通过审核吗？"); });
    $("#go_entering").click(function(){ changeState(1, "确定将费用信息打回进行修正吗？"); });
    $("#return_entering").click(function(){ changeState(1, "确定将费用信息打回重新编辑吗？"); });
    $("#btn_download").click(function(){
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行相应操作。");
            return;
        }
        DOWNLOAD_RECORDBILL( row.id );
    });
    
    function changeState( newState, message ) {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行相应操作。");
            return;
        }
        $.messager.confirm( message, function( ok ){
            if( ok ) {
                $.ajax({
                    url: ksa.buildUrl( "/dialog/finance/charge", "state" ),
                    data: { id : row.id, state : newState/*, nature : NATURE*/ },
                    success: function( result ) {
                        try {
                            if (result.status == "success") { 
                                $grid.datagrid( "reload" );
                            } 
                            else { parent.$.messager.error( result.message ); }
                        } catch (e) { }
                    }
                }); 
            }
        } );
        return false;
    }
    
    var STATE_CHECKED = "审核通过";
    var STATE_CHECKING = "审核中";
    var STATE_ENTERING = "录入中";
    var STATE_NONE = "暂未录入";
    var STATE_DELETED = "业务作废";
    
    var shift = 0;
   /* if(-1 == NATURE) {
        shift = 20;
    }*/
    // 解析托单的状态 返回可读的状态值
    function parseState( state ) {
        if( state == -1 ) {
            return STATE_DELETED;
        } else if( state & (0x8 << shift) ) {
            return STATE_CHECKED;
        } else if( state & (0x2 << shift) ) {
            return STATE_CHECKING;
        } else if( state & (0x1 << shift) ) {
            return STATE_ENTERING;
        } else {
            return STATE_NONE;
        }
    };
    

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
        conditions : PROFIT_QUERY_CONDITION
    });
});