<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
				推荐管理 <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<li><a href="${cbasePath}area/BoxDryList?type=dry">干货推荐管理</a></li>
				<li><a href="${cbasePath}area/BoxDryList?type=xuanye">炫页推荐管理</a></li>
				<li><a href="${cbasePath}area/BoxDryList?type=topic">话题推荐管理</a></li>
				<li><a href="${cbasePath}area/BoxDryList?type=group">群组推荐管理</a></li>
				<li><a href="${cbasePath}area/BoxDryList?type=course">课程推荐管理</a></li>
				<li><a href="${cbasePath}area/BoxDryList?type=activity">活动推荐管理</a></li>
				<!-- <li><a href="#">用户导入</a></li> -->
				<li class="divider"></li>
			</ul>
		</div>
		<a
		href="${cbasePath}area/addDryBoxForm">
		<button type="button" class="btn btn-default dropdown-toggle">新建推荐管理</button>
	</a>
	</div>
	
</div>
