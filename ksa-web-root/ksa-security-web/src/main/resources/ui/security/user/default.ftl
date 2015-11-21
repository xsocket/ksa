<!DOCTYPE html>
<html lang="zh">
<head>
<title>用户管理</title>
<script type="text/javascript" src="<@s.url value="/wro/ksa-security-utils.js" />"></script>
<script type="text/javascript">
	<#include "user-action.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar toolbar">
	<h4 class="title">用户列表</h4>
	<button id="btn_add" class="btn btn-primary"><i class="icon-white icon-plus"></i> 新建... (N)</button>
	<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 编辑... (E)</button>
	<#-- 用户只能锁定，不能删除
	<button id="btn_delete" class="btn btn-danger"><i class="icon-white icon-trash"></i> 删除 (D)</button>
	 -->
</div>
<div id="data_container">
	<div data-options="region:'center'">
		<table id="user_grid" data-options="fit:true,border:false"></table>
	</div>
	<div data-options="region:'east',split:true,title:'所属角色列表'" style="width:250px;">
		<table id="role_grid" data-options="fit:true,border:false"></table>
	</div>
</div>
</body>
</html>