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
				知识管理 <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<%-- <li><a href="${cbasePath }knowledge/upload">上传知识</a></li> --%>
				<%-- <li><a href="#">创建普通用户</a></li>
				<li><a href="#">批量创建</a></li>
				<li><a href="${cbasePath}user/importUserView">用户导入</a></li>
				<li><a href="${cbasePath}user/roboitList">马甲用户列表</a></li> --%>
				<li><a href="${cbasePath }knowledge/knowledgeList">未审核知识</a></li>
				<li class="divider"></li>
			</ul>
		</div>
		<!-- <button type="button" class="btn btn-primary">用户排行榜</button> -->
	</div>
</div>
