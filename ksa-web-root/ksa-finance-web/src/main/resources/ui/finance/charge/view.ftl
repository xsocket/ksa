<!DOCTYPE html>
<html lang="zh">
<head>
<title>费用信息【${model.code!}】</title>
<#-- 判断状态的静态类 -->
<#assign state = model.state?c />
<#assign stateClass = "@com.ksa.model.finance.BookingNoteChargeState" />
<#-- 资金状态为: 正在输入 or 还未输入 时，可以进行编辑 -->
<#assign readonly = stack.findValue("${stateClass}@isDeleted(${state})") 
	|| stack.findValue("${stateClass}@isChecked(${state}, ${nature})") 
	|| stack.findValue("${stateClass}@isChecking(${state}, ${nature})") >

<style type="text/css" media="screen">
	<#include "view.css" />
</style>
<script type="text/javascript">
	var STATE = ${state};
	var NATURE = ${nature};
	// 当前汇率
	var RATES = ${jsonRates!'[]'};
	// 初始收入费用
	var INCOMES = ${jsonIncomes!'[]'};
	// 初始支出费用
	var EXPENSES = ${jsonExpenses!'[]'};
	// 当前用户
	var CURRENT_USER = { id:"${model.creator.id!}", name:"${model.creator.name!}" };
	var READONLY = <#if readonly>true<#else>false</#if>;
	var CHECK_PERMISSION = false;
	<@shiro.hasPermission name="finance:charge-check">
	CHECK_PERMISSION = true;
	</@shiro.hasPermission>
	<#include "/js/finance/utils.js"/>
	<#include "view.js"/>
</script>
</head>
<body>
<form id="dialog_container" method="POST" action="<@s.url namespace="/dialog/finance/charge" action="save" />">
<input id="note_id" type="hidden" name="id" value="${model.id!}" />
<input id="note_type" type="hidden" name="type" value="${model.type!}" />
<input type="hidden" name="nature" value="${nature}" />
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'" style="height:156px;padding:5px">
		<div class="form-inline" style="float:left;line-height:5px;">
			<div class="control-group">
				<label class="control-label narrow">编号：</label>
				<div class="controls narrow">
			    	<input id="note_code" type="text" value="${model.code!}" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label wide">客户：</label>
				<div class="controls wide">
			    	<input type="text" value="${model.customer.name!}" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label wide">记账月份：</label>
				<div class="controls wide">
			    	<input id="chargeDate" name="customChargeDate" type="text" <#if model.chargeDate??>value="${model.chargeDate?string("yyyy-MM")}"</#if> />
				</div>
			</div> <br /> <#-- 第一行 -->
			<div class="control-group">
				<label class="control-label narrow">发票：</label>
				<div class="controls narrow">
			    	<input type="text"value="${model.invoiceNumber!}" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label wide">提单：</label>
				<div class="controls wide">
					<input type="text" value="${model.mawb!}" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label wide">船名/航次：</label>
				<div class="controls wide">
			    	<input type="text" value="${model.routeName!'无'} / ${model.routeCode!'无'}" />
				</div>
			</div> <br />	<#-- 第二行 -->
			<div class="control-group">
				<label class="control-label narrow">品名：</label>
				<div class="controls narrow">
			    	<input type="text" value="${model.cargoName!}" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label wide">离港日：</label>
				<div class="controls wide">
			    	<input type="text" <#if model.departureDate??>value="${model.departureDate?date}"</#if>/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label wide">到港日：</label>
				<div class="controls wide">
			    	<input type="text" <#if model.destinationDate??>value="${model.destinationDate?date}"</#if>/>
				</div>
			</div> <br />	<#-- 第三行 -->
			<div class="control-group">
				<label class="control-label narrow">体积：</label>
				<div class="controls input-append narrow" style="margin-left:0;">
			    	<input type="text" style="text-align:right;" value="${model.volumn!}" />
			    	<span class="add-on">m<sup>3</sup></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label wide">毛重<#if model.type == "AE" || model.type == "AI">/C.W</#if>：</label>
				<div class="controls input-append" style="margin-left:0;">
					<input type="text" style="text-align:right;" value="${model.weight!'无'}<#if model.type == "AE" || model.type == "AI"> / ${model.cargoContainer!'无'}</#if>" />
				<span class="add-on">kg</span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label wide">数量：</label>
				<div class="controls wide">
			    	<input type="text" style="text-align:right;" value="${model.quantity!} ${model.unit!}" />
				</div>
			</div>	
		</div><#-- end 业务基本信息 -->
		<div style="float:right; height:139px; width:320px;">
			<table id="profit_datagrid">
			</table>
		</div><#-- end 利润统计 -->
	</div><#-- end north  -->
	<div data-options="region:'center',border:false">
		<div class="charge-container">
			<table id="income_datagrid" title="<#if nature==-1>境外</#if>收入费用列表">
			</table>
		</div>
		<div class="charge-container">
			<table id="expense_datagrid" title="<#if nature==-1>境外</#if>支出费用列表"></table>
		</div>
	</div><#-- end center -->
