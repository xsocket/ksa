<!DOCTYPE html>
<html lang="zh">
<head>
<#if direction == 1>
	<#assign directionName = "收入费用" />
<#else>
	<#assign directionName = "支出成本" />
</#if>
<title>${directionName}管理</title>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<style type="text/css">
	.charge1, .charge-1 { color: #000; }	
	.charge1 b { color: #BD362F; }
	.charge-1 b { color: #51A351; }	
</style>
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	<#if direction == 1>var DIRECTION = 1;<#else>var DIRECTION = -1;</#if>
	<#if nature == 1>var NATURE = 1;<#else>var NATURE = -1;</#if>
	<#include "charge-single-condition.js" />
	<#include "charge-single-column.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">${directionName}管理</h4>
	<button id="go_checking" class="btn btn-primary hide"><i class="icon-check icon-white"></i> 提交审核</button>
	<@shiro.hasPermission name="finance:charge-check">
	<button id="go_checked" class="btn btn-success hide"><i class="icon-ok-circle icon-white"></i> 审核通过</button>
	<button id="go_entering" class="btn btn-danger hide"><i class="icon-ban-circle icon-white"></i> 审核不通过</button>
	</@shiro.hasPermission>
	<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 编辑/查看... (E)</button>
</div>
<div id="data_container">
	<div data-options="region:'center'" style="overflow:hidden">
		<table id="data_grid"></table>
	</div>
	<div data-options="region:'east',title:'费用查询'" style="width:230px;">
		<div id="query" data-options="fit:true, border:false"></div>
	</div>
</div>
</body>
</html>