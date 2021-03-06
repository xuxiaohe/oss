<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>APP广告位管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
</head>

<body>

	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
				<ol class="breadcrumb">
					<c:if test="${adId=='0' || adId == null}">
						<li>APP首页广告列表</li>
					</c:if>
					<c:if test="${adId=='1'}">
						<li>探索页页广告列表</li>
					</c:if>
				</ol>
				<%-- <form class="form-inline" action="${cbasePath}banner/bannerlist"
					method="get" role="form">
					<div class="form-group">
						<label class="sr-only" for="keyword">Search:</label> <input
							class="form-control" id="keyword" name="keyword"
							placeholder="Enter keyword" value="${keyword}">
					</div>

					<button id="searchIt" type="submit" class="btn btn-default">Search
						it!</button>
				</form> --%>
				<c:if test="${BannerList.status == '200'}">
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${BannerList.data.curr_page}"
							totalPages="${BannerList.data.page_rows}" perPageRows="10"
							totalRows="${BannerList.data.total_rows}"
							linkBaseUrl="${cbasePath}banner/bannerlist?adId=${adId}">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>

					<!---数据显示区域-->
					<%-- ${fn:length(Drys.data.result)} --%>
					<c:forEach items="${BannerList.data.result.result.content}" varStatus="key"
						var="Recourse">
						<div class="row" style="padding: 20px;">
							<div class="col-xs-1">
								<h5 style="margin-top: 40px;">
									<span class="label label-default">${key.count}</span>
								</h5>
							</div>

							<div class="col-xs-1">
								<div class="row">
									<img class="col-xs-12 thumbnail" src="${Recourse.picUrl}"
										style="margin-top: 10px;" alt="" />
								</div>
							</div>
							<div class="col-xs-10">
								<h4 style="margin-left: 12px;">
									<a href="${cbasePath}banner/bannerDetail?id=${Recourse.id}">
										渠道商名称：${Recourse.adSellerName} </a><br> <small><small
										class="pull-right">注册时间：<Date:date
												value="${Recourse.ctime}"></Date:date></small></small>
								</h4>
							&nbsp&nbsp广告位名称：${Recourse.name} &nbsp;&nbsp;&nbsp;&nbsp;广告位置序列：${Recourse.index+1}
							<br>
							广告位置:
							<c:if test="${adId=='0' || adId == null}">
								APP首页
							</c:if>
							<c:if test="${adId=='1'}">
								探索页
							</c:if>
							
							<c:if test="${Recourse.adSid=='0'}">  
							&nbsp&nbsp位置：站内   
							</c:if>
							<c:if test="${Recourse.adSid=='10'}">  
							&nbsp&nbsp位置：站外（企业大学）   
							</c:if>
							<c:if test="${Recourse.adSid=='20'}">  
							&nbsp&nbsp位置：站外（机构网校）   
							</c:if>
							<c:if test="${Recourse.effective==true}">  
							&nbsp&nbsp是否显示：是 
							</c:if>
							<c:if test="${Recourse.effective==false}">  
							&nbsp&nbsp是否显示：否
							</c:if>
							
								<div class="col-xs-12 btn-group-sm">
								<c:if test="${Recourse.index!=0}">  
								<a href="${cbasePath}banner/movebanner?currindex=${Recourse.index}&state=0&adId=${adId}">
									<button  type="button"
										class="up btn btn-primary">位置上移</button></a></c:if>
							    <c:if test="${Recourse.index != (BannerList.data.result.counts)-1}"> 
									<a href="${cbasePath}banner/movebanner?currindex=${Recourse.index}&state=1&adId=${adId}">	
										<button  type="button"
										class="down btn btn-primary">位置下移</button></a></c:if>
								</div>

							</div>
						</div>
					</c:forEach>
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${BannerList.data.curr_page}"
							totalPages="${BannerList.data.page_rows}" perPageRows="10"
							totalRows="${BannerList.data.total_rows}"
							linkBaseUrl="${cbasePath}banner/bannerlist?adId=${adId}">
						</pageNation:PageNation>
					</ul>

					<!-- 分页结束 --> </nav>
				</c:if>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			/* $("#searchIt").click(function(){
				window.location.href = "${cbasePath}user/userList?keyword="+encodeURI($("#keyword").val());
			}); */
			
			$(".deleteBtn")
					.click(
							function() {
								if (window.confirm('你确定要删除吗？')) {
									//alert("${cbasePath}dry/deleteDry?dryid="+$(this).attr("data"));
									window.location.href = "${cbasePath}dry/deleteDry?dryid="
											+ $(this).attr("data")+"&uid="+$(this).attr("data1");
								} else {

								}
							});
			
			
			$(".info")
			.click(
					function() {
						 
							//alert("info");
							window.location.href = "${cbasePath}dry/dryDetail?dryid="
									+ $(this).attr("data");
						 
					});
		});
	</script>
</body>
</html>

