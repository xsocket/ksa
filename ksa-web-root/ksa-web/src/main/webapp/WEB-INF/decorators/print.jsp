<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/shiro-tags" prefix="shiro" %><%-- 自定义扩展后的 Shiro 标签 --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<jsp:include page="../include/main-head.jsp" />
<link rel="stylesheet" type="text/css" href="<s:url value="/wro/ksa-print.css" />"  media="print,screen"/>
<sitemesh:write property="head"/>
<style type="text/css" media="print">
	div.bottom-bar,div.fixed-bar { display:none; }
	textarea,input, input[type="text"] { border: none; background: none; line-height: 1; }
	span.combo { padding: 0 5pt; }
	/* utility components */
	.no-print { display:none; }
</style>
<style type="text/css" media="screen">
	body {padding:5px 5px 50px 5px;}
</style>
<!-- 选择替换单位抬头 -->
<script type="text/javascript" src="<s:url value="/wro/ksa-bd-utils.js" />"></script>
</head>
<body>
<sitemesh:write property="body" />

<%-- 没有打印权限 --%>
<shiro:lacksPermission name="bookingnote:print">
<script type="text/javascript">$(function(){$("#dialog_print").hide();});</script>
</shiro:lacksPermission>
<%-- 没有编辑权限 --%>
<shiro:lacksPermission name="bookingnote:edit">
<script type="text/javascript">$(function(){$("#dialog_save").hide();});</script>
</shiro:lacksPermission>
<script type="text/javascript">
$(function(){
    $("button").addClass("no-print");
	 // 打印
	 $("#dialog_print").click(function(e){
	     window.print();
	     return false;
	 });
	 $("#dialog_close").click(function(){top.$.close();return false;});
});
</script>
</body>
</html>