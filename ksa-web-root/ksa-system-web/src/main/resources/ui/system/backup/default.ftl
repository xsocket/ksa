<!DOCTYPE html>
<html lang="zh">
<head>
<title>系统数据备份/还原</title>
<style type="text/css">
</style>
<script type="text/javascript">
	<#include "default.js" />
</script>
</head>
<body>
<div class="top-bar tool-bar">
	<h4 class="title" style="margin-right:40px;">数据备份信息</h4>
	<button id="btn_strategy" class="btn btn-warning"><i class="icon-white icon-wrench"></i> <span>备份策略设置</span></button>
	<button id="btn_backup" class="btn btn-primary"><i class="icon-white icon-inbox"></i> <span>数据备份</span></button>
	<#-- 
	<button id="btn_restore" class="btn btn-danger"><i class="icon-white icon-repeat"></i> <span>数据还原</span></button>
	-->
</div>
<div id="data_container">
	<table id="data_grid"></table>
</div>
</body>
</html>