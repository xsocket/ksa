function validateSaler() { return ( $("#saler").combobox("getValue") != $("#saler").combobox("getText") ); };
function validateCustomer() { return ( $("#customer").combobox("getValue") != $("#customer").combobox("getText") ); };
$(function(){
    $("#subType").combobox({ data:[ { "id":"FCL", "name":"FCL" }, { "id":"LCL", "name":"LCL" } ], onSelect : function(){ markDirty();} });
    // 销售担当
    initCombobox( "#saler", ksa.buildUrl( "/data/combo", "security-user-all" ), { codeField : "id" } );
    /* ------------ 合作伙伴信息 ------------ */ 
    initCombobox( "#customer, #shipper, #consignee, #notify", ksa.buildUrl( "/data/combo", "bd-partner-without-lock" ) ); 
    // 报关行
    initCombobox( "#chb", ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-bgh' } ) );
    // 代理商
    initCombobox( "#agent", ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-dls' } ) );
    // 车队
    initCombobox( "#team", ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-chedui' } ) );
    // 船代
    initCombobox( "#shipping_agent", ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-cd' } ) );
    // 承运人
    initCombobox( "#carrier", ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-cyr' } ) );   
    // 数量单位
    initCombobox( "#units", ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '01-units' } ), { valueField : "name", onChange:function(){
        onCargoQuantityChanged();
        markDirty();
    } } );
    // 车辆类型
    initCombobox( "#vehicle_type", ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '08-vehicle' } ), { valueField : "name" } );
    /* ------------ 航线信息 ------------ */ 
    // 出发地、目的地
    initCombobox( "#destination, #departure, #stopover",  ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '30-state' } ), { valueField : "name" } );
    // 海运航线
    initCombobox( "#route_sea", ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '33-route-sea' } ), { valueField : "name" } );
    // 港口信息
    initCombobox( "#departure_port, #destination_port, #loading_port, #discharge_port", ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : (IS_SEA?'31-port-sea':'32-port-air') } ), { valueField : "name" } );

    // 标记为已经修改
    $("input, textarea, select").change(function(){ markDirty(); });
    /* 初始化 datebox */
    $(".easyui-datebox").datebox({ onSelect : function() { markDirty(); } });
    /* 初始化 combobox */
    function initCombobox( selector, url, options ) {
        $.ajax( {
            type : "POST",
            url : url,
            dataType : "json",
            success : function( data ) {
                $( selector ).combobox( $.extend({}, { data : data, onSelect : function(){ markDirty();} }, options) );
            }
        } );
    }
    
    // ------------------------- cargo init ------------------------- //
    var CATEGORIES = [{v:"20"},{v:"40"},{v:"8"},{v:"10"},{v:"12"},{v:"22"},{v:"43"},{v:"45"},{v:"53"},{v:"200"},{v:"400"},{v:"450"}];
    var TYPIES = [{v:"GP"},{v:"HC"},{v:"RF"},{v:"BK"},{v:"BO"},{v:"DO"},{v:"FR"},{v:"H"},{v:"HG"},{v:"HH"},{v:"HT"},{v:"HZ"},{v:"L"},{v:"PC"},{v:"RE"},{v:"RH"},{v:"RT"},{v:"S"},{v:"SP"},{v:"ST"},{v:"T"},{v:"TK"},{v:"VE"},{v:"W"},{v:"WH"}];
    var $lastIndex = -1;    
    var $grid = $("#cargo_grid").datagrid( {
        title : '货物信息',
        height : IS_SEA ? 270 : 255,
        pagination:false,
        /**
         * 定义全局变量 IS_SEA, 标明货物是空运还是海运
         * IS_SEA == true    表示海运的货物
         * IS_SEA != true     表示空运的货物
         */
        columns:[ [
            { field:'id', hidden: true, formatter: function(v){ return v + "<input type='hidden' name='id' value='" + v + "' />" ; } },
            { field:'name', title:'名称', width:100, sortable:true, align:"center", editor:"text", hidden: IS_SEA,     // 空运显示
                formatter: function(v, data){ return v + "<input type='hidden' name='name' value='" + v + "' />" ; } },
            { field:'category', title:'箱类', width:100,   sortable:true, align:"center", hidden: ! IS_SEA, // 海运显示
                    formatter: function(v, data){ return v + "<input type='hidden' name='category' value='" + v + "' />" ; },
                    editor:{type:"combobox",options:{ data:CATEGORIES,valueField:'v',textField:'v',onChange:function(){ refreshCargo(); } } } },
            { field:'type', title:'箱型', width:100,   sortable:true, align:"center", hidden: ! IS_SEA,        // 海运显示
                    formatter: function(v, data){ return v + "<input type='hidden' name='type' value='" + v + "' />" ; },
                    editor:{type:"combobox",options:{ data:TYPIES,valueField:'v',textField:'v',onChange:function(){ refreshCargo(); } } } },
            { field:'amount', title:(IS_SEA?'箱量':'数量'), width:100,   sortable:true, align:"right",
                    editor:{type:"numberbox",options:{ onChange:function(){ refreshCargo(); } } },
                    formatter: function(v, data){ return v + "<input type='hidden' name='amount' value='" + v + "' />" ; }    }
       ] ],
       toolbar : [{
           text:'添加',
           cls:'btn-primary',
           iconCls:'icon-plus icon-white',
           handler:function(){
               $grid.datagrid('endEdit', $lastIndex);
               $grid.datagrid('appendRow', { id : '', name : '', category : '', type : '', amount: 0 });
               $lastIndex = $grid.datagrid('getRows').length-1;
               $grid.datagrid('selectRow', $lastIndex);
               $grid.datagrid('beginEdit', $lastIndex);
           }
       }, '-', {
           text:'删除',
           cls:'btn-danger',
           iconCls:'icon-trash icon-white',
           handler:function(){
               var row = $grid.datagrid('getSelected');
               if (row){
                   var index = $grid.datagrid('getRowIndex', row);
                   $grid.datagrid('deleteRow', index);
                   refreshCargo();
               }
           }
       }, {
           text:'撤销修改',
           cls:'btn-info',
           float:'right',
           iconCls:'icon-share-alt icon-white',
           handler:function(){
               $grid.datagrid('rejectChanges');
               $lastIndex = -1;
               refreshCargo();
           }
       }],
       onClickRow:function(rowIndex){
           if ($lastIndex != rowIndex){
               $grid.datagrid('endEdit', $lastIndex);
               $lastIndex = rowIndex;
           }
           $grid.datagrid('beginEdit', rowIndex);
       }
    });
    
    // 页面是否修改的相关标记
    var formDirty = false;
    function markDirty() {
        formDirty = true;
    }
    function isDirty() {
        if( $lastIndex >= 0 ) { $grid.datagrid('endEdit', $lastIndex); }
        return formDirty || ( $grid.datagrid("getChanges").length > 0 );
    }
    
    $("#cargo_refresh").click(function(){
        if( $lastIndex >= 0 ) { $grid.datagrid('endEdit', $lastIndex); }
        refreshCargo();
        return false;
    });
    
    window.refreshCargo = function() {
    	var rows = $grid.datagrid("getRows");
    	var length = rows.length;
    	var v = "";
    	if(rows && length > 0) {
    		for(var i = 0; i < length; i++) {
        		var editors = $grid.datagrid('getEditors', i);
        		if(editors.length == 0) {
        			var row = rows[i];
        			v += ( " + " + row.category + row.type + "*" + row.amount);
        		} else {
        			var category = $(editors[1].target).combobox("getValue");
        			var type = $(editors[2].target).combobox("getValue");
        			var amount = $(editors[3].target).numberbox("getValue");
        			v += ( " + " + category + type + "*" + amount);
        		}
        	}
    		v = v.substring(3);
    	}
        if( v != $("#cargo_container").val() ) {
            $("#cargo_container").val( v );
            markDirty();
        }
    }
    
    function saveBookingNote( validate ) {
     // 格式化数据表格
        if( ! $("form").form("validate") ) { return false; }
        if( isDirty() || !validate ) {
            $("#dialog_save").attr("disabled", "disabled");
            var option = $grid.datagrid("options");
            var rows = $grid.datagrid("getRows");
            for( var j = 0; j < rows.length; j++ ) {
                var tr = option.finder.getTr( $grid[0], j, "body", 2 );
                $("input[type='hidden']", tr).each(function(){
                    $(this).attr("name", "cargos["+j+"]." +$(this).attr("name") );
                });
            }
            
            // 提交表单
            $("form").submit();
        } else {
            top.$.messager.info( "托单未发生变更，无需保存。" );
        }

        // 滚动至顶部
        $("form").parent().scrollTop( 0 );
    }
    
    $("#dialog_save").click(function(){
        saveBookingNote( true );
        return false;
    });    
    
    $("#dialog_save_returned").click(function(){
        if( $("input[name='returnCode']").val() == "" || $("input[name='returnDate']").val() == "" ) {
            top.$.messager.info( "请填写退单号及退单时间。", "信息不完整" );
            return false;
        }
        $("<input type='hidden' name='returned' value='true' />").appendTo( $("form") );
        saveBookingNote( false );
        return false;
    });
    
    // 重写关闭操作，提示保存。
    $("#dialog_close").unbind("click").bind("click", function(){
        if( isDirty() ) {
            top.$.messager.confirm( "托单数据还未保存，是否放弃修改？", function( ok ) {
                if( ! ok ) { return false; }
                else { top.$.close(); }
            } );
        } else {
            top.$.close();
        }
        return false;
    });
    
    // 滚动至顶部
    $("form").parent().scrollTop( 0 );
    
    $("input[name='quantity']").numberbox({
        onChange:onCargoQuantityChanged
    });
    
    function onCargoQuantityChanged() {
        var num = $("input[name='quantity']").val();
        var units = getUnitCode();
        var result = "SAY " + ToEn( num ) + " " + units + " ONLY"; 
        $("textarea[name='quantityDescription']").text( result.toUpperCase() );
        
        function getUnitCode() {
            var value = $("#units").combobox("getValue");
            var data =$("#units").combobox("getData");
            if( data != null && data.length > 0 ) {
                for( var i = 0; i < data.length; i++ ) {
                    if( data[i].name == value ) return data[i].code;
                }
            }
            return value;
        }
        
    }
    
    // 数字转换英文的 utils
    var arr1 = new Array("", " thousand", " million", " billion"),
    arr2 = new Array("zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"),
    arr3 = new Array("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"),
    arr4 = new Array("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen");
    function ToEn(a) {
        var b = a.length,
        f,
        h = 0,
        g = "",
        e = Math.ceil(b / 3),
        k = b - e * 3;
        g = "";
        for (f = k; f < b; f += 3) {++h;
            num3 = f >= 0 ? a.substring(f, f + 3) : a.substring(0, k + 3);
            strEng = English(num3);
            if (strEng != "") {
                if (g != "") g += ",";
                g += English(num3) + arr1[e - h];
            }
        }
        return g;
    }
    function English(a) {
        strRet = "";
        if (a.length == 3 && a.substr(0, 3) != "000") {
            if (a.substr(0, 1) != "0") {
                strRet += arr3[a.substr(0, 1)] + " hundred";
                if (a.substr(1, 2) != "00") strRet += " and ";
            }
            a = a.substring(1);
        }
        if (a.length == 2) if (a.substr(0, 1) == "0") a = a.substring(1);
        else if (a.substr(0, 1) == "1") strRet += arr4[a.substr(1, 2)];
        else {
            strRet += arr2[a.substr(0, 1)];
            if (a.substr(1, 1) != "0") strRet += "-";
            a = a.substring(1);
        }
        if (a.length == 1 && a.substr(0, 1) != "0") strRet += arr3[a.substr(0, 1)];
        return strRet;
    };
    
    if(!$("#node_id").val()) {
        $("#btn_charge").hide();
    }
    
    $("#btn_charge").click(function(){
        var bnId = $("#node_id").val();
        if( !bnId ) {
            return;
        }
        // 打开编辑页面
        top.$.open({
            width:1000,
            height:600,
            
            modal : false,
            collapsible : true,
            title:"费用信息：" + $("input[name='code']").val(),
            src : ksa.buildUrl( "/dialog/finance/charge", "view", { id : bnId, nature: 1 } )
        });
    });
    
    // 品名等 textarea 自动收缩
    $("textarea").focusin(function(){
        try{
            $(this).attr("rows-backup", $(this).attr("rows"));
            $(this).attr("rows", 10);
        }catch(e){}
    }).focusout(function(){
        try{$(this).attr("rows", $(this).attr("rows-backup"));}catch(e){}
    });
});