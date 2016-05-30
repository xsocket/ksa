function validateCustomer() { return ( $("#target").combobox("getValue") != $("#target").combobox("getText") ); };
$(function(){
    // 初始化布局
    $("#tab_container")._outerHeight( $(window).height() - 75 );
    $("#account_container").layout();
    
    // 结算对象选择框
    $("#target").combobox({
        url : ksa.buildUrl( "/data/combo", "bd-partner-without-lock" ),
        onSelect : function( record ){
            $rateGrid.datagrid( "load", { id: $("#account_id").val(), "target.id": record.id } );
            // 自动更新编码
            //$("#code").val( "J" + ksa.utils.dateFormatter( new Date() ) + record.code + "1" );
            //现在采用服务器获取的方式
            $.ajax({
                url: ksa.buildUrl( "/dialog/finance/account", "compute-code", { "code" : record.code } ),
                success: function( result ) {
                    try {
                        if (result.status == "success") { 
                            $("#code").val( result.message );   // 展示从服务器获取的编码
                        } 
                        else { parent.$.messager.error( result.message ); }
                    } catch (e) { }
                }
            });
            
            var d = ksa.utils.parseDate( $("#createdDate").val() );
            var pp = record.pp;
            d.setDate( d.getDate() + pp );
            $("#deadline").datebox( "setValue", (d.getFullYear() + "-" + ( d.getMonth() + 1 ) + "-" + d.getDate()) );
        }
    });
    
    // 费用列表
    var $chargeGrid = $("#charge_datagrid").datagrid({
        loadEmptyMsg:'<span class="label label-warning">注意</span> 暂时没有加入任何费用信息',
        pagination: false,
        remoteSort:false,
        fit: true,
        fitColumns:false,
        columns:[ 
                  $.merge( [ { field:'c.id', title:'标识', width:50, hidden:true, toggleable : false,
                      formatter: function(v, data){ return data.id + "<input type='hidden' name='id' value='"+data.id+"'/>"; } } ], 
                  GET_CHARGE_TABLE_COLUMN( { 'a.code': false, 'bn.mawb': false, 'bn.customer_name' : false } ) ) ],
        onDblClickRow : function(){ viewCharge(); },
        toolbar : ( STATE > 0 ) ? null : [{
            text:'添加...',
            cls:'btn-primary',
            iconCls:'icon-plus icon-white',
            handler:function(){ add(); }
        }, '-', {
            text:'删除',
            cls:'btn-danger',
            iconCls:'icon-trash icon-white',
            handler:function(){ remove(); }
        }, {
            text:'撤销修改',
            cls:'btn-info',
            float:'right',
            iconCls:'icon-share-alt icon-white',
            handler:function(){ undo(); }
        }, '|', {
            text:'查看业务信息',
            cls:'btn-success',
            float:'right',
            handler:function(){ viewNote(); }
        }, {
            text:'查看费用明细',
            cls:'btn-success',
            float:'right',
            handler:function(){ viewCharge(); }
        }]
    }).datagrid('loadData', CHARGES || [] );
    
    function add( target ) {
        ksa.finance.selectCharges( function( records ) {
            var oldData = $chargeGrid.datagrid( "getRows" );
            var map = generateDataMap( oldData );
            var newData = [];
            var warning = false;
            $.each( records, function(i,v){
                if( validateCharge( v.data ) ) {
                    if( !map[v.value] ) { // 不添加重复值
                        newData.push( v.data );
                    }
                } else {
                    warning = true; // 有非法值
                }
            });
            if( warning ) {
                top.$.messager.warning("请为"+ACCOUNT_NAME+"添加合理的费用，"+(DIRECTION == 1 ? "支出" : "收入" )+"费用以及已经开单的费用将被忽略。");
            }
            $chargeGrid.datagrid('loadData', $.merge( newData, oldData ) );
        }, { direction: DIRECTION, /*nature: NATURE,*/ settle: "false" }, "选择"+ACCOUNT_NAME+"的费用明细");
                                                // 国内/境外暂时都显示！
        
        // 验证费用
        function validateCharge( charge ) {
            // 收支方向正确 并且 费用不属于别的结算单
            return charge.direction == DIRECTION && !charge.settle;
        };
        function generateDataMap( list ) {
            var map = {};
            $.each( list, function(i, d){ map[d.id] = d; });
            return map;
        };
    };
    
    function viewNote() {
        var row = $chargeGrid.datagrid( "getSelected" );
        if( ! row ) {
            parent.$.messager.warning("请选择一条数据后，再进行业务查看操作。");
            return;
        }
        var note = row.bookingNote;
        // 打开编辑页面
        top.$.open({
            width:1000,
            height:600,
            modal : false,
            collapsible : true,
            
            title:"托单信息：" + note.code,
            src : ksa.buildUrl( "/dialog/logistics", "edit-" + note.type.toLowerCase(), { id : note.id, code : note.code } )
        });
        return;
    };
    
    function viewCharge() {
        var row = $chargeGrid.datagrid( "getSelected" );
        if( ! row ) {
            parent.$.messager.warning("请选择一条数据后，再进行费用查看操作。");
            return;
        }
        var note = row.bookingNote;
        // 打开编辑页面
        top.$.open({
            width:1000,
            height:600,
            
            modal : false,
            collapsible : true,
            title:"费用信息：" + note.code,
            src : ksa.buildUrl( "/dialog/finance/charge", "view", { id : note.id, nature: 1 } )
        });
        return;
    };
    
    function remove() {
        var row = $chargeGrid.datagrid( "getSelected" );
        if( ! row ) {
            parent.$.messager.warning("请选择一条数据后，再进行删除操作。");
            return;
        }
        
        parent.$.messager.confirm( "确定删除费用 '" + row.type + "' 吗？", function( ok ){
            if( ok ) { $chargeGrid.datagrid( "deleteRow", $chargeGrid.datagrid("getRowIndex",row) ); /*markDirty( target );*/ }
        } );
    };
    /* 撤销费用修改 */
    function undo() {
        /*if( ! isDirty( target ) ) {
            parent.$.messager.warning("数据没有发生任何修改。");
            return;
        }*/
        parent.$.messager.confirm( "确定撤销对费用清单的所有修改吗？", function( ok ){
            if( ok ) { $chargeGrid.datagrid("rejectChanges");/* markClean( target ); */}
        } );
    };
    
    // 汇率列表
    var $lastIndex = -1;
    var $rateGrid = $('#currency_datagrid').datagrid({
        url : ksa.buildUrl( "/data/grid/currency", "account"),
        queryParams : { id: $("#account_id").val(), "target.id": $("#target").val() },
        fit : true,
        pagination : false,
        fitColumns : false,
        columns : [ [
            { field:'id',         title:'标识',      hidden:true,
                formatter: function(v, data){ return "<input type='hidden' name='id' value='"+(data.id ? data.id : "")+"' /><input type='hidden' name='currency.id' value='"+data.currency.id+"' />"; } },
            { field:'code',         title:'货币代码',      width:70,
                    formatter: function(v, data){ return data.currency.code; } },
            { field:'name',         title:'货币名称',      width:70, 
                    formatter: function(v, data){ return data.currency.name; } },
            { field:'rate',  title:'汇率', width:70, align:'right', 
                    formatter: function(v, data){ return v + "<input type='hidden' name='rate' value='"+v+"' />"; },
                    editor:{ type:'numberbox',options:{precision:3} }, 
                    styler:function(){return 'color:blue;';} }
       ] ],
       rowStyler:function(index,row,css){
           if ( !row.id ){
               return 'color:red;font-weight:bold;';
           }
       },
       onClickRow:  ( STATE > 1 ) ? function(){} : function( rowIndex ) {
           $rateGrid.datagrid('endEdit', $lastIndex);
           if ( $lastIndex != rowIndex ) {
               $rateGrid.datagrid('beginEdit', rowIndex);
               $lastIndex = rowIndex;
           } else {
               $lastIndex = -1;
           }
       }
    });
    
    // 保存
    $("#dialog_save").click(function(){
        $rateGrid.datagrid('endEdit', $lastIndex);
        var tables = [ $chargeGrid[0], $rateGrid[0] ];
        var names = [ "charges", "rates" ];
        for( var i = 0; i < names.length; i++ ) {
            var table = tables[i];
            var option = $(table).datagrid("options");
            var rows = $(table).datagrid("getRows");
            for( var j = 0; j < rows.length; j++ ) {
                var tr = option.finder.getTr( table, j, "body", 2 );
                $("input[type='hidden']", tr).each(function(){
                    $(this).attr("name", names[i] + "["+j+"]." +$(this).attr("name") );
                });
            }
        };
    });
    
    // 关闭
    $("#dialog_close").bind("click", function(){
        top.$.close(); return false;
    });
    
    // ------------- 状态变更操作
    $("#go_processing").click(function(){ changeState(1, "确定提交审核"+ACCOUNT_NAME+"吗？<br/>账单提交审核后将不能修改【汇率】信息。"); return false; });
    $("#go_checking").click(function(){ changeState(2, "确定"+ACCOUNT_NAME+"通过审核吗？"); return false; });
    $("#return_processing").click(function(){ changeState(0, "确定将"+ACCOUNT_NAME+"打回进行修正吗？"); return false; });
    $("#go_checked").click(function(){ changeState(8, "确定完成发票开具，进行"+(DIRECTION == 1 ? "收款" : "付款")+"确认吗？"); return false; });    
    $("#return_checking").click(function(){ changeState(1, "确定对"+ACCOUNT_NAME+"重新进行审核吗？"); return false; });
    $("#return_checked").click(function(){ changeState(2, "确定重新开具发票吗？"); return false; });
    $("#go_settled").click(function(){
        var paymentDate = $("#paymentDate").val();
        if( !paymentDate || paymentDate == "" ) {
            parent.$.messager.confirm( "结清日期还未输入，是否今天结清？", function( ok ){
                if( ok ) { 
                    var d = new Date();
                    paymentDate = (d.getFullYear() + "-" + ( d.getMonth() + 1 ) + "-" + d.getDate());
                    $("#paymentDate").datebox( "setValue", paymentDate );
                    changeState( 32, false, { "paymentDate":paymentDate } ); 
                }
            } );
        } else {
            changeState( 32, "确定"+ACCOUNT_NAME+"结算完毕吗？", { "paymentDate":paymentDate } );
        }
        return false; 
     });
    
    function changeState( newState, message, params ) {
        function doChangeState() {
            $.ajax({
                url: ksa.buildUrl( "/dialog/finance/account", "state" ),
                data: $.extend( { id : $("#account_id").val(), state : newState }, params ),
                success: function( result ) {
                    try {
                        if (result.status == "success") { 
                            $("form.easyui-form").attr("action", ksa.buildUrl( "/dialog/finance/account", "account" ) );
                            $("input[type='hidden']", $("div.grid-container") ).remove();
                            $("form.easyui-form")[0].submit();
                        } 
                        else { parent.$.messager.error( result.message ); }
                    } catch (e) { }
                }
            });
        }
        if( message ) {
            parent.$.messager.confirm( message, function( ok ){
                if( ok ) {
                    doChangeState();
                }
            } );
        } else {
            doChangeState();
        }
        return false;
    }
    
    // 新增导出默认结算单的功能。
    $("#btn_download").click(function() {
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
                $("<input type='hidden' name='id' />").val( $("#account_id").val() ).appendTo( form );
            }
        }); 
        return false;
    });
});