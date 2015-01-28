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
<head lang="en">
<meta charset="UTF-8">
<title>运营支持系统</title>
<script src="<%=cbasePath%>/resources/assets/js/jquery.min.js"></script>
<script src="<%=cbasePath%>/resources/assets/js/bootstrap.min.js"></script>
<link href="<%=cbasePath%>/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=cbasePath%>/resources/assets/css/font.css"
	rel="stylesheet">
<style>
#mainMenu li span {
	padding: 5px;
	margin-left: 10px;
	margin-right: 10px;
}
</style>
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
</head>
<!--主菜单加载和框架页调用-->
<body>
	<div class="container">
		<div class="row">
			<div class="col-xs-4 col-xs-offset-4 panel panel-default " style="padding:20px;">
				<form role="form">
					<div class="form-group">
						<label for="exampleInputEmail1">用户名</label> <input
							type="email" class="form-control" id="exampleInputEmail1"
							placeholder="Enter email">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">密码</label> <input
							type="password" class="form-control" id="exampleInputPassword1"
							placeholder="Password">
					</div>
					
					
					<button type="submit" class="btn btn-success">Submit</button>
				</form>
			</div>
		</div>

	</div>
	<script>
		$(function() {
			$("#mainMenu li").click(function() {
				$(this).siblings().removeClass("active");
				$(this).addClass("active");
			});
			$("#mainFrame").load(
					function() {
						var mainheight = $(this).contents().find("body")
								.height() + 130;
						$(this).height(mainheight);
					});
		});
	</script>
</body>
</html>
