<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
				<form class="form-inline" action="${cbasePath}topic/topicList"
					method="get" role="form">
					<div class="form-group">
						<label class="sr-only" for="keyword">Search:</label> <input
							class="form-control" id="keyword" name="keyword"
							placeholder="Enter keyword" value="${keyword}">
					</div>

					<button id="searchIt" type="submit" class="btn btn-default">Search
						it!</button>
				</form>
				<c:if test="${topicList.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${topicList.data.curr_page}"
							totalPages="${topicList.data.page_rows}" perPageRows="10"
							totalRows="${topicList.data.total_rows}"
							linkBaseUrl="${cbasePath}topic/topicList?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>

					<!---数据显示区域-->

					<c:forEach items="${topicList.data.result}" varStatus="key" var="Recourse">
						<div class="row" style="padding: 20px;">
							<div class="col-xs-1">
								<h5 style="margin-top:40px;"><span class="label label-default">${key.count}</span></h5>
							</div>
							 
							<div class="col-xs-1">
								<div class="row">
									<img class="col-xs-12 thumbnail" src="${Recourse.picUrl}"
										style="margin-top: 10px;" alt="" />
								</div>
							</div>
							<div class="col-xs-10">
								<h4 style="margin-left:12px;">
									<a href="${cbasePath}topic/topicDetail?topicid=${Recourse.topicId}">
										标题：${Recourse.title} </a><br>
										内容：
										<c:if test="${Recourse.content != null && Recourse.content != 'null'}">					
											<c:choose>
									          <c:when test="${fn:length(Recourse.content) > 30}"> 
									          
									              <c:out value="${fn:substring(Recourse.content, 0, 30)}......" />
									         </c:when>
									         <c:otherwise>
									            <c:out value="${Recourse.intro}" />
									          </c:otherwise>
									      </c:choose>
									      </c:if> 
										<small><small
										class="pull-right">注册时间：<Date:date
												value="${Recourse.ctime}"></Date:date></small></small>
								</h4>
								回复主楼数：${Recourse.replyCount}
								
								<div class="col-xs-12 btn-group-sm">
									<button data="${Recourse.topicId}" type="button" class="deleteBtn btn btn-primary">删除</button>
									
									<button data="${Recourse.topicId}" type="button"
										class="info btn btn-primary">详情</button>
									<c:if test="${Recourse.sourceId==''}">
									<a 
										class="btn btn-success" href="${cbasePath}topic/updateTopicForm?topicid=${Recourse.topicId}">
										关联群组 </a>
									<%-- <button data="${Recourse.id}" type="button" class="deleteBtn btn btn-primary">关联群组</button> --%>
									</c:if>
									 
									<c:if test="${Recourse.review==false}">
									<a 
										class="btn btn-success" href="${cbasePath}topic/checkTopic?topicid=${Recourse.topicId}">
										审核话题通过 </a>
									<%-- <button data="${Recourse.id}" type="button" class="deleteBtn btn btn-primary">关联群组</button> --%>
									</c:if>
								</div>

							</div>
						</div>
					</c:forEach>
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${topicList.data.curr_page}"
							totalPages="${topicList.data.page_rows}" perPageRows="10"
							totalRows="${topicList.data.total_rows}"
							linkBaseUrl="${cbasePath}topic/topicList?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
		</div>
		
		<!-- Modal -->
		<!-- Modal -->
		<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">群组搜索</h4>
					</div>
					<div class="modal-body" id="modalHtml"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
						<!-- <button type="button" class="btn btn-primary">确定</button> -->
					</div>
				</div>
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
					//alert("${cbasePath}dry/deleteDry?dryid="+$(this).attr("data"));
					window.location.href="${cbasePath}topic/deleteTopic?topicid="+$(this).attr("data");
				}else{
					
				}
			}); 
			
			
			$(".info")
			.click(
					function() {
						 
							//alert("info");
							window.location.href = "${cbasePath}topic/topicDetail?topicid="
									+ $(this).attr("data");
						 
					});
			
		});
	</script>
</body>
</html>

