<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>用户管理</title>
<script src="http://oss.ztiao.cn/oss/resources/assets/js/jquery.min.js"></script>
<script
	src="http://oss.ztiao.cn/oss/resources/assets/js/bootstrap.min.js"></script>
<link
	href="http://oss.ztiao.cn/oss/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="http://oss.ztiao.cn/oss/resources/assets/css/font.css"
	rel="stylesheet">

<style>
#ContentDiv div {
	margin-top: 10px;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<ol class="breadcrumb">
			<li><a href="#">课程管理</a></li>
			<li><a href="${cbasePath}course/courseList">课程列表</a></li>
			<li class="active">课程详情 <small> </small>
			</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12 "
							src="${courseDetail.data.result.logoUrl }" alt="" />
						<%-- <a
							href="${cbasePath}dry/editForm?dryid=${courseDetail.data.result.id }">
							<button type="button" class="btn btn-warning btn-block">审核课程</button>
						</a> --%>
						<!-- <button type="button" class="btn btn-warning btn-block">删除</button> -->
						<hr />
						<a
							href="${cbasePath}course/updateView?cid=${courseDetail.data.result.id}"
							type="button" class="btn btn-primary">编辑信息 </a> <a
							href="${cbasePath}course/updateChapterView?cid=${courseDetail.data.result.id}"
							type="button" class="btn btn-primary">编辑章节 </a><br>
						
						
					</div>
					<div id="ContentDiv" class="col-xs-10" style="">
						<h4 class="col-xs-12">
							课程信息：${courseDetail.data.result.title} <small><small
								class="pull-right">注册时间：<Date:date
										value="${courseDetail.data.result.ctime}"></Date:date></small></small>
						</h4>
						<div class="col-xs-12">简介:${courseDetail.data.result.intro}</div>
						<div class="col-xs-12">创建者:${courseDetail.data.result.createUserName}</div>
						<div class="col-xs-12">
							收费模式:
							<c:choose>
								<c:when test="${courseDetail.data.result.pricemodel=='0'}">免费</c:when>
								<c:when test="${courseDetail.data.result.pricemodel=='1'}">付费</c:when>
								<c:when test="${courseDetail.data.result.pricemodel=='2'}">打赏</c:when>
							</c:choose>
						</div>

						<div class="col-xs-12">价格: ${courseDetail.data.result.price}
						</div>

						<div class="col-xs-12">
							是否审核通过:
							<c:if test="${courseDetail.data.result.checked}">是</c:if>
							<c:if test="${!courseDetail.data.result.checked}">否</c:if>
						</div>



					</div>

				</div>

			</div>
		</div>
		<div class="row">
			<div class="panel-body">
				<ul id="myTabs" class="nav nav-tabs">
					<li role="presentation" class="active"><a href="#home">章节管理</a></li>
					<li role="presentation"><a href="#profile" id="BuyUserListButton">购买用户列表</a></li>
					<li role="presentation"><a href="#progressTab" id="progressButton">学习进度报告</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div role="tabpanel" class="tab-pane fade active in" id="home"
						aria-labelledby="home-tab">
						<ul class="list-group" id="chapterList">
						<c:forEach items="${courseDetail.data.result.chapters}"
							varStatus="key" var="Recourse">
							<c:forEach items="${Recourse.lessons}" varStatus="key"
								var="Lesson">
								<li class="list-group-item">
									<span class="col-xs-5">
										<span class="text-danger">[<c:if
											test="${Lesson.knowledge.ccode==0}">转码成功</c:if> <c:if
											test="${Lesson.knowledge.ccode==1}">等待转码</c:if> <c:if
											test="${Lesson.knowledge.ccode==2}">正在转码</c:if> <c:if
											test="${Lesson.knowledge.ccode==3}">转码失败</c:if>]
								</span>
										<a 
										href="${cbasePath}player/index?id=${Lesson.knowledge.id}"
										target="_blank">${Lesson.title}</a></span> 
									<span class="col-xs-2 pull-right text-right"><small><Date:date value="${Lesson.ctime}"></Date:date></small></span> 
									 <span class="badge" id="msg${Lesson.id}"> <c:if
											test="${Lesson.status==2}">审核通过</c:if> <c:if
											test="${Lesson.status==1}">未审核</c:if> <c:if
											test="${Lesson.status==3}">审核未通过</c:if>
								</span>  <select id="${Lesson.id}">
										<c:choose>
											<c:when test="${Lesson.knowledge.status==2}">
												<option value="0">不通过</option>
												<option value="1" selected="selected">通过</option>
											</c:when>

											<c:otherwise>
												<option value="0" selected="selected">不通过</option>
												<option value="1">通过</option>
											</c:otherwise>
										</c:choose>
								</select>
									<button type="button" class="deleteBtn btn btn-default"
										onclick="checkLesson('${Lesson.id}','${Lesson.knowledge.id}')">审核课时</button>

								</li>
							</c:forEach>
						</c:forEach>
					</ul>
					</div>
					<div role="tabpanel" class="tab-pane fade" id="profile"
						aria-labelledby="profile-tab">
						<div id="buyusers" class="container-fluid"></div>
					</div>
					
					<div role="tabpanel" class="tab-pane fade" id="progressTab"
						aria-labelledby="progressTab">
						<div id="progressDetail" class="container-fluid"></div>
					</div>

				</div>
				
			</div>
		</div>
	</div>
	<script>
		$(function() {
			var BuyUserListisLoaded = false;
			var ProgressisLoaded = false;
			$('#myTabs a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			});
			$("#BuyUserListButton").click(function(){
				if(BuyUserListisLoaded == false){
					var url = '${cbasePath}course/buyusers?cid=${courseDetail.data.result.id}';
					$("#buyusers").html("").load(url);
					BuyUserListisLoaded = true;
				}
			});
			$("#progressButton").click(function(){
				if(ProgressisLoaded == false){
					var url = '${cbasePath}course/progress?cid=${courseDetail.data.result.id}';
					$("#progressDetail").html("").load(url);
					ProgressisLoaded = true;
				}
			});
		});

		function checkLesson(lessonId, knowledge) {
			var status = $("#" + lessonId).val();
			$.post("${cbasePath}course/checkLesson", {
				"lessonId" : lessonId,
				"knowledgeId" : knowledge,
				"status" : status
			}, function(data) {
				alert("审核成功");
				if (status == 1) {
					$("#msg" + lessonId).html("审核通过");
				} else {
					$("#msg" + lessonId).html("审核不通过");
				}
			});
		}

		
	</script>
</body>
</html>

