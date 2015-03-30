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
<title>用户管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
			<form class="form-inline" action="${cbasePath}activity/activityPage"
					method="get" role="form">
					<div class="form-group">
						<label class="sr-only" for="keyword">Search:</label> <input
							class="form-control" id="keywords" name="keywords"
							placeholder="Enter keyword" value="${keywords}">
					</div>

					<button id="searchIt" type="submit" class="btn btn-default">Search
						it!</button>
				</form> 
				<c:if test="${activityList.status == '200'}">
						<div class="col-xs-12">
				<hr />
				<table class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>活动名称</th>
							<th>活动时间</th>
							<th>主办方</th>
							<th>价格</th>
							<th>报名上限</th>
							<th>已报名人数</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					<!---数据显示区域-->
				

					<c:forEach items="${activityList.data.result}" varStatus="key" var="Recourse">
						<tr>
							<td><a href="${cbasePath}activity/detail?id=${Recourse.id }">${Recourse.name }</a></td>
							<td><Date:date value="${Recourse.activityStartTime}"></Date:date>到<Date:date value="${Recourse.activityEndTime}"></Date:date></td>
							<td>${Recourse.company }</td>
							<td>${Recourse.price }</td>
							<td>${Recourse.maxCount }</td>
							<td>${Recourse.registrationCount }</td>
							<td><button class="detailBtn" data="${Recourse.id }">编辑</button>
								<a style="float: right;" class="btn btn-primary" href="${cbasePath}activity/bmList?id=${Recourse.id}" >报名详情</a>
							</td>
						</tr>
					</c:forEach>
						
					</tbody>
				</table>
			</div>
					
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${activityList.data.curr_page}"
							totalPages="${activityList.data.page_rows}" perPageRows="10"
							totalRows="${activityList.data.total_rows}"
							linkBaseUrl="${cbasePath}activity/activityPage?keywords=${keywords }">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			
			
			$(".detailBtn").click(function(){
				window.location.href="${cbasePath}activity/editView?id="+$(this).attr("data");
			});
		});
	</script>
</body>
</html>

