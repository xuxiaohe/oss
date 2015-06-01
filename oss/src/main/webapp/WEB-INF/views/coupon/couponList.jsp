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
				<c:if test="${couponlist.status == '200'}">
					<nav> <!-- 分页开始 -->
					<%-- <ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${couponlist.data.curr_page}"
							totalPages="${couponlist.data.page_rows}" perPageRows="10"
							totalRows="${couponlist.data.total_rows}"
							linkBaseUrl="${cbasePath}order/findOrder?">
						</pageNation:PageNation>
					</ul> --%>

					<!-- 分页结束 --> </nav>
					<!---数据显示区域-->
					
					<c:forEach items="${couponlist.data.result}" varStatus="key" var="Recourse">
						<div class="row" style="padding: 20px;">
							<div class="col-xs-1">
								<h5 style="margin-top:40px;"><span class="label label-default">${key.count}</span></h5>
							</div>
							<div class="col-xs-10">
								<h4 style="margin-left:12px;">
										活动名称:${Recourse.activityName}<small><small
										class="pull-right">使用时间：<Date:date
												value="${Recourse.useTime}"></Date:date></small></small>
								</h4>
								<div class="col-xs-6">
									<p>优惠券编码：${Recourse.cardCode}</p>
								</div>
								<div class="col-xs-6">
									<p>金额:${Recourse.quota}</p>
								</div>
								<div class="col-xs-6">
									<p>状态：
										<c:choose>
											<c:when test="${Recourse.status=='0'}">未使用</c:when>
											<c:when test="${Recourse.status=='1'}">被抢未使用</c:when>
											<c:when test="${Recourse.status=='2'}">已使用</c:when>
											<c:when test="${Recourse.status=='3'}">作废</c:when>
										</c:choose>
									</p>
								</div>
								<div class="col-xs-6">
									<p>有效期开始时间:<Date:date
												value="${Recourse.expiryDateStart}"></Date:date></p>
								</div>
								<div class="col-xs-6">
									<p>有效期结束时间:<Date:date
												value="${Recourse.expiryDateEnd}"></Date:date>
									</p>
								</div>
								

							</div>
						</div>
					</c:forEach>
					
					<nav> <!-- 分页开始 -->
					<%-- <ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${couponlist.data.curr_page}"
							totalPages="${couponlist.data.page_rows}" perPageRows="10"
							totalRows="${couponlist.data.total_rows}"
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
			$("#selectBtn").html("选择用户").show();
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

