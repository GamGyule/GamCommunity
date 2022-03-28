<%@page import="com.gamgyul.gams.util.GamgyulDataSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<!DOCTYPE html>
<html>
<%
	if(session.getAttribute("customer")!= null){
		%>
			<script>history.go(-1);</script>
		<%
	}
%>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<script type="text/javascript" src="js/hello.js"></script>
<script type="text/javascript" src="js/google_oauth.js"></script>
<meta name="google-signin-client_id" content="397205866796-hant6ua349hu2cok1n835rgo2h6ra7fa.apps.googleusercontent.com.apps.googleusercontent.com">
<link rel="stylesheet" type="text/css" href="css/index.css?version=<%=System.currentTimeMillis()%>">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+HK|Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="js/login.js?version=<%=System.currentTimeMillis()%>"></script>
<meta charset="UTF-8">
<title>감귤</title>
</head>
<body>
  <%
    String clientId = "96fum_n52OUBuW6IDWP2";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode(GamgyulDataSet.getIp()+"naver/callback", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 %>
 
 <%
 	String gurl ="https://accounts.google.com/o/oauth2/v2/auth?scope=email profile&access_type=offline&include_granted_scopes=true&state=state_parameter_passthrough_value&"+
 		"redirect_uri="+GamgyulDataSet.getIp()+"google/callback&response_type=code&client_id="+GamgyulDataSet.getClientId();
	 
 
 
 %>
	<div class="login_box_parent">
		<div class="center_position">
			<div class="login_form_box">
				<div class="login_form_box_header">
					<h2><a href="<%=request.getContextPath()%>/">감 귤</a></h2>
				</div>
				<div class="login_form_area">
					<input class="google_login_btn" type="button" onclick="location.href='<%=gurl%>'" value="구글 아이디로 로그인"><br><br>
					<input class="naver_login_btn" type="button" onclick="location.href='<%=apiURL%>'" value="네이버 아이디로 로그인">
					<span>현재 네이버로그인은 아이디를 등록해야만 로그인이 됩니다.</span>
					<div class="login_box_other_link">
						<div>
							<a href="./">메인화면</a>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>