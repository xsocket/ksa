<!DOCTYPE html>
<html lang="zh">
<head>
<title>发票<#if model.code??>【 ${model.code} 】</#if></title>
<style type="text/css">
	<#include "view.css" />
</style>
<script type="text/javascript">
	<#include "view.js"/>
</script>
</head>
<body>
<form id="dialog_container" method="POST" class="easyui-form" action="<@s.url namespace="/dialog/finance/invoice" action="save" />">
	<@s.actionerror />
	<@s.actionmessage />
	<input id="invoice_id" type="hidden" name="id" value="${model.id!}" />
	<div id="invoice_container" class="form-inline" style="line-height:12px;">
		<div class="control-group">
			<label class="control-label">发票号：</label>
			<div class="controls">
		    	<input type="text" name="code" class="easyui-validatebox" value="${model.code!}" data-options="required:true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票类型：</label>
			<div class="controls">
			    <input id="invoice_type" class="easyui-combobox" name="type" value="${model.type!}" 
			    	data-options="validType:'length[0,200]',valueField: 'label',textField: 'value',data: [{label: '增值税专用发票',value: '增值税专用发票'},{label: '增值税普通发票',value: '增值税普通发票'},{label: '其他发票',value: '其他发票'}]" />
			</div>
		</div>
		<br />
		<div class="control-group">
			<label class="control-label">开/收票对象：</label>
			<div class="controls">
		    	<input id="target" type="text" name="target.id" value="${model.target.id!}" 
		    		data-options="required:true,validType:'script[\'validateCustomer()\', \'客户不存在\']'" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收/支方向：</label>
			<div class="controls">
				<input type="text" value="<#if (model.direction == 1)>收到<#else>开出</#if>" readonly="readonly" />
				<input type="hidden" name="direction" value="${model.direction!}" readonly="readonly" />
			<#-- 
				<#if model.account.id??>
				<input type="text" value="<#if (model.direction == 1)>收到<#else>开出</#if>" readonly="readonly" />
				<input type="hidden" name="direction" value="${model.direction!}" readonly="readonly" />
				<#else>
		    	<select id="direction" name="direction">
		    		<option value='1'<#if (model.direction == 1)> selected="selected"</#if>>收到</option>
		    		<option value='-1'<#if (model.direction == -1)> selected="selected"</#if>>开出</option>
		    	</select>
		    	</#if>
		    -->
			</div>
		</div>
		<br />
		<div class="control-group">
			<label class="control-label">币种：</label>
			<div class="controls">
		    	<input id="currency" type="text" name="currency.id" value="${model.currency.id!}"
		    		data-options="required:true,validType:'script[\'validateCurrency()\', \'币种不存在\']'"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额：</label>
			<div class="controls">
		    	<input id="amount" type="text" name="amount" style="text-align:right;" class="easyui-numberbox"  value="${model.amount?c}"
		    		data-options="required:true,min:0,precision:3" />
			</div>
		</div>
		<br />
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
		    	<textarea id="note" name="note" rows="3" class="input-xlarge" value="${model.note!}"></textarea>
			</div>
		</div>
		<br />
		<div class="control-group">
			<label class="control-label">操作员：</label>
			<div class="controls">
		    	<input id="creator_name" type="text" name="creator.name" readonly="readonly" value="${model.creator.name}" />
		    	<input id="creator_id" type="hidden" name="creator.id" value="${model.creator.id!}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开票时间：</label>
			<div class="controls">
		    	<input id="created_date" class="easyui-datebox" type="text" name="createdDate" value="${model.createdDate?date}" data-options="required:true" />
			</div>
		</div>
		<#if model.account.id??>
		<br />
		<div class="control-group">
			<label class="control-label">核算账单：</label>
			<div class="controls">
				<input type="text" name="account.code" class="input-xlarge" readonly="readonly" value="${model.account.code!}" />
		    	<input id="account" type="hidden" name="account.id" readonly="readonly" value="${model.account.id!}" />
			</div>
		</div>
		</#if>
	</div>
	<div class="bottom-bar fixed-bar">
		<span class="title">状态：
		<#if model.id?? && model.account.id??>
			<#assign state = model.account.state?c />
			<#assign stateClass = "@com.ksa.model.finance.AccountState" />
			<#if stack.findValue("${stateClass}@isSettled(${state})")>
				<b style="color:#51A351;">结算完毕</b>
			<#elseif stack.findValue("${stateClass}@isChecked(${state})")>
				<b style="color:#04C;"><#if model.account.direction == 1>收款中<#else>付款中</#if></b>
			<#elseif stack.findValue("${stateClass}@isChecking(${state})")>
				<b style="color:#FAA732;">开票中</b>
			<#elseif stack.findValue("${stateClass}@isProcessing(${state})")>
				<b style="color:#04C;">审核中</b></span>	
			<#else>
				<b style="color:#BD362F;">待审核</b>
			</#if>
		<#else>
			<b style="color:#BD362F;">新建</b>
		</#if>
		</span>
		
		<button id="dialog_save" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
		<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
	</div>
</form><#-- end form -->
</body>
</html>