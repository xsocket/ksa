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
        dataSectionTitle : "备选单位类型列表",
        getSelectedData:function() {
            var data = [];
            var rows = $grid.datagrid('getSelections');
            for(var i=0;i<rows.length;i++){
                data.push( { value:rows[i].id,text:rows[i].name } );
            }
            return data;
        },
        initDataSection: function( container ) {
            $grid.appendTo( container );
            
            $grid.datagrid({
                url: ksa.buildUrl( "/data/grid", "bd-data-bytype", { typeId : "20-department"} ),
                fit: true,
                border: false,
                pageSize: 20,
                singleSelect: false,
                columns:[[
                    { field:'id',             title:'标识', width:50,  checkbox:true,  sortable:true },
                    { field:'name',       title:'名称', width:50,   sortable:true },
                    { field:'note',        title:'说明', width:100, sortable:true}
                ]],
                onDblClickRow : function( i, row ) {
                    $("#multiple_selection").multipleselection("addData", { value:row.id, text:row.name } );
                }
            });
            
        }
     });
});