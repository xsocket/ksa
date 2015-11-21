$(function(){
    var width = $("#created_date").parent().width() * 0.95;
    if( $.browser.msie ) {
        width = 150;
    }
    $("#created_date").datebox({ 'width': width });
    
    initCombobox( "#saler", ksa.buildUrl( "/data/combo", "security-user-all" ), { codeField : "id", width : 400 } );
    initCombobox( "#departure_port, #destination_port", ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '31-port-sea' } ), { valueField:"code", codeField : "name", textField:"code", width: width } );
    function initCombobox( selector, url, options ) {
        $.ajax( {
            type : "POST",
            url : url,
            dataType : "json",
            success : function( data ) {
                $( selector ).combobox( $.extend({}, { data : data, onSelect : function(){ /*markDirty();*/} }, options) );
            }
        } );
    }
    $("#paymentMode, #grouping, #switchShip").combobox({"width":width});
});