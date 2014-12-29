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
    <title>用户管理</title>
    <script src="<%=cbasePath%>/resources/assets/js/jquery.min.js"></script>
    <script src="<%=cbasePath%>/resources/assets/js/bootstrap.min.js"></script>
    <link href="<%=cbasePath%>/resources/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=cbasePath%>/resources/assets/css/font.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    Action <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li class="divider"></li>
                    <li><a href="#">Separated link</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <nav>
                <ul class="pagination">
                    <li><a href="#">&laquo;</a></li>
                    <li><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">&raquo;</a></li>
                </ul>
            </nav>
            <!---数据显示区域-->
            <c:forEach items="${courSharReso.data.result}" var="Recourse">
            <div class="row" style="padding: 20px;">
                <div class="col-xs-1">
                    <div class="row">
                        <img class="col-xs-12 thumbnail" src="${Recourse.logoURL}" style="margin-top: 10px;" alt=""/>
                    </div>
                </div>
                <div class="col-xs-11">
                    <h5><a href="<%=cbasePath%>/user/userDetail?userid=${Recourse.id}">${Recourse.nickName}${Recourse.userName}</a><small><small class="pull-right">注册时间：2014-12-29</small></small></h5>

                    <div>
                        所在群组：5个
                    </div>
                    <div class="btn-group-sm">
                        <button type="button" class="btn btn-primary">禁用</button>
                        <button type="button" class="btn btn-primary">查看</button>
                        <button type="button" class="btn btn-primary">修改密码</button>
                        <button type="button" class="btn btn-primary">编辑</button>
                    </div>

                </div>
            </div>
           </c:forEach>
         </div>
    </div>
</div>
<script>
    $(function(){
        
    });
    </script>
</body>
</html>

