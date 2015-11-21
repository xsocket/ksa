<!DOCTYPE html>
<html lang="zh">
<head>
<title>海运出口【${model.code!}】</title>
<script type="text/javascript">
	window.BODY_PADDING = 0; // 页面不需要留白
</script>
</head>
<body>
<div class="easyui-tabs" data-options="fit:true,border:false">  
	<div title="业务基本信息" data-options="href:'<@s.url value="/dialog/logistics/bookingnote/edit-se.action?id=${model.id!}" />'">  
	</div><#-- end tab-panel 业务基本信息 -->
	<div title="提单确认通知书" data-options="href:'<@s.url value="/dialog/logistics/billoflading/edit-se.action?bookingNote.id=${model.id!}" />'">  
	</div><#-- end tab-panel 提单确认通知书 -->
	<div title="进仓通知单" data-options="href:'<@s.url value="/dialog/logistics/warehousenoting/edit-se.action?bookingNote.id=${model.id!}" />'">  
	</div><#-- end tab-panel 进仓通知单 -->
	<div title="订舱委托单" data-options="href:'<@s.url value="/dialog/logistics/warehousebooking/edit-se.action?bookingNote.id=${model.id!}" />'">  
	</div><#-- end tab-panel 订舱委托单 -->
</div><#-- end tab -->
</body>
</html>