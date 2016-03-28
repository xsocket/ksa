$(function(){
    var $winWidth = 1000;
    var $winHeight = 600;
    
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();
    
    $("#go_processing, #go_checking, #go_checked, #go_unchecked, #go_settled").hide();
    
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/finance/account", "query", { direction : ( NATURE == -1 ? undefined : DIRECTION ), nature : NATURE } ),
        queryParams : QUERY_PARAMS,
        fit : true,
        border:false,
        pageSize: 20,
        fitColumns: false,
        columns:[ GET_ACCOUNT_TABLE_COLUMN() ],
        onDblClickRow:function() {
            if( $("#btn_edit").size() > 0 ) {
                $("#btn_edit").click();
            } else {
                $("#btn_doinvoice").click();
            }
       },
       onClickRow:function(){
           $("#btn_delete,#go_processing, #go_checking, #return_processing, #go_checked, #go_settled,#return_checked, #return_checking").hide();
           var row = $grid.datagrid( "getSelected" );
           var state = parseState( row.state );
           if( state == STATE_NONE ) { $("#btn_delete, #go_processing").show(); } 
           else if( state == STATE_PROCESSING ) { $("#go_checking, #return_processing").show(); } 
           else if( state == STATE_CHECKING ) { $("#go_checked, #return_checking").show(); }
           else if( state == STATE_CHECKED ) { $("#go_settled, #return_checked").show(); }
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
               $.messager.error( "有超过付款截止日还未付款的账单！", "账单过期" );
           } else if( warning > 0 ) {
               $.messager.info( "有账单接近付款截止日还未付款，请注意。", "注意" );
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
    
    // 新建事件
    $("#btn_add").click( function() {
        // 打开新建页面
        $.open({
            width:$winWidth,
            height:$winHeight,
            
            modal : false,
            collapsible : true,
            title: "新建" + ACCOUNT_NAME,
            src : ksa.buildUrl( "/dialog/finance/account", "create", {direction:DIRECTION, nature: NATURE} )
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
            
            modal : false,
            collapsible : true,
            title: ACCOUNT_NAME + "【" + row.code + "】",
            src : ksa.buildUrl( "/dialog/finance/account", "edit", { id : row.id } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    // 编辑事件 - 直接进入开票界面
    $("#btn_doinvoice").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一个账单后，再进行开票操作。");
            return;
        }
        // 打开编辑页面
        $.open({
            width:$winWidth,
            height:$winHeight,
            
            modal : false,
            collapsible : true,
            title: ACCOUNT_NAME + "【" + row.code + "】开票确认",
            src : ksa.buildUrl( "/dialog/finance/account", ( row.state == 0 ? "create" : "edit" ), { id : row.id, selected: 2 } )   // 默认打开第2个tab页，也就是开票页
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
        
        $.messager.confirm( "确定删除" + ACCOUNT_NAME +" '" + row.code + "' 吗？", function( ok ){
            if( ok ) { 
                $.ajax({
                    url: ksa.buildUrl( "/dialog/finance/account", "delete" ),
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

    $("#go_processing").click(function(){ changeState(1, "确定提交审核"+ACCOUNT_NAME+"吗？<br/>账单提交审核后将不能修改【汇率】信息。"); return false; });
    $("#go_checking").click(function(){ changeState(2, "确定"+ACCOUNT_NAME+"通过审核吗？"); return false; });
    $("#return_processing").click(function(){ changeState(0, "确定将"+ACCOUNT_NAME+"打回进行修正吗？"); return false; });
    $("#go_checked").click(function(){ changeState(8, "确定完成发票开具，进行"+(DIRECTION == 1 ? "收款" : "付款")+"确认吗？"); return false; });
    $("#return_checking").click(function(){ changeState(1, "确定对"+ACCOUNT_NAME+"重新进行审核吗？"); return false; });
    $("#return_checked").click(function(){ changeState(2, "确定重新开具发票吗？"); return false; });
    $("#go_settled").click(function(){ changeState(32, "确定"+ACCOUNT_NAME+"结算完毕吗？"); });
    
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
    

    var STATE_NONE = "待审核";
    var STATE_PROCESSING = "审核中";
    var STATE_CHECKING= "开票中";
    var STATE_CHECKED = ( DIRECTION == 1 ? "收款中" : "付款中" );
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
        conditions : ACCOUNT_QUERY_CONDITION
    });
    
    // 新增导出默认结算单的功能。
    $("#btn_download").click(function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行相应操作。");
            return;
        }
        
        var form = $( "<form action='"+ksa.buildUrl( "/dialog/finance/account", "account-download")+"' method='POST' style='display:none;top:-100px;left:-100px;height:0;width:0'></form>" ).appendTo( $("body") );
        form.form("submit", {  
            url: ksa.buildUrl( "/dialog/finance/account", "account-download"),  
            onSubmit: function() {
                // 导出默认列
                $("<input type='hidden' name='columns' value='serial_number' />").appendTo( form );
                $("<input type='hidden' name='columns' value='invoice_number' />").appendTo( form );
                $("<input type='hidden' name='columns' value='mawb' />").appendTo( form );
                $("<input type='hidden' name='columns' value='volumn' />").appendTo( form );
                $("<input type='hidden' name='columns' value='weight' />").appendTo( form );
                $("<input type='hidden' name='columns' value='quantity' />").appendTo( form );
                $("<input type='hidden' name='columns' value='cargo_container' />").appendTo( form );
                $("<input type='hidden' name='columns' value='route_name' />").appendTo( form );
                $("<input type='hidden' name='columns' value='departure_port' />").appendTo( form );
                $("<input type='hidden' name='columns' value='departure_date' />").appendTo( form );
                $("<input type='hidden' name='columns' value='destination_port' />").appendTo( form );
                $("<input type='hidden' name='columns' value='destination_date' />").appendTo( form );
                $("<input type='hidden' name='id' />").val( row.id ).appendTo( form );
            }
        }); 
        return false;
    });
});