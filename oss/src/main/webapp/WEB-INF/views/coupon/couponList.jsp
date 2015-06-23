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
<link href="<%=contextPath%>/resources/assets/css/bootstrap-datepicker.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/font.css" rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
				<ol class="breadcrumb">
				<li><a href="#">红包列表</a></li>
				<li><a href="<%=contextPath%>/coupon/activityList">返回活动列表</a></li>
				</ol>
				<nav>
					<h5>活动名称:${adetail.data.result.Activity.activityName}, 
					&nbsp;&nbsp;&nbsp;&nbsp;红包数量:<a href="${cbasePath}couponListByActivity?aid=${aid}">${adetail.data.result.Activity.num}</a>, 
					&nbsp;&nbsp;&nbsp;&nbsp;已领取但未使用:<a href="${cbasePath}couponListByActivity?aid=${aid}&status=1">${adetail.data.result.linknumber}</a>, 
					&nbsp;&nbsp;&nbsp;&nbsp;已使用:<a href="${cbasePath}couponListByActivity?aid=${aid}&status=2">${adetail.data.result.usenumber}</a>, 
					&nbsp;&nbsp;&nbsp;&nbsp;未使用:<a href="${cbasePath}couponListByActivity?aid=${aid}&status=0">${adetail.data.result.nousenumber}</a>, 
					&nbsp;&nbsp;&nbsp;&nbsp;已删除:<a href="${cbasePath}couponListByActivity?aid=${aid}&status=3">${adetail.data.result.deletenumber}</a>, 
					&nbsp;&nbsp;&nbsp;&nbsp;已锁定:<a href="${cbasePath}couponListByActivity?aid=${aid}&status=4">${adetail.data.result.locknumber}</a></h5>
				</nav>
				<%-- <form class="form-inline" action="<%=contextPath%>/coupon/findBycoupon" onsubmit="javascript:beforeSubmit();"
					method="post" >
						
						<div class="col-xs-10">红包状态:
							<select class="form-control" id="statusSelect" name="status" >
								<option value="">全部红包</option>
								<option value="0">未领取</option>
								<option value="1">已领取</option>
								<option value="2">已使用</option>
								<option value="3">已作废</option>
								<option value="4">已锁定</option>
							</select>
						</div>
						<div class="col-xs-2">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="form-control">查询</button>
						</div>
						<br><br>
						<div class="col-xs-6">
							<label class="control-label" for="starttime">开始时间：</label>
							<input type="hidden" name="startime" id="startTime" value="${starttime}"/>
							<div class="date form_datetime controls" 
								id="starttimeDiv">
								<input type="text" class="form-control" value="${sStartTime}" id="starttime"
									readonly> <span class="add-on"> <i
									class="icon-th"></i>
								</span>
							</div>
						</div>
						<div class="col-xs-6">
							<label class="control-label" for="endtime">结束时间：</label>
							<input type="hidden" name="endtime" id="endTime" value="${endtime}"/>
							<div class=" controls date form_datetime" 
								id="endtimeDiv">
								<input type="text" class="form-control" value="${sEndTime}" id="endtime" readonly>
								<span class="add-on"><i class="icon-th"></i> </span>
							</div>

						</div>
						
					<br>
				</form> --%>
				<input type="hidden" id="uid">
				<c:if test="${couponList.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${couponList.data.curr_page}"
							totalPages="${couponList.data.page_rows}" perPageRows="10"
							totalRows="${couponList.data.total_rows}"
							linkBaseUrl="${cbasePath}couponListByActivity?aid=${aid}&status=${status}">
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
									<td><a href="<%=contextPath%>/coupon/couponDetail?id=${Recourse.id}&aid=${aid}">${Recourse.cardCode}</a></td>
									<td>${Recourse.quota}</td>
									<td>${Recourse.userName}</td>
									<td>
										<c:choose>
											<c:when test="${Recourse.status==0}">未领取</c:when>
											<c:when test="${Recourse.status==1}">已领取</c:when>
											<c:when test="${Recourse.status==2}">已使用</c:when>
											<c:when test="${Recourse.status==3}">已作废</c:when>
											<c:when test="${Recourse.status==4}">已锁定</c:when>
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
							linkBaseUrl="${cbasePath}couponListByActivity?aid=${aid}&status=${status}">
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
	<script type="text/javascript" src="<%=contextPath%>/resources/assets/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript">
		//var today = new Date().valueOf();
		//var start = 0;
		//var end = 0;
		$(function(){
			/*$('#starttime').datepicker({
				format : 'yyyy-mm-dd',
				autoclose : true,
				todayHighlight:true,
				todayBtn : 'linked',
				language : 'zh-CN'
			}).on('changeDate', function(ev) {
				var startTime = ev.date.valueOf();
				start = startTime;
				if (startTime > today) {
					alert('开始日期不能大于今天!');
					$("#starttime").val("");
					$("#starttime").focus();
				}else{
					$("#startTime").val(startTime);
				}
			});
			$('#endtime').datepicker({
				format : 'yyyy-mm-dd',
				autoclose : true,
				todayHighlight:true,
				todayBtn : 'linked',
				language : 'zh-CN'
			}).on('changeDate', function(ev) {
				var endTime = ev.date.valueOf();
				end = endTime;
				if (end < start) {
					alert('结束日期不能早于开始日期!');
					$("#endtime").val("");
					$("#endtime").focus();
				} else {
					endTime = endTime + (60 * 60 * 24 - 1) * 1000;
					$("#endTime").val(endTime);
				}
			});
		});*/
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

