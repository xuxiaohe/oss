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
	<head>
	<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
	<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
	<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
		rel="stylesheet">
	<link href="<%=contextPath%>/resources/assets/css/font.css" rel="stylesheet">
	</head>
	<body>
		<div class="container-fluid">
			<jsp:include page="header.jsp"></jsp:include>
			<ol class="breadcrumb">
				<li><a href="<%=contextPath%>/subject/getBoxPostByType?type=subject">专题列表</a></li>
				<li class="active">创建专题<small>
						 </small>
				</li>
			</ol>
			
			<div class="panel panel-default">
				<div class="panel-body">
					<form action="<%=contextPath%>/subject/createsubject" method="post">
						<div class="form">
							<div class="row form-group">
								<div class="col-md-10 col-md-offset-1">
									<label>专题名称</label> <input class="form-control" name="chinaName"/>
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-10 col-md-offset-1">
									<label>专题分类</label> <select class="form-control" name="categoryId">
										<c:forEach items="${categoryList.data.result[0].childCategory}"
											var="item">
											<option value="${item.id}">${item.categoryName}"</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-10 col-md-offset-1">
									<label>是否有效</label> <select class="form-control" name="enabled">
										<option value="0">是</option>
										<option value="1">否</option>
									</select>
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-10 col-md-offset-1">
									<label>排序</label> <input type="text" class="form-control"
										name="order" />
								</div>
							</div>
							<div class="row form-group">
								<div class="col-md-10 col-md-offset-1">
									<label >上传专题封面</label><br>
									<img alt="" src="" width="100" height="100" id="picUrl">
									<button id="pic" type="button" class="btn btn-primary">上传图片</button>
									<input type="hidden" name="logoUrl" id="logoUrl">
									<input type="hidden" name="height" id="picHeight">
									<input type="hidden" name="width" id="picWidth">
								</div>
							</div>
							<div class="row form-group">
								<input type="hidden" name="type" value="subject">
								<div class="col-xs-10"></div>
								<div class="col-xs-2">
									<button class="btn btn-primary">创建</button>
								</div>
							</div>
						</div>
	
	
					</form>
	
				</div>
			</div>
		</div>
		<script src="${cbasePath}/resources/assets/js/html5shiv.js"></script>
		<script src="${cbasePath}/resources/assets/js/plupload.full.min.js"></script>
		<script src="${cbasePath}/resources/assets/js/qiniu.js"></script>
		<script src="${cbasePath}/resources/assets/js/moxie.min.js"></script>
		<script type="text/javascript">
			$(function(){
				upload("10007");
			});
			
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
			            	jQuery.getJSON(imgUrl + '?imageInfo',
			            			function(result){
		            			var height = result.height; //图片高度
		            			var width = result.width; //图片宽度
		            			//通过dom动态添加image
		            			var $imgContainer = jQuery("#picUrl");
		            			$imgContainer.attr("src", imgUrl);
		            			jQuery("#logoUrl").val(imgUrl);
		            			jQuery("#height").val(height);
		            			jQuery("#width").val(width);
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
										"pathrule" : "subject/imgs/{yyyymm}/"
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