/** true: 表示显示相应名称的列，false: 表示隐藏相应名称的列。*/
var CHARGE_TABLE_SHOW_COLUMN = {
        'bn.state' : true,
        'c.target_name' : true,
        //'c.type' : true,
        'c.direction' : true,
        'c.nature' : true,
        'c.currency_name' : true,
        'c.amount' : true,
        'bn.charge_date' : true,
        'c.created_date' : false,
        'c.creator_name' : false,
        
        'a.code' : true,
        'bn.serial_number' : true,
        'bn.charge_date' : true,
        'bn.type' : false,
        'bn.created_date' : true,
        'bn.customer_name' : false,
//        'bn.invoice_number' : true,
//        'bn.creator_name' : true,
//        'bn.saler_name' : false,
//        'bn.agent_name' : true,
        
        'bn.mawb' : true,
        'bn.shipper_name' : false,
        'bn.consignee_name' : false,
        
//        'bn.cargo_name' : true,
//        'bn.cargo_container' : false,
//        'bn.volumn' : true,
//        'bn.weight' : true,
//        'bn.quantity' : true,
//        
//        'bn.customs_broker_name' : false,
//        'bn.customs_code' : true,
//        'bn.customs_date' : false,
        
//        'bn.route' : false,
//        'bn.route_name' : false,
        'bn.departure_port' : false,
        'bn.departure_date' : false,
        'bn.destination_port' : false,
        'bn.destination_date' : false
};
function GET_CHARGE_TABLE_COLUMN( showColumn, $grid ) {
    // 解析 收入/支出的名称 或者 代码
    function parseChargeDirection( v ) {
        if( v == 1 ) { return "收入"; } 
        else if( v == -1 ) { return "支出"; }
        else if( v == "收入" ) { return 1; }
        else if( v == "支出" ) { return -1; } 
        else { throw new Error( "收支名称或者代码设置错误。" ); }
    };
    function parseChargeNature( v ) {
        if( v == 1 ) { return "国内"; } 
        else if( v == -1 ) { return "境外"; }
        else if( v == "国内" ) { return 1; }
        else if( v == "境外" ) { return -1; } 
        else { throw new Error( "国内/境外设置错误。" ); }
    };
    
    var STATE_CHECKED = "审核通过";
    var STATE_CHECKING = "审核中";
    var STATE_ENTERING = "未审核";
    var STATE_ACCOUNT = "已开单";
    var STATE_UNACCOUNT = "未开单";
    // 解析托单的状态 返回可读的状态值
    function parseState( data ) {
        if( data.settle ) {
            return STATE_ACCOUNT;
        } else {
            var state = data.bookingNote.state;
            //var shift = 10 - 2 * data.direction - 4 * data.nature;
            var shift = ( data.nature == -1 ? 20 : 0 );
            if( state & ( 0x8 << shift ) ) {
                return STATE_CHECKED;
            } else if( state & ( 0x2 << shift ) ) {
                return STATE_CHECKING;
            } else if( state & ( 0x1 << shift ) ) {
                return STATE_ENTERING;
            } else {
                return STATE_UNACCOUNT;
            }
        }
    };
    function parseChargeDate(v){
        if(!v){return"";}
        try{var d=new Date(v);if(d.getDate()+""=="NaN"){while(v.indexOf("-")>=0){v=v.replace("-","/");}
        d=new Date(v);}
        return d.getFullYear()+" - "+(d.getMonth()+1) }catch(e){return v;}
    }
    showColumn = $.extend( {}, CHARGE_TABLE_SHOW_COLUMN,  showColumn || {} );
    return [
        { field:'code', title:'业务编号', width:120, align:"left", toggleable : false },
        { field:'bn.customer_name', title:'客户', width:100, sortable:true, hidden:!showColumn["bn.customer_name"], formatter: function(v, data){ if( !data._parentId ) return data.customer.name; return data.bookingNote.customer.name; } },
        { field:'bn.state', title:'费用状态', width:60, hidden:!showColumn["bn.state"],
            formatter: function(v, data){ if( !data._parentId ) return ""; return parseState( data ); }, 
            styler : function(v,data) {
                if( !data._parentId ) return "";
                var state = parseState( data );
                var css = "font-weight:bold;";
                if( state == STATE_CHECKED ) { return css + "color:#51A351"; } 
                else if( state == STATE_CHECKING ) { return css + "color:#04C"; }
                else if( state == STATE_ENTERING ) { return css + "color:#FAA732"; }
                else if( state == STATE_ACCOUNT ) { return css + "color:#04C"; }
            } },
        { field:'target_name', title:'结算对象', width:100,   sortable:true, hidden:!showColumn["c.target_name"], toggleable : false,
            formatter: function(v, data){ if( !data._parentId ) return ""; return data.target.name; } },
        //{ field:'c.type', title:'费用项目', width:60,   sortable:true, hidden:!showColumn["c.type"], toggleable : false,formatter: function(v, data){ if( !data._parentId ) return ""; return data.type; } },
        { field:'c.amount', title:'金额', width:60,   sortable:true, align:"right", hidden:!showColumn["c.amount"], toggleable : false,
                   styler:function(v,data){ if( !data._parentId ) return ""; if ( data.direction == 1 ){ return 'color:#BD362F;font-weight:bold;'; } return 'color:#51A351;font-weight:bold;'; },
                   formatter: function(v, data){ 
                       if( !data._parentId ) { 
                           return "共 <b>" + $grid.treegrid("getChildren", data.id).length + "</b> 笔"; 
                       } 
                       return parseFloat( data.amount ).toFixed( 2 );  
                   } },     
       { field:'currency_name', title:'币种', width:50,   sortable:true, align:"center", hidden:!showColumn["c.currency_name"], toggleable : false,
                   formatter: function(v, data){ if( !data._parentId ) return ""; return data.currency.name; } },                   
        { field:'c.direction', title:'收/支', width:40,   sortable:true, align:"center", hidden:!showColumn["c.direction"],
                    styler:function(v,data){ if ( data.direction == 1 ){ if( !data._parentId ) return ""; return 'color:#BD362F;font-weight:bold;'; } return 'color:#51A351;font-weight:bold;'; },
                    formatter: function(v, data){ if( !data._parentId ) return ""; return parseChargeDirection( data.direction );  } },
        { field:'c.nature', title:'内/外', width:40,   sortable:true, align:"center", hidden:!showColumn["c.nature"],
                styler:function(v,data){ if ( data.nature == -1 ){ if( !data._parentId ) return ""; return 'color:#f89406;font-weight:bold;'; } return 'color:#0044cc;'; },
                formatter: function(v, data){ if( !data._parentId ) return ""; return parseChargeNature( data.nature );  } },
        { field:'bn.charge_date', title:'记账月份', width:70, sortable:true, align:"center", hidden:!showColumn["bn.charge_date"],
                    formatter : function(v, data) { 
                        var bn = data._parentId?data.bookingNote:data;
                        if(bn.chargeDate) return parseChargeDate( bn.chargeDate );
                        else return "";
                    }  },
        { field:'c.created_date', title:'创建日期', width:70, sortable:true, align:"center", hidden:!showColumn["c.created_date"],
            formatter : function(v, data) { if( !data._parentId ) return "";  return ksa.utils.dateFormatter( data.createdDate ); } },
        { field:'c.creator_name', title:'操作人员', width:50,   sortable:true, align:"center", hidden:!showColumn["c.creator_name"],
                formatter: function(v, data){ return data.creator.name; } },
                
        //{ field:'a.code', title:'结算/对账单号', width:100, sortable:true, align:"center", hidden:!showColumn["a.code"], toggleable : false, formatter: function(v, data){ return data.account.code; } },
  
        { field:'bn.type', title:'类型', width:100, sortable:true, align:"center", hidden:!showColumn["bn.type"],
                formatter : function( value, data ) { 
                    var bn = data._parentId?data.bookingNote:data;
                    var type = "";
                    switch( bn.type.toUpperCase() ) {
                        case "SE" : type = "海运出口"; break; case "AI" : type = "空运进口"; break; 
                        case "SI" : type = "海运进口"; break; case "AE" : type = "空运出口"; break;
                        case "LY" : type = "国内运输"; break; case "KB" : type = "捆包业务"; break;
                        case "RH" : type = "内航线"; break; case "CC" : type = "仓储业务"; break;
                        case "BC" : type = "搬场业务"; break; case "TL" : type = "公铁联运"; break;
                    }
                    if( bn.subType && bn.subType != "" ) {
                        type = type + "( " + bn.subType + " )";
                    }
                    return type;
                } 
        },
        { field:'bn.created_date', title:'接单日期', width:70, sortable:true, align:"center", hidden:!showColumn["bn.created_date"], formatter : function(v, data) { if( !data._parentId ) return ksa.utils.dateFormatter( data.createdDate ); return ksa.utils.dateFormatter( data.bookingNote.createdDate ); } },
    // 托单基本信息                        
        
//        { field:'bn.invoice_number', title:'发票号', width:80, sortable:true, hidden:!showColumn["invoice_number"], formatter: function(v, data){ return data.invoiceNumber; } },
//        { field:'bn.creator_name', title:'操作员', width:50, sortable:true, align:"center", hidden:!showColumn["creator_name"], formatter: function(v, data){ return data.creator.name; } },
//        { field:'bn.saler_name', title:'销售担当', width:50, sortable:true,  align:"center", hidden:!showColumn["saler_name"], formatter: function(v, data){ return data.saler.name; } },
//        { field:'bn.agent_name', title:'代理', width:120, sortable:true, hidden:!showColumn["agent_name"], formatter: function(v, data){ return data.agent.name; } },
    // 提单信息                
        { field:'bn.mawb', title:'提单号', width:100, sortable:true, hidden:!showColumn["bn.mawb"], formatter: function(v, data){ 
            var bn = data._parentId?data.bookingNote:data;
            var n = (bn.mawb ? bn.mawb : "") + " <strong>/</strong> " + (bn.hawb ? bn.hawb : "");
            var t = "主提单号：" + (bn.mawb ? bn.mawb : "暂无") + "\n副提单号：" + (bn.hawb ? bn.hawb : "暂无");
            return "<div title='"+t+"'>"+n+"</div>";
        } },
    //    { field:'bn.shipper_name', title:'发货人', width:120, sortable:true, hidden:!showColumn["bn.shipper_name"], formatter: function(v, data){ return data.bookingNote.shipper.name; } },
    //    { field:'bn.consignee_name', title:'收货人', width:120, sortable:true, hidden:!showColumn["bn.consignee_name"], formatter: function(v, data){ return data.bookingNote.consignee.name; } },
    // 货物信息                
//        { field:'cargo_name', title:'品名', width:150, sortable:true, hidden:!showColumn["cargo_name"], formatter: function(v, data){ return data.cargoName; } },
//        { field:'cargo_container', title:'箱类箱型', width:120, sortable:true, hidden:!showColumn["cargo_container"], formatter: function(v, data){ return data.cargoContainer; } },
//        { field:'volumn', title:'体积', width:50, align:"right", sortable:true, hidden:!showColumn["volumn"], formatter: function(v, data){ if( v ) { return v + " m<sup>3</sup>"; }  return ""; } },  
//        { field:'weight', title:'毛重', width:50, align:"right", sortable:true, hidden:!showColumn["weight"], formatter: function(v, data){ if( v ) { return v + " kg"; } return "";  } },  
//        { field:'quantity', title:'数量', width:50, align:"right", sortable:true, hidden:!showColumn["quantity"], formatter: function(v, data){ if( v ) { return v + " " + data.unit; } return "";  } },  
    // 报关信息                
//        { field:'customs_broker_name', title:'报关行', width:120, sortable:true, hidden:!showColumn["customs_broker_name"], formatter: function(v, data){ return data.customsBroker.name; } },
//        { field:'customs_code', title:'报关单号', width:80, sortable: true, hidden:!showColumn["customs_code"], formatter: function(v, data ){ data.customsCode; } },     
//       { field:'customs_date', title:'报关日期', width:70, sortable:true, align:"center", hidden:!showColumn["customs_date"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.customsDate ); } },
    // 航线信息                   
//        { field:'route', title:'航线', width:100,  hidden:!showColumn["route"], sortable: true },
//        { field:'route_name', title:'船名/航班', width:100, sortable: true, hidden:!showColumn["route_name"], formatter: function(v, data){ data.routeName; } },  
        { field:'bn.departure_port', title:'起运港', width:50, align:"center", hidden:!showColumn["bn.departure"], formatter: function(v, data){if( !data._parentId ) return data.departurePort; data.bookingNote.departurePort; } },  
        { field:'bn.departure_date', title:'离港日', width:70, align:"center", hidden:!showColumn["bn.departure_date"], formatter: function(v, data){if( !data._parentId ) return ksa.utils.dateFormatter( data.departureDate );  return ksa.utils.dateFormatter( data.bookingNote.departureDate ); } },  
        { field:'bn.destination_port', title:'目的港', width:50, align:"center", hidden:!showColumn["bn.destination_port"], formatter: function(v, data){if( !data._parentId ) return data.destinationPort; return data.bookingNote.destinationPort; } }
     ,   { field:'bn.destination_date', title:'到港日', width:70, align:"center", hidden:!showColumn["bn.destination_date"], formatter: function(v, data){ if( !data._parentId ) return ksa.utils.dateFormatter( data.destinationDate ); return ksa.utils.dateFormatter( data.bookingNote.destinationDate ); } }
    ] 
};