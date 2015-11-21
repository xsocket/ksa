<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<% 
	org.apache.shiro.SecurityUtils.getSubject().logout();
	response.sendRedirect("index.jsp"); 
%>