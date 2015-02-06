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
<title>分类管理</title>
<script src="${cbasePath}/resources/assets/js/jquery.min.js"></script>
<script src="${cbasePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${cbasePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${cbasePath}/resources/assets/css/font.css" rel="stylesheet">
<style>
#userInfoDiv div {
	padding: 10px;
}
</style>
</head>
<body>
	<div class="container-fluid">

		<ol class="breadcrumb">
			<li><a href="#">分类管理</a></li>
			<li><a href="${cbasePath}category/categoryList">分类列表</a></li>
			<li><a
				href="${cbasePath}category/categoryDetail?id=${categoryDetail.data.result.id}">分类详情:
			</a></li>
			<li class="active">分类编辑</li>
		</ol>

		<div class="row">

			<div class="col-xs-3">
				<img class="thumbnail col-xs-12" src="${categoryDetail.data.result.logoUrl}" alt="" />

				<%-- <c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach> --%>
			</div>

			<div class="col-xs-9">
				<form role="form" method="post" action="${cbasePath}category/updateFirstCategoryAction?id=${categoryDetail.data.result.id}&logoUrl=${categoryDetail.data.result.logoUrl}" enctype="multipart/form-data">
					<div class="form-group">
						<label for="exampleInputEmail1">一级分类名称：</label> <input type=""
							name="categoryName" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${categoryDetail.data.result.categoryName }">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">如果修改请上传一级分类图片：</label>  
						<input id="file" type="file" name="file" /> 
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
		});
	</script>
</body>
</html>

