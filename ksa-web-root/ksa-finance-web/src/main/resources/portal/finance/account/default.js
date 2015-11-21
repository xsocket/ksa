$(function(){
    var $winWidth = 1000;
    var $winHeight = 600;

    $("#go_processing, #go_checking, #go_checked, #go_unchecked, #go_settled").hide();
    
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/finance/account", "query" ),
        fit : true,
        border:false,
        pageSize: 5,
        fitColumns: false,
        columns:[ GET_ACCOUNT_TABLE_COLUMN( { "a.type": true } ) ],
        onDblClickRow:function() {$("#btn_edit").click();},
       onClickRow:function(){
           $("#go_processing, #go_checking, #go_checked, #go_unchecked, #go_settled").hide();
           var row = $grid.datagrid( "getSelected" );
           var state = parseState( row.state );
           if( state == STATE_NONE ) { $("#go_processing").show(); } 
           else if( state == STATE_PROCESSING ) { $("#go_checking").show(); } 
           else if( state == STATE_CHECKING ) { $("#go_checked, #go_unchecked").show(); }
           else if( state == STATE_CHECKED ) { $("#go_settled").show(); }
       }, 
       rowStyler : function( i, row ) {
           var check = checkDeadline( row );
           if( check < 0 ) {
               // 已超期
               return "background:#F00";
           } else if( check == 0 ) {
               // 接近deadline
               return "background:#FC3";
           }
           return "";
       },
       onLoadSuccess : function( data ) {
           var over = 0;    // 过期数量
           var warning = 0;         // 警示数量
           if( data.rows != null && data.rows.length > 0 ) {
               $.each( data.rows, function(i, row){
                   var check = checkDeadline( row );
                   if( check < 0 ) {
                       over++;
                   } else if( check == 0 ) {
                       warning++;
                   }
               } );
           }
           if( over > 0 ) {
               top.$.messager.error( "有超过付款截止日还未付款的账单！", "账单过期" );
           } else if( warning > 0 ) {
               top.$.messager.info( "有账单接近付款截止日还未付款，请注意。", "注意" );
           }
       }
    });
    /***
     * 检查账单是否已经超期：1. 未超期，0. 将要超期，-1：已超期
     */
    function checkDeadline( row, delta ) {
        if( !row.paymentDate && row.deadline ) {
            delta = delta || 5;
            var deadline = ksa.utils.parseDate( row.deadline );
            var now = new Date();
            if( now >= deadline ) {
                return -1;
            } else if( now.setDate( now.getDate() + delta ) >= deadline ){
                return 0;
            }
        }
        return 1;
    }
    
    // 编辑事件
    $("#btn_edit").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行编辑/查看操作。");
            return;
        }
        // 打开编辑页面
        top.$.open({
            width:$winWidth,
            height:$winHeight,
            
            modal : false,
            collapsible : true,
            title: ( row.direction == 1? "结算单":"对账单") + "【" + row.code + "】",
            src : ksa.buildUrl( "/dialog/finance/account", ( row.state == 0 ? "create" : "edit" ), { id : row.id } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    $("#go_processing").click(function(){ changeState(1, "确定生成账单吗？<br/>账单生成后将不能修改【汇率】信息。"); });
    $("#go_checking").click(function(){ changeState(2, "确定将账单提交审核吗？"); });
    $("#go_checked").click(function(){ changeState(8, "确定账单通过审核吗？"); });
    $("#go_unchecked").click(function(){ changeState(1, "确定将账单打回进行修正吗？"); });
    $("#go_settled").click(function(){ changeState(32, "确定账单结算完毕吗？"); });
    
    function changeState( newState, message ) {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行相应操作。");
            return;
        }
        $.messager.confirm( message, function( ok ){
            if( ok ) {
                $.ajax({
                    url: ksa.buildUrl( "/dialog/finance/account", "state" ),
                    data: { id : row.id, state : newState },
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
    
    var STATE_NONE = "新建";
    var STATE_PROCESSING = "开票中" ;
    var STATE_CHECKING = "审核中";
    var STATE_CHECKED = "结算中";
    var STATE_SETTLED = "结算完毕";
    
    // 解析托单的状态 返回可读的状态值
    function parseState( state ) {
        if( state & 0x20 ) {
            return STATE_SETTLED;
        } else if( state & 0x8 ) {
            return STATE_CHECKED;
        } else if( state & 0x2 ) {
            return STATE_CHECKING;
        } else if( state & 0x1 ) {
            return STATE_PROCESSING;
        } else {
            return STATE_NONE;
        }
    };
    
});