<#if model.nature==-1>
	<#assign accountName = "境外账单" />
<#elseif model.direction == 1>
	<#assign accountName = "结算单" />
<#else>
	<#assign accountName = "对账单" />
</#if>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>${accountName!}管理</title>
<style>
.account-charge1 b { color:#BD362F; }
.account-charge-1 b { color:#51A351; }
.charge-gather1 b { color:#BD362F; }
.charge-gather-1 b { color:#51A351; }
.invoice-gather1 b { color:#51A351; }
.invoice-gather-1 b { color:#BD362F; }
</style>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	var DIRECTION = ${model.direction!1};
	var NATURE = ${model.nature!1};
	var ACCOUNT_NAME = "${accountName!}";
	var QUERY_PARAMS = ${paramsMap!"{}"};
	<#include "account-query-condition.js" />
	<#include "account-table-column.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">${accountName!}管理</h4>
	
	<button id="go_processing" class="btn btn-primary hide"><i class="icon-check icon-white"></i> 提交审核</button>
	
	<@shiro.hasPermission name="finance:account-check">
	<button id="go_checking" class="btn btn-success hide"><i class="icon-ok-circle icon-white"></i> 审核通过</button>
	<button id="return_processing" class="btn btn-danger hide"><i class="icon-ban-circle icon-white"></i> 审核不通过</button>
	<button id="go_checked" class="btn btn-info hide"><i class="icon-check icon-white"></i> 确认<#if model.direction == 1>收款<#else>付款</#if></button>
	<button id="return_checking" class="btn btn-danger hide"><i class="icon-ban-circle icon-white"></i> 重新审核</button>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="finance:account-settle">
	<button id="go_settled" class="btn btn-success hide"><i class="icon-lock icon-white"></i> 结算完毕</button>
	<button id="return_checked" class="btn btn-danger hide"><i class="icon-ban-circle icon-white"></i> 重新开票</button>
	</@shiro.hasPermission>
	<span style="margin-left:50px;"></span>
	<button id="btn_download" class="btn btn-success"><i class="icon-download-alt icon-white"></i> 导出${accountName!}</button>
	<button id="btn_add" class="btn btn-primary"><i class="icon-white icon-plus"></i> 新建... (N)</button>
	<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 编辑/查看... (E)</button>
	<button id="btn_delete" class="btn btn-danger"><i class="icon-white icon-trash"></i> 删除 (D)</button>
</div>
<div id="data_container">
	<div data-options="region:'center'" style="overflow:hidden">
		<table id="data_grid"></table>
	</div>
	<div data-options="region:'east',title:'结算单查询'" style="width:230px;">
		<div id="query" data-options="fit:true, border:false"></div>
	</div>
</div>
</body>
</html>