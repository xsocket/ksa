$(function(){
    
    // 单位类型
    var $grid = $('#data_grid').datagrid({
        title : '单位类型',
        url: ksa.buildUrl( "/data/grid", "bd-partnertype-bypartnerid", { partnerId : $("#partner_id").val() } ),
        pagination : false,
        singleSelect: false,
        height: 250,
        columns:[[
              { field:'dump', checkbox:true },
              { field:'name', title:'单位类型', width:100, formatter:function(v,data,i) {
                  return v + "<input type='hidden' name='typeIds' value='" + data.id + "'/>";
              } }
          ]],
          toolbar:[{
              text:'添加...',
              cls : 'btn-primary',
              iconCls:'icon-plus icon-white',
              handler:function(){
                  ksa.bd.selectDepartments( function( data ) {
                      var rowsCount = $grid.datagrid("getData").total;
                      var rows = $grid.datagrid("getData").rows;
                      for( var i = 0; i < data.length; i++ ) {
                          var duplicate = false;
                          for( var j = 0; j < rowsCount; j++ ) {
                              if( data[i].value == rows[j].id ) {
                                  duplicate = true; break;
                              }
                          }
                          if( !duplicate ) {
                              var row = { id: data[i].value, name: data[i].text };
                              $grid.datagrid( "appendRow", row );
                          }
                      }
                  } );
              }
          },'-',{
              text:'移除',
              cls : 'btn-danger',
              iconCls:'icon-trash icon-white',
              handler:function(){
                  var selectedRows = $grid.datagrid("getSelections");
                  $.each( selectedRows, function(i,row) {
                      var index = $grid.datagrid('getRowIndex', row);
                      $grid.datagrid('deleteRow', index);
                  } );
              }
          }]
    });
    
    // 初始化销售担当
    $("#saler").combobox({
        url : ksa.buildUrl( "/data/combo", "security-user-all" ),
        codeField : "id",
        width : 112
    });
    
    function addTextarea( text ) {
        var textarea = $("<textarea class='input-xxlarge' name='extras' rows='3' col='80'></textarea>").text( text ).click(function(e){
            e.stopPropagation();
        });
        $("<div></div>").append( textarea ).appendTo( $("#text_container") ).mouseover( function(){ 
            $(this).addClass("textrow-hover"); 
        } ).mouseout( function(){ 
            $(this).removeClass("textrow-hover");  
        } ).click( function(){
            $(this).toggleClass("textrow-selected");  
        } );
        
    };
    
    function freshStyle() {
        $("div", "#text_container").removeClass("odd");
        $("div:odd", "#text_container").addClass("odd");
    };
    
    $("#txtLock").click(function(){
    	if($("#txtLock").is(':checked')) {
    		$("#txtImpotant").attr("checked",null);
    		$("#divState").hide();
    	} else {
    		$("#divState").show();
    	}
    });

    // 初始化附加提单信息的增删操作
    $("#extra_add").click(function(){
        addTextarea("");
        freshStyle();
        return false;
    });
    $("#extra_del").click( function(){
        $("div.textrow-selected", $("#text_container") ).remove();
        freshStyle();
        return false;
    } );
    
    $.each( EXTRAS, function(i, v){
        addTextarea( v );
    });
    freshStyle();
    
});