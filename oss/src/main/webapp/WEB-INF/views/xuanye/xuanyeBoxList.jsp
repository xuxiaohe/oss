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

	<jsp:include page="header.jsp"></jsp:include>
	

<body>

	 
	<%-- <div class="panel panel-default">
	<div class="panel-body">
		<div class="btn-group">
		
			
			<ul class="dropdown-menu" role="menu">
			<c:forEach items="${addDryBoxposition.data.result}" varStatus="key"
						var="Recourse"> ${Recourse.chinaName}
				<li><a href="${cbasePath}dry/createDryByGroupView?boxPostId=${Recourse.id}">test</a></li>
				 </c:forEach>
				<!-- <li><a href="#">用户导入</a></li> -->
				<li class="divider"></li>
			</ul>
			
		</div>
	</div>
</div> --%>
		<div class="panel panel-default">
	
		
			<div class="panel-body">
				<form class="form-inline" action="${cbasePath}dry/dryList"
					method="get" role="form">
					 
				</form>
				<h4>当前排行榜为：${name}</h4>
				<br>
					<div class="btn-group">
			<button type="button" class="btn btn-default dropdown-toggle"
				data-toggle="dropdown">
				请选择要关联的排行榜<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
			<c:forEach items="${addDryBoxposition.data.result}" varStatus="key"
						var="Recourse">
				<li><a href="${cbasePath}xuanye/DryBoxList?id=${Recourse.id}&name=${Recourse.chinaName}">${Recourse.chinaName}</a></li>
				</c:forEach>
				 
				<!-- <li><a href="#">用户导入</a></li> -->
				<li class="divider"></li>
			</ul>
		</div><br><br>
		<div class="btn-group">
		<form class="form-inline" action="${cbasePath}xuanye/searchDryBoxList?id=${id}&name=${name}"
					method="post" role="form">
					<div class="form-group">
						<label class="sr-only" for="keyword">Search:</label> <input
							class="form-control" id="keyword" name="keyword"
							placeholder="Enter keyword" value="${keyword}">
					</div>

					<button id="searchIt" type="submit" class="btn btn-default">Search
						it!</button>
				</form>
				</div>
				
				<c:if test="${addDryBoxList.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${addDryBoxList.data.curr_page}"
							totalPages="${addDryBoxList.data.page_rows}" perPageRows="10"
							totalRows="${addDryBoxList.data.total_rows}"
							linkBaseUrl="${cbasePath}xuanye/DryBoxList?id=${id}&name=${name}">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>

					<!---数据显示区域-->
					<c:forEach items="${addDryBoxList.data.result}" varStatus="key"
						var="Recourse">

						<div class="row" style="padding: 20px;">
							<div class="col-xs-1">
								<h5 style="margin-top: 40px;">
									<span class="label label-default">${key.count}</span>
								</h5>
							</div>

							<div class="col-xs-1">
								<div class="row">
									<img class="col-xs-12 thumbnail" src="${Recourse.fileUrl}"
										style="margin-top: 10px;" alt="" />
								</div>
							</div>
							<div class="col-xs-10">
								<h4 style="margin-left: 12px;">
									<a href="${cbasePath}dry/dryDetail?dryid=${Recourse.id}">
										${Recourse.message} </a><br> <small><small
										class="pull-right">注册时间：<Date:date
												value="${Recourse.ctime}"></Date:date></small></small>
								</h4>


								<div class="col-xs-12 btn-group-sm">
								<a href="${cbasePath}xuanye/bindBoxDry?boxPostId=${id}&sourceId=${Recourse.id}&name=${name}&ctime=${Recourse.ctime}">
									<button data="${Recourse.id}" type="button"
										class="deleteBtn btn btn-primary">关联该排行榜</button>
										</a>
										 
									 
								</div>

							</div>
						</div>
					</c:forEach>
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${addDryBoxList.data.curr_page}"
							totalPages="${addDryBoxList.data.page_rows}" perPageRows="10"
							totalRows="${addDryBoxList.data.total_rows}"
							linkBaseUrl="${cbasePath}xuanye/DryBoxList?id=${id}&name=${name}">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
		</div>
	 
	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			
			 
			
			
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

