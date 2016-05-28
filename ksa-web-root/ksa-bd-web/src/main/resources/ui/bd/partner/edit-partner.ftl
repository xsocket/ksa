<!DOCTYPE html>
<html lang="zh">
<head><title>合作伙伴信息：${partner.name!""}</title>
<style type="text/css">
.input-xlarge { width: 400px; }
.input-xxlarge { width: 700px; margin-bottom:0; }
.form-inline .control-label { width:50px; }
.form-inline .controls { margin-left: 50px; }
<#-- /* 附加信息样式 */ -->
#text_container div { padding: 10px 20px; border-bottom:1px solid #DDD; }
#text_container div.odd { background-color: #FAFAFA; }
.textrow-hover { background-color: #EFEFEF !important; }
.textrow-selected { background-color: #FBEC88 !important; }
</style>
<script type="text/javascript">
	var EXTRAS = [];
	<#if partner.extras??>
	<#list partner.extras as extra>
  	EXTRAS.push( "${extra?js_string}" );
	</#list> 
	</#if>
	<#include "/js/bd/utils.js"/>
	<#include "partner.js" />
</script>
</head>
<body>
<form id="dialog_container" method="post" class="easyui-form" action="<@s.url namespace="/dialog/bd/partner" action="update" />">
<div class="easyui-tabs" data-options="fit:true">
	<div title="基本信息"  class="tab-panel" style="padding-top:10px;">
		<@s.actionerror />
		<@s.actionmessage />	
		<input id="partner_id" type="hidden" name="id" value="${partner.id!""}" />
		<div class="form-inline" style="float:left;width:480px;">
			<div class="control-group">
				<label class="control-label" for="code">代码：</label>
				<div class="controls">
			    	<input type="text" name="code" value="${partner.code!""}"
			    		class="easyui-validatebox" data-options="required:true,validType:'length[0,200]'" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="saler.name" style="width:75px;">销售担当：</label>
				<div class="controls" style="margin-left:75px">
			    	<input id="saler" type="text" name="saler.id" value="${partner.saler.id!""}" 
			    		class="easyui-combobox input-small" />
				</div>
			</div><br/>
			<div class="control-group">
				<label class="control-label" for="name">名称：</label>
				<div class="controls">
			    	<input type="text" name="name" value="${partner.name!""}"
			    		class="easyui-validatebox" data-options="required:true,validType:'length[0,200]'" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="pp" style="width:75px;">付款周期：</label>
				<div class="controls input-append" style="margin-left:0;">
			    	<input type="text" name="pp" value="${partner.pp!"30"}" style="text-align:right;"
	    				class="easyui-numberbox input-mini" data-options="min:0,required:true"  />
					<span class="add-on">天付款</span>	    				
				</div>
			</div>
			<br/>
			<div class="control-group">
				<label class="control-label" for="address">抬头：</label>
				<div class="controls">
					<textarea name="alias" rows="3" col="80" 
						class="easyui-validatebox input-xlarge" data-options="validType:'length[0,2000]'" >${partner.alias!""}</textarea>
				</div>
			</div>
			<br/>
			<div class="control-group">
				<label class="control-label" for="address">地址：</label>
				<div class="controls">
			    	<input type="text" name="address" value="${partner.address!""}"
			    		class="easyui-validatebox input-xlarge" data-options="validType:'length[0,2000]'" />
				</div>
			</div>
			<br/>
			<div class="control-group">
				<label class="control-label" for="note">备注：</label>
				<div class="controls">
					<textarea name="note" rows="3" col="80" 
						class="easyui-validatebox input-xlarge" data-options="validType:'length[0,2000]'" >${partner.note!""}</textarea>
				</div>
			</div>
		</div>
		<div class="form-inline" style="float:left;width:240px;">
			<div class="control-group">
				<label class="control-label" for="rank" style="width:75px;">排序：</label>
				<div class="controls" style="margin-left:75px">
			    	<input type="text" name="rank" value="${partner.rank?c}" style="text-align:right;"
	    				class="easyui-numberbox input-mini" data-options="min:0,required:true"  />				
				</div>
			</div>
			<div id="divState" class="control-group" <#if partner.important == -1>style="display:none"</#if>>
				<label class="control-label" for="important">常用：</label>
				<div class="controls">
			    	<input id="txtImpotant" type="checkbox" name="important" value="1" <#if partner.important == 1>checked="checked"</#if>>			
				</div>
			</div>
			<br class="clearfix"/>
			<table id="data_grid"></table>
		</div>
	</div><#-- end : tab-panel 基本属性 -->
	<div title="抬头信息"  class="tab-panel">
		<div class="top-bar">
			<button id="extra_add" class="btn btn-primary btn-mini"><i class="icon-plus icon-white"></i> 添加</button>
			<button id="extra_del" class="btn btn-danger btn-mini"><i class="icon-trash icon-white"></i> 删除</button>
		</div>
		<div id="text_container"></div>
	</div>
</div>
<div class="bottom-bar fixed-bar">
	<span style="margin:3px 20px 0 0; float:left;">
		<b for="important">冻结：</b>
		<input id="txtLock" style="margin:0" type="checkbox" name="important" value="-1" <#if partner.important == -1>checked="checked"</#if>>
	</span>
	<button type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>