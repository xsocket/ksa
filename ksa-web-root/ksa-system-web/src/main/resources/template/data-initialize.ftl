<#-- 初始化迁移数据 -->
<!DOCTYPE html>
<html lang="zh">
<head>
<title>数据迁移</title>
<style type="text/css">
	.control-group input[type='text'], .control-group textarea {
		font-weight:normal;
	}
</style>
<script type="text/javascript">
var initialized = ${stack.findValue('@com.ksa.context.web.RuntimeConfiguration@getConfiguration().getString("initialized", "false")')!false};
$(function(){
	if( initialized ) {
		$("#btn_init").hide();
	} else {
		$("span.alert").hide();
	}
	$("#btn_test").click(function(){
		$.ajax({
            url: ksa.buildUrl( "/ui/system/initialize", "check" ),
            data: $("form").serialize(),
            success: function( result ) {
                try {
                    if (result.status == "success") { 
                        $.messager.success( result.message );
                    } 
                    else { $.messager.alert( result.message, "数据库连接失败" ); }
                } catch (e) { }
            }
        }); 
        return false;
	});
	$("#btn_init").click(function(){
		$("#btn_init").addClass("loading").attr("disabled", true);
        $("#btn_init span").text("数据迁移中，请稍后...");
        $.ajax({
            url: ksa.buildUrl( "/ui/system/initialize", "init" ),
            data: $("form").serialize(),
            success: function( result ) {
                try {
                    if (result.status == "success") { 
                        $("#btn_init").hide();
						$("span.alert").show();
                        window.location.reload();
                    } 
                    else { $.messager.alert( result.message, "数据迁移失败" ); }
                } catch (e) { }
                $("#btn_init").removeClass("loading").attr("disabled", false);
       			$("#btn_init span").text("执行迁移");       			
            }
        }); 
        return false;
	});
});
</script>
</head>
<body>
<h3>数据迁移</h3>
<form class="form-horizontal">
<fieldset>
	<legend>原版本 SQL Server 2005 数据库连接设置</legend>
	<div class="control-group">
	    <label class="control-label" for="server">服务器地址：</label>
	    <div class="controls">
	      <input type="text" name="server" placeholder="数据库服务器 IP 地址" 
	      		value="${stack.findValue('@com.ksa.context.web.RuntimeConfiguration@getConfiguration().getString("sqlserver-server", "localhost")')}">
	    </div>
	</div>
	<div class="control-group">
		<label class="control-label" for="port">服务器端口：</label>
		<div class="controls">
		  <input type="text" name="port" placeholder="服务器连接端口 - 默认为1433" 
		  		value="${stack.findValue('@com.ksa.context.web.RuntimeConfiguration@getConfiguration().getString("sqlserver-port", "1433")')}">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="dbName">数据库名称：</label>
		<div class="controls">
		  <input type="text" name="dbName" placeholder="数据库名称 - 默认为 HuoYun" 
		  		value="${stack.findValue('@com.ksa.context.web.RuntimeConfiguration@getConfiguration().getString("sqlserver-dbName", "HuoYun")')}">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="username">登录用户名：</label>
		<div class="controls">
		  <input type="text" name="username" placeholder="数据库登录用户名"
		  		value="${stack.findValue('@com.ksa.context.web.RuntimeConfiguration@getConfiguration().getString("sqlserver-username", "ksa-admin")')}">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="password">登录密码：</label>
		<div class="controls">
			<input type="password" name="password" placeholder="数据库登录密码">
		</div>
	</div>
	<div class="control-group">
		<div class="control-label" style="padding-top:0;">
			<button id="btn_test" class="btn btn-success"><i class="icon-white icon-resize-small"></i> 测试连接</button>	
		</div>
		<div class="controls">
			<button id="btn_init" class="btn btn-primary" style="margin-left:80px"><i class="icon-white icon-random"></i> 
				<span>执行迁移</span></button>
			<span class="alert  alert-success" style="margin-left:80px;padding-right:14px;"><strong>数据迁移已执行完毕</strong></span>
		</div>
	</div>
</fieldset>
</form>
</body>
</html>