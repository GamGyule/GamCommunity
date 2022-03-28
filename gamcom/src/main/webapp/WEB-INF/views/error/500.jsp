<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

	<title>감귤 - 내부 서버 오류</title>
	<!-- Custom stlylesheet -->
	<link href="https://fonts.googleapis.com/css?family=Noto+Sans+HK|Noto+Sans+KR&display=swap" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/error/404/style.css?t=<%=System.currentTimeMillis()%>" />

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->

</head>

<body>

	<div id="notfound">
		<div class="notfound">
			<div class="notfound-404">
				<h1>500</h1>
			</div>
			<h2>현재 서버에 연결할 수 없습니다.</h2>
			<p>시스템 에러가 발생하여 페이지를 표시할 수 없습니다. 잠시 후 다시 시도하세요<br><a href="<%=request.getContextPath() %>/">홈페이지로 이동하기</a></p>
		</div>
	</div>

</body><!-- This templates was made by Colorlib (https://colorlib.com) -->

</html>
