/**
 * layout - jQuery EasyUI 1.3
 * 
 * Licensed under the GPL terms To use it on other terms please contact us
 * 
 * Copyright(c) 2009-2012 stworthy [ stworthy@gmail.com ]
 * 
 */
( function( $ ) {
    var resizing = false;
    function setSize( container ) {
        var opts = $.data( container, "layout" ).options;
        var panels = $.data( container, "layout" ).panels;
        var cc = $( container );
        if( opts.fit == true ) {
            var p = cc.parent();
            p.addClass( "panel-noscroll" );
            if( p[0].tagName == "BODY" ) {
                $( "html" ).addClass( "panel-fit" );
            }
            cc.width( p.width() );
            cc.height( p.height() );
        }
        var cpos = {
            top : 0,
            left : 0,
            width : cc.width(),
            height : cc.height()
        };
        function setNorthSize( pp ) {
            if( pp.length == 0 ) {
                return;
            }
            pp.panel( "resize", {
                width : cc.width(),
                height : pp.panel( "options" ).height,
                left : 0,
                top : 0
            } );
            cpos.top += pp.panel( "options" ).height;
            cpos.height -= pp.panel( "options" ).height;
        }
        ;
        if( isVisible( panels.expandNorth ) ) {
            setNorthSize( panels.expandNorth );
        } else {
            setNorthSize( panels.north );
        }
        function setSouthSize( pp ) {
            if( pp.length == 0 ) {
                return;
            }
            pp.panel( "resize", {
                width : cc.width(),
                height : pp.panel( "options" ).height,
                left : 0,
                top : cc.height() - pp.panel( "options" ).height
            } );
            cpos.height -= pp.panel( "options" ).height;
        }
        ;
        if( isVisible( panels.expandSouth ) ) {
            setSouthSize( panels.expandSouth );
        } else {
            setSouthSize( panels.south );
        }
        function setEastSize( pp ) {
            if( pp.length == 0 ) {
                return;
            }
            pp.panel( "resize", {
                width : pp.panel( "options" ).width,
                height : cpos.height,
                left : cc.width() - pp.panel( "options" ).width,
                top : cpos.top
            } );
            cpos.width -= pp.panel( "options" ).width;
        }
        ;
        if( isVisible( panels.expandEast ) ) {
            setEastSize( panels.expandEast );
        } else {
            setEastSize( panels.east );
        }
        function setWestSize( pp ) {
            if( pp.length == 0 ) {
                return;
            }
            pp.panel( "resize", {
                width : pp.panel( "options" ).width,
                height : cpos.height,
                left : 0,
                top : cpos.top
            } );
            cpos.left += pp.panel( "options" ).width;
            cpos.width -= pp.panel( "options" ).width;
        }
        ;
        if( isVisible( panels.expandWest ) ) {
            setWestSize( panels.expandWest );
        } else {
            setWestSize( panels.west );
        }
        panels.center.panel( "resize", cpos );
    }
    ;
    function init( container ) {
        var cc = $( container );
        if( cc[0].tagName == "BODY" ) {
            $( "html" ).addClass( "panel-fit" );
        }
        cc.addClass( "layout" );
        function _e( cc ) {
            cc.children( "div" ).each( function() {
                var _f = $.parser.parseOptions( this, ["region"] );
                var r = _f.region;
                if( r == "north" || r == "south" || r == "east" || r == "west" || r == "center" ) {
                    _12( container, {
                        region : r
                    }, this );
                }
            } );
        }
        ;
        cc.children( "form" ).length ? _e( cc.children( "form" ) ) : _e( cc );
        $( "<div class=\"layout-split-proxy-h\"></div>" ).appendTo( cc );
        $( "<div class=\"layout-split-proxy-v\"></div>" ).appendTo( cc );
        cc.bind( "_resize", function( e, _10 ) {
            var _11 = $.data( container, "layout" ).options;
            if( _11.fit == true || _10 ) {
                setSize( container );
            }
            return false;
        } );
    }
    ;
    function _12( _13, _14, el ) {
        _14.region = _14.region || "center";
        var _15 = $.data( _13, "layout" ).panels;
        var cc = $( _13 );
        var dir = _14.region;
        if( _15[dir].length ) {
            return;
        }
        var pp = $( el );
        if( !pp.length ) {
            pp = $( "<div></div>" ).appendTo( cc );
        }
        pp.panel( $.extend( {}, {
            width : ( pp.length ? parseInt( pp[0].style.width ) || pp.outerWidth() : "auto" ),
            height : ( pp.length ? parseInt( pp[0].style.height ) || pp.outerHeight() : "auto" ),
            split : ( pp.attr( "split" ) ? pp.attr( "split" ) == "true" : undefined ),
            doSize : false,
            cls : ( "layout-panel layout-panel-" + dir ),
            bodyCls : "layout-body",
            onOpen : function() {
                var _16 = {
                    north : "up",
                    south : "down",
                    east : "right",
                    west : "left"
                };
                if( !_16[dir] ) {
                    return;
                }
                var _17 = "layout-button-" + _16[dir];
                var _18 = $( this ).panel( "header" ).children( "div.panel-tool" );
                if( !_18.children( "a." + _17 ).length ) {
                    var t = $( "<a href=\"javascript:void(0)\"></a>" ).addClass( _17 ).appendTo( _18 );
                    t.bind( "click", {
                        dir : dir
                    }, function( e ) {
                        collapsePanel( _13, e.data.dir );
                        return false;
                    } );
                }
            }
        }, _14 ) );
        _15[dir] = pp;
        if( pp.panel( "options" ).split ) {
            var _19 = pp.panel( "panel" );
            _19.addClass( "layout-split-" + dir );
            var _1a = "";
            if( dir == "north" ) {
                _1a = "s";
            }
            if( dir == "south" ) {
                _1a = "n";
            }
            if( dir == "east" ) {
                _1a = "w";
            }
            if( dir == "west" ) {
                _1a = "e";
            }
            // 此处依赖了 jquery.resizable.js
            _19.resizable( {
                handles : _1a,
                onStartResize : function( e ) {
                    resizing = true;
                    if( dir == "north" || dir == "south" ) {
                        var _1b = $( ">div.layout-split-proxy-v", _13 );
                    } else {
                        var _1b = $( ">div.layout-split-proxy-h", _13 );
                    }
                    var top = 0, _1c = 0, _1d = 0, _1e = 0;
                    var pos = {
                        display : "block"
                    };
                    if( dir == "north" ) {
                        pos.top = parseInt( _19.css( "top" ) ) + _19.outerHeight() - _1b.height();
                        pos.left = parseInt( _19.css( "left" ) );
                        pos.width = _19.outerWidth();
                        pos.height = _1b.height();
                    } else {
                        if( dir == "south" ) {
                            pos.top = parseInt( _19.css( "top" ) );
                            pos.left = parseInt( _19.css( "left" ) );
                            pos.width = _19.outerWidth();
                            pos.height = _1b.height();
                        } else {
                            if( dir == "east" ) {
                                pos.top = parseInt( _19.css( "top" ) ) || 0;
                                pos.left = parseInt( _19.css( "left" ) ) || 0;
                                pos.width = _1b.width();
                                pos.height = _19.outerHeight();
                            } else {
                                if( dir == "west" ) {
                                    pos.top = parseInt( _19.css( "top" ) ) || 0;
                                    pos.left = _19.outerWidth() - _1b.width();
                                    pos.width = _1b.width();
                                    pos.height = _19.outerHeight();
                                }
                            }
                        }
                    }
                    _1b.css( pos );
                    $( "<div class=\"layout-mask\"></div>" ).css( {
                        left : 0,
                        top : 0,
                        width : cc.width(),
                        height : cc.height()
                    } ).appendTo( cc );
                },
                onResize : function( e ) {
                    if( dir == "north" || dir == "south" ) {
                        var _1f = $( ">div.layout-split-proxy-v", _13 );
                        _1f.css( "top", e.pageY - $( _13 ).offset().top - _1f.height() / 2 );
                    } else {
                        var _1f = $( ">div.layout-split-proxy-h", _13 );
                        _1f.css( "left", e.pageX - $( _13 ).offset().left - _1f.width() / 2 );
                    }
                    return false;
                },
                onStopResize : function() {
                    $( ">div.layout-split-proxy-v", _13 ).css( "display", "none" );
                    $( ">div.layout-split-proxy-h", _13 ).css( "display", "none" );
                    var _20 = pp.panel( "options" );
                    _20.width = _19.outerWidth();
                    _20.height = _19.outerHeight();
                    _20.left = _19.css( "left" );
                    _20.top = _19.css( "top" );
                    pp.panel( "resize" );
                    setSize( _13 );
                    resizing = false;
                    cc.find( ">div.layout-mask" ).remove();
                }
            } );
        }
    }
    ;
    function _21( _22, _23 ) {
        var _24 = $.data( _22, "layout" ).panels;
        if( _24[_23].length ) {
            _24[_23].panel( "destroy" );
            _24[_23] = $();
            var _25 = "expand" + _23.substring( 0, 1 ).toUpperCase() + _23.substring( 1 );
            if( _24[_25] ) {
                _24[_25].panel( "destroy" );
                _24[_25] = undefined;
            }
        }
    }
    ;
    /**
     * duration : A string or number determining how long the animation will run. 
     * Durations are given in milliseconds; higher values indicate slower
     * animations, not faster ones. The strings 'fast' and 'slow' can be
     * supplied to indicate durations of 200 and 600 milliseconds, respectively.
     */
    function collapsePanel( container, region, duration ) {
        if( duration == undefined ) {
            duration = "normal";
        }
        var panels = $.data( container, "layout" ).panels;
        var p = panels[region];
        if( p.panel( "options" ).onBeforeCollapse.call( p ) == false ) {
            return;
        }
        var _2b = "expand" + region.substring( 0, 1 ).toUpperCase() + region.substring( 1 );
        if( !panels[_2b] ) {
            panels[_2b] = _2c( region );
            panels[_2b].panel( "panel" ).click( function() {
                var _2d = _2e();
                p.panel( "expand", false ).panel( "open" ).panel( "resize", _2d.collapse );
                p.panel( "panel" ).animate( _2d.expand );
                return false;
            } );
        }
        var _2f = _2e();
        if( !isVisible( panels[_2b] ) ) {
            panels.center.panel( "resize", _2f.resizeC );
        }
        p.panel( "panel" ).animate( _2f.collapse, duration, function() {
            p.panel( "collapse", false ).panel( "close" );
            panels[_2b].panel( "open" ).panel( "resize", _2f.expandP );
        } );
        function _2c( dir ) {
            var _30;
            if( dir == "east" ) {
                _30 = "layout-button-left";
            } else {
                if( dir == "west" ) {
                    _30 = "layout-button-right";
                } else {
                    if( dir == "north" ) {
                        _30 = "layout-button-down";
                    } else {
                        if( dir == "south" ) {
                            _30 = "layout-button-up";
                        }
                    }
                }
            }
            var p = $( "<div></div>" ).appendTo( container ).panel( {
                cls : "layout-expand",
                title : "&nbsp;",
                closed : true,
                doSize : false,
                tools : [{
                    iconCls : _30,
                    handler : function() {
                        expandPanel( container, region );
                        return false;
                    }
                }]
            } );
            p.panel( "panel" ).hover( function() {
                $( this ).addClass( "layout-expand-over" );
            }, function() {
                $( this ).removeClass( "layout-expand-over" );
            } );
            return p;
        }
        ;
        function _2e() {
            var cc = $( container );
            if( region == "east" ) {
                return {
                    resizeC : {
                        width : panels.center.panel( "options" ).width + panels["east"].panel( "options" ).width - 28
                    },
                    expand : {
                        left : cc.width() - panels["east"].panel( "options" ).width
                    },
                    expandP : {
                        top : panels["east"].panel( "options" ).top,
                        left : cc.width() - 28,
                        width : 28,
                        height : panels["center"].panel( "options" ).height
                    },
                    collapse : {
                        left : cc.width()
                    }
                };
            } else {
                if( region == "west" ) {
                    return {
                        resizeC : {
                            width : panels.center.panel( "options" ).width + panels["west"].panel( "options" ).width - 28,
                            left : 28
                        },
                        expand : {
                            left : 0
                        },
                        expandP : {
                            left : 0,
                            top : panels["west"].panel( "options" ).top,
                            width : 28,
                            height : panels["center"].panel( "options" ).height
                        },
                        collapse : {
                            left : -panels["west"].panel( "options" ).width
                        }
                    };
                } else {
                    if( region == "north" ) {
                        var hh = cc.height() - 28;
                        if( isVisible( panels.expandSouth ) ) {
                            hh -= panels.expandSouth.panel( "options" ).height;
                        } else {
                            if( isVisible( panels.south ) ) {
                                hh -= panels.south.panel( "options" ).height;
                            }
                        }
                        panels.east.panel( "resize", {
                            top : 28,
                            height : hh
                        } );
                        panels.west.panel( "resize", {
                            top : 28,
                            height : hh
                        } );
                        if( isVisible( panels.expandEast ) ) {
                            panels.expandEast.panel( "resize", {
                                top : 28,
                                height : hh
                            } );
                        }
                        if( isVisible( panels.expandWest ) ) {
                            panels.expandWest.panel( "resize", {
                                top : 28,
                                height : hh
                            } );
                        }
                        return {
                            resizeC : {
                                top : 28,
                                height : hh
                            },
                            expand : {
                                top : 0
                            },
                            expandP : {
                                top : 0,
                                left : 0,
                                width : cc.width(),
                                height : 28
                            },
                            collapse : {
                                top : -panels["north"].panel( "options" ).height
                            }
                        };
                    } else {
                        if( region == "south" ) {
                            var hh = cc.height() - 28;
                            if( isVisible( panels.expandNorth ) ) {
                                hh -= panels.expandNorth.panel( "options" ).height;
                            } else {
                                if( isVisible( panels.north ) ) {
                                    hh -= panels.north.panel( "options" ).height;
                                }
                            }
                            panels.east.panel( "resize", {
                                height : hh
                            } );
                            panels.west.panel( "resize", {
                                height : hh
                            } );
                            if( isVisible( panels.expandEast ) ) {
                                panels.expandEast.panel( "resize", {
                                    height : hh
                                } );
                            }
                            if( isVisible( panels.expandWest ) ) {
                                panels.expandWest.panel( "resize", {
                                    height : hh
                                } );
                            }
                            return {
                                resizeC : {
                                    height : hh
                                },
                                expand : {
                                    top : cc.height() - panels["south"].panel( "options" ).height
                                },
                                expandP : {
                                    top : cc.height() - 28,
                                    left : 0,
                                    width : cc.width(),
                                    height : 28
                                },
                                collapse : {
                                    top : cc.height()
                                }
                            };
                        }
                    }
                }
            }
        }
        ;
    }
    ;
    function expandPanel( container, region ) {
        var panels = $.data( container, "layout" ).panels;
        var _35 = _36();
        var p = panels[region];
        if( p.panel( "options" ).onBeforeExpand.call( p ) == false ) {
            return;
        }
        var _37 = "expand" + region.substring( 0, 1 ).toUpperCase() + region.substring( 1 );
        panels[_37].panel( "close" );
        p.panel( "panel" ).stop( true, true );
        p.panel( "expand", false ).panel( "open" ).panel( "resize", _35.collapse );
        p.panel( "panel" ).animate( _35.expand, function() {
            setSize( container );
        } );
        function _36() {
            var cc = $( container );
            if( region == "east" && panels.expandEast ) {
                return {
                    collapse : {
                        left : cc.width()
                    },
                    expand : {
                        left : cc.width() - panels["east"].panel( "options" ).width
                    }
                };
            } else {
                if( region == "west" && panels.expandWest ) {
                    return {
                        collapse : {
                            left : -panels["west"].panel( "options" ).width
                        },
                        expand : {
                            left : 0
                        }
                    };
                } else {
                    if( region == "north" && panels.expandNorth ) {
                        return {
                            collapse : {
                                top : -panels["north"].panel( "options" ).height
                            },
                            expand : {
                                top : 0
                            }
                        };
                    } else {
                        if( region == "south" && panels.expandSouth ) {
                            return {
                                collapse : {
                                    top : cc.height()
                                },
                                expand : {
                                    top : cc.height() - panels["south"].panel( "options" ).height
                                }
                            };
                        }
                    }
                }
            }
        }
        ;
    }
    ;
    function bindEvents( container ) {
        var panels = $.data( container, "layout" ).panels;
        var cc = $( container );
        if( panels.east.length ) {
            panels.east.panel( "panel" ).bind( "mouseover", "east", _3b );
        }
        if( panels.west.length ) {
            panels.west.panel( "panel" ).bind( "mouseover", "west", _3b );
        }
        if( panels.north.length ) {
            panels.north.panel( "panel" ).bind( "mouseover", "north", _3b );
        }
        if( panels.south.length ) {
            panels.south.panel( "panel" ).bind( "mouseover", "south", _3b );
        }
        panels.center.panel( "panel" ).bind( "mouseover", "center", _3b );
        function _3b( e ) {
            if( resizing == true ) {
                return;
            }
            if( e.data != "east" && isVisible( panels.east ) && isVisible( panels.expandEast ) ) {
                collapsePanel( container, "east" );
            }
            if( e.data != "west" && isVisible( panels.west ) && isVisible( panels.expandWest ) ) {
                collapsePanel( container, "west" );
            }
            if( e.data != "north" && isVisible( panels.north ) && isVisible( panels.expandNorth ) ) {
                collapsePanel( container, "north" );
            }
            if( e.data != "south" && isVisible( panels.south ) && isVisible( panels.expandSouth ) ) {
                collapsePanel( container, "south" );
            }
            return false;
        }
        ;
    }
    ;
    function isVisible( pp ) {
        if( !pp ) {
            return false;
        }
        if( pp.length ) {
            return pp.panel( "panel" ).is( ":visible" );
        } else {
            return false;
        }
    }
    ;
    function _3c( container ) {
        var panels = $.data( container, "layout" ).panels;
        if( panels.east.length && panels.east.panel( "options" ).collapsed ) {
            collapsePanel( container, "east", 0 );
        }
        if( panels.west.length && panels.west.panel( "options" ).collapsed ) {
            collapsePanel( container, "west", 0 );
        }
        if( panels.north.length && panels.north.panel( "options" ).collapsed ) {
            collapsePanel( container, "north", 0 );
        }
        if( panels.south.length && panels.south.panel( "options" ).collapsed ) {
            collapsePanel( container, "south", 0 );
        }
    }
    ;
    $.fn.layout = function( options, param ) {
        if( typeof options == "string" ) {
            return $.fn.layout.methods[options]( this, param );
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "layout" );
            if( state ) {
                $.extend( state.options, options );
            } else {
                var opts = $.extend( {}, $.fn.layout.defaults, $.fn.layout.parseOptions( this ), options );
                $.data( this, "layout", {
                    options : opts,
                    panels : {
                        center : $(),
                        north : $(),
                        south : $(),
                        east : $(),
                        west : $()
                    }
                } );
                init( this );
                bindEvents( this );
            }
            setSize( this );
            _3c( this );
        } );
    };
    $.fn.layout.methods = {
            /** Set the layout size. */
        resize : function( jq ) {
            return jq.each( function() {
                setSize( this );
            } );
        },
        /** Return the specified panel, the 'region' parameter possible values:'north','south','east','west','center'. */
        panel : function( jq, region ) {
            return $.data( jq[0], "layout" ).panels[region];
        },
        /** Collapse the specified panel, the 'region' parameter possible values:'north','south','east','west'. */
        collapse : function( jq, region ) {
            return jq.each( function() {
                collapsePanel( this, region );
            } );
        },
        /** Expand the specified panel, the 'region' parameter possible values:'north','south','east','west'. */
        expand : function( jq, region ) {
            return jq.each( function() {
                expandPanel( this, region );
            } );
        },
        add : function( jq, _46 ) {
            return jq.each( function() {
                _12( this, _46 );
                setSize( this );
                if( $( this ).layout( "panel", _46.region ).panel( "options" ).collapsed ) {
                    collapsePanel( this, _46.region, 0 );
                }
            } );
        },
        remove : function( jq, _47 ) {
            return jq.each( function() {
                _21( this, _47 );
                setSize( this );
            } );
        }
    };
    $.fn.layout.parseOptions = function( _48 ) {
        return $.extend( {}, $.parser.parseOptions( _48, [{
            fit : "boolean"
        }] ) );
    };
    $.fn.layout.defaults = {
        fit : false
    };
} )( jQuery );
