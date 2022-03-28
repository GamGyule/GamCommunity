<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Date,com.gamgyul.gams.dto.*,java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<script src="<%=request.getContextPath()%>/js/bbs.js"></script>
<meta charset="UTF-8">
<%
CustomerDTO customer = null;
String Category = URLEncoder.encode(request.getParameter("category"),"UTF-8");
try{
	customer = (CustomerDTO)session.getAttribute("customer");
}catch(Exception e){
	customer = null;
}
%>
<title>감귤 - 커뮤니티</title>

</head>
<body>
	<div class="main-content">
	<jsp:include page="include/header.jsp?t=<%=System.currentTimeMillis()%>"></jsp:include>
		<div class="main_wrap">
			<div class="container">
				<div class="main_box">
					<div class="board_header_box">
						<span class="board_header"><%=request.getParameter("category") %></span>
						<input class="add_post_btn" type="button" onclick="<%=(customer==null?"alert('로그인이 필요합니다!');location.href='"+request.getContextPath()+"/login'":"location.href='"+request.getContextPath()+"/board/write?category="+ Category +"'")%>" value="글 쓰기">
					</div>
					<div class="board_container">
						<table>
							<th class="no">#</th><th>제목</th><th>작성자</th><th class="date">날짜</th><th class="hit">조회</th><th class="vote">추천</th>
							<%
								List<BoardDTO> bbs_list = null;
								List<Integer> CmtCnt = null;
													if (request.getAttribute("bbs_list") != null){
														bbs_list = (List<BoardDTO>) request.getAttribute("bbs_list");
														CmtCnt = (List<Integer>)request.getAttribute("CmtCnt");
													}

													
													for (int i = 0; i < bbs_list.size(); i++) {
														String str = "<tr>";
														BoardDTO bbs = bbs_list.get(i);
														str += "<td class='no'>"+bbs.getBoard_idx()+"</td>"+"<td class='subject'><a href='"+request.getContextPath()+"/board/view/"+bbs.getBoard_idx()+"?category="+Category+"'>"+bbs.getBoard_subject()+"</a><a href='"+request.getContextPath()+"/board/view/"+bbs.getBoard_idx()+"?category="+Category+"#commentArea' class='cmt_cnt'>"+(CmtCnt.get(i)==0?"":"["+CmtCnt.get(i).toString()+"]")+"</a></td>"+"<td class='user'>"+bbs.getBoard_user()+"</td>"+"<td class='date'>"+bbs.getBoard_date().substring(5)+"</td>"+"<td class='hit'>"+bbs.getBoard_hit()+"</td><td class='vote'>"+bbs.getBoard_vote()+"</td>";
														out.print(str+"</tr>");
													}
													if(bbs_list.size()==0){
														%>
															<tr><td align="center" colspan="6">어서 글을 작성하세요!</td>
														<%
													}
							%>
						</table>
					</div>
				</div>
			</div>
		</div>
	<jsp:include page="include/footer.jsp?t=<%=System.currentTimeMillis()%>"></jsp:include>
	</div>
	

</body>
</html>