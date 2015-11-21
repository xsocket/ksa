/**
 * dialog - jQuery EasyUI 1.3
 * 
 * Licensed under the GPL terms To use it on other terms please contact us
 * 
 * Copyright(c) 2009-2012 stworthy [ stworthy@gmail.com ]
 * 
 * Dependencies:
 *       window
 *       linkbutton
 */
( function( $ ) {
    function wrapDialog( target ) {
        var t = $( target );
        t.wrapInner( "<div class=\"dialog-content\"></div>" );
        var contentPanel = t.children( "div.dialog-content" );
        contentPanel.attr( "style", t.attr( "style" ) );
        t.removeAttr( "style" ).css( "overflow", "hidden" );
        contentPanel.panel( {
            border : false,
            doSize : false
        } );
        return contentPanel;
    }
    ;
    function buildDialog( target ) {
        var opts = $.data( target, "dialog" ).options;
        var contentPanel = $.data( target, "dialog" ).contentPanel;
        if( opts.toolbar ) {
            if( typeof opts.toolbar == "string" ) {
                $( opts.toolbar ).addClass( "dialog-toolbar" ).prependTo( target );
                $( opts.toolbar ).show();
            } else {
                $( target ).find( "div.dialog-toolbar" ).remove();
                var toolbar = $( "<div class=\"dialog-toolbar\"></div>" ).prependTo( target );
                for( var i = 0; i < opts.toolbar.length; i++ ) {
                    var p = opts.toolbar[i];
                    if( p == "-" ) {
                        toolbar.append( "<div class=\"dialog-tool-separator\"></div>" );
                    } else {
                        var tool = $( "<a href=\"javascript:void(0)\"></a>" ).appendTo( toolbar );
                        tool.css( "float", "left" );
                        tool[0].onclick = eval( p.handler || function() {
                        } );
                        tool.linkbutton( $.extend( {}, p, {
                            plain : true
                        } ) );
                    }
                }
                toolbar.append( "<div style=\"clear:both\"></div>" );
            }
        } else {
            $( target ).find( "div.dialog-toolbar" ).remove();
        }
        if( opts.buttons ) {
            if( typeof opts.buttons == "string" ) {
                $( opts.buttons ).addClass( "dialog-button" ).appendTo( target );
                $( opts.buttons ).show();
            } else {
                $( target ).find( "div.dialog-button" ).remove();
                var buttons = $( "<div class=\"dialog-button\"></div>" ).appendTo( target );
                for( var i = 0; i < opts.buttons.length; i++ ) {
                    var p = opts.buttons[i];
                    var button = $( "<a href=\"javascript:void(0)\"></a>" ).appendTo( buttons );
                    if( p.handler ) {
                        button[0].onclick = p.handler;
                    }
                    button.linkbutton( p );
                }
            }
        } else {
            $( target ).find( "div.dialog-button" ).remove();
        }
        var href = opts.href;
        var content = opts.content;
        opts.href = null;
        opts.content = null;
        contentPanel.panel( {
            closed : opts.closed,
            cache : opts.cache,
            href : href,
            content : content,
            onLoad : function() {
                if( opts.height == "auto" ) {
                    $( target ).window( "resize" );
                }
                opts.onLoad.apply( target, arguments );
            }
        } );
        $( target ).window(
                $.extend( {}, opts, {
                    onOpen : function() {
                        contentPanel.panel( "open" );
                        if( opts.onOpen ) {
                            opts.onOpen.call( target );
                        }
                    },
                    onResize : function( width, height ) {
                        var wbody = $( target ).panel( "panel" ).find( ">div.panel-body" );
                        contentPanel.panel( "panel" ).show();
                        contentPanel.panel( "resize", {
                            width : wbody.width(),
                            height : ( height == "auto" ) ? "auto" : wbody.height() - wbody.find( ">div.dialog-toolbar" ).outerHeight()
                                    - wbody.find( ">div.dialog-button" ).outerHeight()
                        } );
                        if( opts.onResize ) {
                            opts.onResize.call( target, width, height );
                        }
                    }
                } ) );
        opts.href = href;
        opts.content = content;
    }
    ;
    function refresh( target, href ) {
        var contentPanel = $.data( target, "dialog" ).contentPanel;
        contentPanel.panel( "refresh", href );
    }
    ;
    $.fn.dialog = function( options, param ) {
        if( typeof options == "string" ) {
            var method = $.fn.dialog.methods[options];
            if( method ) {
                return method( this, param );
            } else {
                return this.window( options, param );
            }
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "dialog" );
            if( state ) {
                $.extend( state.options, options );
            } else {
                $.data( this, "dialog", {
                    options : $.extend( {}, $.fn.dialog.defaults, $.fn.dialog.parseOptions( this ), options ),
                    contentPanel : wrapDialog( this )
                } );
            }
            buildDialog( this );
        } );
    };
    $.fn.dialog.methods = {
        options : function( jq ) {
            var options = $.data( jq[0], "dialog" ).options;
            var opts = jq.panel( "options" );
            $.extend( options, {
                closed : opts.closed,
                collapsed : opts.collapsed,
                minimized : opts.minimized,
                maximized : opts.maximized
            } );
            return options;
        },
        dialog : function( jq ) {
            return jq.window( "window" );
        },
        refresh : function( jq, href ) {
            return jq.each( function() {
                refresh( this, href );
            } );
        }
    };
    $.fn.dialog.parseOptions = function( target ) {
        return $.extend( {}, $.fn.window.parseOptions( target ), $.parser.parseOptions( target, ["toolbar", "buttons"] ) );
    };
    $.fn.dialog.defaults = $.extend( {}, $.fn.window.defaults, {
        title : "New Dialog",
        collapsible : false,
        minimizable : false,
        maximizable : false,
        resizable : false,
        toolbar : null,
        buttons : null
    } );
} )( jQuery );
