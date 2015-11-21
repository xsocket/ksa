<!DOCTYPE html>
<html lang="zh">
<head>
<title>备份策略设置</title>
<script type="text/javascript">
	
</script>
</head>
<body>
<form id="dialog_container" method="POST" class="easyui-form form-horizontal" action="<@s.url namespace="/dialog/system/backup/strategy" action="save" />">
	
	<@s.actionmessage />
	<@s.actionerror />
	<div class="control-group" style="margin-top:10px;">
		<label class="control-label" for="on">开启自动备份：</label>
		<div class="controls">
	    	<input type="checkbox" name="on" value="true" <#if on>checked="checked"</#if> />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="next">下次备份时间：</label>
		<div class="controls">
	    	<input type="text" name="next" class="easyui-datetimebox require" data-options="required:true" value="${next?string("yyyy-MM-dd HH:mm:ss")}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="interval">备份间隔周期：</label>
		<div class="controls input-append" style="margin-left:0;">
	    	<input type="text" name="interval" style="text-align:right;width:182px;" <#if interval??>value="${interval?c}"</#if>
	    		class="easyui-numberbox input-small" data-options="min:1,precision:0" data-options="required:true" />
	    	<span class="add-on">天</span>
		</div>
	</div>
	<div class="control-group" style="margin-top:20px;">
		<label class="control-label" for="doJustNow">立即执行备份：</label>
		<div class="controls">
	    	<input type="checkbox" name="doJustNow" value="true" <#if on>checked="checked"</#if> />
		</div>
	</div>
	<#-- end : control-group -->
<div class="bottom-bar fixed-bar">
	<button type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>