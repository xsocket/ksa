<!DOCTYPE html>
<html lang="zh">
<head>
<title>内航线【${model.code!}】</title>
<style type="text/css">
	<#include "../bookingnote.css" />
</style>
<script type="text/javascript">
	var IS_SEA = true;
	 <#include "../bookingnote.js"/>
</script>
</head>
<body>
<form method="POST" class="easyui-form" action="<@s.url namespace="/dialog/logistics/bookingnote" action="update-rh" />" style="padding-bottom:20px;">
	<#include "bookingnote-detail.ftl" />
</form>
</body>
</html>