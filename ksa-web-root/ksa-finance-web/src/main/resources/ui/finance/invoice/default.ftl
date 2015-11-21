<!DOCTYPE html>
<html lang="zh">
<head>
<title>发票管理</title>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	var DIRECTION = ${model.direction!1};
	<#include "invoice-query-condition.js" />
	<#include "invoice-table-column.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">发票管理<#if model.direction == 1>（支出费用）<#else>（收入费用）</#if></h4>
	<button id="btn_add" class="btn btn-primary"><i class="icon-white icon-plus"></i> 新建... (N)</button>
	<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 编辑/查看... (E)</button>
	<button id="btn_delete" class="btn btn-danger"><i class="icon-white icon-trash"></i> 删除 (D)</button>
</div>
<div id="data_container">
	<div data-options="region:'center'" style="overflow:hidden">
		<table id="data_grid"></table>
	</div>
	<div data-options="region:'east',title:'发票查询'" style="width:230px;">
		<div id="query" data-options="fit:true, border:false"></div>
	</div>
</div>
</body>
</html>