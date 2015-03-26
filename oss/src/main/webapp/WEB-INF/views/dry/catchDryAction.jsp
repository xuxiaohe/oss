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
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
<style>
#userInfoDiv div {
	padding: 10px;
}
</style>
</head>
<body>
	<div class="container-fluid">

		<ol class="breadcrumb">
			<li><a href="#">干货仓库</a></li>
			<li><a href="${cbasePath}dry/dryList">干货列表</a></li>
			 
			<li class="active">干货编辑</li>
		</ol>

		<div class="row">

			<%-- <div class="col-xs-3">
				<img class="thumbnail col-xs-12" src="${resuserTopic.data.result.fileUrl}" alt="" />

				<c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach>
			</div> --%>

			<div class="col-xs-9">
				<form role="form" method="post"
					action="${cbasePath}dry/createDryByCatch">
					<br><br>
					抓取的内容：
					<div class="form-group">
						<label for="exampleInputEmail1">URL</label> <input type="text"
							name="url" class="form-control" id="exampleInputEmail1" value="${url }"
							placeholder="">
					</div>
					
					<c:forEach items="${image}" varStatus="key"
						var="Recourse">
					
					<div class="col-xs-1">
								<div class="row">
									<img class="col-xs-12 thumbnail" src="${Recourse}"
										style="margin-top: 10px;" alt="" onclick="sendurll('${Recourse}')" />
								</div>
							</div>
					
					
					</c:forEach>
					
					<div class="form-group">
						<label for="exampleInputEmail1">请选择点击上面的一张图片</label> <input type="text"
							name="fileUrl" class="form-control" id="exampleInputEmail2" value=""
							placeholder="">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">信息</label> <input type="text"
							name="message" class="form-control" id="exampleInputEmail1" value="${message }"
							placeholder="">
					</div>
					
					<br><br><br>
					填写的内容：
					<div class="form-group">
						<label for="exampleInputEmail1">用户名</label> 
							<select class="form-control" name="uid" id="uidSelect">
							<c:if test="${robots.status == '200' }">
								<c:forEach items="${robots.data.result}" varStatus="key"
									var="Recourse">
									<option value="${Recourse.id }">${Recourse.nickName }:${Recourse.userName }</option>
								</c:forEach>
							</c:if>
						</select>

					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">标签</label> <input type="text"
							name="tagName" class="form-control" id="exampleInputEmail1"
							placeholder=""> 例如：name,age,sex
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">群组</label> 
							<select class="form-control" name="gid" id="gidSelect">
							<c:if test="${groupList.status == '200' }">
								<c:forEach items="${groupList.data.result}" varStatus="key"
									var="Recourse">
									<option value="${Recourse.id }">${Recourse.groupName }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
					
					
					<div class="form-group">
						<label for="exampleInputEmail1">干货抓取描述</label> <input type="text"
							name="description" class="form-control" id="exampleInputEmail1"
							placeholder="">
					</div>


					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>


	</div>
	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			$(".btnDelete").click(function() {
				if (window.confirm('你确定要删除吗？')) {
					window.location.href = $(this).attr("data");
				} else {

				}
			});
		});
		
		function sendurll(url)
		{
			$("#exampleInputEmail2").val(url);
		}
	</script>
</body>
</html>

