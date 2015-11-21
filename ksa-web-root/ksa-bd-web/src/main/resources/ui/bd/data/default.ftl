<!DOCTYPE html>
<html lang="zh">
<head>
<title>基本代码管理</title>
<style type="text/css">
#type_list .label { margin-right:10px; display: inline-block; width: 20px; text-align: center; }
#txt_search{-webkit-border-radius: 0;-moz-border-radius: 0;border-radius: 0;padding-left:4px;}
</style>
<script type="text/javascript">
	var TYPE_ID = "${data.type.id!}";
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar toolbar">
	<h4 class="title"><span id="type_name"></span> <span class="label label-info">基本代码</span></h4>
	<div class="form-search pull-left" style="margin-left:20px;">
		<div class="input-prepend" style="margin-right:-5px;">
			<button id="btn_clear" type="submit" class="btn" title="清空搜索条件">&times;</button></div>
		<div class="input-append">
			<input id="txt_search" type="text" class="search-query" style="height:16px;"/>
			<button id="btn_search" class="btn btn-success"><i class="icon-white icon-search"></i> 快速查找</button>
	  </div>
	</div>
	<button id="btn-add" class="btn btn-primary"><i class="icon-white icon-plus"></i> 新建 (N)</button>
	<button id="btn-edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 编辑 (E)</button>
	<button id="btn-delete" class="btn btn-danger"><i class="icon-white icon-trash"></i> 删除 (D)</button>
</div>

<div class="well" style="float:right;width:150px;margin-left:5px;padding: 10px 0; background-color:#FFF;">
  <ul id="type_list" class="nav nav-list">
	<#list allTypes as t>
		<li id="${t.id}"><a href="#"><span class="label label-info">${t_index + 1}</span><span class="type-name">${t.name}<span></a></li>
	</#list>
	</ul>
</div>
<table id="data-grid" style="float:left;"></table>
</body>
</html>