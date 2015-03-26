<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%
	String cpath = request.getContextPath();
	String cbasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ cpath + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<link href="<%=cbasePath%>/resources/wiker/css/list.min.css" rel="stylesheet"
	type="text/css">
<title>轻单 | 发现一个更轻的世界</title>
</head>

<body>
	<div class="content">
		<div class="wrap">
			<!-- 标签 -->
			<div class="topic-info-container"></div>
			<article class="container">
				<header>
					<div class="cover">
						<!-- 封面图片 -->
						<div class="embed-container ratio-640-300">
							<img alt="" src="${requestScope.o.data.result.coverUrl}">
						</div>
					</div>
					<div class="detail">
						<!-- 标题 -->
						<h1>${requestScope.o.data.result.name}</h1>
						<!-- 描述 -->
						<p>${requestScope.o.data.result.des}</p>
					</div>
					
				</header>
				
				<section>
					<ol class="items type-num">
						<c:forEach items="${requestScope.o.data.result.itemArray}" var="i">
							<li class="parent">
								<div class="title">
									<span></span>
									<h2>${i.name}</h2>
								</div>
								<div class="note">
									<c:if test="${i.picUrl != ''}">
										<figure>
											<img alt="" src="${i.picUrl}">
										</figure>
									</c:if>
									<p>${i.des}</p>
								</div>
							</li> 
						</c:forEach>
					</ol>
					<footer class="action-bar">
						<div class="col">
							<button title="共浏览了44次" class="button btn-view">
								<i class="qlisticon icon-remove-red-eye skin-cl" ></i>
								<span class="count">44</span>
							</button>
							<button data-id="VQ-X-hccugejWUCp" title="喜欢" class="btn-ui-like require-login"><i class="qlisticon icon-favorite-outline skin-cl"></i><span class="count">28</span></button>
						</div>
					</footer>
				</section>
			</article>
			
		</div>
	</div>
</body>
</html>