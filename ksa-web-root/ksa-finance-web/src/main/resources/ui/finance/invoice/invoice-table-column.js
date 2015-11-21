/** true: 表示显示相应名称的列，false: 表示隐藏相应名称的列。*/
var ACCOUNT_TABLE_SHOW_COLUMN = {        
        'a.state' : true,
        'i.code' : true,        
        'i.type' : true,
        'i.direction' : true,
        'p.name' : true,
        'c.name' : true,
        'i.amount' : true,
        'i.created_date' : true,
        'a.code' : true,
        'u.name' : true,
        'i.note' : false
};
function GET_INVOICE_TABLE_COLUMN( showColumn ) {
    
    var STATE_NONE = "新建";
    var STATE_UNPROCESSING = "待审核";
    var STATE_PROCESSING = "审核中";
    var STATE_CHECKING= "开票中";
    var STATE_CHECKED = function( direction ){ return direction == 1 ? "付款中" : "收款中";  };
    var STATE_SETTLED = "结算完毕";
    
    // 解析托单的状态 返回可读的状态值
    function parseState( data ) {
        if( data.account && data.account.id ) {
            var account = data.account;
            var state = account.state;
            if( state & 0x20 ) {
                return STATE_SETTLED;
            } else if( state & 0x8 ) {
                return STATE_CHECKED( account.direction );
            } else if( state & 0x2 ) {
                return STATE_CHECKING;
            } else if( state & 0x1 ) {
                return STATE_PROCESSING;
            } else {
                return STATE_UNPROCESSING;
            }
        } else {
            return STATE_NONE;
        }
    };
    
    showColumn = $.extend( {}, ACCOUNT_TABLE_SHOW_COLUMN,  showColumn || {} );
    return [            
        { field:'a.state', title:'状态', width:60,  sortable:true, align:'center', hidden:!showColumn['a.state'],
            formatter: function(v, data){ return parseState( data ); }, 
            styler : function(v,row) {
                var state = parseState( row );
                var css = "font-weight:bold;";
                if( state == "收款中" || state == "付款中" ) { return css + "color:#51A351"; } 
                else if( state == STATE_CHECKING ) { return css + "color:#FAA732"; }
                else if( state == STATE_PROCESSING ) { return css + "color:#04C"; }
                else if( state == STATE_NONE || state == STATE_UNPROCESSING ) { return css + "color:#BD362F"; }
            } },
        { field:'i.code', title:'发票号', width:150,  sortable:true, hidden:!showColumn['i.code'],
            formatter: function(v, data){ return data.code; } },
        { field:'i.type', title:'发票类型', width:120,  sortable:true, hidden:!showColumn['i.type'],
            formatter: function(v, data){ return data.type; } },
        { field:'i.direction', title:'收/支', width:50, sortable:true, align:"center", hidden:!showColumn['i.direction'],
            styler:function(v,data){ if ( data.direction == 1 ){ return 'color:#BD362F;font-weight:bold;'; } return 'color:#51A351;font-weight:bold;'; },
            formatter : function(v, data) { if( data.direction == 1 ){ return "收到"; } else { return "开出"; } } },
        { field:'p.name', title:'结算对象', width:150, sortable:true, hidden:!showColumn['p.name'],
            formatter: function(v, data){ return data.target.name; } },
        { field:'c.name', title:'币种', width:50,   sortable:true, align:"center", hidden:!showColumn["c.name"], toggleable : false,
                formatter: function(v, data){ return data.currency.name; } },
        { field:'i.amount', title:'金额', width:80,   sortable:true, align:"right", hidden:!showColumn["i.amount"], toggleable : false,
               styler:function(v,data){ if ( data.direction == 1 ){ return 'color:#BD362F;font-weight:bold;'; } return 'color:#51A351;font-weight:bold;'; },
               formatter: function(v, data){ return data.amount ; } },
        { field:'i.created_date', title:'开票日期', width:80, sortable:true, align:"center", hidden:!showColumn['i.created_date'],
            formatter : function(v, data) {  return ksa.utils.dateFormatter( data.createdDate ); } },
        { field:'a.code', title:(DIRECTION == 1 ? "对账单" : "结算单"), width:150, sortable:true, hidden:!showColumn['a.code'],
            formatter : function(v, data) {  return data.account.code; } },
        { field:'u.name', title:'创建人', width:60, sortable:true, align:"center", hidden:!showColumn['u.name'],
            formatter : function(v, data) {  return data.creator.name; } },
        { field:'i.note', title:'备注', width:150, sortable:true, hidden:!showColumn['i.note'],
                formatter : function(v, data) {  return data.note; } }
    ];
};