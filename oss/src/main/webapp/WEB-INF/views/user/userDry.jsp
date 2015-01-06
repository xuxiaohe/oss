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
		<jsp:include page="header.jsp"></jsp:include>
		<ol class="breadcrumb">
			<li><a href="#">用户管理</a></li>
			<li><a href="${cbasePath}user/userList">用户列表</a></li>
			<li><a
				href="${cbasePath}user/userDetail?userid=${resuserDetail.data.result.id}">用户详情:
					<small> ${resuserDetail.data.result.nickName }</small>
			</a></li>
			<li class="active">话题列表</li>
		</ol>

		<c:if test="${resuserTopic.status == '200' }">
			<!-- 分页开始 -->
			<nav>
			<ul class="pagination">
				<pageNation:PageNation currPage="${resuserTopic.data.curr_page}"
					totalPages="${resuserTopic.data.page_rows}" perPageRows="10"
					totalRows="${resuserTopic.data.total_rows}"
					linkBaseUrl="${cbasePath}user/userTopic?userid=${resuserDetail.data.result.id}">
				</pageNation:PageNation>
			</ul>
			</nav>
			<!-- 分页结束 -->
			<c:forEach items="${resuserTopic.data.result}" varStatus="key"
				var="group">
				<div id="topicDiv" style="margin: 10px;" class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12" src="${group.fileUrl}"
							alt="${group.groupInfo.groupName}">
					</div>
					<div class="col-xs-10">
						<h5>
							<a href="${group.url }" target="_blank">
								${group.message}
							</a>
						</h5>
						<a class="btnDelete"
							href="${cbasePath}dry/editForm?userid=${resuserDetail.data.result.id}&dryid=${group.id}">
							<button type="button" class="btn btn-success">修改</button>
						</a>
					</div>
				</div>
			</c:forEach>
			<!-- 分页开始 -->
			<nav>
			<ul class="pagination">
				<pageNation:PageNation currPage="${resuserTopic.data.curr_page}"
					totalPages="${resuserTopic.data.page_rows}" perPageRows="10"
					totalRows="${resuserTopic.data.total_rows}"
					linkBaseUrl="${cbasePath}user/userTopic?userid=${resuserDetail.data.result.id}">
				</pageNation:PageNation>
			</ul>
			</nav>
			<!-- 分页结束 -->
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

