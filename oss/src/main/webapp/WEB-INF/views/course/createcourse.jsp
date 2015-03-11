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
<script src="${cbasePath}/resources/assets/js/moxie.min.js"></script>
<script type="text/javascript">
$(function (){
	upload("knowledge_video_cover");
})
function upload(s){
	var ckey=s;
    var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'pic',
        container: 'container',
        drop_element: 'container',
        max_file_size: '100mb',
        flash_swf_url: '${cbasePath}/js/Moxie.swf',
        dragdrop: true,
        chunk_size: '4mb',
        uptoken_url: '${cbasePath}/knowledge/getToken',
        domain: 'tpublic.qiniudn.com',
       	filters:"{mime_types : [{ title : \"Image files\", extensions : \"jpg,gif,png\" }  ],  max_file_size : '2mb', \r\n  prevent_duplicates : true}",
        auto_start: true,
        multi_selection:false,
        
        init: {
             'UploadComplete': function() {
             	
            }, 
            'FileUploaded': function(up, file, info) {
            	$("#key").val("");
            	var res=$.parseJSON(info);
            	$("#key").val(res.key);
            	$.ajax({
					url :"${cbasePath}attachFile/add",
					type : "POST",
					async : false,
					data :{
						"ckey":ckey,
						"fkey":res.key,
						"fname":file.name
					},
					success : function(result) {
						$("#attachId").val("");
						$("#attachId").val(result.attid);
						console.log(result);
						$("#courseImg").attr("src",result.furl);
						$("#logoUrl").val(result.furl);
						alert("上传成功");
					}
			 });
            },
            'Error': function(up, err, errTip) {
                alert(errTip);
            }
            ,
            'Key': function(up, file) {
            	var key="";
            	 $.ajax({
						url :"${cbasePath}knowledge/getFileName",
						type : "POST",
						async : false,
						data :{
							"name" : file.name,
							"pathrule" : "${config.pathrule}"
						},
						success : function(result) {
							$("#kngName").val("");
							key=result;
							$("#kngName").val(file.name);
						}
				 });
                 
                 return key
             }
        }
    });
	
}
	
