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
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="clearfix">
			<p >活动名称：${activity.data.result.name }</p>
			<p >活动时间：<Date:date value="${activity.data.result.activityStartTime}"></Date:date>-<Date:date value="${activity.data.result.activityEndTime}"></Date:date></p>
			<p >报名时间：<Date:date value="${activity.data.result.optionStartTime}"></Date:date>-<Date:date value="${activity.data.result.optionEndTime}"></Date:date></p>
			<p >活动类型：<c:choose>
			<c:when test="${activity.data.result.type == 0}">
			需要报名 <%-- 报名必选项 :<c:forEach items="${activity.data.result.options}" varStatus="key" var="Recourse">
				${Recourse },
			</c:forEach> --%>
			</c:when>
			<c:otherwise>
			不需要报名
			</c:otherwise>
			</c:choose></p>
			<p >活动简介：${activity.data.result.intro }</p>
			<p >活动主图：<img width="100" height="100" src="${activity.data.result.mainImg }"></p>
			<p >活动详情：${activity.data.result.des }</p>
			<p >详情主图：<img width="100" height="100" src="${activity.data.result.desMainImg }"></p>
			<p >详情图片：<c:forEach items="${activity.data.result.desImgs}" varStatus="key" var="Recourse">
				<img width="100" height="100" src="${Recourse }">
				
			</c:forEach></p>
			<p >活动价格：${activity.data.result.price }</p>
			<p >主办方：${activity.data.result.company }</p>
			<p>地址：${activity.data.result.province }-${activity.data.result.city }-${activity.data.result.address }</p>
		</div>
			</div>
		</div>
		
	</div>
</body>
</html>

