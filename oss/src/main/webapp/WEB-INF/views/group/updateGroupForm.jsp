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
<title>群组管理</title>
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
			<li><a href="#">群管理</a></li>
			<li><a href="${cbasePath}group/groupList">群列表</a></li>
			<li><a
				href="${cbasePath}group/groupDetail?gid=${Group.data.result.id}">
					<small> ${Group.data.result.groupName }</small>
			</a></li>
			<li class="active">群组编辑</li>
		</ol>

		<div class="row">
			<div class="col-xs-3">
				<img class="thumbnail col-xs-12"
					src="${resupdateGroupView.data.result.logoUrl}" alt="" />

				<%-- <c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach> --%>
			</div>


			<div class="col-xs-9">
				<a href="${url }" target="_blank">${resupdateGroupView.data.result.message }</a>
				<form role="form" method="post"
					action="${cbasePath}group/updateGroup?gid=${resupdateGroupView.data.result.id}&uid=${resOwner}&logoUrl=${resupdateGroupView.data.result.logoUrl }"
					enctype="multipart/form-data">


					<div class="form-group">
						<label for="exampleInputEmail1">群组信息</label> <input type="text"
							name="intro" class="form-control" id="exampleInputEmail1"
							value="${resupdateGroupView.data.result.intro }" placeholder="">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">群组标签</label> <input type="text"
							name="tag" class="form-control" id="exampleInputEmail1">原群组标签为：${resupdateGroupView.data.result.tag }
					</div>
					<br>
					<div class="form-group">
						<label for="exampleInputEmail1">如果修改请上传群组图片(支持JPEG,JPG,PNG)</label> 
						<input id="file" type="file" name="file" /> 
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">群组名称</label> <input type="text"
							name="groupName" class="form-control" id="exampleInputEmail1"
							value="${resupdateGroupView.data.result.groupName }"
							placeholder="">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">bgUrl</label> <input type="text"
							name="bgUrl" class="form-control" id="exampleInputEmail1"
							value="${resupdateGroupView.data.result.bgUrl }" placeholder="">
					</div>


					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>

	</div>

</body>
</html>