</script>
</head>
<body>
	<div class="container-fluid">
	<jsp:include page="header.jsp"></jsp:include>
	<div class="panel panel-default">
	<div class="panel-body">
	<div class="form-group clearfix">
		<div class="h4">用户名</div> 
		<div class="col-xs-10 col-sm-6 col-md-4">
		<div class="row">
			<select class="form-control" name="uid" id="uidSelect">
			<c:if test="${robots.status == '200' }">
				<c:forEach items="${robots.data.result}" varStatus="key"
					var="Recourse">
					<option value="${Recourse.id }">${Recourse.nickName }:${Recourse.userName }</option>
				</c:forEach>
			</c:if>
		</select>
		</div>
			
		</div>
			

	</div>
	<div class="form-group clearfix">
		<div class="h4">课程名称</div>
		<div class="col-xs-10 col-sm-6 col-md-4">
			<div class="row">
				<input type="text" id="courseTitle" class="form-control" />
			</div>
		</div>
	</div>
	<div class="form-group clearfix">
		<div class="media">
			<div class="media-left">
				<img id="courseImg" width="100" height="100" src="">
			</div>
			<div class="media-body">
				<div id="container">
					<div class="row">
						<div class="col-xs-9">
							<button id="pic" type="submit" class="btn btn-default">上传图片</button>
						</div>
					</div>
				
					<input type="hidden" id="key">
					<input type="hidden" id="attachId">
					<input type="hidden" id="kngName">
					<input type="hidden" id="input">
					<input type="hidden" id="logoUrl" class="form-control" />
			</div>
		</div>
		
		
		</div>
	</div>
	<div class="form-group clearfix">
		<div class="h4">课程简介</div>
		<div class="col-xs-10 col-sm-6 col-md-4">
			<div class="row"><input type="text" id="intro" class="form-control" /></div>
		</div>
	</div>
	<div class="form-group clearfix">
		<div class="h4">课程标签</div>
		<div class="col-xs-10 col-sm-6 col-md-4">
			<div class="row"><input type="text" id="tagNames" class="form-control" placeholder="多个标签之间请用逗号隔开"/></div>
		</div>
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
	//var lesson=[];
	var userId="";
	$(function(){
		var html = "<div class='row'><div class='col-xs-10 col-sm-9'><div class='panel panel-default'>"
					+"<div class='panel-body'>"
					+"<div class='col-xs-12 col-sm-10'><div class='row'><label>章节名称</label><input type='text' class='form-control c-title' /><label>序号</label><input type='text' class='form-control c-order' /></div></div>"
					+"<div class='col-xs-12'><div class='row'><h5><button class='btn btn-info addlesson'>点击添加课时</button></h5><div class='col-xs-6' style='margin-bottom:10px;'>课时名称</div><div class='col-xs-4' style='margin-bottom:10px;'>序号</div></div></div>"
					+"<div class='col-xs-12'><div class='row'><button class='btn btn-default cancel' style='margin-right:15px;'>取消</button><button class='btn btn-primary chapter'>提交</button></div></div>"
					+"</div></div></div></div>";

		var createChapter = $("#createChapter");
		createChapter.click(function(){
			$(this).parent().parent().append(html);
		});

		createChapter.parent().parent().delegate(".addlesson","click",function(){
			$(this).parent().parent().append("<div class='lesson-row'><div class='col-xs-6'><input type='text' class='form-control title' /></div><div class='col-xs-4'><div class='input-group'  style='margin-bottom:15px;'>"+
          		"<input type='text' class='form-control' />"+
          		"<span class='input-group-btn'>"+
            		"<button class='btn btn-primary' onclick='addKnowledge(this);' type='button'>添加知识</button>"+
          		"</span>"+
        		"</div></div></div>");
		});

		createChapter.parent().parent().delegate(".cancel ","click",function(){
			$(this).closest(".panel").closest(".row").remove();
		});
		createChapter.parent().parent().delegate(".chapter","click",function(){
			saveChapter(this);
		});
		
	});
	
	function saveChapter(sender){
		var container = $(sender).closest(".panel-body");
		var title=$.trim(container.find(".c-title").val());
		var order=$.trim(container.find(".c-order").val());
		if(title==""){
			alert("章节名称非空！");
			return false;
		}
		if(order==""){
			alert("章节序号非空！");
			return false;
		}
		  $.ajax({
				url :"${cbasePath}course/saveChapter",
				type : "POST",
				data :{
					"lessonIds": container.data("id"),
					"title" : title,
					"order":order
				},
				success : function(result) {
					chapter.push(result.chapterId);
				}
		 }); 
		  var cHtml = $("<div class='panel panel-info'>"
	  				+"<div class='panel-heading'>章节编号:"+order+"/章节标题:"+title+"</div><div class='panel-body'><ul></ul></div>"  
	  				+"</div>");
		  var data = $(sender).closest(".panel-body").data("lesson");
		  for(var i=0;i<data.length;i++){
			  cHtml.find(".panel-body").find("ul").append("<li>课时编号:"+data[i].order+"/课时标题:"+data[i].title+"</li>");
		  }
		  $("#Chapters").append(cHtml);
		  $(sender).closest(".panel").parent().remove();
	};
	
	function saveCourse(){
		console.log(chapter);
		var title=$.trim($("#courseTitle").val());
		var logoUrl=$.trim($("#logoUrl").val());
		var intro=$.trim($("#intro").val());
		var tagNames=$.trim($("#tagNames").val());
		if(title==""){
			alert("课程名称非空！");
			return false;
		}
		if(logoUrl==""){
			alert("课程封面非空！");
			return false;
		}
		if(intro==""){
			alert("课程简介非空！");
			return false;
		}
		if(title==""){
			alert("课程标签非空！");
			return false;
		}
		  $.ajax({
				url :"${cbasePath}course/modifyCourse",
				type : "POST",
				data :{
					"chapterIds": chapter,
					"courseId" : courseId,
					"title":title,
					"logoUrl":logoUrl,
					"intro":intro,
					"tagNames":tagNames,
					"userId":$("#uidSelect").val()
				},
				success : function(result) {
					alert("课程创建成功");
					window.location.href = "${cbasePath}course/courseList"
				}
		 }); 
	};
	function savaeLesson(sender){
		var order =$.trim($(sender).closest(".input-group").find(".form-control").val());
		var title = $.trim($(sender).closest(".lesson-row").find(".title").val());
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
					"order":order,
					"knowledgeId":knowledgeId,
					"userId":$("#uidSelect").val()
				},
				success : function(result) {
					var obj = {};
					obj.id = result.lessonId;
					obj.title =title;
					obj.order = order;
					 if(!container.data("lesson")){
						 container.data("lesson",new Array());
						 container.data("id",new Array());
					 }
					 if(container.data("lesson").length-1 >= index){
						 container.data("lesson")[index] = obj;
						 container.data("id")[index] = obj.id;
					 }else{
						 
						 container.data("lesson").push(obj);
						 container.data("id").push(obj.id);
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
		var order =$.trim($(sender).closest(".input-group").find(".form-control").val());
		var title = $.trim($(sender).closest(".lesson-row").find(".title").val());
		if(title==""){
			alert("课时名称非空!");
			return false;
		}
		if(order==""){
			alert("课时序号非空!");
			return false;
		}
		$.ModalDialog(sender,"文件上传","${cbasePath}knowledge/uploadKnowledge?ckey=knowledge_video");
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

