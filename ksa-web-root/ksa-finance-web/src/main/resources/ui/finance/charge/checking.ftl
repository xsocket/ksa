<!DOCTYPE html>
<html lang="zh">
<head>
<#if nature==1>
	<#assign pageTitle="业务">
<#else>
	<#assign pageTitle="境外">
</#if>
<title>${pageTitle}费用审核</title>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<style type="text/css">
	.charge1, .charge-1 { color: #000; }	
	.charge1 b { color: #BD362F; }
	.charge-1 b { color: #51A351; }	
</style>
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	var NATURE = ${nature!1};
	<#include "/ui/finance/profit/profit-query-condition.js" />
	<#include "/ui/finance/profit/profit-table-column.js" />
	<#include "checking.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">${pageTitle}费用审核</h4>
	<button id="btn_download" class="btn btn-success hide"><i class="icon-download-alt icon-white"></i> 导出<#if nature==-1> DEBIT NOTE<#else>面单</#if></button>
	<button id="go_checking" class="btn btn-primary hide"><i class="icon-check icon-white"></i> 提交审核</button>
	<@shiro.hasPermission name="finance:charge-check">
	<button id="go_checked" class="btn btn-success hide"><i class="icon-ok-circle icon-white"></i> 审核通过</button>
	<button id="go_entering" class="btn btn-danger hide"><i class="icon-ban-circle icon-white"></i> 审核不通过</button>
	<button id="return_entering" class="btn btn-danger hide"><i class="icon-repeat icon-white"></i> 重新修改</button>
	</@shiro.hasPermission>
	<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 编辑/查看... (E)</button>
</div>
<div id="data_container">
	<div data-options="region:'center'" style="overflow:hidden">
		<table id="data_grid"></table>
	</div>
</div>
</body>
</html>