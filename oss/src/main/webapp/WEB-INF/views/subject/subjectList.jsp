<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
	<head>
	<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
	<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
	<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
		rel="stylesheet">
	<link href="<%=contextPath%>/resources/assets/css/bootstrap-datepicker.min.css"
		rel="stylesheet">
	<link href="<%=contextPath%>/resources/assets/css/font.css" rel="stylesheet">
	</head>
	<body>
		<div class="container-fluid">
			<jsp:include page="header.jsp"></jsp:include>
			<div class="panel panel-default">
				<div class="panel-body">
					<form class="form-inline" action="${cbasePath}subject/getBoxPostByType"
					method="post" role="form">
						<div class="form-group">
							<label>专题类型</label>&nbsp;&nbsp;&nbsp;&nbsp;
							<select class="form-control"  id="specialtype" onchange="javascript:selectUserType(this);">
								<option value="contentspecial">精选专题</option>
								<option value="activityspecial">活动专题</option>
								<option value="onecoursespecial">一元课</option>
							</select>
						</div>
					</form>
				
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${boxlist.data.curr_page}"
							totalPages="${boxlist.data.page_rows}" perPageRows="10"
							totalRows="${boxlist.data.total_rows}"
							linkBaseUrl="${cbasePath}subject/getBoxPostByType?type=${type}">
						</pageNation:PageNation>
					</ul>
					</nav>
					<c:forEach items="${boxlist.data.result}" var="special" varStatus="key">
						<div class="row">
							<div class="col-xs-1">
								<h5 style="margin-top:40px;"><span class="label label-default">${key.count}</span></h5>
							</div>
							<div class="col-xs-1">
								<div class="row">
									<img class="col-xs-12 thumbnail" src="${special.logoUrl}"
										style="margin-top: 10px;" alt="" />
								</div>
							</div>
							<div class="col-xs-10">
								<h4 style="margin-left:12px;">
									<a href="#" onclick="javascript:specialDetail('${special.id}', '${special.type}', '${special.logoUrl}', '${special.h5Url}', '${special.categoryId}');">
										${special.chinaName} </a><small><small
										class="pull-right">创建时间：<Date:date
												value="${special.ctime}"></Date:date></small></small>
								</h4>
							</div>
						</div>
					</c:forEach>
					
					<nav> <!-- 分页开始 -->
					<ul class="pagination" id="pagination">
						<pageNation:PageNation currPage="${boxlist.data.curr_page}"
							totalPages="${boxlist.data.page_rows}" perPageRows="10"
							totalRows="${boxlist.data.total_rows}"
							linkBaseUrl="${cbasePath}subject/getBoxPostByType?type=${type}">
						</pageNation:PageNation>
					</ul>
					</nav>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				$("#specialtype option[value='${type}']").attr("selected", true);
			});
			function selectUserType(obj){
				var type = $(obj).val();
				window.location.href= '${cbasePath}subject/getBoxPostByType?type=' + type;
			}
			function specialDetail(id, type, logoUrl, h5Url, categoryId){
				var url = '${cbasePath}subject/subjectDetail?id=' + id + '&type=' + type + '&logoUrl=' + logoUrl + '&h5Url=' + h5Url + '&categoryId=' + categoryId;
				window.location.href = url;
				//var data = {'id' : id, 'chinaName' : chinaName, 'type' : type, 'logoUrl' : logoUrl == null ? '' : logoUrl, 'ctime' : ctime};
				//$.post(url, data, function(result){console.log($(document));$(document).html(result)});
			}
		</script>
	</body>
</html>