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
			<li><a
				href="${cbasePath}user/userDetail?userid=${resuserDetail.data.result.id}">用户详情:
					<small> ${resuserDetail.data.result.nickName }</small>
			</a></li>
			<li class="active">群组列表</li>
		</ol>

		<c:if test="${resuserGroup.status == '200' }">

			<div class="panel-group" style="margin: 10px;" id="accordion"
				role="tablist" aria-multiselectable="true">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingOne">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseOne" aria-expanded="true"
								aria-controls="collapseOne"> 我创建的 </a>
						</h4>
					</div>
					<div id="collapseOne" class="panel-collapse collapse in"
						role="tabpanel" aria-labelledby="headingOne">
						<div class="panel-body">
							<c:if test="${resuserGroup.status == '200'}">
								<c:forEach items="${resuserGroup.data.result.GroupByOwner}"
									varStatus="key" var="group">
									<div class="col-xs-2">
										<a href="${cbasePath}group/groupDetail?gid=${group.id}"
											class="thumbnail"> <img src="${group.logoUrl}"
											alt="${group.groupName}">
											<div class="caption">
												<p class="text-center">${group.groupName}</p>
											</div>
										</a>
									</div>
								</c:forEach>
							</c:if>
							<div class="col-xs-2">
								<a
									href="${cbasePath}user/createGroupForUserForm?userid=${resuserDetail.data.result.id}">
									<img class="thumbnail col-xs-6"
									src="${cbasePath}resources/assets/image/plus.jpg" alt="">
									
								</a>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingTwo">
						<h4 class="panel-title">
							<a class="collapsed" data-toggle="collapse"
								data-parent="#accordion" href="#collapseTwo"
								aria-expanded="false" aria-controls="collapseTwo"> 我管理的 </a>
						</h4>
					</div>
					<div id="collapseTwo" class="panel-collapse collapse in"
						role="tabpanel" aria-labelledby="headingTwo">
						<div class="panel-body">
							<c:if test="${resuserGroup.status == '200'}">
								<c:forEach items="${resuserGroup.data.result.GroupByAdmin}"
									varStatus="key" var="group">
									<div class="col-xs-2">
										<a href="${cbasePath}group/groupDetail?gid=${group.id}"
											class="thumbnail"> <img src="${group.logoUrl}"
											alt="${group.groupName}">
											<div class="caption">
												<p class="text-center">${group.groupName}</p>
											</div>
										</a>
									</div>
								</c:forEach>
							</c:if>

						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingThree">
						<h4 class="panel-title">
							<a class="collapsed" data-toggle="collapse"
								data-parent="#accordion" href="#collapseThree"
								aria-expanded="false" aria-controls="collapseThree"> 我加入的 </a>
						</h4>
					</div>
					<div id="collapseThree" class="panel-collapse collapse in"
						role="tabpanel" aria-labelledby="headingThree">
						<div class="panel-body">
							<div class="row">
								<c:if test="${resuserGroup.status == '200'}">
									<c:forEach items="${resuserGroup.data.result.GroupByMember}"
										varStatus="key" var="group">
										<div class="col-xs-2">
											<a href="${cbasePath}group/groupDetail?gid=${group.id}"
												class="thumbnail"> <img src="${group.logoUrl}"
												alt="${group.groupName}">
												<div class="caption">
													<p class="text-center">${group.groupName}</p>
												</div>
											</a>
										</div>
									</c:forEach>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
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

