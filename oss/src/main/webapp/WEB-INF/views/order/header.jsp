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
				订单汇总查询<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<li><a href="${cbasePath}order/list">全部订单</a></li>
				<li class="divider"></li>
				<li><a href="${cbasePath}order/orderByuserid">用户订单</a></li>
				<li><a href="${cbasePath}order/orderBygroupid">群组订单</a></li>
				<li><a href="${cbasePath}order/orderBycourseid">课程订单</a></li>
			</ul>
		</div>
		&nbsp;&nbsp;
		<button class="btn btn-primary" data-toggle="modal" data-target="#myModal" id="selectBtn" onclick = "javascript:loadContents();">选择</button>
	</div>
</div>
