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
        dataSectionTitle : "备选用户列表",
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
                url: ksa.buildUrl( "/data/grid", "security-user-all" ),
                fit: true,
                border: false,
                pageSize: 20,
                singleSelect: false,
                columns:[[
                    { field:'id',             title:'用户标识', width:50,  checkbox:true,  sortable:true },
                    { field:'name',       title:'用户姓名', width:50,   sortable:true },
                    { field:'email',        title:'电子邮箱', width:100, sortable:true},
                    { field:'telephone', title:'联系电话', width:50,   sortable:true},
                    { field:'is_locked',  title:'状态',      width:30,   sortable:true, align:"center",
                        formatter : function(v, data ) {  
                            if( data.locked ) { return "已锁定"; } 
                            else { return "已激活"; } 
                        }  
                    }
                ]],
                onDblClickRow : function( i, row ) {
                    $("#multiple_selection").multipleselection("addData", { value:row.id, text:row.name, data:row } );
                },
                rowStyler:function(index,data,css){
                    // 锁定状态显示为 '红色'
                    if ( data.locked ){ return 'color:red;'; }
                }
            });
            
        }
     });
});