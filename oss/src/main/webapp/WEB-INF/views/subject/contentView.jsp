<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String contextPath = request.getContextPath();
%>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>活动专题</title>

<!-- Bootstrap -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?026d612dbab361df1dd0624d521eebe5";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<body>
	<div class="container-fluid" style='background-image: ${logoUrl} '>
		<div id="header" class="row banner">
			
							<img src="${logoUrl}" class="col-xs-12" alt="" />
		</div>
		<!-- <div id="content" class="content"> -->
			<c:forEach items="${specialInfo.data.result}" var="Resource">
				<c:if test="${Resource.content.type=='01'}">
						<!-- 话题  -->
					<div class="row content">
	
						<div class="row">
							<div class="col-xs-12">
	
								<h5>
									<span class="glyphicon glyphicon-comment"></span> <a class="itemTitle"
										href="objc://ztiao?openNative=3&sourceId=${Resource.content.sourceId}&groupId=${Resource.content.groupId}">${Resource.content.title}</a>
								</h5>
	
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<span class="itemContent">
								 <a 
										href="objc://ztiao?openNative=3&sourceId=${Resource.content.sourceId}&groupId=${Resource.content.groupId}">
								 ${fn:substring(Resource.content.content, 0, 50)}
								 </a>
								  </span>
							</div>
						</div>
						<div class="row">
	
							<div class="col-xs-12">
								<div class="thumbnail imgItem">
									 <a class="itemTitle"
										href="objc://ztiao?openNative=3&sourceId=${Resource.content.sourceId}&groupId=${Resource.content.groupId}">
									<img src="${Resource.content.images[0].picUrl}" />
									</a>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-2 clearfix">
								<a href="objc://ztiao?openNative=1&sourceId=${Resource.content.autherId}"><span>
										<img src="${Resource.content.autherLogoUrl}?imageView2/1/w/30/h/30"
										class="img-circle userLogo" />
								</span></a>
							</div>
							<div class="col-xs-10 clearfix">
								<div class="row">
									<div class="col-xs-12">
										<small class="itemUserName">${Resource.content.autherNickName} </small>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-12">
										<small class="itemDate">发布于：<span
											class="date"><Date:date value="${Resource.content.ctime}"></Date:date></span></small>
									</div>
								</div>
	
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${Resource.type=='11'}">
					<!-- 课程 -->
					<div class="row content">
						<div class="row">
							<div class="col-xs-12">
	
								<h5>
									<span class="glyphicon glyphicon-book"></span> <a class="itemTitle"
										href="objc://ztiao?openNative=2&sourceId=${Resource.content.courseId}&groupId=${Resource.content.groupId}&groupCourseId=${Resource.content.sourceId}">${Resource.content.title}</a>
								</h5>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<span class="itemContent"><a 
										href="objc://ztiao?openNative=2&sourceId=${Resource.content.courseId}&groupId=${Resource.content.groupId}&groupCourseId=${Resource.content.sourceId}"> ${fn:substring(Resource.content.content, 0, 50)} </a></span>
							</div>
							<div class="col-xs-12">
								<div class="thumbnail imgItem">
									<a 
										href="objc://ztiao?openNative=2&sourceId=${Resource.content.courseId}&groupId=${Resource.content.groupId}&groupCourseId=${Resource.content.sourceId}">
									<img src="${Resource.content.images[0].picUrl}" />
									</a>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-2 clearfix">
								<a href="objc://ztiao?openNative=1&sourceId=${Resource.content.autherId}"><span>
										<img src="${Resource.content.autherLogoUrl}?imageView2/1/w/30/h/30"
										class="img-circle userLogo" />
								</span></a>
							</div>
							<div class="col-xs-10 clearfix">
								<div class="row">
									<div class="col-xs-12">
										<small class="itemUserName">${Resource.content.autherNickName} </small>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-12">
										<small  class="itemDate">发布于：<span
											class="date"><Date:date value="${Resource.content.ctime}"></Date:date></span></small>
									</div>
								</div>
	
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${Resource.type=='21'}">
					<!-- 干货 -->
					<div  class="row content">
						<div class="row">
							<div class="col-xs-12">
	
								<h5>
									<span class="glyphicon glyphicon-link"></span> <a class="itemTitle"
										href="objc://ztiao?openNative=5&sourceId=${Resource.content.sourceId}&groupId=${Resource.content.groupId}">${Resource.content.title}</a>
								</h5>
							</div>
						</div>
						<div class="row">
	
							<div class="col-xs-12">
								<span class="itemContent"> 
								<a 
										href="objc://ztiao?openNative=5&sourceId=${Resource.content.sourceId}&groupId=${Resource.content.groupId}">${fn:substring(Resource.content.content, 0, 50)}</a></span>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="thumbnail imgItem">
									<a
										href="objc://ztiao?openNative=5&sourceId=${Resource.content.sourceId}&groupId=${Resource.content.groupId}">
									<img src="${Resource.content.images[0].picUrl}" />
									</a>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-2 clearfix">
								<a href="objc://ztiao?openNative=1&sourceId=${Resource.content.autherId}"><span>
										<img src="${Resource.content.autherLogoUrl}?imageView2/1/w/30/h/30"
										class="img-circle userLogo" />
								</span></a>
							</div>
							<div class="col-xs-10 clearfix">
								<div class="row">
									<div class="col-xs-12">
										<small class="itemUserName">${Resource.content.autherNickName} </small>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-12">
										<small  class="itemDate">发布于：<span
											class="date"><Date:date value="${Resource.content.ctime}"></Date:date></span></small>
									</div>
								</div>
	
							</div>
						</div>
				</div>
				</c:if>


			</c:forEach>
			
		</div>
	<!-- </div> -->
	<style>
	.titleDiv {
		background-color: #ffffff;
		min-height: 24px;
		font-size: 14px;
		padding: 5px 5px 5px 10px;
		color: #585858;
		-moz-border-radius-bottomleft: 10px;
		-moz-border-radius-bottomright: 10px;
		-webkit-border-bottom-left-radius: 10px;
		-webkit-border-bottom-right-radius: 10px;
		border-bottom-right-radius: 10px;
		border-bottom-left-radius: 10px;
	}
	
	.titleDiv a {
		color: #585858;
	}
	
	body {
		font-family: "Microsoft YaHei", "黑体", "Helvetica Neue",
			"Hiragino Sans GB", Arial, sans-serif;
		background-color: #ebebeb;
	}
	
	a{
		color : #585858;
	}
	a:link{
	text-decoration:none;
	}
	a:visited{
	text-decoration:none;
	}
	a:hover{
	text-decoration:none;
	}
	a:active{
	text-decoration:none;
	}
	
	.banner {
		
	}
	
	.banner img {
		margin: 0;
		padding: 0;
	}
	
	.imgItem{
		border : 0;
		padding : 0;
	}
	
	.content {
		margin-top:15px;
		padding-top: 10px;
		padding-left: 10px;
		padding-right: 10px;
		background-color: #ffffff;
	}
	
	
	.imgBackground {
		margin: 0;
		padding: 0;
		float: left;
		height: 145px;
		border-bottom-left-radius: 0;
		border-bottom-right-radius: 0;
	}
	
	.itemimg{
		margin : 0 0 0 0px;
	}
	
	.price {
		margin-top: 113px;
		padding: 0 0 0 10px;
		font-weight: lighter;
		font-size: 20px;
		background-color: #40a2c4;
		height: 30px;
		z-index: 10;
	}
	
	.textStyle {
		margin: 0;
		padding: 0;
		height: 145px;
		background-image: url(http://yxt-bj.qiniudn.com/h5/static/Activity_hongbao.png);
		background-size: 100% 100%;
		color: #ffff00;
		border-bottom-left-radius: 0;
		border-bottom-right-radius: 0;
	}
	
	.pickCoupon {
		position: relative;
		top: 10px;
		color: #ffff00;
	}
	
	.pickCoupon a {
		margin-bottom : 20px;
		color: #ffff00;
	}
	
	.FontSize14 {
		font-size: 14px;
		color: #ffffff;
	}
	
	.ColorWhite {
		color: #ffffff;
	}
	
	.userLogo{
		margin-left:10px;
		width : 40px;
		height : 40px;
	}
	
	.logo{
		margin-bottom:5px;
	}
	
	.rightGo{
		margin-right:10px;
	}
	
	
	.itemTitle{
		color : #333333;
	}
	
	.itemContent{
		color : #666666;
	}
	
	.itemUserName{
		color : #333333;
	}
	
	.itemDate{
		color : #999999;
	}
	</style>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$(".date").each(function(i){
			var d = $.trim($(this).html());
			$(this).html(d.substr(0, 10));
		});
	});
	</script>
</body>
</html>