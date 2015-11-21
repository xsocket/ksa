/**
 * form - jQuery EasyUI 1.3
 * 
 *   覆盖原生的 jquery.form.js，以添加自定义扩展。
 * 
 *  <h3>扩展内容</h3>
 *  <ol>
 *      <li>初始化后就进行 validate；</li>
 *      <li>修改 ajax 提交表单的功能，仅保留表单验证的功能；</li>
 *  </ol>
 * 
 */
( function( $ ) {
    function submitForm( target, options ) {
        options = options || {};
        if( options.onSubmit ) {
            if( options.onSubmit.call( target ) == false ) {
                return;
            }
        }
        try {
            $( target )[0].submit();
        } catch( e ) {}
    }
    ;
    function load( target, data ) {
        if( !$.data( target, "form" ) ) {
            $.data( target, "form", {
                options : $.extend( {}, $.fn.form.defaults )
            } );
        }
        var opts = $.data( target, "form" ).options;
        if( typeof data == "string" ) {
            var param = {};
            if( opts.onBeforeLoad.call( target, param ) == false ) {
                return;
            }
            $.ajax( {
                url : data,
                data : param,
                dataType : "json",
                success : function( data ) {
                    _load( data );
                },
                error : function() {
                    opts.onLoadError.apply( target, arguments );
                }
            } );
        } else {
            _load( data );
        }
        function _load( data ) {
            var form = $( target );
            for( var name in data ) {
                var val = data[name];
                var rr = _15( name, val );
                if( !rr.length ) {
                    var f = form.find( "input[numberboxName=\"" + name + "\"]" );
                    if( f.length ) {
                        f.numberbox( "setValue", val );
                    } else {
                        $( "input[name=\"" + name + "\"]", form ).val( val );
                        $( "textarea[name=\"" + name + "\"]", form ).val( val );
                        $( "select[name=\"" + name + "\"]", form ).val( val );
                    }
                }
                _16( name, val );
            }
            opts.onLoadSuccess.call( target, data );
            validate( target );
        }
        ;
        function _15( name, val ) {
            var form = $( target );
            var rr = $( "input[name=\"" + name + "\"][type=radio], input[name=\"" + name + "\"][type=checkbox]", form );
            $.fn.prop ? rr.prop( "checked", false ) : rr.attr( "checked", false );
            rr.each( function() {
                var f = $( this );
                if( f.val() == String( val ) ) {
                    $.fn.prop ? f.prop( "checked", true ) : f.attr( "checked", true );
                }
            } );
            return rr;
        }
        ;
        function _16( name, val ) {
            var form = $( target );
            var cc = ["combobox", "combotree", "combogrid", "datetimebox", "datebox", "combo"];
            var c = form.find( "[comboName=\"" + name + "\"]" );
            if( c.length ) {
                for( var i = 0; i < cc.length; i++ ) {
                    var _1b = cc[i];
                    if( c.hasClass( _1b + "-f" ) ) {
                        if( c[_1b]( "options" ).multiple ) {
                            c[_1b]( "setValues", val );
                        } else {
                            c[_1b]( "setValue", val );
                        }
                        return;
                    }
                }
            }
        }
        ;
    }
    ;
    function clear( target ) {
        $( "input,select,textarea", target ).each( function() {
            var t = this.type, tag = this.tagName.toLowerCase();
            if( t == "text" || t == "hidden" || t == "password" || tag == "textarea" ) {
                this.value = "";
            } else {
                if( t == "file" ) {
                    var node = $( this );
                    node.after( node.clone().val( "" ) );
                    node.remove();
                } else {
                    if( t == "checkbox" || t == "radio" ) {
                        this.checked = false;
                    } else {
                        if( tag == "select" ) {
                            this.selectedIndex = -1;
                        }
                    }
                }
            }
        } );
        if( $.fn.combo ) {
            $( ".combo-f", target ).combo( "clear" );
        }
        if( $.fn.combobox ) {
            $( ".combobox-f", target ).combobox( "clear" );
        }
        if( $.fn.combotree ) {
            $( ".combotree-f", target ).combotree( "clear" );
        }
        if( $.fn.combogrid ) {
            $( ".combogrid-f", target ).combogrid( "clear" );
        }
        validate( target );
    }
    ;
    function setForm( target ) {
        var opts = $.data( target, "form" ).options;
        var form = $( target );
        form.unbind( ".form" ).bind( "submit.form", function() {
            setTimeout( function() {
                submitForm( target, opts );
            }, 0 );
            return false;
        } );
    }
    ;
    function validate( target ) {
        if( $.fn.validatebox ) {
            var box = $( ".validatebox-text", target );
            if( box.length ) {
                box.validatebox( "validate" );
                box.trigger( "focus" );
                box.trigger( "blur" );
                var invalid = $( ".validatebox-invalid:first", target ).focus();
                return invalid.length == 0;
            }
        }
        return true;
    }
    ;
    $.fn.form = function( options, param ) {
        if( typeof options == "string" ) {
            return $.fn.form.methods[options]( this, param );
        }
        options = options || {};
        return this.each( function() {
            if( !$.data( this, "form" ) ) {
                $.data( this, "form", {
                    options : $.extend( {}, $.fn.form.defaults, options )
                } );
            }
            setForm( this );
            // 初始化后就进行 validate
            validate( this );
        } );
    };
    $.fn.form.methods = {
        submit : function( jq, param ) {
            return jq.each( function() {
                submitForm( this, $.extend( {}, $.fn.form.defaults, param || {} ) );
            } );
        },
        load : function( jq, param ) {
            return jq.each( function() {
                load( this, param );
            } );
        },
        clear : function( jq ) {
            return jq.each( function() {
                clear( this );
            } );
        },
        validate : function( jq ) {
            return validate( jq[0] );
        }
    };
    $.fn.form.defaults = {
        onSubmit : function() {
            return $( this ).form( "validate" );
        },
        onBeforeLoad : function( param ) {
        },
        onLoadSuccess : function( data ) {
        },
        onLoadError : function() {
        }
    };
} )( jQuery );
