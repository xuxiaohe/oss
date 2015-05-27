<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>用户管理</title>
<script src="<%=contextPath %>/resources/assets/js/jquery.min.js"></script>
	<script src="<%=contextPath %>/resources/assets/js/bootstrap.min.js"></script>
	<link href="<%=contextPath %>/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
	<link href="<%=contextPath %>/resources/assets/css/font.css" rel="stylesheet">
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
			 
			<li class="active">话题创建</li>
		</ol>

		<div class="row">

			<%-- <div class="col-xs-3">
				<img class="thumbnail col-xs-12" src="${resuserTopic.data.result.fileUrl}" alt="" />

				<c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach>
			</div> --%>

			<div class="col-xs-9">
				<a href="${url }" target="_blank">${resuserTopic.data.result.message }</a>
				<form role="form" method="post"
					action="${cbasePath}topic/createTopicByGroup">
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
						<label for="exampleInputEmail1">标题</label> <input type="text"
							name="title" class="form-control" id="exampleInputEmail1"
							placeholder="">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">群组</label> 
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
							  选择群组
							</button>
							<input type="text" id="qnametext" class="form-control"/>
							<input type="hidden" id="qidtext" name="gid"/>
						<%-- <label for="exampleInputEmail1">群组</label> 
							<select class="form-control" name="gid" id="gidSelect">
							<c:if test="${groupList.status == '200' }">
								<c:forEach items="${groupList.data.result}" varStatus="key"
									var="Recourse">
									<option value="${Recourse.id }">${Recourse.groupName }</option>
								</c:forEach>
							</c:if>
						</select> --%>
					</div>
					<!-- <div class="form-group">
						<label for="exampleInputEmail1">类型</label> <input type="text"
							name="type" class="form-control" id="exampleInputEmail1"
							placeholder="">
					</div> -->
					<div class="form-group">
						<label for="exampleInputEmail1">内容</label>
						<!-- <input type="text"
							name="content" class="form-control" id="exampleInputEmail1"
							placeholder="" style="width:652px;height:150px"> -->
							<textarea name="content" class="form-control" rows="3" cols="20"></textarea>
					</div>



					<div class="form-group">
						<label for="exampleInputEmail1">标签</label> <input type="text"
							name="tagName" class="form-control" id="exampleInputEmail1"
							placeholder="">例如：name,age,sex
					</div>
					<!-- 上传图片 -->
					<div class="form-group clearfix">
						<div class="media">
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
						<h4 class="modal-title" id="myModalLabel">群组搜索</h4>
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
			$.ajax({
				url : '<%=contextPath%>/dry/tablegroupList',
				type : 'post',
				dataType : 'html',
				success : function(data){
					$("#modalHtml").html("").html(data);
				}
			});
			//$("#myModal").load("<%=contextPath%>/dry/tablegroupList");
			upload("10007");
		});
		function selectGroup(groupId, groupName){
			var $groupSelec = $("#qnametext");
			$groupSelec.val(groupName);
			$("#qidtext").val(groupId);
		}
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
		            	if(imgs >= 9){//大于9张则不能再上传
		            		jQuery("#pic").hiden();
		            		return;
		            	}
		            	jQuery.getJSON(imgUrl + '?imageInfo',
		            			function(result){
	            			var height = result.height; //图片高度
	            			var width = result.width; //图片宽度
	            			//通过dom动态添加image
	            			var $imgContainer = jQuery("#imgContainer");
	            			$imgContainer.append("<input type=\"hidden\" name=\"courseImg\" value=\"" + imgUrl + "\"/>");
	            			$imgContainer.append("<input type=\"hidden\" name=\"picHeight\" value=\"" + height + "\"/>");
	            			$imgContainer.append("<input type=\"hidden\" name=\"picWidth\" value=\"" + width + "\"/>");
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
									"pathrule" : "topic/imgs/{yyyymm}/"
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

