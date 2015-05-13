<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>用户管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css"
	rel="stylesheet">
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
			<li><a href="#">课程管理</a></li>
			<li><a href="${cbasePath}course/courseListNoCheck">课程列表</a></li>
			<li class="active">课程详情 <small> </small>
			</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<c:if test="${courseDetail.data.result.logoUrl != null}">
						<img class="thumbnail col-xs-12"
							src="${courseDetail.data.result.logoUrl }" alt="" />
						</c:if>
						<hr />
					</div>
					<div id="userInfoDiv" class="row" >
						<h4 style="margin-left: 12px;">
							课程信息：${courseDetail.data.result.title} <small><small
								class="pull-right">注册时间：<Date:date
										value="${courseDetail.data.result.ctime}"></Date:date></small></small>
						</h4>
						<div class="col-xs-6">简介:${courseDetail.data.result.intro}</div>
						<div class="col-xs-6">创建者:${courseDetail.data.result.createUserName}</div>
						<div class="col-xs-6">
							收费模式:
							<c:choose>
								<c:when test="${courseDetail.data.result.pricemodel=='0'}">免费</c:when>
								<c:when test="${courseDetail.data.result.pricemodel=='1'}">付费</c:when>
								<c:when test="${courseDetail.data.result.pricemodel=='2'}">打赏</c:when>
							</c:choose>
							
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							价格: ${courseDetail.data.result.price}
						</div>

						
					</div>
					
				</div>
				<div class="row" >
					<div class="dol-xs-4"></div>
					<div class="col-xs-4">
							<form role="form" method="post" action="${cbasePath}course/checkCourseAction" >
								<input type="hidden" name="cid" value="${courseDetail.data.result.id}">
								<button type="submit" class="btn btn-primary">通过</button>
								<button type="button" class="btn btn-primary" onclick="javascript:toUncheckPage();">不通过</button>
							</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function toUncheckPage(){
			window.location.href='${cbasePath}course/courseListNoCheck';
		}
	</script>
</body>
</html>

