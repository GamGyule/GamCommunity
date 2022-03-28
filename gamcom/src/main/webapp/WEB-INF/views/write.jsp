<%@page import="java.util.Date,com.gamgyul.gams.dto.BoardDTO,java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("customer")== null){
		%>
			<script>history.go(-1);</script>
		<%
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script src="<%=request.getContextPath()%>/js/write.js?"></script>
<meta charset="UTF-8">
<title>감귤 - 몰라</title>
</head>
<body>
	<div class="main-content">
	<jsp:include page="include/header.jsp?t=<%=System.currentTimeMillis()%>"></jsp:include>
		<div class="main_wrap">
			<div class="container">
				<div class="main_box">
					<div class="board_header_box">
						<span class="board_header"><%=request.getParameter("category") %></span>
					</div>
					<div class="board_container">
					<form id="fm">
						<input type="hidden" name="category" value="<%=request.getParameter("category")%>">
						<div>
							<input id="subject" name="subject" type="text" placeholder="제목" autocomplete="off" style="width:100%;padding:10px;">
						</div>
						<div>
							<textarea rows="5" id="content" class="form-control" name="writeContent"></textarea>
							<script>
								CKEDITOR.replace("writeContent", {
									height:'400px',
									filebrowserUploadUrl : "<%=request.getContextPath()%>/board/imageUpload",
									uploadUrl: "<%=request.getContextPath()%>/board/imageUpload/drag",
								});
								CKEDITOR.on('dialogDefinition', function (ev) {
						            var dialogName = ev.data.name;
						            var dialog = ev.data.definition.dialog;
						            var dialogDefinition = ev.data.definition;
						            if (dialogName == 'image2') {
						                dialog.on('show', function (obj) {
						                    this.selectPage('Upload'); //업로드텝으로 시작
						                });
						                dialogDefinition.removeContents('advanced'); // 자세히탭 제거
						                dialogDefinition.removeContents('Link'); // 링크탭 제거
						                
						            }
						            var infoTab = dialogDefinition.getContents( 'info' );  //info탭을 제거하면 이미지 업로드가 안된다.
						            infoTab.remove( 'txtHSpace');
						            infoTab.remove( 'txtVSpace');
						            infoTab.remove( 'txtBorder');
						            //infoTab.remove( 'txtWidth');
						            //infoTab.remove( 'txtHeight');
						            infoTab.remove( 'ratioLock');
						            infoTab.remove( 'cmbAlign');
						        });
							</script>
						</div>
						<input class="post_btn" type="button" onclick="postWrite()" value="글 쓰기">
					</form>
					</div>
				</div>
			</div>
		</div>
	<jsp:include page="include/footer.jsp?t=<%=System.currentTimeMillis()%>"></jsp:include>
	</div>
</body>
</html>