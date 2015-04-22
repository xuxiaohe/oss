<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
	<head lang="en">
	<meta charset="UTF-8">
	<title>数据统计</title>
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
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row" style="padding: 20px;">
						<div class="col-xs-1">
							<h5 style="margin-top: 40px;">
								<span class="label label-default">1</span>
							</h5>
						</div>
	
						<div class="col-xs-1">
							<div class="row">
								<img class="col-xs-12 thumbnail"
									src="http://echarts.baidu.com/doc/asset/img/example/line1.png"
									style="margin-top: 10px;" alt="" />
							</div>
						</div>
						<div class="col-xs-10">
							<h4 style="margin-left: 12px;">
								<a href="<%=contextPath%>/table/user/page"> 用户数据统计</a><br> <small><small
									class="pull-right">(日/月)用户总数及新增用户数统计</small></small>
							</h4>
						</div>
	
					</div>
					<div class="row" style="padding: 20px;">
						<div class="col-xs-1">
							<h5 style="margin-top: 40px;">
								<span class="label label-default">2</span>
							</h5>
						</div>
	
						<div class="col-xs-1">
							<div class="row">
								<img class="col-xs-12 thumbnail"
									src="http://echarts.baidu.com/doc/asset/img/example/line1.png"
									style="margin-top: 10px;" alt="" />
							</div>
						</div>
						<div class="col-xs-10">
							<h4 style="margin-left: 12px;">
								<a href="<%=contextPath%>/table/groupTable"> 群组数据统计</a><br>
								<small><small class="pull-right">(日/月)群组的总量及新增量统计</small></small>
							</h4>
						</div>
	
					</div>
					
				</div>
			</div>
		</div>
	</body>
</html>

