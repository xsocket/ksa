<!DOCTYPE html>
<html lang="zh">
<head><title>每月货币汇率</title>
<style type="text/css">
	.toolbar input { margin-bottom : 0; }
	html, body { overflow : hidden; }
</style>
<script type="text/javascript">
	<#include "default.js" />
</script>
</head>
<body>
<form method="POST">
<div class="top-bar toolbar">
	<h4 class="title">每月汇率列表 <span class="label label-info">${yyyy?c}年${mm?c}月</span></h4>
	<input id="yy" type="text" class="easyui-numberspinner" style="width:75px;" 
		name="yyyy" value="${yyyy?c}" data-options="min:1980,max:2100" /> 年
	<input id="mm" type="text" class="easyui-numberspinner" style="width:60px;" 
		name="mm" value="${mm?c}" data-options="min:1,max:12" /> 月
	<button class="btn btn-success"><i class="icon-white icon-search"></i> 查询 (Q)</button>
	<button id="save" class="btn btn-primary" style="margin-left:50px;"><i class="icon-white icon-ok"></i> 保存 (S)</button>
</div>
<table id="data-grid"></table>
</form>
</body>
</html>