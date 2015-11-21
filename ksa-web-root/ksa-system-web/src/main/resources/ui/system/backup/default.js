function restore(name, btn) {
    $.messager.confirm( "确定从数据备份文件 '" + name + "' 中还原数据库吗？", function( ok ){
        if( ok ) { 
            $(btn).toggleClass("loading").attr("disabled", true);
            $("span", btn).text("还原中...");
            $.ajax({
                url: ksa.buildUrl( "/ui/system/backup", "restore" ),
                data: { filename : name },
                success: function( result ) {
                    try {
                        if (result.status == "success") { 
                            $.messager.success( result.message );
                        } 
                        else { $.messager.error( result.message, "数据还原失败" ); }
                    } catch (e) { }
                    $(btn).removeClass("loading").attr("disabled", false);
                    $("span", btn).text("还原");
                }
            }); 
        }
    } );
}
function delBackup(name) {
    $.messager.confirm( "确定删除数据备份文件 '" + name + "' 吗？", function( ok ){
        if( ok ) { 
            $.ajax({
                url: ksa.buildUrl( "/ui/system/backup", "delete" ),
                data: { filename : name },
                success: function( result ) {
                    try {
                        if (result.status == "success") { 
                            $.messager.success( result.message );
                            $('#data_grid').datagrid( "reload" );
                        } 
                        else { $.messager.error( result.message ); }
                    } catch (e) { }
                }
            }); 
        }
    } );
}
$(function() {
    $("#data_container").height( $(window).height() - 105 );
    
    var $grid = $('#data_grid').datagrid({
        url : ksa.buildUrl( "/data/system/backup", "query" ),
        fit: true,
        pageSize: 20,
        singleSelect: true,
        fitColumns: false,
        columns:[[
            { field:'name', title:'备份文件名称', width:200,  sortable:false, align:"center" },
            { field:'size', title:'文件大小', width:100,   sortable:false, align:"center",
                formatter : function(v) {
                    return ksa.utils.fileSizeFormatter( v );
                } },
            { field:'lastModified',     title:'创建时间', width:200, sortable:false, align:"center",
                formatter : function(v) { return v.replace("T", " "); } },
            { field:'test',     title:'操作', width:200, sortable:false, align:"center",
                    formatter : function(v, data) {
                        return '<a href="download.action?filename='+data.name+'" class="btn btn-mini btn-success"><i class="icon-white icon-download-alt"></i> <span>下载</span></a>'
                        + ' <button onclick="restore(\''+data.name+'\', this);" class="btn btn-mini btn-warning"><i class="icon-white icon-repeat"></i> <span>还原</span></button>'
                        + ' <button onclick="delBackup(\''+data.name+'\');" class="btn btn-mini btn-danger"><i class="icon-white icon-trash"></i> <span>删除</span></button>';
                } }
        ]]
    });
    
    // 业务信息查看
    $("#btn_backup").click( function() {        
        $(this).toggleClass("loading").attr("disabled", true);
        $("#btn_backup span").text("数据备份中...");
        $.ajax({
            url: ksa.buildUrl( "/ui/system/backup", "backup" ),
            success: function( result ) {
                try {
                    if (result.status == "success") { 
                        $.messager.success( result.message );
                        $grid.datagrid( "reload" );
                    } 
                    else { $.messager.error( result.message, "数据备份失败" ); }
                } catch (e) { }
                $("#btn_backup").removeClass("loading").attr("disabled", false);
                $("#btn_backup span").text("数据备份");
            }
        }); 
    });
    
 // 业务信息查看
    $("#btn_strategy").click( function() {        
        $.open({
            title:"设置备份策略",
            iconCls : "icon-wrench",
            src : ksa.buildUrl( "/dialog/system/backup/strategy", "view" ),
            width:500,
            height:400
        }, function(){
            $grid.datagrid( "reload" );
        });
    });
    
    
    // 绑定热键事件
    ksa.hotkey.bindButton( $(".tool-bar button.btn") );
    
});