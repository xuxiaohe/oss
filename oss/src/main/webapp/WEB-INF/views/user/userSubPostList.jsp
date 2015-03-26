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
</head>

<body>

	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
			<ol class="breadcrumb">
			<li><a href="#">用户管理</a></li>
			<li><a href="${cbasePath}user/userList">用户列表</a></li>
			<li class="active">用户详情: <small>
					${resuserDetail.nickName }</small>
			</li>
		</ol>
			用户副楼回复：	 
				<c:if test="${usersubpost.status == '200'}">
					 

					<!---数据显示区域-->
					<%-- ${fn:length(Drys.data.result)} --%>
					<c:forEach items="${usersubpost.data.result}" varStatus="key"
						var="Recourse">

						<div class="row" style="padding: 20px;">
							<div class="col-xs-1">
								<h5 style="margin-top: 40px;">
									<span class="label label-default">${key.count}</span>
								</h5>
							</div>

						 
							<div class="col-xs-10">
								<h4 style="margin-left: 12px;">
									<a href="${cbasePath}dry/dryDetail?dryid=${Recourse.post_id}">
										${Recourse.message} </a><br> <small><small
										class="pull-right">注册时间：<Date:date
												value="${Recourse.ctime}"></Date:date></small></small>
								</h4>


								<a
								href="${cbasePath}user/deleteSubPostByTopicId?uid=${uid}&postid=${Recourse.parentId}&subpostid=${Recourse.post_id}">
									<button data="${Recourse.topicId}"  data2="${Recourse.postId}" type="button"
										class="deleteBtn btn btn-primary">删除</button>
									</a>	

							</div>
						</div>
					</c:forEach>
					 
				</c:if>
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
									window.location.href = "${cbasePath}dry/deleteDry?dryid="
											+ $(this).attr("data");
								} else {

								}
							});
			
			
			$(".info")
			.click(
					function() {
						 
							//alert("info");
							window.location.href = "${cbasePath}dry/dryDetail?dryid="
									+ $(this).attr("data");
						 
					});
		});
	</script>
</body>
</html>

