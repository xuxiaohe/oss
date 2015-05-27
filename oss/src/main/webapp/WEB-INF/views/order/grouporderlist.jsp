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
<link href="<%=contextPath%>/resources/assets/css/bootstrap-datepicker.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/font.css" rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
				<input type="hidden" id="gid">
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
					<h4>群组的订单总金额为 ${orderlist.data.result.countprise} 元</h4><br>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>课程名</th>
								<th>群组名</th>
								<th>作者</th>
								<th>价格</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${orderlist.status==200}">
								<c:forEach items="${orderlist.data.result.orderlist}" var="Recourse" varStatus="key">
									<c:if test="${key.count<=100}">
										<tr>
											<td>${Recourse.courseTitle}</td>
											<td>${Recourse.groupName}</td>
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
		<!-- Modal -->
		<!-- Modal -->
		<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">用户搜索</h4>
					</div>
					<div class="modal-body" id="modalHtml"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="javascript:searchOrder();">确定</button>
						<!-- <button type="button" class="btn btn-primary">确定</button> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#selectBtn").html("选择群组").show();
			
		});
		/**
		加载群组列表
		*/
		function loadContents(){
			$("#modalHtml").load('${cbasePath}group/selectgroup');
		}
		
		/*
		确认选择用户
		*/
		function searchOrder(){
			var url = '${cbasePath}order/orderBygroupid?gid=' + $("#gid").val();
			window.location.href = url;
		}
		
		/**
		选择用户
		*/
		function selectGroup(gid, gname){
			$("#gid").val(gid);
		}
	</script>
</body>
</html>

