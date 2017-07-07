var shift = 10 - 2 * DIRECTION - 4 * NATURE;
var CHARGE_SINGLE_CONDITION = [
    { title:"记账月份", name:"CHARGE_DATE", type:"date" },
    { title:"费用状态", name:"STATE", init: function( target, condition ){
            $(target).append("<label></label>");
            var input = $("<select name='" + condition.name + "' />").width( $(target).width() - 50 );
            input.append("<option value='0'>暂未录入</option>");
            input.append("<option value='"+(0x1 << shift)+"'>录入中</option>");
            input.append("<option value='"+(0x2 << shift)+"'>审核中</option>");
            input.append("<option value='"+(0x8 << shift)+"'>审核通过</option>");
            input.append("<option value='-1'>业务作废</option>");
            $(target).append(input);
            input.combobox( {multiple:true,editable:false} );
        } 
    },
    { title:"托单编号", name:"CODE", type:"text" },
    { title:"业务类型", name:"TYPE", init: function( target, condition ){
        $(target).append("<label></label>");
        var input = $("<select name='" + condition.name + "' />").width( $(target).width() - 50 );
        input.append("<option value='SE'>海运出口</option>");
        input.append("<option value='SI'>海运进口</option>");
        input.append("<option value='AE'>空运出口</option>");
        input.append("<option value='AI'>空运进口</option>");
        input.append("<option value='LY'>国内运输</option>");
        input.append("<option value='KB'>捆包业务</option>");
        input.append("<option value='RH'>内航线</option>");
        input.append("<option value='CC'>仓储业务</option>");
        input.append("<option value='BC'>搬场业务</option>");
        input.append("<option value='TL'>公铁联运</option>");
        input.append("<option value='ZJ'>证件代办</option>");
        $(target).append(input);
        input.combobox( {multiple:true,editable:false} );
    } },
    { title:"接单日期", name:"CREATED_DATE", type:"date" },
    { title:"客户", name:"CUSTOMER_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-partner-all" )} },
    { title:"发票号", name:"INVOICE_NUMBER", type:"text" },
    { title:"操作员", name:"CREATOR_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "security-user-all" ),codeField : "id", multiple:true,editable:false }  },
    { title:"销售担当", name:"SALER_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "security-user-all" ),codeField : "id", multiple:true,editable:false } },
    { title:"承运人", name:"CARRIER_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-cyr' } ) } },
    { title:"船代", name:"SHIPPING_AGENT_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-cd' } ) } },
    { title:"代理", name:"AGENT_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-dls' } ) } },
    
    { title:"品名", name:"CARGO_NAME", type:"textarea" },
    { title:"箱类箱型", name:"CARGO_CONTAINER", type:"text" },
    { title:"唛头", name:"SHIPPING_MARK", type:"textarea" },
    
    { title:"提单号", name:"BL_NO", type:"text" },
    { title:"发货人", name:"SHIPPER_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-partner-all" )} },
    { title:"收货人", name:"CONSIGNEE_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-partner-all" )} },
    
    // TODO combo 如何传入多个参数
    { title:"出发地", name:"DEPARTURE", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '30-state' } ), valueField : "name"} },
    { title:"目的地", name:"DESTINATION", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '31-port-sea' } ), valueField : "name"} },
    
    { title:"起运港", name:"DEPARTURE_PORT", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '31-port-sea' } ), valueField : "name"} },
    { title:"目的港", name:"DESTINATION_PORT", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '31-port-sea' } ), valueField : "name"} },
    { title:"装货港", name:"LOADING_PORT", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '31-port-sea' } ), valueField : "name"} },
    { title:"卸货港", name:"DISCHARGE_PORT", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '31-port-sea' } ), valueField : "name"} },
    
    { title:"离港日", name:"DEPARTURE_DATE", type:"date" },
    { title:"到港日", name:"DESTINATION_DATE", type:"date" },
    { title:"送货日", name:"DELIVER_DATE", type:"date" },
    
    
    { title:"航线", name:"ROUTE", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '33-route-sea' } ), valueField : "name"} },
    { title:"船名/航班", name:"ROUTE_NAME", type:"text" },
    { title:"航次", name:"ROUTE_CODE", type:"text" },
    
    { title:"报关行", name:"CUSTOMS_BROKER_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-bgh' } ) } },
    { title:"报关单号", name:"CUSTOMS_CODE", type:"text" },
    { title:"报关日期", name:"CUSTOMS_DATE", type:"date" },
    { title:"退单号", name:"CUSTOMS_CODE", type:"text" },
    { title:"退单日期", name:"RETURN_DATE", type:"date" },
    
    { title:"车队", name:"VEHICLE_TEAM_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-partner-bytype", { typeId : '20-department-chedui' } ) } },
    { title:"车型", name:"VEHICLE_TYPE", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '08-vehicle' } ), valueField : "name"}  },
    { title:"车牌号", name:"VEHICLE_NUMBER", type:"text" }
];