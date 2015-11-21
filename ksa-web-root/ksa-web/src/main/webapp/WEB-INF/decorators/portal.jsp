<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<jsp:include page="../include/main-head.jsp" />
<sitemesh:write property="head"/>
</head>
<body>
<sitemesh:write property="body" />
</body>
<script type="text/javascript">
if( window.BODY_PADDING == null ) {
    window.BODY_PADDING = 0;
}
$("body").css("padding", BODY_PADDING);
var fixBottom = $(".bottom-bar.fixed-bar").outerHeight();
var fixTop = $(".top-bar.fixed-bar").outerHeight();
$("#portal_container")._outerHeight( $(window).height() - fixTop - fixBottom - BODY_PADDING * 2 );
$("#portal_container").css("padding-top", fixTop );
</script>
</html>