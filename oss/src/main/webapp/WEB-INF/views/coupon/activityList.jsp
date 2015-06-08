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
<title>优惠劵列表</title>
<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/font.css" rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
				<form class="form-inline" action="${cbasePath}coupon/activityList"
					method="post" role="form">
					<div class="form-group">
						<label class="sr-only" for="keyword">Search:</label> <input
							class="form-control" id="keyword" name="keyword"
							placeholder="Enter keyword" value="${keyword}">
					</div>

					<button id="searchIt" type="submit" class="btn btn-default">Search
						it!</button>
				</form>
				<c:if test="${activityList.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${activityList.data.curr_page}"
							totalPages="${activityList.data.page_rows}" perPageRows="10"
							totalRows="${activityList.data.total_rows}"
							linkBaseUrl="${cbasePath}coupon/activityList?keyword=${keyword}">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
					<!---数据显示区域-->
					
					<table class="table table-hover">
						<thead>
							<tr>
								<th>活动名称</th>
								<th>发行数量</th>
								<th>创建时间</th>
								<th>开始时间</th>
								<th>结束时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${activityList.data.result}" var="Recourse">
								<tr>
									<td>${Recourse.activityName}</td>
									<td>${Recourse.num}</td>
									<td><Date:date value="${Recourse.ctime}"></Date:date></td>
									<td><Date:date value="${Recourse.expiryDateStart}"></Date:date></td>
									<td><Date:date value="${Recourse.expiryDateEnd}"></Date:date></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${activityList.data.curr_page}"
							totalPages="${activityList.data.page_rows}" perPageRows="10"
							totalRows="${activityList.data.total_rows}"
							linkBaseUrl="${cbasePath}coupon/activityList?keyword=${keyword}">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
			
		</div>
	</div>
	<script type="text/javascript">
	</script>
</body>
</html>

