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
				<input type="hidden" id="uid">
				<c:if test="${couponList.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${couponList.data.curr_page}"
							totalPages="${couponList.data.page_rows}" perPageRows="10"
							totalRows="${couponList.data.total_rows}"
							linkBaseUrl="${cbasePath}coupon/couponList?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
					<!---数据显示区域-->
					
					<table class="table table-hover">
						<thead>
							<tr>
								<th>卡号</th>
								<th>面值</th>
								<th>领券人</th>
								<th>状态</th>
								<th>生成时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${couponList.data.result}" var="Recourse">
								<tr>
									<td><a href="<%=contextPath%>/coupon/couponDetail?id=${Recourse.id}">${Recourse.cardCode}</a></td>
									<td>${Recourse.quota}</td>
									<td></td>
									<td>
										<c:choose>
											<c:when test="${Recourse.status==0}">未领取</c:when>
											<c:when test="${Recourse.status==1}">已领取</c:when>
											<c:when test="${Recourse.status==2}">已使用</c:when>
											<c:when test="${Recourse.status==3}">已作废</c:when>
										</c:choose>
									</td>
									<td><Date:date value="${Recourse.ctime}"></Date:date></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${couponList.data.curr_page}"
							totalPages="${couponList.data.page_rows}" perPageRows="10"
							totalRows="${couponList.data.total_rows}"
							linkBaseUrl="${cbasePath}coupon/couponList?">
						</pageNation:PageNation>
					</ul>

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
			/*if($.trim($("#tableContent").html()) == ''){
				$("#selectBtn").click();
			}*/
		});
		/**
		加载用户列表
		*/
		function loadContents(){
			$("#modalHtml").load('${cbasePath}course/selectUser');
		}
		
		/*
		确认选择用户
		*/
		function searchOrder(){
			var url = '${cbasePath}coupon/list?userId=' + $("#uid").val();
			window.location.href = url;
		}
		
		/**
		选择用户
		*/
		function selectUser(userId, userName, logoUrl){
			$("#uid").val(userId);
		}
	</script>
</body>
</html>

