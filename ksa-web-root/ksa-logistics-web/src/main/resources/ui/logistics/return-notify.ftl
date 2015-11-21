<!DOCTYPE html>
<html lang="zh">
<head>
<title>退单管理</title>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	<#include "return-notify-query-condition.js" />
	<#include "return-notify-table-column.js" />
	<#include "return-notify.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">退单管理</h4>
	<@shiro.hasPermission name="bookingnote:edit">
	<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 退单... (E)</button>
	</@shiro.hasPermission>
	<@shiro.lacksPermission name="bookingnote:edit">
		<@shiro.hasPermission name="bookingnote:edit:view">
		<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 查看... (E)</button>
		</@shiro.hasPermission>
	</@shiro.lacksPermission>
</div>
<div id="data_container">
	<div data-options="region:'center'" style="overflow:hidden">
		<table id="data_grid"></table>
	</div>
	<div data-options="region:'east',title:'退单查询'" style="width:225px;">
		<div id="query" data-options="fit:true, border:false">
			<fieldset>
				<legend title="">固定参数：<br/>退单提醒天数（进出港 n 天后提示退单）</legend>
				<div style="padding:0 5px;">
				<input id="checkDateCheck" type="checkbox" checked="checked"/>
				<input id="checkDate" type="text" style="text-align:right;" value="30" class="easyui-numberbox input-small" data-options="min:0,precision:0" />
				</div>
			</fieldset>
		</div>
	</div>
</div>
</body>
</html>