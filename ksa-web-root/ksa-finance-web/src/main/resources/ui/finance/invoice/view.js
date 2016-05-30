function validateCustomer() { return ( $("#target").combobox("getValue") != $("#target").combobox("getText") ); };
function validateCurrency() { return ( $("#currency").combobox("getValue") != $("#target").combobox("getText") ); };
$(function(){
    // 结算对象选择框
    $("#target").combobox({ url : ksa.buildUrl( "/data/combo", "bd-partner-without-lock" ) });
    // 收支
    $("#direction").combobox({editable:false});
    // 币种
    $("#currency").combobox({ url : ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '00-currency' } ) });    
});