<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
	<head>
	<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
	<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
	<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
		rel="stylesheet">
	<link href="<%=contextPath%>/resources/assets/css/font.css"
		rel="stylesheet">
	<link href="<%=contextPath%>/resources/assets/css/jquery.shCircleLoader.css"
		rel="stylesheet">
	</head>
	<body>
		<div class="container-fluid">
			<jsp:include page="header.jsp"></jsp:include>
			<ol class="breadcrumb">
				<li><a
					href="<%=contextPath%>/subject/getBoxPostByType?type=contentspecial">专题列表</a></li>
				<li class="active">专题详情<small> </small>
				</li>
			</ol>
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-2">
							<img class="thumbnail col-xs-12" id="logoUrl" src="${logoUrl}" alt="" />
	
							<hr />
							
							<c:if test="${specialDetail.type=='activityspecial'}">
								<a href="#" onclick="javascript:showPreview('${specialDetail.type}', '${specialDetail.id}', '${logoUrl}');"><button class="btn btn-success btn-block">
									预览页面</button></a>
								<a href="#" onclick="javascript:createH5File('${specialDetail.type}', '${specialDetail.id}', '${logoUrl}', '${specialDetail.h5Url}');"><button class="btn btn-success btn-block">
									生成静态页面</button></a>
								<a href="#" onclick="javascript:showActivities();"><button class="btn btn-success btn-block"
										data-toggle="modal" data-target="#myModal">添加活动</button></a>
							</c:if>
							<c:if test="${specialDetail.type=='contentspecial'}">
								<a href="#" onclick="javascript:showPreview('${specialDetail.type}', '${specialDetail.id}', '${logoUrl}');"><button class="btn btn-success btn-block">
									预览页面</button></a>
								<a href="#" onclick="javascript:createH5File('${specialDetail.type}', '${specialDetail.id}', '${logoUrl}', '${specialDetail.h5Url}');"><button class="btn btn-success btn-block">
									生成静态页面</button></a>
								<a href="#" onclick="javascript:showCourse();"><button class="btn btn-success btn-block"
										data-toggle="modal" data-target="#myModal">添加课程</button></a>
								<a href="#" onclick="javascript:showTopic();"><button class="btn btn-success btn-block"
										data-toggle="modal" data-target="#myModal">添加话题</button></a>
								<a href="#" onclick="javascript:showDry();"><button class="btn btn-success btn-block"
										data-toggle="modal" data-target="#myModal">添加干货</button></a>
							</c:if>
	
						</div>
						<div id="userInfoDiv" class="col-xs-10" style="">
							<div id="circleLoader" style="left:300px;top:100px;position:absolute;  z-index:99999;"></div>
							<h4 style="margin-left: 12px;">
								<span id="specialName">专题名称:${specialDetail.chinaName}</span><small><small class="pull-right">创建时间：<Date:date
											value="${specialDetail.ctime}"></Date:date></small></small>
							</h4>
							<div class="col-xs-6">
								专题类型:
								<c:if test="${specialDetail.type=='activityspecial'}">活动专题</c:if>
								<c:if test="${specialDetail.type=='contentspecial'}">精选专题</c:if>
							</div>
							<br /><br />
							<div class="col-xs-12">
							<h5>专题内容:</h5>
							</div>
							<div class="col-xs-12">
								<c:if test="${specialDetail.type=='activityspecial'}">
									<table class="table table-striped table-hover">
										<thead>
											<tr>
												<th>活动名称</th>
												<th>面值</th>
												<th>红包数量</th>
												<th>课程名称</th>
												<th>课程价格</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${specialInfo.data.result}" var="Recourse">
												<tr>
													<td>${Recourse.activityName}</td>
													<td>${Recourse.quota}</td>
													<td>${Recourse.num}</td>
													<td>${Recourse.course.title}</td>
													<td>${Recourse.course.price}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
								<c:if test="${specialDetail.type=='contentspecial'}">
									<table class="table table-striped table-hover">
										<thead>
											<tr>
												<th>标题</th>
												<th>类型</th>
												<th>群组</th>
												<th>发表时间</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${specialInfo.data.result}" var="Recourse">
												<tr>
													<td>${fn:substring(Recourse.title, 0, 15)}</td>
													<td><c:if test="${Recourse.type=='21'}">干货</c:if>
														<c:if test="${Recourse.type=='01'}">话题</c:if>
														<c:if test="${Recourse.type=='11'}">课程</c:if>
													</td>
													<td>${Recourse.groupName}</td>
													<td><Date:date value="${Recourse.ctime}"></Date:date></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">课程搜索</h4>
					</div>
					<div class="modal-body" id="modalHtml"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="javascript:closeDialog();">确定</button>
						<!-- <button type="button" class="btn btn-primary">确定</button> -->
					</div>
				</div>
			</div>
		</div>
		
		<script src="<%=contextPath%>/resources/assets/js/jquery.shCircleLoader-min.js"></script>
		<script type="text/javascript">
			//显示预览页面
			function showPreview(type, boxId, logoUrl){
				var url = '<%=contextPath%>/subject/showPreview?type=' + type + '&boxId=' + boxId + '&logoUrl=' + logoUrl;
				window.open(url, '预览', 'width=400');
			}
		
			function createH5File(type, boxId, logoUrl, h5Url){
				$("#circleLoader").shCircleLoader();
				var url = '<%=contextPath%>/subject/createH5File?type=' + type + '&boxId=' + boxId + '&logoUrl=' + logoUrl + '&h5Url=' + h5Url;
				$.ajax({
					url : url,
					type : 'post',
					success : function(data){
						$("#circleLoader").shCircleLoader('destroy');
						if(data=='success') alert('生成成功');
					}
				});
			}
		
			function showActivities(){//加载活动列表
				$("#myModalLabel").html('活动选择');
				var url = '<%=contextPath%>/subject/findByothers?pageType=selectActivity&dataType=activityspecial&boxPostId=${specialDetail.id}&ctime=${specialDetail.ctime}&logoUrl=${logoUrl}';
				$("#modalHtml").load(url);
			}
		
			function showCourse(){//加载课程列表
				$("#myModalLabel").html('课程选择');
				var url = '<%=contextPath%>/subject/findByothers?pageType=selectCourse&dataType=course&boxPostId=${specialDetail.id}&ctime=${specialDetail.ctime}&categoryId=${categoryId}&logoUrl=${logoUrl}';
				$("#modalHtml").load(url);
			}
			function showTopic(){//加载话题列表
				$("#myModalLabel").html('话题选择');
				var url = '<%=contextPath%>/subject/findByothers?pageType=selectTopic&dataType=topic&boxPostId=${specialDetail.id}&ctime=${specialDetail.ctime}&categoryId=${categoryId}&logoUrl=${logoUrl}';
				$("#modalHtml").load(url);
			}
			function showDry(){//加载干货列表
				$("#myModalLabel").html('干货选择');
				var url = '<%=contextPath%>/subject/findByothers?pageType=selectDry&dataType=dry&boxPostId=${specialDetail.id}&ctime=${specialDetail.ctime}&categoryId=${categoryId}&logoUrl=${logoUrl}';
				$("#modalHtml").load(url);
			}
		</script>
</body>
</html>