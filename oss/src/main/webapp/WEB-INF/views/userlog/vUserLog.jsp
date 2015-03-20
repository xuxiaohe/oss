<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>用户统计</title>

	
	<script src="${cbasePath}resources/assets/js/jQuery.js"></script>
	<script src="${cbasePath}resources/assets/js/bootstrap.js"></script>
	<link href="${cbasePath}resources/assets/css/bootstrap.css"
	rel="stylesheet">
	<style>
		.block {border-radius: 6px;padding: 7px 20px;}
		.block1 {background-color: #999;}
		.block2 {background-color: #E4E4E4;}
		.font1 {font-size: 18px;font-weight: bold;}
	</style>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-body">
			<ul class="nav nav-pills nav-justified">
				<li ><a href="./">渠道用户统计</a></li>
				<li class="active"><a href="vlog">邀请用户统计</a></li>
				<li><a href="#">渠道商管理</a></li>
			</ul>
			<ol class="breadcrumb" style="margin-top:10px">
			  <li><a href="#">用户统计</a></li>
			  <li class="active">用户邀请列表</li>
			</ol>
			<form class="form-inline" role="form" action="">
				<div class="col-xs-6">
					<div class="form-group form-group-sm">
						<label style="width: 200px;" >开始日期：</label>
						<input type="text" class="form-control datein" placeholder="2015-02-03" name="ctime" value="${ctime}" id="ctime">
					</div>
					</div>
					<div class="col-xs-6">
					<div class="form-group form-group-sm">
						<label style="width: 200px;">结束日期：</label>
						<input type="text" class="form-control" name="etime"  placeholder="2015-02-03"  value="${etime} " id="etime">
					</div>
				</div>
				
				<div class="col-xs-8" style="margin-top:15px;">
					<div class="form-group form-group-sm">
						<label style="width: 200px;">邀请人昵称/邮箱/手机号：</label>
						<input type="text" class="form-control" name="key" value="${key}" id="userKey">
					</div>
				</div>
				
				
				
				<br>
				<div class="col-xs-6 pull-left text-left" style="margin-top:15px;">
					<button type="submit" class="btn btn-primary">筛选</button>
				</div>
				
				<div class="col-xs-6 pull-left text-left" style="margin-top:15px;">
					<button type="button" class="btn btn-primary" onclick="exportcsv();">导出</button>
				</div>
				<script>
					function exportcsv(){
						 var ctime=$("#ctime").val();
						 var etime=$("#etime").val();
						 var key=$("#userKey").val();
							$.post("exportV",{"ctime":ctime,"etime":etime,"key":key},function(data){
								console.log(data);
								top.location.href=data.url;
							});
					}
				</script>
			</form>
			<div class="col-xs-12">
			<hr />
				<div class="block block1 pull-left text-center">
					<p>邀请人总数</p>
					<div class="font1">${resuserList.data.total_rows}</div>
				</div>
				
			</div>
			<div class="col-xs-12">
				<hr />
				<table class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>邀请人昵称</th>
							<th>邮箱</th>
							<th>手机号</th>
							<th>注册渠道 </th>
							<th>成功注册人数<span class="glyphicon glyphicon-arrow-up"></span></th>
							<th>注册时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${resuserList.data.result}" varStatus="key" var="log">
						<tr>
							<td><a href="../user/userDetail?userid=${log.vUserId}">${log.vUserNick}</a></td>
							<td>${log.vUserEmail}</td>
							<td>${log.vUserPhone}</td>
							<td>${log.userNick}</td>
							<td>${log.total}</td>
							<td>
								<Date:date value="${log.ctime}"></Date:date>
							</td>
							
							<td><a href="searchVuser?vkey=<c:set var="vuseremail" scope="session" value="${log.vUserEmail}"/><c:choose>
							    <c:when test="${vuseremail == 'null'}">${log.vUserPhone}</c:when>
							    <c:otherwise> ${log.vUserEmail}</c:otherwise>
								</c:choose>">查看注册用户</a></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<nav> 
	<!-- 分页开始 -->
		<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${resuserList.data.curr_page}"
							totalPages="${resuserList.data.page_rows}" perPageRows="10"
							totalRows="${resuserList.data.total_rows}"
							linkBaseUrl="${cbasePath}searchVuser/?ctime=${ctime}&etime=${etime}&ukey=${ukey}&ukey=${ukey}">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>

	
    <!-- 分页结束 -->
     </nav>
</body>
</html>