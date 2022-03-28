<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html> 
<body> 
<script type="text/javascript"> 
<%
if(request.getAttribute("msg") != null){
%>
var message = "<%=request.getAttribute("msg").toString()%>"; 
alert(message); 
<%}%>

var returnUrl = "<%=request.getAttribute("url").toString()%>"; 
var parameter = "<%=request.getAttribute("category").toString()%>";

parameter = encodeURIComponent(parameter);

document.location.href = returnUrl+parameter;
</script></body></html> 