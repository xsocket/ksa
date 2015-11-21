<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title><sitemesh:write property="title" /> - 杭州凯思爱物流管理系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />	<!-- 设定 IE Document Mode 为最新可用模式 -->
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="杭州凯思爱物流管理系统">
<meta name="author" content="麻文强 - mawenqiang1985@gmail.com">
<style type="text/css">
	html, body, div.chart-container {margin:0;padding:0; overflow:hidden;}
	body { padding: 5px; }
	div.chart-container { text-align: center; }
</style>
<script type="text/javascript">
<%-- 定义框架的初始化 js 定义 --%>
var APPLICATION_CONTEXTPATH = "<%= org.apache.struts2.ServletActionContext.getServletContext().getContextPath() %>";
</script>
<script type="text/javascript" src="<s:url value="/wro/ksa-global-default.js" />"></script>
<script type="text/javascript" src="<s:url value="/wro/ksa-chart.js" />"></script>
</head>
<body>
<sitemesh:write property="body" />
</body>
</html>