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
			<li><a href="#">群组管理</a></li>
			<li><a href="${cbasePath}group/groupList">群组列表</a></li>
			<li><a
				href="${cbasePath}user/userGroup?userid=${user.data.result.id}">为用户:
					<small> ${user.data.result.nickName }</small>创建群
			</a></li>
			<li class="active">创建群</li>
		</ol>

		<div class="row">
			<div class="col-xs-2"></div>
			<div class="col-xs-10">
				<form role="form" action="${cbasePath}group/createGroupForUserAction" method="post">
					<div class="form-group">
						<label for=""groupName"">群名称</label> <input type="text"
							class="form-control" id="groupName" name="groupName"
							placeholder="输入群名称">
					</div>
					<div class="form-group">
						<label for="groupDesc">群介绍</label>
						<textarea class="form-control" id="groupDesc" name="groupDesc"
							placeholder="输入群介绍">
								
						</textarea>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">群标签</label> <input type="text"
							class="form-control" id="groupTag" name="groupTag"
							placeholder="标签以,分隔">
					</div>
					<input type="hidden" name="id" value="${userid }">

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
		});
	</script>
</body>
</html>

