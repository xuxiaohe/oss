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
</style>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<ol class="breadcrumb">
			<li><a href="#">课程管理</a></li>
			<li><a href="${cbasePath}group/groupList">群列表</a></li>
			<li><a
				href="${cbasePath}group/groupDetail?gid=${Group.data.result.id}">
					<small> ${Group.data.result.groupName }</small>
			</a></li>
			<li class="active">课程列表</li>
		</ol>
		
		<div class="col-xs-12 btn-group-sm">
		<a href="${cbasePath}group/courseListNoShare?gid=${Group.data.result.id}">
		<button name="delete" data="${Group.data.result.id}" type="button"
			class=" btn btn-primary">添加其他课程</button>
		 </a>
	</div>

		<c:if test="${CourseList.status == '200' }">
			<!-- 分页开始 -->
			<nav>
			<ul class="pagination">
				<pageNation:PageNation currPage="${CourseList.data.curr_page}"
					totalPages="${CourseList.data.page_rows}" perPageRows="10"
					totalRows="${CourseList.data.total_rows}"
					linkBaseUrl="${cbasePath}group/groupCourse?gid=${Group.data.result.id}">
				</pageNation:PageNation>
			</ul>
			</nav>
			<!-- 分页结束 -->
			<c:forEach items="${CourseList.data.result}" varStatus="key"
				var="course">
				<div id="topicDiv" style="margin: 10px;" class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12" src="${course.course.logoUrl}"
							alt="${course.course.title}">
					</div>
					<div class="col-xs-10">
						<div class="caption">
							<p class="">${course.course.title}</p>
						</div>
						<a href="${cbasePath}course/deleteToMyGroup?groupId=${Group.data.result.id}&courseId=${course.id}">
							<button type="button" class="delete btn-danger" data="${course.id}">删除</button>
						</a>
					</div>
				</div>
			</c:forEach>
			<!-- 分页开始 -->
			<nav>
			<ul class="pagination">
				<pageNation:PageNation currPage="${CourseList.data.curr_page}"
					totalPages="${CourseList.data.page_rows}" perPageRows="10"
					totalRows="${CourseList.data.total_rows}"
					linkBaseUrl="${cbasePath}group/groupCourse?gid=${Group.data.result.id}">
				</pageNation:PageNation>
			</ul>
			</nav>
			<!-- 分页结束 -->
		</c:if>
		
		
		
	</div>


	

	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			 
			
			$(".delete").click(function(){
				if(window.confirm('你确定要删除吗？')){
					window.location.href="${cbasePath}course/deleteToMyGroup?groupId="+${Group.data.result.id}+"&courseId="+$(this).attr("data");
				}else{
					
				}
			}); 
			
			
			$(".addcourse").click(function() {
				 
					 window.location.href = "${cbasePath}group/courseListNoShare?gid="+$(this).attr("data");
				 
			});
			
			
			
		});
	</script>
</body>
</html>

