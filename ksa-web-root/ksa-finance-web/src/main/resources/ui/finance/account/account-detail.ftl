<#-- 判断状态的静态类 -->
<#assign state = model.state?c />
<#assign stateClass = "@com.ksa.model.finance.AccountState" />

<input id="account_id" type="hidden" name="id" value="${model.id!}" />
<input id="account_direction" type="hidden" name="direction" value="${model.direction!1}" />
<input id="account_nature" type="hidden" name="nature" value="${model.nature!1}" />
<div id="account_container" data-options="fit:true">
	<div data-options="region:'north',border:false" style="height:140px;padding:5px">
		<div class="form-inline" style="float:left;line-height:6px;">
			<div class="control-group">
				<label class="control-label">结算对象：</label>
				<div class="controls">
			    	<input id="target" name="target.id" type="text" value="${model.target.id!}" 
			    		data-options="required:true,validType:'script[\'validateCustomer()\', \'客户不存在\']'" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">编号：</label>
				<div class="controls">
			    	<input id="code" type="text" name="code" class="easyui-validatebox" value="${model.code!}" 
			    		data-options="required:true,validType:'length[0,200]'" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">创建人：</label>
				<div class="controls">
			    	<input type="text" name="creator.name" value="${model.creator.name!}" readonly="readonly" />
			    	<input type="hidden" name="creator.id" value="${model.creator.id!}" readonly="readonly" />
				</div>
			</div> <br /> <#-- 第一行 -->
			<div class="control-group">
				<label class="control-label">开单日期：</label>
				<div class="controls">
			    	<input id="createdDate" type="text" name="createdDate" class="easyui-datebox" <#if model.createdDate??>value="${model.createdDate?date}"</#if> />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">付款截止日：</label>
				<div class="controls">
			    	<input id="deadline" type="text" name="deadline" class="easyui-datebox" <#if model.deadline??>value="${model.deadline?date}"</#if> />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">结清日期：</label>
				<div class="controls">
			    	<input id="paymentDate" type="text" name="paymentDate" class="easyui-datebox" <#if model.paymentDate??>value="${model.paymentDate?date}"</#if> />
				</div>
			</div> <br />	<#-- 第二行 -->
			<div class="control-group">
				<label class="control-label">备注：</label>
				<div class="controls">
			    	<textarea row="2" name="note" class="input-xxlarge">${model.note!}</textarea>
				</div>
			</div>
		</div><#-- end 业务基本信息 -->
		<#--<div style="float:right; height:100px; width:320px;">
			<table id="profit_datagrid">
			</table>
		</div> end 利润统计 -->
	</div><#-- end north  -->
	<div class="grid-container" data-options="region:'center',border:false">
		<div class="charge-container">
			<table id="charge_datagrid" title="费用清单">
			</table>
		</div>
		<div class="currency-container">
			<table id="currency_datagrid" title="汇率明细"></table>
		</div>
	</div><#-- end center -->
</div>
<div class="bottom-bar fixed-bar">
	<button id="btn_download" class="btn btn-success pull-left" style="margin-right:10px;"><i class="icon-download-alt icon-white"></i> 导出</button>

	
<#if stack.findValue("${stateClass}@isSettled(${state})")>
	<span class="title">状态：<b style="color:#51A351;">结算完毕</b></span>
<#elseif stack.findValue("${stateClass}@isChecked(${state})")>
	<span class="title">状态：<b style="color:#04C;"><#if model.direction == 1>收款中<#else>付款中</#if></b></span>
	<@shiro.hasPermission name="finance:account-settle">
	<button id="go_settled" class="btn btn-success pull-left"><i class="icon-lock icon-white"></i> 结算完毕</button>
	<button id="return_checked" class="btn btn-danger pull-left"><i class="icon-ban-circle icon-white"></i> 重开发票</button>
	</@shiro.hasPermission>
<#elseif stack.findValue("${stateClass}@isChecking(${state})")>
	<span class="title">状态：<b style="color:#FAA732;">开票中</b></span>
	<@shiro.hasPermission name="finance:account-check">
	<button id="go_checked" class="btn btn-info pull-left"><i class="icon-check icon-white"></i> 确认<#if model.direction == 1>收款<#else>付款</#if></button>
	<button id="return_checking" class="btn btn-danger pull-left"><i class="icon-ban-circle icon-white"></i> 重新审核</button>
	</@shiro.hasPermission>
<#elseif stack.findValue("${stateClass}@isProcessing(${state})")>
	<span class="title">状态：<b style="color:#04C;">审核中</b></span>	
	<@shiro.hasPermission name="finance:account-check">
	<button id="go_checking" class="btn btn-success pull-left"><i class="icon-ok-circle icon-white"></i> 审核通过</button>
	<button id="return_processing" class="btn btn-danger pull-left"><i class="icon-ban-circle icon-white"></i> 审核不通过</button>
	</@shiro.hasPermission>
<#elseif stack.findValue("${stateClass}@isNone(${state})")>
	<#if model.id??>
	<span class="title">状态：<b style="color:#BD362F;">待审核</b></span>
	<button id="go_processing" class="btn btn-primary pull-left"><i class="icon-check icon-white"></i> 提交审核</button>
	<#else>
	<span class="title">状态：<b style="color:#BD362F;">新建</b></span>
	</#if>	
</#if>

<#if ! stack.findValue("${stateClass}@isSettled(${state})")>
	<button id="dialog_save" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
</#if>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>