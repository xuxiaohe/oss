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
				干货管理 <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<li><a href="${cbasePath}dry/createDryByGroupView">干货创建</a></li>
				<li><a href="${cbasePath}dry/catchDryByGroupView">干货抓取</a></li>
				<%-- <li><a href="${cbasePath}dry/addDryBoxForm">干货排行榜创建</a></li>
				<li><a href="${cbasePath}dry/BoxDryList">干货排行榜管理</a></li> --%>
				<li><a href="${cbasePath}dry/DryBoxDetail">干货与推荐绑定</a></li>
				<li><a href="${cbasePath}dry/noCheckDryList">未审核干货列表</a></li>
				<li><a href="${cbasePath}dry/catchDryList">定时抓取干货列表</a></li>
				<%-- <li><a href="${cbasePath}dry/DryBoxBindedDetail">干货已关联排行榜管理</a></li> --%>
				<!-- <li><a href="#">用户导入</a></li> -->
				<li class="divider"></li>
			</ul>
		</div>
	</div>
</div>
