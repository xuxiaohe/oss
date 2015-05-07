<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
<title>创建Banner</title>
<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/font.css"
	rel="stylesheet">
</head>
<body>
	<div class="container-fluid">

		<ol class="breadcrumb">
			<li><a href="#">banner管理</a></li>
			<li><a href="<%=contextPath%>/banner/list">banner列表</a></li>

			<li class="active">banner创建</li>
		</ol>

		<div class="row">
			<div class="col-xs-9">
				<form role="form" method="post"
					action="<%=contextPath%>/banner/create">

					<div class="form-group">
						<label for="exampleInputEmail1">名称</label> <input type="text"
							class="form-group" />
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">类型</label>
						<input type="radio" name="bannerType" value="1" checked="checked"/>站内
						<input type="radio" name="bannerType" value="0"/>站外
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
