$(function(){
    var $winWidth = 1000;
    var $winHeight = 600;
    
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();
    
    $("#go_checking, #go_checked, #go_entering").hide();
    
    $.fn.datagrid.defaults.loadEmptyMsg = '<span class="label label-warning">注意</span> 暂时没有需要审核的费用信息';
    var state = ( 0x2 << (10 - 2 * DIRECTION - 4 * NATURE) );
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/finance/charge/single", "query", { direction: DIRECTION, nature: NATURE, STATE: state } ),
        fit : true,
        border:false,
        pageSize: 20,
        fitColumns: false,
        columns:[ GET_CHARGE_SINGLE_COLUMN() ],
       onDblClickRow:function() {
           $("#btn_edit").click();
       },
       onClickRow:function(){
           $("#go_checking, #go_checked, #go_entering").hide();
           var row = $grid.datagrid( "getSelected" );
           var state = parseState( row.state );
           if( state == STATE_ENTERING ) { $("#go_checking").show(); } 
           else if( state == STATE_CHECKING ) { $("#go_checked, #go_entering").show(); }
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
            
            modal : false,
            collapsible : true,
            title:"费用信息：" + row.code,
            src : ksa.buildUrl( "/dialog/finance/charge/single", "view", { id : row.id, direction : DIRECTION, nature : NATURE } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    $("#go_checking").click(function(){ changeState(2, "确定将费用信息提交审核吗？"); });
    $("#go_checked").click(function(){ changeState(8, "确定费用信息通过审核吗？"); });
    $("#go_entering").click(function(){ changeState(1, "确定将费用信息打回进行修正吗？"); });
    

    function changeState( newState, message ) {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行相应操作。");
            return;
        }
        $.messager.confirm( message, function( ok ){
            if( ok ) {
                $.ajax({
                    url: ksa.buildUrl( "/dialog/finance/charge/single", "state" ),
                    data: { id : row.id, state : newState, nature: NATURE, direction: DIRECTION },
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
    // 解析托单的状态 返回可读的状态值
    var SHIFT = 10 - 2 * DIRECTION - 4 * NATURE;
    function parseState( state ) {
        if( state == -1 ) {
            return STATE_DELETED;
        } else if( state & (0x8 << SHIFT) ) {
            return STATE_CHECKED;
        } else if( state & (0x2 << SHIFT) ) {
            return STATE_CHECKING;
        } else if( state & (0x1 << SHIFT) ) {
            return STATE_ENTERING;
        } else {
            return STATE_NONE;
        }
    };
    

    // 绑定热键事件
    ksa.hotkey.bindButton( $(".tool-bar button.btn") );
    
    // 初始化托单查询组件
/*    $("#query").compositequery({
        onClear:function() {
            $grid.datagrid( "reload", {} );
        },
        onQuery:function( queryString ) {
            $grid.datagrid( "load", queryString );
        },
        conditions : CHARGE_SINGLE_CONDITION
    });*/
});