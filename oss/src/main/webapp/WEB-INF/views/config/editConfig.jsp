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
</style>
</head>
<body>
	<div class="container-fluid">

		<ol class="breadcrumb">
			<li><a href="#">配置编辑</a></li>
			<li><a href="${cbasePath}config/configList">配置列表</a></li>
			<%-- <li><a
				href="${cbasePath}user/userDetail?userid=${resuserDetail.data.result.id}">用户详情:
					<small> ${resuserDetail.data.result.nickName }</small>
			</a></li> --%>
			<li class="active">配置编辑</li>
		</ol>

		<div class="row">

		<%-- 	<div class="col-xs-3">
				<img class="thumbnail col-xs-12"
					src="${resuserDetail.data.result.logoURL}" alt="" />

				<c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach>
			</div> --%>

			<div class="col-xs-9">

				<form role="form" method="post"
					action="${cbasePath}config/updateConfig?id=${resuserDetail.data.result.id}"
					enctype="multipart/form-data">
					<div class="form-group">
						<label for="exampleInputEmail1">文件配置key</label> <input type=""
							name="ckey" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.ckey }">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">配置分类</label> <input type=""
							name="category" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.category }">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">配置描述</label> <input type=""
							name="desc" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.desc }">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">空间名称</label> <input type=""
							name="bucket" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.bucket }">提示：标签间以，分隔
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">filter配置</label> <input type=""
							name="filters" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.filters }">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">文件参数配置</label> <input type=""
							name="fileParam" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.fileParam }">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">文件路径规则</label> <input type=""
							name="pathrule" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.pathrule }">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">baseUrls</label> <input type=""
							name="baseUrls" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.baseUrls }">
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
	</script>
</body>
</html>

