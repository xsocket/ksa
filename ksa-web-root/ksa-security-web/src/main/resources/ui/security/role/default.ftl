<!DOCTYPE html>
<html lang="zh">
<head>
<title>角色管理</title>
<script type="text/javascript" src="<@s.url value="/wro/ksa-security-utils.js" />"></script>
<script type="text/javascript">
	<#include "role-action.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar toolbar">
	<h4 class="title">角色列表</h4>
	<button id="btn_add" class="btn btn-primary"><i class="icon-white icon-plus"></i> 新建... (N)</button>
	<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 编辑... (E)</button>
	<button id="btn_delete" class="btn btn-danger"><i class="icon-white icon-trash"></i> 删除 (D)</button>
</div>
<div id="data_container">
	<div data-options="region:'center'">
		<table id="role_grid" data-options="fit:true"></table>
	</div>
	<div data-options="region:'east',split:true,border:false" style="width:400px;">
		<div class="easyui-tabs" data-options="fit:true,plain:false">  
		    <div title="所含用户">  
		        <table id="user_grid" data-options="fit:true,border:false"></table>
		    </div>  
		    <div title="角色权限">  
		        <table id="permission_grid" data-options="fit:true,border:false"></table>
		    </div>  
		</div>
	</div>
</div>
</body>
</html>