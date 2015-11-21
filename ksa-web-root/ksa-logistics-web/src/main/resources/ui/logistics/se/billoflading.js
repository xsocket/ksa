$(function(){
    // CHECKBOX
    $("div.checkbox").click(function() {
        var $img = $("img", $(this));
        if( $img.attr("src") == CHECKBOX_CHECK_SRC ) {  // 选中时点击，仅仅取消选择即可
            $img.attr("src", CHECKBOX_SRC );
            $("#deliver_type").val("");
        } else {    // 没选中时点击，另外一个 checkbox 要取消选中
            $("img", "div.checkbox").attr("src", CHECKBOX_SRC );
            $img.attr("src", CHECKBOX_CHECK_SRC );
            $("#deliver_type").val( $img.attr("alt") );
        }
    });

    //initCombobox( "#loading_port, #discharge_port, #destination_port", ksa.buildUrl( "/data/combo", "bd-data-bytype", { typeId : '31-port-sea' } ), { valueField : "code", width: 150 } );
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
        var filename = $(this).attr("data-filename");
        var downType = "download-se";
        if(type=="copy-exce") {
            downType = "poi-download-copy";
        } else if( type == "original-exce" ) {
            downType = "poi-download";
        }
        var form = $( "<form action='"+ksa.buildUrl( "/dialog/logistics/billoflading", downType )+"' method='POST' style='display:none;top:-100px;left:-100px;height:0;width:0'></form>" ).appendTo( $("body") );
        form.form("submit", {  
            url: ksa.buildUrl( "/dialog/logistics/billoflading", downType),  
            onSubmit: function() {
                $("<input type='hidden' name='bookingNote.id' />").val( $("#bn_id").val() ).appendTo( form );
                $("<input type='hidden' name='type' />").val( type ).appendTo( form );
                if( filename != null && filename != "" ) {
                    $("<input type='hidden' name='filename' />").val( filename ).appendTo( form );
                }
            }
        }); 
        return false;
    });
    
 // CHECKBOX
    $("#dialog_refresh").click(function() {
        $("form").attr("action", "delete-se.action");
        $("form")[0].submit();
    });
});