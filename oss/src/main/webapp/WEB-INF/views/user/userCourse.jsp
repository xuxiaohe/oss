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
		<jsp:include page="header.jsp"></jsp:include>
		<ol class="breadcrumb">
			<li><a href="#">用户管理</a></li>
			<li><a href="${cbasePath}user/userList">用户列表</a></li>
			<li><a href="${cbasePath}user/userDetail?userid=${resuserDetail.data.result.id}">用户详情: <small>
					${resuserDetail.data.result.nickName }</small></a></li>
			<li class="active">
			话题列表
			</li>
		</ol>

		<c:if test="${resuserTopic.status == '200' }">
			
			<!-- 分页结束 -->
			<c:forEach items="${resuserTopic.data.result}" varStatus="key"
				var="group">
				<div id="topicDiv" style="margin: 10px;" class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12" src="${group.logoUrl}"
							alt="${group.title}">
					</div>
					<div class="col-xs-10">
						<div class="caption">
							<p class="">${group.title}</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:if>
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

