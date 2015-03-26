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
			<li><a href="#">用户管理</a></li>
			<li><a href="${cbasePath}config/configList">配置列表</a></li>
			 
			<li class="active">配置创建</li>
		</ol>

		<div class="row">
		


			<div class="col-xs-9">
				<form role="form" method="post"
					action="${cbasePath}config/createConfig">


					<div class="form-group">
						<label for="exampleInputEmail1">文件配置key</label> <input type="text"
							name="ckey" class="form-control" id="exampleInputEmail1"
							  placeholder="">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">配置分类</label> <input type="text"
							name="category" class="form-control" id="exampleInputEmail1"
							  placeholder="">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">配置描述</label> <input type="text"
							name="desc" class="form-control" id="exampleInputEmail1"
							  placeholder="">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">空间名称</label> <input type="text"
							name="bucket" class="form-control" id="exampleInputEmail1"
							  placeholder="">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">filter配置</label> <input type="text"
							name="filters" class="form-control" id="exampleInputEmail1"
							  placeholder="格式：{mime_types: [{ title :'Image files',extensions:'jpg,jpeg,gif,png' }],max_file_size : '2MB',  prevent_duplicates : true}">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">文件参数配置</label> <input type="text"
							name="fileParam" class="form-control" id="exampleInputEmail1"
							  placeholder="格式：{width:80,height:80,defaulturl:’g/img/doc_default.png’,maxfiles:1}">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">文件路径规则</label> <input type="text"
							name="pathrule" class="form-control" id="exampleInputEmail1"
							  placeholder="格式：kng/video/{yyyymm}/">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">baseUrls</label> <input type="text"
							name="baseUrls" class="form-control" id="exampleInputEmail1"
							  placeholder="格式：[ 'http://tpublic.yunxuetang.com/']">
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

