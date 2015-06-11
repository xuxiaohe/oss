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
				<li><a href="${cbasePath}coupon/activityList">活动列表</a></li>
				<li><a href="${cbasePath}coupon/couponListByActivity?aid=${aid}">红包列表</a></li>
				<li class="active">红包详情 <small> </small>
				</li>
			</ol>
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-6">
							<label>卡号:</label>${couponDetail.data.result.cardCode}
						</div>
						<div class="col-xs-6">
							<label>卡有效期:</label>&nbsp;&nbsp;<Date:date value="${couponDetail.data.result.expiryDateStart}"></Date:date>&nbsp;-&nbsp;
								<Date:date value="${couponDetail.data.result.expiryDateEnd}"></Date:date>
							
						</div>
					</div>
					<div class="row">
						<div class="col-xs-4">
							<label>面值:</label>${couponDetail.data.result.quota}
						</div>
						<div class="col-xs-4">
							<label>卡类型:</label>课程卡
						</div>
						<div class="col-xs-4">
							<label>备注:</label>${couponDetail.data.result.remark}
						</div>
					</div>
					<div class="row">
						<div class="col-xs-4">
							<label>适用课程:${courseDetail.data.result.title}</label>
						</div>
					</div>
					<hr />
					<div class="row">
						<div class="col-xs-5">
							<label>领券人:</label>
						</div>
						<div class="col-xs-5">
							<label>发放时间:</label>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-5">
							<label>下单时间:</label>
						</div>
						<div class="col-xs-5">
							<label>支付时间:</label>
						</div>
					</div>
					<hr/>
					<!-- <div class="row">
						<div class="col-xs-8"></div>
						<div class="col-xs-4">
							<label>订单号:</label>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-1"></div>
						<div class="col-xs-10">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>课程</th>
										<th>课程价格</th>
										<th>优惠</th>
										<th>实付金额</th>
										<th>群组</th>
										<th>课程收益人</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div> -->
					
				</div>
			</div>
		</div>
</body>
</html>