var INVOICE_QUERY_CONDITION = [
    { title:"发票状态", name:"INVOICE_STATE", init: function( target, condition ) {
        $(target).append("<label></label>");
        var input = $("<select name='" + condition.name + "' />").width( $(target).width() - 50 );
        input.append("<option value='0'>待审核</option>");
        input.append("<option value='1'>审核中</option>");
        input.append("<option value='2'>开票中</option>");
        input.append("<option value='8'>收/付款中</option>");
        input.append("<option value='32'>结算完毕</option>");
        $(target).append(input);
        input.combobox( {multiple:true,editable:false} );
    } },            
    { title:"发票号", name:"CODE", type:"text" },
    { title:"账单号", name:"ACCOUNT_CODE", type:"text" },
    { title:"发票类型", name:"TYPE", type:"text" },
    { title:"结算对象", name:"TARGET_ID", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "bd-partner-all" )} },
    { title:"收/支", name:"DIRECTION", init: function( target, condition ) {
        $(target).append("<label></label>");
        var input = $("<select name='" + condition.name + "' />").width( $(target).width() - 50 );
        input.append("<option value='1'>收到</option>");
        input.append("<option value='-1'>开出</option>");/*<#--开出的发票算支出-->*/
        $(target).append(input);
        input.combobox( {multiple:true,editable:false} );
    } },
    { title:"币种", name:"CURRENCY", type:"combo", option:{url : ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '00-currency' } )} },
    { title:"开票日期", name:"CREATED_DATE", type:"combo",  type:"date" },
    { title:"操作员", name:"INPUTOR", type:"combo", option:{url:ksa.buildUrl( "/data/combo", "security-user-all", multiple:true,editable:false ),codeField : "id" }  }
];