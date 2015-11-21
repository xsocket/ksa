<!DOCTYPE html>
<html lang="zh">
<head>
<title>货物及箱量统计</title>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<style type="text/css">
	div.title { color:#04C; text-align:left; line-height:25px; }
	div.title b { color:#000; }
</style>
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	<#include "cargo-table-column.js" />
	<#include "/ui/logistics/bookingnote-query-condition.js" />
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">货物及箱量统计</h4>
	<div id="title" class="title"></div>
	<button id="btn_edit" class="btn btn-info"><i class="icon-white icon-zoom-in"></i> 查看 (E)</button>
</div>
<div id="data_container">
	<div data-options="region:'center'" style="overflow:hidden">
		<table id="data_grid"></table>
	</div>
	<div data-options="region:'east',title:'业务查询'" style="width:230px;">
		<div id="query" data-options="fit:true, border:false"></div>
	</div>
</div>
</body>
</html>