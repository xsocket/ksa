/**
 * spinner - jQuery EasyUI 1.3 
 * 
 * Dependencies:
 *   validatebox
 * 
 * 覆盖原生的 jquery.spinner.js，以添加自定义扩展。
 * 
 * <h3>扩展内容</h3>
 *  <ol>
 *      <li>修改 spinner 的样式，使其与 bootstrap 风格相适应。</li>
 *  </ol>
 */
( function( $ ) {
    function init( target ) {
        var spinner = $( "<span class=\"spinner\">" 
                    + "<span class=\"spinner-arrow\">" 
                    + "<span class=\"spinner-arrow-up\"></span>"
                    + "<span class=\"spinner-arrow-down\"></span>" 
                    + "</span>" 
                    + "</span>" ).insertAfter( target );
        $( target ).addClass( "spinner-text" ).prependTo( spinner );
        return spinner;
    }
    ;
    function setSize( target, width ) {
        var opts = $.data( target, "spinner" ).options;
        var spinner = $.data( target, "spinner" ).spinner;
        var arrow = spinner.find( ".spinner-arrow" );
        if( width ) {
            opts.width = width;
        }
        var tmp = $( "<div style=\"display:none\"></div>" ).insertBefore( spinner );
        spinner.appendTo( "body" );
        if( isNaN( opts.width ) ) {
            opts.width = $( target ).outerWidth();
        }
        
        /* Probe : 改变箭头的位置，将其置于 target 内部。 */
        spinner.css({ "border" : "none" });
        spinner._outerWidth( opts.width );
        $( target )._outerWidth( opts.width );
        
        var padding = ( $( target ).outerHeight() - arrow.outerHeight() ) / 2;
        arrow.css({
            "position" : "relative",
            "top" : padding,
            "right" : arrow.outerWidth() + padding
        });
        
        spinner.insertAfter( tmp );
        tmp.remove();
    }
    ;
    function bindEvents( target ) {
        var opts = $.data( target, "spinner" ).options;
        var spinner = $.data( target, "spinner" ).spinner;
        spinner.find( ".spinner-arrow-up,.spinner-arrow-down" ).unbind( ".spinner" );
        if( !opts.disabled ) {
            spinner.find( ".spinner-arrow-up" ).bind( "mouseenter.spinner", function() {
                $( this ).addClass( "spinner-arrow-hover" );
            } ).bind( "mouseleave.spinner", function() {
                $( this ).removeClass( "spinner-arrow-hover" );
            } ).bind( "click.spinner", function() {
                opts.spin.call( target, false );
                opts.onSpinUp.call( target );
                $( target ).validatebox( "validate" );
            } );
            spinner.find( ".spinner-arrow-down" ).bind( "mouseenter.spinner", function() {
                $( this ).addClass( "spinner-arrow-hover" );
            } ).bind( "mouseleave.spinner", function() {
                $( this ).removeClass( "spinner-arrow-hover" );
            } ).bind( "click.spinner", function() {
                opts.spin.call( target, true );
                opts.onSpinDown.call( target );
                $( target ).validatebox( "validate" );
            } );
        }
    }
    ;
    function setDisabled( target, disabled ) {
        var opts = $.data( target, "spinner" ).options;
        if( disabled ) {
            opts.disabled = true;
            $( target ).attr( "disabled", true );
        } else {
            opts.disabled = false;
            $( target ).removeAttr( "disabled" );
        }
    }
    ;
    $.fn.spinner = function( options, param ) {
        if( typeof options == "string" ) {
            var method = $.fn.spinner.methods[options];
            if( method ) {
                return method( this, param );
            } else {
                return this.validatebox( options, param );
            }
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "spinner" );
            if( state ) {
                $.extend( state.options, options );
            } else {
                state = $.data( this, "spinner", {
                    options : $.extend( {}, $.fn.spinner.defaults, $.fn.spinner.parseOptions( this ), options ),
                    spinner : init( this )
                } );
                $( this ).removeAttr( "disabled" );
            }
            $( this ).val( state.options.value );
            $( this ).attr( "readonly", !state.options.editable );
            setDisabled( this, state.options.disabled );
            setSize( this );
            $( this ).validatebox( state.options );
            bindEvents( this );
        } );
    };
    $.fn.spinner.methods = {
        options : function( jq ) {
            var opts = $.data( jq[0], "spinner" ).options;
            return $.extend( opts, {
                value : jq.val()
            } );
        },
        destroy : function( jq ) {
            return jq.each( function() {
                var spinner = $.data( this, "spinner" ).spinner;
                $( this ).validatebox( "destroy" );
                spinner.remove();
            } );
        },
        resize : function( jq, width ) {
            return jq.each( function() {
                setSize( this, width );
            } );
        },
        enable : function( jq ) {
            return jq.each( function() {
                setDisabled( this, false );
                bindEvents( this );
            } );
        },
        disable : function( jq ) {
            return jq.each( function() {
                setDisabled( this, true );
                bindEvents( this );
            } );
        },
        getValue : function( jq ) {
            return jq.val();
        },
        setValue : function( jq, value ) {
            return jq.each( function() {
                var opts = $.data( this, "spinner" ).options;
                opts.value = value;
                $( this ).val( value );
            } );
        },
        clear : function( jq ) {
            return jq.each( function() {
                var opts = $.data( this, "spinner" ).options;
                opts.value = "";
                $( this ).val( "" );
            } );
        }
    };
    $.fn.spinner.parseOptions = function( target ) {
        var t = $( target );
        return $.extend( {}, $.fn.validatebox.parseOptions( target ), $.parser.parseOptions( target, ["width", "min", "max", {
            increment : "number",
            editable : "boolean"
        }] ), {
            value : ( t.val() || undefined ),
            disabled : ( t.attr( "disabled" ) ? true : undefined )
        } );
    };
    $.fn.spinner.defaults = $.extend( {}, $.fn.validatebox.defaults, {
        width : "auto",
        value : "",
        min : null,
        max : null,
        increment : 1,
        editable : true,
        disabled : false,
        spin : function( down ) {
        },
        onSpinUp : function() {
        },
        onSpinDown : function() {
        }
    } );
} )( jQuery );
