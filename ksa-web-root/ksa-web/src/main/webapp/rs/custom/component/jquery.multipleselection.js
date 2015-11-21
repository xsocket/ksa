/**
 * 多选组件
 * 这是一个复合组件，组件左边部分为数据区域（展示候选数据），右边部分的已选区域（展示已经选择的数据），
 * 中间有 "选择" 和 "移除" 按钮。
 */
(function($) {
    /**
     * 初始化组件
     */
    function init( target, options ) {
        var east = $("<div data-options='fit:true, border:false'></div>");
        $("<div style='width:"+( options.resultSectionWidth + 75 )+"px;'></div>").attr("data-options", "region:'east',split:true,border:false").append( east ).appendTo( target );
        var dataSection = $("<div></div>").attr("data-options", "region:'center'").attr("title", options.dataSectionTitle).appendTo( target );
        $(target).layout( options );
        
        var buttonSection = $("<div style='width:75px;'></div>").attr("data-options", "region:'west',border:false").appendTo( east );
        var buttonBar = $("<div style='position:absolute;top:50%;height:200px;margin-top:-100px;'></div>").appendTo(buttonSection);
        var addButton = $("<button class='btn btn-mini' style='display:block; margin:50px 10px;'>" + options.addButtonTitle + " <i class='icon-arrow-right'></i></button>").appendTo(buttonBar);
        var removeButton = $("<button class='btn btn-mini' style='display:block; margin:50px 10px;'><i class='icon-arrow-left'></i> " + options.removeButtonTitle + "</button>").appendTo(buttonBar);
        var resultSection = $("<div></div>").attr("data-options", "region:'center'").attr("title", options.resultSectionTitle).appendTo( east );
        var selection = $("<select multiple='multiple' style='display:block; width:100%;height:100%;margin:0; border:none;'></selection>").appendTo(resultSection);
        $(east).layout( options );
        
        // 初始化数据区域
        options.initDataSection.call( target, dataSection );
        
        addButton.click(function(){
            var opts = $.data(target, 'multipleselection').options
            var data = opts.getSelectedData.call( target );
            addData( target, data );
        });
        
        // 移除已选数据
        removeButton.click(function(){
            var selects = selection.children("option:selected");
            // 所选数据大于0            
            if( selects.length > 0 ) {
                var data = [];
                selects.each(function(i,v){
                    data.push( { value:$(v).attr("value"), text:$(v).text() } );
                });
                var opts = $.data(target, 'multipleselection').options;
                if( opts.onRemove.call( target, data ) ) {
                    selects.remove();
                    var selectedData = $.data(target, 'multipleselection').data; 
                    $.each( data, function(i,d) {
                        selectedData[ d.value ] = null;    // 删除已选的数据
                    }  );
                }
            }
        });
        
        return {
          selection:  selection
        };
    };
    
    function addData( target, data ) {
        var $t = $.data(target, 'multipleselection');
        var opts = $t.options;
        var selection = $t.selection;
        var selectedData = $t.data;    // 保存添加的数值
        if( data && $.isArray( data ) ) {
            $.each( data, function( i, v ) {
                // 不允许重复数据
                var value = v.value;
                if( selectedData[ value ] != null ) {
                    // 已经存在相同 value 的数据，则不继续添加
                    return;
                }
                // 添加选中的数据
                $("<option></option>").attr("value", v.value).text(v.text).appendTo( selection );  
                selectedData[ value ] = v;
            } );
        }
    }
   
    function getResults( target ) {
        var data = $.data(target, 'multipleselection');
        var selectedData = data.data;
        var results = [];
        for( var key in selectedData ) {
            if( selectedData[key] != null ) {
                results.push( selectedData[key] );
            }
        }
        return results;
    };
    function getValues( target ) {
        var data = $.data(target, 'multipleselection');
        var results = [];
        data.selection.children("option").each(function(i,v){
            results.push( $(v).attr("value") );
        });
        return results;
    };
    function getTexts( target ) {
        var data = $.data(target, 'multipleselection');
        var results = [];
        data.selection.children("option").each(function(i,v){
            results.push( $(v).text() );
        });
        return results;
    };
    
    // fn
    $.fn.multipleselection = function(options, param) {
        if (typeof options == 'string') {
            return $.fn.multipleselection.methods[options](this, param);
        }
        options = options || {};
        return this.each(function() {
            var state = $.data(this, 'multipleselection');
            if ( state) {
                $.extend(state.options, options);
            } else {
                var opts = $.extend( {}, $.fn.multipleselection.defaults, $.fn.multipleselection.parseOptions( this ), options );
                var widget = init( this, opts );
                state = $.data( this, "multipleselection", {
                    options : opts,
                    selection : widget.selection,
                    data : {} // 存放选中的数据, 
                } );
            }
        });
    };
    // method
    $.fn.multipleselection.methods = {
        addData : function( jq, data ) {    // data[n] = { value:'', text:'', data:{} }
            return jq.each(function() {
                addData(this, $.isArray( data ) ? data : [ data ] );
            });
        },
        getResults : function(jq) {
            return getResults( jq[0] );
        },
        getValues : function( jq ) {
            return getValues( jq[0] );
        },
        getTexts : function( jq ) {
            return getTexts( jq[0] );
        }
    };
    $.fn.multipleselection.parseOptions = function( target ) {
        var t = $( target );
        return $.extend( {}, $.fn.panel.parseOptions( target ), $.parser.parseOptions( target, ["dataSectionTitle", "resultSectionTitle"] ) );
    };
    $.fn.multipleselection.defaults = {
        width: "auto",
        height: "auto",
        fit: false,
        
        dataSectionTitle: "数据选择",
        resultSectionTitle: "选择结果",
        resultSectionWidth: 150,
        addButtonTitle: "选择",
        removeButtonTitle: "移除",
        // 监听事件
        onRemove : function( dataArray ) {
            // 返回false可以阻止移除操作。
            return true;
        }, 
        
        // 以下是需要用户扩展的函数
        /**
         * need override 初始化待选数据区域
         */ 
        initDataSection : function( container ) {
            
        },
        /**
         * need override 获取需要被选中的数据（这些数据将被加入结果列表）
         * 返回结果为 {value:'', text:''} 形式的数组。
         */
        getSelectedData : function() {
            return [];
        }
    };
})(jQuery);
