<!DOCTYPE html>
<html lang="zh">
<head><title>新建用户</title>
<style type="text/css">
div.locked-controls { padding:4px; }
div.locked-controls b { font-size:14px; margin-left:25px; }
div.locked-controls b input { margin:0; padding:0; }
</style>
<script type="text/javascript" src="<@s.url value="/wro/ksa-security-utils.js" />"></script>
<script type="text/javascript">
	<#include "user.js" />
</script>
</head>
<body>
<form id="dialog_container" method="post" class="easyui-form" action="<@s.url namespace="/dialog/security/user" action="insert" />">
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'east',split:true,title:'所属角色列表'" style="width:250px;">
		<table id="role_grid" data-options="fit:true,border:false"></table>
	</div>
	<div class="form-horizontal" data-options="region:'center'" style="padding:10px;">
		<legend>用户基本属性</legend>
		<@s.actionerror />
		<@s.actionmessage />
		<div class="control-group">
			<label class="control-label" for="id">用户标识：</label>
			<div class="controls">
		    	<input id="user_id" type="text" name="id" value="${user.id!""}"
		    		class="easyui-validatebox" data-options="required:true,validType:'length[1,36]'" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="id">登录密码：</label>
			<div class="controls">
		    	<input type="password" name="password" 
		    		class="easyui-validatebox" data-options="required:true,validType:'length[0,256]'" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="passwordAgain">密码确认：</label>
			<div class="controls">
		    	<input type="password" name="passwordAgain"
		    		class="easyui-validatebox" data-options="required:true,validType:'script[\'validatePassword()\', \'两次密码输入不一致\']'" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">用户姓名：</label>
			<div class="controls">
		    	<input type="text" name="name" value="${user.name!""}"
		    		class="easyui-validatebox" data-options="required:true,validType:'length[1,256]'" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="email">用户邮箱：</label>
			<div class="controls">
		    	<input type="text" name="email" value="${user.email!""}"
		    		class="easyui-validatebox" data-options="validType:'email'" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="telephone">联系电话：</label>
			<div class="controls">
		    	<input type="text" name="telephone" value="${user.telephone!""}"
		    		class="easyui-validatebox" data-options="validType:'length[0,256]'" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="locked">用户状态：</label>
			<div class="controls locked-controls">
		    	<b>激活：<input type="radio" name="locked" value="false" 
		    						<#if user.locked?? && user.locked == false>checked="checked"</#if>/></b> 
		    	<b>锁定：<input type="radio" name="locked" value="true" 
		    						<#if user.locked?? && user.locked == true>checked="checked"</#if>/></b> 
			</div>
		</div>
		<#-- end : control-group -->
		
	</div><#-- end : tab-panel 基本属性 -->
</div>
<div class="bottom-bar fixed-bar">
	<button type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>