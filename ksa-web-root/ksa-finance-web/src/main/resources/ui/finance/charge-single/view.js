$(function(){
    // 基本信息只读
    $("div.form-inline input").attr("readonly", "readonly");
    
    var DIRTY_MARK = " <b>*</b>";
    var $rates = initRates( RATES );
    var $grid1 = initGrid( $( "table#charge_datagrid" ) ).datagrid('loadData', CHARGES);
    
    var chargeDateChanged = false;
    if( !READONLY ) {
        $("#chargeDate").datebox({
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
        var charges = $grid1.datagrid("getRows");
        // 计算总收支
        $.each( charges, function(i,c){
            var currencyId = c.currency.id;
            if( profit[currencyId] == null ) {
                profit[currencyId] = { currency:c.currency.name, rate : $rates[currencyId].rate, charge:0 };
            }
            profit[currencyId].charge += parseFloat( c.amount );
        });
        
        var total = { currency:'人民币汇总', rate:'', charge:0 };
        var profitArray = [];
        for( var k in profit ) {
            profitArray.push( profit[k] );
            total.charge += profit[k].charge * parseFloat( profit[k].rate );
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
                 {field:'currency',title:'币种',width:80, align:'center'},
                 {field:'rate',title:'汇率',width:80, align:'right',
                     formatter: function(v){ return  (v == "") ? v : parseFloat( v ).toFixed( 4 );} },
                 {field:'charge',title:(DIRECTION==1?"收入":"支出") + (NATURE==1?"":"（境外）"), width:100, align:'right', 
                     styler:function(v,data){ return DIRECTION == 1 ? 'color:#BD362F;font-weight:bold;' : 'color:#51A351;font-weight:bold;' ; },
                     formatter: function(v){ return parseFloat( v ).toFixed( 2 );} }
            ] ],
            rowStyler:function(i,data){
                if (data.currency == '人民币汇总' ){
                    return 'background-color:#FAA732;color:#fff;font-weight:bold;';
                }
            }
        });
    }
    
    /* 初始化费用表格 */
    function initGrid( target ) {
        return target.datagrid({
            loadEmptyMsg:'<span class="label label-warning">注意</span> 暂时没有录入任何费用信息',
            fit:true,
            remoteSort:false,
            fitColumns:false,
            pagination:false,
            columns:[ [
                { field:'target', title:'结算对象', width:150,   sortable:true, 
                    formatter: function(v, data){ return data.target.name + "<input type='hidden' name='id' value='" + ( data.id != null ? data.id : "" ) + "' /><input type='hidden' name='target.id' value='" + data.target.id + "' />" ; } },
                { field:'type', title:'费用项目', width:100,   sortable:true, 
                        formatter: function(v, data){ return v + "<input type='hidden' name='type' value='" + v + "' />" ; } },
                { field:'direction', title:'收/支', width:60,   sortable:true, align:"center", 
                    styler:function(v,data){ if ( data.direction == 1 ){ return 'color:#BD362F;'; } return 'color:#51A351;'; },
                    formatter: function(v, data){ return parseChargeDirection( data.direction ) + "<input type='hidden' name='direction' value='" + v + "' />";  } },
                { field:'nature', title:'国内/境外', width:60,   sortable:true, align:"center", 
                    formatter: function(v, data){ return parseChargeNature( data.nature ) + "<input type='hidden' name='nature' value='" + v + "' />";  } },
                { field:'currency', title:'币种', width:60,   sortable:true, align:"center", 
                            formatter: function(v, data){ return data.currency.name + "<input type='hidden' name='currency.id' value='" + data.currency.id + "' />"; } },
                { field:'price', title:'单价', width:60,   sortable:true, align:"right", 
                    styler:function(v,data){ if ( data.direction == 1 ){ return 'color:#BD362F;'; } return 'color:#51A351;'; },
                    formatter: function(v, data){ if( v ) { return v + "<input type='hidden' name='price' value='" + v + "' />"; } else { return ""; } } },
                { field:'quantity', title:'数量', width:50, sortable:true, align:"right", 
                    formatter: function(v, data){ if( v ) { return v + "<input type='hidden' name='quantity' value='" + v + "' />"; } else { return ""; } } },
                { field:'amount', title:'金额', width:80,   sortable:true, align:"right", 
                           styler:function(v,data){ if ( data.direction == 1 ){ return 'color:#BD362F;font-weight:bold;'; } return 'color:#51A351;font-weight:bold;'; },
                           formatter: function(v, data){ return parseFloat( v ).toFixed( 2 ) + "<input type='hidden' name='amount' value='" + v + "' />" ; } },
                { field:'creator', title:'操作员', width:60,   sortable:true, align:"center",
                    formatter: function(v, data){ return data.creator.name + "<input type='hidden' name='creator.id' value='" + data.creator.id + "' />" ; } },
                { field:'createdDate', title:'创建日期', width:80, sortable:true, align:"center",
                    formatter : function(v, data) {  data.createdDate = ksa.utils.dateFormatter( data.createdDate ); return data.createdDate + "<input type='hidden' name='createdDate' value='" + data.createdDate + "' />" ; } },
                { field:'note', title:'备注', width:150,   sortable:true,
                        formatter: function(v, data){ return v + "<input type='hidden' name='note' value='" + v + "' />" ; } }
           ] ],
           onDblClickRow : function(){ edit( target ); },
           toolbar : READONLY ? null : [{
               text:'添加 ...',
               id: 'add_charge',
               cls:'btn-primary',
               iconCls:'icon-plus icon-white',
               handler:function(){ add( target ); }
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
    function add( target ) {
        editChargeData( { direction : DIRECTION, nature: NATURE }, function( data ) {
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
        editChargeData( row, function( data){
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
        if( STATE > 0 && !isDirty( $grid1 ) && !chargeDateChanged ) { 
            // 不是初始设置 仅判断两个表格
            return false; 
         }
        
        if( STATE == 0 && $grid1.datagrid("getRows").length == 0 && !chargeDateChanged ) {
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
            amount : 0,
            createdDate : ksa.utils.dateFormatter( new Date() ),
            customerId : data.target ? data.target.id : $("#customer").combobox("getValue"),
            currencyId : data.currency ? data.currency.id : "",
            creatorId : data.creator ? data.creator.id : CURRENT_USER.id,
            creatorName : data.creator ? data.creator.name : CURRENT_USER.name
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
            direction : DIRECTION,
            nature : NATURE,
        };
    }
    
    // 获取费用编辑窗口
    function getChargeWindow() {
        if( $window == null ) {
            // 初始化编辑页面的选择框
            $("#customer").combobox({ url : ksa.buildUrl( "/data/combo", "bd-partner-without-lock" ) });
            $("#charge_type").combobox({ url : ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '10-charge' } ) });
            $("#currency").combobox({ url : ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '00-currency' } ) });    
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
                    $("#add_charge").trigger("click");
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
        
        // 格式化 form 数据
        var table = $grid1[0];
        var option = $(table).datagrid("options");
        var rows = $(table).datagrid("getRows");
        for( var j = 0; j < rows.length; j++ ) {
            var tr = option.finder.getTr( table, j, "body", 2 );
            $("input[type='hidden']", tr).each(function(){
                $(this).attr("name", "charges["+j+"]." +$(this).attr("name") );
            });
        }
    });
    
    // 引用模板
    $("#copy_template").click(function(){
        ksa.finance.selectChargeTemplates(function(bns){
            if( bns && bns.length > 0 ) {
                $("<input type='hidden' name='template' value='"+bns[0].value+"' />").appendTo($("#dialog_container"));
                var action = $("#dialog_container").attr("action");
                $("#dialog_container").attr("action", action.replace('save', 'view'));
                $("#dialog_container")[0].submit();
            }
        }, { direction: DIRECTION, nature: NATURE } );
        return false;
    });
    
    $("#go_checking").click(function(){ changeState(2, "确定将费用信息提交审核吗？"); return false; });
    $("#go_checked").click(function(){ changeState(8, "确定费用信息通过审核吗？"); return false; });
    $("#go_entering").click(function(){ changeState(1, "确定将费用信息打回进行修正吗？"); return false; });
    
    function changeState( newState, message ) {
        if( isDirty( $grid1 ) ) { parent.$.messager.warning("请先保存对费用信息所做的修改。"); return false; }
        parent.$.messager.confirm( message, function( ok ){
            if( ok ) {
                $.ajax({
                    url: ksa.buildUrl( "/dialog/finance/charge/single", "state" ),
                    data: { id : $("#note_id").val(), state : newState, nature: NATURE, direction: DIRECTION },
                    success: function( result ) {
                        try {
                            if (result.status == "success") { 
                                $("#dialog_container").attr("action", ksa.buildUrl( "/dialog/finance/charge/single", "view" ) );
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
        var form = $( "<form action='"+ksa.buildUrl( "/ui/finance/recordbill", "download")+"' method='POST' style='display:none;top:-100px;left:-100px;height:0;width:0'></form>" ).appendTo( $("body") );
        form.form("submit", {  
            url: ksa.buildUrl( "/ui/finance/recordbill", "download"),  
            onSubmit: function() {
                $("<input type='hidden' name='id' />").val( $("#note_id").val() ).appendTo( form );
            }
        }); 
        return false;
    });
});