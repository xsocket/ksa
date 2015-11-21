$(function(){
    
    // 业务托单列表
    var $bnGrid = $("#bn_datagrid").datagrid({
        loadEmptyMsg:'<span class="label label-warning">注意</span> 暂时没有任何相关的业务信息',
        pagination: false,
        remoteSort:false,
        fit: true,
        fitColumns:false,
        columns:[ getBookingNoteTableColumn() ],
        onDblClickRow : function(){ viewNote(); }
    }).datagrid('loadData', BOOKING_NOTES || [] );
    
    function getBookingNoteTableColumn() {
        var NOT_SHOW_COLUMNS = [ "creator_name", "agent_name", "customs_broker_name", "customs_code", "customs_date" ]; 
        var columns = [];
        $.each( GET_BOOKINGNOTE_TABLE_COLUMN({
            type:false,
            created_date:false,customer_name:false,invoice_number:true,creator_name:false,agent_name:false,cargo_name:false,customs_code:false,
            //volumn:false,weight:false,quantity:false,
            cargo_container:true,   // 空运不显示
            route_name:true,
            departure_port:true,
            departure_date:true,
            destination_port:true,
            destination_date:true
        }), function(i,d){
            if( $.inArray( d.field, NOT_SHOW_COLUMNS ) < 0 ) {
                d.sortable=false;
                columns.push( d );
            }
        });
        return columns;
    };

    // 数据列表
    var $chargeGrid = $('#charge_datagrid').datagrid({
        fit : true,
        pagination : false,
        fitColumns : false,
        rownumbers : false,
        columns : CHARGE_COLUMNS,
        rowStyler:function(i){
            if ( i == CHARGE_DATA.length - 1 ){ // TOTAL 行
                if( DIRECTION == 1 ) {
                    return 'font-weight:bold;color:#BD362F;';
                } else {
                    return 'font-weight:bold;color:#51A351;';
                }
            }
        },
        onDblClickRow : function(){ alert("todo view charge"); }
    }).datagrid( "loadData", CHARGE_DATA );
    
    $("#btn_download").click(function() {
        var form = $( "<form action='"+ksa.buildUrl( "/dialog/finance/account", "account-download")+"' method='POST' style='display:none;top:-100px;left:-100px;height:0;width:0'></form>" ).appendTo( $("body") );
        form.form("submit", {  
            url: ksa.buildUrl( "/dialog/finance/account", "account-download"),  
            onSubmit: function() {
                var option = $("#bn_datagrid").datagrid("options");
                var tr = option.finder.getTr(  $("#bn_datagrid")[0], 0, "body", 2 );
                $("td:visible",tr).each( function(i,td) {
                    $("<input type='hidden' name='columns' />").val( $(td).attr("field") ).appendTo( form );
                });
                $("<input type='hidden' name='id' />").val( $("#account_id").val() ).appendTo( form );
            }
        }); 
        return false;
    });
    
    function viewNote(){
        var row = $bnGrid.datagrid( "getSelected" );
        if( ! row ) {
            $.messager.warning("请选择一条数据后，再进行查看业务操作。");
            return;
        }
        // 打开编辑页面
        top.$.open({
            width:1000,
            height:600,
            modal : false,
            collapsible : true,
            
            title:"编辑托单信息：" + row.code,
            src : ksa.buildUrl( "/dialog/logistics", "edit-" + row.type.toLowerCase(), { id : row.id, code : row.code } )
        }, function(){
            window.location.href=window.location.href;
        });
    }
    
});