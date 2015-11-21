$(function(){
    // 确认选择    
    $("#btn_ok").click( function() {
        var results =  $("#multiple_selection").multipleselection("getResults");
        parent.$.close( results );
        return false;
    });
    var $grid = $("<table></table>");
    function init( container ) {
        var $layout = $("<div data-options='fit:true'></div>");
        var $layout_main = $("<div data-options=\"region:'center',border:false\" style='overflow:hidden'></div>");
        var $layout_sidebar = $("<div data-options=\"region:'east',title:'发票查询'\" style='width:220px'></div>");
        $layout.append( $layout_main ).append( $layout_sidebar ).appendTo( container ).layout();
        
        initGrid( $layout_main );
        initQuery( $layout_sidebar );
    };
    
    function initGrid( parent ) {
        $grid.appendTo( parent ); 
        $grid.datagrid({
            url: ksa.buildUrl( "/data/finance/invoice", "query",{
                direction: ( DIRECTION != null ? DIRECTION : undefined ),
                nature: ( NATURE != null ? NATURE : undefined ),
                settle: ( SETTLE != null ? SETTLE : undefined )
            } ),
            fit: true,
            border: false,
            fitColumns:false,
            pageSize: 20,
            singleSelect: false,
            columns:[ GET_INVOICE_TABLE_COLUMN( {"i.type":false, "a.code":false, "u.name":false } ) ],
            onDblClickRow : function( i, row ) {
                $("#multiple_selection").multipleselection("addData", { 
                    value:row.id, 
                    text: row.code,
                    data : row
                 } );
            }
        });
    };
    
    function initQuery( parent ) {
        $( "<div data-options='fit:true, border:false'></div>" ).appendTo( parent ).compositequery({
            onClear:function() {
                $grid.datagrid( "reload", {} );
            },
            onQuery:function( queryString ) {
                $grid.datagrid( "load", queryString );
            },
            conditions : INVOICE_QUERY_CONDITION
        });
    }
    
    // 初始化多选组件
    $("#multiple_selection").multipleselection({
        dataSectionTitle : "备选发票列表",
        getSelectedData:function() {
            var data = [];
            var rows = $grid.datagrid('getSelections');
            for(var i=0;i<rows.length;i++){
                var row = rows[i];
                data.push( { 
                    value:row.id, 
                    text: row.code,
                    data : row
                } );
            }
            return data;
        },
        initDataSection: function( container ) {
            init( container );
        }
     });
});