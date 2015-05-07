<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%
	String cpath = request.getContextPath();
	String cbasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ cpath + "/";
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="btn-group">
			<button type="button" class="btn btn-default dropdown-toggle"
				data-toggle="dropdown">
				Banner管理 <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<li><a href="<%=cpath%>/banner/createView">创建Banner</a></li>
				<li class="divider"></li>
			</ul>
		</div>
	</div>
</div>
