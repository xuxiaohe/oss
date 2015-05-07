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
<title>Banner管理</title>
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
				<!-- 分页开始 -->
				<nav>
					<ul class="pagination">
						<pageNation:PageNation currPage="${courses.data.curr_page}"
							totalPages="${courses.data.page_rows}" perPageRows="10"
							totalRows="${courses.data.total_rows}"
							linkBaseUrl="${cbasePath}course/courseList?">
						</pageNation:PageNation>
					</ul>
				</nav>

				<!-- 分页开始 -->
				<nav>
					<ul class="pagination">
						<pageNation:PageNation currPage="${courses.data.curr_page}"
							totalPages="${courses.data.page_rows}" perPageRows="10"
							totalRows="${courses.data.total_rows}"
							linkBaseUrl="${cbasePath}course/courseList?">
						</pageNation:PageNation>
					</ul>
				</nav>
			</div>
		</div>
	</div>
	<script>
		
	</script>
</body>
</html>