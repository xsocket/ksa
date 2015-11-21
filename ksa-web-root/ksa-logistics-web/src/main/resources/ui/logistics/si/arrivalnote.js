$(function(){
    $("button[data-download], a[data-download]").click(function() {
        var type = $(this).attr("data-download");
        var filename = $(this).attr("data-filename");
        var form = $( "<form action='"+ksa.buildUrl( "/dialog/logistics/arrivalnote", "download-si")+"' method='POST' style='display:none;top:-100px;left:-100px;height:0;width:0'></form>" ).appendTo( $("body") );
        form.form("submit", {  
            url: ksa.buildUrl( "/dialog/logistics/arrivalnote", "download-si"),  
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
});