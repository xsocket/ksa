/**
 * combotree - jQuery EasyUI 1.3
 * 
 * Licensed under the GPL terms To use it on other terms please contact us
 * 
 * Copyright(c) 2009-2012 stworthy [ stworthy@gmail.com ]
 * 
 * Dependencies:
 *  combo 
 *  tree
 */
( function( $ ) {
    function setSize( target ) {
        var opts = $.data( target, "combotree" ).options;
        var comboTree = $.data( target, "combotree" ).tree;
        $( target ).addClass( "combotree-f" );
        $( target ).combo( opts );
        var panel = $( target ).combo( "panel" );
        if( !comboTree ) {
            comboTree = $( "<ul></ul>" ).appendTo( panel );
            $.data( target, "combotree" ).tree = comboTree;
        }
        comboTree.tree( $.extend( {}, opts, {
            checkbox : opts.multiple,
            onLoadSuccess : function( node, data ) {
                var values = $( target ).combotree( "getValues" );
                if( opts.multiple ) {
                    var checkNodes = comboTree.tree( "getChecked" );
                    for( var i = 0; i < checkNodes.length; i++ ) {
                        var id = checkNodes[i].id;
                        ( function() {
                            for( var i = 0; i < values.length; i++ ) {
                                if( id == values[i] ) {
                                    return;
                                }
                            }
                            values.push( id );
                        } )();
                    }
                }
                $( target ).combotree( "setValues", values );
                opts.onLoadSuccess.call( this, node, data );
            },
            onClick : function( node ) {
                select( target );
                $( target ).combo( "hidePanel" );
                opts.onClick.call( this, node );
            },
            onCheck : function( node, checked ) {
                select( target );
                opts.onCheck.call( this, node, checked );
            }
        } ) );
    }
    ;
    function select( target ) {
        var opts = $.data( target, "combotree" ).options;
        var comboTree = $.data( target, "combotree" ).tree;
        var vv = [], ss = [];
        if( opts.multiple ) {
            var checkedNode = comboTree.tree( "getChecked" );
            for( var i = 0; i < checkedNode.length; i++ ) {
                vv.push( checkedNode[i].id );
                ss.push( checkedNode[i].text );
            }
        } else {
            var selectedNode = comboTree.tree( "getSelected" );
            if( selectedNode ) {
                vv.push( selectedNode.id );
                ss.push( selectedNode.text );
            }
        }
        $( target ).combo( "setValues", vv ).combo( "setText", ss.join( opts.separator ) );
    }
    ;
    function setValues( target, values ) {
        var opts = $.data( target, "combotree" ).options;
        var comboTree = $.data( target, "combotree" ).tree;
        comboTree.find( "span.tree-checkbox" ).addClass( "tree-checkbox0" ).removeClass( "tree-checkbox1 tree-checkbox2" );
        var vv = [], ss = [];
        for( var i = 0; i < values.length; i++ ) {
            var v = values[i];
            var s = v;
            var node = comboTree.tree( "find", v );
            if( node ) {
                s = node.text;
                comboTree.tree( "check", node.target );
                comboTree.tree( "select", node.target );
            }
            vv.push( v );
            ss.push( s );
        }
        $( target ).combo( "setValues", vv ).combo( "setText", ss.join( opts.separator ) );
    }
    ;
    $.fn.combotree = function( options, param ) {
        if( typeof options == "string" ) {
            var method = $.fn.combotree.methods[options];
            if( method ) {
                return method( this, param );
            } else {
                return this.combo( options, param );
            }
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "combotree" );
            if( state ) {
                $.extend( state.options, options );
            } else {
                $.data( this, "combotree", {
                    options : $.extend( {}, $.fn.combotree.defaults, $.fn.combotree.parseOptions( this ), options )
                } );
            }
            setSize( this );
        } );
    };
    $.fn.combotree.methods = {
        options : function( jq ) {
            return $.data( jq[0], "combotree" ).options;
        },
        tree : function( jq ) {
            return $.data( jq[0], "combotree" ).tree;
        },
        loadData : function( jq, data ) {
            return jq.each( function() {
                var opts = $.data( this, "combotree" ).options;
                opts.data = data;
                var comboTree = $.data( this, "combotree" ).tree;
                comboTree.tree( "loadData", data );
            } );
        },
        reload : function( jq, url ) {
            return jq.each( function() {
                var opts = $.data( this, "combotree" ).options;
                var comboTree = $.data( this, "combotree" ).tree;
                if( url ) {
                    opts.url = url;
                }
                comboTree.tree( {
                    url : opts.url
                } );
            } );
        },
        setValues : function( jq, value ) {
            return jq.each( function() {
                setValues( this, value );
            } );
        },
        setValue : function( jq, value ) {
            return jq.each( function() {
                setValues( this, [value] );
            } );
        },
        clear : function( jq ) {
            return jq.each( function() {
                var comboTree = $.data( this, "combotree" ).tree;
                comboTree.find( "div.tree-node-selected" ).removeClass( "tree-node-selected" );
                var cc = comboTree.tree( "getChecked" );
                for( var i = 0; i < cc.length; i++ ) {
                    comboTree.tree( "uncheck", cc[i].target );
                }
                $( this ).combo( "clear" );
            } );
        }
    };
    $.fn.combotree.parseOptions = function( target ) {
        return $.extend( {}, $.fn.combo.parseOptions( target ), $.fn.tree.parseOptions( target ) );
    };
    $.fn.combotree.defaults = $.extend( {}, $.fn.combo.defaults, $.fn.tree.defaults, {
        editable : false
    } );
} )( jQuery );
