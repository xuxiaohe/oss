<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>用户管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
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
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12"
							src="${resuserDetail.data.result.logoUrl }" alt="" />
						<%-- <button type="button" data="${resuserDetail.id}" class="edit btn-warning btn-block">编辑</button>
						<button id="delete" data="${resuserDetail.id}" type="button" class="delete btn-warning btn-block">删除</button> --%>
						<hr />
					</div>
					<div id="userInfoDiv" class="col-xs-10" style="">
						<h4 style="margin-left: 12px;">
							知识名称：${resuserDetail.data.result.name} <small>
							 <small
								class="pull-right">注册时间：<Date:date
										value="${resuserDetail.data.result.ctime}"></Date:date></small> </small>
						</h4>
						<div class="col-xs-6">
							知识类型：
							<c:if test="${resuserDetail.data.result.kngType=='1'}">视频</c:if>
  							<c:if test="${resuserDetail.data.result.kngType=='2'}">文档</c:if>
						</div>
						<div class="col-xs-6">
							<c:if test="${resuserDetail.data.result.kngType=='1'}">时长：${resuserDetail.data.result.duration }秒</c:if>
  							<c:if test="${resuserDetail.data.result.kngType=='2'}">页数：${resuserDetail.data.result.pages }页</c:if>
  						</div>
						<div class="col-xs-6">知识大小：${resuserDetail.data.result.fileSize}bit</div>
						<div class="col-xs-12 badge">
							<c:if test="${resuserDetail.data.result.kngType=='1'}">
								<a  href="${cbasePath}player/index?id=${resuserDetail.data.result.id}" target="_blank" style="color:#fff000">查看</a> 
							</c:if>
							<c:if test="${resuserDetail.data.result.kngType=='2'}">
								<a  href="${ resuserDetail.data.result.furl}" target="_blank" style="color:#fff000">查看</a>
							</c:if>
						</div>
				</div>
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
			
			$(".delete").click(function(){
				  if(window.confirm('你确定要删除吗？')){
					alert("${cbasePath}user/deleteUser?userid="+$(this).attr("data"));
					window.location.href="${cbasePath}user/deleteUser?userid="+$(this).attr("data");
				}else{
					
				}  
				
			 
			});
			
			$(".edit").click(function(){
				  
					window.location.href="${cbasePath}user/updateUserForm?userid="+$(this).attr("data"); 
				 
				
			 
			});
			
		});
	</script>
</body>
</html>

