$(function(){
    // 上次编辑的行编号
    var lastIndex = -1;
    
    var year = $("#yy").val();
    var month = $("#mm").val();
    var now = new Date();
    
    // 初始化汇率表
    var $grid = $('#data-grid').datagrid({
        url : ksa.buildUrl( "/data/grid/currency", "date", { "year" : year, "month" : month } ),
        height : $(window).height() - 65,
        pagination : false,
        fitColumns : false,
        columns : [ [
            { field:'code',         title:'货币代码',      width:100,
                    formatter: function(v, data ){ try{return data.currency.code;}catch(e){return "";} } },
            { field:'name',         title:'货币名称',      width:100, 
                    formatter: function(v, data ){ try{return data.currency.name;}catch(e){return "";} } },
            { field:'rate',  title:'汇率', width:100, align:'right', editor:{ type:'numberbox',options:{precision:4} }, 
                    styler:function(){return 'color:blue;';} },
       ] ],
       rowStyler:function(index,row,css){
           if ( !row.id ){
               return 'color:red;font-weight:bold;';
           }
       },
       onClickRow:function( rowIndex ) {
           if (lastIndex != rowIndex){
               if( lastIndex >= 0 ) {
                   save();
               }
               $grid.datagrid('beginEdit', rowIndex);
           }
           lastIndex = rowIndex;
       }

    });
    
    // 绑定保存事件
    $("#save").unbind("click").bind("click", function(e){
        var length = save();
        if( length > 0 ) {
            $.messager.success("货币汇率设置成功！");
        } else {
            $.messager.info("暂时没有任何修改需要保存。");
        }
        return false;
    });
    
    function save() {
        if( lastIndex >= 0 ) { $grid.datagrid('endEdit', lastIndex); }
        var rates = $grid.datagrid('getChanges');
        $grid.datagrid('acceptChanges');
        
        $.each( rates, function(i, rate) {
            var rowIndex = $grid.datagrid( 'getRowIndex', rate );
            $.ajax({
                url: ksa.buildUrl( "/ui/bd/currency", "save" ),
                data: { "id": rate.id, "rate": rate.rate, "currency.id": rate.currency.id, "month": (year + "-" + month + "-1") },
                success: function( result ) {
                    try {
                        if (result.status == "success") { 
                            $grid.datagrid( "updateRow",{ index:rowIndex,row:result.data } );
                        } 
                        else { $.messager.error( result.message ); }
                    } catch (e) { }
                }
            } );
        } );
        return rates.length;    // 返回更新数量
    }
    
    // 绑定热键事件
    ksa.hotkey.bindButton( $(".toolbar button.btn") );
});