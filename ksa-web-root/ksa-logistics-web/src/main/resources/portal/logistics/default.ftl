<!DOCTYPE html>
<html lang="zh">
<head>
<title>托单列表</title>
<#assign securityUtilsClass = "@com.ksa.service.security.util.SecurityUtils" />
<#assign currentUser = stack.findValue("${securityUtilsClass}@getCurrentUser()") />
<script type="text/javascript">
	var CURRENT_USER_ID = "${currentUser.id!}";
	<#include "/ui/logistics/bookingnote-table-column.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar fixed-bar" style="padding:1px 2px;text-align:left;">
	<@shiro.hasPermission name="bookingnote:edit">
	<div id="btn_add" class="pull-left btn-group" style="text-align:left; display:inline-block; margin:3px 5px 0 0">
		  <a class="btn btn-primary btn-mini dropdown-toggle" data-toggle="dropdown" href="#">
		  		<i class="icon-white icon-plus"></i>  新建 ... <span class="caret"></span></a>
		  <ul class="dropdown-menu">
                <li><a name="se" href="javascript:void(0)">海运出口</a></li>
		        <li><a name="si" href="javascript:void(0)">海运进口</a></li>
				<li class="divider"></li>
				<li><a name="ae" href="javascript:void(0)">空运出口</a></li>
				<li><a name="ai" href="javascript:void(0)">空运进口</a></li>
				<li class="divider"></li>
				<li><a name="ly" href="javascript:void(0)">国内运输</a></li>
				<li><a name="kb" href="javascript:void(0)">捆包业务</a></li>
				<li><a name="rh" href="javascript:void(0)">内航线</a></li>
				<li><a name="cc" href="javascript:void(0)">仓储业务</a></li>
				<li><a name="bc" href="javascript:void(0)">搬场业务</a></li>
				<li><a name="tl" href="javascript:void(0)">公铁联运</a></li>
				<li><a name="zj" href="javascript:void(0)">代办证件</a></li>
		  </ul>
	</div>
	<div class="datagrid-btn-separator"></div>
	<button id="btn_edit" class="pull-left btn btn-warning btn-mini" style="margin:3px 0 0 5px;"><i class="icon-white icon-edit"></i> 编辑托单 ... </button>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="finance:charge">
	<div id="btn_charge" class="pull-right btn-group" style="display:inline-block; margin:3px 5px 0 0">
		  <a class="btn btn-warning btn-mini dropdown-toggle" data-toggle="dropdown" href="#">
		  		<i class="icon-white icon-list-alt"></i>  费用输入 ... <span class="caret"></span></a>
		  <ul class="dropdown-menu">
                <li><a id="edit_income" href="javascript:void(0)">收入费用</a></li>
		        <li><a id="edit_expense" href="javascript:void(0)">支出费用</a></li>
				<li class="divider"></li>
				<li><a id="edit_foreign" href="javascript:void(0)">境外费用</a></li>
		  </ul>
	</div>
	</@shiro.hasPermission>
</div>
<div id="portal_container">
	<table id="data_grid"></table>
</div>
</body>
</html>