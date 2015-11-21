$(function(){
    // 确认选择    
    $("#btn_ok").click( function() {
        var results =  $("#multiple_selection").multipleselection("getResults");
        parent.$.close( results );
        return false;
    });
    
    var $grid = $("<table></table>");
    // 初始化多选组件
    $("#multiple_selection").multipleselection({
        dataSectionTitle : "备选权限列表",
        getSelectedData:function() {
            var data = [];
            var rows = $grid.datagrid('getSelections');
            for(var i=0;i<rows.length;i++){
                data.push( { value:rows[i].id,text:rows[i].name,data:rows[i] } );
            }
            return data;
        },
        initDataSection: function( container ) {
            $grid.appendTo( container );
            
            $grid.datagrid({
                url: ksa.buildUrl( "/data/grid", "security-permission-all" ),
                fit: true,
                border: false,
                pageSize: 20,
                singleSelect: false,
                columns:[[
                    { field:'id',             title:'权限标识', width:50,  checkbox:true,  sortable:true },
                    { field:'name',       title:'权限', width:50,   sortable:true },
                    { field:'description',        title:'说明', width:100, sortable:true}
                ]],
                onDblClickRow : function( i, row ) {
                    $("#multiple_selection").multipleselection("addData", { value:row.id, text:row.name, data:row } );
                }
            });
            
        }
     });
});