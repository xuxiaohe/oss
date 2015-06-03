<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
	<head lang="en">
	<meta charset="UTF-8">
	<title>优惠劵详情</title>
	<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
	<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
	<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
		rel="stylesheet">
	<link href="<%=contextPath%>/resources/assets/css/font.css"
		rel="stylesheet">
	</head>
	<body>
		<div class="container-fluid">
			<jsp:include page="header.jsp"></jsp:include>
			<ol class="breadcrumb">
				<li><a href="#">优惠券管理</a></li>
				<li><a href="${cbasePath}coupon/couponList">优惠券列表</a></li>
				<li class="active">优惠券详情 <small> </small>
				</li>
			</ol>
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div></div>
					</div>
				
				</div>
			</div>
		</div>
</body>
</html>