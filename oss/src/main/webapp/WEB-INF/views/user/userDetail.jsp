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
		<jsp:include page="header.jsp"></jsp:include>
		<ol class="breadcrumb">
			<li><a href="#">用户管理</a></li>
			<li><a href="${cbasePath}user/userList">用户列表</a></li>
			<li class="active">用户详情: <small>
					${resuserDetail.data.result.nickName }</small>
			</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12"
							src="${resuserDetail.data.result.logoURL }" alt="" />
						<button type="button" class="btn btn-warning btn-block">编辑</button>
						<button type="button" class="btn btn-warning btn-block">删除</button>
						<hr />
						<div id="btnGroupDiv" class="col-xs-12">
							<a
								href="${cbasePath}user/userTopic?userid=${resuserDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看话题</button>
							</a>
							<a
								href="${cbasePath}user/userDry?userid=${resuserDetail.data.result.id }">
							<button type="button" class="btn btn-success btn-block">查看干货</button>
							</a>
							<a
								href="${cbasePath}user/userCourse?userid=${resuserDetail.data.result.id }">
							<button type="button" class="btn btn-success btn-block">查看课程</button>
							</a>
							<a
								href="${cbasePath}user/userGroup?userid=${resuserDetail.data.result.id }"><button
									type="button" class="btn btn-success btn-block">查看群组</button></a>
						</div>
					</div>
					<div id="userInfoDiv" class="col-xs-10" style="">
						<h4 style="margin-left: 12px;">
							用户昵称：${resuserDetail.data.result.nickName} <small><small
								class="pull-right">注册时间：<Date:date
										value="${resuserDetail.data.result.ctime}"></Date:date></small></small>
						</h4>
						<div class="col-xs-6">
							最近登录：
							<Date:date value="${resuserDetail.data.result.logintime}"></Date:date>
						</div>
						<div class="col-xs-6">
							登陆账号：${resuserDetail.data.result.userName}</div>
						<div class="col-xs-6">
							用户编号：${resuserDetail.data.result.userNumber}</div>

						<div class="col-xs-6">性别：${resuserDetail.data.result.sex}</div>

						<div class="col-xs-12">
							OpenFire：${resuserDetail.data.result.openFireUser.serverList}
							服务器，${resuserDetail.data.result.openFireUser.openFireUserName}</div>
						<div class="col-xs-6">星座：${resuserDetail.data.result.constelLation}
						</div>
						<div class="col-xs-6">年龄：${resuserDetail.data.result.age}</div>
						<div class="col-xs-6">行业：${resuserDetail.data.result.industry}
						</div>
						<div class="col-xs-6">状态：${resuserDetail.data.result.station}
						</div>
						<div class="col-xs-6">学历：${resuserDetail.data.result.education}
						</div>
						<div class="col-xs-6">学校：${resuserDetail.data.result.school}
						</div>
						<div class="col-xs-6">活动区域：${resuserDetail.data.result.area}
						</div>
						<div class="col-xs-6">公司：${resuserDetail.data.result.company}
						</div>
						<div class="col-xs-6">感兴趣于：${resuserDetail.data.result.interest}
						</div>
						<div class="col-xs-6">擅长：${resuserDetail.data.result.special}
						</div>

						<div class="col-xs-6">邮箱：${resuserDetail.data.result.email}
						</div>
						<div class="col-xs-6">
							手机：${resuserDetail.data.result.phoneNumber}</div>

						<div class="col-xs-6">标签：${resuserDetail.data.result.tag}</div>
						<div class="col-xs-6">
							位置信息：${resuserDetail.data.result.location}</div>
						<div class="col-xs-6">
							位置信息：${resuserDetail.data.result.scoreSum}</div>


						<div class="col-xs-12">
							介绍：${resuserDetail.data.result.intro}</div>
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

