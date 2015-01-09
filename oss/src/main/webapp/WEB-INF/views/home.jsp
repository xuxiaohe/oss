<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%
String cpath = request.getContextPath();
String cbasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+cpath+"/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>运营支持系统</title>
    <script src="<%=cbasePath%>/resources/assets/js/jquery.min.js"></script>
    <script src="<%=cbasePath%>/resources/assets/js/bootstrap.min.js"></script>
    <link href="<%=cbasePath%>/resources/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=cbasePath%>/resources/assets/css/font.css" rel="stylesheet">
    <style>
        #mainMenu li span {
            padding: 5px;
            margin-left: 10px;
            margin-right: 10px;
        }
    </style>
</head>
<!--主菜单加载和框架页调用-->
<body>
<div class="container">
    <div class="row">
        <div class="col-md-2">
            <div class="text-center" style="text-align: center;padding-top: 20px;">
                <img src="<%=cbasePath%>/resources/assets/image/logo.png" class="col-xs-12" alt=""/>
            </div>
            <div class="text-center" style="text-align: center;padding: 20px;">
                <h4>运营支持系统OSS</h4>
            </div>
            <div class="menu">
                <ul id="mainMenu" class="nav nav-pills nav-stacked" role="tablist">
                    <li role="presentation" class="active">
                        <a href="<%=cbasePath%>user/userList" target="mainFrame"><span class="glyphicon glyphicon-cloud"></span>控制中心</a>
                    </li>
                    <li role="presentation">
                        <a href="<%=cbasePath%>user/userList" target="mainFrame"><span class="glyphicon glyphicon-star"></span>用户管理</a>
                    </li>
                    <li role="presentation">
                        <a href="<%=cbasePath%>group/groupList" target="mainFrame"><span class="glyphicon glyphicon-star"></span>群组管理</a>
                    </li>
                    <li role="presentation">
                        <a href="<%=cbasePath%>dry/dryList" target="mainFrame"><span class="glyphicon glyphicon-star"></span>干货仓库</a>
                    </li>
                    <li role="presentation">
                        <a href="#" target="mainFrame"><span class="glyphicon glyphicon-star"></span>课程仓库</a>
                    </li>
                    <li role="presentation">
                        <a href="#" target="mainFrame"><span class="glyphicon glyphicon-star"></span>话题仓库</a>
                    </li>
                    <li role="presentation">
                        <a href="#" target="mainFrame"><span class="glyphicon glyphicon-star"></span>标签仓库</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="col-md-10">
            <iframe style="min-height:1600px;" id="mainFrame" name="mainFrame" class="col-md-12" src="<%=cbasePath%>user/userList" frameborder="0"></iframe>
        </div>
    </div>

</div>
<script>
    $(function () {
        $("#mainMenu li").click(function () {
            $(this).siblings().removeClass("active");
            $(this).addClass("active");
        });
        $("#mainFrame").load(function () {
            var mainheight = $(this).contents().find("body").height() + 130;
            $(this).height(mainheight);
        });
    });
</script>
</body>
</html>
