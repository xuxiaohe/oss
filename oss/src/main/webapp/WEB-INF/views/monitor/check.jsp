<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%
	String contextPath = request.getContextPath();
%>
<html>
	<head lang="en">
	<meta charset="UTF-8">
	<title>系统运行详情</title>
	<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
	<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
	<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
		rel="stylesheet">
	<link href="<%=contextPath%>/resources/assets/css/font.css"
		rel="stylesheet">
	</head>
	<body>
		<div class="container-fluid">
			<ol class="breadcrumb">
				<li class="active"><a href="#">系统运行详情</a> <small> </small>
				</li>
			</ol>
			<div class="panel panel-default">
				<div class="panel-body">
					<h5>每30秒刷新一次系统状态, 下一次刷新时间:&nbsp;&nbsp;<span style="color:green;" id="nextTime"></span></h5>
					<h4>纸条后台状态:
						<c:if test="${ztiao.status=='UP'}"><span style="color:green;">正常</span></c:if>
						<c:if test="${ztiao.status=='DOWN'}"><span style="color:red;">异常</span></c:if></h4>
					<table class="table  table-hover">
						<thead>
							<tr>
								<th>服务名</th>
								<th>版本</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Redis</td>
								<td>${ztiao.redis.version}</td>
								<td>
									<c:if test="${ztiao.redis.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${ztiao.redis.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>Rabbit MQ</td>
								<td>${ztiao.rabbit.version}</td>
								<td>
									<c:if test="${ztiao.rabbit.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${ztiao.rabbit.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>MongoDB</td>
								<td>${ztiao.mongo.version}</td>
								<td>
									<c:if test="${ztiao.mongo.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${ztiao.mongo.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
						</tbody>
					</table>
					<br>
					
					<h4>订单服务状态:
						<c:if test="${order.status=='UP'}"><span style="color:green;">正常</span></c:if>
						<c:if test="${order.status=='DOWN'}"><span style="color:red;">异常</span></c:if></h4>
					<table class="table  table-hover">
						<thead>
							<tr>
								<th>服务名</th>
								<th>版本</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Redis</td>
								<td>${order.redis.version}</td>
								<td>
									<c:if test="${order.redis.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${order.redis.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>Rabbit MQ</td>
								<td>${order.rabbit.version}</td>
								<td>
									<c:if test="${order.rabbit.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${order.rabbit.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>MongoDB</td>
								<td>${order.mongo.version}</td>
								<td>
									<c:if test="${order.mongo.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${order.mongo.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
						</tbody>
					</table>
					
					<br/>
					<h4>标签服务状态:
						<c:if test="${tag.status=='UP'}"><span style="color:green;">正常</span></c:if>
						<c:if test="${tag.status=='DOWN'}"><span style="color:red;">异常</span></c:if></h4>
					<table class="table  table-hover">
						<thead>
							<tr>
								<th>服务名</th>
								<th>版本</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Redis</td>
								<td>${tag.redis.version}</td>
								<td>
									<c:if test="${tag.redis.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${tag.redis.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>Rabbit MQ</td>
								<td>${tag.rabbit.version}</td>
								<td>
									<c:if test="${tag.rabbit.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${tag.rabbit.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>MongoDB</td>
								<td>${tag.mongo.version}</td>
								<td>
									<c:if test="${tag.mongo.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${tag.mongo.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
						</tbody>
					</table>
					
					<br/>
					<h4>微课服务状态:
						<c:if test="${wiker.status=='DOWN'}"><span style="color:green;">正常</span></c:if>
						<c:if test="${wiker.status=='UP'}"><span style="color:red;">异常</span></c:if></h4>
					<table class="table  table-hover">
						<thead>
							<tr>
								<th>服务名</th>
								<th>版本</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Redis</td>
								<td>${wiker.redis.version}</td>
								<td>
									<c:if test="${wiker.redis.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${wiker.redis.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>Rabbit MQ</td>
								<td>${wiker.rabbit.version}</td>
								<td>
									<c:if test="${wiker.rabbit.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${wiker.rabbit.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>MongoDB</td>
								<td>${wiker.mongo.version}</td>
								<td>
									<c:if test="${wiker.mongo.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${wiker.mongo.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
						</tbody>
					</table>
					
					<br/>
					<h4>轻单服务状态:
						<c:if test="${qdan.status=='DOWN'}"><span style="color:green;">正常</span></c:if>
						<c:if test="${qdan.status=='UP'}"><span style="color:red;">异常</span></c:if></h4>
					<table class="table  table-hover">
						<thead>
							<tr>
								<th>服务名</th>
								<th>版本</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Redis</td>
								<td>${qdan.redis.version}</td>
								<td>
									<c:if test="${qdan.redis.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${qdan.redis.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>Rabbit MQ</td>
								<td>${qdan.rabbit.version}</td>
								<td>
									<c:if test="${qdan.rabbit.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${qdan.rabbit.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
							<tr>
								<td>MongoDB</td>
								<td>${qdan.mongo.version}</td>
								<td>
									<c:if test="${qdan.mongo.status=='UP'}"><p class="text-success">正常</p></c:if>
									<c:if test="${qdan.mongo.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
								</td>
							</tr>
						</tbody>
					</table>	
				
					<%-- <div class="row">
						<div class="col-xs-4"><p class="text-primary">纸条后台运行状态：</p>
						</div>
						<div class="col-xs-4">
							<c:if test="${system.status=='UP'}"><p class="text-success">正常</p></c:if>
							<c:if test="${system.status=='DOWN'}"><p class="text-warning">异常</p></c:if>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-4"><p class="text-primary">Redis运行状态：</p></div>
					</div>
					<div class="row">
						<div class="col-xs-4"><p class="text-primary">Rabbit运行状态：</p></div>
					</div>
					<div class="row">
						<div class="col-xs-4"><p class="text-primary">Mongo运行状态：</p></div>
					</div> --%>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				var datetime = new Date();
				var time = datetime.getTime() + 30 * 1000;
				datetime.setTime(time);
				var year = datetime.getFullYear();
				var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
				var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
				var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
				var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
				var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
				var text = year + "-" + month + "-" + date+" "+hour+" : "+minute+" : "+second;
				  
				$("#nextTime").html(text);
				setTimeout(function(){
					console.log('刷新页面');
					window.location.href='<%=contextPath%>/checkjobs/view';
				}, 1000 * 30);
				
			});
		</script>
	</body>
</html>