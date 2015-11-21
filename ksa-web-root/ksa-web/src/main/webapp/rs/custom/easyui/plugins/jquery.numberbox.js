/**
 * numberbox - jQuery EasyUI 1.3
 * 
 * Licensed under the GPL terms To use it on other terms please contact us
 * 
 * Copyright(c) 2009-2012 stworthy [ stworthy@gmail.com ]
 * 
 * Dependencies:
 *   validatebox
 */
( function( $ ) {
    function initField( target ) {
        var v = $( "<input type=\"hidden\">" ).insertAfter( target );
        var fieldName = $( target ).attr( "name" );
        if( fieldName ) {
            v.attr( "name", fieldName );
            $( target ).removeAttr( "name" ).attr( "numberboxName", fieldName );
        }
        return v;
    }
    ;
    function initValue( target ) {
        var opts = $.data( target, "numberbox" ).options;
        var fn = opts.onChange;
        opts.onChange = function() {
        };
        fixValue( target, opts.parser.call( target, opts.value ) );
        opts.onChange = fn;
    }
    ;
    function getValue( target ) {
        return $.data( target, "numberbox" ).field.val();
    }
    ;
    function fixValue( target, newValue ) {
        var state = $.data( target, "numberbox" );
        var opts = state.options;
        var oldValue = getValue( target );
        newValue = opts.parser.call( target, newValue );
        opts.value = newValue;
        state.field.val( newValue );
        $( target ).val( opts.formatter.call( target, newValue ) );
        if( oldValue != newValue ) {
            opts.onChange.call( target, newValue, oldValue );
        }
    }
    ;
    function bindEvents( target ) {
        var opts = $.data( target, "numberbox" ).options;
        $( target ).unbind( ".numberbox" ).bind(
                "keypress.numberbox",
                function( e ) {
                    if( e.which == 45 ) {
                        if( $( this ).val().indexOf( "-" ) == -1 ) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    if( e.which == 46 ) {
                        if( $( this ).val().indexOf( "." ) == -1 ) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if( ( e.which >= 48 && e.which <= 57 && e.ctrlKey == false && e.shiftKey == false ) || e.which == 0
                                || e.which == 8 ) {
                            return true;
                        } else {
                            if( e.ctrlKey == true && ( e.which == 99 || e.which == 118 ) ) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                } ).bind( "paste.numberbox", function() {
            if( window.clipboardData ) {
                var s = clipboardData.getData( "text" );
                if( !/\D/.test( s ) ) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } ).bind( "dragenter.numberbox", function() {
            return false;
        } ).bind( "blur.numberbox", function() {
            fixValue( target, $( this ).val() );
            $( this ).val( opts.formatter.call( target, getValue( target ) ) );
        } ).bind( "focus.numberbox", function() {
            var vv = getValue( target );
            if( $( this ).val() != vv ) {
                $( this ).val( vv );
            }
        } );
    }
    ;
    function validate( target ) {
        if( $.fn.validatebox ) {
            var opts = $.data( target, "numberbox" ).options;
            $( target ).validatebox( opts );
        }
    }
    ;
    function setDisabled( target, param ) {
        var opts = $.data( target, "numberbox" ).options;
        if( param ) {
            opts.disabled = true;
            $( target ).attr( "disabled", true );
        } else {
            opts.disabled = false;
            $( target ).removeAttr( "disabled" );
        }
    }
    ;
    $.fn.numberbox = function( options, param ) {
        if( typeof options == "string" ) {
            var method = $.fn.numberbox.methods[options];
            if( method ) {
                return method( this, param );
            } else {
                return this.validatebox( options, param );
            }
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "numberbox" );
            if( state ) {
                $.extend( state.options, options );
            } else {
                state = $.data( this, "numberbox", {
                    options : $.extend( {}, $.fn.numberbox.defaults, $.fn.numberbox.parseOptions( this ), options ),
                    field : initField( this )
                } );
                $( this ).removeAttr( "disabled" );
                $( this ).css( {
                    imeMode : "disabled"
                } );
            }
            setDisabled( this, state.options.disabled );
            bindEvents( this );
            validate( this );
            initValue( this );
        } );
    };
    $.fn.numberbox.methods = {
        options : function( jq ) {
            return $.data( jq[0], "numberbox" ).options;
        },
        destroy : function( jq ) {
            return jq.each( function() {
                $.data( this, "numberbox" ).field.remove();
                $( this ).validatebox( "destroy" );
                $( this ).remove();
            } );
        },
        disable : function( jq ) {
            return jq.each( function() {
                setDisabled( this, true );
            } );
        },
        enable : function( jq ) {
            return jq.each( function() {
                setDisabled( this, false );
            } );
        },
        fix : function( jq ) {
            return jq.each( function() {
                fixValue( this, $( this ).val() );
            } );
        },
        setValue : function( jq, value ) {
            return jq.each( function() {
                fixValue( this, value );
            } );
        },
        getValue : function( jq ) {
            return getValue( jq[0] );
        },
        clear : function( jq ) {
            return jq.each( function() {
                var state = $.data( this, "numberbox" );
                state.field.val( "" );
                $( this ).val( "" );
            } );
        }
    };
    $.fn.numberbox.parseOptions = function( target ) {
        var t = $( target );
        return $.extend( {}, $.fn.validatebox.parseOptions( target ), $.parser.parseOptions( target, ["decimalSeparator",
                "groupSeparator", "prefix", "suffix", {
                    min : "number",
                    max : "number",
                    precision : "number"
                }] ), {
            disabled : ( t.attr( "disabled" ) ? true : undefined ),
            value : ( t.val() || undefined )
        } );
    };
    $.fn.numberbox.defaults = $.extend( {}, $.fn.validatebox.defaults, {
        disabled : false,
        value : "",
        min : null,
        max : null,
        precision : 0,
        decimalSeparator : ".",
        groupSeparator : "",
        prefix : "",
        suffix : "",
        formatter : function( v ) {
            if( !v ) {
                return v;
            }
            v = v + "";
            var opts = $( this ).numberbox( "options" );
            var s1 = v, s2 = "";
            var indexOfDot = v.indexOf( "." );
            if( indexOfDot >= 0 ) {
                s1 = v.substring( 0, indexOfDot );
                s2 = v.substring( indexOfDot + 1, v.length );
            }
            if( opts.groupSeparator ) {
                var p = /(\d+)(\d{3})/;
                while( p.test( s1 ) ) {
                    s1 = s1.replace( p, "$1" + opts.groupSeparator + "$2" );
                }
            }
            if( s2 ) {
                return opts.prefix + s1 + opts.decimalSeparator + s2 + opts.suffix;
            } else {
                return opts.prefix + s1 + opts.suffix;
            }
        },
        parser : function( s ) {
            s = s + "";
            var opts = $( this ).numberbox( "options" );
            if( opts.groupSeparator ) {
                s = s.replace( new RegExp( "\\" + opts.groupSeparator, "g" ), "" );
            }
            if( opts.decimalSeparator ) {
                s = s.replace( new RegExp( "\\" + opts.decimalSeparator, "g" ), "." );
            }
            if( opts.prefix ) {
                s = s.replace( new RegExp( "\\" + $.trim( opts.prefix ), "g" ), "" );
            }
            if( opts.suffix ) {
                s = s.replace( new RegExp( "\\" + $.trim( opts.suffix ), "g" ), "" );
            }
            s = s.replace( /\s/g, "" );
            var val = parseFloat( s ).toFixed( opts.precision );
            if( isNaN( val ) ) {
                val = "";
            } else {
                if( typeof ( opts.min ) == "number" && val < opts.min ) {
                    val = opts.min.toFixed( opts.precision );
                } else {
                    if( typeof ( opts.max ) == "number" && val > opts.max ) {
                        val = opts.max.toFixed( opts.precision );
                    }
                }
            }
            return val;
        },
        /** Fires when the field value is changed. */
        onChange : function( newValue, oldValue ) {
        }
    } );
} )( jQuery );
