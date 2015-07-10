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
<head>
<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/font.css"
	rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12">
				<div>
					<h3>
						周读精选 
						<br />
						<small>每周一上午更新</small>
					</h3>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<span class="glyphicon-class"></span>
				<h5>然后加入组成菜单的 HTML 代码</h5>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<span>包括250多个来自 Glyphicon Halflings 的字体图标。Glyphicons Halflings 一般是收费的，但是他们的作者允许 Bootstrap 免费使用。为了表示感谢，希望你在使用时尽量为 Glyphicons 添加一个友情链接。</span>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<div class="thumbnail">
					<img src="http://yxt-bj.qiniudn.com//news/2fbaf1d239c6f9a7a8a30247544e256b.jpg?imageMogr2/thumbnail/400x300" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<div>
				<span>
				<img src="http://yxt-bj.qiniudn.com//news/2fbaf1d239c6f9a7a8a30247544e256b.jpg?imageView2/1/w/80/h/80" class="img-circle" />
				</span>
				<span>
					今日头条
					
					<small>发布于：06-23</small>
				</span>
				<span>
					1
				</span>
				</div>
			</div>
		</div>

	</div>
	<script type="text/javascript">
	</script>
</body>
</html>