<!DOCTYPE html>
<html lang="zh">
<head>
<title>合作伙伴信息管理</title>
<script type="text/javascript">
	<#include "default.js" />
</script>
<style type="text/css">
	#txt_search{
		-webkit-border-radius: 0;
		-moz-border-radius: 0;
		border-radius: 0;
		padding-left:4px;
	}
</style>
</head>
<body>
<div class="top-bar toolbar">
	<h4 class="title">合作伙伴信息列表</h4>
	<div class="form-search pull-left" style="margin-left:20px;">
		<div class="input-prepend" style="margin-right:-5px;">
			<button id="btn_clear" type="submit" class="btn" title="清空搜索条件">&times;</button></div>
		<div class="input-append">
			<input id="txt_search" type="text" class="search-query" style="height:16px;"/>
			<button id="btn_search" class="btn btn-success"><i class="icon-white icon-search"></i> 快速查找</button>
	  </div>
	</div>
	<button id="btn-add" class="btn btn-primary"><i class="icon-white icon-plus"></i> 新建... (N)</button>
	<button id="btn-edit" class="btn btn-warning"><i class="icon-white icon-edit"></i> 编辑... (E)</button>
	<button id="btn-delete" class="btn btn-danger"><i class="icon-white icon-trash"></i> 冻结 (D)</button>
</div>
<table id="data-grid"></table>
</body>
</html>