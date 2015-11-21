/**
 * 多条件符合查询 widget 插件
 */
(function($) {
    
    /** 初始化组件 */
    function initWidget( target, options ) {
        var $this = $( target ).panel( options );
        var queryBar = $("<div class='compositequery-toolbar'></div>").appendTo( $this );
        var queryForm = $("<form class='compositequery-form'></form>").appendTo( $this );
        
        $("<button class='btn btn-primary btn-mini'><i class='icon-white icon-plus'></i> " + options.addLabel + "</button>").click(function(){
            createCondition( target );
        }).appendTo( queryBar );
        $( "<div class='compositequery-btn-separator'></div>" ).appendTo( queryBar );
        $("<button class='btn btn-danger btn-mini'><i class='icon-white icon-trash'></i> " + options.clearLabel + "</button>").click(function(){
            queryForm.empty();
            if( $.isFunction( options.onClear ) ) { options.onClear.call( target ); }
        }).appendTo( queryBar );
        $("<button class='btn btn-success btn-mini' style='float:right'><i class='icon-white icon-search'></i> " + options.queryLabel + "</button>").click(function(){
            if( $.isFunction( options.onQuery ) ) { options.onQuery.call( target, queryForm.serialize() ); }
        }).appendTo( queryBar );
    };
    
    function createCondition(target) {
        var opts = $.data(target, 'compositequery').options;
        var conditions = opts.conditions;
        if( ! conditions || conditions.length <= 0 ) {
            return;
        }
        var rules = opts.rules;

        // 创建 condition-block
        var block = $("<div class='condition-block'></div>");
        block.append( "<button type='button' class='close' data-dismiss='alert'>×</button>" );
        block.appendTo( $("form.compositequery-form", $(target) ) );
        
        var condition = $("<select style='width:150px;'></select>");
        
        for( var i = 0; i < conditions.length; i++ ) {
            condition.append("<option value='"+i+"'>"+conditions[i].title+"</option>");
        }
        condition.appendTo( block );
        var conditionDiv = $("<div></div>").appendTo( block );
        condition.combobox({
            editable:false,
            onSelect : function( record ){
                $(conditionDiv).empty();
                var opt = conditions[ record.id ];
                if( opt.init ) {
                    opt.init( conditionDiv, opt );
                } else {
                    rules[ opt.type ]( conditionDiv, opt );
                }
            }
        });
        condition.combobox("select", 0);
    };
    
    // fn
    $.fn.compositequery = function(options, param) {
        if (typeof options == 'string') {
            return $.fn.compositequery.methods[options](this, param);
        }
        options = options || {};
        return this.each(function() {
            var state = $.data(this, 'compositequery');
            if (state) {
                $.extend(state.options, options);
            } else {
                var opts = $.extend({}, $.fn.compositequery.defaults, options)
                $.data( this, 'compositequery', { options : opts } );
                initWidget( this, opts );
            }
        });
    };
    // method
    $.fn.compositequery.methods = {
        add : function(jq) {
            return jq.each(function() {
                createCondition(this);
            });
        },
        clear : function(jq) {
            return jq.each(function() {
                $(this).empty();
            });
        }
    };
    $.fn.compositequery.defaults = {
            rules : {
                text : function( target, condition ) {
                    $(target).append("<label>" + ( condition.label || "" ) +"</label>");
                    var input = $("<input type='text' name='" + condition.name + "' />").width( $(target).width() - 50 );   // TODO 宽度的设置需要调整
                    $(target).append(input);
                },
                textarea : function( target, condition ) {
                    $(target).append("<label>" + ( condition.label || "" ) +"</label>");
                    var input = $("<textarea row='2'  name='" + condition.name + "'></textarea>").width( $(target).width() - 50 );
                    $(target).append(input);
                },
                date : function( target, condition ) {
                    $(target).append("<label>从</label>");
                    var d1 = $("<input type='text' name='" + condition.name + "' />").width( $(target).width() - 50 );
                    $(target).append(d1);
                    d1.datebox();
                    $(target).append("<br/>").append("<label>到</label>");
                    var d2 = $("<input type='text' name='" + condition.name + "' />").width( $(target).width() - 50 );
                    $(target).append(d2);
                    d2.datebox();
                },
                dateMonth : function( target, condition ) {
                    $(target).append("<label>从</label>");
                    var d1 = $("<input type='text' name='" + condition.name + "' />").width( $(target).width() - 50 );
                    $(target).append(d1);
                    d1.datebox({monthMode:true,formatter : function( date ) {
                        var y = date.getFullYear();
                        var m = date.getMonth()+1;
                        return y+'-'+(m<10?('0'+m):m);
                    }});
                    $(target).append("<br/>").append("<label>到</label>");
                    var d2 = $("<input type='text' name='" + condition.name + "' />").width( $(target).width() - 50 );
                    $(target).append(d2);
                    d2.datebox({monthMode:true,formatter : function( date ) {
                        var y = date.getFullYear();
                        var m = date.getMonth()+1;
                        return y+'-'+(m<10?('0'+m):m);
                    }});
                },
                combo : function( target, condition ) {
                    $(target).append("<label>" + ( condition.label || "" ) +"</label>");
                    var input = $("<input type='text' name='" + condition.name + "' />").width( $(target).width() - 50 );
                    $(target).append(input);
                    input.combobox( $.extend( {/*multiple:true*/}, condition.option ) );
                }
            },
            conditions : [],
            /** 清空查询条件 */
            onClear : function() {},
            /** 确定查询 */
            onQuery : function() {},
            
            /** 添加查询条件按钮的名称 */
            addLabel : "添加",
            /** 清空查询条件按钮的名称 */
            clearLabel : "清空",
            /** 确定查询按钮的名称 */
            queryLabel : "查询"
    };
})(jQuery);