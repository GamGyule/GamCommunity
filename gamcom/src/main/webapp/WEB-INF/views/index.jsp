<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta name="viewport" content="width=device-width,initial-scale=1">
<script src="js/index.js?t=<%=System.currentTimeMillis()%>"></script>
<meta charset="UTF-8">

<title>감귤 - 몰라</title>

</head>
<body>
	<div class="main-content">
	<jsp:include page="include/header.jsp?t=<%=System.currentTimeMillis()%>"></jsp:include>
		<div class="main_wrap">
			<div class="container">
				<div class="main_box">
					
					
					<div class="video_box">
						<div class="main_box_video">
							<iframe width="560" src="https://www.youtube.com/embed/m_o0MpnD7oQ" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
						</div>
						

						<div class="main_box_video_dec"><span>오버워치 2 시네마틱 트레일러 | “제로 아워”</span></div>
					</div>
					
					<div class="video_box">
						<div class="main_box_video">
							<iframe width="560" src="https://www.youtube.com/embed/XUuSxAC5tx8" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>	
						</div>
						<div class="main_box_video_dec"><span>디아블로 IV 공식 시네마틱 영상 | 세 명이 오리라</span></div>
					</div>
					
				</div>
			</div>
		</div>
	<jsp:include page="include/footer.jsp?t=<%=System.currentTimeMillis()%>"></jsp:include>
	</div>
	

</body>
</html>