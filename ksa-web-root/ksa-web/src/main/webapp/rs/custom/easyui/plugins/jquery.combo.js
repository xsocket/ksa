/**
 * combo - jQuery EasyUI 1.3
 * 
 * Dependencies:
 *   panel
 *   validatebox
 *   
 * 覆盖原生的 jquery.combo.js，以添加自定义扩展。
 * 
 * <h3>扩展内容</h3>
 *  <ol>
 *      <li>在 editable == false 的情况下，修改 input 的样式并添加 click 事件的处理（打开/关闭选择面板）；</li>
 *      <li>修改 combo 的样式，使其与 bootstrap 风格相适应。</li>
 *  </ol>
 */
( function( $ ) {
    function setSize( target, width ) {
        var state = $.data( target, "combo" );
        var opts = state.options;
        var combo = state.combo;
        var panel = state.panel;
        var input = combo.find( "input.combo-text" );
        var arrow = combo.find( ".combo-arrow" );
        var arrowWrap = arrow.parent();
        if( width ) {
            opts.width = width;
        }
        combo.appendTo( "body" );
        if( isNaN( opts.width ) ) {
            opts.width = input.outerWidth();
        }
        var arrowWidth = 0;
        if( opts.hasDownArrow ) {
            arrowWidth = arrow.outerWidth();
        }
        /* Probe : 改变下滑箭头的位置，将其置于 input.combo-text 内部。 */
        combo.width( opts.width );
        combo.height( input.outerHeight() );
        input._outerWidth( opts.width );
        combo.css({ "border" : "none" });
        var padding = ( input.outerHeight() - arrow.outerHeight() ) / 2;
        arrowWrap.css({
            "position" : "relative",
            "top" : padding,
            "right" : arrowWidth + padding,
            "z-index" : 5
        });
        
        panel.panel( "resize", {
            width : ( opts.panelWidth ? opts.panelWidth : input.outerWidth() ),
            height : opts.panelHeight
        } );
        combo.insertAfter( target );
    }
    ;
    function initArrow( target ) {
        var opts = $.data( target, "combo" ).options;
        var combo = $.data( target, "combo" ).combo;
        if( opts.hasDownArrow ) {
            combo.find( ".combo-arrow" ).show();
        } else {
            combo.find( ".combo-arrow" ).hide();
        }
    }
    ;
    function init( target ) {
        $( target ).addClass( "combo-f" ).hide();
        var span = $( "<span class=\"combo\"></span>" ).insertAfter( target );
        var input = $( "<input type=\"text\" class=\"combo-text\">" ).appendTo( span );
        $( "<span><span class=\"combo-arrow\"></span></span>" ).appendTo( span );
        $( "<input type=\"hidden\" class=\"combo-value\">" ).appendTo( span );
        var panel = $( "<div class=\"combo-panel\"></div>" ).appendTo( "body" );
        panel.panel( {
            doSize : false,
            closed : true,
            cls : "combo-p",
            style : {
                position : "absolute",
                zIndex : 10
            },
            onOpen : function() {
                $( this ).panel( "resize" );
            }
        } );
        var name = $( target ).attr( "name" );
        if( name ) {
            span.find( "input.combo-value" ).attr( "name", name );
            $( target ).removeAttr( "name" ).attr( "comboName", name );
        }
        input.attr( "autocomplete", "off" );
        return {
            combo : span,
            panel : panel
        };
    }
    ;
    function destroy( target ) {
        var input = $.data( target, "combo" ).combo.find( "input.combo-text" );
        input.validatebox( "destroy" );
        $.data( target, "combo" ).panel.panel( "destroy" );
        $.data( target, "combo" ).combo.remove();
        $( target ).remove();
    }
    ;
    function bindEvents( target ) {
        var state = $.data( target, "combo" );
        var opts = state.options;
        var combo = $.data( target, "combo" ).combo;
        var panel = $.data( target, "combo" ).panel;
        var input = combo.find( ".combo-text" );
        var arrow = combo.find( ".combo-arrow" );
        $( document ).unbind( ".combo" ).bind( "mousedown.combo", function( e ) {
            var context = $( "body>div.combo-p>div.combo-panel" );
            var p = $( e.target ).closest( "div.combo-panel", context );
            if( p.length ) {
                return;
            }
            context.panel( "close" );
        } );
        combo.unbind( ".combo" );
        panel.unbind( ".combo" );
        input.unbind( ".combo" );
        arrow.unbind( ".combo" );
        if( !opts.disabled ) {
            input.bind( "mousedown.combo", function( e ) {
                e.stopPropagation();
            } ).bind( "keydown.combo", function( e ) {
                switch( e.keyCode ) {
                    case 38:
                        opts.keyHandler.up.call( target );
                        break;
                    case 40:
                        opts.keyHandler.down.call( target );
                        break;
                    case 13:
                        e.preventDefault();
                        opts.keyHandler.enter.call( target );
                        return false;
                    case 9:
                    case 27:
                        hidePanel( target );
                        break;
                    default:
                        if( opts.editable ) {
                            if( state.timer ) {
                                clearTimeout( state.timer );
                            }
                            state.timer = setTimeout( function() {
                                var q = input.val();
                                if( state.previousValue != q ) {
                                    state.previousValue = q;
                                    showPanel( target );
                                    opts.keyHandler.query.call( target, input.val() );
                                    validate( target, true );
                                }
                            }, opts.delay );
                        }
                }
            } );
            arrow.bind( "click.combo", function() {
                if( panel.is( ":visible" ) ) {
                    hidePanel( target );
                } else {
                    $( "div.combo-panel" ).panel( "close" );
                    showPanel( target );
                }
                input.focus();
            } ).bind( "mouseenter.combo", function() {
                $( this ).addClass( "combo-arrow-hover" );
            } ).bind( "mouseleave.combo", function() {
                $( this ).removeClass( "combo-arrow-hover" );
            } ).bind( "mousedown.combo", function() {
                return false;
            } );
            /* Probe : 增加不可编辑状态下的点击事件 */ 
            if( !opts.editable ) {
                input.bind( "click.combo", function() {
                    if( panel.is( ":visible" ) ) {
                        hidePanel( target );
                    } else {
                        $( "div.combo-panel" ).panel( "close" );
                        showPanel( target );
                    }
                    input.focus();
                } );
            }
        }
    }
    ;
    function showPanel( target ) {
        var opts = $.data( target, "combo" ).options;
        var combo = $.data( target, "combo" ).combo;
        var panel = $.data( target, "combo" ).panel;
        if( $.fn.window ) {
            panel.panel( "panel" ).css( "z-index", $.fn.window.defaults.zIndex++ );
        }
        panel.panel( "move", {
            left : combo.offset().left,
            top : fixedTop()
        } );
        panel.panel( "open" );
        opts.onShowPanel.call( target );
        ( function() {
            if( panel.is( ":visible" ) ) {
                panel.panel( "move", {
                    left : fixedLeft(),
                    top : fixedTop()
                } );
                setTimeout( arguments.callee, 200 );
            }
        } )();
        function fixedLeft() {
            var left = combo.offset().left;
            if( left + panel.outerWidth() > $( window ).width() + $( document ).scrollLeft() ) {
                left = $( window ).width() + $( document ).scrollLeft() - panel.outerWidth();
            }
            if( left < 0 ) {
                left = 0;
            }
            return left;
        }
        ;
        function fixedTop() {
            var top = combo.offset().top + combo.outerHeight();
            if( top + panel.outerHeight() > $( window ).height() + $( document ).scrollTop() ) {
                top = combo.offset().top - panel.outerHeight();
            }
            if( top < $( document ).scrollTop() ) {
                top = combo.offset().top + combo.outerHeight();
            }
            return top;
        }
        ;
    }
    ;
    function hidePanel( target ) {
        var opts = $.data( target, "combo" ).options;
        var panel = $.data( target, "combo" ).panel;
        panel.panel( "close" );
        opts.onHidePanel.call( target );
    }
    ;
    function validate( target, doit ) {
        var opts = $.data( target, "combo" ).options;
        var input = $.data( target, "combo" ).combo.find( "input.combo-text" );
        input.validatebox( opts );
        if( doit ) {
            input.validatebox( "validate" );
            input.trigger( "mouseleave" );
        }
    }
    ;
    function setDisabled( target, disabled ) {
        var opts = $.data( target, "combo" ).options;
        var combo = $.data( target, "combo" ).combo;
        if( disabled ) {
            opts.disabled = true;
            $( target ).attr( "disabled", true );
            combo.find( ".combo-value" ).attr( "disabled", true );
            combo.find( ".combo-text" ).attr( "disabled", true );
        } else {
            opts.disabled = false;
            $( target ).removeAttr( "disabled" );
            combo.find( ".combo-value" ).removeAttr( "disabled" );
            combo.find( ".combo-text" ).removeAttr( "disabled" );
        }
    }
    ;
    function clear( target ) {
        var opts = $.data( target, "combo" ).options;
        var combo = $.data( target, "combo" ).combo;
        if( opts.multiple ) {
            combo.find( "input.combo-value" ).remove();
        } else {
            combo.find( "input.combo-value" ).val( "" );
        }
        combo.find( "input.combo-text" ).val( "" );
    }
    ;
    function getText( target ) {
        var combo = $.data( target, "combo" ).combo;
        return combo.find( "input.combo-text" ).val();
    }
    ;
    function setText( target, text ) {
        var combo = $.data( target, "combo" ).combo;
        combo.find( "input.combo-text" ).val( text );
        validate( target, true );
        $.data( target, "combo" ).previousValue = text;
    }
    ;
    function getValues( target ) {
        var values = [];
        var combo = $.data( target, "combo" ).combo;
        combo.find( "input.combo-value" ).each( function() {
            values.push( $( this ).val() );
        } );
        return values;
    }
    ;
    function setValues( target, values ) {
        var opts = $.data( target, "combo" ).options;
        var oldValues = getValues( target );
        var combo = $.data( target, "combo" ).combo;
        combo.find( "input.combo-value" ).remove();
        var name = $( target ).attr( "comboName" );
        for( var i = 0; i < values.length; i++ ) {
            var input = $( "<input type=\"hidden\" class=\"combo-value\">" ).appendTo( combo );
            if( name ) {
                input.attr( "name", name );
            }
            input.val( values[i] );
        }
        var tmp = [];
        for( var i = 0; i < oldValues.length; i++ ) {
            tmp[i] = oldValues[i];
        }
        var aa = [];
        for( var i = 0; i < values.length; i++ ) {
            for( var j = 0; j < tmp.length; j++ ) {
                if( values[i] == tmp[j] ) {
                    aa.push( values[i] );
                    tmp.splice( j, 1 );
                    break;
                }
            }
        }
        if( aa.length != values.length || values.length != oldValues.length ) {
            if( opts.multiple ) {
                opts.onChange.call( target, values, oldValues );
            } else {
                opts.onChange.call( target, values[0], oldValues[0] );
            }
        }
    }
    ;
    function getValue( target ) {
        var values = getValues( target );
        return values[0];
    }
    ;
    function setValue( target, value ) {
        setValues( target, [value] );
    }
    ;
    function initValue( target ) {
        var opts = $.data( target, "combo" ).options;
        var fn = opts.onChange;
        opts.onChange = function() {
        };
        if( opts.multiple ) {
            if( opts.value ) {
                if( typeof opts.value == "object" ) {
                    setValues( target, opts.value );
                } else {
                    setValue( target, opts.value );
                }
            } else {
                setValues( target, [] );
            }
        } else {
            setValue( target, opts.value );
        }
        opts.onChange = fn;
    }
    ;
    $.fn.combo = function( options, param ) {
        if( typeof options == "string" ) {
            return $.fn.combo.methods[options]( this, param );
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "combo" );
            if( state ) {
                $.extend( state.options, options );
            } else {
                var r = init( this );
                state = $.data( this, "combo", {
                    options : $.extend( {}, $.fn.combo.defaults, $.fn.combo.parseOptions( this ), options ),
                    combo : r.combo,
                    panel : r.panel,
                    previousValue : null
                } );
                $( this ).removeAttr( "disabled" );
            }
            $( "input.combo-text", state.combo ).attr( "readonly", !state.options.editable );
            initArrow( this );
            setDisabled( this, state.options.disabled );
            setSize( this );
            bindEvents( this );
            validate( this );
            initValue( this );
        } );
    };
    $.fn.combo.methods = {
        options : function( jq ) {
            return $.data( jq[0], "combo" ).options;
        },
        panel : function( jq ) {
            return $.data( jq[0], "combo" ).panel;
        },
        textbox : function( jq ) {
            return $.data( jq[0], "combo" ).combo.find( "input.combo-text" );
        },
        destroy : function( jq ) {
            return jq.each( function() {
                destroy( this );
            } );
        },
        resize : function( jq, width ) {
            return jq.each( function() {
                setSize( this, width );
            } );
        },
        showPanel : function( jq ) {
            return jq.each( function() {
                showPanel( this );
            } );
        },
        hidePanel : function( jq ) {
            return jq.each( function() {
                hidePanel( this );
            } );
        },
        disable : function( jq ) {
            return jq.each( function() {
                setDisabled( this, true );
                bindEvents( this );
            } );
        },
        enable : function( jq ) {
            return jq.each( function() {
                setDisabled( this, false );
                bindEvents( this );
            } );
        },
        validate : function( jq ) {
            return jq.each( function() {
                validate( this, true );
            } );
        },
        isValid : function( jq ) {
            var state = $.data( jq[0], "combo" ).combo.find( "input.combo-text" );
            return state.validatebox( "isValid" );
        },
        clear : function( jq ) {
            return jq.each( function() {
                clear( this );
            } );
        },
        getText : function( jq ) {
            return getText( jq[0] );
        },
        setText : function( jq, _59 ) {
            return jq.each( function() {
                setText( this, _59 );
            } );
        },
        getValues : function( jq ) {
            return getValues( jq[0] );
        },
        setValues : function( jq, values ) {
            return jq.each( function() {
                setValues( this, values );
            } );
        },
        getValue : function( jq ) {
            return getValue( jq[0] );
        },
        setValue : function( jq, value ) {
            return jq.each( function() {
                setValue( this, value );
            } );
        }
    };
    $.fn.combo.parseOptions = function( target ) {
        var t = $( target );
        return $.extend( {}, $.fn.validatebox.parseOptions( target ), $.parser.parseOptions( target, ["width", "separator", {
            panelWidth : "number",
            editable : "boolean",
            hasDownArrow : "boolean",
            delay : "number"
        }] ), {
            panelHeight : ( t.attr( "panelHeight" ) == "auto" ? "auto" : parseInt( t.attr( "panelHeight" ) ) || undefined ),
            multiple : ( t.attr( "multiple" ) ? true : undefined ),
            disabled : ( t.attr( "disabled" ) ? true : undefined ),
            value : ( t.val() || undefined )
        } );
    };
    $.fn.combo.defaults = $.extend( {}, $.fn.validatebox.defaults, {
        width : "auto",
        panelWidth : null,
        panelHeight : 200,
        multiple : false,
        separator : ",",
        editable : true,
        disabled : false,
        hasDownArrow : true,
        value : "",
        delay : 200,
        keyHandler : {
            up : function() {
            },
            down : function() {
            },
            enter : function() {
            },
            query : function( q ) {
            }
        },
        onShowPanel : function() {
        },
        onHidePanel : function() {
        },
        onChange : function( newValue, oldValue ) {
        }
    } );
} )( jQuery );
