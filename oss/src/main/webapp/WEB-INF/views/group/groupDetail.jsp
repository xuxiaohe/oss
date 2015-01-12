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

#btnGroupDiv button {
	margin: 10px;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<ol class="breadcrumb">
			<li><a href="#">群管理</a></li>
			<li><a href="${cbasePath}group/groupList">群列表</a></li>
			<li class="active">群详情: <small>
					${Group.data.result.groupName }</small>
			</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12"
							src="${Group.data.result.logoUrl }" alt="" />
						<button type="button" class="btn btn-warning btn-block">编辑</button>
						<button type="button" class="btn btn-warning btn-block">删除</button>
						<hr />
						<div id="btnGroupDiv" class="col-xs-12">
							<a
								href="${cbasePath}group/groupTopic?gid=${Group.data.result.id }">
								<button type="button" class="btn btn-success btn-block">管理话题</button>
							</a>
							<a
								href="${cbasePath}group/groupDry?gid=${Group.data.result.id }">
							<button type="button" class="btn btn-success btn-block">管理干货</button>
							</a>
							<a
								href="${cbasePath}group/groupCourse?gid=${Group.data.result.id }">
							<button type="button" class="btn btn-success btn-block">管理课程</button>
							</a>
							
							<a
								href="${cbasePath}group/groupMember?gid=${Group.data.result.id }"><button
									type="button" class="btn btn-success btn-block">管理成员</button></a>
						</div>
					</div>
					<div id="userInfoDiv" class="col-xs-10" style="">
						<h4 style="margin-left: 12px;">
							${Group.data.result.groupName} <small><small
								class="pull-right">注册时间：<Date:date
										value="${Group.data.result.ctime}"></Date:date></small></small>
						</h4>
						
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
		});
	</script>
</body>
</html>

