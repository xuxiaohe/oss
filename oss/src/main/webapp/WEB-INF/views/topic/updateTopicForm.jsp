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
				<img class="thumbnail col-xs-12"
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
					<div class="form-group">
						<label for="exampleInputEmail1">如果修改请上传话题图片(支持JPEG,JPG,PNG)</label> <input
							id="file" type="file" name="file" />
					</div>


					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>


	</div>
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
			$("#categorySelect").change();
		});
	</script>
</body>
</html>

