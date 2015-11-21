(function($){
    var applicationContext = window.APPLICATION_CONTEXTPATH || "";
    /** 构建 url，namespace：命名空间，action：操作名称，parameters：参数 */
    function buildUrl( namespace, action, parameters ) {
        if ( !namespace || !action ) { return null; }
        var url = applicationContext + namespace + "/" + action + getURLExtension();

        var pString = "";
        if ( parameters ) {
            var pv = [];
            for (key in parameters) {
                var value = parameters[key];
                if (!value) { continue; }
                pv.push(key + "=" + value);
            }
            pString = "?" + encodeURI( pv.join("&") );
            if (pString == "?") { pString = ""; }
        }
        return url + pString;
    }
    
    /** 获取 url 链接的后缀 */
    function getURLExtension(){
        try {
            var url = window.location.pathname;
            if (url.lastIndexOf(".") != -1) {
                return url.substring(url.lastIndexOf("."));
            }
        } catch (ex) {/* DO NOTHING */}
        return ".action";
    }
    
    /* KSA */
    window.ksa = window.ksa || {};
    $.extend( ksa, {
        // 构建 url 链接
        buildUrl : buildUrl
    });
    
    /* KSA - menu */
    ksa.menu = ksa.menu || {};
    $.extend( ksa.menu, {
        // 激活菜单项
        activate : function( obj ) {
            $(obj).addClass("active");
        }, 
        // 取消激活
        deactivate : function( obj ) {
            $(obj).removeClass("active");
        }
    });
    
    /* KSA - utils */
    ksa.utils = ksa.utils || {};
    $.extend( ksa.utils, {
        // 格式化文件大小
        fileSizeFormatter : function( v ) {
            var units = [ "B", "KB", "MB", "GB" ];
            var i = 0;
            while( v > 1024 && i < units.length ) {
                v = Math.ceil(v/1024);
                i++;
            }
            return v +" " + units[i];
        }, 
        dateFormatter : function( v ) {
            if( !v ) { return ""; }
            try { var d = new Date( v );
                if( d.getDate() + "" == "NaN" ) {
                    while( v.indexOf("-") >= 0 ) {
                        v = v.replace( "-", "/" );
                    }
                    d = new Date( v );
                }
                return d.getFullYear() + "-" + ( d.getMonth() + 1 ) + "-" + d.getDate();
            } catch( e ) { return v; }
        }, 
        parseDate : function( v ) {
            var d = new Date( v );
            if( d.getDate() + "" == "NaN" ) {
                while( v.indexOf("-") >= 0 ) {
                    v = v.replace( "-", "/" );
                }
                d = new Date( v );
            }
            return d;
        }
    } );
    
    /* KSA - hotkey */
    ksa.hotkey = ksa.hotkey || {};
    $.extend( ksa.hotkey, {
        // 按键点击类型 'keydown', 'keyup', 'keypress'
        type : "keydown",
        
        // 绑定按钮快键点击事件
        bindButton : function( btn, key ) {
            $( btn ).each( function() {
                var myKey = key;
                if( !myKey ) {
                    var patterns = ( /.*\((.*)\)/i ).exec( $( this ).text() );
                    if( patterns ) { myKey = patterns[ 1 ]; }
                }
                if( myKey ) {
                    var myBtn = $( this );
                    $(document).bind( ksa.hotkey.type, myKey, function(){ myBtn.click(); } );
                }
            } );
        },
        // 绑定表格 数据按钮选中行 的功能
        bindDatagrid : function( grid ) {
            $(document).bind( ksa.hotkey.type, '1 2 3 4 5 6 7 8 9', function( evt ) {
                // 选中相应的行
                $(grid).datagrid("selectRow", evt.keyCode - 49);
            } );
            $(document).bind( ksa.hotkey.type, 'up', function( evt ){
                var count = $( grid ).datagrid("getRows").length;
                if( count > 0 ) {
                    
                }
                $(grid).datagrid("selectRow", evt.keyCode - 49);
            } );
        }
    });
    
})(jQuery);

(function($){
    $(function(){
        /* struts2 扩展 */
        setTimeout(function() {
            $(".alert.alert-success.actionMessage,.alert.alert-error.errorMessage").remove();
        }, 5000);   // 5秒后自动关闭 struts2 消息
        
        // 按钮样式
        $("button.btn").addClass("btn-small");
    });
})(jQuery);