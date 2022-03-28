<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="com.gamgyul.gams.dto.*" %>
<!DOCTYPE html>
<html>
<head>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/index.css?version=<%=System.currentTimeMillis()%>">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+HK|Noto+Sans+KR&display=swap" rel="stylesheet">
</head>
<%
CustomerDTO customer = null;
	try{
		customer = (CustomerDTO)session.getAttribute("customer");
	}catch(Exception e){
		%>
		<script>alert("고객 정보 없음")</script>
		<%
		customer = null;
	}
%>
<body>
	<div class="header_main">
		<div class="header_wrap">
			<div class="container">
				<div class="header">
					<div class="header_box">
						<a class="header_logo_a" href="<%=request.getContextPath() %>/"><img class="global_header_logo" src="<%=request.getContextPath() %>/images/globalheader_logo.png"></a>
						<div class="login_box">
							<%
								if(customer==null){
									%>
										<input class="login_btn" type="button" onclick="location.href='<%=request.getContextPath() %>/login';" value="로그인">
									<%
								}else{
									%>
										<a href="javascript:;"><%=customer.getCustomer_name() %></a><input class="login_btn" type="button" onclick="location.href='<%=request.getContextPath() %>/logout';" value="로그아웃">
									<%
								}
							%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="header_text container">
			<div>
				<h1><a style="color:white" href="<%=request.getContextPath() %>/">감귤</a></h1>
			</div>
		</div>
		<div class="header_nav">
			<div class="container">
			<%
				String category = "";
				if(request.getParameter("category") != null)
					category = request.getParameter("category");
			%>
					<ul>
						<li><a <%=(category.equals("게시판1")?"class='active'":"") %> href="<%=request.getContextPath() %>/board/list?category=<%=URLEncoder.encode("게시판1","UTF-8") %>" class="nav_menu_btn">게시판1</a></li>
						<li><a <%=(category.equals("게시판2")?"class='active'":"") %> href="<%=request.getContextPath() %>/board/list?category=<%=URLEncoder.encode("게시판2","UTF-8") %>" class="nav_menu_btn">게시판2</a></li>						
					</ul>
			<%
				
			%>
			</div>
		</div>
	</div>


</body>
</html>