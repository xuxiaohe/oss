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
				课程管理 <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<li><a href="${cbasePath}course/courseListNoShare">未分享课程</a></li>
				<li><a href="${cbasePath}course/courseListNoCheck">未审核课程</a></li>
				<li><a href="${cbasePath}course/createcourseview">创建课程</a></li>
				<li><a href="${cbasePath}course/courseBoxDetail">课程推荐绑定</a></li>
				<li><a href="${cbasePath}course/unUsedChapterList">无用章节</a></li>
				<li><a href="${cbasePath}course/unUsedLessonList">无用课时</a></li>
				<!-- <li><a href="#">批量创建</a></li>
				<li><a href="#">用户导入</a></li> -->
				<li class="divider"></li>
			</ul>
		</div>
	</div>
</div>
