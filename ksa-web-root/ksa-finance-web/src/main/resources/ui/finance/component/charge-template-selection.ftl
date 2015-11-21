<!DOCTYPE html>
<html lang="zh">
<head><title>费用模板选择</title>
<style type="text/css">
	.charge1, .charge-1 { color: #000; }	
	.charge1 b { color: #BD362F; }
	.charge-1 b { color: #51A351; }	
</style>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	<#if direction == 1>var DIRECTION = 1;<#else>var DIRECTION = -1;</#if>
	<#if nature == 1>var NATURE = 1;<#else>var NATURE = -1;</#if>
	<#include "/ui/finance/charge-single/charge-single-condition.js" />
	<#include "/ui/finance/charge-single/charge-single-column.js" />
	<#include "charge-template-selection.js" />
</script>
<style type="text/css">
.tab-panel { padding:10px; }
.layout-split-east { border : none; }
</style>
</head>
<body>
<div id="component_container">
<div id="multiple_selection" data-options="fit:true"></div>
<div class="bottom-bar fixed-bar">
	<button id="btn_ok" class="btn btn-primary"><i class="icon-ok icon-white"></i> 确定</button>
	<button id="btn_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 取消</button>
</div>
</div>
</body>
</html>