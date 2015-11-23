/** true: 表示显示相应名称的列，false: 表示隐藏相应名称的列。*/
var SHOW_COLUMN = {
        'charge_gather' : true,
        'charge_detail' : true,
        
        'state' : true,
        'serial_number' : true,
        'type' : false,
        'charge_date' : true,
        'customer_name' : true,
        'invoice_number' : false,
        'creator_name' : true,
        'saler_name' : false,
        'agent_name' : false,
        
        'mawb' : false,
        'shipper_name' : false,
        'consignee_name' : false,
        
        'cargo_name' : false,
        'volumn' : false,
        'weight' : false,
        'quantity' : false,
        'cargo_container' : true,
        
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
/** 下载面单 */
function DOWNLOAD_RECORDBILL( id ) {
    var form = $( "<form action='"+ksa.buildUrl( "/ui/finance/recordbill", "download")+"' method='POST' style='display:none;top:-100px;left:-100px;height:0;width:0'></form>" ).appendTo( $("body") );
    form.form("submit", {  
        url: ksa.buildUrl( "/ui/finance/recordbill", "download"),  
        onSubmit: function() {
            $("<input type='hidden' name='id' />").val( id ).appendTo( form );
        }
    }); 
    return false;
}

function GET_CHARGE_SINGLE_COLUMN( showColumn ) {
    
    //解析 费用明细
    function parseChargesDetail( details, direction ) {
        var charges = details;
        if( direction != null ) {
            charges = filterCharges( details, direction );
        }
        if( !charges || charges.length <= 0 ) return "<b>/</b>";
        var title = charges[0].type + "：" + charges[0].amount + charges[0].currency.name;
        var detail = "<span class='charge" + ((charges[0].amount>0?1:-1) * charges[0].direction ) + "'>" + charges[0].type + "：<b>"+charges[0].amount+"</b> " + charges[0].currency.name + "</span>";
        for( var i = 1; i < charges.length; i++ ) {
            ( i % 2 != 0 ) ? detail += "，" : detail += "<br/>";
            title += "\n";
            var c = charges[i];
            detail += "<span class='charge" + ((c.amount>0?1:-1)*c.direction ) + "'>" + c.type + "：<b>" + c.amount + "</b> " + c.currency.name + "</span>";
            title += c.type + "：" + c.amount + c.currency.name;
        }
        return "<div title='" + title + "'>" + detail +"</div>" ;
        
    };
    
    // 汇总费用/票据的金额
    function gatherDetail( details, direction ) {
        var charges = details;
        if( direction != null ) {
            charges = filterCharges( details, direction );
        }
        if( !charges || charges.length <= 0 ) return "<b>/</b>";
        var map = {};
        $.each( charges, function(i,v){
            if( map[ v.currency.name ] ) {
                map[ v.currency.name ] += (v.amount * v.direction);
            } else {
                map[ v.currency.name ] = (v.amount * v.direction);
            }
        } );
        var detail = "";
        //var sign = ( direction == null ) ? 1 : direction;
        for( var k in map ) {
            /*var amount = parseFloat( map[k] * sign ).toFixed( 1 );
            detail += "<div class='charge" + ( amount < 0 ? "-1" : sign ) + "'>" + k + "：<b>" + amount + "</b></div>";*/
            var amount = parseFloat( map[k] ).toFixed( 1 );
            detail += "<div class='charge" + ( amount < 0 ? "-1" : "1" ) + "'>" + k + "：<b>" + ( amount * DIRECTION ) + "</b></div>";
        }
        return detail;
    };
    
    function parseChargeDate(v){
    	if(!v){return"";}
		try{var d=new Date(v);if(d.getDate()+""=="NaN"){while(v.indexOf("-")>=0){v=v.replace("-","/");}
		d=new Date(v);}
		return d.getFullYear()+" - "+(d.getMonth()+1) }catch(e){return v;}
	}

    function filterCharges( rawCharges, inout ) {
        var filtered = [];
        $.each( rawCharges, function(i,c){if(c.direction == inout)filtered.push(c);} );
        return filtered;
    };
    
    var STATE_CHECKED = "审核通过";
    var STATE_CHECKING = "审核中";
    var STATE_ENTERING = "录入中";
    var STATE_NONE = "暂未录入";
    var STATE_DELETED = "业务作废";
    
    var shift = 10 - 2 * DIRECTION - 4 * NATURE;
    // 解析托单的状态 返回可读的状态值
    function parseState( state ) {
        if( state == -1 ) {
            return STATE_DELETED;
        } else if( state & (0x8 << shift) ) {
            return STATE_CHECKED;
        } else if( state & (0x2 << shift) ) {
            return STATE_CHECKING;
        } else if( state & (0x1 << shift) ) {
            return STATE_ENTERING;
        } else {
            return STATE_NONE;
        }
    };
    
    showColumn = $.extend( {}, SHOW_COLUMN,  showColumn || {} );
    return [
        { field:'state', title:'费用状态', width:60,   sortable:true, align:"center", hidden:!showColumn["state"],
            formatter: function(v, data){ 
            	try{
            		var state = parseState( data.state );
            		if( state == STATE_NONE || state == STATE_DELETED ) {
            		    return state;
            		} else {
            		    return "<span title='导出面单' onclick='DOWNLOAD_RECORDBILL(\"" + data.id + "\"); return false;'>" + state + "</span>";
            		}
            	}catch(e){return "";} 
            }, 
            styler : function(v,row) {
                var state = parseState( row.state );
                var css = "font-weight:bold;";
                if( state == STATE_CHECKED ) { return css + "cursor:pointer;color:#51A351"; } 
                else if( state == STATE_CHECKING ) { return css + "cursor:pointer;color:#04C"; }
                else if( state == STATE_ENTERING ) { return css + "cursor:pointer;color:#FAA732"; }
                else if( state == STATE_NONE ) { return css + "color:#BD362F"; }
            } 
         },
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
        { field:'charge_date', title:'记账月份', width:70, sortable:true, align:"center", hidden:!showColumn["charge_date"], 
            formatter : function(v, data) {
                var charges = data.charges;
                if( !charges || charges.length <= 0 ) return "";
                else {
                    if(data.chargeDate) return parseChargeDate( data.chargeDate );
                    else return parseChargeDate( data.createdDate );
                } 
            } 
        },
        { field:'customer_name', title:'客户', width:120, sortable:true, hidden:!showColumn["customer_name"], formatter: function(v, data){ return data.customer.name; } },

        { field:'charge_detail', title:( 1 == DIRECTION ? '收入明细' : '支出明细'), width:250, toggleable:false, hidden:!showColumn["charge_detail"],
            formatter: function(v, data){ return parseChargesDetail( data.charges ); } },
        { field:'charge_gather', title: ( 1 == DIRECTION ? '收入汇总' : '支出汇总'), width:100, toggleable:false, hidden:!showColumn["charge_gather"],
            formatter: function(v, data){ return "<div>" + gatherDetail( data.charges ) + "</div>"; } },
            
    // 托单基本信息                        
        { field:'invoice_number', title:'发票号', width:80, sortable:true, hidden:!showColumn["invoice_number"], formatter: function(v, data){ return data.invoiceNumber; } },
        { field:'creator_name', title:'操作员', width:50, sortable:true, align:"center", hidden:!showColumn["creator_name"], formatter: function(v, data){ return data.creator.name; } },
        { field:'saler_name', title:'销售担当', width:50, sortable:true,  align:"center", hidden:!showColumn["saler_name"], formatter: function(v, data){ return data.saler.name; } },
        { field:'agent_name', title:'代理', width:120, sortable:true, hidden:!showColumn["agent_name"], formatter: function(v, data){ return data.agent.name; } },
    // 提单信息                
        { field:'mawb', title:'提单号', width:80, sortable:true, hidden:!showColumn["mawb"] },
        { field:'shipper_name', title:'发货人', width:120, sortable:true, hidden:!showColumn["shipper_name"], formatter: function(v, data){ return data.shipper.name; } },
        { field:'consignee_name', title:'收货人', width:120, sortable:true, hidden:!showColumn["consignee_name"], formatter: function(v, data){ return data.consignee.name; } },
    // 货物信息                
        { field:'cargo_name', title:'品名', width:150, sortable:true, hidden:!showColumn["cargo_name"], formatter: function(v, data){ return data.cargoName; } },
        { field:'volumn', title:'体积', width:50, align:"right", sortable:true, hidden:!showColumn["volumn"], formatter: function(v, data){ if( v ) { return v + " m<sup>3</sup>"; }  return ""; } },  
        { field:'weight', title:'毛重', width:50, align:"right", sortable:true, hidden:!showColumn["weight"], formatter: function(v, data){ if( v ) { return v + " kg"; } return "";  } },  
        { field:'quantity', title:'数量', width:50, align:"right", sortable:true, hidden:!showColumn["quantity"], formatter: function(v, data){ if( v ) { return v + " " + data.unit; } return "";  } },
        { field:'cargo_container', title:'箱类箱型', width:120, sortable:true, hidden:!showColumn["cargo_container"], formatter: function(v, data){ return data.cargoContainer; } },         
    // 报关信息                
        { field:'customs_broker_name', title:'报关行', width:120, sortable:true, hidden:!showColumn["customs_broker_name"], formatter: function(v, data){ return data.customsBroker.name; } },
        { field:'customs_code', title:'报关单号', width:80, sortable: true, hidden:!showColumn["customs_code"], formatter: function(v, data ){ data.customsCode; } },     
        { field:'customs_date', title:'报关日期', width:70, sortable:true, align:"center", hidden:!showColumn["customs_date"], formatter : function(v, data) { return ksa.utils.dateFormatter( data.customsDate ); } },
    // 航线信息                   
        //{ field:'route', title:'航线', width:100,  hidden:!showColumn["route"], sortable: true },
        { field:'route_name', title:'船名/航班', width:100, sortable: true, hidden:!showColumn["route_name"], formatter: function(v, data){ data.routeName; } },  
        { field:'departure_port', title:'起运港', width:50, align:"center", hidden:!showColumn["departure_port"], formatter: function(v, data){ data.departurePort; } },  
        //{ field:'departure_date', title:'离港日', width:70, align:"center", hidden:!showColumn["departure_date"], formatter: function(v, data){ return ksa.utils.dateFormatter( data.departureDate ); } },  
        { field:'destination_port', title:'目的港', width:50, align:"center", hidden:!showColumn["destination_port"], formatter: function(v, data){ data.destinationPort; } }
        //,{ field:'destination_date', title:'到港日', width:70, align:"center", hidden:!showColumn["destination_date"], formatter: function(v, data){ return ksa.utils.dateFormatter( data.destinationDate ); } }
    ]; 
};