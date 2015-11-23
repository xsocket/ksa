<!DOCTYPE html>
<html lang="zh">
<head>
<title>内航线【${model.code!}】</title>
<style type="text/css">
	<#include "../bookingnote.css" />
</style>
<script type="text/javascript">
	window.BODY_PADDING = 0; // 页面不需要留白
	var IS_SEA = true;
	<#include "/js/logistics/utils.js"/>
	 <#include "../bookingnote.js"/>
	 <#include "../copy-template.js"/>
</script>
</head>
<body>
<div class="easyui-tabs" data-options="fit:true,border:false">  
<div title="业务基本信息" style="padding-bottom:20px;">
<form method="POST" class="easyui-form" action="<@s.url namespace="/dialog/logistics/bookingnote" action="insert-rh" />">
	<#include "bookingnote-detail.ftl" />
</form><#-- end form -->
</div><#-- end tab-panel 业务基本信息 -->
</div><#-- end tab -->
</body>
</html>