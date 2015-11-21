<!DOCTYPE html>
<html lang="zh">
<head><title>每月货币汇率</title>
<style type="text/css">
	.toolbar input { margin-bottom : 0; }
	html, body { overflow : hidden; }
</style>
<script type="text/javascript">
	<#include "default.js" />
</script>
</head>
<body>
<form method="POST">
<div class="top-bar toolbar">
	<h4 class="title">客户汇率列表 <#if model.partner.name??><span class="label label-info">${model.partner.name!}</span></#if></h4>
	选择客户：
	<input id="partner" type="text" name="partner.id" value="${model.partner.id!}" data-options="validType:'length[0,200]'" />
	<button class="btn btn-success"><i class="icon-white icon-search"></i> 查询 (Q)</button>
	<button id="save" class="btn btn-primary" style="margin-left:50px;"><i class="icon-white icon-ok"></i> 保存 (S)</button>
</div>
<table id="data-grid"></table>
</form>
</body>
</html>