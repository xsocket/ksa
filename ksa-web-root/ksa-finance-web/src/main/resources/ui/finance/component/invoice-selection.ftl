<#--
	发票选择界面
	装饰页面：WEB-INF/decorators/component.jsp
 -->
<!DOCTYPE html>
<html lang="zh">
<head><title>发票选择</title>
<link rel="stylesheet" type="text/css" href="/wro/ksa-component-compositequery.css" />
<script  type="text/javascript" src="/wro/ksa-component-compositequery.js"></script>
<script type="text/javascript">
	<#if direction??>var DIRECTION = ${direction?c};<#else>var DIRECTION = null;</#if>
	<#if nature??>var NATURE = ${nature?c};<#else>var NATURE = null;</#if>
	<#if settle??>var SETTLE = "${settle?string("true", "false")}";<#else>var SETTLE = null;</#if>
	<#include "/ui/finance/invoice/invoice-query-condition.js" />
	<#include "/ui/finance/invoice/invoice-table-column.js" />
	<#include "invoice-selection.js" />
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