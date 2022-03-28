<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Date,com.gamgyul.gams.dto.*,java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	BoardDTO board = null;
	String subject = "";
	int board_idx = 0;
	String content = "";
	int vote = 0;
	if (request.getAttribute("board") != null) {
		board = (BoardDTO) request.getAttribute("board");
		subject = board.getBoard_subject();
		board_idx = board.getBoard_idx();
		content = board.getBoard_content();
		vote = board.getBoard_vote();
	}else{
		response.sendRedirect("/error/404.jsp");
	}
	CustomerDTO customer = null;
	try {
		customer = (CustomerDTO) session.getAttribute("customer");
	} catch (Exception e) {
		customer = null;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<script src="<%=request.getContextPath()%>/js/view.js?"></script>
<meta charset="UTF-8">
<title><%=subject %></title>

</head>
<body>
	<div class="main-content">
	<input id="bcustomer_idx" type="hidden" value="<%=board.getCustomer_idx()%>">
	<jsp:include page="include/header.jsp"></jsp:include>
		<div class="main_wrap">
			<div class="container">
				<div class="main_box">
					<div class="board_header_box">
						<span class="board_header"><%=request.getParameter("category") %></span>
						<input id="board_idx" type="hidden" value="<%=board_idx%>">
					</div>
					<div class="board_container">
						<div class="view_subject_container">
							<div class="view_subject"><h2><%=subject %></h2></div>
						</div>
						<div class="view_contents_container">
							<div class="view_contents"><%=content %></div>
							<div class="vote_container_box">
								<div class="vote_container"><img width="40" onclick="addVote();" src="<%=request.getContextPath()%>/images/vote.png"></div>
								<div class="vote_count"><span id="vote"><%=vote %></span></div>
							</div>
						</div>
						<%
							if(customer != null){
								if(customer.getCustomer_idx().equals(board.getCustomer_idx()) || customer.getCustomer_admin() == 1){
									out.print("<div class='view_subject_btn'><ul><li><a href='"+board_idx+"/delete?category="+URLEncoder.encode(request.getParameter("category"),"UTF-8")+"'>삭제</a></li></ul></div>");
								}
							}
						%>
						<div>
							<div class="social_share"><ul><li><a href="javascript:KakaoLinkSend();"><img width="45" title="카카오톡 공유하기" src="<%=request.getContextPath()%>/images/share/kakaobtn.png"></a></li><li><a href="javascript:CopyUrl();"><img width="45" title="주소 복사하기" src="<%=request.getContextPath()%>/images/share/urlbtn.png"></a></li></ul></div>
						</div>
					</div>
					<hr>
					<div class="board_comment_container">
						<div id="commentArea" class="board_comment">
							<%
								List<CommentDTO> cmtList = (List<CommentDTO>)request.getAttribute("cmtList");
							
								for(int i = 0; i < cmtList.size();i++){
									CommentDTO cmt = cmtList.get(i);
									if(cmt.getCustomer_idx().equals(board.getCustomer_idx())){
										%>
											<div class='cmt_writer'>
												<div class='cmt_header'><span><%=cmt.getCustomer_name() %></span><img class='cmt_customer_profile' src='<%=cmt.getCustomer_url()%>'></div>
												<div class='cmt_main'><span><%=cmt.getCmt_contents() %></span></div><%=cmt.getCmt_date() %>
											</div>
										<%
									}else{
										%>
											<div class='cmt_other'>
												<div class='cmt_header'><img class='cmt_customer_profile' src='<%=cmt.getCustomer_url()%>'><span><%=cmt.getCustomer_name() %></span></div>
												<div class='cmt_main'><span><%=cmt.getCmt_contents() %></span></div><%=cmt.getCmt_date() %>
											</div>
										<%
									}
								}
							%>
						</div>
						<div>
							<textarea name="content" id="content" class="cmt_input" rows="2" autocomplete="off" placeholder="댓글"></textarea><input style="margin-left: 20px;" class="cmt_btn" onclick="SessionCheck();" type="button" value="보내기">
							<script>
								function SessionCheck(){
									var session = <%=(customer==null?"false":"true")%>;
									if(session == false){
										alert("로그인이 필요합니다");
										return;
									}else{
										content.value = encodeURIComponent(content.value);
										addCmt();
										content.value = "";
									}								
								}
							</script>
						</div>
					</div>
				</div>
			</div>
		</div>
	<jsp:include page="include/footer.jsp?t=<%=System.currentTimeMillis()%>"></jsp:include>
	</div>
	

</body>
</html>