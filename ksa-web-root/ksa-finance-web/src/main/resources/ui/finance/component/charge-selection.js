$(function(){
    // 确认选择    
    $("#btn_ok").click( function() {
        var selected =  $("#multiple_selection").multipleselection("getResults");
        var results = [], ids = {};
        $.each( selected, function(i,node) {
            var row = node.data;
            if( !row._parentId ) {//托单行
                var charges = $grid.treegrid("getChildren", row.id);
                $.each(charges, function(j,c){
                    if( !ids[c.id] ) {
                        results.push({value:c.id,text: c.bookingNote.code + ":" + c.type,data : c});
                        ids[c.id]=true;
                    }
                });
            } else {
                if( !ids[row.id] ) {
                    results.push({value:row.id,text: row.bookingNote.code + ":" + row.type,data : row});
                    ids[row.id]=true;
                }
            }
        } );
        parent.$.close( results );
        return false;
    });
    var $grid = $("<table></table>");
    function init( container ) {
        var $layout = $("<div data-options='fit:true'></div>");
        var $layout_main = $("<div data-options=\"region:'center',border:false\" style='overflow:hidden'></div>");
        var $layout_sidebar = $("<div data-options=\"region:'east',title:'费用查询'\" style='width:220px'></div>");
        $layout.append( $layout_main ).append( $layout_sidebar ).appendTo( container ).layout();
        
        initGrid( $layout_main );
        initQuery( $layout_sidebar );
    };
    
    function initGrid( parent ) {
        $grid.appendTo( parent ); 
        $grid = $grid.treegrid({
            url: ksa.buildUrl( "/data/finance/charge", "query", {
                direction: ( DIRECTION != null ? DIRECTION : undefined ),
                nature: ( NATURE != null ? NATURE : undefined ),
                settle: ( SETTLE != null ? SETTLE : undefined )
            } ),
            idField: "id",
            treeField: "code",
            fit: true,
            border: false,
            fitColumns:false,
            rownumbers:false,
            pagination:false,
            pageSize: 50,
            singleSelect: false,
            columns:[ GET_CHARGE_TABLE_COLUMN( {}, $grid ) ],
            onDblClickRow : function( row ) {
                var text = "";
                if( !row._parentId ) {
                    text = row.code + ":" + $grid.treegrid("getChildren", row.id).length + "笔";
                } else {
                    text = row.bookingNote.code + ":" + row.type;
                }
                $("#multiple_selection").multipleselection("addData", {                     
                    value:row.id, 
                    text: text,
                    data : row
                 } );
            }
        });
    };
    
    function initQuery( parent ) {
        $( "<div data-options='fit:true, border:false'></div>" ).appendTo( parent ).compositequery({
            onClear:function() {
                $grid.treegrid( "reload", {} );
            },
            onQuery:function( queryString ) {
                var params = {};
                $.each( queryString.split("&"), function(i,p){
                    var a = p.split("=");
                    if( a.length == 2 ) {
                        params[a[0]] = a[1];
                    }
                });
                $grid.treegrid( "reload", params );
            },
            conditions : CHARGE_QUERY_CONDITION
        });
    }
    
    // 初始化多选组件
    $("#multiple_selection").multipleselection({
        dataSectionTitle : "备选费用列表",
        getSelectedData:function() {
            var data = [];
            var rows = $grid.treegrid('getSelections');
            for(var i=0;i<rows.length;i++){
                var row = rows[i], text = "";
                if( !row._parentId ) {
                    text = row.code + ":" + $grid.treegrid("getChildren", row.id).length + "笔";
                } else {
                    text = row.bookingNote.code + ":" + row.type;
                }
                data.push( { 
                    value:row.id,
                    text: text,
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