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
		<button type="button" class="btn btn-primary">数据统计</button>
		
		<a href="http://v1.bj.api.ztiao.cn/oss/schedule/addIndex?token=717E13E6AEA8AD5BA2086207C5EB807B">
			<button type="button" class="btn btn-primary">刷新app首页数据</button>
			</a>
	</div>
	<!-- <div class="panel-body">
		<a href="http://v1.bj.api.ztiao.cn/oss/schedule/addIndex?token=717E13E6AEA8AD5BA2086207C5EB807B">
			<button type="button" class="btn btn-primary">刷新app首页数据</button>
			</a>
	</div> -->
</div>
