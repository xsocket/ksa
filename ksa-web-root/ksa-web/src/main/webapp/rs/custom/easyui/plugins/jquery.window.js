/**
 * window extension - jQuery EasyUI 1.3
 * 
 * Dependencies:
 *   panel
 *   draggable
 *   resizable
 *   
 *   覆盖原生的 jquery.window.js，以添加自定义扩展。
 * 
 *  <h3>扩展内容</h3>
 *  <ol>
 *      <li>拖拽时不能超出父窗体的界限；</li>
 *      <li>去除 shadow 属性，改用 css 的方式实现窗体边框的阴影效果；</li>
 *  </ol>
 */
( function( $ ) {
    function setSize( target, param ) {
        var opts = $.data( target, "window" ).options;
        if( param ) {
            if( param.width ) {
                opts.width = param.width;
            }
            if( param.height ) {
                opts.height = param.height;
            }
            if( param.left != null ) {
                opts.left = param.left;
            }
            if( param.top != null ) {
                opts.top = param.top;
            }
        }
        $( target ).panel( "resize", opts );
    }
    ;
    function move( target, param ) {
        var state = $.data( target, "window" );
        if( param ) {
            if( param.left != null ) {
                state.options.left = param.left;
            }
            if( param.top != null ) {
                state.options.top = param.top;
            }
        }
        $( target ).panel( "move", state.options );
    }
    ;
    function init( target ) {
        var state = $.data( target, "window" );
        var win = $( target ).panel( $.extend( {}, state.options, {
            border : false,
            doSize : true,
            closed : true,
            cls : "window",
            headerCls : "window-header",
            bodyCls : "window-body " + ( state.options.noheader ? "window-body-noheader" : "" ),
            onBeforeDestroy : function() {
                if( state.options.onBeforeDestroy.call( target ) == false ) {
                    return false;
                }
                if( state.mask ) {
                    state.mask.remove();
                }
            },
            onClose : function() {
                if( state.mask ) {
                    state.mask.hide();
                }
                state.options.onClose.call( target );
            },
            onOpen : function() {
                if( state.mask ) {
                    state.mask.css( {
                        display : "block",
                        zIndex : $.fn.window.defaults.zIndex++
                    } );
                }
                state.window.css( "z-index", $.fn.window.defaults.zIndex++ );
                state.options.onOpen.call( target );
            },
            onResize : function( width, height ) {
                var opts = $( target ).panel( "options" );
                state.options.width = opts.width;
                state.options.height = opts.height;
                state.options.left = opts.left;
                state.options.top = opts.top;
                state.options.onResize.call( target, width, height );
            },
            onMinimize : function() {
                if( state.mask ) {
                    state.mask.hide();
                }
                state.options.onMinimize.call( target );
            },
            onBeforeCollapse : function() {
                if( state.options.onBeforeCollapse.call( target ) == false ) {
                    return false;
                }
            },
            onExpand : function() {
                state.options.onExpand.call( target );
            }
        } ) );
        state.window = win.panel( "panel" );
        if( state.mask ) {
            state.mask.remove();
        }
        if( state.options.modal == true ) {
            state.mask = $( "<div class=\"window-mask\"></div>" ).insertAfter( state.window );
            state.mask.css( {
                width : ( state.options.inline ? state.mask.parent().width() : getPageArea().width ),
                height : ( state.options.inline ? state.mask.parent().height() : getPageArea().height ),
                display : "none"
            } );
        }
        if( state.options.left == null ) {
            var width = state.options.width;
            if( isNaN( width ) ) {
                width = state.window.outerWidth();
            }
            if( state.options.inline ) {
                var parent = state.window.parent();
                state.options.left = ( parent.width() - width ) / 2 + parent.scrollLeft();
            } else {
                state.options.left = ( $( window ).width() - width ) / 2 + $( document ).scrollLeft();
            }
        }
        if( state.options.top == null ) {
            var height = state.window.height;
            if( isNaN( height ) ) {
                height = state.window.outerHeight();
            }
            if( state.options.inline ) {
                var parent = state.window.parent();
                state.options.top = ( parent.height() - height ) / 2 + parent.scrollTop();
            } else {
                state.options.top = ( $( window ).height() - height ) / 2 + $( document ).scrollTop();
            }
        }
        move( target );
        if( state.options.closed == false ) {
            win.window( "open" );
        }
    }
    ;
    function setProperties( target ) {
        var state = $.data( target, "window" );
        state.window.draggable( {
            handle : ">div.panel-header>div.panel-title",
            disabled : state.options.draggable == false,
            onStartDrag : function( e ) {
                if( state.mask ) {
                    state.mask.css( "z-index", $.fn.window.defaults.zIndex++ );
                }
                state.window.css( "z-index", $.fn.window.defaults.zIndex++ );
                if( !state.proxy ) {
                    state.proxy = $( "<div class=\"window-proxy\"></div>" ).insertAfter( state.window );
                }
                state.proxy.css( {
                    display : "none",
                    zIndex : $.fn.window.defaults.zIndex++,
                    left : e.data.left,
                    top : e.data.top
                } );
                state.proxy._outerWidth( state.window.outerWidth() );
                state.proxy._outerHeight( state.window.outerHeight() );
                setTimeout( function() {
                    if( state.proxy ) {
                        state.proxy.show();
                    }
                }, 500 );
            },            
            onDrag : function( e ) {
                /* CUSTOM : 拖拽时不能超出父窗体的界限。 */
                // 取消拖拽限制
                /*  var parent = state.options.inline ? state.window.parent() : $( window );
                var leftLimit = parent.width() - state.window.outerWidth();
                var topLimit = parent.height() - state.window.outerHeight(); */
                state.proxy.css( {
                    display : "block",
                    left : e.data.left /* < 0 ? 0 : ( e.data.left > leftLimit ? leftLimit : e.data.left ) */,
                    top : e.data.top < 0 ? 0 : e.data.top /* < 0 ? 0 : ( e.data.top > topLimit ? topLimit : e.data.top ) */
                } );
                return false;
            },
            onStopDrag : function( e ) {                
                /* var parent = state.options.inline ? state.window.parent() : $( window );
                var leftLimit = parent.width() - state.window.outerWidth();
                var topLimit = parent.height() - state.window.outerHeight(); */
                state.options.left = e.data.left /* < 0 ? 0 : ( e.data.left > leftLimit ? leftLimit : e.data.left )  */;
                state.options.top = e.data.top < 0 ? 0 : e.data.top /* < 0 ? 0 : ( e.data.top > topLimit ? topLimit : e.data.top )  */;
                $( target ).window( "move" );
                state.proxy.remove();
                state.proxy = null;
            }
        } );
        state.window.resizable( {
            disabled : state.options.resizable == false,
            onStartResize : function( e ) {
                state.pmask = $( "<div class=\"window-proxy-mask\"></div>" ).insertAfter( state.window );
                state.pmask.css( {
                    zIndex : $.fn.window.defaults.zIndex++,
                    left : e.data.left,
                    top : e.data.top,
                    width : state.window.outerWidth(),
                    height : state.window.outerHeight()
                } );
                if( !state.proxy ) {
                    state.proxy = $( "<div class=\"window-proxy\"></div>" ).insertAfter( state.window );
                }
                state.proxy.css( {
                    zIndex : $.fn.window.defaults.zIndex++,
                    left : e.data.left,
                    top : e.data.top
                } );
                state.proxy._outerWidth( e.data.width );
                state.proxy._outerHeight( e.data.height );
            },
            onResize : function( e ) {
                state.proxy.css( {
                    left : e.data.left,
                    top : e.data.top
                } );
                state.proxy._outerWidth( e.data.width );
                state.proxy._outerHeight( e.data.height );
                return false;
            },
            onStopResize : function( e ) {
                state.options.left = e.data.left;
                state.options.top = e.data.top;
                state.options.width = e.data.width;
                state.options.height = e.data.height;
                setSize( target );
                state.pmask.remove();
                state.pmask = null;
                state.proxy.remove();
                state.proxy = null;
            }
        } );
    }
    ;
    function getPageArea() {
        if( document.compatMode == "BackCompat" ) {
            return {
                width : Math.max( document.body.scrollWidth, document.body.clientWidth ),
                height : Math.max( document.body.scrollHeight, document.body.clientHeight )
            };
        } else {
            return {
                width : Math.max( document.documentElement.scrollWidth, document.documentElement.clientWidth ),
                height : Math.max( document.documentElement.scrollHeight, document.documentElement.clientHeight )
            };
        }
    }
    ;
    $( window ).resize( function() {
        $( "body>div.window-mask" ).css( {
            width : $( window ).width(),
            height : $( window ).height()
        } );
        setTimeout( function() {
            $( "body>div.window-mask" ).css( {
                width : getPageArea().width,
                height : getPageArea().height
            } );
        }, 50 );
    } );
    $.fn.window = function( options, param ) {
        if( typeof options == "string" ) {
            var method = $.fn.window.methods[options];
            if( method ) {
                return method( this, param );
            } else {
                return this.panel( options, param );
            }
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "window" );
            if( state ) {
                $.extend( state.options, options );
            } else {
                state = $.data( this, "window", {
                    options : $.extend( {}, $.fn.window.defaults, $.fn.window.parseOptions( this ), options )
                } );
                if( !state.options.inline ) {
                    $( this ).appendTo( "body" );
                }
            }
            init( this );
            setProperties( this );
        } );
    };
    $.fn.window.methods = {
        options : function( jq ) {
            var opts = jq.panel( "options" );
            var options = $.data( jq[0], "window" ).options;
            return $.extend( options, {
                closed : opts.closed,
                collapsed : opts.collapsed,
                minimized : opts.minimized,
                maximized : opts.maximized
            } );
        },
        window : function( jq ) {
            return $.data( jq[0], "window" ).window;
        },
        resize : function( jq, param ) {
            return jq.each( function() {
                setSize( this, param );
            } );
        },
        move : function( jq, param ) {
            return jq.each( function() {
                move( this, param );
            } );
        }
    };
    $.fn.window.parseOptions = function( target ) {
        return $.extend( {}, $.fn.panel.parseOptions( target ), $.parser.parseOptions( target, [{
            draggable : "boolean",
            resizable : "boolean",
            modal : "boolean",
            inline : "boolean"
        }] ) );
    };
    $.fn.window.defaults = $.extend( {}, $.fn.panel.defaults, {
        zIndex : 9000,
        draggable : true,
        resizable : true,
        modal : false,
        inline : false,
        title : "New Window",
        collapsible : true,
        minimizable : true,
        maximizable : true,
        closable : true,
        closed : false
    } );
} )( jQuery );

