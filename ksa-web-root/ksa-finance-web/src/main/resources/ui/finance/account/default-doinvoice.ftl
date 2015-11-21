<!DOCTYPE html>
<html lang="zh">
<head>
<title>账单管理</title>
<style>
.account-charge b { color: <#if model.direction == 1>#BD362F<#else>#51A351</#if>; }
.charge-gather b { color: <#if model.direction == 1>#BD362F<#else>#51A351</#if>; }
.invoice-gather b { color: <#if model.direction == -1>#BD362F<#else>#51A351</#if>; }
</style>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	var DIRECTION = ${model.direction!1};
	var NATURE= ${model.nature!1};
	var ACCOUNT_NAME = <#if model.direction == 1>"结算单"<#else>"对账单"</#if>;
	var QUERY_PARAMS = ${paramsMap!"{}"};
	<#include "account-query-condition.js" />
	<#include "account-table-column.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">账单列表</h4>
	
	<button id="go_processing" class="btn btn-info hide"><i class="icon-ok-circle icon-white"></i> 确认生成</button>
	<button id="go_checking" class="btn btn-primary hide"><i class="icon-check icon-white"></i> 提交审核</button>
	
	<@shiro.hasPermission name="finance:account-check">
	<button id="go_checked" class="btn btn-success hide"><i class="icon-ok-circle icon-white"></i> 审核通过</button>
	<button id="go_unchecked" class="btn btn-danger hide"><i class="icon-ban-circle icon-white"></i> 审核不通过</button>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="finance:account-settle">
	<button id="go_settled" class="btn btn-success hide"><i class="icon-lock icon-white"></i> 结算完毕</button>
	</@shiro.hasPermission>
	<span style="margin-left:50px;"></span>
	
	<button id="btn_doinvoice" class="btn btn-warning"><i class="icon-white icon-edit"></i> 开票... (E)</button>
</div>
<div id="data_container">
	<div data-options="region:'center'" style="overflow:hidden">
		<table id="data_grid"></table>
	</div>
</div>
</body>
</html>