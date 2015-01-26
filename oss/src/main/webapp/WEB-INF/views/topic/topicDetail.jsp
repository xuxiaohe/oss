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
						
						<a href="${cbasePath}topic/deleteTopic?topicid=${topicDetail.data.result.topicId }">
						<button type="button" data="${topicDetail.data.result.topicId}" class="deletetopic btn-warning btn-block">删除</button></a><br>
						
						<a href="${cbasePath}topic/addPostByTopicIdForm?topicid=${topicDetail.data.result.topicId }">
						<button name="addpost" data="${topicDetail.data.result.topicId}" type="button" class="addpost btn-warning btn-block">添加主楼回复</button></a>
						<hr />
					</div>
					 
					<div id="userInfoDiv" class="col-xs-10" style="">
						<h4 style="margin-left: 12px;">
							话题标题：${topicDetail.data.result.title} <small><small
								class="pull-right">注册时间：<Date:date
										value="${topicDetail.data.result.ctime}"></Date:date></small></small>
						</h4>
						  
						<div class="col-xs-6">话题内容：${topicDetail.data.result.content}
						</div><br> 
						
						<br><br>
						<c:if test="${resTopicPost.status == '200'}">
						<c:forEach items="${resTopicPost.data.result}" varStatus="key" var="Recourse">
						 
								<ul class="list-group">
									<li class="list-group-item"><span class="badge">副楼回复数：${Recourse.number}</span>
										主楼回复：${Recourse.post.message} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="${cbasePath}topic/deletePostByTopicId?topicid=${topicDetail.data.result.topicId }&postid=${Recourse.post.postId}">
										<button name="postdelete"   type="button" class="postdelete btn btn-danger"">删除</button></a>
								 <a href="${cbasePath}topic/addSubPostForm?topicid=${topicDetail.data.result.topicId }&postid=${Recourse.post.postId}">
								 <button name="postedit"   type="button" class="postedit btn btn-primary">添加副楼回复</button></a>
										
										<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										
										<c:if test="${Recourse.post.subPosts != null}">
											
											<c:forEach items="${Recourse.post.subPosts}" varStatus="key" var="subpost">
											
										<br> <br>	   副楼回复：${subpost.message}  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											
									<a href="${cbasePath}topic/deleteSubPostByTopicId?postid=${Recourse.post.postId}&index=${key.count-1}&topicid=${topicDetail.data.result.topicId }">		 
									<button name="subpostdelete"   type="button" class="subpostdelete btn btn-danger"">删除</button></a>
											</c:forEach>
										</c:if>
										
										<c:if test="${Recourse.number != '0'}">
											
											<c:forEach items="${Recourse.subpost}" varStatus="key" var="subpost">
											
											<br><br>  副楼回复：${subpost.message}   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											
									<a href="${cbasePath}topic/deleteSubPostByTopicId?postid=${Recourse.post.postId}&subpostid=${subpost.post_id}">		 
									<button name="subpostdelete"   type="button" class="subpostdelete btn btn-danger"">删除</button></a>
											</c:forEach>
										</c:if>
										</li>
								</ul>

							</c:forEach>
						</c:if>
						 
						 
				</div>




			</div>
		</div>
	</div>
	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			
			
			$(".postdelete").click(function(){
				if(window.confirm('你确定要删除主楼回复吗？')){
					 
					 window.location.href="${cbasePath}topic/deleteTopic?postid="+$(this).attr("data")+"&topicid="+${topicDetail.data.result.topicId};
				}else{
					
				}
			}); 
			
			$(".postedit").click(function(){
				 
					alert("postedit");
			 
			}); 
			
			$(".subpostdelete").click(function(){
				if(window.confirm('你确定要删除吗？')){
					alert("subpostdelete");
					//window.location.href="${cbasePath}topic/deleteTopic?topicid="+$(this).attr("data");
				}else{
					
				}
			}); 
			
			$(".subpostedit").click(function(){
				 
					alert("subpostedit");
					//window.location.href="${cbasePath}topic/deleteTopic?topicid="+$(this).attr("data");
			 
			}); 
			
			$(".addpost").click(function(){
				 
				 
				window.location.href="${cbasePath}topic/addPostByTopicIdForm?topicid="+$(this).attr("data");
		 
		});
			
			
			
			$(".deletetopic").click(function(){
				if(window.confirm('你确定要删除吗？')){
					alert("topic");
					//window.location.href="${cbasePath}topic/deleteTopic?topicid="+$(this).attr("data");
				}else{
					
				}
			}); 
			
			
		});
	</script>
</body>
</html>

