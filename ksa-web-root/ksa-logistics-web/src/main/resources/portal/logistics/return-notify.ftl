<!DOCTYPE html>
<html lang="zh">
<head>
<title>退单提醒</title>
<script type="text/javascript">
	<#include "/ui/logistics/return-notify-table-column.js" />
	<#include "return-notify.js" />
</script>
</head>
<body>
<div class="top-bar fixed-bar" style="padding:1px 2px;">
	<@shiro.hasPermission name="bookingnote:edit">
	<button id="btn_edit" class="btn btn-mini btn-warning"><i class="icon-white icon-edit"></i> 退单 ...</button>
	</@shiro.hasPermission>
	<@shiro.lacksPermission name="bookingnote:edit">
		<@shiro.hasPermission name="bookingnote:edit:view">
		<button id="btn_edit" class="btn btn-mini btn-warning"><i class="icon-white icon-edit"></i> 查看 ...</button>
		</@shiro.hasPermission>
	</@shiro.lacksPermission>
</div>
<div id="portal_container">
	<table id="data_grid"></table>
</div>
</body>
</html>