</div>
<div class="bottom-bar fixed-bar">
<#if stack.findValue("${stateClass}@isDeleted(${state})")>
	<span class="title">状态：<b style="text-decoration: line-through;">业务作废</b></span>
<#elseif stack.findValue("${stateClass}@isChecked(${state}, ${nature})")>
	<span class="title">状态：<b style="color:#51A351;">审核通过</b></span>
	<@shiro.hasPermission name="finance:charge-check">
	<button id="return_entering" class="btn btn-danger pull-left"><i class="icon-repeat icon-white"></i> 重新修改</button>
	</@shiro.hasPermission>
<#elseif stack.findValue("${stateClass}@isChecking(${state}, ${nature})")>
	<span class="title">状态：<b style="color:#04C;">审核中</b></span>
	<@shiro.hasPermission name="finance:charge-check">
	<button id="go_checked" class="btn btn-success pull-left"><i class="icon-ok-circle icon-white"></i> 审核通过</button>
	<button id="go_entering" class="btn btn-danger pull-left"><i class="icon-ban-circle icon-white"></i> 审核不通过</button>
	</@shiro.hasPermission>
<#elseif stack.findValue("${stateClass}@isEntering(${state}, ${nature})")>
	<span class="title">状态：<b style="color:#FAA732;">录入中</b></span>	
	<button id="go_checking" class="btn btn-primary pull-left"><i class="icon-check icon-white"></i> 提交审核</button>
<#else>
	<span class="title">状态：<b style="color:#BD362F;">暂未录入</b></span>	
	<button id="copy_template" class="btn btn-success pull-left"><i class='icon-inbox icon-white'></i> 引用模板</button>
</#if>
	<a id="view_account" href="javascript:void(0)" title="查看结算单" class="btn btn-mini btn-success pull-left hide"><i class='icon-tag icon-white'></i> 查看结算单</a>

	<button id="dialog_download" class="btn btn-success"><i class="icon-download-alt icon-white"></i> 导出<#if nature==-1> DEBIT NOTE<#else>面单</#if></button>
	<button id="btn_note" class="btn btn-success">查看业务信息</button>
<#if !readonly>	<#-- 只读状态不允许修改保存  -->
	<button id="dialog_save" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
<#else>
	<@shiro.hasPermission name="finance:charge-check">
	<button id="dialog_save" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	</@shiro.hasPermission>
</#if>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
<#if !readonly>
<div id="charge_window" style="display:none;">
	<form id="charge_form" class="form-inline" style="padding:10px;margin:0; line-height:10px;">
		<div class="control-group">
			<label class="control-label">结算对象：</label>
			<div class="controls">
		    	<input id="customer" type="text" name="customerId" data-options="required:true,validType:'length[0,200]'" style="width:356px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用项目：</label>
			<div class="controls">
		    	<input id="charge_type" type="text" name="type" data-options="required:true,validType:'length[0,200]'" style="width:356px;"/>
			</div>
		</div>
		<br />
		<div class="control-group">
			<label class="control-label">国内/境外：</label>
			<div class="controls">
				<select id="charge_nature" name="nature">
		    		<option value='1' selected="selected">国内费用</option>
		    		<option value='-1'>境外费用</option>
		    	</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收/支方向：</label>
			<div class="controls">
		    	<input id="inout" type="text" name="inout" readonly="readonly" />
			</div>
		</div>
		<br />
		<div class="control-group">
			<label class="control-label">币种：</label>
			<div class="controls">
		    	<input id="currency" type="text" name="currencyId" data-options="required:true,validType:'length[0,200]'" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额：</label>
			<div class="controls">
		    	<input id="amount" type="text" name="amount" style="text-align:right;" class="easyui-numberbox" data-options="required:true,precision:3" />
			</div>
		</div>
		<br />
		<div class="control-group">
			<label class="control-label">单价：</label>
			<div class="controls">
		    	<input id="price" type="text" name="price" style="text-align:right;" class="easyui-numberbox" data-options="precision:3"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量：</label>
			<div class="controls">
		    	<input id="quantity" type="text" name="quantity" style="text-align:right;" class="easyui-numberbox" data-options="min:0,precision:3" />
			</div>
		</div>
		<br />
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
		    	<textarea id="note" name="note" rows="2" class="input-xlarge"></textarea>
			</div>
		</div>
		<br />
		<div class="control-group">
			<label class="control-label">操作员：</label>
			<div class="controls">
		    	<input id="creator_name" type="text" name="creatorName" readonly="readonly" />
		    	<input id="creator_id" type="hidden" name="creatorId" readonly="readonly" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
		    	<input id="created_date" type="text" name="createdDate" readonly="readonly" />
			</div>
		</div>
	</form>
	<div class="bottom-bar">
		<button id="form_continue" class="btn btn-success" style="float:left;"><i class="icon-ok icon-white"></i> 确定并继续添加</button>
		<button id="form_ok" class="btn btn-primary"><i class="icon-ok icon-white"></i> 确定</button>
		<button id="form_close" class="btn btn-danger"><i class="icon-remove icon-white"></i> 取消</button>
	</div>
</div>
</#if>
</body>
</html>