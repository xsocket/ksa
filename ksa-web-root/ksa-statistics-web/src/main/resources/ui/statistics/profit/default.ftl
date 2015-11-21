<!DOCTYPE html>
<html lang="zh">
<head>
<title>利润统计</title>
<link rel="stylesheet" type="text/css" href="<@s.url value="/wro/ksa-component-compositequery.css" />" />
<style type="text/css">
<#-- 费用不变色
	.charge1, .charge-1 { color: #000; }	
	.charge1 b { color: #BD362F; }
	.charge-1 b { color: #51A351; }	
-->	
	.profit1{ color: #BD362F; }
	.profit-1{ color: #51A351; }	
	#chart_form { margin:0; padding:0; border:none;}
	#chart_form button { margin-top: 2px; }
	#chart_form input {margin-bottom:0;}
	#chart_form div.input-prepend {margin: 0 5px 0 0;}
	#profit_window { overflow: hidden; }
</style>
<script  type="text/javascript" src="<@s.url value="/wro/ksa-component-compositequery.js" />"></script>
<script type="text/javascript">
	var NATURE = ${nature!1};
	// 当前汇率
	var RATES = ${jsonRates!'[]'};
	<#include "/ui/finance/profit/profit-table-column.js" />
	<#include "/ui/finance/profit/profit-query-condition.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">利润统计</h4>
	<div id="title" class="title" style="font-size:16px;text-align:left;"></div>
	<button id="btn_charge_edit" class="btn btn-info"><i class="icon-white icon-zoom-in"></i> <b>费用</b>明细 (E)</button>
	<button id="btn_bn_edit" class="btn btn-info"><i class="icon-white icon-zoom-in"></i> <b>业务</b>明细 (D)</button>
</div>
<div id="data_container">
	<div data-options="region:'center'" style="overflow:hidden">
		<table id="data_grid"></table>
	</div>
	<div data-options="region:'east',title:'统计查询'" style="width:230px;">
		<div id="query" data-options="fit:true, border:false"></div>
	</div>
</div>
<div id="profit_window">
	<div class="tool-bar" style="padding: 10px;">
		<form id="chart_form" target="chart_frame" method="GET" action="<@s.url namespace="/dialog/chart/profit" action="default" />">
			<div id="chart_query" style="display:none;"></div>
			<div class="input-prepend pull-left">
			  <span class="add-on">标题</span><input type="text" class="span2" name="title" placeholder="设置图形标题" />
			</div>
			<div class="input-prepend pull-left">
				<span class="add-on">图形</span>
				<select name="chart" class="easyui-combobox" data-options="editable:false,width:100">
					<option value="Column2D">柱状图</option>
					<option value="Line">折线图</option>
					<option value="Pie2D">饼状图</option>
					<option value="Doughnut2D">饼圈图</option>
				</select>
			</div>
			<div class="input-prepend pull-left">
				<span class="add-on">分组</span>
				<select name="group" class="easyui-combobox" data-options="editable:false,width:100">
					<option value="date">日期</option>
					<option value="customer">客户/代理商</option>
					<option value="creator">操作员</option>
					<option value="saler">销售员</option>
					<option value="type">业务类型</option>
					<option value="departure">起运港</option>
					<option value="destination">目的港</option>
					<option value="shipper">发货人</option>
					<option value="consignee">收货人</option>
				</select>
			</div>
			<button id="btn_chart" class="btn btn-primary" title="根据当前利润数据创建相应的图表"><i class="icon-white icon-picture"></i> 生成图形</button>
		</form>
	</div>
	<iframe id="chart_frame" name="chart_frame" style="border:none; width:100%;overflow:hidden;"></iframe>
</div>
</body>
</html>