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
			<li><a href="#">干货仓库</a></li>
			<li><a href="${cbasePath}user/userList">干货列表</a></li>
			<li><a
				href="${cbasePath}user/userDetail?userid=${resuserDetail.data.result.id}">干货详情:
					<small> ${resuserDetail.data.result.nickName }</small>
			</a></li>
			<li class="active">干货编辑</li>
		</ol>

		<div class="row">

			<div class="col-xs-3">
				<img class="thumbnail col-xs-12" src="${resuserDetail.data.result.logoURL}" alt="" />

				<%-- <c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach> --%>
			</div>

			<div class="col-xs-9">
				 
				<form role="form" method="post" action="${cbasePath}user/updateUser?userid=${resuserDetail.data.result.id}">
					<div class="form-group">
						<label for="exampleInputEmail1">性别</label> <input type=""
							name="sex" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.sex }"> 提示：1男2女
					</div>
					
					<div class="form-group">
						<label for="exampleInputEmail1">手机号</label> <input type=""
							name="phoneNumber" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.phoneNumber }">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">邮箱</label> <input type=""
							name="email" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.email }">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">标签</label> <input type=""
							name="tag" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.tag }">提示：标签间以，分隔
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">头像图片地址</label> <input type=""
							name="logoURL" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.logoURL }">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">信息</label> <input type=""
							name="intro" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.intro }">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">别名</label> <input type=""
							name="nickName" class="form-control" id="exampleInputEmail1"
							placeholder="" value="${resuserDetail.data.result.nickName }">
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

