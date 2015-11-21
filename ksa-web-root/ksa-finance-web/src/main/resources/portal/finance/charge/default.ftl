<!DOCTYPE html>
<html lang="zh">
<head>
<title>业务费用列表</title>
<#assign securityUtilsClass = "@com.ksa.service.security.util.SecurityUtils" />
<#assign currentUser = stack.findValue("${securityUtilsClass}@getCurrentUser()") />
<style type="text/css">
	.charge1, .charge-1 { color: #000; }	
	.charge1 b { color: #BD362F; }
	.charge-1 b { color: #51A351; }	
</style>
<script type="text/javascript">
	var CURRENT_USER_ID = "${currentUser.id!}";
	<#include "/ui/finance/profit/profit-table-column.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div id="portal_container">
	<table id="data_grid"></table>
</div>
</body>
</html>