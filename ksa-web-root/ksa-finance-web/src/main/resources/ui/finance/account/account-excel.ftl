<#-- Excel 导出形式的结算单页面 -->
<!DOCTYPE html>
<html lang="zh">
<head>
<#setting number_format="0.##">	<#-- 格式化数字输出 -->
<#if model.direction == 1>
	<#assign directionName = "结算单" />
	<#assign chargeStyle = "font-weight:bold;color:#BD362F;" />
<#else>
	<#assign directionName = "对账单" />
	<#assign chargeStyle = "font-weight:bold;color:#51A351;" />
</#if>
<title>${directionName} 【 ${model.code!} 】</title>
<style type="text/css">
	<#include "account.css" />
</style>
<script type="text/javascript">
	window.BODY_PADDING = 0; // 页面不需要留白
	// 业务托单列表
	var BOOKING_NOTES = ${jsonBookingNotes!'[]'};
	var CHARGE_COLUMNS = [ [
		<#list chargeHeader.keySet().toArray() as currencyName>
			<#if (currencyName_index > 0)>,</#if>{ title : "${currencyName}", colspan : ${chargeHeader.get( currencyName ).size() } }
		</#list>
	],
	[	
		<#list chargeHeader.values() as chargeTypes>
			<#list chargeTypes as type>
				<#if (chargeTypes_index != 0 || type_index != 0)>,</#if>{ align:"right", width:60, field:"${type}", title:"${type.substring(type.indexOf("-") + 1)}",formatter:function(v){return v||"/";}<#if (type.indexOf("小计") >= 0)>, styler:function(){ return "${chargeStyle}"; }</#if> }
			</#list>
		</#list>
	] ];
	
	var CHARGE_DATA = [
		<#list chargeData.values() as charges>
			<#if (charges_index > 0)>,</#if>{
			<#list charges.keySet().toArray() as field>
				<#if (field_index > 0)>,</#if>"${field}": ${charges.get( field ) }
			</#list>
			}
		</#list>
	];
	
	var DIRECTION = ${model.direction!1};
	var ACCOUNT_NAME = "${directionName}";
	<#include "/ui/logistics/bookingnote-table-column.js" />
	<#include "account-excel.js"/>
</script>
</head>
<body>
<div id="dialog_container">
<input id="account_id" type="hidden" name="id" value="${model.id!}" />
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'west',border:false,split:true" style="width:500px;">
		<table id="bn_datagrid" title="业务详细信息及费用汇总">
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<table id="charge_datagrid"></table>
	</div><#-- end center -->
</div>
<div class="bottom-bar fixed-bar">
	<button id="btn_download" class="btn btn-success pull-left"><i class="icon-download-alt icon-white"></i> 导出</button>
	<div style="margin: 1px 10px;padding:1px 5px;" class="alert alert-info pull-left"><b>提示：</b>右键点击业务信息表的表头可以过滤需要导出的内容。</div>
	<button class="btn btn-primary"  onclick="javascript:window.location.href=window.location.href;"><i class="icon-refresh icon-white"></i> 刷新</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</div><#-- end div -->
</body>
</html>