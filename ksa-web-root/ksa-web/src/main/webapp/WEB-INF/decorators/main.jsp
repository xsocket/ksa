<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<jsp:include page="../include/main-head.jsp" />
<style>
	.row-fluid .span-sidebar { width:14%; }
	.row-fluid .span-mainpage { margin-left:1%; width:85%; }
</style>
<sitemesh:write property="head"/>
</head>
<body>
<jsp:include page="../include/main-nav.jsp" />
<div id="_container"  class="easyui-panel container-fluid" 
	data-options="fit:true,border:false" style="padding-top:45px;">
<div class="row-fluid">
<sitemesh:write property="body" />
</div>
</div>
</body>
</html>