$(function(){
    var width = $("#code").parent().width() * 0.95;
    initCombobox( "#saler", ksa.buildUrl( "/data/combo", "security-user-all" ), { codeField : "id", width : 400 } );
    initCombobox( "#loading_port, #destination_port", ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '32-port-air' } ), { valueField : "name", width: width } );
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
});