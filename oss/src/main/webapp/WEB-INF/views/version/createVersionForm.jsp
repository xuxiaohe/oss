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
<link href="${sourcePath}/resources/assets/css/font.css"
	rel="stylesheet">
<style>
#userInfoDiv div {
	padding: 10px;
}
</style>
</head>
<body>
	<div class="container-fluid">

		<ol class="breadcrumb">
			<li><a href="#">版本管理</a></li>
			<li><a href="${cbasePath}version/versionList">版本列表</a></li>

			<li class="active">创建版本</li>
		</ol>

		<div class="row">


			<div class="col-xs-9">

				<form role="form" method="post"
					action="${cbasePath}version/createVersionAction">


					<div class="form-group">
						产品线: <select class="form-control" id="vdomain" name="vdomain">
							<option value="yxt">yxt</option>
							<option value="weike">weike</option>
							<option value="pc">Pc</option>
						</select>

					</div>

					<div class="form-group">
						来源: <select class="form-control" id="vappkey" name="vappkey">
							<option value="ios">ios</option>
							<option value="android">android</option>
							<option value="pc">Pc</option>
						</select>

					</div>

					<div class="form-group">
						设备: <select class="form-control" id="device" name="device">
							<option value="iPhone">iPhone</option>
							<option value="Android">Android</option>
							<option value="Pc">Pc</option>
						</select>

					</div>


					<div class="form-group">
						版本证书: <select class="form-control" id="vcertificate"
							name="vcertificate">
							<option value="0">企业</option>
							<option value="10">官方</option>
						</select>

					</div>


					<div class="form-group">
						版本阶段: <select class="form-control" id="vphase" name="vphase">
							<option value="0">开发</option>
							<option value="10">测试</option>
							<option value="20">内侧</option>
							<option value="30">公测</option>
							<option value="40">上线</option>
						</select>

					</div>


					<div class="form-group">
						<label for="exampleInputEmail1">版本号（如：1.0.0，1.2.12，2.0.3等）</label>
						<input type="text" name="versionId" class="form-control"
							id="exampleInputEmail1" placeholder="">
					</div>


					<div class="form-group">
						<label for="exampleInputEmail1">更新描述</label> <input type="text"
							name="message" class="form-control" id="exampleInputEmail1"
							placeholder="">
					</div>


					<div class="form-group">
						<label for="exampleInputEmail1">下载地址</label> <input type="text"
							name="url" class="form-control" id="exampleInputEmail1"
							placeholder="">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">app版本描述</label> <input type="text"
							name="context" class="form-control" id="exampleInputEmail1"
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
	</script>
</body>
</html>

