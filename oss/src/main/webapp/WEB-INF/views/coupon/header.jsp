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
				红包管理<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<%-- <li><a href="${cbasePath}coupon/couponList">红包列表</a></li>
				<li><a href="${cbasePath}coupon/activityList">活动列表</a></li>
				<li class="divider"></li> --%>
				<li><a href="${cbasePath}coupon/createView">创建卡券</a></li>
				<%-- <li><a href="${cbasePath}coupon/createView">分配红包</a></li>
				<li><a href="${cbasePath}coupon/createView">销毁红包</a></li> --%>
				<li><a href="${cbasePath}coupon/deleteView">测试人员取消领取的红包</a></li>
			</ul>
		</div>
	</div>
</div>
