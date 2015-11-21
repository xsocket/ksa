<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>系统登录 - 杭州凯思爱物流管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="杭州凯思爱物流管理系统">
<meta name="author" content="麻文强 - mawenqiang1985@gmail.com">
<link rel="stylesheet" type="text/css" href="rs/bootstrap/css/bootstrap.css" />
<style type="text/css">
form#login {
	position: relative; margin: 0 auto;
	background: url("rs/images/login-bg.jpg") no-repeat;
	width: 948px; height: 570px;
}
div.login-panel {
	position: relative; text-align: left;
	width: 330px; 
	top: 220px; left: 350px;
	background-color: #fcfcfc;
	border: 1px solid #e3e3e3;
	-webkit-border-radius: 4px;
	   -moz-border-radius: 4px;
	        	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
	   -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
	        box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
}
div.login-panel div.alert { margin: 0; }
div.login-panel label.control-label { width: 60px; }
div.login-panel div.controls {margin-left: 70px;}
div.login-panel div.title-bar {
	background-color: #f4f9fd;
	font-size: 18px; font-weight:bold;
	padding: 10px 30px;
	margin-top: 0;
	margin-bottom:20px;
	border-bottom: 1px solid #999;
	-webkit-box-shadow: inset 0 -1px 0 #ffffff;
	   -moz-box-shadow: inset 0 -1px 0 #ffffff;
                box-shadow: inset 0 -1px 0 #ffffff;
}
fieldset form {margin-bottom:0px;}
</style>
</head>
<body>
	<form id="login" method="post">
		<div class="login-panel form-horizontal">
			<div class="title-bar">用户登录</div>
			<div class="control-group">
				<label class="control-label" for="username">账号：</label>
				<div class="controls">
					<input type="text" id="username" placeholder="用户账号" name="username" autofocus="autofocus" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="password">密码：</label>
				<div class="controls">
					<input type="password" id="password" placeholder="登录密码" name="password" />
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="checkbox" style="float:left;margin-right:100px;">
						<input type="checkbox" value="true" name="rememberMe">记住我</label>
					<button style="float:left;" type="submit" class="btn btn-primary"> 登 录 </button>
				</div>
			</div>
<%	/* 展示登录失败信息 */
	Object obj = request.getAttribute(org.apache.shiro.web.filter.authc. FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
    if( obj != null ) {
        if( "org.apache.shiro.authc.UnknownAccountException".equals( obj ) ) {
            out.print( "<div class='alert alert-error'><b>登录失败：</b> 用户账号或密码输入错误！</div>" );	/*账号错误*/
        }
        else if( "org.apache.shiro.authc.IncorrectCredentialsException".equals( obj ) ) { 
            out.print( "<div class='alert alert-error'><b>登录失败：</b> 用户账号或密码输入错误！</div>" );	/*密码错*/ 
        } else if( "org.apache.shiro.authc.LockedAccountException".equals( obj ) ) { 
            out.print( "<div class='alert alert-error'><b>登录失败：</b> 用户账号已被锁定！</div>" );	/*密码错*/ 
        }
        else { 
            out.print( "<div class='alert alert-error'><b>登录失败：</b> 身份认证失败！</div>" ); 
        }
    } 
%>
		</div>
	</form>
</body>
</html>