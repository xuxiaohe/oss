<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css"
	rel="stylesheet">
</head>
<body>
	 <div class="panel panel-default">
			<div class="panel-body">
			<form class="form-inline" action="${cbasePath}dry/tablegroupList"
				method="get" role="form">
				<div class="form-group">
					<label class="sr-only" for="keyword">Search:</label> <input
						class="form-control" id="keyword" name="keyword"
						placeholder="Enter keyword" value="${keyword }">
				</div>

				<button id="searchIt" type="submit" class="btn btn-default">Search
					it!</button>
			</form>
			<c:if test="${groupList.status == '200'}">
				<nav> <!-- 分页开始 -->
				<ul class="pagination">
					<pageNation:PageNation currPage="${groupList.data.curr_page}"
						totalPages="${groupList.data.page_rows}" perPageRows="10"
						totalRows="${groupList.data.total_rows}"
						linkBaseUrl="${cbasePath}dry/tablegroupList?">
					</pageNation:PageNation>
				</ul>

				<!-- 分页结束 --> </nav>

				<!---数据显示区域-->

				<c:forEach items="${groupList.data.result}" varStatus="key"
					var="Recourse">
					<div class="row" style="padding: 20px;">
					<%-- 	<div class="col-xs-1">
							<div class="row">
								<img class="col-xs-12 thumbnail" src="${Recourse.logoUrl}"
									style="margin-top: 10px;" alt="" />
							</div>
						</div> --%>
						<div class="col-xs-10">
							<div class="col-xs-12">
								<p>${Recourse.groupName}</p>
							</div>

						</div>
					</div>
				</c:forEach>
			</c:if>
	 	</div>
					</div>
</body>
</html>