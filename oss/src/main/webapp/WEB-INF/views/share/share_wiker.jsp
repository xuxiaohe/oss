<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%
	String cpath = request.getContextPath();
	String cbasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ cpath + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no,target-densitydpi=device-dpi" />
<link href="<%=cbasePath%>/resources/wiker/css/main.css" rel="stylesheet"
	type="text/css">
<title>Wiker--图片加声音, 让课件更好玩</title>
</head>

<body>
	<div class="divTop">
		<div class="logo">
			<img src="<%=cbasePath%>/resources/wiker/image/logo.png" />
		</div>
		<div class="button">
			<a
				href="https://passport.yunxuetang.cn/t/yxt/iphone/weike/beta/weike.ipa"><img
				src="<%=cbasePath%>/resources/wiker/image/button.png"></a>
		</div>
		<div class="clear"></div>
	</div>
	<div class="divContent1">
		<div class="dc">

			<div id="dc_title" class="dc_content" style="margin-top: 20px">
				<!--标题-->
				${requestScope.course.data.result.title}
			</div>

			<div id="dc_content" class="dc_content">
				<!--内容-->
				${requestScope.course.data.result.yunDescription}
			</div>

			<div class="dc_bottom">
				<div id="dc_study" class="dc_b_content">${requestScope.course.data.result.comment}&nbsp;人评论</div>
				<div id="dc_fav" class="dc_b_content">${requestScope.course.data.result.favour}&nbsp;人赞过</div>
				<div id="dc_share" class="dc_b_content">${requestScope.course.data.result.shareCnt}&nbsp;人分享</div>
				<div class="clear"></div>
			</div>
			<div >
				<c:forEach items="${requestScope.course.data.result.imageModelMuArray}" var="Recourse">
					<img id="curpic" src="${Recourse.imagePath}" /><!--  课程图片-->
				</c:forEach>
				<audio id="curpic"
					src="${requestScope.course.data.result.audioPath}"
					style="display : none; width:100%;height:20px;" loop="loop" controls="controls" autoplay="autoplay">
					您的浏览器不支持 audio 标签
				</audio>
			</div>
		</div>
	</div>
	<div class="divTop">
		<div class="logo">
			<img src="<%=cbasePath%>/resources/wiker/image/logo.png" />
		</div>
		<div class="button">
			<a
				href="https://passport.yunxuetang.cn/t/yxt/iphone/weike/beta/weike.ipa"><img
				src="<%=cbasePath%>/resources/wiker/image/button.png"></a>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>