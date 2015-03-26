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
<title>分类管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
<style>
#userInfoDiv div {
	padding: 10px;
}

#btnGroupDiv button {
	margin: 10px;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<ol class="breadcrumb">
			<li><a href="#">分类管理</a></li>
			<li><a href="${cbasePath}category/categoryList">分类列表</a></li>
			<li class="active">分类详情 <small>
				 </small>
			</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12"
							src="${categoryDetail.data.result.logoUrl }" alt="" /> <a
							href="${cbasePath}category/updateFirstCategoryForm?id=${categoryDetail.data.result.id }">
							<button type="button" class="btn btn-warning btn-block">编辑</button><br>
						</a>
						<button type="button" class="btn btn-warning btn-block">删除</button><br>
						<a
							href="${cbasePath}category/createSecondCategoryForm?parentId=${categoryDetail.data.result.id }">
						<button type="button" class="btn btn-warning btn-block">添加二级分类</button>
						</a>
						<hr />
						 
					</div>
					<div id="userInfoDiv" class="col-xs-10" style="">
						<h4 style="margin-left: 12px;">
							一级分类：${categoryDetail.data.result.categoryName}  
						</h4>
						<ul class="list-group">
						
						<br><br>一级分类所包含的二级分类：
							<c:forEach items="${categoryDetail.data.result.childCategory}"
								varStatus="key" var="Recourse">
								 
								 
								 <div class="row" style="padding: 20px;">
							<div class="col-xs-1">
								<h5 style="margin-top: 40px;">
									<span class="label label-default">${key.count}</span>
								</h5>
							</div>

							<div class="col-xs-1">
								<div class="row">
									<img class="col-xs-12 thumbnail" src="${Recourse.logoUrl}"
										style="margin-top: 10px;" alt="" />
								</div>
							</div>
							<div class="col-xs-10">
								<h4 style="margin-left: 12px;">
									<a href="${cbasePath}category/categoryDetail?id=${Recourse.id}">
										${Recourse.categoryName} </a><br> 
								</h4>


								<div class="col-xs-12 btn-group-sm">
									<button data="${Recourse.id}" type="button"
										class="deleteBtn btn btn-primary">删除</button>
										<a
							href="${cbasePath}category/updateSecondCategoryForm?id=${Recourse.id }">
										<button data="${Recourse.id}" type="button"
										class="info btn btn-primary">编辑</button>
										</a>
									 
								</div>

							</div>
						</div>
								 
								 

								
						</ul>
						</c:forEach>

					</div>




				</div>
			</div>
		</div>
		<script>
			$(function() {
				/* $("#searchIt").click(function(){
					window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
				}); */
			});
		</script>
</body>
</html>

