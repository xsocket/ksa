/** true: 表示显示相应名称的列，false: 表示隐藏相应名称的列。*/
var SHOW_COLUMN = {
        state : true,
        serial_number : true,
        type : false,
        created_date : false,
        customer_name : true,
        invoice_number : false,
        creator_name : false,
        saler_name : false,
        agent_name : true,
        
        mawb : true,
        shipper_name : false,
        consignee_name : false,
        
        cargo_name : false,
        cargo_container : false,
        
        customs_broker_name : true,
        customs_code : true,
        customs_date : true,
        
        return_code : true,
        return_express_code: true,
        return_date : true,
        return_date2 : true,
        tax_express_code: true,
        tax_code: true,
        tax_date1: true,
        tax_date2 : true,
        
        route : false,
        route_name : true,
        departure_port : false,
        departure_date : true,
        destination_port : false,
        destination_date : false
};
function GET_BOOKINGNOTE_TABLE_COLUMN( showColumn ) {
    
    showColumn = $.extend( {}, SHOW_COLUMN,  showColumn || {} );
    
    var STATE_UNRETURNED = "未退单";
    var STATE_RETURNED = "已退单";
    
    // 解析托单的状态 返回可读的状态值
    function parseState( data ) {
        var state = data.state;
        if( state & 0x10000000 ) {
            return STATE_RETURNED;
        } else {
            return STATE_UNRETURNED;
        }
    };
    
    return [
        { field:'state', title:'退单状态', width:50, sortable:true, align:"center",
            formatter: function(v, data){ return parseState( data ); }, 
            styler : function(v,row) {
                var state = parseState( row );
                var css = "font-weight:bold;";
                if( state == STATE_UNRETURNED ) { return css + "color:#FAA732"; } 
                else { return css + "color:#51A351"; }
            } },
        { field:'serial_number', title:'业务编号', width:70, sortable:true, align:"center", hidden:!showColumn["serial_number"], formatter: function(v, data){ return data.code; } },
        { field:'type', title:'类型', width:100, sortable:true, align:"center", hidden:!showColumn["type"],
               formatter : function( value, data ) {
                   var type = "";
                   switch( value.toUpperCase() ) {
                       case "SE" : type = "海运出口"; break; case "AI" : type = "空运进口"; break; 
                       case "SI" : type = "海运进口"; break; case "AE" : type = "空运出口"; break;
                       case "LY" : type = "国内运输"; break; case "KB" : type = "捆包业务"; break;
                       case "RH" : type = "内航线"; break; case "CC" : type = "仓储业务"; break;
                       case "BC" : type = "搬场业务"; break; case "TL" : type = "公铁联运"; break;
                   }
                   if( data.subType && data.subType != "" ) {
                       type = type + "( " + data.subType + " )";
                   }
                   return type;
               } 
        },
        { field:'customer_name', title:'客户', width:120, sortable:true, hidden:!showColumn["customer_name"], formatter: function(v, data){ return data.customer.name; } },
        { field:'departure_date', title:'出航日', width:70, align:"center", hidden:!showColumn["departure_date"], formatter: function(v, data){ return ksa.utils.dateFormatter( data.departureDate ); } },  
        { field:'mawb', title:'提单号', width:80, sortable:true, hidden:!showColumn["mawb"] },
        { field:'customs_code', title:'报关单号', width:80, sortable: true, hidden:!showColumn["customs_code"], formatter: function(v, data ){ return data.customsCode; } },                    
        { field:'customs_date', title:'报关日期', width:70, sortable:true, align:"center", hidden:!showColumn["customs_date"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.customsDate ); } },
        { field:'return_code', title:'退单号', width:80, sortable: true, hidden:!showColumn["return_code"], formatter: function(v, data ){ return data.returnCode; } },
        { field:'return_express_code', title:'退单快件号', width:80, sortable: true, hidden:!showColumn["return_express_code"], formatter: function(v, data ){ return data.returnExpressCode; } },
        { field:'return_date', title:'退单收单日', width:70, sortable:true, align:"center", hidden:!showColumn["return_date"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.returnDate ); } },
        { field:'return_date2', title:'退单寄送日', width:70, sortable:true, align:"center", hidden:!showColumn["return_date2"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.returnDate2 ); } },

        { field:'tax_code', title:'税单号', width:80, sortable: true, hidden:!showColumn["tax_code"], formatter: function(v, data ){ return data.taxCode; } },     
        { field:'tax_express_code', title:'税单快件号', width:80, sortable: true, hidden:!showColumn["tax_express_code"], formatter: function(v, data ){ return data.taxExpressCode; } },
        { field:'tax_date1', title:'税单收单日', width:70, sortable:true, align:"center", hidden:!showColumn["tax_date1"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.taxDate1 ); } },
        { field:'tax_date2', title:'税单寄送日', width:70, sortable:true, align:"center", hidden:!showColumn["tax_date2"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.taxDate2 ); } },
          // 报关信息                
        { field:'route_name', title:'船名/航次', width:100, sortable: true, hidden:!showColumn["route_name"], formatter: function(v, data){ return data.routeName; } },  
        { field:'customs_broker_name', title:'报关行', width:120, sortable:true, hidden:!showColumn["customs_broker_name"], formatter: function(v, data){ return data.customsBroker.name; } },
        { field:'created_date', title:'接单日期', width:70, sortable:true, align:"center", hidden:!showColumn["created_date"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.createdDate ); } },
        // 托单基本信息                        
        { field:'invoice_number', title:'发票号', width:80, sortable:true, hidden:!showColumn["invoice_number"], formatter: function(v, data){ return data.invoiceNumber; } },
     
    // 提单信息                
        
   // 货物信息                
        { field:'cargo_name', title:'品名', width:150, sortable:true, hidden:!showColumn["cargo_name"], formatter: function(v, data){ return data.cargoName; } },
        { field:'cargo_container', title:'箱类箱型', width:120, sortable:true, hidden:!showColumn["cargo_container"], formatter: function(v, data){ 
                var c = ( data.cargoContainer == null ? "" : data.cargoContainer ); return "<div title='"+c+"'>"+c+"</div>"; } },    
    // 航线信息                   
        { field:'departure_port', title:'起运港', width:50, align:"center", hidden:!showColumn["departure_port"], formatter: function(v, data){ return data.departurePort; } },  
        { field:'destination_port', title:'目的港', width:50, align:"center", hidden:!showColumn["destination_port"], formatter: function(v, data){ return data.destinationPort; } },
        { field:'destination_date', title:'到港日', width:70, align:"center", hidden:!showColumn["destination_date"], formatter: function(v, data){ return ksa.utils.dateFormatter( data.destinationDate ); } }
    ] 
};