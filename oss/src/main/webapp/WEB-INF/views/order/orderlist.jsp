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
				<form class="form-inline" 
					method="get" >
					<div class="form-group">
						
						<div class="col-xs-6"><label class="sr-only" for="keyword">订单状态:</label>
							<select class="form-control" id="statusSelect" onchange = "javascript:findByStatus(this);">
								<option value="">全部订单</option>
								<option value="0">未支付</option>
								<option value="1">支付中</option>
								<option value="2">支付成功, 未评价</option>
								<option value="3">支付失败</option>
								<option value="4">支付超时</option>
								<option value="5">支付成功, 已评价</option>
								<option value="6">订单关闭</option>
								<option value="7">订单退款</option>
								<option value="8">订单撤销</option>
							</select>
						</div>
					</div>

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
										${Recourse.courseTitle}<small><small
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
								<div class="col-xs-6">
									<p>订单状态:
										<c:choose>
											<c:when test="${Recourse.orderStatus=='0'}">未支付</c:when>
											<c:when test="${Recourse.orderStatus=='1'}">支付中</c:when>
											<c:when test="${Recourse.orderStatus=='2'}">支付成功,未成功</c:when>
											<c:when test="${Recourse.orderStatus=='3'}">支付失败</c:when>
											<c:when test="${Recourse.orderStatus=='4'}">支付超时</c:when>
											<c:when test="${Recourse.orderStatus=='5'}">支付成功,已评价</c:when>
											<c:when test="${Recourse.orderStatus=='6'}">订单关闭</c:when>
											<c:when test="${Recourse.orderStatus=='7'}">订单退款</c:when>
											<c:when test="${Recourse.orderStatus=='8'}">订单撤销</c:when>
										</c:choose>
									</p>
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
		$(function(){
			var status = '${orderStatus}';
			if(status != '') $("#statusSelect").val(status);
		});
		function findByStatus(obj){
			var status = $.trim($(obj).val());
			if(status != ''){
				var url = '${cbasePath}order/list?orderStatus=' + status;
				window.location.href = url;
			}else{
				var url = '${cbasePath}order/list';
				window.location.href = url;
			}
			
		}
	</script>
</body>
</html>

