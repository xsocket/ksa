<!DOCTYPE html>
<html lang="zh">
<head>
<title>证件代办【${model.code!}】</title>
<script type="text/javascript">
	window.BODY_PADDING = 0; // 页面不需要留白
</script>
</head>
<body>
<div class="easyui-tabs" data-options="fit:true,border:false">  
	<div title="业务基本信息" data-options="href:'<@s.url value="/dialog/logistics/bookingnote/edit-zj.action?id=${model.id!}" />'">
	</div><#-- end tab-panel 业务基本信息 -->
<#-- 暂时关闭
	<div title="Manifest" data-options="href:'<@s.url value="/dialog/logistics/manifest/edit-zj.action?bookingNote.id=${model.id!}" />'">
	</div>
	<div title="到货通知单" data-options="href:'<@s.url value="/dialog/logistics/arrivalnote/edit-zj.action?bookingNote.id=${model.id!}" />'">
	</div>
-->
</div><#-- end tab -->
</body>
</html>