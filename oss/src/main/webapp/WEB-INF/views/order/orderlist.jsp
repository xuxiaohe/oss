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
		<div class="panel panel-default">
			<div class="panel-body">
				<form class="form-inline" action="<%=contextPath%>/order/findOrder" onsubmit="javascript:beforeSubmit();"
					method="post" >
					<div class="form-group">
						
						<div class="col-xs-12">订单状态:
							<select class="form-control" id="statusSelect" name="orderStatus" >
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
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="form-control">查询</button>
						</div>
						<br><br>
						<div class="col-xs-6">
							<label class="control-label" for="starttime">开始时间：</label>
							<input type="hidden" name="starttime" id="startTime" value="${starttime}"/>
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
						
					</div>
					<br>
				</form>
				<c:if test="${orderlist.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${orderlist.data.curr_page}"
							totalPages="${orderlist.data.page_rows}" perPageRows="10"
							totalRows="${orderlist.data.total_rows}"
							linkBaseUrl="${cbasePath}order/findOrder?">
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
											<c:when test="${Recourse.orderStatus=='2'}">支付成功,未评价</c:when>
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
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${orderlist.data.curr_page}"
							totalPages="${orderlist.data.page_rows}" perPageRows="10"
							totalRows="${orderlist.data.total_rows}"
							linkBaseUrl="${cbasePath}/order/findOrder?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
			
		</div>
	</div>
	<script type="text/javascript" src="<%=contextPath%>/resources/assets/js/bootstrap-datepicker.min.js"></script>
	<script>
		Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
	
	
		var today = new Date().valueOf();
		var start = 0;
		var end = 0;
		var param = '';
		$(function(){
			
			$('#starttime').datepicker({
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
				if(end > today){
					alert('结束日期不能大于今天!');
					$("#endtime").val("");
					$("#endtime").focus();
				}else if (end < start) {
					alert('结束日期不能早于开始日期!');
					$("#endtime").val("");
					$("#endtime").focus();
				} else {
					endTime = endTime + (60 * 60 * 24 - 1) * 1000;
					$("#endTime").val(endTime);
				}
			});
			
			//以下为补齐分页参数
			var status = '${orderStatus}';
			var startTime = '${starttime}';
			var endTime = '${endtime}';
			if (status != '' && status != null) {
				$("#statusSelect").val(status);
				param = param + '&orderStatus=' + status;
			}else{
				param = param + '&orderStatus=';
			}
			if(startTime == '' || startTime == null){
				startTime = 0;
			}
			param = param + '&starttime=' + startTime;
			if(endTime == '' || endTime == null){
				endTime = 0;
			}
			param = param + '&endtime=' + endTime;
			if(param != ''){
				$("#pagination li  a").each(function(index) {
					if ($(this).parent().hasClass("disabled") == false) {
						var url = $.trim($(this).attr("href"));
						url = url + param;
						$(this).attr('href', url);
					}
				});
			}
			//补齐分页参数 end
		});
		function beforeSubmit(){
			if($("#startTime").val() == ''){
				$("#startTime").val("0");
			}
			if($("#endTime").val() == ''){
				$("#endTime").val("0");
			}
		}
	</script>
</body>
</html>

