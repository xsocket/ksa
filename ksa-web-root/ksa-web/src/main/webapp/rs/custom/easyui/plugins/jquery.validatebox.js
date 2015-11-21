/**
 * validatebox - jQuery EasyUI 1.3
 * 
 * 覆盖原生的 jquery.validatebox.js，以添加自定义扩展。
 * 
 *  <h3>扩展内容</h3>
 *  <ol>
 *      <li>规范正则表达式，规避 jsMin 压缩的 bug；</li>
 *      <li>集成 bootstrap 样式；（注释内容有 'bootstrap'）</li>
 *  </ol>
 * 
 */
( function( $ ) {
    function init( target ) {
        $( target ).addClass( "validatebox-text" );
    }
    ;
    function destroyBox( target ) {
        var state = $.data( target, "validatebox" );
        state.validating = false;
        var tip = state.tip;
        if( tip ) {
            tip.remove();
        }
        $( target ).unbind();
        $( target ).remove();
    }
    ;
    function bindEvents( target ) {
        var box = $( target );
        var state = $.data( target, "validatebox" );
        state.validating = false;
        box.unbind( ".validatebox" ).bind( "focus.validatebox", function() {
            state.validating = true;
            state.value = undefined;
            ( function() {
                if( state.validating ) {
                    if( state.value != box.val() ) {
                        state.value = box.val();
                        validate( target );
                    }
                    setTimeout( arguments.callee, 200 );
                }
            } )();
        } ).bind( "blur.validatebox", function() {
            state.validating = false;
            hideTip( target );
        } ).bind( "mouseenter.validatebox", function() {
            if( box.hasClass( "validatebox-invalid" ) ) {
                showTip( target );
            }
        } ).bind( "mouseleave.validatebox", function() {
            hideTip( target );
        } );
    }
    ;
    function showTip( target ) {
        var box = $( target );
        var msg = $.data( target, "validatebox" ).message;
        var tip = $.data( target, "validatebox" ).tip;
        if( !tip ) {
            tip = $( "<div class=\"validatebox-tip\"><span class=\"validatebox-tip-content\"></span>"
                        + "<span class=\"validatebox-tip-pointer\"></span></div>" ).appendTo( "body" );
            $.data( target, "validatebox" ).tip = tip;
        }
        tip.find( ".validatebox-tip-content" ).html( msg );
        tip.css( {
            display : "block",
            left : box.offset().left + box.outerWidth(),
            top : box.offset().top
        } );
    }
    ;
    function hideTip( target ) {
        var tip = $.data( target, "validatebox" ).tip;
        if( tip ) {
            tip.remove();
            $.data( target, "validatebox" ).tip = null;
        }
    }
    ;
    function validate( target ) {
        var opts = $.data( target, "validatebox" ).options;
        var tip = $.data( target, "validatebox" ).tip;
        var box = $( target );
        var value = box.val();
        function setTipMessage( msg ) {
            $.data( target, "validatebox" ).message = msg;
        }
        ;
        var disabled = box.attr( "disabled" );
        if( disabled == true || disabled == "true" ) {
            return true;
        }
        if( opts.required ) {
            if( value == "" ) {
                box.addClass( "validatebox-invalid" );
                box.parents("div.control-group").addClass("error"); // 集成 bootstrap
                setTipMessage( opts.missingMessage );
                showTip( target );
                return false;
            }
        }
        if( opts.validType ) {
            var result = /([a-zA-Z_]+)(.*)/.exec( opts.validType );
            var rule = opts.rules[result[1]];
            if( rule && ( value || rule.forceValidate ) ) {
                var param = eval( result[2] );
                if( !rule["validator"]( value, param ) ) {
                    box.addClass( "validatebox-invalid" );
                    box.parents("div.control-group").addClass("error"); // 集成 bootstrap
                    var message = rule["message"];
                    if( param ) {
                        for( var i = 0; i < param.length; i++ ) {
                            message = message.replace( new RegExp( "\\{" + i + "\\}", "g" ), param[i] );
                        }
                    }
                    setTipMessage( opts.invalidMessage || message );
                    showTip( target );
                    return false;
                }
            }
        }
        box.removeClass( "validatebox-invalid" );
        box.parents("div.control-group").removeClass("error"); // 集成 bootstrap
        hideTip( target );
        return true;
    }
    ;
    $.fn.validatebox = function( options, param ) {
        if( typeof options == "string" ) {
            return $.fn.validatebox.methods[options]( this, param );
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "validatebox" );
            if( state ) {
                $.extend( state.options, options );
            } else {
                init( this );
                $.data( this, "validatebox", {
                    options : $.extend( {}, $.fn.validatebox.defaults, $.fn.validatebox.parseOptions( this ), options )
                } );
            }
            bindEvents( this );
        } );
    };
    $.fn.validatebox.methods = {
        destroy : function( jq ) {
            return jq.each( function() {
                destroyBox( this );
            } );
        },
        validate : function( jq ) {
            return jq.each( function() {
                validate( this );
            } );
        },
        isValid : function( jq ) {
            return validate( jq[0] );
        }
    };
    $.fn.validatebox.parseOptions = function( target ) {
        var t = $( target );
        return $.extend( {}, $.parser.parseOptions( target, ["validType", "missingMessage", "invalidMessage"] ), {
            required : ( t.attr( "required" ) ? true : undefined )
        } );
    };
    $.fn.validatebox.defaults = {
        required : false,
        validType : null,
        missingMessage : "This field is required.",
        invalidMessage : null,
        rules : {
            email : {
                validator : function( value ) {
                    /*jsMin压缩部分正则表达式会发生问题，详见 http://code.google.com/p/minify/issues/detail?id=74
                     * 变通的解决方案是用小括号包裹住正则表达式，如 /'/ 改为 (/'/)
                     * 
                     * 正则表达式后缀 'i'的意义：The i modifier is used to perform case-insensitive matching.
                     **/              
                    return (/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i)
                            .test( value );
                },
                message : "Please enter a valid email address."
            },
           url : {
                validator : function( value ) {
                    /*jsMin压缩部分正则表达式会发生问题，详见 http://code.google.com/p/minify/issues/detail?id=74
                     * 变通的解决方案是用小括号包裹住正则表达式，如 /'/ 改为 (/'/)
                     * 
                     * 正则表达式后缀 'i'的意义：The i modifier is used to perform case-insensitive matching.
                     **/
                    return (/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i)
                            .test( value );
                },
                message : "Please enter a valid URL."
            }, 
            length : {
                validator : function( value, param ) {
                    var len = $.trim( value ).length;
                    return len >= param[0] && len <= param[1];
                },
                message : "Please enter a value between {0} and {1}."
            },
            remote : {
                validator : function( value, param ) {
                    var data = {};
                    data[param[1]] = value;
                    var isValidate = $.ajax( {
                        url : param[0],
                        dataType : "json",
                        data : data,
                        async : false,
                        cache : false,
                        type : "post"
                    } ).responseText;
                    return isValidate == "true";
                },
                message : "Please fix this field."
            },
            /** 
             * 自定义的脚本验证类型，传入的参数中，
             *      第一个参数为脚本验证的具体逻辑，
             *      第二个参数为验证失败后提示的错误信息
             **/
            script : {
                forceValidate : true,    // 当值为空值时是否强制验证
                validator : function( value, param ) {
                    try {
                        return eval( param[0] )
                    } catch(e) {
                        return false;
                    }
                },
                message : "{1}"
            }
        }
    };
} )( jQuery );
