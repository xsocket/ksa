var STATE_PROCESSING = "已开单";
var STATE_UNACCOUNT = "未开单";
var STATE_SETTLED1 = "已收款";
var STATE_SETTLED2 = "已支付";
// 解析托单的状态 返回可读的状态值
function parseAccountState( state, direction ) {
	if( state == -1 ) {
        return STATE_UNACCOUNT;
    } else if( (state & 0x20) > 0 ) {
        return direction == 1 ? STATE_SETTLED1 : STATE_SETTLED2;
    } else {
        return STATE_PROCESSING;
    }
};

$(function(){
    // 基本信息只读
    $("div.form-inline input").attr("readonly", "readonly");
    
    // 查看是否开出结算单
    var accountId = false;
    var accountState = 0;
    //var charges = $.merge( $.merge( [], INCOMES ), EXPENSES );
    var charges = $.merge( [], INCOMES );	// 结算单状态不考虑支付费用
    for( var i = 0; i < charges.length; i++ ) {
        var c = charges[i];
        if( c.account.id ) {
            accountId = c.account.id;
            accountState = c.account.state;
            break;
        }
    }
    
    var DIRTY_MARK = " <b>*</b>";
    var $rates = initRates( RATES );
    var $grid1 = initGrid( $( "table#income_datagrid" ), 1 ).datagrid('loadData', INCOMES);
    var $grid2 = initGrid( $( "table#expense_datagrid" ), -1 ).datagrid('loadData', EXPENSES);
    
    var chargeDateChanged = false;
    if( !READONLY || CHECK_PERMISSION ) {
        $("#chargeDate").datebox({
            monthMode:true,
            // 只显示月份
            formatter : function( date ) {
                var y = date.getFullYear();
                var m = date.getMonth()+1;
                return y+'-'+(m<10?('0'+m):m);
            }, onSelect : function() {
                chargeDateChanged = true;
            }
        });
    }

    // 利润汇总
    var $gridP = initProfit( $("table#profit_datagrid") );
    refreshProfit( $gridP );
    
    var $chargeForm = $("#charge_form");
    var $window = null;

    // 解析 收入/支出的名称 或者 代码
    function parseChargeDirection( v ) {
        if( v == 1 ) { return "收入"; } 
        else if( v == -1 ) { return "支出"; }
        else if( v == "收入" ) { return 1; }
        else if( v == "支出" ) { return -1; } 
        else { throw new Error( "收支名称或者代码设置错误。" ); }
    };
    
    // 解析 国内/境外的名称 或者 代码
    function parseChargeNature( v ) {
        if( v == 1 ) { return "国内"; } 
        else if( v == -1 ) { return "境外"; }
        else if( v == "国内" ) { return 1; }
        else if( v == "境外" ) { return -1; } 
        else { throw new Error( "国内/境外费用名称或者代码设置错误。" ); }
    };
    
    function initRates( rates ) {
        var result = {};
        $.each( rates, function(i, rate ) {
            result[ rate.currency.id ] = { "name" : rate.currency.name, "rate" : rate.rate };
        });
        return result;
    }
    
    /* 刷新利润表格 */
    function refreshProfit( target ) {
        target = target || $gridP;
        var profit = {};    // 按币种划分
        var charges = $.merge( $.merge( [], $grid1.datagrid("getRows") ),  $grid2.datagrid("getRows") );
        // 计算总收支
        $.each( charges, function(i,c){
            var currencyId = c.currency.id;
            if( profit[currencyId] == null ) {
                profit[currencyId] = { currency:c.currency.name, rate : $rates[currencyId].rate, income:0, expense:0 };
            }
            if( c.direction == 1 ) {
                profit[currencyId].income += parseFloat( c.amount );
            } else {
                profit[currencyId].expense += parseFloat( c.amount );
            }
        });
        
        var total = { currency:'汇总', rate:'', income:0, expense:0};
        var profitArray = [];
        for( var k in profit ) {
            profitArray.push( profit[k] );
            total.income += profit[k].income * parseFloat( profit[k].rate );
            total.expense += profit[k].expense * parseFloat( profit[k].rate );
        }
        profitArray.push( total );
        
        target.datagrid('loadData', profitArray);
    }
    
    /* 初始胡利润表格 */
    function initProfit( target ) {
        return target.datagrid({
            height: $(target).parent().height(),
            rownumbers:false,
            pagination:false, 
            columns : [ [
                 {field:'currency',title:'币种',width:50, align:'center'},
                 {field:'rate',title:'汇率',width:70, align:'right',
                     formatter: function(v){ return  (v == "") ? v : parseFloat( v ).toFixed( 4 );} },
                 {field:'income',title:'收入',width:70, align:'right', 
                     styler:function(v,data){ return 'color:#BD362F;font-weight:bold;'; },
                     formatter: function(v){ return parseFloat( v ).toFixed( 2 );} },
                 {field:'expense',title:'支出',width:70, align:'right', 
                    styler:function(v,data){ return 'color:#51A351;font-weight:bold;'; },
                    formatter: function(v){ return parseFloat( v ).toFixed( 2 );} },
                 {field:'profit',title:'利润',width:70, align:'right', 
                     formatter: function(v, data){ return parseFloat( parseFloat( data.income ) - parseFloat( data.expense ) ).toFixed( 2 ); },
                     styler:function(v,data){ if ( data.income - data.expense >= 0 ){ return 'color:#BD362F;font-weight:bold;'; } return 'color:#51A351;font-weight:bold;'; } }
            ] ],
            rowStyler:function(i,data){
                if (data.currency == '汇总' ){
                    return 'background-color:#FAA732;color:#fff;font-weight:bold;';
                }
            }
        });
    }
    
    
    /* 初始化费用表格 */
    function initGrid( target, direction ) {
        return target.datagrid({
            loadEmptyMsg:'<span class="label label-warning">注意</span> 暂时没有录入任何费用信息',
            fit:true,
            remoteSort:false,
            fitColumns:false,
            pagination:false,
            columns:[ [
                { field:'target', title:'结算对象', width:150,   sortable:true, 
                    formatter: function(v, data){ return data.target.name + "<input type='hidden' name='id' value='" + ( data.id != null ? data.id : "" ) + "' /><input type='hidden' name='target.id' value='" + data.target.id + "' />" ; },
                    sorter:function(t1,t2){
                        var n1 = (t1.name?t1.name:""),  n2 = (t2.name?t2.name:"");
                        return (n1==n2?0:(n1>n2?1:-1));
                    }
                },
                { field:'accountState', title:'状态', width:45, hidden:false, align:"center",
	                formatter: function(v, data){ return parseAccountState( v, data.direction ); }, 
	                styler : function(v,data) {
	                    var state = parseAccountState( v, data.direction );
	                    var css = "font-weight:bold;";
	                    if( state == window.STATE_SETTLED1 || state == window.STATE_SETTLED2 ) { return css + "color:#51A351"; } 
	                    else if( state == window.STATE_PROCESSING ) { return css + "color:#04C"; }
	                    else if( state == window.STATE_UNACCOUNT ) { return css + "color:#FAA732"; }
	                } 
                },
                { field:'type', title:'费用项目', width:50,   sortable:true, 
                        formatter: function(v, data){ return v + "<input type='hidden' name='type' value='" + v + "' />" ; } },
                { field:'direction', title:'收/支', width:40,   sortable:true, hidden:true,  align:"center",
                    formatter: function(v, data){ return parseChargeDirection( data.direction ) + "<input type='hidden' name='direction' value='" + v + "' />";  } },
                { field:'nature', title:'内/外', width:40,   sortable:true, align:"center", align:"center",
                    formatter: function(v, data){ return parseChargeNature( data.nature ) + "<input type='hidden' name='nature' value='" + v + "' />";  } },
                { field:'currency', title:'币种', width:40,   sortable:true, align:"center", 
                    formatter: function(v, data){ return data.currency.name + "<input type='hidden' name='currency.id' value='" + data.currency.id + "' />"; },
                    sorter:function(t1,t2){
                        var n1 = (t1.name?t1.name:""),  n2 = (t2.name?t2.name:"");
                        return (n1==n2?0:(n1>n2?1:-1));
                    }
                },
                { field:'price', title:'单价', width:35,   sortable:true, align:"right", hidden:true, 
                    styler:function(v,data){ if ( data.direction == 1 ){ return 'color:#BD362F;'; } return 'color:#51A351;'; },
                    formatter: function(v, data){ if( v ) { return v + "<input type='hidden' name='price' value='" + v + "' />"; } else { return ""; } } },
                { field:'quantity', title:'数量', width:35, sortable:true, align:"right", hidden:true, 
                    formatter: function(v, data){ if( v ) { return v + "<input type='hidden' name='quantity' value='" + v + "' />"; } else { return ""; } } },
                { field:'amount', title:'金额', width:55,   sortable:true, align:"right", 
                           styler:function(v,data){ if ( data.direction == 1 ){ return 'color:#BD362F;font-weight:bold;'; } return 'color:#51A351;font-weight:bold;'; },
                           formatter: function(v, data){ return parseFloat( v ).toFixed( 2 ) + "<input type='hidden' name='amount' value='" + v + "' />" ; } },
                { field:'creator', title:'操作员', width:40,   sortable:true, align:"center", 
                    formatter: function(v, data){ return data.creator.name + "<input type='hidden' name='creator.id' value='" + data.creator.id + "' />" ; } },
                { field:'createdDate', title:'创建日期', width:80, sortable:true, align:"center", hidden:true,
                    formatter : function(v, data) {  data.createdDate = ksa.utils.dateFormatter( data.createdDate ); return data.createdDate + "<input type='hidden' name='createdDate' value='" + data.createdDate + "' />" ; } },
                { field:'note', title:'备注', width:30,   sortable:true, hidden:false, align:"center", 
                        formatter: function(v, data){ 
                        	var hidden = "<input type='hidden' name='note' value='" + v + "' />";
                        	return (v=="") ? hidden : "<i class='icon-list-alt' title='" + v + "'></i>" + hidden ; } }
           ] ],
           onDblClickRow : function(){ if(!READONLY) edit( target ); },
           toolbar : ( READONLY || (accountId != false) ) ? null : [{
               text:'添加 ...',
               id: 'add_charge' + direction,
               cls:'btn-primary',
               iconCls:'icon-plus icon-white',
               handler:function(){ add( target, direction ); }
           }, '-', {
               text:'编辑 ...',
               cls:'btn-warning',
               iconCls:'icon-edit icon-white',
               handler:function(){ edit( target ); }
           },'-', {
               text:'删除',
               cls:'btn-danger',
               iconCls:'icon-trash icon-white',
               handler:function(){ remove( target ); }
           }, {
               text:'撤销修改',
               cls:'btn-info',
               float:'right',
               iconCls:'icon-share-alt icon-white',
               handler:function(){ undo( target ); }
           }]
        });
    };
    
    /* 添加费用 */
    function add( target, direction ) {
        editChargeData( { direction : direction }, function( data) {
            target.datagrid( "appendRow", data);
            markDirty( target );
        } );
    };
    /* 编辑费用 */
    function edit( target ) {
        var row = target.datagrid( "getSelected" );
        if( ! row ) {
            parent.$.messager.warning("请选择一条数据后，再进行编辑操作。");
            return;
        }
        if(row.accountState != -1) {
        	parent.$.messager.warning("该费用当前 '" + parseAccountState( row.accountState, row.direction ) + "' ，不能修改。");
            return;
        }
        editChargeData( row, function(data){
            data.creator = CURRENT_USER;
            target.datagrid( "updateRow", {
                index : target.datagrid("getRowIndex",row),
                row : data
            } );
            markDirty( target );
        } );
    };
    /* 删除费用 */
    function remove( target ) {
        var row = target.datagrid( "getSelected" );
        if( ! row ) {
            parent.$.messager.warning("请选择一条数据后，再进行删除操作。");
            return;
        }
        if(row.accountState != -1) {
        	parent.$.messager.warning("该费用当前 '" + parseAccountState( row.accountState, row.direction ) + "' ，不能删除。");
            return;
        }
        parent.$.messager.confirm( "确定删除费用 '" + row.type + "' 吗？", function( ok ){
            if( ok ) { target.datagrid( "deleteRow", target.datagrid("getRowIndex",row) ); markDirty( target ); }
        } );
    };
    /* 撤销费用修改 */
    function undo( target ) {
        if( ! isDirty( target ) ) {
            parent.$.messager.warning("数据没有发生任何修改。");
            return;
        }
        parent.$.messager.confirm( "确定撤销对费用数据的所有修改吗？", function( ok ){
            if( ok ) { target.datagrid("rejectChanges"); markClean( target ); }
        } );
    };
    
    /* 标记数据已做修改 */
    function markDirty( target ) {
        var title = target.datagrid("options").title;
        target.datagrid("getPanel").panel( "setTitle", title + DIRTY_MARK );
        refreshProfit();
    }
    
    /* 标记数据未作修改 */
    function markClean( target ) {
        var title = target.datagrid("options").title;
        target.datagrid("getPanel").panel( "setTitle", title );
        refreshProfit();
    }
    /* 查询数据是否已做修改 */
    function isDirty( target ) {
        return target.datagrid("getPanel").panel( "options" ).title.indexOf( DIRTY_MARK ) >= 0;
    }
    
    /* 验证表单输入正确 */
    function checkFormValidate() {
        // 首先必填项输入
        if( $chargeForm.form( "validate" ) ) {
            // 1. 判断合作伙伴信息是否正确
            var tId = $("#customer").combobox("getValue");
            var tName = $("#customer").combobox("getText");
            if( tName == tId ) {  // 错误
                parent.$.messager.warning("名称为 '"+tName+"' 的结算对象不存在，请首先在基础数据管理中添加相应对象的信息。");
                $("#customer").combobox( "setValue", "" );
                return false;
            }
            // 2. 判断货币信息是否正确
            var cId = $("#currency").combobox("getValue");
            var cName = $("#currency").combobox("getText");
            if( cName == cId ) {  // 错误
                parent.$.messager.warning("名称为 '"+cName+"' 的币种不存在，请首先在基础数据管理中添加相应币种的信息。");
                $("#currency").combobox( "setValue", "" );
                return false;
            }
            return true;
        }
        return false;
    }
    
    function isGlobalDirty() {        
        if( STATE > 0 && !isDirty( $grid1 ) && !isDirty( $grid2 ) && !chargeDateChanged ) { 
            // 不是初始设置 仅判断两个表格
            return false; 
         }
        
        if( STATE == 0 && $grid1.datagrid("getRows").length == 0 && $grid2.datagrid("getRows").length == 0 && !chargeDateChanged ) {
            // 是初始设置 判断表格是否有内容，解决引用模板发生的问题
            return false; 
        }
        
        if( STATE < 0 ) {
            return false;   // 被删除的托单不允许编辑
        }
        
        return true;
    }
    
    /* 设置编辑窗口数据 */
    function editChargeData( data, callback ) {
        var $win = getChargeWindow();
        $chargeForm.form( "load", $.extend( {
            note : "",
            type : "",
            price : "",
            quantity:"",
            amount : "",
            createdDate : ksa.utils.dateFormatter( new Date() ),
            customerId : data.target ? data.target.id : $("#customer").combobox("getValue"),
            currencyId : data.currency ? data.currency.id : $("#currency").combobox("getValue"),
            creatorId : data.creator ? data.creator.id : CURRENT_USER.id,
            creatorName : data.creator ? data.creator.name : CURRENT_USER.name,
            nature : $("#charge_nature").combobox("getValue"),
            inout : parseChargeDirection( data.direction )
        }, data) );
        // 弹出修改窗口
        $win.window("open");
        $chargeForm.form( "validate" ); // 修正警告标签初始显示在顶部的bug
        // 确认修改
        $("#form_ok").unbind("click").bind("click", function() {
            if( checkFormValidate() ) {
                callback( loadChargeData() );
                $window.window("close"); 
            }
        });
    }
    /* 获取编辑窗口中的数据 */
    function loadChargeData() {
        return {
            creator : { id : $("#creator_id").val(), name : $("#creator_name").val() },
            createdDate : $("#created_date").val(),
            note : $("#note").val(),
            price : $("#price").numberbox( "getValue" ),        
            quantity : $("#quantity").numberbox( "getValue" ),        
            amount : $("#amount").numberbox( "getValue" ),            
            currency : { id: $("#currency").combobox("getValue"), name: $("#currency").combobox("getText") },
            target : { id: $("#customer").combobox("getValue"), name: $("#customer").combobox("getText") },
            type : $("#charge_type").combobox("getText"),
            direction : parseChargeDirection( $("#inout").val() ),
            nature : $("#charge_nature").combobox("getValue")
        };
    }
    
    // 获取费用编辑窗口
    function getChargeWindow() {
        if( $window == null ) {
            // 初始化编辑页面的选择框
            $("#customer").combobox({ url : ksa.buildUrl( "/data/combo", "bd-partner-without-lock" ) });
            $("#charge_type").combobox({ url : ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '10-charge' } ) });
            $("#currency").combobox({ url : ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '00-currency' } ) });    
            $("#charge_nature").combobox();
            // 初始化 window 窗口
            $("#charge_window").show();
            $window = $("#charge_window").window({
                title:'费用编辑',
                width:465,
                modal:true,
                minimizable:false,
                maximizable:false,
                collapsible:false,
                resizable:false,
                closable:false,
                closed:true
            });
            $("#form_close").click(function(){ $window.window("close"); });
            $("#form_continue").click(function(){
                if( checkFormValidate() ) {
                    $("#form_ok").trigger("click");
                    $("#add_charge" + parseChargeDirection($("#inout").val()) ).trigger("click");
                }
            });
        }
        return $window;
    }
    
    
    
    // 重写关闭操作，提示保存。
    $("#dialog_close").unbind("click").bind("click", function(){
        if( isGlobalDirty() ) {
            parent.$.messager.confirm( "费用数据还未保存，是否放弃修改？", function( ok ) {
                if( ok ) { parent.$.close(); return false; }
            } );
            return false;
        } else { parent.$.close(); return false; }
    });
    
    /* 保存 */
    $("#dialog_save").click(function(){
        if( ! isGlobalDirty() ) {
            parent.$.messager.warning("数据未发生变更，无需保存。"); 
            return false; 
        }
        
        var tables = [ $grid1[0], $grid2[0] ];
        var names = [ "incomes", "expenses" ];
        for( var i = 0; i < 2; i++ ) {
            var table = tables[i];
            var option = $(table).datagrid("options");
            var rows = $(table).datagrid("getRows");
            for( var j = 0; j < rows.length; j++ ) {
                var tr = option.finder.getTr( table, j, "body", 2 );
                $("input[type='hidden']", tr).each(function(){
                    $(this).attr("name", names[i] + "["+j+"]." +$(this).attr("name") );
                });
            }
        }
    });
    
    // 引用模板
    $("#copy_template").click(function(){
        ksa.finance.selectBookingNotes(function(bns){
            if( bns && bns.length > 0 ) {
                $("<input type='hidden' name='template' value='"+bns[0].value+"' />").appendTo($("#dialog_container"));
                var action = $("#dialog_container").attr("action");
                $("#dialog_container").attr("action", action.replace('save', 'view'));
                $("#dialog_container")[0].submit();
            }
        });
        return false;
    });
    
    $("#go_checking").click(function(){ changeState(2, "确定将费用信息提交审核吗？"); return false; });
    $("#go_checked").click(function(){ changeState(8, "确定费用信息通过审核吗？"); return false; });
    $("#go_entering").click(function(){ changeState(1, "确定将费用信息打回进行修正吗？"); return false; });
    $("#return_entering").click(function(){ changeState(1, "确定将费用信息打回重新编辑吗？"); return false; });
    
    function changeState( newState, message ) {
        if( isDirty( $grid1 ) || isDirty( $grid2 ) ) { parent.$.messager.warning("请先保存对费用信息所做的修改。"); return false; }
        parent.$.messager.confirm( message, function( ok ){
            if( ok ) {
                $.ajax({
                    url: ksa.buildUrl( "/dialog/finance/charge", "state" ),
                    data: { id : $("#note_id").val(), state : newState, nature: NATURE },
                    success: function( result ) {
                        try {
                            if (result.status == "success") { 
                                $("#dialog_container").attr("action", ksa.buildUrl( "/dialog/finance/charge", "view" ) );
                                $("input[type='hidden']", $("div.charge-container") ).remove();
                                $("#dialog_container")[0].submit();
                            } 
                            else { parent.$.messager.error( result.message ); }
                        } catch (e) { }
                    }
                }); 
            }
        } );
        return false;
    }
    
    $("#dialog_download").click(function(){        
        var downloadUrl = ksa.buildUrl( "/ui/finance/" + (NATURE == -1 ? "debitnote" : "recordbill"), "download");
        var form = $( "<form action='"+ downloadUrl +"' method='POST' style='display:none;top:-100px;left:-100px;height:0;width:0'></form>" ).appendTo( $("body") );
        form.form("submit", {  
            url: downloadUrl,  
            onSubmit: function() {
                $("<input type='hidden' name='id' />").val( $("#note_id").val() ).appendTo( form );
            }
        }); 
        return false;
    });
    
    // 增加自动计算功能
    $("#amount").change(function(){
        var a = $(this).val();
        if( $.isNumeric(a) ) {            
            var q = $("#quantity").val();
            if( $.isNumeric(q) ) { $("#price").numberbox( "setValue", a / q ); }
            else {
                var p = $("#price").val();
                if( $.isNumeric(p) ) { $("#quantity").numberbox( "setValue", a / p ); }
            }
        }
    });
    $("#quantity").change(function(){
        var q = $(this).val();
        if( $.isNumeric(q) ) {            
            var a = $("#amount").val();
            if( $.isNumeric(a) ) { $("#price").numberbox( "setValue", a / q ); }
            else {
                var p = $("#price").val();
                if( $.isNumeric(p) ) { $("#amount").numberbox( "setValue", q * p ); }
            }
        }
    });
    $("#price").change(function(){
        var p = $(this).val();
        if( $.isNumeric(p) ) {            
            var a = $("#amount").val();
            if( $.isNumeric(a) ) { $("#quantity").numberbox( "setValue", a / p ); }
            else {
                var q = $("#quantity").val();
                if( $.isNumeric(q) ) { $("#amount").numberbox( "setValue", q * p ); }
            }
        }
    });
    $("#btn_note").click(function(){
        var bnId = $("#note_id").val();
        if( !bnId ) {
            return false;
        }
        // 打开编辑页面
        top.$.open({
            width:1000,
            height:600,
            modal : false,
            collapsible : true,
            
            title:"托单信息：" + $("#note_code").val(),
            src : ksa.buildUrl( "/dialog/logistics", "edit-" + $("#note_type").val().toLowerCase(), { id : bnId, code : $("#node_code").val() } )
        });
        return false;
    });
    
    // 结算单状态
    var STATE_NONE = "";
    var STATE_PROCESSING = " - 账单审核中";
    var STATE_CHECKING= " - 账单开票中";
    var STATE_CHECKED = " - 账单收款中";
    var STATE_SETTLED = " - 账单结算完毕";
    
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
    
    if( accountId ) {
        var accountStateName = "已开单" + parseState(accountState);        
            //已开结算单不能查看
        $("div.bottom-bar button.pull-left").hide();
        $("div.bottom-bar span.title b").text( accountStateName );
        
        $("#view_account").show();
        $("#view_account").click(function(){
         // 打开编辑页面
            top.$.open({
                width:1000,
                height:600,
                modal : false,
                collapsible : true,
                title: "查看结算单",
                src : ksa.buildUrl( "/dialog/finance/account", "edit", { id : accountId } )
            });
            return false;
        });
    }
});