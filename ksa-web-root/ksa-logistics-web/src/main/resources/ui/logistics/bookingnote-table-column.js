/** true: 表示显示相应名称的列，false: 表示隐藏相应名称的列。*/
var SHOW_COLUMN = {
        serial_number : true,
        type : true,
        created_date : true,
        customer_name : true,
        invoice_number : true,
        creator_name : true,
        saler_name : false,
        agent_name : true,
        
        mawb : true,
        shipper_name : false,
        consignee_name : false,
        
        cargo_name : true,
        volumn : true,
        weight : true,
        quantity : true,
        cargo_container : false,
        
        customs_broker_name : false,
        customs_code : true,
        customs_date : false,
        
        route : false,
        route_name : false,
        departure_port : false,
        departure_date : false,
        destination_port : false,
        destination_date : false
};
function GET_BOOKINGNOTE_TABLE_COLUMN( showColumn ) {
    
    showColumn = $.extend( {}, SHOW_COLUMN,  showColumn || {} );
    return [
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
                       case "ZJ" : type = "证件代办"; break;
                   }
                   if( data.subType && data.subType != "" ) {
                       type = type + "( " + data.subType + " )";
                   }
                   return type;
               } 
        },
        { field:'created_date', title:'接单日期', width:60, sortable:true, align:"center", hidden:!showColumn["created_date"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.createdDate ); } },
    // 托单基本信息                        
        { field:'customer_name', title:'客户', width:120, sortable:true, hidden:!showColumn["customer_name"], formatter: function(v, data){ return data.customer.name; } },
        { field:'invoice_number', title:'发票号', width:80, sortable:true, hidden:!showColumn["invoice_number"], formatter: function(v, data){ return data.invoiceNumber; } },
        { field:'creator_name', title:'操作员', width:50, sortable:true, align:"center", hidden:!showColumn["creator_name"], formatter: function(v, data){ return data.creator.name; } },
        { field:'saler_name', title:'销售担当', width:50, sortable:true,  align:"center", hidden:!showColumn["saler_name"], formatter: function(v, data){ return data.saler.name; } },
        { field:'agent_name', title:'代理', width:120, sortable:true, hidden:!showColumn["agent_name"], formatter: function(v, data){ return data.agent.name; } },
    // 提单信息                
        { field:'mawb', title:'提单号', width:100, sortable:true, hidden:!showColumn["mawb"], formatter: function(v, data){ 
            var n = (data.mawb ? data.mawb : "") + " <strong>/</strong> " + (data.hawb ? data.hawb : "");
            var t = "主提单号：" + (data.mawb ? data.mawb : "暂无") + "\n副提单号：" + (data.hawb ? data.hawb : "暂无");
            return "<div title='"+t+"'>"+n+"</div>";
        } },
        { field:'shipper_name', title:'发货人', width:120, sortable:true, hidden:!showColumn["shipper_name"], formatter: function(v, data){ return data.shipper.name; } },
        { field:'consignee_name', title:'收货人', width:120, sortable:true, hidden:!showColumn["consignee_name"], formatter: function(v, data){ return data.consignee.name; } },
    // 货物信息                
        { field:'cargo_name', title:'品名', width:150, sortable:true, hidden:!showColumn["cargo_name"], formatter: function(v, data){ return data.cargoName; } },
        { field:'volumn', title:'体积', width:50, align:"right", sortable:true, hidden:!showColumn["volumn"], formatter: function(v, data){ if( v ) { return v + " m<sup>3</sup>"; }  return ""; } },  
        { field:'weight', title:'毛重', width:50, align:"right", sortable:true, hidden:!showColumn["weight"], formatter: function(v, data){ if( v ) { return v + " kg"; } return "";  } },  
        { field:'quantity', title:'数量', width:50, align:"right", sortable:true, hidden:!showColumn["quantity"], formatter: function(v, data){ if( v ) { return v + " " + data.unit; } return "";  } },
        { field:'cargo_container', title:'箱类箱型', width:120, sortable:true, hidden:!showColumn["cargo_container"], formatter: function(v, data){ 
                var c = ( data.cargoContainer == null ? "" : data.cargoContainer ); return "<div title='"+c+"'>"+c+"</div>"; } },         
    // 报关信息                
        { field:'customs_broker_name', title:'报关行', width:120, sortable:true, hidden:!showColumn["customs_broker_name"], formatter: function(v, data){ return data.customsBroker.name; } },
        { field:'customs_code', title:'报关单号', width:80, sortable: true, hidden:!showColumn["customs_code"], formatter: function(v, data ){ return data.customsCode; } },     
        { field:'customs_date', title:'报关日期', width:70, sortable:true, align:"center", hidden:!showColumn["customs_date"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.customsDate ); } },
    // 航线信息                   
        { field:'route', title:'航线', width:100,  hidden:!showColumn["route"], sortable: true },
        { field:'route_name', title:'船名/航次', width:100, sortable: true, hidden:!showColumn["route_name"], formatter: function(v, data){ return data.routeName; } },  
        { field:'departure_port', title:'起运港', width:50, align:"center", hidden:!showColumn["departure_port"], formatter: function(v, data){ return data.departurePort; } },  
        { field:'departure_date', title:'离港日', width:70, align:"center", hidden:!showColumn["departure_date"], formatter: function(v, data){ return ksa.utils.dateFormatter( data.departureDate ); } },  
        { field:'destination_port', title:'目的港', width:50, align:"center", hidden:!showColumn["destination_port"], formatter: function(v, data){ return data.destinationPort; } },
        { field:'destination_date', title:'到港日', width:70, align:"center", hidden:!showColumn["destination_date"], formatter: function(v, data){ return ksa.utils.dateFormatter( data.destinationDate ); } }
    ] 
};