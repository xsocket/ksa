<!DOCTYPE html>
<html lang="zh">
<head>
<#if model.direction == 1>
	<#assign directionName = "结算单" />
<#else>
<#assign directionName = "对账单" />
</#if>
<title>新建${directionName}</title>
<style type="text/css">
	<#include "account.css" />
</style>
<script type="text/javascript">
	window.BODY_PADDING = 0; // 页面不需要留白
	var STATE = ${model.state!0};
	var CHARGES = ${jsonCharges!'[]'};
	var ACCOUNT_NAME = "${directionName}";
	var DIRECTION = <#if model.direction == 1>1<#else>-1</#if>;
	var NATURE = <#if model.nature == 1>1<#else>-1</#if>;
	<#include "/ui/finance/charge/charge-table-column.js" />
	<#include "account.js"/>
	<#include "/js/finance/utils.js"/>
</script>
</head>
<body>
<div class="easyui-tabs" data-options="fit:true,border:false">  
<div title="${directionName}信息" style="padding-bottom:20px;">
<form id="tab_container" method="POST" class="easyui-form" action="<@s.url namespace="/dialog/finance/account" action="save" />">
	<#include "account-detail.ftl" />
</form><#-- end form -->
</div><#-- end tab-panel 业务基本信息 -->
</div><#-- end tab -->
</body>
</html>