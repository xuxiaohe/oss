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
<title>订单管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-body">
				<form class="form-inline" action="${cbasePath}order/list"
					method="get" role="form">
					<div class="form-group">
						<label class="sr-only" for="keyword">Search:</label> <input
							class="form-control" id="keyword" name="keyword"
							placeholder="Enter keyword" value="${keyword }">
					</div>

					<button id="searchIt" type="submit" class="btn btn-default">Search
						it!</button>
				</form>
				<c:if test="${orderlist.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${orderlist.data.curr_page}"
							totalPages="${orderlist.data.page_rows}" perPageRows="10"
							totalRows="${orderlist.data.total_rows}"
							linkBaseUrl="${cbasePath}group/groupList?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>

					<!---数据显示区域-->

					<c:forEach items="${orderlist.data.result}" varStatus="key" var="Recourse">
						<div class="row" style="padding: 20px;">
							<div class="col-xs-1">
								<h5 style="margin-top:40px;"><span class="label label-default">${key.count}</span></h5>
							</div>
							<div class="col-xs-1">
								<div class="row">
									<img class="col-xs-12 thumbnail" src="${Recourse.courseLogo}"
										style="margin-top: 10px;" alt="" />
								</div>
							</div>
							<div class="col-xs-10">
								<h4 style="margin-left:12px;">
									<a href="#">
										${Recourse.courseTitle} </a><small><small
										class="pull-right">成交时间：<Date:date
												value="${Recourse.ctime}"></Date:date></small></small>
								</h4>
								<div class="col-xs-6">
									<p>课程拥有者：${Recourse.courseOwerNickName}</p>
								</div>
								<div class="col-xs-6">
									<p>价格：${Recourse.orderPrice}元</p>
								</div>
								<div class="col-xs-6">
									<p>购买用户:${Recourse.userNickName}</p>
								</div>
								<div class="col-xs-6">
									<p>所在群组:${Recourse.groupName}</p>
								</div>
								

							</div>
						</div>
					</c:forEach>
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${orderlist.data.curr_page}"
							totalPages="${orderlist.data.page_rows}" perPageRows="10"
							totalRows="${orderlist.data.total_rows}"
							linkBaseUrl="${cbasePath}group/groupList?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
		</div>
	</div>
	<script>
	</script>
</body>
</html>

