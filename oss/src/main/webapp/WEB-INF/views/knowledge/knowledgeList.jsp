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
<title>课程管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script type="text/javascript">
$.noConflict();
</script>
<script src="${sourcePath}/resources/assets/js/plupload.full.min.js"></script>

<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
</head>

<body>

	<div class="container-fluid">
	<jsp:include page="header.jsp"></jsp:include>
		<ol class="breadcrumb">
			<li><a href="#">知识管理管理</a></li>
			<li class="active"><a href="#">未审核知识</a></li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<form class="form-inline" action="${cbasePath}knowledge/knowledgeList"
					method="get" role="form">
					<div class="form-group">
						<label class="sr-only" for="keyword">Search:</label> <input
							class="form-control" id="keywords" name="keywords"
							placeholder="Enter keyword" value="${keywords}">
					</div>

					<button id="searchIt" type="submit" class="btn btn-default">Search
						it!</button>
				</form> 
				<c:if test="${knowledgeList.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${knowledgeList.data.curr_page}"
							totalPages="${knowledgeList.data.page_rows}" perPageRows="10"
							totalRows="${knowledgeList.data.total_rows}"
							linkBaseUrl="${cbasePath}knowledge/knowledgeList?keywords=${keywords }">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
					
					<!---数据显示区域-->
					<%-- ${fn:length(Drys.data.result)} --%>
					<c:forEach items="${knowledgeList.data.result}" varStatus="key"
						var="Recourse">
						<div class="row" style="padding: 20px;" id="${Recourse.id}">
							<div class="col-xs-1">
								<h5 style="margin-top: 40px;">
									<span class="label label-default">${key.count}</span>
								</h5>
							</div>

							<div class="col-xs-1">
								<div class="row">
									<img class="col-xs-12 thumbnail" src="${Recourse.logoUrl}"
										style="margin-top: 10px;" alt="" /> 
								</div>
							</div>
							<div class="col-xs-10">
								<h4 style="margin-left: 12px;">
									<a href="${Recourse.furl}" target="_Blank">
										${Recourse.name} </a><br> <small><small
										class="pull-right">上传时间：：<Date:date
												value="${Recourse.ctime}"></Date:date></small></small>
								</h4>


								<div class="col-xs-12 btn-group-sm">
									<button data="${Recourse.id}" type="button" onclick="verify('${Recourse.id}','2')"
										class="deleteBtn btn btn-primary">审核通过</button>
										
										<button data="${Recourse.id}" type="button" onclick="verify('${Recourse.id}','3')"
										class="info btn btn-primary">拒绝通过</button>
									<%-- <c:if test="${Recourse.group==''}">
										<a class="btn btn-success"
											href="${cbasePath}dry/updateDryForm?dryid=${Recourse.id}">
											关联群组 </a>
										<button data="${Recourse.id}" type="button" class="deleteBtn btn btn-primary">关联群组</button>
									</c:if> --%>
								</div>

							</div>
						</div>
					</c:forEach>
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${knowledgeList.data.curr_page}"
							totalPages="${knowledgeList.data.page_rows}" perPageRows="10"
							totalRows="${knowledgeList.data.total_rows}"
							linkBaseUrl="${cbasePath}knowledge/knowledgeList?keywords=${keywords }">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
		</div>
	</div>
	<script>
			
			function verify(id,status){
				 jQuery.ajax({
						url :"${cbasePath}knowledge/verify",
						type : "POST",
						data :{
							"id" : id,
							"status" : status
						},
						success : function() {
							window.location.href = "${cbasePath}knowledge/knowledgeList"
						}
				 });
			};
	
			
	</script>
</body>
</html>

