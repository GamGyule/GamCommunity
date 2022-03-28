var checkUnload = true;
window.onbeforeunload=function(){
	if(checkUnload){
		return "페이지를 벗어나면 작성중인 글은 없어집니다.";
	}
}

function postWrite(){
	var fm = document.querySelector("#fm");
	var subject = document.querySelector("#subject");
	var content = CKEDITOR.instances.content.getData();
	
	if(subject.value.length < 2){
		alert("제목은 2자 이상입니다.");
		return;
	}
	
	if(content.length < 3){
		alert("내용은 3자 이상입니다.");
		return;
	}	
	
	
	fm.method="POST"
	fm.action="./upload"
	checkUnload = false;
	fm.submit();
	
	
}