<!DOCTYPE html>
<html lang="zh">
<head>
<title>托单管理</title>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	<#include "bookingnote-query-condition.js" />
	<#include "bookingnote-table-column.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">托单列表</h4>
	<@shiro.hasPermission name="bookingnote:delete">
	<div id="btn_modify_type" class="btn-group" style="text-align:left; display:inline-block; top:-3px; margin-right:5px;">
          <a class="btn btn-warning btn-small dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="icon-white icon-edit"></i>  变更业务类型... <span class="caret"></span></a>
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
            	<li><a name="zj" href="javascript:void(0)">证件代办</a></li>
          </ul>
    </div>
    </@shiro.hasPermission>
	
	<button id="btn_download" class="btn btn-success"><i class="icon-white icon-download-alt"></i> 导出托单</button>
	<@shiro.hasPermission name="bookingnote:edit">
	<div id="btn_add" class="btn-group" style="text-align:left; display:inline-block; top:-3px; margin-right:5px;">
		  <a class="btn btn-primary btn-small dropdown-toggle" data-toggle="dropdown" href="#">
		  		<i class="icon-white icon-plus"></i>  新建... <span class="caret"></span></a>
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
            	<li><a name="zj" href="javascript:void(0)">证件代办</a></li>
		  </ul>
	</div>
	<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 编辑... (E)</button>
	</@shiro.hasPermission>
	<@shiro.lacksPermission name="bookingnote:edit">
		<@shiro.hasPermission name="bookingnote:edit:view">
		<button id="btn_edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 查看... (E)</button>
		</@shiro.hasPermission>
	</@shiro.lacksPermission>
	
	<@shiro.hasPermission name="bookingnote:delete">
	<button id="btn_delete" class="btn btn-danger"><i class="icon-white icon-trash"></i> 删除 (D)</button>
	</@shiro.hasPermission>
</div>
<div id="data_container">
	<div data-options="region:'center'" style="overflow:hidden">
		<table id="data_grid"></table>
	</div>
	<div data-options="region:'east',title:'托单查询'" style="width:230px;">
		<div id="query" data-options="fit:true, border:false"></div>
	</div>
</div>
</body>
</html>