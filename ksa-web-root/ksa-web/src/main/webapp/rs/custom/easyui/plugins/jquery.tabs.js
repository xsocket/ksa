/**
 * tabs extension - jQuery EasyUI 1.3
 * 
 * 覆盖原生的 jquery.tabs.js，以添加自定义扩展。
 * 
 * <h3>扩展内容</h3>
 *  <ol>
 *      <li>新增 onBeforeSelect 事件，如果返回 false 则阻止 tab 页被选中</li>
 *  </ol>
 * 
 */
( function( $ ) {
    function getMaxScrollWidth( container ) {
        var header = $( container ).children( "div.tabs-header" );
        var tabsWidth = 0;
        $( "ul.tabs li", header ).each( function() {
            tabsWidth += $( this ).outerWidth( true );
        } );
        var wrapWidth = header.children( "div.tabs-wrap" ).width();
        var padding = parseInt( header.find( "ul.tabs" ).css( "padding-left" ) );
        return tabsWidth - wrapWidth + padding;
    }
    ;
    function setScrollers( container ) {
        var opts = $.data( container, "tabs" ).options;
        var header = $( container ).children( "div.tabs-header" );
        var tool = header.children( "div.tabs-tool" );
        var leftScroller = header.children( "div.tabs-scroller-left" );
        var rightScroller = header.children( "div.tabs-scroller-right" );
        var wrap = header.children( "div.tabs-wrap" );
        tool._outerHeight( header.outerHeight() - ( opts.plain ? 2 : 0 ) );
        var height = 0;
        $( "ul.tabs li", header ).each( function() {
            height += $( this ).outerWidth( true );
        } );
        var realWidth = header.width() - tool.outerWidth();
        if( height > realWidth ) {
            leftScroller.show();
            rightScroller.show();
            tool.css( "right", rightScroller.outerWidth() );
            wrap.css( {
                marginLeft : leftScroller.outerWidth(),
                marginRight : rightScroller.outerWidth() + tool.outerWidth(),
                left : 0,
                width : realWidth - leftScroller.outerWidth() - rightScroller.outerWidth()
            } );
        } else {
            leftScroller.hide();
            rightScroller.hide();
            tool.css( "right", 0 );
            wrap.css( {
                marginLeft : 0,
                marginRight : tool.outerWidth(),
                left : 0,
                width : realWidth
            } );
            wrap.scrollLeft( 0 );
        }
    }
    ;
    function buildButton( container ) {
        var opts = $.data( container, "tabs" ).options;
        var header = $( container ).children( "div.tabs-header" );
        if( opts.tools ) {
            if( typeof opts.tools == "string" ) {
                $( opts.tools ).addClass( "tabs-tool" ).appendTo( header );
                $( opts.tools ).show();
            } else {
                header.children( "div.tabs-tool" ).remove();
                var _15 = $( "<div class=\"tabs-tool\"></div>" ).appendTo( header );
                for( var i = 0; i < opts.tools.length; i++ ) {
                    var _16 = $( "<a href=\"javascript:void(0);\"></a>" ).appendTo( _15 );
                    _16[0].onclick = eval( opts.tools[i].handler || function() {
                    } );
                    _16.linkbutton( $.extend( {}, opts.tools[i], {
                        plain : true
                    } ) );
                }
            }
        } else {
            header.children( "div.tabs-tool" ).remove();
        }
    }
    ;
    function setSize( container ) {
        var opts = $.data( container, "tabs" ).options;
        var cc = $( container );
        if( opts.fit == true ) {
            var p = cc.parent();
            p.addClass( "panel-noscroll" );
            if( p[0].tagName == "BODY" ) {
                $( "html" ).addClass( "panel-fit" );
            }
            opts.width = p.width();
            opts.height = p.height();
        }
        cc.width( opts.width ).height( opts.height );
        var header = $( container ).children( "div.tabs-header" );
        header._outerWidth( opts.width );
        setScrollers( container );
        var panels = $( container ).children( "div.tabs-panels" );
        var height = opts.height;
        if( !isNaN( height ) ) {
            panels._outerHeight( height - header.outerHeight() );
        } else {
            panels.height( "auto" );
        }
        var width = opts.width;
        if( !isNaN( width ) ) {
            panels._outerWidth( width );
        } else {
            panels.width( "auto" );
        }
    }
    ;
    function fitContent( container ) {
        var opts = $.data( container, "tabs" ).options;
        var tab = getSelected( container );
        if( tab ) {
            var panel = $( container ).children( "div.tabs-panels" );
            var width = opts.width == "auto" ? "auto" : panel.width();
            var height = opts.height == "auto" ? "auto" : panel.height();
            tab.panel( "resize", {
                width : width,
                height : height
            } );
        }
    }
    ;
    function wrapTabs( container ) {
        var tabs = $.data( container, "tabs" ).tabs;
        var cc = $( container );
        cc.addClass( "tabs-container" );
        cc.wrapInner( "<div class=\"tabs-panels\"/>" );
        $("<div class=\"tabs-header\">" + "<div class=\"tabs-scroller-left\"></div>"
                + "<div class=\"tabs-scroller-right\"></div>" + "<div class=\"tabs-wrap\">"
                + "<ul class=\"tabs\"></ul>" + "</div>" + "</div>" ).prependTo( container );
        cc.children( "div.tabs-panels" ).children( "div" ).each( function( i ) {
            var opts = $.extend( {}, $.parser.parseOptions( this ), {
                selected : ( $( this ).attr( "selected" ) ? true : undefined )
            } );
            var pp = $( this );
            tabs.push( pp );
            createTab( container, pp, opts );
        } );
        cc.children( "div.tabs-header" ).find( ".tabs-scroller-left, .tabs-scroller-right" ).hover( function() {
            $( this ).addClass( "tabs-scroller-over" );
        }, function() {
            $( this ).removeClass( "tabs-scroller-over" );
        } );
        cc.bind( "_resize", function( e, param ) {
            var opts = $.data( container, "tabs" ).options;
            if( opts.fit == true || param ) {
                setSize( container );
                fitContent( container );
            }
            return false;
        } );
    }
    ;
    function setProperties( container ) {
        var opts = $.data( container, "tabs" ).options;
        var header = $( container ).children( "div.tabs-header" );
        var panels = $( container ).children( "div.tabs-panels" );
        if( opts.plain == true ) {
            header.addClass( "tabs-header-plain" );
        } else {
            header.removeClass( "tabs-header-plain" );
        }
        if( opts.border == true ) {
            header.removeClass( "tabs-header-noborder" );
            panels.removeClass( "tabs-panels-noborder" );
        } else {
            header.addClass( "tabs-header-noborder" );
            panels.addClass( "tabs-panels-noborder" );
        }
        $( ".tabs-scroller-left", header ).unbind( ".tabs" ).bind( "click.tabs", function() {
            var wrap = $( ".tabs-wrap", header );
            var pos = wrap.scrollLeft() - opts.scrollIncrement;
            wrap.animate( {
                scrollLeft : pos
            }, opts.scrollDuration );
        } );
        $( ".tabs-scroller-right", header ).unbind( ".tabs" ).bind( "click.tabs", function() {
            var wrap = $( ".tabs-wrap", header );
            var pos = Math.min( wrap.scrollLeft() + opts.scrollIncrement, getMaxScrollWidth( container ) );
            wrap.animate( {
                scrollLeft : pos
            }, opts.scrollDuration );
        } );
    }
    ;
    function createTab( container, pp, options ) {
        var tabs = $.data( container, "tabs" );
        options = options || {};
        pp.panel( $.extend( {}, options, {
            border : false,
            noheader : true,
            closed : true,
            doSize : false,
            iconCls : ( options.icon ? options.icon : undefined ),
            onLoad : function() {
                if( options.onLoad ) {
                    options.onLoad.call( this, arguments );
                }
                tabs.options.onLoad.call( container, $( this ) );
            }
        } ) );
        var opts = pp.panel( "options" );
        var header = $( container ).children( "div.tabs-header" ).find( "ul.tabs" );
        function _38( li ) {
            return header.find( "li" ).index( li );
        }
        ;
        opts.tab = $( "<li></li>" ).appendTo( header );
        opts.tab.unbind( ".tabs" ).bind( "click.tabs", function() {
            if( $( this ).hasClass( "tabs-disabled" ) ) {
                return;
            }
            selectTab( container, _38( this ) );
        } ).bind( "contextmenu.tabs", function( e ) {
            if( $( this ).hasClass( "tabs-disabled" ) ) {
                return;
            }
            tabs.options.onContextMenu.call( container, e, $( this ).find( "span.tabs-title" ).html(), _38( this ) );
        } );
        var tabInner = $( "<a href=\"javascript:void(0)\" class=\"tabs-inner\"></a>" ).appendTo( opts.tab );
        var tabTitle = $( "<span class=\"tabs-title\"></span>" ).html( opts.title ).appendTo( tabInner );
        var tabIcon = $( "<span class=\"tabs-icon\"></span>" ).appendTo( tabInner );
        if( opts.closable ) {
            tabTitle.addClass( "tabs-closable" );
            var _3c = $( "<a href=\"javascript:void(0)\" class=\"tabs-close\"></a>" ).appendTo( opts.tab );
            _3c.unbind( ".tabs" ).bind( "click.tabs", function() {
                if( $( this ).parent().hasClass( "tabs-disabled" ) ) {
                    return;
                }
                closeTab( container, _38( $( this ).parent() ) );
                return false;
            } );
        }
        if( opts.iconCls ) {
            tabTitle.addClass( "tabs-with-icon" );
            tabIcon.addClass( opts.iconCls );
        }
        if( opts.tools ) {
            var _3e = $( "<span class=\"tabs-p-tool\"></span>" ).insertAfter( tabInner );
            if( typeof opts.tools == "string" ) {
                $( opts.tools ).children().appendTo( _3e );
            } else {
                for( var i = 0; i < opts.tools.length; i++ ) {
                    var t = $( "<a href=\"javascript:void(0)\"></a>" ).appendTo( _3e );
                    t.addClass( opts.tools[i].iconCls );
                    if( opts.tools[i].handler ) {
                        t.bind( "click", eval( opts.tools[i].handler ) );
                    }
                }
            }
            var pr = _3e.children().length * 12;
            if( opts.closable ) {
                pr += 8;
            } else {
                pr -= 3;
                _3e.css( "right", "5px" );
            }
            tabTitle.css( "padding-right", pr + "px" );
        }
    }
    ;
    function addTab( container, options ) {
        var opts = $.data( container, "tabs" ).options;
        var tabs = $.data( container, "tabs" ).tabs;
        if( options.selected == undefined ) {
            options.selected = true;
        }
        var pp = $( "<div></div>" ).appendTo( $( container ).children( "div.tabs-panels" ) );
        tabs.push( pp );
        createTab( container, pp, options );
        opts.onAdd.call( container, options.title, tabs.length - 1 );
        setScrollers( container );
        if( options.selected ) {
            selectTab( container, tabs.length - 1 );
        }
    }
    ;
    function update( container, param ) {
        var selectHis = $.data( container, "tabs" ).selectHis;
        var pp = param.tab;
        var title = pp.panel( "options" ).title;
        pp.panel( $.extend( {}, param.options, {
            iconCls : ( param.options.icon ? param.options.icon : undefined )
        } ) );
        var opts = pp.panel( "options" );
        var tab = opts.tab;
        tab.find( "span.tabs-icon" ).attr( "class", "tabs-icon" );
        tab.find( "a.tabs-close" ).remove();
        tab.find( "span.tabs-title" ).html( opts.title );
        if( opts.closable ) {
            tab.find( "span.tabs-title" ).addClass( "tabs-closable" );
            $( "<a href=\"javascript:void(0)\" class=\"tabs-close\"></a>" ).appendTo( tab );
        } else {
            tab.find( "span.tabs-title" ).removeClass( "tabs-closable" );
        }
        if( opts.iconCls ) {
            tab.find( "span.tabs-title" ).addClass( "tabs-with-icon" );
            tab.find( "span.tabs-icon" ).addClass( opts.iconCls );
        } else {
            tab.find( "span.tabs-title" ).removeClass( "tabs-with-icon" );
        }
        if( title != opts.title ) {
            for( var i = 0; i < selectHis.length; i++ ) {
                if( selectHis[i] == title ) {
                    selectHis[i] = opts.title;
                }
            }
        }
        setProperties( container );
        $.data( container, "tabs" ).options.onUpdate.call( container, opts.title, getTabIndex( container, pp ) );
    }
    ;
    function closeTab( container, which ) {
        var opts = $.data( container, "tabs" ).options;
        var tabs = $.data( container, "tabs" ).tabs;
        var selectHis = $.data( container, "tabs" ).selectHis;
        if( !exists( container, which ) ) {
            return;
        }
        var tab = getTab( container, which );
        var title = tab.panel( "options" ).title;
        var tabIndex = getTabIndex( container, tab );
        if( opts.onBeforeClose.call( container, title, tabIndex ) == false ) {
            return;
        }
        var tab = getTab( container, which, true );
        tab.panel( "options" ).tab.remove();
        tab.panel( "destroy" );
        opts.onClose.call( container, title, tabIndex );
        setScrollers( container );
        for( var i = 0; i < selectHis.length; i++ ) {
            if( selectHis[i] == title ) {
                selectHis.splice( i, 1 );
                i--;
            }
        }
        var lastTab = selectHis.pop();
        if( lastTab ) {
            selectTab( container, lastTab );
        } else {
            if( tabs.length ) {
                selectTab( container, 0 );
            }
        }
    }
    ;
    function getTab( container, which, close ) {
        var tabs = $.data( container, "tabs" ).tabs;
        if( typeof which == "number" ) {
            if( which < 0 || which >= tabs.length ) {
                return null;
            } else {
                var tab = tabs[which];
                if( close ) {
                    tabs.splice( which, 1 );
                }
                return tab;
            }
        }
        for( var i = 0; i < tabs.length; i++ ) {
            var tab = tabs[i];
            if( tab.panel( "options" ).title == which ) {
                if( close ) {
                    tabs.splice( i, 1 );
                }
                return tab;
            }
        }
        return null;
    }
    ;
    function getTabIndex( container, tab ) {
        var tabs = $.data( container, "tabs" ).tabs;
        for( var i = 0; i < tabs.length; i++ ) {
            if( tabs[i][0] == $( tab )[0] ) {
                return i;
            }
        }
        return -1;
    }
    ;
    function getSelected( container ) {
        var tabs = $.data( container, "tabs" ).tabs;
        for( var i = 0; i < tabs.length; i++ ) {
            var tab = tabs[i];
            if( tab.panel( "options" ).closed == false ) {
                return tab;
            }
        }
        return null;
    }
    ;
    function initSelectTab( container ) {
        var tabs = $.data( container, "tabs" ).tabs;
        for( var i = 0; i < tabs.length; i++ ) {
            if( tabs[i].panel( "options" ).selected ) {
                selectTab( container, i );
                return;
            }
        }
        if( tabs.length ) {
            selectTab( container, 0 );
        }
    }
    ;
    function selectTab( container, which ) {
        var opts = $.data( container, "tabs" ).options;
        var tabs = $.data( container, "tabs" ).tabs;
        var selectHis = $.data( container, "tabs" ).selectHis;
        if( tabs.length == 0 ) {
            return;
        }
        var tab = getTab( container, which );
        if( !tab ) {
            return;
        }
        var tabIndex = getTabIndex( container, tab );
        var title = tab.panel( "options" ).title;
        if( opts.onBeforeSelect.call( container, title, tabIndex ) == false ) {
            // 新增 onBeforeSelect 事件，如果返回 false 则阻止 tab 页被选中
            return;
        }
        var selected = getSelected( container );
        if( selected ) {
            selected.panel( "close" );
            selected.panel( "options" ).tab.removeClass( "tabs-selected" );
        }
        tab.panel( "open" );
        selectHis.push( title );
        var tab = tab.panel( "options" ).tab;
        tab.addClass( "tabs-selected" );
        var wrap = $( container ).find( ">div.tabs-header div.tabs-wrap" );
        var leftPos = tab.position().left + wrap.scrollLeft();
        var left = leftPos - wrap.scrollLeft();
        var right = left + tab.outerWidth();
        if( left < 0 || right > wrap.innerWidth() ) {
            var pos = Math.min( leftPos - ( wrap.width() - tab.width() ) / 2, getMaxScrollWidth( container ) );
            wrap.animate( {
                scrollLeft : pos
            }, opts.scrollDuration );
        } else {
            var pos = Math.min( wrap.scrollLeft(), getMaxScrollWidth( container ) );
            wrap.animate( {
                scrollLeft : pos
            }, opts.scrollDuration );
        }
        fitContent( container );
        opts.onSelect.call( container, title, tabIndex );
    }
    ;
    function exists( container, which ) {
        return getTab( container, which ) != null;
    }
    ;
    $.fn.tabs = function( options, param ) {
        if( typeof options == "string" ) {
            return $.fn.tabs.methods[options]( this, param );
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "tabs" );
            var opts;
            if( state ) {
                opts = $.extend( state.options, options );
                state.options = opts;
            } else {
                $.data( this, "tabs", {
                    options : $.extend( {}, $.fn.tabs.defaults, $.fn.tabs.parseOptions( this ), options ),
                    tabs : [],
                    selectHis : []
                } );
                wrapTabs( this );
            }
            buildButton( this );
            setProperties( this );
            setSize( this );
            initSelectTab( this );
        } );
    };
    $.fn.tabs.methods = {
        options : function( jq ) {
            return $.data( jq[0], "tabs" ).options;
        },
        tabs : function( jq ) {
            return $.data( jq[0], "tabs" ).tabs;
        },
        resize : function( jq ) {
            return jq.each( function() {
                setSize( this );
                fitContent( this );
            } );
        },
        /** Add a new tab panel, the options parameter is a config object, see tab panel properties for more details. */
        add : function( jq, options ) {
            return jq.each( function() {
                addTab( this, options );
            } );
        },
        /** Close a tab panel, the 'which' parameter can be the title or index of tab panel to be closed. */
        close : function( jq, which ) {
            return jq.each( function() {
                closeTab( this, which );
            } );
        },
        /** Get the specified tab panel, the 'which' parameter can be the title or index of tab panel. */
        getTab : function( jq, which ) {
            return getTab( jq[0], which );
        },
        /** Get the specified tab panel index */
        getTabIndex : function( jq, tab ) {
            return getTabIndex( jq[0], tab );
        },
        /** Get the selected tab panel. */
        getSelected : function( jq ) {
            return getSelected( jq[0] );
        },
        /** Select a tab panel, the 'which' parameter can be the title or index of tab panel. */
        select : function( jq, param ) {
            return jq.each( function() {
                selectTab( this, param );
            } );
        },
        /** Indicate if the special panel is exists, the 'which' parameter can be the title or index of tab panel. */
        exists : function( jq, which ) {
            return exists( jq[0], which );
        },
        /**
         * Update the specified tab panel, the param parameter contains two properties: 
         *      tab:        the tab panel to be updated. 
         *      options: the panel options.
         */
        update : function( jq, param ) {
            return jq.each( function() {
                update( this, param );
            } );
        },
        enableTab : function( jq, which ) {   
            return jq.each( function() {
                $( this ).tabs( "getTab", which ).panel( "options" ).tab.removeClass( "tabs-disabled" );
            } );
        },
        disableTab : function( jq, which ) {
            return jq.each( function() {
                $( this ).tabs( "getTab", which ).panel( "options" ).tab.addClass( "tabs-disabled" );
            } );
        }
    };
    $.fn.tabs.parseOptions = function( target ) {
        return $.extend( {}, $.parser.parseOptions( target, ["width", "height", "tools", {
            fit : "boolean",
            border : "boolean",
            plain : "boolean"
        }] ) );
    };
    $.fn.tabs.defaults = {
        width : "auto",
        height : "auto",
        plain : true,
        fit : false,
        border : true,
        tools : null,
        scrollIncrement : 100,
        scrollDuration : 400,
        onLoad : function( panel ) {
        },
        onBeforeSelect : function( title, index ) {
        },
        onSelect : function( title, index ) {
        },
        onBeforeClose : function( title, index ) {
        },
        onClose : function( title, index ) {
        },
        onAdd : function( title, index ) {
        },
        onUpdate : function( title, index ) {
        },
        onContextMenu : function( e, title, index ) {
        }
    };
} )( jQuery );
