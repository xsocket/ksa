<!DOCTYPE html>
<html lang="zh">
<head>
<title>账单管理</title>
<style>
.account-charge1 b { color:#BD362F; }
.account-charge-1 b { color:#51A351; }
.charge-gather1 b { color:#BD362F; }
.charge-gather-1 b { color:#51A351; }
.invoice-gather1 b { color:#51A351; }
.invoice-gather-1 b { color:#BD362F; }
.btn-mini { margin: 3px 5px; }
</style>
<script type="text/javascript">
	<#include "/ui/finance/account/account-table-column.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar fixed-bar" style="padding:1px 2px;">
	<button id="go_processing" class="btn btn-mini btn-info hide"><i class="icon-ok-circle icon-white"></i> 确认生成</button>
	<button id="go_checking" class="btn btn-mini btn-primary hide"><i class="icon-check icon-white"></i> 提交审核</button>
	
	<@shiro.hasPermission name="finance:account-check">
	<button id="go_checked" class="btn btn-mini btn-success hide"><i class="icon-ok-circle icon-white"></i> 审核通过</button>
	<button id="go_unchecked" class="btn btn-mini btn-danger hide"><i class="icon-ban-circle icon-white"></i> 审核不通过</button>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="finance:account-settle">
	<button id="go_settled" class="btn btn-mini btn-success hide"><i class="icon-lock icon-white"></i> 结算完毕</button>
	</@shiro.hasPermission>
	<span style="margin-left:50px;"></span>
	<button id="btn_edit" class="btn btn-mini btn-warning"><i class="icon-white icon-edit"></i> 编辑/查看 ...</button>
</div>
<div id="portal_container">
	<table id="data_grid"></table>
</div>
</body>
</html>