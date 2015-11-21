<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<title><sitemesh:write property="title" /> - 杭州凯思爱物流管理系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />	<!-- 设定 IE Document Mode 为最新可用模式 -->
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="杭州凯思爱物流管理系统">
<meta name="author" content="麻文强 - mawenqiang1985@gmail.com">
<script type="text/javascript">
<%-- 定义框架的初始化 js 定义 --%>
var APPLICATION_CONTEXTPATH = "<%= org.apache.struts2.ServletActionContext.getServletContext().getContextPath() %>";
</script>
<link rel="stylesheet" type="text/css" href="<s:url value="/wro/ksa-global-default.css" />" media="screen" />
<script type="text/javascript" src="<s:url value="/wro/ksa-global-default.js" />"></script>
<%-- Le HTML5 shim, for IE6-8 support of HTML5 elements。 --%>
<!--[if lt IE 9]>
<script type="text/javascript" src="<s:url value="/wro/html5.js" includeParams="none"/>"></script>
<![endif]-->