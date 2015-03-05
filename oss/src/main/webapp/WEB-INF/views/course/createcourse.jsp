<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>

<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>

<head lang="en">

<meta charset="UTF-8">

<title>课程管理</title>

<script src="${cbasePath}/resources/assets/js/jquery.min.js"></script>

<script src="${cbasePath}/resources/assets/js/bootstrap.min.js"></script>

<link href="${cbasePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">

<link href="${cbasePath}/resources/assets/css/font.css" rel="stylesheet">
<script src="${cbasePath}/resources/assets/js/html5shiv.js"></script>
<script src="${cbasePath}/resources/assets/js/plupload.full.min.js"></script>
<script src="${cbasePath}/resources/assets/js/qiniu.js"></script>

</head>
<body>
	<div class="container-fluid">
	<jsp:include page="header.jsp"></jsp:include>
	<div class="panel panel-default">
	<div class="panel-body">
		<div class="h4">课程名称</div>
		<div class="col-xs-10 col-sm-6 col-md-4">
			<div class="row"><input type="text" id="courseTitle" class="form-control" /></div>
		</div>
		<div class="col-xs-12" id="Chapters">
		</div>
		<div class="row">
			<div class="col-xs-12">
				<div class="h5"><button class="btn btn-info" id="createChapter">点击添加章节</button></div>
			</div>
		</div>
		<div>
			<button class="btn btn-primary" onclick="saveCourse()">提交</button>
		</div>
	</div>
</div>
</div>
<script>
	var courseId="${courseId}";
	var knowledgeId="";
	var chapter=[];
	var lesson=[];
	$(function(){
		var html = "<div class='row'><div class='col-xs-10 col-sm-9'><div class='panel panel-default'>"
					+"<div class='panel-body'>"
					+"<div class='col-xs-12 col-sm-7'><div class='row'><label>章节名称</label><input type='text' class='form-control c-title' /></div></div>"
					+"<div class='col-xs-12 col-sm-7'><div class='row'><h5><button class='btn btn-info addlesson'>点击添加课时</button></h5></div></div>"
					+"<div class='col-xs-12'><div class='row'><button class='btn btn-default cancel' style='margin-right:15px;'>取消</button><button class='btn btn-primary chapter'>提交</button></div></div>"
					+"</div></div></div></div>";

		var createChapter = $("#createChapter");
		createChapter.click(function(){
			$(this).parent().parent().append(html);
		});

		createChapter.parent().parent().delegate(".addlesson","click",function(){
			$(this).parent().parent().append("<div class='input-group'  style='margin-bottom:15px;'>"+
          		"<input type='text' class='form-control' />"+
          		"<span class='input-group-btn'>"+
            		"<button class='btn btn-primary' onclick='addKnowledge(this);' type='button'>添加知识</button>"+
          		"</span>"+
        		"</div>");
		});

		createChapter.parent().parent().delegate(".cancel ","click",function(){
			$(this).closest(".panel").closest(".row").remove();
		});
		createChapter.parent().parent().delegate(".chapter","click",function(){
			saveChapter(this);
		});
		
	});
	
	function saveChapter(sender){
		alert("ok");
		var container = $(sender).closest(".panel-body");
		var title=$.trim(container.find(".c-title").val());
		if(title==""){
			alert("章节名称非空！");
			return false;
		}
		  $.ajax({
				url :"${cbasePath}course/saveChapter",
				type : "POST",
				data :{
					"lessonIds": container.data("lesson"),
					"title" : title
				},
				success : function(result) {
					chapter.push(result.chapterId);
				}
		 }); 
		  $("#Chapters").append("<div>"+title+"</div>");
		  $(sender).closest(".panel").parent().remove();
	};
	
	function saveCourse(){
		console.log(chapter);
		var title=$.trim($("#courseTitle").val());
		if(title==""){
			alert("课程名称非空！");
			return false;
		}
		  $.ajax({
				url :"${cbasePath}course/modifyCourse",
				type : "POST",
				data :{
					"chapterIds": chapter,
					"courseId" : courseId,
					"title":$("#courseTitle").val()
				},
				success : function(result) {
					alert("课程创建成功");
				}
		 }); 
	};
	function savaeLesson(sender){
		var title =$.trim($(sender).closest(".input-group").find(".form-control").val());
		 var container = $(sender).closest(".panel-body");
		 var inputGroup = container.find(".input-group");
		 var current = $(sender).closest(".input-group").eq(0);
		var index = inputGroup.index(current);
		 var value = container.val();
		  $.ajax({
				url :"${cbasePath}course/createLesson",
				type : "POST",
				data :{
					"title":title,
					"knowledgeId":knowledgeId
				},
				success : function(result) {
					alert(result.lessonId);
					 if(!container.data("lesson")){
						 container.data("lesson",new Array());
					 }
					 if(container.data("lesson").length-1 >= index){
						 container.data("lesson")[index] = result.lessonId;
					 }else{
						 container.data("lesson").push(result.lessonId);
						 console.log(container.data("lesson"));
					 }
					 
				//	lesson.push(result.lessonId);
				}
		 }); 
		 
		  
		//  return lesson;
	}
	function addKnowledge(sender){
		/* alert("添加知识");
		lesson=[];
		lesson=savaeLesson(); */
		var input =$.trim($(sender).closest(".input-group").find(".form-control").val());
		if(input==""){
			alert("课时名称非空");
			return false;
		}
		$.ModalDialog(sender,"test","${cbasePath}knowledge/uploadKnowledge?ckey=knowledge_video");
	}

//	弹框
	$.ModalDialog = function(sender,title,url){
		var dialog = $("<div />");
		dialog.attr("class","modal fade");
		dialog.get(0).id = "ModalId";
		var html = "<div class='modal-dialog'>" +
				"<div class='modal-content'>" +
				"<div class='modal-header'>" +
				"<button type='button' class='close'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>" +
				"<h4 class='modal-title'>"+title+"</h4></div>" +
				"<div class='modal-body'><button class='btn btn-info' data-dismiss='modal'>close</button></div>" +
				"</div></div>";


		dialog.append(html);
		$("body").append(dialog);
		dialog.on("hidden.bs.modal",function(){
			$(this).remove();
		});
		dialog.data("sender",sender);
		
		dialog.modal("show");
		dialog.find(".modal-body").load(url);
		dialog.find(".close").click(function(){
			dialog.modal("hide");

		});
	};

	$.CloseModalDialog = function(){
		$("#ModalId").modal("hide");	
	}
	
</script>
</body>
</html>

