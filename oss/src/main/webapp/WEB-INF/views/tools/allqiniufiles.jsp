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
			<li><a
				href="${cbasePath}dry/dryDetail?dryid=${resuserTopic.data.result.id}">干货详情:
			</a></li>
			<li class="active">干货编辑</li>
		</ol>

		<div class="row">

			点击图片查看图片地址 <br>
				 
     	
     			
				<%-- <c:forEach items="${imageurls}" varStatus="key" var="img">
					<img src="http://yxt-bj.qiniudn.com/${img}" alt="" />
				</c:forEach> --%>
				
				
				<c:forEach items="${imageurls}"
								varStatus="key" var="img">
								
								<div class="col-xs-2">
									  <img src="http://yxt-bj.qiniudn.com/${img}"
										onclick="sendurll('http://yxt-bj.qiniudn.com/${img}')" >
										<br>
										<%-- <div class="caption">
											<p class="text-center">${group.nickName}</p>
											<a
								href="${cbasePath}group/kickGroupMenber?userId=${group.id }&gid=${gid}&ownerid=${ownerid}">
								<button type="button" class="btn btn-success btn-block">踢出</button>
							</a>
										</div> --%>
									 
								</div>
								
							</c:forEach>
		 

			
			</div>
		</div>

图片路径为：${imageurl}
	 
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
			alert(url);
		}
	</script>
</body>
</html>

