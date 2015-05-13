<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

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

<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>

<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>

<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">

<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
<script src="${sourcePath}/resources/assets/js/html5shiv.js"></script>
<script src="${sourcePath}/resources/assets/js/plupload.full.min.js"></script>
<script src="${sourcePath}/resources/assets/js/qiniu.js"></script>
<script src="${sourcePath}/resources/assets/js/moxie.min.js"></script>
<script type="text/javascript">
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
        uptoken_url: '${cbasePath}/knowledge/getToken?ckey='+s,
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
            	var imgUrl = 'http://tpublic.qiniudn.com/' + res.key;
            	jQuery.getJSON(imgUrl + '?imageInfo',
            			function(result){
            		var height = result.height; //图片高度
        			var width = result.width; //图片宽度
        			jQuery("#height").val(height);
        			jQuery("#width").val(width);
            	});
            	$("#key").val(res.key);
            	$.ajax({
					url :"${cbasePath}attachFile/add",
					type : "POST",
					async : false,
					data :{
						"ckey":ckey,
						"fkey":res.key,
						"fname":file.name,
						"fsize":file.size
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
							"pathrule" : "kng/imgs/{yyyymm}/"
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
		<%-- <div class="h4">用户名</div> 
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
			
		</div> --%>
		<label for="uid">所属用户</label>
		<input type="text" class="form-control" name = "userName" id="userName" readonly="readonly"/>	
		<input type="hidden" name="uid" id="uid"/>
		<input type="hidden" name="userLogoUrl" id="userLogoUrl">
		<br/>
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">选择用户</button>
	</div>
	<div class="form-group clearfix">
		<div class="h4">课程名称</div>
		<div class="col-xs-10 col-sm-6 col-md-4">
			<div class="row">
				<input type="text" id="courseTitle" class="form-control" />
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="exampleInputEmail1">一级分类</label>
		<select  class="form-control" id="categorySelect" name="categoryId">
						
			<c:forEach items="${categoryList}" var="cate">
				<option value="${cate.id}">${cate.categoryName}</option>
			</c:forEach> 
		</select>
	</div>
	<div class="form-group">
		<label for="exampleInputEmail1">二级分类</label>
		<select class="form-control" id="childCategorySelect" name="childCategoryId">
		</select>
	</div>
	<div class="form-group clearfix">
		<label for="pricemodel">付费模式:</label>
		<label class="radio-inline"><input type="radio" name="pricemodel" value="0" checked onclick="javascript:checkPriceModel(this);"/>免费</label>
		<label class="radio-inline"><input type="radio" name="pricemodel" value="1" onclick="javascript:checkPriceModel(this);"/>付费</label>
		<label class="radio-inline"><input type="radio" name="pricemodel" value="2" onclick="javascript:checkPriceModel(this);"/>打赏</label>
	</div>
	<div class="form-group clearfix">
		<label for="price">价格</label>
		<input type="text" class="form-control" name="price" id="price" value="" disabled="disabled" onblur="javascript:checkIsNum(this);"/>
		
	</div>
	<div class="form-group clearfix"  style="display:none;" id="scaleDiv">
		<label for="scale">分成比例</label>
		<input type="text" class="form-control" name="scale" id="scale" value="" >
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
					<input type="hidden" id="width"/>
					<input type="hidden" id="height"/>
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
			<button class="btn btn-primary" onclick="saveCourse()">提交</button>（提交课程时先提交章节）
		</div>
	</div>
</div>

		<!-- Modal -->
		<!-- Modal -->
		<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">用户搜索</h4>
					</div>
					<div class="modal-body" id="modalHtml"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
						<!-- <button type="button" class="btn btn-primary">确定</button> -->
					</div>
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
		upload("10007");
		/**
		判断价格是否非法
		*/
		/*$("#price").blur(function(){
			var pirce = $("#price").val();
			if(!isNaN(price)){
				alert("价格非法!");
				$("#price").val("");
			}
		});*/
		var html = "<div class='row'><div class='col-xs-10 col-sm-9'><div class='panel panel-default'>"
					+"<div class='panel-body'>"
					+"<div class='col-xs-12 col-sm-10'><div class='row'><label>章节名称</label><input type='text' class='form-control c-title' /><label>序号</label><input type='text' class='form-control c-order' /></div></div>"
					+"<div class='col-xs-12'><div class='row'><h5><button class='btn btn-info addlesson'>点击添加课时</button></h5><div class='col-xs-6' style='margin-bottom:10px;'>课时名称</div><div class='col-xs-4' style='margin-bottom:10px;'>序号</div></div></div>"
					+"<div class='col-xs-12'><div class='row'><button class='btn btn-default cancel' style='margin-right:15px;'>取消</button><button class='btn btn-primary chapter'>提交章节</button></div></div>"
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
		//二级分类联动
		$("#categorySelect").change(function(){
			//
			var categoryId = $("#categorySelect").val();
			if(categoryId != ''){
				$.ajax({
					url : '${cbasePath}category/getChildCategory',
					data : {'categoryId' : categoryId},
					type : 'post',
					dataType : 'json',
					success : function(result){
						var categorys = result.data.result.childCategory;
						var $childCategorySelect = $("#childCategorySelect");
						$childCategorySelect.empty();
						$.each(categorys, function(index, content){
							if(content.id != ''){
								$childCategorySelect.append("<option value='" + content.id + "'>" + content.categoryName + "</option>");
							}
						});
					}
				});
			}
		});
		$("#categorySelect").change();
		//加载用户搜索列表
		var url = '${cbasePath}course/selectUser';
		$.ajax({
			url : url,
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#modalHtml").html(data);
			}
			
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
		var price = $.trim($("#price").val());
		var pricemodel = $('input:radio[name=pricemodel]:checked').val();
		var uid = $.trim($("#uid").val());
		var userName = $.trim($("#userName").val());
		var userLogoUrl = $.trim($("#userLogoUrl").val());
		var categoryId = $.trim($("#categorySelect").val());
		var childCategoryId = $.trim($("#childCategorySelect").val());
		
		if(pricemodel == '1' && price == ''){
			alert("请输入价格!");
			return false;
		}else if(pricemodel != '1'){
			price = 0;
		}
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
					"createUserName" : userName,
					"userLogo" : userLogoUrl,
					"chapterIds": chapter,
					"courseId" : courseId,
					"title":title,
					"logoUrl":logoUrl,
					"intro":intro,
					"tagNames":tagNames,
					"categoryId" : categoryId,
					"childCategoryId" : childCategoryId,
					"userId":uid,
					"price" : price,
					"pricemodel" : pricemodel,
					"width" : $("#width").val(),
					"height" : $("#height").val()
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
					"userId":$("#uid").val()
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
		$.ModalDialog(sender,"文件上传","${cbasePath}knowledge/uploadKnowledge?ckey=10002");
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
	
	/**
	选择用户
	*/
	function selectUser(userId, userName, logoUrl){
		$("#uid").val(userId);
		$("#userName").val(userName);
		$("#userLogoUrl").val(logoUrl);
	}
	//判断价格是否非法
	function checkIsNum(obj){
		/*var pirce = parseFloat($(obj).val());
		if(prise == NaN){
			alert("价格非法!");
			$(obj).val("");
		}else{
			if(price < 0){
				alert("价格非法!");
				$(obj).val("");
			}
		}*/
	}
	/*判断收费模式*/
	function checkPriceModel(obj){
		var val = $(obj).val();
		if(val == '1'){
			$("#price").attr("disabled", false);
			$("#scaleDiv").show();
		}else{
			$("#price").attr("disabled",true);
			$("#price").val("0");
			$("#scaleDiv").hide();
		}
		
	}
</script>
</body>
</html>

