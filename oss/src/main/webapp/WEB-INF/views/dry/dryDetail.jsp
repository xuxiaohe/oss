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
			<li><a href="#">干货管理</a></li>
			<li><a href="${cbasePath}dry/dryList">干货列表</a></li>
			<li class="active">干货详情: <small>
					${dryDetail.data.result.message }</small>
			</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12"
							src="${dryDetail.data.result.fileUrl }" alt="" />
						<a href="${cbasePath}dry/editForm?dryid=${dryDetail.data.result.id }">
						<button type="button" class="btn btn-warning btn-block">编辑</button></a><br>
						<button data="${dryDetail.data.result.id }" type="button" class="deleteBtn btn-warning btn-block">删除</button><br>
						<a href="${cbasePath}dry/addPostByDryIdForm?dryid=${dryDetail.data.result.id}">
						<button name="addpost"   type="button" class="addpost btn-warning btn-block">添加主楼回复</button></a>
						<hr />
						<%-- <div id="btnGroupDiv" class="col-xs-12">
							<a
								href="${cbasePath}user/userTopic?userid=${resuserDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看话题</button>
							</a> <a
								href="${cbasePath}user/userDry?userid=${resuserDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看干货</button>
							</a> <a
								href="${cbasePath}user/userCourse?userid=${resuserDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看课程</button>
							</a> <a
								href="${cbasePath}user/userGroup?userid=${resuserDetail.data.result.id }"><button
									type="button" class="btn btn-success btn-block">查看群组</button></a>
						</div> --%>
					</div>
					<div id="userInfoDiv" class="col-xs-10" style="">
						<h4 style="margin-left: 12px;">
							干货信息：${dryDetail.data.result.message} <small><small
								class="pull-right">注册时间：<Date:date
										value="${dryDetail.data.result.ctime}"></Date:date></small></small>
						</h4>
						 <c:forEach items="${dryDetail.data.result.sharePerList}" varStatus="key" var="Recourse">
						<div class="col-xs-6">
							分享人列表：${Recourse.userName}</div>
						</c:forEach>
						<div class="col-xs-6">浏览量：${dryDetail.data.result.viewCount}
						</div>
						<div class="col-xs-6">回复数：${dryDetail.data.result.replyCount}</div>
						<div class="col-xs-6">被点赞的次数：${dryDetail.data.result.likesCount}
						</div>
						<div class="col-xs-6">不赞数量：${dryDetail.data.result.unLikeCount}
						</div>
						<div class="col-xs-6">收藏人数统计：${dryDetail.data.result.favCount}
						</div>
						<br><br><br><br><br>
						
						
				<br><br>
						<c:if test="${resTopicPost.status == '200'}">
						<c:forEach items="${resTopicPost.data.result}" varStatus="key" var="Recourse">
						 
								<ul class="list-group">
									<li class="list-group-item"><span class="badge">副楼回复数：${Recourse.number}</span>
										主楼回复：${Recourse.post.message} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="${cbasePath}dry/deletePostByDryId?dryid=${dryDetail.data.result.id}&postid=${Recourse.post.postId}">
										<button name="postdelete"   type="button" class="postdelete btn btn-danger"">删除</button></a>
								 <a href="${cbasePath}dry/addSubPostForm?dryid=${dryDetail.data.result.id }&postid=${Recourse.post.postId}">
								 <button name="postedit"   type="button" class="postedit btn btn-primary">添加副楼回复</button></a>
										
										<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										
										<c:if test="${Recourse.post.subPosts != null}">
											
											<c:forEach items="${Recourse.post.subPosts}" varStatus="key" var="subpost">
											
										<br> <br>	   副楼回复：${subpost.message}  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											
									<a href="${cbasePath}dry/deleteSubPostByDryId?postid=${Recourse.post.postId}&index=${key.count-1}&dryid=${dryDetail.data.result.id}">		 
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
			
			$(".deleteBtn")
			.click(
					function() {
						if (window.confirm('你确定要删除吗？')) {
							//alert("${cbasePath}dry/deleteDry?dryid="+$(this).attr("data"));
							 window.location.href = "${cbasePath}dry/deleteDry?dryid="+ $(this).attr("data");
						} else {

						}
					});
			
			
		});
	</script>
</body>
</html>

