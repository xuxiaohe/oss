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
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
				<form class="form-inline" role="form" action="">
				<div class="col-xs-6">
					<div class="form-group form-group-sm">
						<label style="width: 200px;" >开始日期：</label>
						<input type="text" class="form-control datein" placeholder="2015-02-03" name="ctime" value="${ctime}">
					</div>
					</div>
					<div class="col-xs-6">
					<div class="form-group form-group-sm">
						<label style="width: 200px;">结束日期：</label>
						<input type="text" class="form-control" name="etime"  placeholder="2015-02-03"  value="${etime}">
					</div>
				</div>
				
				<!-- <div class="col-xs-6" style="margin-top:15px;">
					<div class="form-group form-group-sm">
						<label style="width: 200px;">渠道ID：</label> -->
						<input type="hidden" class="form-control" name="qdId" value="${qdId}">
				<!-- 	</div>
				</div> -->
				<div class="col-xs-6 pull-left text-right" style="margin-top:15px;">
					<button type="submit" class="btn btn-primary">筛选</button>
				</div>
			</form>
				
				<c:if test="${userList.status == '200'}">
						<div class="col-xs-12">
				<hr />
				<table class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>用户</th>
							<th>邮箱</th>
							<th>手机号</th>
							<th>注册时间</th>
						</tr>
					</thead>
					<tbody>
					<!---数据显示区域-->
				

					<c:forEach items="${userList.data.result}" varStatus="key" var="Recourse">
						<tr>
							<td>${Recourse.userNick }</td>
							<td>${Recourse.email }</td>
							<td>${Recourse.phoneNumber }</td>
							<td><Date:date value="${Recourse.ctime}"></Date:date></td>
							<td><button class="detailBtn" data="${Recourse.adSellerId}">详情</button></td>
						</tr>
					</c:forEach>
						
					</tbody>
				</table>
			</div>
					
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${userList.data.curr_page}"
							totalPages="${userList.data.page_rows}" perPageRows="10"
							totalRows="${userList.data.total_rows}"
							linkBaseUrl="${cbasePath}adSeller/userList?ctime=${ctime}&etime=${etime}&qdId=${userKey}">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
