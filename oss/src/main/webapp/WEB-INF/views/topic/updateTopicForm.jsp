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
			<li><a
				href="${cbasePath}topic/topicDetail?topicid=${resuserTopic.data.result.topicId}">话题详情

			</a></li>
			<li class="active">话题编辑</li>
		</ol>

		<div class="row">
			<div class="col-xs-3">
				<img class="thumbnail col-xs-12" name="picUrl" id="picUrl"
					src="${resuserTopic.data.result.picUrl}" alt="" />

				<%-- <c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach> --%>
			</div>

			<div class="col-xs-9">
				<a href="${url }" target="_blank">${resuserTopic.data.result.message }</a>
				<form role="form" method="post"
					action="${cbasePath}topic/updateTopicByGroup?topicid=${resuserTopic.data.result.topicId}&picUrl=${resuserTopic.data.result.picUrl}"
					enctype="multipart/form-data">
					<input type="hidden" value="${courseDetail.data.result.logoUrl}" name="oldLogoUrl"/>
					<input type="hidden" value="" id="logoUrl" name="logoUrl"/>
					
					<input type="hidden" value="" id="height" name="height"/>
					<input type="hidden" value="" id="width" name="width"/>

					

					<div class="form-group">
						<label for="exampleInputEmail1">title</label> <input type="text"
							name="title" class="form-control" id="exampleInputEmail1"
							value="${resuserTopic.data.result.title }" placeholder="">
					</div>
					
					<div class="form-group">
						<label for="exampleInputEmail1">标签</label> <input type="text"
							name="tagName" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserTopic.data.result.tagNames }"> 例如：name,age,sex
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">content</label> 
						
						<textarea name="content" class="form-control" rows="3" cols="20" value="${resuserTopic.data.result.content }">${resuserTopic.data.result.content }</textarea>
						<%-- <input type="text"
							name="content" class="form-control" id="exampleInputEmail1"
							value="${resuserTopic.data.result.content }" placeholder=""> --%>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">话题分类</label>
						<select  class="form-control" id="categorySelect" name="categoryId">
						
							<c:forEach items="${categoryList}" var="cate">
								<option <c:if test="${cate.id == resuserTopic.data.result.categoryId}">selected</c:if>  value="${cate.id}">${cate.categoryName}</option>
							</c:forEach> 
						</select>
						原群组分类为：<c:if test="${currCategory.data.result != ''}">${currCategory.data.result.categoryName}</c:if>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">二级分类</label>
						<select class="form-control" id="childCategorySelect" name="childCategoryId">
							<c:if test="${currCategory.data.result != ''}">
								<c:forEach items="${currCategory.data.result.childCategory}" var="cate">
									<option <c:if test="${cate.id == resuserTopic.data.result.childCategoryId}">selected</c:if> value="${cate.id}" >${cate.categoryName}</option>
								</c:forEach>
							</c:if>
						</select>
						原群组二级分类为：<c:if test="${currChildCategory.data.result != ''}">${currChildCategory.data.result.categoryName} </c:if>
					</div>
					<!-- <div class="form-group">
						<label for="exampleInputEmail1">如果修改请上传话题图片(支持JPEG,JPG,PNG)</label> <input
							id="file" type="file" name="file" />
					</div> -->
					<div class="form-group" id="imageDiv">
						<label for="exampleInputEmail1">话题图片</label><br>
						<c:forEach items="${resuserTopic.data.result.images}" var="Resource" varStatus="sta">
							<div>
							 <img class="" src="${Resource.picUrl}" width="150" height="150" /><br>
							 <input type="hidden" name="picUrl" value="${Resource.picUrl}">
							 <input type="hidden" name="picWidth" value="${Resource.picWidth}">
							 <input type="hidden" name="picHeight" value="${Resource.picHeight}">
							 <button type="button" class="postdelete btn btn-danger" onclick="javascript:removeImage(this);">删除</button>
							 </div>&nbsp;&nbsp;
							<c:if test="${sta.index%3==0&&sta.index!=0}">
								<br/>
							</c:if>
						</c:forEach>
					</div>					
					<div class="form-group">
						<!-- <label for="exampleInputEmail1">文件地址（图片/语音）</label> <input type="text"
							name="fileUrl" class="form-control" id="exampleInputEmail1"
							placeholder="">  -->
						<div class="media" id="picFrame">
							<div class="media-body">
								<div id="container">
									<div class="row">
										<div class="col-xs-9">
											<label for="exampleInputEmail1">修改话题图片</label>
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
	            			var $div = $("#imageDiv");
	            			$div.append("<div>" + 
	            					"<img class='' src='" + imgUrl + "' width='150' height='150' /><br>"
	            					+ "<input type='hidden' name='picUrl' value='" + imgUrl + "'>"
	            					+ "<input type='hidden' name='picWidth' value='" + height + "'>"
	            					+ "<input type='hidden' name='picHeight' value='" + width + "'>"
	            					+ "<button type='button' class='postdelete btn btn-danger' onclick='javascript:removeImage(this);'>删除</button>"
	            					+ "</div>");
	            			if($div.children().length % 3 == 0){
	            				$div.append("<br/>");
	            			}
	            			//var $imgContainer = jQuery("#picUrl");
	            			//$imgContainer.attr("src", imgUrl);
	            			//jQuery("#logoUrl").val(imgUrl);
	            			//jQuery("#height").val(height);
	            			//jQuery("#width").val(width);
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
		function removeImage(obj){
			var $div = $(obj).parent();
			$div.remove();//删除图片所在的段落
		}
	</script>
</body>
</html>

