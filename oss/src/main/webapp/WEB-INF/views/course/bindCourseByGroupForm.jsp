<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%
	String contextPath = request.getContextPath();
%>
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
				href="${cbasePath}course/courseDetail?cid=${resuserCourse.data.result.id}">课程详情
					
			</a></li>
			<li class="active">课程绑定群组</li>
		</ol>

		<div class="row">
			<div class="col-xs-3">
				<img class="thumbnail col-xs-12"
					src="${resuserCourse.data.result.logoUrl}" alt="" />

				<%-- <c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach> --%>
			</div>


			<div class="col-xs-9">
				
				<a href="${url }" target="_blank">${resuserCourse.data.result.message }</a>
				<form role="form" method="post"
					action="${cbasePath}course/shareToMyGroup?courseId=${resuserCourse.data.result.id}">


					<div class="form-group">
						<label for="exampleInputEmail1">群组</label> 
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
							  选择群组
							</button>
							<input type="text" id="qnametext" class="form-control" value=""/>
							<input type="hidden" id="qidtext" name="groupId"/>
					</div>

					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>


		<!-- Modal -->
		<!-- Modal -->
		<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">群组搜索</h4>
					</div>
					<div class="modal-body" id="modalHtml"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
						<!-- <button type="button" class="btn btn-primary">确定</button> -->
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
			$(".btnDelete").click(function() {
				if (window.confirm('你确定要删除吗？')) {
					window.location.href = $(this).attr("data");
				} else {

				}
			});
			var uid = '${resuserCourse.data.result.createUser}';
			if(uid == 'null' || uid == '') return;
			$.ajax({
				url : '<%=contextPath%>/course/selectGroup',
				data : {'uid' : uid},
				type : 'post',
				dataType : 'html',
				success : function(data){
					$("#modalHtml").html("").html(data);
				}
			});
		});
		function selectGroup(groupId, groupName){
			var $groupSelec = $("#qnametext");
			$groupSelec.val(groupName);
			$("#qidtext").val(groupId);
		}
	</script>
</body>
</html>

