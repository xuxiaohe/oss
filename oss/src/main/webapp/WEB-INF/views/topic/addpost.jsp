<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>用户管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
<style>
#userInfoDiv div {
	padding: 10px;
}
</style>
</head>
<body>
	<div class="container-fluid">

		<ol class="breadcrumb">
			<li><a href="#">话题仓库</a></li>
			<li><a href="${cbasePath}topic/topicList">话题列表</a></li>
			 
			<li class="active">主楼回复创建</li>
		</ol>
			主楼回复：<br>
		<div class="row">
		
			<%-- <div class="col-xs-3">
				<img class="thumbnail col-xs-12" src="${resuserTopic.data.result.fileUrl}" alt="" />

				<c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach>
			</div> --%>

			<div class="col-xs-9">
				<form role="form" method="post"
					action="${cbasePath}topic/addPostByTopicIdAction?appKey=yxtapp&topicid=${topicid}">
					<div class="form-group">
						<label for="exampleInputEmail1">用户名</label> 
							<select class="form-control" name="uid" id="uidSelect">
							<c:if test="${robots.status == '200' }">
								<c:forEach items="${robots.data.result}" varStatus="key"
									var="Recourse">
									<option value="${Recourse.id }">${Recourse.nickName }:${Recourse.userName }</option>
								</c:forEach>
							</c:if>
						</select>

					</div>
					 
					<div class="form-group">
						<!-- <label for="exampleInputEmail1">类型 0:文本 1：语音 2：图片/带有图片</label> <input type="text"
							name="type" class="form-control" id="exampleInputEmail1"
							placeholder=""> -->
						<!-- <label class="radio-inline">
						  <input type="radio" name="type" id="inlineRadio1" value="0" onclick=""> 文本
						</label> -->
						<!-- <label class="radio-inline">
						  <input type="radio" name="type" id="inlineRadio2" value="1"> 语音 -->
						<!-- </label>
						<label class="radio-inline">
						  <input type="radio" name="type" id="inlineRadio3" value="2"> 图片
						</label> -->
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">内容</label> <input type="text"
							name="message" class="form-control" id="exampleInputEmail1"
							placeholder="">
					</div>
					
					<div class="form-group">
						<!-- <label for="exampleInputEmail1">文件地址（图片/语音）</label> <input type="text"
							name="fileUrl" class="form-control" id="exampleInputEmail1"
							placeholder="">  -->
						<div class="media" id="picFrame">
							<div class="media-left" id="imgContainer">
								<!-- <img id="courseImg" width="100" height="100" src=""> -->
							</div>
							<div class="media-body">
								<div id="container">
									<div class="row">
										<div class="col-xs-9">
											<button id="pic" type="button" class="btn btn-default">上传图片</button>
										</div>
									</div>

								</div>
							</div>

						</div>
					</div>

					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>


	</div>
	<script src="${cbasePath}/resources/assets/js/html5shiv.js"></script>
	<script src="${cbasePath}/resources/assets/js/plupload.full.min.js"></script>
	<script src="${cbasePath}/resources/assets/js/qiniu.js"></script>
	<script src="${cbasePath}/resources/assets/js/moxie.min.js"></script>
	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			$(".btnDelete").click(function() {
				if (window.confirm('你确定要删除吗？')) {
					window.location.href = $(this).attr("data");
				} else {

				}
			});
			upload("10007");
		});
		/**
		选择文件类型
		*/
		/* function chooseType(obj){
			var type = $(obj).val();
			if(type == 0){
				
			}else if(type == 1){
				
			}else if(type == 2){
				
			}
		} */
		function upload(s){
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
		            'FileUploaded': function(up, file, info) {//完成一张图片之后
		            	
		            	var res=$.parseJSON(info);
		            	var imgUrl = 'http://tpublic.qiniudn.com/' + res.key;
		            	jQuery("#imgContainer").append("<img  width=\"100\" height=\"100\" src=\"" + imgUrl + "\">").append("&nbsp;&nbsp;&nbsp;&nbsp;");
		            	var imgs = jQuery("#imgContainer").children('img').length;
		            	if(imgs >= 3){//大于9张则不能再上传
		            		jQuery("#pic").hiden();
		            	}
		            	jQuery.getJSON(imgUrl + '?imageInfo',
		            			function(result){
	            			var height = result.height; //图片高度
	            			var width = result.width; //图片宽度
	            			//通过dom动态添加image
	            			var $imgContainer = jQuery("#imgContainer");
	            			$imgContainer.append("<input type=\"hidden\" name=\"courseImg\" value=\"" + imgUrl + "\"/>");
	            			$imgContainer.append("<input type=\"hidden\" name=\"imgHeight\" value=\"" + height + "\"/>");
	            			$imgContainer.append("<input type=\"hidden\" name=\"imgWidth\" value=\"" + width + "\"/>");
	            			alert("上传成功");
		            	});
		            	
		            },
		            'Error': function(up, err, errTip) {
		                alert(errTip);
		            }
		            ,
		            'Key': function(up, file) {
		            	var key="";
		            	 $.ajax({
								url :"${cbasePath}topic/getFileName",
								type : "POST",
								async : false,
								data :{
									"name" : file.name,
									"pathrule" : "topicPost/imgs/{yyyymm}/"
								},
								success : function(result) {
									key=result;
								}
						 });
		                 return key;
		             }
		        }
		    });
			
		}
	</script>
</body>
</html>

