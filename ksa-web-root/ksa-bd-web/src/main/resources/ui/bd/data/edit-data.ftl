<!DOCTYPE html>
<html lang="zh">
<head><title>基本代码 ：${type.name!}</title>
<style type="text/css">
.form-horizontal { padding:10px; }
</style>
</head>
<body>
<form id="dialog_container" method="post" class="easyui-form" action="<@s.url namespace="/dialog/bd/data" action="update" />">
<div class="easyui-panel form-horizontal" data-options="fit:true">
	<@s.actionerror />
	<@s.actionmessage />
	<input type="hidden" name="id" value="${data.id!""}" />
	<input type="hidden" name="type.id" value="${data.type.id!""}" />
	<div class="control-group">
		<label class="control-label" for="code">代码：</label>
		<div class="controls">
	    	<input type="text" name="code" value="${data.code!""}"
	    		class="easyui-validatebox" data-options="required:true,validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="name">中文名称：</label>
		<div class="controls">
	    	<input type="text" name="name" value="${data.name!""}"
	    		class="easyui-validatebox" data-options="required:true,validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="alias">英文名称：</label>
		<div class="controls">
	    	<input type="text" name="alias" value="${data.alias!""}"
	    		class="easyui-validatebox" data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<#if data.type.id == "00-currency">
	<div class="control-group">
		<label class="control-label" for="extra">汇率：</label>
		<div class="controls">
	    	<input type="text" name="extra" value="${data.extra!"1.000"}" style="text-align:right;"
	    		class="easyui-numberbox" data-options="required:true,precision:4,min:0" />
		</div>
	</div>
	<#else>
	<input type="hidden" name="extra" value="${data.extra!}" style="display:none" /> 
	</#if>
	<div class="control-group">
		<label class="control-label" for="note">备注：</label>
		<div class="controls">
			<textarea name="note" rows="<#if data.type.id == "00-currency">2<#else>4</#if>" col="80" class="easyui-validatebox" data-options="validType:'length[0,2000]'" >${data.note!""}</textarea>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="rank">排序：</label>
		<div class="controls">
	    	<input type="text" name="rank" value="${data.rank?c}"
	    		class="easyui-numberbox" data-options="min:0,required:true" />
		</div>
	</div>
	<#-- end : control-group -->
	
</div><#-- end : tab-panel 基本属性 -->
<div class="bottom-bar fixed-bar">
	<button type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>