( function( $ ) {
    
    // var _windowStack = [];
    var _windowMap = {};
    
    var defaultSettings = {
        collapsible : false,
        minimizable : false,
        maximizable : true,
        resizable : false,
        closable : true,
        modal : true,
        iframe : true,
        iconCls : "c-icon-logo",
        width : 800,
        height : 600,
        src : "",
        title : "无标题"
    };
    /**
     * 自定义的 window 打开快捷方式
     *  setting : 是 window 打开的基本设置
     */
    $.open = function( setting, callback ) {
        
        if( typeof setting=="string" ) {
            setting = { src : setting };
        }
        var sets = $.extend( {}, defaultSettings, setting || {} );
        var $onclose = !!sets["onClose"] ? sets["onClose"] : function(){ };
        var $ondestroy = !!sets["onDestroy"] ? sets["onDestroy"] : function(){ };
        $.extend(sets, {
            onClose : function() { 
                $onclose();
                $( this ).window( "destroy" );
             },
             onDestroy : function() {
                 $ondestroy();
                 _windowMap[ $(this).attr("id") ] = null;
                 // _windowStack.pop();
             }
        });
        var randomId = parseInt( Math.random() * new Date().getTime() ) + "";
        var win = $("<div id='" + randomId +"' class='_ksa_win'/>").attr("title", sets.title).css({
            overflow : "hidden"
        }).appendTo( $("body") );
        if (sets.src) {
            $("<div/>").appendTo( win ).panel({
                href : sets.src,
                iframe : sets.iframe,
                border : false,
                fit : true
            });
        }
        win.callback = callback;
        _windowMap[ randomId ] = win.window(sets);
        // _windowStack.push( win.window(sets) );
    }
    
    /** 关闭窗口，返回 returnData。 */
    $.close = function( returnData ) {
        var $win = $.currentWindow();
        if( $win != null ) {
            try {
                if ( !!$win["callback"] ) { /** 执行返回数据回调方法 */ $win.callback( returnData || {} ); }
            } catch (e) {}
            $win.window( "close" );
        }
        /* 改变关闭窗口的方式，解决不进行 modal iframe 窗口关闭的顺序问题
        if( _windowStack.length > 0 ) {
            var $win = _windowStack[ _windowStack.length - 1 ];
            try {
                if ( !!$win["callback"] ) {  $win.callback( returnData || {} ); }
            } catch (e) {}
            $win.window( "close" );
        }*/
    }
    
    $.currentWindow = function() {
        /*if( _windowStack.length > 0 ) {
            return _windowStack[ _windowStack.length - 1 ];
        }
        return null;*/
        var $cwin = null;
        var $windows = $("div._ksa_win");
        if( $windows.length > 0 ) {
            $cwin = $( $windows[0] );
            $cwin.zindex = parseInt( $cwin.parent().css("z-index") );
            for( var i = 1; i < $windows.length; i++ ) {
                $win = $( $windows[i] );
                $win.zindex = parseInt( $win.parent().css("z-index") );
                if( $win.zindex > $cwin.zindex ) { $cwin = $win; }
            }
            return _windowMap[ $cwin.attr("id") ];
        }
        return null;
    }
} )( jQuery );