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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>用户管理</title>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
</head>

<body>

	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
				<form class="form-inline" action="${cbasePath}tag/tagList"
					method="get" role="form">
					<div class="form-group">
						<label class="sr-only" for="keyword">Search:</label> <input
							class="form-control" id="keyword" name="keyword"
							placeholder="Enter keyword" value="${keyword}">
					</div>

					<button id="searchIt" type="submit" class="btn btn-default">Search
						it!</button>
				</form>
				<c:if test="${tags.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${tags.data.curr_page}"
							totalPages="${tags.data.result.totalPages}" perPageRows="10"
							totalRows="${tags.data.result.totalElements}"
							linkBaseUrl="${cbasePath}tag/tagList?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>

					<!---数据显示区域-->
					<%-- ${fn:length(tags.data.result)} --%>
					<c:forEach items="${tags.data.result.content}" varStatus="key"
						var="Recourse">
						<div class="row" style="padding: 20px;">
							<div class="col-xs-1">
								<h5 style="margin-top: 40px;">
									<span class="label label-default">${key.count}</span>
								</h5>
							</div>

							<div class="col-xs-10">
								<h4 style="margin-left: 12px;">
									<%-- <a href="${cbasePath}tag/detailView?tid=${Recourse.id}&type=${Recourse.type}&tagName=${Recourse.tagName}&tagNameLowCase=${Recourse.tagNameLowCase}&status=${Recourse.status}&score=${Recourse.score}">${Recourse.tagName}</a> --%>
									<a href="#" onclick="javascript:loadDetail('${cbasePath}tag/detailView', '${Recourse.id}', '${Recourse.tagType}', '${Recourse.tagName}', '${Recourse.tagNameLowCase}', '${Recourse.status}', '${Recourse.score}')">${Recourse.tagName}</a>
								</h4>

								<div class="col-xs-12 btn-group-sm">
										<button data="${Recourse.id}" type="button" 
										class="deleteBtn btn btn-primary" onclick="javascript:toUpdate('${cbasePath}tag/updateView', '${Recourse.id}', '${Recourse.tagName}', '${Recourse.score}')">编辑</button>
										
										<button data="${Recourse.id}" type="button" onclick="javascript:loadDetail('${cbasePath}tag/detailView', '${Recourse.id}', '${Recourse.tagType}', '${Recourse.tagName}', '${Recourse.tagNameLowCase}', '${Recourse.status}', '${Recourse.score}')"
										class="info btn btn-primary">详情</button>
								</div>

							</div>
						</div>
					</c:forEach>
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${tags.data.curr_page}"
							totalPages="${tags.data.result.totalPages}" perPageRows="10"
							totalRows="${tags.data.result.totalElements}"
							linkBaseUrl="${cbasePath}tag/tagList?">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
		</div>
	</div>
	<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
	<script type="text/javascript">
		function loadDetail(url, tid, type, tagName, tagNameLowCase, status, score){
			$.ajax({
				url : url,
				data : {'tid' : tid, 'type' : type, 'tagName' : tagName, 'tagNameLowCase' : tagNameLowCase, 'status' : status, 'score' : score},
				type : 'post',
				success : function(data){
					$("html").empty().html(data);
				}
				
			}); 
			//jQuery.post(url,{'tid' : tid, 'type' : type, 'tagName' : tagName, 'tagNameLowCase' : tagNameLowCase, 'status' : status, 'score' : score});
			//$("#mainFrame").load(url,  {'tid' : tid, 'type' : type, 'tagName' : tagName, 'tagNameLowCase' : tagNameLowCase, 'status' : status, 'score' : score});
		}
		
		function toUpdate(url, tid, tagName, score){
			$.ajax({
				url : url,
				data : {'tid' : tid, 'tagName' : tagName,  'score' : score},
				type : 'post',
				success : function(data){
					$("html").empty().html(data);
				}
				
			}); 
		}
		
	</script>
</body>
</html>

