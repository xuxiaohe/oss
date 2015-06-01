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
<title>订单管理</title>
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
				<c:if test="${orderlist.status == '200'}">
					<nav> <!-- 分页开始 -->
					<%-- <ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${orderlist.data.curr_page}"
							totalPages="${orderlist.data.page_rows}" perPageRows="10"
							totalRows="${orderlist.data.total_rows}"
							linkBaseUrl="${cbasePath}order/findOrder?">
						</pageNation:PageNation>
					</ul> --%>

					<!-- 分页结束 --> </nav>
					<!---数据显示区域-->
					<h4>订单总金额: ${orderlist.data.result.countprise} 元</h4><br>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>序号</th>
								<th>课程名</th>
								<th>用户名</th>
								<th>作者</th>
								<th>价格</th>
							</tr>
						</thead>
						<tbody id="tableContent">
							<c:if test="${orderlist.status==200}">
								<c:forEach items="${orderlist.data.result.orderlist}" var="Recourse" varStatus="key">
									<c:if test="${key.count<=100}">
										<tr>
											<td>${key.count}</td>
											<td>${Recourse.courseTitle}</td>
											<td>${Recourse.userNickName}</td>
											<td>${Recourse.courseOwerNickName}</td>
											<td>${Recourse.orderPrice}元</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
					<nav> <!-- 分页开始 -->
					<%-- <ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${orderlist.data.curr_page}"
							totalPages="${orderlist.data.page_rows}" perPageRows="10"
							totalRows="${orderlist.data.total_rows}"
							linkBaseUrl="${cbasePath}/order/findOrder?">
						</pageNation:PageNation>
					</ul> --%>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
			
		</div>
	</div>
	<script type="text/javascript">
	$(function(){
		$("#selectBtn").hide();	
	});
	</script>
</body>
</html>

