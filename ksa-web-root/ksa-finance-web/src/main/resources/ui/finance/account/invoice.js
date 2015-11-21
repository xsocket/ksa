$(function(){
    var $grid = $('#data_grid').datagrid({
        url: ksa.buildUrl( "/data/finance/invoice", "query",  { "ACCOUNT_ID" : ACCOUNT_ID } ),
        fit : true,
        pageSize: 20,
        fitColumns: false,
        columns:[ GET_INVOICE_TABLE_COLUMN( {"a.code":false} ) ],
        toolbar : ( (STATE & 0x8) > 0) ? null : [{
            text:'添加新发票 ...',
            cls:'btn-primary',
            iconCls:'icon-plus icon-white',
            handler:function(){ 
                top.$.open({
                    width:650,
                    height:400,
                    
                    title: "新建发票",
                    src : ksa.buildUrl( "/dialog/finance/invoice", "create", { direction: DIRECTION * -1, "account.id": ACCOUNT_ID, "target.id": TARGET_ID } )
                }, function(){
                    $grid.datagrid( "reload" );
                });
            }
        }, '-', {
            text:'选择已有发票 ...',
            cls:'btn-success',
            iconCls:'icon-search icon-white',
            handler:function(){ 
                ksa.finance.selectInvoices( function( results ) {           
                    $.each( results, function(i, v){
                        var invoice = v.data;
                        if( invoice.account.id == ACCOUNT_ID ) {
                            return;
                        } else if( invoice.direction == DIRECTION ) {
                            top.$.messager.warning(DIRECTOIN_NAME + "必须选择" + ( DIRECTION == 1 ? "开出" : "收到" ) + "的发票进行对账销账。");
                        } else if( invoice.account.id ) {
                            top.$.messager.warning("发票【"+invoice.code+"】已经被用于对账销账，无法重复使用。");
                        } else {
                            assignInvoice( invoice.id, ACCOUNT_ID, (i+1) == results.length );   // 最后一个提交的刷新列表
                        }
                    } );
                }, { direction : DIRECTION * -1, settle: "false"} );
            }
        }, {
            text:'销账还原',
            cls:'btn-danger',
            float:'right',
            iconCls:'icon-share-alt icon-white',
            handler:function(){
                var row = $grid.datagrid( "getSelected" );
                if( ! row ) {
                    top.$.messager.warning("请选择一条数据后，再进行还原操作。");
                    return;
                }
                // 还原
                assignInvoice( row.id, "", true );
            }
        }]
    });
    
    function assignInvoice( invoiceId, accountId, reload ) {
        $.ajax({
            url: ksa.buildUrl( "/dialog/finance/invoice", "assign" ),
            data: { "id" : invoiceId, "account.id" : accountId },
            success: function( result ) {
                try {
                    if (result.status == "success" && reload ) {
                        $grid.datagrid("reload");
                    } 
                    else { top.$.messager.error( result.message ); }
                } catch (e) { }
            }
        }); 
    };
    
 // ------------- 状态变更操作
    $("#go_processing").click(function(){ changeState(1, "确定提交审核"+ACCOUNT_NAME+"吗？<br/>账单提交审核后将不能修改【汇率】信息。"); return false; });
    $("#go_checking").click(function(){ changeState(2, "确定"+ACCOUNT_NAME+"通过审核吗？"); return false; });
    $("#return_processing").click(function(){ changeState(0, "确定将"+ACCOUNT_NAME+"打回进行修正吗？"); return false; });
    $("#go_checked").click(function(){ changeState(8, "确定完成发票开具，进行"+(DIRECTION == 1 ? "收款" : "付款")+"确认吗？"); return false; });    
    $("#return_checked").click(function(){ changeState(2, "确定重新开具发票吗？"); return false; });
    $("#go_settled").click(function(){ changeState(32, "确定"+ACCOUNT_NAME+"结算完毕吗？"); });
    function changeState( newState, message ) {
        parent.$.messager.confirm( message, function( ok ){
            if( ok ) {
                $.ajax({
                    url: ksa.buildUrl( "/dialog/finance/account", "state" ),
                    data: { id : ACCOUNT_ID, state : newState },
                    success: function( result ) {
                        try {
                            if (result.status == "success") { 
                                parent.window.location.reload();
                            } 
                            else { parent.$.messager.error( result.message ); }
                        } catch (e) { }
                    }
                }); 
            }
        } );
        return false;
    }
});