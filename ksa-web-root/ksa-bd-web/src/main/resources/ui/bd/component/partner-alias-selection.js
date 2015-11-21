$(function() {
    
    /* ------------ 合作伙伴信息 ------------ */ 
    $( "#partner_id" ).combobox( { 
        url : ksa.buildUrl( "/data/combo", "bd-partner-all" ),
        onSelect : function( record ) {
            $grid.datagrid( "load", { id : record.id } );
        }
    } );
    
    
    // 确认选择    
    $("#btn_ok").click( function() {
        var results =  $("#extra_grid").datagrid("getSelected");
        parent.$.close( results );
        return false;
    });
    
    // 添加确认
    $("#btn_extra_ok").click( function() {
        $("#btn_extra_ok").attr("disabled", "disabled");
        var extra = $("#extra").val();
        if( ! extra ) {
            top.$.messager.warning("请输入新建的抬头信息。");
            $("#btn_extra_ok").attr("disabled", null );
            return false;
        } else {
            // 保存
            $.ajax({
                url: ksa.buildUrl( "/component/bd", "partner-alias-insert" ),
                data: { "partner.id" : $("#partner_id").combobox("getValue"), extra : extra },
                success: function( result ) {
                    try {
                        if (result.status == "success") { // 添加成功
                            parent.$.close( extra );
                            return false;
                        } 
                        else { $.messager.error( result.message ); $("#btn_extra_ok").attr("disabled", null ); }
                    } catch (e) { $("#btn_extra_ok").attr("disabled", null ); }
                }
            }); 
        }
    } );
    // 添加关闭
    $("#btn_extra_close").click( function() {
        $("#extra_window").window("close");
    } );
    
    // 单位别名
    var NEW_LINE = "\n";
    $.fn.datagrid.defaults.loadEmptyMsg = '<span class="label label-warning">注意</span> 没有获取到任何数据，请选择新的合作单位。';
    var $grid = $('#extra_grid').datagrid({
        title : '抬头信息：' + PARTNER_NAME,
        url: ksa.buildUrl( "/data/grid", "bd-partner-extra" ),
        pagination : false,
        queryParams : {
            id : $("#partner_id").combobox("getValue") 
        },
        fit : true,
        onDblClickRow : function( i, data ) {
            parent.$.close( data );
            return false;
        },
        columns:[[
              { field:'dump', checkbox:true },
              { field:'name', title:'抬头', width:200, formatter:function(v,data,i) {
                  var a = data;
                  try { while( a.indexOf( NEW_LINE ) >= 0 ) { a = a.replace( NEW_LINE, "<br/>" ); } return a; } 
                  catch(e) { return data; }
              } }
          ]],
          toolbar:[{
              text:'添加...',
              cls: 'btn-primary',
              iconCls:'icon-plus icon-white',
              handler:function(){
                  var id = $("#partner_id").combobox("getValue");
                  if( !id || id == "" ) {
                      top.$.messager.warning("请首先选择合作单位，再进行抬头信息的添加操作。");
                      return;
                  }
                  $("#extra_window").window("open");
                  $("#extra").val("");
                  try { $("#extra")[0].focus(); } catch(e){}
              }
          }, '-', {
              text:'删除',
              cls: 'btn-danger',
              iconCls:'icon-trash icon-white',
              handler:function(){ deleteExtra(); }
          }]
    });
    
    // 删除
    function deleteExtra() {
        var row = $grid.datagrid( "getSelected" );
        if( ! row ) {
            top.$.messager.warning("请选择一条数据后，再进行删除操作。");
            return;
        }
        
        $.messager.confirm( "确定删除所选抬头吗？", function( ok ){
            if( ok ) { 
                $.ajax({
                    url: ksa.buildUrl( "/component/bd", "partner-alias-delete" ),
                    data: { "partner.id" : $("#partner_id").combobox("getValue"), extra : $grid.datagrid("getSelected") },
                    success: function( result ) {
                        try {
                            if (result.status == "success") { 
                                $.messager.success( result.message );
                                $grid.datagrid( "reload" );
                            } 
                            else { $.messager.error( result.message ); }
                        } catch (e) { }
                    }
                }); 
            }
        } );
    }
});