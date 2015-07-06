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
	<head>
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
				<div class="panel-body">
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${boxlist.data.curr_page}"
							totalPages="${boxlist.data.page_rows}" perPageRows="10"
							totalRows="${boxlist.data.total_rows}"
							linkBaseUrl="${cbasePath}subject/getBoxPostByType?type=subject">
						</pageNation:PageNation>
					</ul>
					</nav>
					
					
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${boxlist.data.curr_page}"
							totalPages="${boxlist.data.page_rows}" perPageRows="10"
							totalRows="${boxlist.data.total_rows}"
							linkBaseUrl="${cbasePath}subject/getBoxPostByType?type=subject">
						</pageNation:PageNation>
					</ul>
					</nav>
				</div>
			</div>
		</div>
	</body>
</html>