/**
 * messager - jQuery EasyUI 1.3
 * 
 * Dependencies:
 *  draggable
 *  resizable
 *  panel
 *  window
 *  
 *   覆盖原生的 jquery.messager.js，以添加自定义扩展。
 * 
 *  <h3>扩展内容</h3>
 *  <ol>
 *      <li>显示 bootstrap 风格的信息窗口；</li>
 *  </ol>
 */
( function( $ ) {
    function showDialog( el, speed, timeout ) {
        var win = $( el ).window( "window" );
        if( !win ) {
            return;
        }
        win.show( speed );
        var timer = null;
        if( timeout > 0 ) {
            timer = setTimeout( function() {
                hideDialog( el, speed );
            }, timeout );
        }
        win.hover( function() {
            if( timer ) {
                clearTimeout( timer );
            }
        }, function() {
            if( timeout > 0 ) {
                timer = setTimeout( function() {
                    hideDialog( el, speed );
                }, timeout );
            }
        } );
    }
    ;
    function hideDialog( el, speed ) {
        if( el.locked == true ) {
            return;
        }
        el.locked = true;
        var win = $( el ).window( "window" );
        if( !win ) {
            return;
        }
        win.hide( speed );
        setTimeout( function() {
            $( el ).window( "destroy" );
        }, speed );
    }
    ;
    function createDialog( msg, title, type, buttons ) {
        var win = $( "<div></div>" ).appendTo( "body" );
        $( "<div class='messager-icon'></div>" ).addClass( type ).appendTo( win );        
        $( "<div class='messager-body'></div>" ).addClass( type ).appendTo( win )
            .append( title ? ("<b class='label label-" + type + "'>" + title + "</b> " + msg ) : msg );
        if( buttons ) {
            var tb = $( "<div class='messager-footer'></div>" ).appendTo( win );
            $( buttons ).each( function() {
                $(this).appendTo( tb );
            } );
        }
        return win;
    }
    ;
    function createButton( name, type, icon, handler ) {
        return $( "<button class='btn " + type + "'><i class='icon-white " + icon + "'></i> " + name + "</button>" )
            .bind( "click", eval( handler ) );
    }
    ;
    function alertDialog( win, title, fn ) {
        win.window( {
            title : title,
            noheader : ( title ? false : true ),
            width : 400,
            height : "auto",
            modal : true,
            collapsible : false,
            minimizable : false,
            maximizable : false,
            resizable : false,
            onClose : function() {
                // CUSTOM : 加入 enter 和 esc 键盘事件
                $(document).unbind("keydown", escClose );
                $(document).unbind("keydown", enterClose );
                
                setTimeout( function() {
                    win.window( "destroy" );
                }, 100 );
            }
        } );
        win.children( "div.messager-footer" ).children( "button:first" ).focus();
        
        // CUSTOM : 加入 enter 和 esc 键盘事件
        $(document).bind("keydown", "esc", escClose );
        $(document).bind("keydown", "enter", enterClose );
        function escClose( ) {
            win.window( "close" );
            if( fn ) fn( false );
        }
        function enterClose( ) {
            win.window( "close" );
            if( fn ) fn( true );
        }
    }
    ;
    $.messager = {
        show : function( msg, title, type, options ) {
            if( ! type ) { type = "info" }
            else if( type == "question" ) { return $.messager.confirm( msg, title, options.handler ); }
            var win = createDialog( msg, title, type );
            var opts = $.extend( {
                width : 300,
                showSpeed : 500,
                timeout : 3000
            }, options || {} );
            win.window( {
                noheader : true,
                width : opts.width,
                height : "auto",
                collapsible : false,
                minimizable : false,
                maximizable : false,
                shadow : false,
                draggable : false,
                resizable : false,
                closed : true,
                onBeforeOpen : function() {
                    showDialog( this, opts.showSpeed, opts.timeout );
                    return false;
                },
                onBeforeClose : function() {
                    hideDialog( this, opts.showSpeed );
                    return false;
                }
            } );
            win.window( "window" ).css( {
                left : "",
                top : "",
                right : 0,
                zIndex : $.fn.window.defaults.zIndex++,
                bottom : -document.body.scrollTop - document.documentElement.scrollTop
            } );
            win.window( "open" );
        },
        alert : function( msg, title, type, fn ) {
            if( ! type ) { type = "info" }
            else if( type == "question" ) { return $.messager.confirm( msg, title, fn ); }
            var button = createButton( $.messager.defaults.close, 'btn-danger', 'icon-off', function() {
                win.window( "close" );
                if( fn ) { fn(); return false; }
            } );
            var win = createDialog( msg, title, type, [ button ] );
            alertDialog( win, title, fn );
        },
        confirm : function( msg, title, fn ) {
            if( typeof title == "function" ) {
                fn = title;
                title = $.messager.defaults.types[ "question" ];
            }
            var btnOk = createButton( $.messager.defaults.ok, 'btn-primary', 'icon-ok', function() { win.window( "close" ); if( fn ) { fn( true ); return false; }  } );
            var btnCancel = createButton( $.messager.defaults.cancel, 'btn-danger', 'icon-remove', function() { win.window( "close" ); if( fn ) { fn( false ); return false; } } );
            var win = createDialog( msg, title, "question", [ btnOk, btnCancel ] );
            alertDialog( win, title, fn );
        },
        info : function( msg, title, fn ) {
            $.messager.display( msg, title, fn, "info" );
        },
        error : function( msg, title, fn ) {
            $.messager.display( msg, title, fn, "error" );
        },
        success : function( msg, title, fn ) {
            $.messager.display( msg, title, fn, "success" );
        },
        warning : function( msg, title, fn ) {
            $.messager.display( msg, title, fn, "warning" );
        },
        question : function( msg, title, fn ) {
            $.messager.confirm( msg, title, fn );
        },
        display : function( msg, title, fn, type ) {
            var modal = false;
            var t = ( typeof title == "string" ) ? title : $.messager.defaults.types[ type ];
            var handler = undefined;
            // 从后到前对类型进行测试 
            if( typeof fn == "function" ) {
                handler = fn;
                moal = true;
            } else if( typeof fn == "boolean" ) {
                modal = fn;
            }
            if( typeof title == "function" ) {
                handler = title;
                moal = true;
            } else if( typeof title == "boolean" ) {
                modal = title;
            }
            if( modal ) {
                $.messager.alert( msg, t, type, handler );
            } else {
                $.messager.show( msg, t, type );
            }
        }
    };
    $.messager.defaults = {
        ok : "确定",
        cancel : "取消",
        close : "关闭",
        types : {
            "info" : "提示",
            "warning" : "警告",
            "error" : "操作失败",
            "success" : "操作成功",
            "question" : "操作确认",
            "confirm" : "操作确认"
        }
    };
} )( jQuery );
