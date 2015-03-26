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
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
			<%-- 	<form class="form-inline" action="${cbasePath}config/configList"
					method="get" role="form">
					<div class="form-group">
						<label class="sr-only" for="keyword">Search:</label> <input
							class="form-control" id="keyword" name="keyword"
							placeholder="Enter keyword" value="${keyword }">
					</div>

					<button id="searchIt" type="submit" class="btn btn-default">Search
						it!</button>
				</form> --%>
				<c:if test="${resuserList.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${resuserList.data.curr_page}"
							totalPages="${resuserList.data.page_rows}" perPageRows="10"
							totalRows="${resuserList.data.total_rows}"
							linkBaseUrl="${cbasePath}user/userList?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>

					<!---数据显示区域-->

					<c:forEach items="${resuserList.data.result}" varStatus="key" var="Recourse">
						<div class="row" style="padding: 20px;">
							<div class="col-xs-1">
								<h5 style="margin-top:40px;"><span class="label label-default">${key.count}</span></h5>
							</div>
							<%-- <div class="col-xs-1">
								<div class="row">
									<img class="col-xs-12 thumbnail" src="${Recourse.logoURL}"
										style="margin-top: 10px;" alt="" />
								</div>
							</div> --%>
							<div class="col-xs-10">
								<h4 style="margin-left:12px;">
									<a href="${cbasePath}user/userDetail?userid=${Recourse.id}">
										文件配置key：${Recourse.ckey} </a><small><small
										class="pull-right">配置分类：${Recourse.category}</small></small>
								</h4>
								<%-- <div class="col-xs-4">
									filter配置：${Recourse.filters}
								</div> --%>
								<%-- <div class="col-xs-4">
									文件参数配置：${Recourse.fileParam}
								</div> --%>
								<div class="col-xs-4">
									文件路径规则：${Recourse.pathrule}
								</div>
								<div class="col-xs-4">
									配置描述：${Recourse.desc}
								</div>
								
								<%-- <div class="col-xs-4">
									视频或文档转码参数：${Recourse.pops}
								</div>
								<div class="col-xs-4">
									文件处理后的回调api：${Recourse.cburl}
								</div> --%>
								<div class="col-xs-12 btn-group-sm">
									<button name="delete" data="${Recourse.id}" type="button" class="deleteBtn btn btn-primary">删除</button>
									<button name="edit" data="${Recourse.id}" type="button" class="edit btn btn-primary">编辑</button>
								</div>

							</div>
						</div>
					</c:forEach>
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${resuserList.data.curr_page}"
							totalPages="${resuserList.data.page_rows}" perPageRows="10"
							totalRows="${resuserList.data.total_rows}"
							linkBaseUrl="${cbasePath}user/userList?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			
			$(".deleteBtn").click(function(){
				if(window.confirm('你确定要删除吗？')){
					window.location.href="${cbasePath}config/deleteConfig?configId="+$(this).attr("data");
				}else{
					
				}
			});
		
			$(".edit").click(function(){
				window.location.href="${cbasePath}config/updateConfigView?configId="+$(this).attr("data"); 
				 
			});
		});
	</script>
</body>
</html>

