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
				
				<c:if test="${users.status == '200'}">
						<div class="col-xs-12">
				<hr />
				<table class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>报名人</th>
							<th>电话</th>
							<th>公司</th>
							<th>职位</th>
							<th>报名时间</th>
						</tr>
					</thead>
					<tbody>
					<!---数据显示区域-->
				

					<c:forEach items="${users.data.result}" varStatus="key" var="Recourse">
						<tr>
							<td>${Recourse.name }</td>
							<td>${Recourse.phone }</td>
							<td>${Recourse.company }</td>
							<td>${Recourse.job }</td>
							<td><Date:date value="${Recourse.ctime}"></Date:date></td>
						</tr>
					</c:forEach>
						
					</tbody>
				</table>
			</div>
					
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${users.data.curr_page}"
							totalPages="${users.data.page_rows}" perPageRows="10"
							totalRows="${users.data.total_rows}"
							linkBaseUrl="${cbasePath}activity/bmList">
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

