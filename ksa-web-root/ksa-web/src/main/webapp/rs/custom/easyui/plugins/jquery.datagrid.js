/**
 * datagrid - jQuery EasyUI 1.3
 * 
 * Licensed under the GPL terms To use it on other terms please contact us
 * 
 * Copyright(c) 2009-2012 stworthy [ stworthy@gmail.com ]
 * 
 * Dependencies: 
 *      resizable 
 *      linkbutton 
 *      pagination
 */
( function( $ ) {
    var staticDatagridCount = 0;    // 表示全局 jquery.datagrid widget 的数量
    function _2( array, obj ) {
        for( var i = 0; i < array.length; i++ ) {
            if( array[i] == obj ) {
                return i;
            }
        }
        return -1;
    }
    ;
    function _4( a, o, id ) {
        if( typeof o == "string" ) {
            for( var i = 0, _5 = a.length; i < _5; i++ ) {
                if( a[i][o] == id ) {
                    a.splice( i, 1 );
                    return;
                }
            }
        } else {
            var _6 = _2( a, o );
            if( _6 != -1 ) {
                a.splice( _6, 1 );
            }
        }
    }
    ;
    function setSize( target, param ) {
        var opts = $.data( target, "datagrid" ).options;
        var panel = $.data( target, "datagrid" ).panel;
        if( param ) {
            if( param.width ) {
                opts.width = param.width;
            }
            if( param.height ) {
                opts.height = param.height;
            }
        }
        if( opts.fit == true ) {
            var p = panel.panel( "panel" ).parent();
            opts.width = p.width();
            opts.height = p.height();
        }
        panel.panel( "resize", {
            width : opts.width,
            height : opts.height
        } );
    }
    ;
    function _c( _d ) {
        var opt = $.data( _d, "datagrid" ).options;
        var dc = $.data( _d, "datagrid" ).dc;
        var panel = $.data( _d, "datagrid" ).panel;
        var panelWidth = panel.width();
        var panelHeight = panel.height();
        var view = dc.view;
        var view1 = dc.view1;
        var view2 = dc.view2;
        var view1Header = view1.children( "div.datagrid-header" );
        var view2Header = view2.children( "div.datagrid-header" );
        var view1Table = view1Header.find( "table" );
        var view2Table = view2Header.find( "table" );
        view.width( panelWidth );
        var _19 = view1Header.children( "div.datagrid-header-inner" ).show();
        view1.width( _19.find( "table" ).width() );
        if( !opt.showHeader ) {
            _19.hide();
        }
        view2.width( panelWidth - view1.outerWidth() );
        view1.children( "div.datagrid-header,div.datagrid-body,div.datagrid-footer" ).width( view1.width() );
        view2.children( "div.datagrid-header,div.datagrid-body,div.datagrid-footer" ).width( view2.width() );
        var hh;
        view1Header.css( "height", "" );
        view2Header.css( "height", "" );
        view1Table.css( "height", "" );
        view2Table.css( "height", "" );
        hh = Math.max( view1Table.height(), view2Table.height() );
        view1Table.height( hh );
        view2Table.height( hh );
        view1Header.add( view2Header )._outerHeight( hh );
        if( opt.height != "auto" ) {
            var _1a = panelHeight - view2.children( "div.datagrid-header" ).outerHeight( true )
                    - view2.children( "div.datagrid-footer" ).outerHeight( true )
                    - panel.children( "div.datagrid-toolbar" ).outerHeight( true );
            panel.children( "div.datagrid-pager" ).each( function() {
                _1a -= $( this ).outerHeight( true );
            } );
            view1.children( "div.datagrid-body" ).height( _1a );
            view2.children( "div.datagrid-body" ).height( _1a );
        }
        view.height( view2.height() );
        view2.css( "left", view1.outerWidth() );
    }
    ;
    function fixRowHeight( target, rowIndex, _1e ) {
        var rows = $.data( target, "datagrid" ).data.rows;
        var opts = $.data( target, "datagrid" ).options;
        var dc = $.data( target, "datagrid" ).dc;
        if( !dc.body1.is( ":empty" ) && ( !opts.nowrap || opts.autoRowHeight || _1e ) ) {
            if( rowIndex != undefined ) {
                var tr1 = opts.finder.getTr( target, rowIndex, "body", 1 );
                var tr2 = opts.finder.getTr( target, rowIndex, "body", 2 );
                _21( tr1, tr2 );
            } else {
                var tr1 = opts.finder.getTr( target, 0, "allbody", 1 );
                var tr2 = opts.finder.getTr( target, 0, "allbody", 2 );
                _21( tr1, tr2 );
                if( opts.showFooter ) {
                    var tr1 = opts.finder.getTr( target, 0, "allfooter", 1 );
                    var tr2 = opts.finder.getTr( target, 0, "allfooter", 2 );
                    _21( tr1, tr2 );
                }
            }
        }
        _c( target );
        if( opts.height == "auto" ) {
            var _22 = dc.body1.parent();
            var _23 = dc.body2;
            var _24 = 0;
            var _25 = 0;
            _23.children().each( function() {
                var c = $( this );
                if( c.is( ":visible" ) ) {
                    _24 += c.outerHeight();
                    if( _25 < c.outerWidth() ) {
                        _25 = c.outerWidth();
                    }
                }
            } );
            if( _25 > _23.width() ) {
                _24 += 18;
            }
            _22.height( _24 );
            _23.height( _24 );
            dc.view.height( dc.view2.height() );
        }
        dc.body2.triggerHandler( "scroll" );
        function _21( _26, _27 ) {
            for( var i = 0; i < _27.length; i++ ) {
                var tr1 = $( _26[i] );
                var tr2 = $( _27[i] );
                tr1.css( "height", "" );
                tr2.css( "height", "" );
                var _28 = Math.max( tr1.height(), tr2.height() );
                tr1.css( "height", _28 );
                tr2.css( "height", _28 );
            }
        }
        ;
    }
    ;
    function wrapGrid( target, rownumbers ) {
        function _2c() {
            var _2d = [];
            var _2e = [];
            $( target ).children( "thead" ).each( function() {
                var opt = $.parser.parseOptions( this, [{
                    frozen : "boolean"
                }] );
                $( this ).find( "tr" ).each( function() {
                    var _2f = [];
                    $( this ).find( "th" ).each( function() {
                        var th = $( this );
                        var col = $.extend( {}, $.parser.parseOptions( this, ["field", "align", {
                            sortable : "boolean",
                            checkbox : "boolean",
                            resizable : "boolean"
                        }, {
                            rowspan : "number",
                            colspan : "number",
                            width : "number"
                        }] ), {
                            title : ( th.html() || undefined ),
                            hidden : ( th.attr( "hidden" ) ? true : undefined ),
                            formatter : ( th.attr( "formatter" ) ? eval( th.attr( "formatter" ) ) : undefined ),
                            styler : ( th.attr( "styler" ) ? eval( th.attr( "styler" ) ) : undefined )
                        } );
                        if( !col.align ) {
                            col.align = "left";
                        }
                        if( th.attr( "editor" ) ) {
                            var s = $.trim( th.attr( "editor" ) );
                            if( s.substr( 0, 1 ) == "{" ) {
                                col.editor = eval( "(" + s + ")" );
                            } else {
                                col.editor = s;
                            }
                        }
                        _2f.push( col );
                    } );
                    opt.frozen ? _2d.push( _2f ) : _2e.push( _2f );
                } );
            } );
            return [_2d, _2e];
        }
        ;
        var gridWrap = $(
                "<div class=\"datagrid-wrap\">" + "<div class=\"datagrid-view\">" + "<div class=\"datagrid-view1\">"
                        + "<div class=\"datagrid-header\">" + "<div class=\"datagrid-header-inner\"></div>" + "</div>"
                        + "<div class=\"datagrid-body\">" + "<div class=\"datagrid-body-inner\"></div>" + "</div>"
                        + "<div class=\"datagrid-footer\">" + "<div class=\"datagrid-footer-inner\"></div>" + "</div>"
                        + "</div>" + "<div class=\"datagrid-view2\">" + "<div class=\"datagrid-header\">"
                        + "<div class=\"datagrid-header-inner\"></div>" + "</div>" + "<div class=\"datagrid-body\"></div>"
                        + "<div class=\"datagrid-footer\">" + "<div class=\"datagrid-footer-inner\"></div>" + "</div>"+ "</div>"                       
                        + "</div>" + "</div>" ).insertAfter( target );
        gridWrap.panel( {
            doSize : false
        } );
        gridWrap.panel( "panel" ).addClass( "datagrid" ).bind( "_resize", function( e, _31 ) {
            var _32 = $.data( target, "datagrid" ).options;
            if( _32.fit == true || _31 ) {
                setSize( target );
                setTimeout( function() {
                    if( $.data( target, "datagrid" ) ) {
                        fixColumnSize( target );
                    }
                }, 0 );
            }
            return false;
        } );
        $( target ).hide().appendTo( gridWrap.children( "div.datagrid-view" ) );
        var cc = _2c();
        var view = gridWrap.children( "div.datagrid-view" );
        var view1 = view.children( "div.datagrid-view1" );
        var view2 = view.children( "div.datagrid-view2" );
        return {
            panel : gridWrap,
            frozenColumns : cc[0],
            columns : cc[1],
            dc : {
                view : view,
                view1 : view1,
                view2 : view2,
                header1 : view1.children( "div.datagrid-header" ).children( "div.datagrid-header-inner" ),
                header2 : view2.children( "div.datagrid-header" ).children( "div.datagrid-header-inner" ),
                body1 : view1.children( "div.datagrid-body" ).children( "div.datagrid-body-inner" ),
                body2 : view2.children( "div.datagrid-body" ),
                footer1 : view1.children( "div.datagrid-footer" ).children( "div.datagrid-footer-inner" ),
                footer2 : view2.children( "div.datagrid-footer" ).children( "div.datagrid-footer-inner" )
            }
        };
    }
    ;
    function getGridData( target ) {
        var data = {
            total : 0,
            rows : []
        };
        var fields = getColumnFields( target, true ).concat( getColumnFields( target, false ) );
        $( target ).find( "tbody tr" ).each( function() {
            data.total++;
            var col = {};
            for( var i = 0; i < fields.length; i++ ) {
                col[fields[i]] = $( "td:eq(" + i + ")", this ).html();
            }
            data.rows.push( col );
        } );
        return data;
    }
    ;
    function init( target ) {
        var state = $.data( target, "datagrid" );
        var opts = state.options;
        var dc = state.dc;
        var panel = state.panel;
        panel.panel( $.extend( {}, opts, {
            id : null,
            doSize : false,
            onResize : function( width, height ) {
                setTimeout( function() {
                    if( $.data( target, "datagrid" ) ) {
                        _c( target );
                        fitColumns( target );
                        opts.onResize.call( panel, width, height );
                    }
                }, 0 );
            },
            onExpand : function() {
                fixRowHeight( target );
                opts.onExpand.call( panel );
            }
        } ) );
        state.rowIdPrefix = "datagrid-row-r" + ( ++staticDatagridCount );
        _43( dc.header1, opts.frozenColumns, true );
        _43( dc.header2, opts.columns, false );
        _44();
        dc.header1.add( dc.header2 ).css( "display", opts.showHeader ? "block" : "none" );
        dc.footer1.add( dc.footer2 ).css( "display", opts.showFooter ? "block" : "none" );
        if( opts.toolbar ) {
            if( typeof opts.toolbar == "string" ) {
                $( opts.toolbar ).addClass( "datagrid-toolbar" ).prependTo( panel );
                $( opts.toolbar ).show();
            } else {
                $( "div.datagrid-toolbar", panel ).remove();
                var tb = $( "<div class=\"datagrid-toolbar\"></div>" ).prependTo( panel );
                for( var i = 0; i < opts.toolbar.length; i++ ) {
                    var btn = opts.toolbar[i];
                    if( btn == "-" ) {
                        $( "<div class=\"datagrid-btn-separator\"></div>" ).appendTo( tb );
                    } else if( btn == "|" ) {   // CUSTOM : 向右浮动的分隔符 与 '-' 相对
                        $( "<div class=\"datagrid-btn-separator\" style=\"float:right\"></div>" ).appendTo( tb );
                    } else {
                        /*var _45 = $( "<a href=\"javascript:void(0)\"></a>" );
                        _45[0].onclick = eval( btn.handler || function() {
                        } );
                        _45.css( "float", "left" ).appendTo( tb ).linkbutton( $.extend( {}, btn, {
                            plain : true
                        } ) );*/
                        // CUSTOM : 采用 bootstrap button 
                        var icon = btn.iconCls ? ( "<i class='icon-empty " + btn.iconCls + "'></i> " ) : "";
                        var b = $("<span class='btn btn-mini'>"+ icon + btn.text +"</span>");
                        b.attr("id", btn.id ? btn.id : null );
                        b.addClass( btn.cls ).css( "float", btn.float == "right" ? "right" : "left" ).css("margin", "2px 3px").appendTo( tb );
                        b[0].onclick = eval( btn.handler || function() { } );
                    }
                }
            }
        } else {
            $( "div.datagrid-toolbar", panel ).remove();
        }
        $( "div.datagrid-pager", panel ).remove();
        if( opts.pagination ) {
            var _46 = $( "<div class=\"datagrid-pager\"></div>" );
            if( opts.pagePosition == "bottom" ) {
                _46.appendTo( panel );
            } else {
                if( opts.pagePosition == "top" ) {
                    _46.addClass( "datagrid-pager-top" ).prependTo( panel );
                } else {
                    var _47 = $( "<div class=\"datagrid-pager datagrid-pager-top\"></div>" ).prependTo( panel );
                    _46.appendTo( panel );
                    _46 = _46.add( _47 );
                }
            }
            _46.pagination( {
                pageNumber : opts.pageNumber,
                pageSize : opts.pageSize,
                pageList : opts.pageList,
                onSelectPage : function( _48, _49 ) {
                    opts.pageNumber = _48;
                    opts.pageSize = _49;
                    _46.pagination( "refresh", {
                        pageNumber : _48,
                        pageSize : _49
                    } );
                    request( target );
                }
            } );
            opts.pageSize = _46.pagination( "options" ).pageSize;
        }
        function _43( _4a, _4b, _4c ) {
            if( !_4b ) {
                return;
            }
            $( _4a ).show();
            $( _4a ).empty();
            var t = $(
                    "<table class=\"datagrid-htable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody></tbody></table>" )
                    .appendTo( _4a );
            for( var i = 0; i < _4b.length; i++ ) {
                var tr = $( "<tr class=\"datagrid-header-row\"></tr>" ).appendTo( $( "tbody", t ) );
                var _4d = _4b[i];
                for( var j = 0; j < _4d.length; j++ ) {
                    var col = _4d[j];
                    var _4e = "";
                    if( col.rowspan ) {
                        _4e += "rowspan=\"" + col.rowspan + "\" ";
                    }
                    if( col.colspan ) {
                        _4e += "colspan=\"" + col.colspan + "\" ";
                    }
                    var td = $( "<td " + _4e + "></td>" ).appendTo( tr );
                    if( col.checkbox ) {
                        td.attr( "field", col.field );
                        $( "<div class=\"datagrid-header-check\"></div>" ).html( "<input type=\"checkbox\"/>" ).appendTo( td );
                    } else {
                        if( col.field ) {
                            td.attr( "field", col.field );
                            td
                                    .append( "<div class=\"datagrid-cell\"><span></span><span class=\"datagrid-sort-icon\"></span></div>" );
                            $( "span", td ).html( col.title );
                            $( "span.datagrid-sort-icon", td ).html( "&nbsp;" );
                            var _4f = td.find( "div.datagrid-cell" );
                            if( col.resizable == false ) {
                                _4f.attr( "resizable", "false" );
                            }
                            if( col.width ) {
                                _4f._outerWidth( col.width );
                                col.boxWidth = parseInt( _4f[0].style.width );
                            } else {
                                col.auto = true;
                            }
                            _4f.css( "text-align", ( col.align || "left" ) );
                            col.cellClass = "datagrid-cell-c" + staticDatagridCount + "-" + col.field.replace( ".", "-" );
                            col.cellSelector = "div." + col.cellClass;
                        } else {
                            $( "<div class=\"datagrid-cell-group\"></div>" ).html( col.title ).appendTo( td );
                        }
                    }
                    if( col.hidden ) {
                        td.hide();
                    }
                }
            }
            if( _4c && opts.rownumbers ) {
                var td = $( "<td rowspan=\"" + opts.frozenColumns.length
                        + "\"><div class=\"datagrid-header-rownumber\"></div></td>" );
                if( $( "tr", t ).length == 0 ) {
                    td.wrap( "<tr class=\"datagrid-header-row\"></tr>" ).parent().appendTo( $( "tbody", t ) );
                } else {
                    td.prependTo( $( "tr:first", t ) );
                }
            }
        }
        ;
        function _44() {
            dc.view.children( "style" ).remove();
            var ss = ["<style type=\"text/css\">"];
            var _50 = getColumnFields( target, true ).concat( getColumnFields( target ) );
            for( var i = 0; i < _50.length; i++ ) {
                var col = getColumnOption( target, _50[i] );
                if( col && !col.checkbox ) {
                    ss.push( col.cellSelector + " {width:" + col.boxWidth + "px;}" );
                }
            }
            ss.push( "</style>" );
            $( ss.join( "\n" ) ).prependTo( dc.view );
        }
        ;
    }
    ;
    function setProperties( target ) {
        var _54 = $.data( target, "datagrid" );
        var _55 = _54.panel;
        var _56 = _54.options;
        var dc = _54.dc;
        var _57 = dc.header1.add( dc.header2 );
        _57.find( "td:has(div.datagrid-cell)" ).unbind( ".datagrid" ).bind( "mouseenter.datagrid", function() {
            $( this ).addClass( "datagrid-header-over" );
        } ).bind( "mouseleave.datagrid", function() {
            $( this ).removeClass( "datagrid-header-over" );
        } ).bind( "contextmenu.datagrid", function( e ) {
            var _58 = $( this ).attr( "field" );
            _56.onHeaderContextMenu.call( target, e, _58 );
        } );
        _57.find( "input[type=checkbox]" ).unbind( ".datagrid" ).bind( "click.datagrid", function( e ) {
            if( _56.singleSelect && _56.selectOnCheck ) {
                return false;
            }
            if( $( this ).is( ":checked" ) ) {
                checkAll( target );
            } else {
                uncheckAll( target );
            }
            e.stopPropagation();
        } );
        var _59 = _57.find( "div.datagrid-cell" );
        _59.unbind( ".datagrid" ).bind( "click.datagrid", function( e ) {
            if( e.pageX < $( this ).offset().left + $( this ).outerWidth() - 5 ) {
                var _5a = $( this ).parent().attr( "field" );
                var col = getColumnOption( target, _5a );
                if( !col.sortable || _54.resizing ) {
                    return;
                }
                _56.sortName = _5a;
                _56.sortOrder = "asc";
                var c = "datagrid-sort-asc";
                if( $( this ).hasClass( c ) ) {
                    c = "datagrid-sort-desc";
                    _56.sortOrder = "desc";
                }
                _59.removeClass( "datagrid-sort-asc datagrid-sort-desc" );
                $( this ).addClass( c );
                if( _56.remoteSort ) {
                    request( target );
                } else {
                    var data = $.data( target, "datagrid" ).data;
                    renderGrid( target, data );
                }
                _56.onSortColumn.call( target, _56.sortName, _56.sortOrder );
            }
        } ).bind( "dblclick.datagrid", function( e ) {
            if( e.pageX > $( this ).offset().left + $( this ).outerWidth() - 5 ) {
                var _5c = $( this ).parent().attr( "field" );
                var col = getColumnOption( target, _5c );
                if( col.resizable == false ) {
                    return;
                }
                $( target ).datagrid( "autoSizeColumn", _5c );
                col.auto = false;
            }
        } );
        _59.each( function() {
            $( this ).resizable( {
                handles : "e",
                disabled : ( $( this ).attr( "resizable" ) ? $( this ).attr( "resizable" ) == "false" : false ),
                minWidth : 25,
                onStartResize : function( e ) {
                    _54.resizing = true;
                    _57.css( "cursor", "e-resize" );
                    if( !_54.proxy ) {
                        _54.proxy = $( "<div class=\"datagrid-resize-proxy\"></div>" ).appendTo( dc.view );
                    }
                    _54.proxy.css( {
                        left : e.pageX - $( _55 ).offset().left - 1,
                        display : "none"
                    } );
                    setTimeout( function() {
                        if( _54.proxy ) {
                            _54.proxy.show();
                        }
                    }, 500 );
                },
                onResize : function( e ) {
                    _54.proxy.css( {
                        left : e.pageX - $( _55 ).offset().left - 1,
                        display : "block"
                    } );
                    return false;
                },
                onStopResize : function( e ) {
                    _57.css( "cursor", "" );
                    var _5d = $( this ).parent().attr( "field" );
                    var col = getColumnOption( target, _5d );
                    col.width = $( this ).outerWidth();
                    col.boxWidth = parseInt( this.style.width );
                    col.auto = undefined;
                    fixColumnSize( target, _5d );
                    dc.view2.children( "div.datagrid-header" ).scrollLeft( dc.body2.scrollLeft() );
                    _54.proxy.remove();
                    _54.proxy = null;
                    if( $( this ).parents( "div:first.datagrid-header" ).parent().hasClass( "datagrid-view1" ) ) {
                        _c( target );
                    }
                    fitColumns( target );
                    _56.onResizeColumn.call( target, _5d, col.width );
                    setTimeout( function() {
                        _54.resizing = false;
                    }, 0 );
                }
            } );
        } );
        dc.body1.add( dc.body2 ).unbind().bind( "mouseover", function( e ) {
            var tr = $( e.target ).closest( "tr.datagrid-row" );
            if( !tr.length ) {
                return;
            }
            var _5e = _5f( tr );
            _56.finder.getTr( target, _5e ).addClass( "datagrid-row-over" );
            e.stopPropagation();
        } ).bind( "mouseout", function( e ) {
            var tr = $( e.target ).closest( "tr.datagrid-row" );
            if( !tr.length ) {
                return;
            }
            var _60 = _5f( tr );
            _56.finder.getTr( target, _60 ).removeClass( "datagrid-row-over" );
            e.stopPropagation();
        } ).bind( "click", function( e ) {
            var tt = $( e.target );
            var tr = tt.closest( "tr.datagrid-row" );
            if( !tr.length ) {
                return;
            }
            var _61 = _5f( tr );
            if( tt.parent().hasClass( "datagrid-cell-check" ) ) {
                if( _56.singleSelect && _56.selectOnCheck ) {
                    if( !_56.checkOnSelect ) {
                        uncheckAll( target, true );
                    }
                    checkRow( target, _61 );
                } else {
                    if( tt.is( ":checked" ) ) {
                        checkRow( target, _61 );
                    } else {
                        uncheckRow( target, _61 );
                    }
                }
            } else {
                var row = _56.finder.getRow( target, _61 );
                var td = tt.closest( "td[field]", tr );
                if( td.length ) {
                    var _62 = td.attr( "field" );
                    _56.onClickCell.call( target, _61, _62, row[_62] );
                }
                if( _56.singleSelect == true ) {
                    selectRow( target, _61 );
                } else {
                    if( tr.hasClass( "datagrid-row-selected" ) ) {
                        unselectRow( target, _61 );
                    } else {
                        selectRow( target, _61 );
                    }
                }
                _56.onClickRow.call( target, _61, row );
            }
            e.stopPropagation();
        } ).bind( "dblclick", function( e ) {
            var tt = $( e.target );
            var tr = tt.closest( "tr.datagrid-row" );
            if( !tr.length ) {
                return;
            }
            var _63 = _5f( tr );
            var row = _56.finder.getRow( target, _63 );
            var td = tt.closest( "td[field]", tr );
            if( td.length ) {
                var _64 = td.attr( "field" );
                _56.onDblClickCell.call( target, _63, _64, row[_64] );
            }
            _56.onDblClickRow.call( target, _63, row );
            e.stopPropagation();
        } ).bind( "contextmenu", function( e ) {
            var tr = $( e.target ).closest( "tr.datagrid-row" );
            if( !tr.length ) {
                return;
            }
            var _65 = _5f( tr );
            var row = _56.finder.getRow( target, _65 );
            _56.onRowContextMenu.call( target, e, _65, row );
            e.stopPropagation();
        } );
        dc.body2.bind( "scroll", function() {
            dc.view1.children( "div.datagrid-body" ).scrollTop( $( this ).scrollTop() );
            dc.view2.children( "div.datagrid-header,div.datagrid-footer" ).scrollLeft( $( this ).scrollLeft() );
        } );
        function _5f( tr ) {
            if( tr.attr( "datagrid-row-index" ) ) {
                return parseInt( tr.attr( "datagrid-row-index" ) );
            } else {
                return tr.attr( "node-id" );
            }
        }
        ;
    }
    ;
    function fitColumns( target ) {
        var opts = $.data( target, "datagrid" ).options;
        var dc = $.data( target, "datagrid" ).dc;
        if( !opts.fitColumns ) {
            return;
        }
        var gridHeader2 = dc.view2.children( "div.datagrid-header" );
        var visableWidth = 0;
        var visableColumn;
        var fields = getColumnFields( target, false );
        for( var i = 0; i < fields.length; i++ ) {
            var col = getColumnOption( target, fields[i] );
            if( _6d( col ) ) {
                visableWidth += col.width;
                visableColumn = col;
            }
        }
        var innerHeader = gridHeader2.children( "div.datagrid-header-inner" ).show();
        var fullWidth = gridHeader2.width() - gridHeader2.find( "table" ).width() - opts.scrollbarSize;
        var rate = fullWidth / visableWidth;
        if( !opts.showHeader ) {
            innerHeader.hide();
        }
        for( var i = 0; i < fields.length; i++ ) {
            var col = getColumnOption( target, fields[i] );
            if( _6d( col ) ) {
                var width = Math.floor( col.width * rate );
                fitColumnWidth( col, width );
                fullWidth -= width;
            }
        }
        if( fullWidth && visableColumn ) {
            fitColumnWidth( visableColumn, fullWidth );
        }
        fixColumnSize( target );
        function fitColumnWidth( col, width ) {
            col.width += width;
            col.boxWidth += width;
            gridHeader2.find( "td[field=\"" + col.field + "\"] div.datagrid-cell" ).width( col.boxWidth );
        }
        ;
        function _6d( col ) {
            if( !col.hidden && !col.checkbox && !col.auto ) {
                return true;
            }
        }
        ;
    }
    ;
    function autoSizeColumn( target, width ) {
        var _77 = $.data( target, "datagrid" ).options;
        var dc = $.data( target, "datagrid" ).dc;
        if( width ) {
            setSize( width );
            if( _77.fitColumns ) {
                _c( target );
                fitColumns( target );
            }
        } else {
            var _78 = false;
            var _79 = getColumnFields( target, true ).concat( getColumnFields( target, false ) );
            for( var i = 0; i < _79.length; i++ ) {
                var width = _79[i];
                var col = getColumnOption( target, width );
                if( col.auto ) {
                    setSize( width );
                    _78 = true;
                }
            }
            if( _78 && _77.fitColumns ) {
                _c( target );
                fitColumns( target );
            }
        }
        function setSize( _7a ) {
            var _7b = dc.view.find( "div.datagrid-header td[field=\"" + _7a + "\"] div.datagrid-cell" );
            _7b.css( "width", "" );
            var col = $( target ).datagrid( "getColumnOption", _7a );
            col.width = undefined;
            col.boxWidth = undefined;
            col.auto = true;
            $( target ).datagrid( "fixColumnSize", _7a );
            var _7c = Math.max( _7b.outerWidth(), _7d( "allbody" ), _7d( "allfooter" ) );
            _7b._outerWidth( _7c );
            col.width = _7c;
            col.boxWidth = parseInt( _7b[0].style.width );
            $( target ).datagrid( "fixColumnSize", _7a );
            _77.onResizeColumn.call( target, _7a, col.width );
            function _7d( _7e ) {
                var _7f = 0;
                _77.finder.getTr( target, 0, _7e ).find( "td[field=\"" + _7a + "\"] div.datagrid-cell" ).each( function() {
                    var w = $( this ).outerWidth();
                    if( _7f < w ) {
                        _7f = w;
                    }
                } );
                return _7f;
            }
            ;
        }
        ;
    }
    ;
    function fixColumnSize( target, cell ) {
        var opts = $.data( target, "datagrid" ).options;
        var dc = $.data( target, "datagrid" ).dc;
        var _83 = dc.view.find( "table.datagrid-btable,table.datagrid-ftable" );
        _83.css( "table-layout", "fixed" );
        if( cell ) {
            fix( cell );
        } else {
            var ff = getColumnFields( target, true ).concat( getColumnFields( target, false ) );
            for( var i = 0; i < ff.length; i++ ) {
                fix( ff[i] );
            }
        }
        _83.css( "table-layout", "auto" );
        fixMergedCellsSize( target );
        setTimeout( function() {
            fixRowHeight( target );
            fixEditorSize( target );
        }, 0 );
        function fix( cell ) {
            var col = getColumnOption( target, cell );
            if( col.checkbox ) {
                return;
            }
            var _86 = dc.view.children( "style" )[0];
            var _87 = _86.styleSheet ? _86.styleSheet : ( _86.sheet || document.styleSheets[document.styleSheets.length - 1] );
            var _88 = _87.cssRules || _87.rules;
            for( var i = 0, len = _88.length; i < len; i++ ) {
                var _89 = _88[i];
                if( _89.selectorText.toLowerCase() == col.cellSelector.toLowerCase() ) {
                    _89.style["width"] = col.boxWidth ? col.boxWidth + "px" : "auto";
                    break;
                }
            }
        }
        ;
    }
    ;
    function fixMergedCellsSize( target ) {
        var dc = $.data( target, "datagrid" ).dc;
        dc.body1.add( dc.body2 ).find( "td.datagrid-td-merged" ).each( function() {
            var td = $( this );
            var colspan = td.attr( "colspan" ) || 1;
            var fieldWidth = getColumnOption( target, td.attr( "field" ) ).width;
            for( var i = 1; i < colspan; i++ ) {
                td = td.next();
                fieldWidth += getColumnOption( target, td.attr( "field" ) ).width + 1;
            }
            $( this ).children( "div.datagrid-cell" )._outerWidth( fieldWidth );
        } );
    }
    ;
    function fixEditorSize( target ) {
        var dc = $.data( target, "datagrid" ).dc;
        dc.view.find( "div.datagrid-editable" ).each( function() {
            var _8f = $( this );
            var field = _8f.parent().attr( "field" );
            var col = $( target ).datagrid( "getColumnOption", field );
            _8f._outerWidth( col.width );
            var ed = $.data( this, "datagrid.editor" );
            if( ed.actions.resize ) {
                ed.actions.resize( ed.target, _8f.width() );
            }
        } );
    }
    ;
    function getColumnOption( target, field ) {
        function _93( columns ) {
            if( columns ) {
                for( var i = 0; i < columns.length; i++ ) {
                    var cc = columns[i];
                    for( var j = 0; j < cc.length; j++ ) {
                        var c = cc[j];
                        if( c.field == field ) {
                            return c;
                        }
                    }
                }
            }
            return null;
        }
        ;
        var opts = $.data( target, "datagrid" ).options;
        var col = _93( opts.columns );
        if( !col ) {
            col = _93( opts.frozenColumns );
        }
        return col;
    }
    ;
    function getColumnFields( target, frozen ) {
        var opts = $.data( target, "datagrid" ).options;
        var columns = ( frozen == true ) ? ( opts.frozenColumns || [[]] ) : opts.columns;
        if( columns.length == 0 ) {
            return [];
        }
        var fields = [];
        function getFixedColspan( index ) {
            var c = 0;
            var i = 0;
            while( true ) {
                if( fields[i] == undefined ) {
                    if( c == index ) {
                        return i;
                    }
                    c++;
                }
                i++;
            }
        }
        ;
        function findColumnFields( r ) {
            var ff = [];
            var c = 0;
            for( var i = 0; i < columns[r].length; i++ ) {
                var col = columns[r][i];
                if( col.field ) {
                    ff.push( [c, col.field] );
                }
                c += parseInt( col.colspan || "1" );
            }
            for( var i = 0; i < ff.length; i++ ) {
                ff[i][0] = getFixedColspan( ff[i][0] );
            }
            for( var i = 0; i < ff.length; i++ ) {
                var f = ff[i];
                fields[f[0]] = f[1];
            }
        }
        ;
        for( var i = 0; i < columns.length; i++ ) {
            findColumnFields( i );
        }
        return fields;
    }
    ;
    function renderGrid( target, data ) {
        var state = $.data( target, "datagrid" );
        var opts = state.options;
        var dc = state.dc;
        var selectedRows = state.selectedRows;
        data = opts.loadFilter.call( target, data );
        state.data = data;
        if( data.footer ) {
            state.footer = data.footer;
        }
        if( !opts.remoteSort ) {
            var opt = getColumnOption( target, opts.sortName );
            if( opt ) {
                var sorter = opt.sorter || function( a, b ) {
                    return ( a > b ? 1 : -1 );
                };
                data.rows.sort( function( r1, r2 ) {
                    return sorter( r1[opts.sortName], r2[opts.sortName] ) * ( opts.sortOrder == "asc" ? 1 : -1 );
                } );
            }
        }
        if( opts.view.onBeforeRender ) {
            opts.view.onBeforeRender.call( opts.view, target, data.rows );
        }
        opts.view.render.call( opts.view, target, dc.body2, false );
        opts.view.render.call( opts.view, target, dc.body1, true );
        if( opts.showFooter ) {
            opts.view.renderFooter.call( opts.view, target, dc.footer2, false );
            opts.view.renderFooter.call( opts.view, target, dc.footer1, true );
        }
        if( opts.view.onAfterRender ) {
            opts.view.onAfterRender.call( opts.view, target );
        }
        opts.onLoadSuccess.call( target, data );
        // CUSTOM : 添加对于空数据的处理
        if( data.rows.length == 0 ) {
            opts.onLoadEmpty.call( target, data );
            dc.body2.append("<div class='datagrid-empty'>"+opts.loadEmptyMsg+"</div>");
        } else {
            $("div.datagrid-empty", dc.body2).remove();
        }
        
        var pager = $( target ).datagrid( "getPager" );
        if( pager.length ) {
            if( pager.pagination( "options" ).total != data.total ) {
                pager.pagination( "refresh", {
                    total : data.total
                } );
            }
        }
        fixRowHeight( target );
        dc.body2.triggerHandler( "scroll" );
        _a6();
        $( target ).datagrid( "autoSizeColumn" );
        function _a6() {
            if( opts.idField ) {
                for( var i = 0; i < data.rows.length; i++ ) {
                    var row = data.rows[i];
                    if( isSelected( row ) ) {
                        selectRecord( target, row[opts.idField] );
                    }
                }
            }
            function isSelected( row ) {
                for( var i = 0; i < selectedRows.length; i++ ) {
                    if( selectedRows[i][opts.idField] == row[opts.idField] ) {
                        selectedRows[i] = row;
                        return true;
                    }
                }
                return false;
            }
            ;
        }
        ;
    }
    ;
    function getRowIndex( target, row ) {
        var opts = $.data( target, "datagrid" ).options;
        var rows = $.data( target, "datagrid" ).data.rows;
        if( typeof row == "object" ) {
            return _2( rows, row );
        } else {
            for( var i = 0; i < rows.length; i++ ) {
                if( rows[i][opts.idField] == row ) {
                    return i;
                }
            }
            return -1;
        }
    }
    ;
    function getSelected( target ) {
        var opts = $.data( target, "datagrid" ).options;
        var data = $.data( target, "datagrid" ).data;
        if( opts.idField ) {
            return $.data( target, "datagrid" ).selectedRows;
        } else {
            var selectedRows = [];
            opts.finder.getTr( target, "", "selected", 2 ).each( function() {
                var index = parseInt( $( this ).attr( "datagrid-row-index" ) );
                selectedRows.push( data.rows[index] );
            } );
            return selectedRows;
        }
    }
    ;
    function selectRecord( target, id ) {
        var opts = $.data( target, "datagrid" ).options;
        if( opts.idField ) {
            var rowIndex = getRowIndex( target, id );
            if( rowIndex >= 0 ) {
                selectRow( target, rowIndex );
            }
        }
    }
    ;
    function selectRow( target, rowIndex, _ba ) {
        var state = $.data( target, "datagrid" );
        var dc = state.dc;
        var opts = state.options;
        var data = state.data;
        var selectedRows = $.data( target, "datagrid" ).selectedRows;
        if( opts.singleSelect ) {
            unselectAll( target );
            selectedRows.splice( 0, selectedRows.length );
        }
        if( !_ba && opts.checkOnSelect ) {
            checkRow( target, rowIndex, true );
        }
        if( opts.idField ) {
            var row = opts.finder.getRow( target, rowIndex );
            ( function() {
                for( var i = 0; i < selectedRows.length; i++ ) {
                    if( selectedRows[i][opts.idField] == row[opts.idField] ) {
                        return;
                    }
                }
                selectedRows.push( row );
            } )();
        }
        opts.onSelect.call( target, rowIndex, data.rows[rowIndex] );
        var tr = opts.finder.getTr( target, rowIndex ).addClass( "datagrid-row-selected" );
        if( tr.length ) {
            var _c1 = dc.view2.children( "div.datagrid-header" ).outerHeight();
            var _c2 = dc.body2;
            var top = tr.position().top - _c1;
            if( top <= 0 ) {
                _c2.scrollTop( _c2.scrollTop() + top );
            } else {
                if( top + tr.outerHeight() > _c2.height() - 18 ) {
                    _c2.scrollTop( _c2.scrollTop() + top + tr.outerHeight() - _c2.height() + 18 );
                }
            }
        }
    }
    ;
    function unselectRow( target, rowIndex, _c6 ) {
        var state = $.data( target, "datagrid" );
        var dc = state.dc;
        var opts = state.options;
        var data = state.data;
        var selectedRows = $.data( target, "datagrid" ).selectedRows;
        if( !_c6 && opts.checkOnSelect ) {
            uncheckRow( target, rowIndex, true );
        }
        opts.finder.getTr( target, rowIndex ).removeClass( "datagrid-row-selected" );
        var row = opts.finder.getRow( target, rowIndex );
        if( opts.idField ) {
            _4( selectedRows, opts.idField, row[opts.idField] );
        }
        opts.onUnselect.call( target, rowIndex, row );
    }
    ;
    function selectAll( target, _ce ) {
        var state = $.data( target, "datagrid" );
        var opts = state.options;
        var rows = state.data.rows;
        var selectedRows = $.data( target, "datagrid" ).selectedRows;
        if( !_ce && opts.checkOnSelect ) {
            checkAll( target, true );
        }
        opts.finder.getTr( target, "", "allbody" ).addClass( "datagrid-row-selected" );
        if( opts.idField ) {
            for( var i = 0; i < rows.length; i++ ) {
                ( function() {
                    var row = rows[i];
                    for( var i = 0; i < selectedRows.length; i++ ) {
                        if( selectedRows[i][opts.idField] == row[opts.idField] ) {
                            return;
                        }
                    }
                    selectedRows.push( row );
                } )();
            }
        }
        opts.onSelectAll.call( target, rows );
    }
    ;
    function unselectAll( target, _d6 ) {
        var state = $.data( target, "datagrid" );
        var opts = state.options;
        var rows = state.data.rows;
        var selectedRows = $.data( target, "datagrid" ).selectedRows;
        if( !_d6 && opts.checkOnSelect ) {
            uncheckAll( target, true );
        }
        opts.finder.getTr( target, "", "selected" ).removeClass( "datagrid-row-selected" );
        if( opts.idField ) {
            for( var i = 0; i < rows.length; i++ ) {
                _4( selectedRows, opts.idField, rows[i][opts.idField] );
            }
        }
        opts.onUnselectAll.call( target, rows );
    }
    ;
    function checkRow( target, rowIndex, _df ) {
        var state = $.data( target, "datagrid" );
        var opts = state.options;
        var data = state.data;
        if( !_df && opts.selectOnCheck ) {
            selectRow( target, rowIndex, true );
        }
        var ck = opts.finder.getTr( target, rowIndex ).find( "div.datagrid-cell-check input[type=checkbox]" );
        $.fn.prop ? ck.prop( "checked", true ) : ck.attr( "checked", true );
        opts.onCheck.call( target, rowIndex, data.rows[rowIndex] );
    }
    ;
    function uncheckRow( target, rowIndex, _e5 ) {
        var state = $.data( target, "datagrid" );
        var opts = state.options;
        var data = state.data;
        if( !_e5 && opts.selectOnCheck ) {
            unselectRow( target, rowIndex, true );
        }
        var ck = opts.finder.getTr( target, rowIndex ).find( "div.datagrid-cell-check input[type=checkbox]" );
        $.fn.prop ? ck.prop( "checked", false ) : ck.attr( "checked", false );
        opts.onUncheck.call( target, rowIndex, data.rows[rowIndex] );
    }
    ;
    function checkAll( target, _ea ) {
        var state = $.data( target, "datagrid" );
        var opts = state.options;
        var data = state.data;
        if( !_ea && opts.selectOnCheck ) {
            selectAll( target, true );
        }
        var checkbox = opts.finder.getTr( target, "", "allbody" ).find( "div.datagrid-cell-check input[type=checkbox]" );
        $.fn.prop ? checkbox.prop( "checked", true ) : checkbox.attr( "checked", true );
        opts.onCheckAll.call( target, data.rows );
    }
    ;
    function uncheckAll( target, _f0 ) {
        var state = $.data( target, "datagrid" );
        var opts = state.options;
        var data = state.data;
        if( !_f0 && opts.selectOnCheck ) {
            unselectAll( target, true );
        }
        var checkbox = opts.finder.getTr( target, "", "allbody" ).find( "div.datagrid-cell-check input[type=checkbox]" );
        $.fn.prop ? checkbox.prop( "checked", false ) : checkbox.attr( "checked", false );
        opts.onUncheckAll.call( target, data.rows );
    }
    ;
    function beginEdit( target, rowIndex ) {
        var opts = $.data( target, "datagrid" ).options;
        var tr = opts.finder.getTr( target, rowIndex );
        var row = opts.finder.getRow( target, rowIndex );
        if( tr.hasClass( "datagrid-row-editing" ) ) {
            return;
        }
        if( opts.onBeforeEdit.call( target, rowIndex, row ) == false ) {
            return;
        }
        tr.addClass( "datagrid-row-editing" );
        createEditor( target, rowIndex );
        fixEditorSize( target );
        tr.find( "div.datagrid-editable" ).each( function() {
            var field = $( this ).parent().attr( "field" );
            var ed = $.data( this, "datagrid.editor" );
            ed.actions.setValue( ed.target, row[field] );
        } );
        validateRow( target, rowIndex );
    }
    ;
    function stopEdit( target, rowIndex, revert ) {
        var opts = $.data( target, "datagrid" ).options;
        var updatedRows = $.data( target, "datagrid" ).updatedRows;
        var insertedRows = $.data( target, "datagrid" ).insertedRows;
        var tr = opts.finder.getTr( target, rowIndex );
        var row = opts.finder.getRow( target, rowIndex );
        if( !tr.hasClass( "datagrid-row-editing" ) ) {
            return;
        }
        if( !revert ) {
            if( !validateRow( target, rowIndex ) ) {
                return;
            }
            var changed = false;
            var newValues = {};
            tr.find( "div.datagrid-editable" ).each( function() {
                var field = $( this ).parent().attr( "field" );
                var ed = $.data( this, "datagrid.editor" );
                var value = ed.actions.getValue( ed.target );
                if( row[field] != value ) {
                    row[field] = value;
                    changed = true;
                    newValues[field] = value;
                }
            } );
            if( changed ) {
                if( _2( insertedRows, row ) == -1 ) {
                    if( _2( updatedRows, row ) == -1 ) {
                        updatedRows.push( row );
                    }
                }
            }
        }
        tr.removeClass( "datagrid-row-editing" );
        destroyEditor( target, rowIndex );
        $( target ).datagrid( "refreshRow", rowIndex );
        if( !revert ) {
            opts.onAfterEdit.call( target, rowIndex, row, newValues );
        } else {
            opts.onCancelEdit.call( target, rowIndex, row );
        }
    }
    ;
    function getEditors( target, rowIndex ) {
        var opts = $.data( target, "datagrid" ).options;
        var tr = opts.finder.getTr( target, rowIndex );
        var editors = [];
        tr.children( "td" ).each( function() {
            var cell = $( this ).find( "div.datagrid-editable" );
            if( cell.length ) {
                var ed = $.data( cell[0], "datagrid.editor" );
                editors.push( ed );
            }
        } );
        return editors;
    }
    ;
    function getEditor( target, options ) {
        var editors = getEditors( target, options.index );
        for( var i = 0; i < editors.length; i++ ) {
            if( editors[i].field == options.field ) {
                return editors[i];
            }
        }
        return null;
    }
    ;
    function createEditor( target, rowIndex ) {
        var opts = $.data( target, "datagrid" ).options;
        var tr = opts.finder.getTr( target, rowIndex );
        tr.children( "td" ).each( function() {
            var cell = $( this ).find( "div.datagrid-cell" );
            var field = $( this ).attr( "field" );
            var col = getColumnOption( target, field );
            if( col && col.editor ) {
                var type, editorOpts;
                if( typeof col.editor == "string" ) {
                    type = col.editor;
                } else {
                    type = col.editor.type;
                    editorOpts = col.editor.options;
                }
                var editor = opts.editors[type];
                if( editor ) {
                    var html = cell.html();
                    var width = cell.outerWidth();
                    cell.addClass( "datagrid-editable" );
                    cell._outerWidth( width );
                    cell.html( "<table border=\"0\" cellspacing=\"0\" cellpadding=\"1\"><tr><td></td></tr></table>" );
                    cell.children( "table" ).attr( "align", col.align );
                    cell.children( "table" ).bind( "click dblclick contextmenu", function( e ) {
                        e.stopPropagation();
                    } );
                    $.data( cell[0], "datagrid.editor", {
                        actions : editor,
                        target : editor.init( cell.find( "td" ), editorOpts ),
                        field : field,
                        type : type,
                        oldHtml : html
                    } );
                }
            }
        } );
        fixRowHeight( target, rowIndex, true );
    }
    ;
    function destroyEditor( target, rowIndex ) {
        var opts = $.data( target, "datagrid" ).options;
        var tr = opts.finder.getTr( target, rowIndex );
        tr.children( "td" ).each( function() {
            var cell = $( this ).find( "div.datagrid-editable" );
            if( cell.length ) {
                var ed = $.data( cell[0], "datagrid.editor" );
                if( ed.actions.destroy ) {
                    ed.actions.destroy( ed.target );
                }
                cell.html( ed.oldHtml );
                $.removeData( cell[0], "datagrid.editor" );
                cell.removeClass( "datagrid-editable" );
                cell.css( "width", "" );
            }
        } );
    }
    ;
    function validateRow( target, rowIndex ) {
        var tr = $.data( target, "datagrid" ).options.finder.getTr( target, rowIndex );
        if( !tr.hasClass( "datagrid-row-editing" ) ) {
            return true;
        }
        var vbox = tr.find( ".validatebox-text" );
        vbox.validatebox( "validate" );
        vbox.trigger( "mouseleave" );
        var rowIndex = tr.find( ".validatebox-invalid" );
        return rowIndex.length == 0;
    }
    ;
    function getChanges( target, type ) {
        var insertedRows = $.data( target, "datagrid" ).insertedRows;
        var deletedRows = $.data( target, "datagrid" ).deletedRows;
        var updatedRows = $.data( target, "datagrid" ).updatedRows;
        if( !type ) {
            var rows = [];
            rows = rows.concat( insertedRows );
            rows = rows.concat( deletedRows );
            rows = rows.concat( updatedRows );
            return rows;
        } else {
            if( type == "inserted" ) {
                return insertedRows;
            } else {
                if( type == "deleted" ) {
                    return deletedRows;
                } else {
                    if( type == "updated" ) {
                        return updatedRows;
                    }
                }
            }
        }
        return [];
    }
    ;
    function deleteRow( target, rowIndex ) {
        var opts = $.data( target, "datagrid" ).options;
        var data = $.data( target, "datagrid" ).data;
        var insertedRows = $.data( target, "datagrid" ).insertedRows;
        var deletedRows = $.data( target, "datagrid" ).deletedRows;
        var selectedRows = $.data( target, "datagrid" ).selectedRows;
        $( target ).datagrid( "cancelEdit", rowIndex );
        var row = data.rows[rowIndex];
        if( _2( insertedRows, row ) >= 0 ) {
            _4( insertedRows, row );
        } else {
            deletedRows.push( row );
        }
        _4( selectedRows, opts.idField, data.rows[rowIndex][opts.idField] );
        opts.view.deleteRow.call( opts.view, target, rowIndex );
        if( opts.height == "auto" ) {
            fixRowHeight( target );
        }
    }
    ;
    function insertRow( target, param ) {
        var view = $.data( target, "datagrid" ).options.view;
        var insertedRows = $.data( target, "datagrid" ).insertedRows;
        view.insertRow.call( view, target, param.index, param.row );
        insertedRows.push( param.row );
    }
    ;
    function appendRow( target, row ) {
        var view = $.data( target, "datagrid" ).options.view;
        var insertedRows = $.data( target, "datagrid" ).insertedRows;
        view.insertRow.call( view, target, null, row );
        insertedRows.push( row );
    }
    ;
    function resetOperation( target ) {
        var data = $.data( target, "datagrid" ).data;
        var rows = data.rows;
        var originalRows = [];
        for( var i = 0; i < rows.length; i++ ) {
            originalRows.push( $.extend( {}, rows[i] ) );
        }
        $.data( target, "datagrid" ).originalRows = originalRows;
        $.data( target, "datagrid" ).updatedRows = [];
        $.data( target, "datagrid" ).insertedRows = [];
        $.data( target, "datagrid" ).deletedRows = [];
    }
    ;
    function acceptChanges( target ) {
        var data = $.data( target, "datagrid" ).data;
        var ok = true;
        for( var i = 0, len = data.rows.length; i < len; i++ ) {
            if( validateRow( target, i ) ) {
                stopEdit( target, i, false );
            } else {
                ok = false;
            }
        }
        if( ok ) {
            resetOperation( target );
        }
    }
    ;
    function rejectChanges( target ) {
        var opts = $.data( target, "datagrid" ).options;
        var originalRows = $.data( target, "datagrid" ).originalRows;
        var insertedRows = $.data( target, "datagrid" ).insertedRows;
        var deletedRows = $.data( target, "datagrid" ).deletedRows;
        var selectedRows = $.data( target, "datagrid" ).selectedRows;
        var data = $.data( target, "datagrid" ).data;
        for( var i = 0; i < data.rows.length; i++ ) {
            stopEdit( target, i, true );
        }
        var records = [];
        for( var i = 0; i < selectedRows.length; i++ ) {
            records.push( selectedRows[i][opts.idField] );
        }
        selectedRows.splice( 0, selectedRows.length );
        data.total += deletedRows.length - insertedRows.length;
        data.rows = originalRows;
        renderGrid( target, data );
        for( var i = 0; i < records.length; i++ ) {
            selectRecord( target, records[i] );
        }
        resetOperation( target );
    }
    ;
    function request( target, param ) {
        var opts = $.data( target, "datagrid" ).options;
        if( param ) {
            opts.queryParams = param;
        }
        // CUSTOM : 处理 param 是 serialize 字符串的情况
        var params = opts.queryParams;
        if( typeof opts.queryParams == "string" ) {
            if( opts.pagination ) {
                params = ( "page=" + opts.pageNumber + "&rows=" + opts.pageSize + "&" + params );
            }
            if( opts.sortName ) {
                $.extend( params, {
                    sort : opts.sortName,
                    order : opts.sortOrder
                } );
                params = ( "sort=" + opts.sortName + "&order=" + opts.sortOrder + "&" + params );
            }
        } else {
            params = $.extend( {}, opts.queryParams );
            if( opts.pagination ) {
                $.extend( params, {
                    page : opts.pageNumber,
                    rows : opts.pageSize
                } );
            }
            if( opts.sortName ) {
                $.extend( params, {
                    sort : opts.sortName,
                    order : opts.sortOrder
                } );
            }
        }
        
        if( opts.onBeforeLoad.call( target, params ) == false ) {
            return;
        }
        $( target ).datagrid( "loading" );
        setTimeout( function() {
            doRequest();
        }, 0 );
        function doRequest() {
            var result = opts.loader.call( target, params, function( data ) {
                setTimeout( function() {
                    $( target ).datagrid( "loaded" );
                }, 0 );
                renderGrid( target, data );
                setTimeout( function() {
                    resetOperation( target );
                }, 0 );
            }, function() {
                setTimeout( function() {
                    $( target ).datagrid( "loaded" );
                }, 0 );
                opts.onLoadError.apply( target, arguments );
            } );
            if( result == false ) {
                $( target ).datagrid( "loaded" );
            }
        }
        ;
    }
    ;
    function mergeCells( target, options ) {
        var opts = $.data( target, "datagrid" ).options;
        var rows = $.data( target, "datagrid" ).data.rows;
        options.rowspan = options.rowspan || 1;
        options.colspan = options.colspan || 1;
        if( options.index < 0 || options.index >= rows.length ) {
            return;
        }
        if( options.rowspan == 1 && options.colspan == 1 ) {
            return;
        }
        var field = rows[options.index][options.field];
        var tr = opts.finder.getTr( target, options.index );
        var td = tr.find( "td[field=\"" + options.field + "\"]" );
        td.attr( "rowspan", options.rowspan ).attr( "colspan", options.colspan );
        td.addClass( "datagrid-td-merged" );
        for( var i = 1; i < options.colspan; i++ ) {
            td = td.next();
            td.hide();
            rows[options.index][td.attr( "field" )] = field;
        }
        for( var i = 1; i < options.rowspan; i++ ) {
            tr = tr.next();
            var td = tr.find( "td[field=\"" + options.field + "\"]" ).hide();
            rows[options.index + i][td.attr( "field" )] = field;
            for( var j = 1; j < options.colspan; j++ ) {
                td = td.next();
                td.hide();
                rows[options.index + i][td.attr( "field" )] = field;
            }
        }
        fixMergedCellsSize( target );
    }
    ;
    // CUSTOM : 绑定热键
    function bindHotkey( target ) {
        $(document).bind( 'keydown', '0 1 2 3 4 5 6 7 8 9', function( evt ) {
            selectRow( target, evt.keyCode - 49 );  
        } );
        $(document).bind( 'keydown', 'up down', function(evt){
            var down = ( event.keyCode == 40 );
            var count = $.data( target, "datagrid" ).data.rows.length;
            if( count > 0 ) {
                var row = getSelected( target )[0];
                var index = ( down ? 0 : count - 1 );
                if( row ) {
                    var rowIndex = getRowIndex( target, row );
                    if( down ) {
                        if( rowIndex < count - 1 ) index = rowIndex + 1;
                    } else {
                         if( rowIndex > 0 ) index = rowIndex - 1;
                    }
                }
                selectRow( target, index );
            }
        } );
    }
    ;
    $.fn.datagrid = function( options, param ) {
        if( typeof options == "string" ) {
            return $.fn.datagrid.methods[options]( this, param );
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "datagrid" );
            var opts;
            if( state ) {
                opts = $.extend( state.options, options );
                state.options = opts;
            } else {
                opts = $.extend( {}, $.extend( {}, $.fn.datagrid.defaults, {
                    queryParams : {}
                } ), $.fn.datagrid.parseOptions( this ), options );
                $( this ).css( "width", "" ).css( "height", "" );
                var gridWrap = wrapGrid( this, opts.rownumbers );
                if( !opts.columns ) {
                    opts.columns = gridWrap.columns;
                }
                if( !opts.frozenColumns ) {
                    opts.frozenColumns = gridWrap.frozenColumns;
                }
                $.data( this, "datagrid", {
                    options : opts,
                    panel : gridWrap.panel,
                    dc : gridWrap.dc,
                    selectedRows : [],
                    data : {
                        total : 0,
                        rows : []
                    },
                    originalRows : [],
                    updatedRows : [],
                    insertedRows : [],
                    deletedRows : []
                } );
            }
            init( this );
            if( !state ) {
                var data = getGridData( this );
                if( data.total > 0 ) {
                    renderGrid( this, data );
                    resetOperation( this );
                }
            }
            setSize( this );
            request( this );
            setProperties( this );
            
            // CUSTOM : 绑定热键
            if( opts.hotkey ) {
                bindHotkey( this );
            }
        } );
    };
    var editors = {
        text : {
            init : function( container, options ) {
                var editor = $( "<input type=\"text\" class=\"datagrid-editable-input\">" ).appendTo( container );
                return editor;
            },
            getValue : function( target ) {
                return $( target ).val();
            },
            setValue : function( target, value ) {
                $( target ).val( value );
            },
            resize : function( target, width ) {
                $( target )._outerWidth( width );
            }
        },
        textarea : {
            init : function( container, options ) {
                var editor = $( "<textarea class=\"datagrid-editable-input\"></textarea>" ).appendTo( container );
                return editor;
            },
            getValue : function( target ) {
                return $( target ).val();
            },
            setValue : function( target, value ) {
                $( target ).val( value );
            },
            resize : function( target, width ) {
                $( target )._outerWidth( width );
            }
        },
        checkbox : {
            init : function( container, options ) {
                var editor = $( "<input type=\"checkbox\">" ).appendTo( container );
                editor.val( options.on );
                editor.attr( "offval", options.off );
                return editor;
            },
            getValue : function( target ) {
                if( $( target ).is( ":checked" ) ) {
                    return $( target ).val();
                } else {
                    return $( target ).attr( "offval" );
                }
            },
            setValue : function( target, value ) {
                var checked = false;
                if( $( target ).val() == value ) {
                    checked = true;
                }
                $.fn.prop ? $( target ).prop( "checked", checked ) : $( target ).attr( "checked", checked );
            }
        },
        numberbox : {
            init : function( container, options ) {
                var editor = $( "<input type=\"text\" class=\"datagrid-editable-input\">" ).appendTo( container );
                editor.numberbox( options );
                return editor;
            },
            destroy : function( target ) {
                $( target ).numberbox( "destroy" );
            },
            getValue : function( target ) {
                return $( target ).numberbox( "getValue" );
            },
            setValue : function( target, value ) {
                $( target ).numberbox( "setValue", value );
            },
            resize : function( target, width ) {
                $( target )._outerWidth( width );
            }
        },
        validatebox : {
            init : function( container, options ) {
                var editor = $( "<input type=\"text\" class=\"datagrid-editable-input\">" ).appendTo( container );
                editor.validatebox( options );
                return editor;
            },
            destroy : function( target ) {
                $( target ).validatebox( "destroy" );
            },
            getValue : function( target ) {
                return $( target ).val();
            },
            setValue : function( target, value ) {
                $( target ).val( value );
            },
            resize : function( target, width ) {
                $( target )._outerWidth( width );
            }
        },
        datebox : {
            init : function( container, options ) {
                var editor = $( "<input type=\"text\">" ).appendTo( container );
                editor.datebox( options );
                return editor;
            },
            destroy : function( target ) {
                $( target ).datebox( "destroy" );
            },
            getValue : function( target ) {
                return $( target ).datebox( "getValue" );
            },
            setValue : function( target, value ) {
                $( target ).datebox( "setValue", value );
            },
            resize : function( target, width ) {
                $( target ).datebox( "resize", width );
            }
        },
        combobox : {
            init : function( container, options ) {
                var editor = $( "<input type=\"text\">" ).appendTo( container );
                editor.combobox( options || {} );
                return editor;
            },
            destroy : function( target ) {
                $( target ).combobox( "destroy" );
            },
            getValue : function( target ) {
                return $( target ).combobox( "getValue" );
            },
            setValue : function( target, value ) {
                $( target ).combobox( "setValue", value );
            },
            resize : function( target, width ) {
                $( target ).combobox( "resize", width );
            }
        },
        combotree : {
            init : function( container, options ) {
                var editor = $( "<input type=\"text\">" ).appendTo( container );
                editor.combotree( options );
                return editor;
            },
            destroy : function( target ) {
                $( target ).combotree( "destroy" );
            },
            getValue : function( target ) {
                return $( target ).combotree( "getValue" );
            },
            setValue : function( target, value ) {
                $( target ).combotree( "setValue", value );
            },
            resize : function( target, width ) {
                $( target ).combotree( "resize", width );
            }
        }
    };
    $.fn.datagrid.methods = {
        options : function( jq ) {
            var gridOpts = $.data( jq[0], "datagrid" ).options;
            var panelOpts = $.data( jq[0], "datagrid" ).panel.panel( "options" );
            var opts = $.extend( gridOpts, {
                width : panelOpts.width,
                height : panelOpts.height,
                closed : panelOpts.closed,
                collapsed : panelOpts.collapsed,
                minimized : panelOpts.minimized,
                maximized : panelOpts.maximized
            } );
            var pager = jq.datagrid( "getPager" );
            if( pager.length ) {
                var pagerOpts = pager.pagination( "options" );
                $.extend( opts, {
                    pageNumber : pagerOpts.pageNumber,
                    pageSize : pagerOpts.pageSize
                } );
            }
            return opts;
        },
        getPanel : function( jq ) {
            return $.data( jq[0], "datagrid" ).panel;
        },
        getPager : function( jq ) {
            return $.data( jq[0], "datagrid" ).panel.children( "div.datagrid-pager" );
        },
        getColumnFields : function( jq, frozen ) {
            return getColumnFields( jq[0], frozen );
        },
        getColumnOption : function( jq, field ) {
            return getColumnOption( jq[0], field );
        },
        resize : function( jq, param ) {
            return jq.each( function() {
                setSize( this, param );
            } );
        },
        load : function( jq, param ) {
            return jq.each( function() {
                var opts = $( this ).datagrid( "options" );
                opts.pageNumber = 1;
                var pager = $( this ).datagrid( "getPager" );
                pager.pagination( {
                    pageNumber : 1
                } );
                request( this, param );
            } );
        },
        reload : function( jq, param ) {
            return jq.each( function() {
                request( this, param );
            } );
        },
        reloadFooter : function( jq, footer ) {
            return jq.each( function() {
                var opts = $.data( this, "datagrid" ).options;
                var dc = $.data( this, "datagrid" ).dc;
                if( footer ) {
                    $.data( this, "datagrid" ).footer = footer;
                }
                if( opts.showFooter ) {
                    opts.view.renderFooter.call( opts.view, this, dc.footer2, false );
                    opts.view.renderFooter.call( opts.view, this, dc.footer1, true );
                    if( opts.view.onAfterRender ) {
                        opts.view.onAfterRender.call( opts.view, this );
                    }
                    $( this ).datagrid( "fixRowHeight" );
                }
            } );
        },
        loading : function( jq ) {
            return jq.each( function() {
                var opts = $.data( this, "datagrid" ).options;
                $( this ).datagrid( "getPager" ).pagination( "loading" );
                if( opts.loadMsg ) {
                    var wrap = $( this ).datagrid( "getPanel" );
                    $( "<div class=\"datagrid-mask\" style=\"display:block\"></div>" ).appendTo( wrap );
                    var msg = $( "<div class=\"datagrid-mask-msg\" style=\"display:block\"></div>" ).html( opts.loadMsg )
                            .appendTo( wrap );
                    msg.css( "left", ( wrap.width() - msg.outerWidth() ) / 2 );
                }
            } );
        },
        loaded : function( jq ) {
            return jq.each( function() {
                $( this ).datagrid( "getPager" ).pagination( "loaded" );
                var wrap = $( this ).datagrid( "getPanel" );
                wrap.children( "div.datagrid-mask-msg" ).remove();
                wrap.children( "div.datagrid-mask" ).remove();
            } );
        },
        fitColumns : function( jq ) {
            return jq.each( function() {
                fitColumns( this );
            } );
        },
        fixColumnSize : function( jq, _19b ) {
            return jq.each( function() {
                fixColumnSize( this, _19b );
            } );
        },
        fixRowHeight : function( jq, index ) {
            return jq.each( function() {
                fixRowHeight( this, index );
            } );
        },
        autoSizeColumn : function( jq, width ) {
            return jq.each( function() {
                autoSizeColumn( this, width );
            } );
        },
        loadData : function( jq, data ) {
            return jq.each( function() {
                renderGrid( this, data );
                resetOperation( this );
            } );
        },
        getData : function( jq ) {
            return $.data( jq[0], "datagrid" ).data;
        },
        getRows : function( jq ) {
            return $.data( jq[0], "datagrid" ).data.rows;
        },
        getFooterRows : function( jq ) {
            return $.data( jq[0], "datagrid" ).footer;
        },
        getRowIndex : function( jq, id ) {
            return getRowIndex( jq[0], id );
        },
        getChecked : function( jq ) {
            var rr = [];
            var rows = jq.datagrid( "getRows" );
            var dc = $.data( jq[0], "datagrid" ).dc;
            dc.view.find( "div.datagrid-cell-check input:checked" ).each( function() {
                var rowIndex = $( this ).parents( "tr.datagrid-row:first" ).attr( "datagrid-row-index" );
                rr.push( rows[rowIndex] );
            } );
            return rr;
        },
        getSelected : function( jq ) {
            var rows = getSelected( jq[0] );
            return rows.length > 0 ? rows[0] : null;
        },
        getSelections : function( jq ) {
            return getSelected( jq[0] );
        },
        clearSelections : function( jq ) {
            return jq.each( function() {
                var selectedRows = $.data( this, "datagrid" ).selectedRows;
                selectedRows.splice( 0, selectedRows.length );
                unselectAll( this );
            } );
        },
        selectAll : function( jq ) {
            return jq.each( function() {
                selectAll( this );
            } );
        },
        unselectAll : function( jq ) {
            return jq.each( function() {
                unselectAll( this );
            } );
        },
        selectRow : function( jq, index ) {
            return jq.each( function() {
                selectRow( this, index );
            } );
        },
        selectRecord : function( jq, id ) {
            return jq.each( function() {
                selectRecord( this, id );
            } );
        },
        unselectRow : function( jq, index ) {
            return jq.each( function() {
                unselectRow( this, index );
            } );
        },
        checkRow : function( jq, index ) {
            return jq.each( function() {
                checkRow( this, index );
            } );
        },
        uncheckRow : function( jq, index ) {
            return jq.each( function() {
                uncheckRow( this, index );
            } );
        },
        checkAll : function( jq ) {
            return jq.each( function() {
                checkAll( this );
            } );
        },
        uncheckAll : function( jq ) {
            return jq.each( function() {
                uncheckAll( this );
            } );
        },
        beginEdit : function( jq, index ) {
            return jq.each( function() {
                beginEdit( this, index );
            } );
        },
        endEdit : function( jq, index ) {
            return jq.each( function() {
                stopEdit( this, index, false );
            } );
        },
        cancelEdit : function( jq, index ) {
            return jq.each( function() {
                stopEdit( this, index, true );
            } );
        },
        getEditors : function( jq, index ) {
            return getEditors( jq[0], index );
        },
        getEditor : function( jq, options ) {
            return getEditor( jq[0], options );
        },
        refreshRow : function( jq, index ) {
            return jq.each( function() {
                var opts = $.data( this, "datagrid" ).options;
                opts.view.refreshRow.call( opts.view, this, index );
            } );
        },
        validateRow : function( jq, index ) {
            return validateRow( jq[0], index );
        },
        updateRow : function( jq, param ) {
            return jq.each( function() {
                var opts = $.data( this, "datagrid" ).options;
                opts.view.updateRow.call( opts.view, this, param.index, param.row );
            } );
        },
        appendRow : function( jq, row ) {
            return jq.each( function() {
                appendRow( this, row );
            } );
        },
        insertRow : function( jq, param ) {
            return jq.each( function() {
                insertRow( this, param );
            } );
        },
        deleteRow : function( jq, _1ad ) {
            return jq.each( function() {
                deleteRow( this, _1ad );
            } );
        },
        getChanges : function( jq, type ) {
            return getChanges( jq[0], type );
        },
        acceptChanges : function( jq ) {
            return jq.each( function() {
                acceptChanges( this );
            } );
        },
        rejectChanges : function( jq ) {
            return jq.each( function() {
                rejectChanges( this );
            } );
        },
        mergeCells : function( jq, options ) {
            return jq.each( function() {
                mergeCells( this, options );
            } );
        },
        showColumn : function( jq, field ) {
            return jq.each( function() {
                var panel = $( this ).datagrid( "getPanel" );
                panel.find( "td[field=\"" + field + "\"]" ).show();
                $( this ).datagrid( "getColumnOption", field ).hidden = false;
                $( this ).datagrid( "fitColumns" );
            } );
        },
        hideColumn : function( jq, field ) {
            return jq.each( function() {
                var panel = $( this ).datagrid( "getPanel" );
                panel.find( "td[field=\"" + field + "\"]" ).hide();
                $( this ).datagrid( "getColumnOption", field ).hidden = true;
                $( this ).datagrid( "fitColumns" );
            } );
        }
    };
    $.fn.datagrid.parseOptions = function( target ) {
        var t = $( target );
        return $.extend( {}, $.fn.panel.parseOptions( target ), $.parser.parseOptions( target, ["url", "toolbar", "idField",
                "sortName", "sortOrder", "pagePosition", {
                    fitColumns : "boolean",
                    autoRowHeight : "boolean",
                    striped : "boolean",
                    nowrap : "boolean"
                }, {
                    rownumbers : "boolean",
                    singleSelect : "boolean",
                    checkOnSelect : "boolean",
                    selectOnCheck : "boolean"
                }, {
                    pagination : "boolean",
                    pageSize : "number",
                    pageNumber : "number"
                }, {
                    remoteSort : "boolean",
                    showHeader : "boolean",
                    showFooter : "boolean"
                }, {
                    scrollbarSize : "number"
                }] ), {
            pageList : ( t.attr( "pageList" ) ? eval( t.attr( "pageList" ) ) : undefined ),
            loadMsg : ( t.attr( "loadMsg" ) != undefined ? t.attr( "loadMsg" ) : undefined ),
            rowStyler : ( t.attr( "rowStyler" ) ? eval( t.attr( "rowStyler" ) ) : undefined )
        } );
    };
    /**
     * The view is an object that will tell datagrid how to render rows. 
     * The object must defines the following functions:
     *      render
     *      renderFooter
     *      renderRow
     *      refreshRow
     *      onBeforeRender
     *      onAfterRender
     */
    var view = {
            /** Called when the data is loaded. */
        render : function( target, container, frozen ) {
            var state = $.data( target, "datagrid" );
            var opts = state.options;
            var rows = state.data.rows;
            var fields = $( target ).datagrid( "getColumnFields", frozen );
            if( frozen ) {
                if( !( opts.rownumbers || ( opts.frozenColumns && opts.frozenColumns.length ) ) ) {
                    return;
                }
            }
            var html = ["<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
            for( var i = 0; i < rows.length; i++ ) {
                var cls = ( i % 2 && opts.striped ) ? "class=\"datagrid-row datagrid-row-alt\"" : "class=\"datagrid-row\"";
                var style = opts.rowStyler ? opts.rowStyler.call( target, i, rows[i] ) : "";
                style = style ? "style=\"" + style + "\"" : "";
                var rowId = state.rowIdPrefix + "-" + ( frozen ? 1 : 2 ) + "-" + i;
                html.push( "<tr id=\"" + rowId + "\" datagrid-row-index=\"" + i + "\" " + cls + " " + style + ">" );
                html.push( this.renderRow.call( this, target, fields, frozen, i, rows[i] ) );
                html.push( "</tr>" );
            }
            html.push( "</tbody></table>" );
            $( container ).html( html.join( "" ) );
        },
        renderFooter : function( target, container, frozen ) {
            var opts = $.data( target, "datagrid" ).options;
            var rows = $.data( target, "datagrid" ).footer || [];
            var fields = $( target ).datagrid( "getColumnFields", frozen );
            var html = ["<table class=\"datagrid-ftable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
            for( var i = 0; i < rows.length; i++ ) {
                html.push( "<tr class=\"datagrid-row\" datagrid-row-index=\"" + i + "\">" );
                html.push( this.renderRow.call( this, target, fields, frozen, i, rows[i] ) );
                html.push( "</tr>" );
            }
            html.push( "</tbody></table>" );
            $( container ).html( html.join( "" ) );
        },
        /** This is an option function and will be called by render function. */
        renderRow : function( target, fields, frozen, rowIndex, rowData ) {
            var opts = $.data( target, "datagrid" ).options;
            var cc = [];
            if( frozen && opts.rownumbers ) {
                var rowNumber = rowIndex + 1;
                if( opts.pagination ) {
                    rowNumber += ( opts.pageNumber - 1 ) * opts.pageSize;
                }
                cc.push( "<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">" + rowNumber + "</div></td>" );
            }
            for( var i = 0; i < fields.length; i++ ) {
                var field = fields[i];
                var col = $( target ).datagrid( "getColumnOption", field );
                if( col ) {
                    var _1cb = col.styler ? ( col.styler( rowData[field], rowData, rowIndex ) || "" ) : "";
                    var style = col.hidden ? "style=\"display:none;" + _1cb + "\"" : ( _1cb ? "style=\"" + _1cb + "\"" : "" );
                    cc.push( "<td field=\"" + field + "\" " + style + ">" );
                    style = "";
                    if( !col.checkbox ) {
                        style += "text-align:" + ( col.align || "left" ) + ";";
                        if( !opts.nowrap ) {
                            style += "white-space:normal;height:auto;";
                        } else {
                            if( opts.autoRowHeight ) {
                                style += "height:auto;";
                            }
                        }
                    }
                    cc.push( "<div style=\"" + style + "\" " );
                    if( col.checkbox ) {
                        cc.push( "class=\"datagrid-cell-check " );
                    } else {
                        cc.push( "class=\"datagrid-cell " + col.cellClass );
                    }
                    cc.push( "\">" );
                    if( col.checkbox ) {
                        cc.push( "<input type=\"checkbox\" name=\"" + field + "\" value=\""
                                + ( rowData[field] != undefined ? rowData[field] : "" ) + "\"/>" );
                    } else {
                        if( col.formatter ) {
                            cc.push( col.formatter( rowData[field], rowData, rowIndex ) );
                        } else {
                            cc.push( rowData[field] );
                        }
                    }
                    cc.push( "</div>" );
                    cc.push( "</td>" );
                }
            }
            return cc.join( "" );
        },
        /** Defines how to refresh the specified row. */
        refreshRow : function( target, rowIndex ) {
            var row = {};
            var fields = $( target ).datagrid( "getColumnFields", true ).concat( $( target ).datagrid( "getColumnFields", false ) );
            for( var i = 0; i < fields.length; i++ ) {
                row[fields[i]] = undefined;
            }
            var rows = $( target ).datagrid( "getRows" );
            $.extend( row, rows[rowIndex] );
            this.updateRow.call( this, target, rowIndex, row );
        },
        updateRow : function( target, rowIndex, row ) {
            var opts = $.data( target, "datagrid" ).options;
            var rows = $( target ).datagrid( "getRows" );
            var tr = opts.finder.getTr( target, rowIndex );
            for( var field in row ) {
                rows[rowIndex][field] = row[field];
                var td = tr.children( "td[field=\"" + field + "\"]" );
                var cell = td.find( "div.datagrid-cell" );
                var col = $( target ).datagrid( "getColumnOption", field );
                if( col ) {
                    var style = col.styler ? col.styler( rows[rowIndex][field], rows[rowIndex], rowIndex ) : "";
                    td.attr( "style", style || "" );
                    if( col.hidden ) {
                        td.hide();
                    }
                    if( col.formatter ) {
                        cell.html( col.formatter( rows[rowIndex][field], rows[rowIndex], rowIndex ) );
                    } else {
                        cell.html( rows[rowIndex][field] );
                    }
                }
            }
            var style = opts.rowStyler ? opts.rowStyler.call( target, rowIndex, rows[rowIndex] ) : "";
            tr.attr( "style", style || "" );
            $( target ).datagrid( "fixRowHeight", rowIndex );
        },
        insertRow : function( target, rowIndex, row ) {
            var opts = $.data( target, "datagrid" ).options;
            var dc = $.data( target, "datagrid" ).dc;
            var data = $.data( target, "datagrid" ).data;
            if( rowIndex == undefined || rowIndex == null ) {
                rowIndex = data.rows.length;
            }
            if( rowIndex > data.rows.length ) {
                rowIndex = data.rows.length;
            }
            for( var i = data.rows.length - 1; i >= rowIndex; i-- ) {
                opts.finder.getTr( target, i, "body", 2 ).attr( "datagrid-row-index", i + 1 );
                var tr = opts.finder.getTr( target, i, "body", 1 ).attr( "datagrid-row-index", i + 1 );
                if( opts.rownumbers ) {
                    tr.find( "div.datagrid-cell-rownumber" ).html( i + 2 );
                }
            }
            var frozenFields = $( target ).datagrid( "getColumnFields", true );
            var fields = $( target ).datagrid( "getColumnFields", false );
            var tr1 = "<tr class=\"datagrid-row\" datagrid-row-index=\"" + rowIndex + "\">"
                    + this.renderRow.call( this, target, frozenFields, true, rowIndex, row ) + "</tr>";
            var tr2 = "<tr class=\"datagrid-row\" datagrid-row-index=\"" + rowIndex + "\">"
                    + this.renderRow.call( this, target, fields, false, rowIndex, row ) + "</tr>";
            if( rowIndex >= data.rows.length ) {
                if( data.rows.length ) {
                    opts.finder.getTr( target, "", "last", 1 ).after( tr1 );
                    opts.finder.getTr( target, "", "last", 2 ).after( tr2 );
                } else {
                    dc.body1
                            .html( "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" + tr1 + "</tbody></table>" );
                    dc.body2
                            .html( "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" + tr2 + "</tbody></table>" );
                }
            } else {
                opts.finder.getTr( target, rowIndex + 1, "body", 1 ).before( tr1 );
                opts.finder.getTr( target, rowIndex + 1, "body", 2 ).before( tr2 );
            }
            data.total += 1;
            data.rows.splice( rowIndex, 0, row );
            this.refreshRow.call( this, target, rowIndex );
        },
        deleteRow : function( target, rowIndex ) {
            var state = $.data( target, "datagrid" );
            var opts = state.options;
            var data = state.data;
            opts.finder.getTr( target, rowIndex ).remove();
            for( var i = rowIndex + 1; i < data.rows.length; i++ ) {
                var tr2 = opts.finder.getTr( target, i, "body", 2 ).attr( "datagrid-row-index", i - 1 );
                var tr1 = opts.finder.getTr( target, i, "body", 1 ).attr( "datagrid-row-index", i - 1 );
                if( opts.rownumbers ) {
                    tr1.find( "div.datagrid-cell-rownumber" ).html( i );
                }
                // CUSTOM 修复bug, 删除行时, id 属性中相应行号的部分也需要更新
                tr1.attr("id", state.rowIdPrefix + "-" + 1 + "-" + ( i - 1 ) );
                tr2.attr("id", state.rowIdPrefix + "-" + 2 + "-" + ( i - 1 ) );
            }
            data.total -= 1;
            data.rows.splice( rowIndex, 1 );
        },
        /** Fires before the view is rendered. */
        onBeforeRender : function( target, rows ) {
        },
        /** Fires after the view is rendered. */
        onAfterRender : function( target ) {
            var opts = $.data( target, "datagrid" ).options;
            if( opts.showFooter ) {
                var footer = $( target ).datagrid( "getPanel" ).find( "div.datagrid-footer" );
                footer.find( "div.datagrid-cell-rownumber,div.datagrid-cell-check" ).css( "visibility", "hidden" );
            }
        }
    };
    $.fn.datagrid.defaults = $.extend( {}, $.fn.panel.defaults,
            {
                frozenColumns : undefined,
                columns : undefined,
                fitColumns : true,
                autoRowHeight : true,
                toolbar : null,
                striped : true,
                method : "post",
                nowrap : true,
                idField : null,
                url : null,
                loadMsg : "Processing, please wait ...",
                // CUSTOM : 没有获取任何数据时显示的空数据提示
                loadEmptyMsg : "Could not found any record. ",
                // CUSTOM : 是否绑定热键 '1~9 up down'
                hotkey : true,
                rownumbers : true,
                singleSelect : true,
                selectOnCheck : true,
                checkOnSelect : true,
                pagination : true,
                pagePosition : "bottom",
                pageNumber : 1,
                pageSize : 20,
                pageList : [5, 10, 20, 30, 50, 100, 200, 500],
                queryParams : {},
                sortName : null,
                sortOrder : "asc",
                remoteSort : true,
                showHeader : true,
                showFooter : false,
                scrollbarSize : 18,
                rowStyler : function( rowIndex, rowData ) {
                },
                loader : function( params, loadSuccess, loadError ) {
                    var opts = $( this ).datagrid( "options" );
                    if( !opts.url ) {
                        return false;
                    }
                    $.ajax( {
                        type : opts.method,
                        url : opts.url,
                        data : params,
                        dataType : "json",
                        success : function( data ) {
                            loadSuccess( data );
                        },
                        error : function() {
                            loadError.apply( this, arguments );
                        }
                    } );
                },
                loadFilter : function( data ) {
                    if( typeof data.length == "number" && typeof data.splice == "function" ) {
                        return {
                            total : data.length,
                            rows : data
                        };
                    } else {
                        return data;
                    }
                },
                editors : editors,
                finder : {
                    getTr : function( target, rowIndex, type, _1e4 ) {
                        type = type || "body";
                        _1e4 = _1e4 || 0;
                        var data = $.data( target, "datagrid" );
                        var dc = data.dc;
                        var opts = data.options;
                        if( _1e4 == 0 ) {
                            var tr1 = opts.finder.getTr( target, rowIndex, type, 1 );
                            var tr2 = opts.finder.getTr( target, rowIndex, type, 2 );
                            return tr1.add( tr2 );
                        } else {
                            if( type == "body" ) {
                                var tr = $( "#" + data.rowIdPrefix + "-" + _1e4 + "-" + rowIndex );
                                if( !tr.length ) {
                                    tr = ( _1e4 == 1 ? dc.body1 : dc.body2 ).find( ">table>tbody>tr[datagrid-row-index=" + rowIndex
                                            + "]" );
                                }
                                return tr;
                            } else {
                                if( type == "footer" ) {
                                    return ( _1e4 == 1 ? dc.footer1 : dc.footer2 ).find( ">table>tbody>tr[datagrid-row-index="
                                            + rowIndex + "]" );
                                } else {
                                    if( type == "selected" ) {
                                        return ( _1e4 == 1 ? dc.body1 : dc.body2 )
                                                .find( ">table>tbody>tr.datagrid-row-selected" );
                                    } else {
                                        if( type == "last" ) {
                                            return ( _1e4 == 1 ? dc.body1 : dc.body2 )
                                                    .find( ">table>tbody>tr:last[datagrid-row-index]" );
                                        } else {
                                            if( type == "allbody" ) {
                                                return ( _1e4 == 1 ? dc.body1 : dc.body2 )
                                                        .find( ">table>tbody>tr[datagrid-row-index]" );
                                            } else {
                                                if( type == "allfooter" ) {
                                                    return ( _1e4 == 1 ? dc.footer1 : dc.footer2 )
                                                            .find( ">table>tbody>tr[datagrid-row-index]" );
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    getRow : function( _1e6, _1e7 ) {
                        return $.data( _1e6, "datagrid" ).data.rows[_1e7];
                    }
                },
                view : view,
                /**
                 * Fires before a request is made to load data. If return false
                 * the load action will be canceled.
                 */
                onBeforeLoad : function( param ) {
                },
                /** Fires when data is loaded successfully. */
                onLoadSuccess : function( data ) {
                },
                /** Fires when some error occur to load remote data. */
                onLoadError : function() {
                },
                /** Custom : Fires when load nothing. */
                onLoadEmpty : function() {                   
                },
                /**
                 * Fires when user click a row, the parameters contains:
                 * rowIndex: the clicked row index, start with 0 rowData: the
                 * record corresponding to the clicked row
                 */
                onClickRow : function( _1e9, _1ea ) {
                },
                /**
                 * Fires when user dblclick a row, the parameters contains:
                 * rowIndex: the clicked row index, start with 0 rowData: the
                 * record corresponding to the clicked row
                 */
                onDblClickRow : function( rowIndex, rowData ) {
                },
                /** Fires when user click a cell. */
                onClickCell : function( rowIndex, field, value ) {
                },
                /** Fires when user dblclick a cell. */
                onDblClickCell : function( rowIndex, field, value ) {
                },
                /**
                 * Fires when user sort a column, the parameters contains: sort:
                 * the sort column field name order: the sort column order
                 */
                onSortColumn : function( sort, order ) {
                },
                /** Fires when user resize the column. */
                onResizeColumn : function( field, width ) {
                },
                /**
                 * Fires when user select a row, the parameters contains:
                 * rowIndex: the unselected row index, start with 0 rowData: the
                 * record corresponding to the unselected row
                 */
                onSelect : function( rowIndex, rowData ) {
                },
                /**
                 * Fires when user unselect a row, the parameters contains:
                 * rowIndex: the unselected row index, start with 0 rowData: the
                 * record corresponding to the unselected row
                 */
                onUnselect : function( rowIndex, rowData ) {
                },
                /** Fires when user select all rows. */
                onSelectAll : function( rows ) {
                },
                /** Fires when user unselect all rows. */
                onUnselectAll : function( rows ) {
                },
                /**
                 * Fires when user check a row, the parameters contains:
                 * rowIndex: the unselected row index, start with 0 rowData: the
                 * record corresponding to the unselected row
                 */
                onCheck : function( rowIndex, rowData ) {
                },
                /**
                 * Fires when user uncheck a row, the parameters contains:
                 * rowIndex: the unselected row index, start with 0 rowData: the
                 * record corresponding to the unselected row
                 */
                onUncheck : function( rowIndex, rowData ) {
                },
                /** Fires when user check all rows. */
                onCheckAll : function( rows ) {
                },
                /** Fires when user uncheck all rows. */
                onUncheckAll : function( rows ) {
                },
                /**
                 * Fires when user start editing a row, the parameters contains:
                 * rowIndex: the editing row index, start with 0 rowData: the
                 * record corresponding to the editing row
                 */
                onBeforeEdit : function( rowIndex, rowData ) {
                },
                /**
                 * Fires when user finish editing, the parameters contains:
                 * rowIndex: the editing row index, start with 0 rowData: the
                 * record corresponding to the editing row changes: the changed
                 * field/value pairs
                 */
                onAfterEdit : function( rowIndex, rowData, changes ) {
                },
                /**
                 * Fires when user cancel editing a row, the parameters
                 * contains: rowIndex: the editing row index, start with 0
                 * rowData: the record corresponding to the editing row
                 */
                onCancelEdit : function( rowIndex, rowData ) {
                },
                /** Fires when the header of datagrid is right clicked. */
                onHeaderContextMenu : function( e, field ) {
                    // CUSTOM : 默认加入表头选择功能
                    // 自定义了属性 toggleable, 表示表头是否能显示/隐藏, 默认值为 true
                    var showIcon = "c-icon-ok";
                    var hiddenIcon = "";
                    var target = $(this);
                    e.preventDefault();
                    var headerMenu = $.data( this, "datagrid" ).headerMenu;
                    if( !headerMenu ) {
                        headerMenu = $('<div style="width:120px;"></div>').appendTo('body');
                        var fields = target.datagrid('getColumnFields');
                        for(var i=0; i<fields.length; i++){
                            var opts = target.datagrid('getColumnOption', fields[i]);          
                            if( false != opts.toggleable ) {
                                $('<div></div>').html( opts.title ).attr('name', opts.field).attr('iconCls', (opts.hidden ? hiddenIcon : showIcon) ).appendTo(headerMenu);
                            }
                        }
                        headerMenu.menu({
                            onClick: function(item){
                                if (item.iconCls==showIcon){
                                    target.datagrid('hideColumn', item.name);
                                    headerMenu.menu('setIcon', {
                                        target: item.target,
                                        iconCls: hiddenIcon
                                    });
                                } else {
                                    target.datagrid('showColumn', item.name);
                                    headerMenu.menu('setIcon', {
                                        target: item.target,
                                        iconCls: showIcon
                                    });
                                }
                            }
                        });
                        $.data( this, "datagrid" ).headerMenu = headerMenu;
                    }
                    headerMenu.menu('show', {
                        left:e.pageX,
                        top:e.pageY
                    });
                },
                /** Fires when a row is right clicked. */
                onRowContextMenu : function( e, rowIndex, rowData ) {
                }
            } );
} )( jQuery );
