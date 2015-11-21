$(function() {
    var $winWidth = 1000;
    var $winHeight = 600;
    
    $("#data_container").height( $(window).height() - 105 );
    $("#data_container").layout();
    
    var profitWinWidth = 700;
    var profitWinHeight = 500;
    // 初始化利润图表窗口
    var $win = $("#profit_window").window({
        title:'利润统计图',
        width : profitWinWidth,
        height : profitWinHeight,
        closable:false,
        maximizable:false,
        minimizable:false,
        resizable:false
    });
    $win.window("move", { top:0, left: $(window).width() - profitWinWidth });
    $("#chart_frame").height( $win.height() - $("#profit_window div.tool-bar").outerHeight() );
    $win.panel("collapse");
    
    $("#btn_chart").click(function() {
        var $div = $("#chart_query").empty();
        // 必须分页，不然数据量太大
        var pagination = $("#data_grid").datagrid("getPager").pagination("options");
        $div.append("<input type='hidden' name='page' value='"+pagination.pageNumber+"'/>");
        $div.append("<input type='hidden' name='rows' value='"+pagination.pageSize+"'/>");
        
        $("form.compositequery-form div.condition-block").clone().each(function(){
            $(this).appendTo($div);
        });
    });
    
    var $grid = $('#data_grid').datagrid({
        url : ksa.buildUrl( "/data/finance/profit", "query" ),
        fit : true,
        border : false,
        pagination : true,
        fitColumns : false,
        columns : [GET_PROFIT_TABLE_COLUMN({ 'expense_detail':false,'income_detail':false})],   // 明细默认不用显示
        onDblClickRow : function() {
            $("#btn_charge_edit").click();
        },
        onLoadSuccess : function( data ) {
            if( data.total <= 0 ) {
                $("#title").html("");
            } else {
                $("#title").html( gatherDetail( data ) );
            }
            
            function gatherDetail( d ) {
                if(!window.RATES&&RATES.length<=0) return;  // 有汇率数据才进行汇总
                
                var rmbIncome = 0, rmbExpend = 0, rmbProfit = 0;
                $.each( d.rows, function(j,row) {
                    var charges = row.charges;
                    if( !charges || charges.length <= 0 ) return;
                    var month = ksa.utils.parseDate( row.chargeDate?row.chargeDate:row.createdDate );   // 记账月份
                    $.each( charges, function(i,v) {
                        var amount = parseFloat( v.amount * getRate(v.currency.name, month) ); 
                        if( v.direction == 1 ) {
                            rmbIncome += amount;
                        } else {
                            rmbExpend += amount;
                        }
                    } );
                } );
                rmbProfit = rmbIncome - rmbExpend;
                var 
                incomeDetail = "<span class='profit" + ( rmbIncome < 0 ? "-1" : 1 ) + "'>总收入：<b> ￥" + rmbIncome.toFixed( 2 ) + "</b></span>",
                expendDetail = "<span class='profit" + ( rmbExpend < 0 ? "1" : "-1" ) + "'>总支出：<b> ￥" + rmbExpend.toFixed( 2 ) + "</b></span>",
                profitDetail = "<span class='profit" + ( rmbProfit < 0 ? "-1" : 1 ) + "'>总利润：<b> ￥" + rmbProfit.toFixed( 2 ) + "</b></span>";
                return incomeDetail + "　　" + expendDetail + "　　" + profitDetail;
            };
        },
        rowStyler:function(index,row,css){
            if ( row.state == -1 ){
                return 'text-decoration: line-through;';
            }
         }  
    });
    
    function initRates( rates ) {
        var result = {};
        $.each( rates, function(i, rate ) {
            if( !result[rate.currency.name] ) {
                result[ rate.currency.name ] = [];
            }
            rate.month = ksa.utils.parseDate( rate.month );
            result[ rate.currency.name ].push( rate );
        });
        return result;
    }
    var _rateMap = false;
    // 根据日期获取对应货币的汇率
    function getRate( currencyName, date ) {
        if( !_rateMap ) {
            _rateMap = initRates( window.RATES );
        }
        var rates = _rateMap[ currencyName ];
        if( rates.length == 1 ) {
            return rates[0].rate;
        } else {    // 选最接近的账单日的汇率
            var d = ksa.utils.parseDate(date);
            var result = {"rate":rates[0].rate, "month":new Date(0)};
            for( var i = 0; i < rates.length; i++ ) {
                if( rates[i].month <= d && rates[i].month > result.month ) 
                    result = rates[i];
            }
            return result.rate;
        }
    }
    
    // 费用明细查看
    $("#btn_charge_edit").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行查看操作。");
            return;
        }
        // 打开编辑页面
        $.open({
            width:$winWidth,
            height:$winHeight,
            
            title:"费用信息：" + row.code,
            src : ksa.buildUrl( "/dialog/finance/charge", "view", { id : row.id } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    // 业务信息查看
    $("#btn_bn_edit").click( function() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行编辑操作。");
            return;
        }
        // 打开编辑页面
        $.open({
            width:$winWidth,
            height:$winHeight,
            
            title:"编辑托单信息：" + row.code,
            src : ksa.buildUrl( "/dialog/logistics", "edit-" + row.type.toLowerCase(), { id : row.id, code : row.code } )
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    
    // 绑定热键事件
    ksa.hotkey.bindButton( $(".tool-bar button.btn") );
    
    // 初始化账单查询组件
    $("#query").compositequery({
        queryLabel : "统计汇总",
        onClear:function() {
            $grid.datagrid( "reload", {} );
        },
        onQuery:function( queryString ) {
            $grid.datagrid( "load", queryString );
        },
        conditions : PROFIT_QUERY_CONDITION
    });
});