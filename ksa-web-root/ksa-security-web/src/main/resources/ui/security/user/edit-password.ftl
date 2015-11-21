<!DOCTYPE html>
<html lang="zh">
<head>
<#assign securityUtilsClass = "@com.ksa.service.security.util.SecurityUtils" />
<#assign currentUser = stack.findValue("${securityUtilsClass}@getCurrentUser()") />
<title>修改密码：${currentUser.name!""}</title>
<script type="text/javascript">
	<#include "user.js" />
</script>
</head>
<body>
<form id="dialog_container" method="POST" class="easyui-form form-horizontal" action="<@s.url namespace="/dialog/security/user" action="update-password" />">
	<input type="hidden" name="id" value="${currentUser.id!}" />
	<@s.actionmessage />
	<@s.actionerror />
	<div class="control-group" style="margin-top:20px;">
		<label class="control-label" for="id">原密码：</label>
		<div class="controls">
	    	<input type="password" name="passwordOld"
	    		class="easyui-validatebox" data-options="required:true,validType:'script[\'validateOldPassword()\', \'请输入原登录密码\']'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="id">新密码：</label>
		<div class="controls">
	    	<input type="password" name="password"
	    		class="easyui-validatebox" data-options="required:true,validType:'script[\'validateNewPassword()\', \'请输入新登录密码\']'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="passwordAgain">密码确认：</label>
		<div class="controls">
	    	<input type="password" name="passwordAgain"
	    		class="easyui-validatebox" data-options="required:true,validType:'script[\'validatePassword()\', \'两次密码输入不一致\']'" />
		</div>
	</div>
	<#-- end : control-group -->
<div class="bottom-bar fixed-bar">
	<button type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>