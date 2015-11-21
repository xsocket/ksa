/* 通过托单模板加速托单的创建 */
$(function(){
    var copyBtn = $("<button class='btn btn-success btn-small pull-left'><i class='icon-inbox icon-white'></i> 引用模板</button>");
    copyBtn.appendTo( $("form div.bottom-bar") );
    copyBtn.click(function(){
        ksa.logistics.selectBookingNotes(function(bns){
            if( bns && bns.length > 0 ) {
                $("<input type='hidden' name='template' value='"+bns[0].value+"' />").appendTo($("form"));
                var action = $("form").attr("action");
                $("form").attr("action", action.replace('bookingnote/insert', 'create'));
                $("input[name='type']").remove();
                $("form")[0].submit();
            }
        });
        return false;
    });
});