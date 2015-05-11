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
<meta charset="UTF-8">
<title>用户管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css"
	rel="stylesheet">
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
			<li><a href="#">APP广告位管理</a></li>
			<li><a href="${cbasePath}dry/dryList">广告位列表</a></li>
			<li class="active">APP广告位详情 <small></li>
		</ol>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-2">
						<img class="thumbnail col-xs-12"
							src="${bannerDetail.data.result.picUrl }" alt="" /> <a
							href="${cbasePath}banner/modify?id=${bannerDetail.data.result.id }">
							<button type="button" class="btn btn-warning btn-block">编辑</button>
						</a><br>
						<button data="${bannerDetail.data.result.id }" type="button"
							class="deleteBtn btn-warning btn-block">删除</button>
						<br>

					</div>
					<div id="userInfoDiv" class="col-xs-10" style="">
						<h4 style="margin-left: 12px;">

							<small><small class="pull-right">注册时间：<Date:date
										value="${bannerDetail.data.result.ctime}"></Date:date></small></small>
						</h4>

						<div class="col-xs-6">广告位名称：${bannerDetail.data.result.name}
						</div>
						<div class="col-xs-6">渠道商：${bannerDetail.data.result.adSid}</div>
						<div class="col-xs-6">渠道：${bannerDetail.data.result.adSellerId}
						</div>
						<div class="col-xs-6">广告是否有效：${bannerDetail.data.result.effective}
						</div>
						<div class="col-xs-6">点击数量：${bannerDetail.data.result.ccount}
						</div>
						<%-- <div class="col-xs-6">URL：${dryDetail.data.result.url}
						</div> --%>
						
						
						<br> <br>
						<br>

					</div>

				</div>
			</div>
		</div>
		
		<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			
			$(".deleteBtn")
			.click(
					function() {
						if (window.confirm('你确定要删除吗？')) {
							//alert("${cbasePath}dry/deleteDry?dryid="+$(this).attr("data"));
							 window.location.href = "${cbasePath}dry/deleteDry?dryid="+ $(this).attr("data");
						} else {

						}
					});
			
			
		});
	</script>
</body>
</html>

