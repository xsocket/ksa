<#-- 合作伙伴抬头信息选择页面 附加编辑的功能 -->
<!DOCTYPE html>
<html lang="zh">
<head><title>单位抬头选择</title>
<script type="text/javascript">
	var PARTNER_NAME = "${partner.name!}";
	<#include "partner-alias-selection.js" />
</script>
</head>
<body>
<div id="component_container">
<div class="top-bar fixed-bar form-inline">
	<div class="control-group" style="margin-bottom:0;text-align: left;">
		<label class="control-label">选择单位：</label>
		<div class="controls">
	    	<input id="partner_id" type="text" name="id" value="${partner.id!""}" data-options="validType:'length[0,200]'" style="width:400px;"/>
		</div>
	</div>
</div>
<table id="extra_grid"></table>
<div id="extra_window" class="easyui-window" data-options="closed:true,modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,title:'新建抬头'">
	<textarea id="extra" rows="7" style="width:400px; margin: 10px; resize:none"></textarea>
	<div class="bottom-bar">
		<button id="btn_extra_ok" class="btn btn-primary"><i class="icon-ok icon-white"></i> 确定</button>
		<button id="btn_extra_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 取消</button>
	</div>
</div>
<div class="bottom-bar fixed-bar">
	<button id="btn_ok" class="btn btn-primary"><i class="icon-ok icon-white"></i> 确定</button>
	<button id="btn_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 取消</button>
</div>
</div>
</body>
</html>