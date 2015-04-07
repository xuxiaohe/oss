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
		<ol class="breadcrumb">
			<li><a href="#">标签管理</a></li>
			<li><a href="${cbasePath}tag/tagList">标签列表</a></li>
			<li class="active">标签详情: 
			</li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<a href="#">
						<button type="button" class="btn btn-warning btn-block" onclick="javascript:toUpdate('${cbasePath}tag/updateView', '${tagDetail.id}', '${tagDetail.tagName}', '${tagDetail.score}')">编辑</button></a><br>
						<%-- <div id="btnGroupDiv" class="col-xs-12">
							<a
								href="${cbasePath}user/userTopic?userid=${resuserDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看话题</button>
							</a> <a
								href="${cbasePath}user/userDry?userid=${resuserDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看干货</button>
							</a> <a
								href="${cbasePath}user/userCourse?userid=${resuserDetail.data.result.id }">
								<button type="button" class="btn btn-success btn-block">查看课程</button>
							</a> <a
								href="${cbasePath}user/userGroup?userid=${resuserDetail.data.result.id }"><button
									type="button" class="btn btn-success btn-block">查看群组</button></a>
						</div> --%>
					</div>
					<div id="userInfoDiv" class="col-xs-10" style="">
						<h4 style="margin-left: 12px;">
							标签名 : ${tagDetail.tagName}
						</h4>
						<div class="col-xs-6">LowCase : ${tagDetail.tagNameLowCase}
						</div>
						<div class="col-xs-12">热度 : ${tagDetail.score}
						</div>
						
						
						
						 
					</div>
				
				



			</div>
		</div>
	</div>
	<script type="text/javascript">
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

