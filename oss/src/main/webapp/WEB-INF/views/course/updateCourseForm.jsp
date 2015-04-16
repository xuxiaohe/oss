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
			<li><a href="#">课程仓库</a></li>
			<li><a href="${cbasePath}course/courseList">课程列表</a></li>
			<li><a
				href="${cbasePath}course/courseDetail?cid=${courseDetail.data.result.id}">课程详情

			</a></li>
			<li class="active">课程编辑</li>
		</ol>

		<div class="row">
			<div class="col-xs-3">
				<img class="thumbnail col-xs-12"
					src="${courseDetail.data.result.logoUrl}" alt="" />

				<%-- <c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach> --%>
			</div>

			<div class="col-xs-9">
				<%-- <a href="${url }" target="_blank">${resuserTopic.data.result.message }</a> --%>
				<form role="form" method="post"
					action="${cbasePath}course/updateCourse?cid=${courseDetail.data.result.id}"
					enctype="multipart/form-data">


					<div class="form-group">
						<label for="exampleInputEmail1">标题</label> <input type="text"
							name="title" class="form-control" id="exampleInputEmail1"
							value="${courseDetail.data.result.title }" placeholder="">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">课程信息</label> <input type="text"
							name="intro" class="form-control" id="exampleInputEmail1"
							value="${courseDetail.data.result.intro }" placeholder="">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">话题分类</label>
						<select  class="form-control" id="categorySelect" name="categoryId">
							<c:if test="${categoryList != null}">
								<c:forEach items="${categoryList}" var="cate">
									<option <c:if test="${cate.id == courseDetail.data.result.categoryId}">selected</c:if>  value="${cate.id}">${cate.categoryName}</option>
								</c:forEach> 
							</c:if>
						</select>
						原课程分类为：<c:if test="${currCategory.data.result != null}">${currCategory.data.result.categoryName}</c:if>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">二级分类</label>
						<select class="form-control" id="childCategorySelect" name="childCategoryId">
							<c:if test="${currCategory.data.result.childCategory != null}">
								<c:forEach items="${currCategory.data.result.childCategory}" var="cate">
									<option <c:if test="${cate.id == courseDetail.data.result.childCategoryId}">selected</c:if> value="${cate.id}" >${cate.categoryName}</option>
								</c:forEach>
							</c:if>
						</select>
						原课程二级分类为：<c:if test="${currChildCategory.data.result != null}">${currChildCategory.data.result.categoryName}</c:if>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">标签</label>
						<input type="text" class="form-control" name="tagNames" value="${courseDetail.data.result.tagNames}">
					</div>


					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>


	</div>
	<script>
		$(function() {
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
		});
	</script>
</body>
</html>

