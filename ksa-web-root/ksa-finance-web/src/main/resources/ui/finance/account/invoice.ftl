<!DOCTYPE html>
<html lang="zh">
<head>
<#if model.direction == 1>
	<#assign directionName = "结算单" />
	<#assign chargeStyle = "font-weight:bold;color:#BD362F;" />
<#else>
	<#assign directionName = "对账单" />
	<#assign chargeStyle = "font-weight:bold;color:#51A351;" />
</#if>
<title>${directionName} 【 ${model.code!} 】发票信息</title>
<style type="text/css">
	/** 工具栏 */
	div.bottom-bar  span.title {margin:3px 20px 0 0;}
</style>
<script type="text/javascript">
	var STATE = ${model.state!};
	var ACCOUNT_ID = "${model.id!}";
	var DIRECTION = ${model.direction};
	var DIRECTOIN_NAME = "${directionName}";
	var TARGET_ID = "${model.target.id!}";
	<#include "/js/finance/utils.js" />
	<#include "/ui/finance/invoice/invoice-table-column.js" />
	<#include "invoice.js"/>
</script>
</head>
<body>
<div id="dialog_container">
	<table id="data_grid"></table>
	<div class="bottom-bar fixed-bar">
		<#-- 判断状态的静态类 -->
<#assign state = model.state?c />
<#assign stateClass = "@com.ksa.model.finance.AccountState" />
<#if stack.findValue("${stateClass}@isSettled(${state})")>
	<span class="title">状态：<b style="color:#51A351;">结算完毕</b></span>
<#elseif stack.findValue("${stateClass}@isChecked(${state})")>
	<span class="title">状态：<b style="color:#04C;"><#if model.direction == 1>收款中<#else>付款中</#if></b></span>
	<button id="go_settled" class="btn btn-success pull-left"><i class="icon-lock icon-white"></i> 结算完毕</button>
	<button id="return_checked" class="btn btn-danger pull-left"><i class="icon-ban-circle icon-white"></i> 重开发票</button>
<#elseif stack.findValue("${stateClass}@isChecking(${state})")>
	<span class="title">状态：<b style="color:#FAA732;">开票中</b></span>
	<button id="go_checked" class="btn btn-info pull-left"><i class="icon-check icon-white"></i> 确认<#if model.direction == 1>收款<#else>付款</#if></button>
<#elseif stack.findValue("${stateClass}@isProcessing(${state})")>
	<span class="title">状态：<b style="color:#04C;">审核中</b></span>	
	<button id="go_checking" class="btn btn-success pull-left"><i class="icon-ok-circle icon-white"></i> 审核通过</button>
	<button id="return_processing" class="btn btn-danger pull-left"><i class="icon-ban-circle icon-white"></i> 审核不通过</button>
<#elseif stack.findValue("${stateClass}@isNone(${state})")>
	<#if model.id??>
	<span class="title">状态：<b style="color:#BD362F;">待审核</b></span>
	<button id="go_processing" class="btn btn-primary pull-left"><i class="icon-check icon-white"></i> 提交审核</button>
	<#else>
	<span class="title">状态：<b style="color:#BD362F;">新建</b></span>
	</#if>	
</#if>
		<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
	</div>
</div><#-- end div -->
</body>
</html>