<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
<style>
#userInfoDiv div {
	padding: 10px;
}

#btnGroupDiv button {
	margin: 10px;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<ol class="breadcrumb">
			<li><a href="#">课程管理</a></li>
			<li><a href="${cbasePath}course/courseList">课程列表</a></li>
			<li class="active">课程详情 <small>
				 </small>
			</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12"
							src="${courseDetail.data.result.logoUrl }" alt="" /> <%-- <a
							href="${cbasePath}dry/editForm?dryid=${courseDetail.data.result.id }">
							<button type="button" class="btn btn-warning btn-block">审核课程</button>
						</a> --%>
						<!-- <button type="button" class="btn btn-warning btn-block">删除</button> -->
						<hr />
						<a href="${cbasePath}course/updateView?cid=${courseDetail.data.result.id}">
						<button type="button" class="edit btn-warning btn-block">编辑</button></a><br>
						<a href="${cbasePath}course/updateChapterView?cid=${courseDetail.data.result.id}">
						<button type="button" class="edit btn-warning btn-block">编辑章节</button></a><br>
						<%-- <div id="btnGroupDiv" class="col-xs-12">
							<a
								href="${cbasePath}user/userTopic?userid=${courseDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看话题</button>
							</a> <a
								href="${cbasePath}user/userDry?userid=${courseDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看干货</button>
							</a> <a
								href="${cbasePath}user/userCourse?userid=${courseDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看课程</button>
							</a> <a
								href="${cbasePath}user/userGroup?userid=${courseDetail.data.result.id }"><button
									type="button" class="btn btn-success btn-block">查看群组</button></a>
						</div> --%>
					</div>
					<div id="userInfoDiv" class="row" style="">
						<h4 style="margin-left: 12px;">
							课程信息：${courseDetail.data.result.intro} <small><small
								class="pull-right">注册时间：<Date:date
										value="${courseDetail.data.result.ctime}"></Date:date></small></small>
						</h4>
						<div class="col-xs-6">创建者:${courseDetail.data.result.createUserName}</div>
						<div class="col-xs-6">
							收费模式: <c:choose>
										<c:when test="${courseDetail.data.result.pricemodel=='0'}">免费</c:when>
										<c:when test="${courseDetail.data.result.pricemodel=='1'}">付费</c:when>
										<c:when test="${courseDetail.data.result.pricemodel=='2'}">打赏</c:when>
									</c:choose>
						</div>
						
						<div class="col-xs-6">价格: ${courseDetail.data.result.price}
						</div>
						
						<%-- <div class="col-xs-6">浏览量：${dryDetail.data.result.viewCount}
						</div>
						<div class="col-xs-6">回复数：${dryDetail.data.result.replyCount}</div>
						<div class="col-xs-6">被点赞的次数：${dryDetail.data.result.likesCount}
						</div>
						<div class="col-xs-6">不赞数量：${dryDetail.data.result.unLikeCount}
						</div>
						<div class="col-xs-6">收藏人数统计：${dryDetail.data.result.favCount}
						</div> --%>

					</div>
					<ul class="list-group">
							<c:forEach items="${courseDetail.data.result.chapters}"
								varStatus="key" var="Recourse">
								<c:forEach items="${Recourse.lessons}" varStatus="key"
									var="Lesson">
									<li class="list-group-item">
										<span style="width:200px;float:left;">${Lesson.title}</span>
										<span class="badge">
											 <Date:date value="${Lesson.ctime}"></Date:date>
										</span>
										<span class="badge">
											<a href="${cbasePath}player/index?id=${Lesson.knowledge.id}" target="_blank" style="color:#fff000">查看视频</a> 
										</span>
										<span class="badge" id="msg${Lesson.id}">
											<c:if test="${Lesson.status==2}">审核通过</c:if>
											<c:if test="${Lesson.status==1}">未审核</c:if>
											<c:if test="${Lesson.status==3}">审核未通过</c:if>
										</span>
									    
									    <span class="badge" >
											<c:if test="${Lesson.knowledge.ccode==0}">转码成功</c:if>
											<c:if test="${Lesson.knowledge.ccode==1}">等待转码</c:if>
											<c:if test="${Lesson.knowledge.ccode==2}">正在转码</c:if>
											<c:if test="${Lesson.knowledge.ccode==3}">转码失败</c:if>
										</span>
											
										
										<select id="${Lesson.id}">
												<c:choose>  
											   <c:when test="${Lesson.knowledge.status==2}">  
											   <option value="0" >不通过</option>
												<option value="1" selected="selected">通过</option> 
											   </c:when>  
											     
											   <c:otherwise>  
												<option value="0" selected="selected">不通过</option>
												<option value="1">通过</option>
											   </c:otherwise>  
											</c:choose>  
											</select>
											<button type="button" class="deleteBtn btn btn-primary" onclick="checkLesson('${Lesson.id}','${Lesson.knowledge.id}')">审核课时</button>
										
									</li>
								</c:forEach>
								</c:forEach>
						</ul>



				</div>
			</div>
		</div>
		<script>
			$(function() {
				/* $("#searchIt").click(function(){
					window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
				}); */
			});
			
			function checkLesson(lessonId,knowledge){
				var status=$("#"+lessonId).val();
				$.post("${cbasePath}course/checkLesson",{"lessonId":lessonId,"knowledgeId":knowledge,"status":status},function(data){
					 alert("审核成功");
					 if(status==1){
						 $("#msg"+lessonId).html("审核通过");
					 }else{
						 $("#msg"+lessonId).html("审核不通过");
					 }
				});
			}
		</script>
</body>
</html>

