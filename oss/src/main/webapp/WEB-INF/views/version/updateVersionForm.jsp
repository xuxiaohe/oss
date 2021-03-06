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
			<li><a href="#">版本管理</a></li>
			<li><a href="${cbasePath}version/versionList">版本列表</a></li>
			 
			<li class="active">版本编辑</li>
		</ol>

		<div class="row">
			 

			<div class="col-xs-9">
				 
				<form role="form" method="post"
					action="${cbasePath}version/updateVersion?id=${id}">


					 
					<div class="form-group">
						<label for="exampleInputEmail1">版本号</label> <input type="text"
							name="versionId" class="form-control" id="exampleInputEmail1"
							value="${versionId}" placeholder="">
					</div>
					
					<div class="form-group">
						<label for="exampleInputEmail1">更新描述</label> <input type="text"
							name="message" class="form-control" id="exampleInputEmail1"
							value="${message}" placeholder="">
					</div>
					
					<div class="form-group">
						<label for="exampleInputEmail1">下载地址</label> <input type="text"
							name="url" class="form-control" id="exampleInputEmail1"
							value="${url}" placeholder="">
					</div>
					
					<div class="form-group">
						<label for="exampleInputEmail1">app版本描述</label> <input type="text"
							name="context" class="form-control" id="exampleInputEmail1"
							value="${context}" placeholder="">
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

