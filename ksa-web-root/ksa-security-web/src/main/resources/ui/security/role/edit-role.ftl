<!DOCTYPE html>
<html lang="zh">
<head><title>编辑角色</title>
<script type="text/javascript" src="<@s.url value="/wro/ksa-security-utils.js" />"></script>
<script type="text/javascript">
	<#include "role-action.js" />
	<#include "role.js" />
</script>
</head>
<body>
<form id="dialog_container" method="post" class="easyui-form" action="<@s.url namespace="/dialog/security/role" action="update" />">
	<input id="role_id" type="hidden" name="id" value="${role.id!""}" />
<div class="easyui-layout" data-options="fit:true">
	<div class="form-horizontal" data-options="region:'north',border:false" style="height:150px;padding-top:10px;">
		<@s.actionerror />
		<@s.actionmessage />
		<div class="control-group">
			<label class="control-label" for="id">角色名称：</label>
			<div class="controls">
		    	<input type="text" name="name" value="${role.name!""}"
		    		class="easyui-validatebox input-xlarge" data-options="required:true,validType:'length[1,200]'" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="id">角色说明：</label>
			<div class="controls">
				<textarea name="description" rows="3"
					class="easyui-validatebox input-xlarge" data-options="validType:'length[0,2000]'">${role.description!""}</textarea>
			</div>
		</div>
		<#-- end : control-group -->
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-tabs" data-options="fit:true">
			<div title="所含用户"  class="tab-panel">
				<table id="user_grid" data-options="fit:true,border:false"></table>
			</div><#-- end : tab-panel 所属用户 -->
			<div title="角色权限"  class="tab-panel">
				<table id="permission_grid" data-options="fit:true,border:false"></table>
			</div><#-- end : tab-panel 角色权限 -->
		</div>
	</div>
</div>
<div class="bottom-bar fixed-bar">
	<button type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>