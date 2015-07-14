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
<title>取消分配的优惠劵</title>
<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/bootstrap-datepicker.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/font.css" rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<form action="<%=contextPath%>/deletecoupon/delete" method="post">
				<div class="panel-body bg-info">
					<div class="row form-group">
						<h4>&nbsp;&nbsp;&nbsp;&nbsp;取消卡券</h4>
					</div>
					<div class="row form-group">
						<div class="col-xs-6">
							<label for="cardHeader" >用户ID</label>
							<input type="text" class="form-control" name="userid" id="cname"/>
						</div>
						<div class="col-xs-6">
							<label>活动号码</label>
							<input type="text" class="form-control" name="activitycode" id="actname"/>
						</div>
					</div>
					 
					<div class="row form-group">
						<div class="col-xs-10"></div>				
						<div class="col-xs-2"><button type="submit" class="btn btn-primary">提交</button></div>
					</div>
				</div>
			</form>
		</div>
		
	 
</body>
</html>