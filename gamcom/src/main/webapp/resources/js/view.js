var vote;
var board_idx;
var commentArea;
var bgroup;
var bcustomer;
window.onload = function() {
	Kakao.init('c2ae28510e351f6b6aed330b20d3cc18');
	board_idx = document.querySelector("#board_idx");
	commentArea = document.querySelector("#commentArea");
	bcustomer = document.querySelector("#bcustomer_idx");
	bgroup = document.querySelector("#bgroup");
	vote = document.querySelector("#vote");
	StartInterval();
	commentArea.scrollTop = commentArea.scrollHeight;
}

function StartInterval(){
	voteInterval = setInterval(function() {
		getVote();
	}, 10000);
}

function CommentSubmit(){
	var comment = document.querySelector("#cmt_contents");
	
	var expUrl = ﻿﻿ /(((http(s)?:\/\/)\S+(\.[^(\n|\t|\s,)]+)+)|((http(s)?:\/\/)?(([a-zA-z\-_]+[0-9]*)|([0-9]*[a-zA-z\-_]+)){2,}(\.[^(\n|\t|\s,)]+)+))+/gi;
	comment.value.replace(expUrl, '<a href="$&">$&</a>');
}

function getVote() {
	var xhr;
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	xhr.open('GET', '../../ajax/board/getvote?board_idx=' + board_idx.value);
	xhr.setRequestHeader('Context-Type', 'application/x-www-form-urlencoded;UTF-8');
	

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// 통신 성공시 구현부분
			
		}
	}
	xhr.send();
}

function addCmt() {
	var xhr;
	
	if(!content.value){
		alert("댓글을 입력하세요");
		return;
	}
	
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xhr.open('post', board_idx.value+'/comment/upload?content='+content.value);
	xhr.setRequestHeader('Context-Type', 'application/x-www-form-urlencoded;charset=utf-8');

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// 통신 성공시 구현부분
			getCmt();
		}
	}
	xhr.send();
}

function getCmt() {
	var xhr;
	var bcustomer_idx;
	bcustomer_idx = document.querySelector("#bcustomer_idx");
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xhr.open('POST', '../../ajax/board/'+board_idx.value+'/getCmt?bcustomer_idx='+bcustomer_idx.value);
	xhr.setRequestHeader('Context-Type', 'application/x-www-form-urlencoded');

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// 통신 성공시 구현부분
			var str = xhr.responseText;
			
			commentArea.innerHTML = "";
			commentArea.innerHTML = str;
			commentArea.scrollTop = commentArea.scrollHeight;
		}
	}
	xhr.send();
}


function addVote() {
	alert("추우천~");
	var xhr;
	vote.innerHTML = parseInt(vote.innerHTML)+1;
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xhr.open('GET', '../../ajax/board/addvote?board_idx=' + board_idx.value);
	xhr.setRequestHeader('Context-Type', 'application/x-www-form-urlencoded');

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			
		}
	}
	xhr.send();
}

function KakaoLinkSend() {
	Kakao.Link
			.sendDefault({
				objectType : 'feed',
				content : {
					title : document.title,
					description : '#감귤 #커뮤니티',
					imageUrl : 'https://ndevthumb-phinf.pstatic.net/20190725_209/1564038577213kv5PO_PNG/MHQTID0Ubhnk20190725160937.png',
					link : {
						webUrl : document.location.href,
						mobileWebUrl : document.location.href
					}
				},
				buttons : [ {
					title : '열기',
					link : {
						mobileWebUrl : document.location.href,
						webUrl : document.location.href
					}
				} ]
			});
}

function CopyUrl() {
	var t = document.createElement("textarea");
	document.body.appendChild(t);
	t.value = document.location.href;
	t.select();
	document.execCommand('copy');
	document.body.removeChild(t);

	alert("복사되었습니다.");
}