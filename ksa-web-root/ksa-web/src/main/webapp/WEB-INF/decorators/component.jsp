<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<jsp:include page="../include/main-head.jsp" />
<script type="text/javascript" src="<s:url value="/wro/ksa-component.js" />"></script>
<sitemesh:write property="head"/>
</head>
<body>
<sitemesh:write property="body" />
</body>
<script type="text/javascript">
var bodyPadding = 5;
$("body").css("padding", bodyPadding);
var fixBottom = $(".bottom-bar.fixed-bar").outerHeight();
var fixTop = $(".top-bar.fixed-bar").outerHeight();
$("#component_container")._outerHeight( $(window).height() - fixTop - fixBottom - bodyPadding * 2 );
$("#component_container").css("padding-top", fixTop );
$("#btn_close").click(function(){parent.$.close();return false;});
</script>
</html>