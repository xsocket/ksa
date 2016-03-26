<!DOCTYPE html>
<html lang="zh">
<head>
<#if model.direction == 1>
	<#assign directionName = "结算单" />
<#else>
<#assign directionName = "对账单" />
</#if>
<title>编辑${directionName}</title>
<script type="text/javascript">
	window.BODY_PADDING = 0; // 页面不需要留白
	$("#account_tab").tabs({ fit:true, border:false });
</script>
</head>
<body>
<div id="account_tab" class="easyui-tabs" data-options="fit:true,border:false">  
	<div title="${directionName}信息" data-options="<#if selected==0>selected:true,</#if>href:'<@s.url value="/dialog/finance/account/account.action?id=${model.id!}" />'">  
	</div><#-- end tab-panel 业务基本信息 -->
	<div title="${directionName}导出" data-options="<#if selected==1>selected:true,</#if>href:'<@s.url value="/dialog/finance/account/account-excel.action?id=${model.id!}" />'">  
	</div><#-- end tab-panel 业务基本信息 -->
<@shiro.hasAnyPermissions name="finance:account-check,finance:account-settle">
	<div title="开票确认" data-options="<#if selected==2>selected:true,</#if>href:'<@s.url value="/dialog/finance/account/invoice.action?id=${model.id!}" />'">  
	</div><#-- end tab-panel 业务基本信息 -->
</@shiro.hasAnyPermissions>
</div><#-- end tab -->
</body>
</html>