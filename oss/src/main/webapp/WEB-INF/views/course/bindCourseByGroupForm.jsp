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
<script src="${cbasePath}/resources/assets/js/jquery.min.js"></script>
<script src="${cbasePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${cbasePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${cbasePath}/resources/assets/css/font.css" rel="stylesheet">
<style>
#userInfoDiv div {
	padding: 10px;
}
</style>
</head>
<body>
	<div class="container-fluid">

		<ol class="breadcrumb">
			<li><a href="#">课程仓库</a></li>
			<li><a href="${cbasePath}course/courseList">课程列表</a></li>
			<li><a
				href="${cbasePath}course/courseDetail?cid=${resuserCourse.data.result.id}">课程详情
					
			</a></li>
			<li class="active">课程绑定群组</li>
		</ol>

		<div class="row">
			<div class="col-xs-3">
				<img class="thumbnail col-xs-12"
					src="${resuserCourse.data.result.logoUrl}" alt="" />

				<%-- <c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach> --%>
			</div>


			<div class="col-xs-9">
				<a href="${url }" target="_blank">${resuserTopic.data.result.message }</a>
				<form role="form" method="post"
					action="${cbasePath}course/shareToMyGroup?courseId=${resuserCourse.data.result.id}">


					<div class="form-group">
						<label for="exampleInputEmail1">群组ID</label>  
							  <select
							class="form-control" name="groupId" id="gidSelect">
							<c:if test="${groupList.status == '200' }">
								<c:forEach items="${groupList.data.result}" varStatus="key"
									var="Recourse">
									<option value="${Recourse.id }">${Recourse.groupName }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>

					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>


	</div>
	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			$(".btnDelete").click(function() {
				if (window.confirm('你确定要删除吗？')) {
					window.location.href = $(this).attr("data");
				} else {

				}
			});
		});
	</script>
</body>
</html>

