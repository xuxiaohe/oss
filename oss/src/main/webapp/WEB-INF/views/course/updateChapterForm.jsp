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
			<li><a href="#">课程仓库</a></li>
			<li><a href="${cbasePath}course/courseList">课程列表</a></li>
			<li><a
				href="${cbasePath}course/courseDetail?cid=${courseDetail.data.result.id}">课程详情

			</a></li>
			<li class="active">课程编辑</li>
		</ol>

		<div class="row">
			<div class="col-xs-9">
			<c:forEach items="${chapters}" varStatus="key" var="Recourse">
			<div>
				<label style="background: red" for="exampleInputEmail1">章节名称</label>
				<input type="hidden" id="id${Recourse.id}" value="${Recourse.id}">
				<input id="title${Recourse.id}"  value="${Recourse.title}">
				<label for="exampleInputEmail1">序号</label>
				<input type="text" id="order${Recourse.id}" value="${Recourse.order}">
				<input type="button" onclick="updateChapter('${Recourse.id}')" value="保存章节">
					<c:forEach items="${Recourse.lessons}" varStatus="key" var="lessons">
					<div>
						<label style="background: pink" for="exampleInputEmail1">课时名称</label>
						<input  id="id${lessons.id}" type="hidden" value="${lessons.id}">
						<input type="text"  id="title${lessons.id}" value="${lessons.title}">
						<label for="exampleInputEmail1">序号</label>
						<input type="text"  id="order${lessons.id}" value="${lessons.order}">
						<label for="exampleInputEmail1">是否可看</label>
						<input type="text"  id="isbuy${lessons.id}" value="${lessons.isbuy}">
						<input type="button" onclick="updateLesson('${lessons.id}')" value="保存课时">
						<%-- <div>
							<label style="background: yellow" for="exampleInputEmail1">知识名称</label>
							<input type="text" value="${lessons.knowledge.id}">
							<label for="exampleInputEmail1">${lessons.knowledge.name}<input type="submit" value="重新上传"></label>
							
						</div> --%>
					</div>
					</c:forEach>
					
						
			</div>
			</c:forEach>
			</div>
		</div>

		
	</div>
	<script src="${cbasePath}/resources/assets/js/html5shiv.js"></script>
	<script src="${cbasePath}/resources/assets/js/plupload.full.min.js"></script>
	<script src="${cbasePath}/resources/assets/js/qiniu.js"></script>
	<script src="${cbasePath}/resources/assets/js/moxie.min.js"></script>
	<script>
		function updateChapter(id){
			$.ajax({
				url : '${cbasePath}course/updateChapter',
				data : {'id' : $("#id"+id).val(),'title':$("#title"+id).val(),'order':$("#order"+id).val()},
				type : 'post',
				dataType : 'json',
				success : function(result){
					if(result.data.result==true){
						alert("修改成功！");
					}
				}
			});
		}
		
		function updateLesson(id){
			$.ajax({
				url : '${cbasePath}course/updateLesson',
				data : {'id' : $("#id"+id).val(),'title':$("#title"+id).val(),'order':$("#order"+id).val(),'isbuy':$("#isbuy"+id).val()},
				type : 'post',
				dataType : 'json',
				success : function(result){
					if(result.data.result==true){
						alert("修改成功！");
					}
				}
			});
		}
	
		$(function() {
			
		});
	</script>
</body>
</html>

