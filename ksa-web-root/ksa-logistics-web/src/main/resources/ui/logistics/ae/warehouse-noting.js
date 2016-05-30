$(function(){
    var width = $("#customer").parent().width() * 0.95;
    initCombobox( "#saler", ksa.buildUrl( "/data/combo", "security-user-all" ), { codeField : "id", width : 400 } );
    initCombobox( "#customer", ksa.buildUrl( "/data/combo", "bd-partner-without-lock" ), { width: width } ); 
    initCombobox( "#destination",  ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '30-state' } ), { valueField : "name", width: width } );
    initCombobox( "#loading_port, #discharge_port", ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '32-port-air' } ), { valueField : "name", width: width } );
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
    
    $("button[data-download], a[data-download]").click(function() {
        var type = $(this).attr("data-download");
        var form = $( "<form action='"+ksa.buildUrl( "/dialog/logistics/warehousenoting", "download-ae")+"' method='POST' style='display:none;top:-100px;left:-100px;height:0;width:0'></form>" ).appendTo( $("body") );
        form.form("submit", {  
            url: ksa.buildUrl( "/dialog/logistics/warehousenoting", "download-ae"),  
            onSubmit: function() {
                $("<input type='hidden' name='bookingNote.id' />").val( $("#bn_id").val() ).appendTo( form );
                $("<input type='hidden' name='type' />").val( type ).appendTo( form );
            }
        }); 
        return false;
    });
});