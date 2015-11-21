/**
 * combogrid - jQuery EasyUI 1.3
 * 
 * Licensed under the GPL terms To use it on other terms please contact us
 * 
 * Copyright(c) 2009-2012 stworthy [ stworthy@gmail.com ]
 * 
 * Dependencies:
 *   combo
 *   datagrid
 */
( function( $ ) {
    function create( target ) {
        var opts = $.data( target, "combogrid" ).options;
        var grid = $.data( target, "combogrid" ).grid;
        $( target ).addClass( "combogrid-f" );
        $( target ).combo( opts );
        var panel = $( target ).combo( "panel" );
        if( !grid ) {
            grid = $( "<table></table>" ).appendTo( panel );
            $.data( target, "combogrid" ).grid = grid;
        }
        grid.datagrid( $.extend( {}, opts, {
            border : false,
            fit : true,
            singleSelect : ( !opts.multiple ),
            onLoadSuccess : function( data ) {
                var remainText = $.data( target, "combogrid" ).remainText;
                var values = $( target ).combo( "getValues" );
                setValues( target, values, remainText );
                opts.onLoadSuccess.apply( target, arguments );
            },
            onClickRow : onClickRow,
            onSelect : function( rowIndex, rowData ) {
                retrieveValues();
                opts.onSelect.call( this, rowIndex, rowData );
            },
            onUnselect : function( rowIndex, rowData ) {
                retrieveValues();
                opts.onUnselect.call( this, rowIndex, rowData );
            },
            onSelectAll : function( rows ) {
                retrieveValues();
                opts.onSelectAll.call( this, rows );
            },
            onUnselectAll : function( rows ) {
                if( opts.multiple ) {
                    retrieveValues();
                }
                opts.onUnselectAll.call( this, rows );
            }
        } ) );
        function onClickRow( rowIndex, row ) {
            $.data( target, "combogrid" ).remainText = false;
            retrieveValues();
            if( !opts.multiple ) {
                $( target ).combo( "hidePanel" );
            }
            opts.onClickRow.call( this, rowIndex, row );
        }
        ;
        function retrieveValues() {
            var remainText = $.data( target, "combogrid" ).remainText;
            var rows = grid.datagrid( "getSelections" );
            var vv = [], ss = [];
            for( var i = 0; i < rows.length; i++ ) {
                vv.push( rows[i][opts.idField] );
                ss.push( rows[i][opts.textField] );
            }
            if( !opts.multiple ) {
                $( target ).combo( "setValues", ( vv.length ? vv : [""] ) );
            } else {
                $( target ).combo( "setValues", vv );
            }
            if( !remainText ) {
                $( target ).combo( "setText", ss.join( opts.separator ) );
            }
        }
        ;
    }
    ;
    function selectRow( target, step ) {
        var opts = $.data( target, "combogrid" ).options;
        var grid = $.data( target, "combogrid" ).grid;
        var length = grid.datagrid( "getRows" ).length;
        $.data( target, "combogrid" ).remainText = false;
        var index;
        var rows = grid.datagrid( "getSelections" );
        if( rows.length ) {
            index = grid.datagrid( "getRowIndex", rows[rows.length - 1][opts.idField] );
            index += step;
            if( index < 0 ) {
                index = 0;
            }
            if( index >= length ) {
                index = length - 1;
            }
        } else {
            if( step > 0 ) {
                index = 0;
            } else {
                if( step < 0 ) {
                    index = length - 1;
                } else {
                    index = -1;
                }
            }
        }
        if( index >= 0 ) {
            grid.datagrid( "clearSelections" );
            grid.datagrid( "selectRow", index );
        }
    }
    ;
    function setValues( target, values, remainText ) {
        var opts = $.data( target, "combogrid" ).options;
        var grid = $.data( target, "combogrid" ).grid;
        var rows = grid.datagrid( "getRows" );
        var ss = [];
        for( var i = 0; i < values.length; i++ ) {
            var index = grid.datagrid( "getRowIndex", values[i] );
            if( index >= 0 ) {
                grid.datagrid( "selectRow", index );
                ss.push( rows[index][opts.textField] );
            } else {
                ss.push( values[i] );
            }
        }
        if( $( target ).combo( "getValues" ).join( "," ) == values.join( "," ) ) {
            return;
        }
        $( target ).combo( "setValues", values );
        if( !remainText ) {
            $( target ).combo( "setText", ss.join( opts.separator ) );
        }
    }
    ;
    function doQuery( target, q ) {
        var opts = $.data( target, "combogrid" ).options;
        var grid = $.data( target, "combogrid" ).grid;
        $.data( target, "combogrid" ).remainText = true;
        if( opts.multiple && !q ) {
            setValues( target, [], true );
        } else {
            setValues( target, [q], true );
        }
        if( opts.mode == "remote" ) {
            grid.datagrid( "clearSelections" );
            grid.datagrid( "load", $.extend( {}, opts.queryParams, {
                q : q
            } ) );
        } else {
            if( !q ) {
                return;
            }
            var rows = grid.datagrid( "getRows" );
            for( var i = 0; i < rows.length; i++ ) {
                if( opts.filter.call( target, q, rows[i] ) ) {
                    grid.datagrid( "clearSelections" );
                    grid.datagrid( "selectRow", i );
                    return;
                }
            }
        }
    }
    ;
    $.fn.combogrid = function( options, param ) {
        if( typeof options == "string" ) {
            var method = $.fn.combogrid.methods[options];
            if( method ) {
                return method( this, param );
            } else {
                return $.fn.combo.methods[options]( this, param );
            }
        }
        options = options || {};
        return this.each( function() {
            var state = $.data( this, "combogrid" );
            if( state ) {
                $.extend( state.options, options );
            } else {
                state = $.data( this, "combogrid", {
                    options : $.extend( {}, $.fn.combogrid.defaults, $.fn.combogrid.parseOptions( this ), options )
                } );
            }
            create( this );
        } );
    };
    $.fn.combogrid.methods = {
        options : function( jq ) {
            return $.data( jq[0], "combogrid" ).options;
        },
        grid : function( jq ) {
            return $.data( jq[0], "combogrid" ).grid;
        },
        setValues : function( jq, values ) {
            return jq.each( function() {
                setValues( this, values );
            } );
        },
        setValue : function( jq, value ) {
            return jq.each( function() {
                setValues( this, [value] );
            } );
        },
        clear : function( jq ) {
            return jq.each( function() {
                $( this ).combogrid( "grid" ).datagrid( "clearSelections" );
                $( this ).combo( "clear" );
            } );
        }
    };
    $.fn.combogrid.parseOptions = function( target ) {
        var t = $( target );
        return $.extend( {}, 
                        $.fn.combo.parseOptions( target ), 
                        $.fn.datagrid.parseOptions( target ), 
                        $.parser.parseOptions( target, [ "idField", "textField", "mode"] ) 
                    );
    };
    $.fn.combogrid.defaults = $.extend( {}, $.fn.combo.defaults, $.fn.datagrid.defaults, {
        loadMsg : null,
        idField : null,
        textField : null,
        mode : "local",
        keyHandler : {
            up : function() {
                selectRow( this, -1 );
            },
            down : function() {
                selectRow( this, 1 );
            },
            enter : function() {
                selectRow( this, 0 );
                $( this ).combo( "hidePanel" );
            },
            query : function( q ) {
                doQuery( this, q );
            }
        },
        /** Defines how to select the local data when 'mode' setted to 'local'. Return true to select the row. */
        filter : function( q, row ) {
            var opts = $( this ).combogrid( "options" );
            return row[opts.textField].indexOf( q ) == 0;
        }
    } );
} )( jQuery );
