<!DOCTYPE html>
<html lang="zh">
<head>
<title>海运出口【${model.code!}】</title>
<style type="text/css">
	<#include "../bookingnote.css" />
</style>
<script type="text/javascript">
	var IS_SEA = true;
	 <#include "../bookingnote.js"/>
</script>
</head>
<body>
<form method="POST" class="easyui-form" action="<@s.url namespace="/dialog/logistics/bookingnote" action="update-se" />" style="padding-bottom:20px;">
	<#include "bookingnote-detail.ftl" />
</form>
</body>
</html>