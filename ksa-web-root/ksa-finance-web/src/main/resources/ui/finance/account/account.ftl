<!DOCTYPE html>
<html lang="zh">
<head>
<#if model.direction == 1>
	<#assign directionName = "结算单" />
<#else>
<#assign directionName = "对账单" />
</#if>
<title>${directionName} 【 ${model.code!} 】</title>
<style type="text/css">
	<#include "account.css" />
</style>
<script type="text/javascript">
	window.BODY_PADDING = 0; // 页面不需要留白
	var STATE = ${model.state!0};
	var CHARGES = ${jsonCharges!'[]'};
	var DIRECTION = ${model.direction!1};
	var NATURE = ${model.nature!1};
	var ACCOUNT_NAME = "${directionName}";
	<#include "/ui/finance/charge/charge-table-column.js" />
	<#include "account.js"/>
	<#include "/js/finance/utils.js"/>
</script>
</head>
<body>
<form id="dialog_container" method="POST" class="easyui-form" action="<@s.url namespace="/dialog/finance/account" action="save" />">
	<#include "account-detail.ftl" />
</form><#-- end form -->
</body>
</html>