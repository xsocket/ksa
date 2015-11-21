/** true: 表示显示相应名称的列，false: 表示隐藏相应名称的列。*/
var ACCOUNT_TABLE_SHOW_COLUMN = {
        'a.state' : true,
        'a.type' : false,
        'a.code' : true,
        'a.target_id' : true,
        'a.charges' : true, // 组合而成的费用明细
        'a.charge_gather' : true,
        'a.created_date' : true,
        'a.invoice_gather' : true,
        'a.deadline' : true,
        'a.payment_date' : true,
        'a.creator_name' : false,
        'a.note' : false
};
function GET_ACCOUNT_TABLE_COLUMN( showColumn ) {
    // 解析 费用明细
    function parseChargesDetail( charges, direction ) {
        if( !charges || charges.length <= 0 ) return "";
        var title = charges[0].type + "：" + charges[0].amount + charges[0].currency.name;
        var detail = "<span class='account-charge account-charge"+direction+"'>" + charges[0].type + "：<b>"+charges[0].amount+"</b> " + charges[0].currency.name + "</span>";
        for( var i = 1; i < charges.length; i++ ) {
            ( i % 2 != 0 ) ? detail += "，" : detail += "<br/>";
            title += "\n";
            var c = charges[i];
            detail += "<span class='account-charge account-charge"+direction+"'>" + c.type + "：<b>" + c.amount + "</b> " + c.currency.name + "</span>";
            title += c.type + "：" + c.amount + c.currency.name;
        }
        return "<div title='" + title + "'>" + detail +"</div>" ;
    };
    
    // 汇总费用/票据的金额
    function gatherDetail( details ) {
        if( !details || details.length <= 0 ) return "";
        var map = {};
        $.each( details, function(i,v){
            if( map[ v.currency.name ] ) {
                map[ v.currency.name ] += v.amount;
            } else {
                map[ v.currency.name ] = v.amount;
            }
        } );
        var detail = "";
        for( var k in map ) {
            detail += "<div>" + k + "：<b>" + parseFloat( map[k] ).toFixed( 1 ) + "</b></div>";
        }
        return detail;
    }
    
    var STATE_NONE = "录入中";
    var STATE_PROCESSING = "审核中";
    var STATE_CHECKING= "开票中";
    var STATE_CHECKED = function( direction ){ return direction == 1 ? "收款中" : "付款中";  };
    var STATE_SETTLED = "结算完毕";
    
    // 解析托单的状态 返回可读的状态值
    function parseState( data ) {
        var state = data.state;
        if( state & 0x20 ) {
            return STATE_SETTLED;
        } else if( state & 0x8 ) {
            return STATE_CHECKED( data.direction );
        } else if( state & 0x2 ) {
            return STATE_CHECKING;
        } else if( state & 0x1 ) {
            return STATE_PROCESSING;
        } else {
            return STATE_NONE;
        }
    };
    
    function parseType( direction, nature ) {
        var type = ( direction == 1 ? "结算单" : "对账单" );
        if( nature == -1 ) {
            type += "（境外）";
        }
        return type;
    };
    
    showColumn = $.extend( {}, ACCOUNT_TABLE_SHOW_COLUMN,  showColumn || {} );
    return [            
        { field:'a.state', title:'状态', width:50, align:'center', hidden:!showColumn["a.state"],
            formatter: function(v, data){ return parseState( data ); }, 
            styler : function(v,row) {
                var state = parseState( row );
                var css = "font-weight:bold;";
                if( state == STATE_CHECKED( row.direction ) ) { return css + "color:#51A351"; } 
                else if( state == STATE_CHECKING ) { return css + "color:#FAA732"; }
                else if( state == STATE_PROCESSING ) { return css + "color:#04C"; }
                else if( state == STATE_NONE ) { return css + "color:#BD362F"; }
            } },
        { field:'a.type', title:'账单类型', width:70, align:'center', sortable:false, hidden:!showColumn["a.type"],
            formatter: function(v, data){ return parseType( data.direction, data.nature ); },
            styler : function(v,row) {
                return row.direction == 1 ? "color:#BD362F" : "color:#51A351";
            } },
        { field:'a.code', title:'编号', width:130,  sortable:true, hidden:!showColumn["a.code"],
            formatter: function(v, data){ return data.code; } },
      { field:'a.target_id', title:'结算对象', width:100, sortable:true, hidden:!showColumn["a.target_id"],
            formatter: function(v, data){ return data.target.name; } },
        { field:'a.charges', title:'费用明细', width:250, hidden:!showColumn["a.charges"],
                formatter: function(v, data){ return parseChargesDetail( data.charges, data.direction ); } },
        { field:'a.charge_gather', title:'费用汇总', width:100, hidden:!showColumn["a.charge_gather"],
            formatter: function(v, data){ return "<div class='charge-gather charge-gather"+data.direction+"'>" + gatherDetail( data.charges ) + "</div>"; } },
        { field:'a.invoice_gather', title:'开票汇总', width:100, hidden:!showColumn["a.invoice_gather"],
            formatter: function(v, data){ return "<div class='invoice-gather invoice-gather"+data.direction+"'>" + gatherDetail( data.invoices ) + "</div>"; } },
        { field:'a.created_date', title:'开单日期', width:75, sortable:true, align:"center", hidden:!showColumn["a.created_date"],
            formatter : function(v, data) {  return ksa.utils.dateFormatter( data.createdDate ); } },
        { field:'a.deadline', title:'付款截止日', width:75, sortable:true, align:"center", hidden:!showColumn["a.deadline"],
            formatter : function(v, data) {  return ksa.utils.dateFormatter( data.deadline ); } },
        { field:'a.payment_date', title:'结清日期', width:75, sortable:true, align:"center", hidden:!showColumn['a.payment_date'],
            formatter : function(v, data) {  return ksa.utils.dateFormatter( data.paymentDate ); } },
        { field:'a.creator_name', title:'创建人', width:60, sortable:true, align:"center", hidden:!showColumn['a.creator_name'],
            formatter : function(v, data) {  return data.creator.name; } },
        { field:'a.note', title:'备注', width:150, sortable:true, hidden:!showColumn['a.note'],
                formatter : function(v, data) {  return data.note; } }
    ];
};