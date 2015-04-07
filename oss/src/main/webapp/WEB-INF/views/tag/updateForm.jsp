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
<title>标签管理</title>
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
			<li><a href="#">标签仓库</a></li>
			<li><a href="${cbasePath}tag/tagList">标签列表</a></li>
			 
			<li class="active">修改标签</li>
		</ol>

		<div class="row">
		


			<div class="col-xs-9">
				<form role="form" method="post"
					action="${cbasePath}tag/updateTag">


					<div class="form-group">
						<label for="exampleInputEmail1">标签名:</label> 
						<input type="hidden" value="${tagDetail.tagName}" name="oldTagName">
						<input type="text"
							name="tagName" class="form-control" id="exampleInputEmail1" value="${tagDetail.tagName}" name="tagName"
							  placeholder="">
					</div>
					
					<div class="form-group">
						<label for="exampleInputEmail1">热度:</label>${tagDetail.score}
					</div>
					


					<button type="submit" class="btn btn-default">提交</button>
				</form>
			</div>
		</div>


	</div>
</body>
</html>

