<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String contextPath = request.getContextPath();
%>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
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
		<div class="row">
			<div class="col-xs-2">
				<img src="" class="img-circle" alt="" />
			</div>
			<div class="col-xs-2">
				<h4>李家强企业管理培训</h4>
			</div>
			<div class="col-xs-2">
				<img alt="" src="">
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="row">
					<div class="col-xs-12">
						<img alt="" src="">
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<span class="label label-primary">免费</span>
					</div>
				</div>
			</div>
		</div>


	</div>
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
		background-color: #f0f0f0;
	}
	
	.banner {
		
	}
	
	.banner img {
		margin: 0;
		padding: 0;
	}
	
	.content {
		padding-top: 24px;
		padding-left: 20px;
		padding-right: 20px;
	}
	
	.content .item {
		padding: 0 0 24px;
		clear: both;
		height: 145px;
		margin: 0 0 44px;
	}
	
	.imgBackground {
		margin: 0;
		padding: 0;
		float: left;
		height: 145px;
		border-bottom-left-radius: 0;
		border-bottom-right-radius: 0;
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
	</style>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>