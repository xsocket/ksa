$(function(){
    // 初始化 tabs
    var $tabs = $("#container").tabs({
        height: $(window).height() - 50
    });
    // 激活菜单
    ksa.menu.activate( "#_sidebar_bd_currency" );
});