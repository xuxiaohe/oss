<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>用户管理</title>
<script src="${cbasePath}/resources/assets/js/jquery.min.js"></script>
<script src="${cbasePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${cbasePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${cbasePath}/resources/assets/css/font.css" rel="stylesheet">
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
			<li><a href="#">话题管理</a></li>
			<li><a href="${cbasePath}topic/topicList">话题列表</a></li>
			<li class="active">编辑话题<small>
					 </small>
			</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12"
							src="${topicDetail.data.result.picUrl }" alt="" />
						<a href="${cbasePath}topic/updateTopicItemsForm?topicid=${topicDetail.data.result.topicId }">
						<button type="button" class="edit btn-warning btn-block">编辑</button></a><br>
						<button type="button" data="${topicDetail.data.result.topicId}" class="delete btn-warning btn-block">删除</button>
						<hr />
						<%-- <div id="btnGroupDiv" class="col-xs-12">
							<a
								href="${cbasePath}user/userTopic?userid=${topicDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看话题</button>
							</a> <a
								href="${cbasePath}user/userDry?userid=${topicDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看干货</button>
							</a> <a
								href="${cbasePath}user/userCourse?userid=${topicDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看课程</button>
							</a> <a
								href="${cbasePath}user/userGroup?userid=${topicDetail.data.result.id }"><button
									type="button" class="btn btn-success btn-block">查看群组</button></a>
						</div> --%>
					</div>
					<div id="userInfoDiv" class="col-xs-10" style="">
						<h4 style="margin-left: 12px;">
							话题标题：${topicDetail.data.result.title} <small><small
								class="pull-right">注册时间：<Date:date
										value="${topicDetail.data.result.ctime}"></Date:date></small></small>
						</h4>
						  
						<div class="col-xs-6">话题内容：${topicDetail.data.result.content}
						</div>
						<%-- <div class="col-xs-6">回复数：${dryDetail.data.result.replyCount}</div>
						<div class="col-xs-6">被点赞的次数：${dryDetail.data.result.likesCount}
						</div>
						<div class="col-xs-6">不赞数量：${dryDetail.data.result.unLikeCount}
						</div>
						<div class="col-xs-6">收藏人数统计：${dryDetail.data.result.favCount}
						</div> --%>
						 
				</div>




			</div>
		</div>
	</div>
	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			
			
			$(".delete").click(function(){
				if(window.confirm('你确定要删除吗？')){
					//alert("${cbasePath}dry/deleteDry?dryid="+$(this).attr("data"));
					window.location.href="${cbasePath}topic/deleteTopic?topicid="+$(this).attr("data");
				}else{
					
				}
			}); 
			
			
		});
	</script>
</body>
</html>

