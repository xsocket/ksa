/** true: 表示显示相应名称的列，false: 表示隐藏相应名称的列。*/
var SHOW_COLUMN = {
        'serial_number' : true,
        'type' : false,
        'created_date' : true,
        'customer_name' : true,
        
        'cargo_name' : false,
        'volumn' : true,
        'weight' : true,
        'quantity' : true,
        'cargo_container' : true,
        
        'invoice_number' : false,
        'creator_name' : true,
        'saler_name' : false,
        'agent_name' : false,
        
        'mawb' : false,
        'shipper_name' : false,
        'consignee_name' : false,
        
        'customs_broker_name' : false,
        'customs_code' : false,
        'customs_date' : false,
        
        'route' : false,
        'route_name' : false,
        'departure_port' : true,
        'departure_date' : false,
        'destination_port' : true,
        'destination_date' : false
};


function GET_CARGO_TABLE_COLUMN( showColumn ) {
    
    var containerTypes = [ "20", "40" ];
    // 箱量统计
    function containerParse( cargos, type ) {
        var count = 0;
        $.each( cargos, function(i,cargo){
            if( type == null && $.inArray( cargo.category, containerTypes ) < 0 ) {
                count++;
            } else if( cargo.category == type ) {
                count++;
            }
        } );
        return count == 0 ? "/" : count;
    };   
    
    showColumn = $.extend( {}, SHOW_COLUMN,  showColumn || {} );
    return [
        { field:'serial_number', title:'业务编号', width:70, sortable:true, align:"center", hidden:!showColumn["serial_number"], formatter: function(v, data){ return data.code; } },
        { field:'type', title:'类型', width:60, sortable:true, align:"center", hidden:!showColumn["type"],
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
        { field:'created_date', title:'接单日期', width:70, sortable:true, align:"center", hidden:!showColumn["created_date"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.createdDate ); } },
        { field:'customer_name', title:'客户', width:120, sortable:true, hidden:!showColumn["customer_name"], formatter: function(v, data){ return data.customer.name; } },
        
   // 货物信息                
        { field:'cargo_name', title:'品名', width:150, sortable:true, hidden:!showColumn["cargo_name"], formatter: function(v, data){ return data.cargoName; } },
        { field:'volumn', title:'体积', width:50, toggleable:false, align:"right", sortable:true, hidden:!showColumn["volumn"], formatter: function(v, data){ if( v ) { return v + " m<sup>3</sup>"; }  return ""; } },  
        { field:'weight', title:'毛重', width:50, toggleable:false, align:"right", sortable:true, hidden:!showColumn["weight"], formatter: function(v, data){ if( v ) { return v + " kg"; } return "";  } },  
        { field:'quantity', title:'数量', width:50, toggleable:false, align:"right", sortable:true, hidden:!showColumn["quantity"], formatter: function(v, data){ if( v ) { return v + " " + data.unit; } return "";  } },
        { field:'cargo_container', title:'箱类箱型', width:140, toggleable:false, sortable:true, hidden:!showColumn["cargo_container"], 
            formatter: function(v, data){ 
                if(data.cargoContainer) { return "<div title='"+data.cargoContainer+"'>"+data.cargoContainer+"</div>";}
                else return "";
            } 
        },
        { field:'container20', title:'箱类：20', width:70, toggleable:false, align:"right", formatter: function(v, data){ return containerParse( data.cargos, "20" ); } },
        { field:'container40', title:'箱类：40', width:70, toggleable:false, align:"right", formatter: function(v, data){ return containerParse( data.cargos, "40" ); } },
        { field:'container_other', title:'箱类：其他', width:70, toggleable:false, align:"right", formatter: function(v, data){ return containerParse( data.cargos, null ); } },
        { field:'container_total', title:'箱量合计', width:70, toggleable:false, align:"right", formatter: function(v, data){ return data.cargos.length; } },
            
    // 托单基本信息                        
        { field:'invoice_number', title:'发票号', width:80, sortable:true, hidden:!showColumn["invoice_number"], formatter: function(v, data){ return data.invoiceNumber; } },
        { field:'creator_name', title:'操作员', width:50, sortable:true, align:"center", hidden:!showColumn["creator_name"], formatter: function(v, data){ return data.creator.name; } },
        { field:'saler_name', title:'销售担当', width:50, sortable:true,  align:"center", hidden:!showColumn["saler_name"], formatter: function(v, data){ return data.saler.name; } },
        { field:'agent_name', title:'代理', width:120, sortable:true, hidden:!showColumn["agent_name"], formatter: function(v, data){ return data.agent.name; } },
    // 提单信息                
        { field:'mawb', title:'提单号', width:80, sortable:true, hidden:!showColumn["mawb"] },
        { field:'shipper_name', title:'发货人', width:120, sortable:true, hidden:!showColumn["shipper_name"], formatter: function(v, data){ return data.shipper.name; } },
        { field:'consignee_name', title:'收货人', width:120, sortable:true, hidden:!showColumn["consignee_name"], formatter: function(v, data){ return data.consignee.name; } },    
    // 报关信息                
        { field:'customs_broker_name', title:'报关行', width:120, sortable:true, hidden:!showColumn["customs_broker_name"], formatter: function(v, data){ return data.customsBroker.name; } },
        { field:'customs_code', title:'报关单号', width:80, sortable: true, hidden:!showColumn["customs_code"], formatter: function(v, data ){ data.customsCode; } },     
        { field:'customs_date', title:'报关日期', width:70, sortable:true, align:"center", hidden:!showColumn["customs_date"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.customsDate ); } },
    // 航线信息                   
        { field:'route', title:'航线', width:100,  hidden:!showColumn["route"], sortable: true },
        { field:'route_name', title:'船名/航班', width:100, sortable: true, hidden:!showColumn["route_name"], formatter: function(v, data){ data.routeName; } },  
        { field:'departure_port', title:'起运港', width:50, align:"center", hidden:!showColumn["departure_port"], formatter: function(v, data){ data.departurePort; } },  
        { field:'departure_date', title:'离港日', width:70, align:"center", hidden:!showColumn["departure_date"], formatter: function(v, data){ return ksa.utils.dateFormatter( data.departureDate ); } },  
        { field:'destination_port', title:'目的港', width:50, align:"center", hidden:!showColumn["destination_port"], formatter: function(v, data){ data.destinationPort; } },
        { field:'destination_date', title:'到港日', width:70, align:"center", hidden:!showColumn["destination_date"], formatter: function(v, data){ return ksa.utils.dateFormatter( data.destinationDate ); } }
    ]; 
};