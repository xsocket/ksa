<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/shiro-tags" prefix="shiro" %><%-- 自定义扩展后的 Shiro 标签 --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title><sitemesh:write property="title" /></title>
<sitemesh:write property="head"/>
</head>
<body>
<div id="_sidebar" class="span-sidebar">
	<div class="well sidebar">
		<ul class="nav nav-list">
			<shiro:hasPermission name="bd:partner">
			<li id="_sidebar_bd_partner"><a href="<s:url namespace="/ui/bd/partner" action="default" />"> 合作伙伴管理</a></li>
			<li class="divider"></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="bd:data">
			<li id="_sidebar_bd_data"><a href="<s:url namespace="/ui/bd/data" action="default" />"> 基本代码管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="bd:currency">
			<li id="_sidebar_bd_currency"><a href="<s:url namespace="/ui/bd/currency" action="default" />"> 货币汇率管理</a>
			</shiro:hasPermission>
		</ul>
	</div><!--/.well -->
</div><!--/span2-->

<div id="_mainpage" class="span-mainpage">
	<sitemesh:write property="body" />
</div>
<script type="text/javascript">
	ksa.menu.activate("#_menu_bd");
</script>
</body>
</html>