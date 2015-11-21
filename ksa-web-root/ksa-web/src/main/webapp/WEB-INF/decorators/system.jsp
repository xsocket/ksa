<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/shiro-tags" prefix="shiro" %><%-- 自定义扩展后的 Shiro 标签 --%>
<%@ taglib uri="/struts-tags" prefix="s"%><!DOCTYPE html>
<html lang="zh">
<head>
<title><sitemesh:write property="title" /></title>
<sitemesh:write property="head"/>
</head>
<body>
<div id="_sidebar" class="span-sidebar">
	<div class="well sidebar">
		<ul class="nav nav-list">
			<shiro:hasAnyPermissions name="security:user,security:role">
			<li class="nav-header">系统安全配置</li>
			<shiro:hasPermission name="security:user">
			<li id="_sidebar_system_user"><a href="<s:url namespace="/ui/security/user" action="default" />"> 用户管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="security:role">
			<li id="_sidebar_system_role"><a href="<s:url namespace="/ui/security/role" action="default" />"> 角色管理</a></li>
			</shiro:hasPermission>
			</shiro:hasAnyPermissions>
		</ul>
	</div><!--/.well -->
</div><!--/span2-->

<div id="_mainpage" class="span-mainpage">
	<sitemesh:write property="body" />
</div>
<script type="text/javascript">
	ksa.menu.activate("#_menu_system");
</script>
</body>
</html>