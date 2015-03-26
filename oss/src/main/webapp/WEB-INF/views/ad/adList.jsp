<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>用户管理</title>
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
				<form class="form-inline" role="form" action="">
				<div class="col-xs-6">
					<div class="form-group form-group-sm">
						<label style="width: 200px;" >开始日期：</label>
						<input type="text" class="form-control datein" placeholder="2015-02-03" name="ctime" value="${ctime}">
					</div>
					</div>
					<div class="col-xs-6">
					<div class="form-group form-group-sm">
						<label style="width: 200px;">结束日期：</label>
						<input type="text" class="form-control" name="etime"  id="etime" placeholder="2015-02-03"  value="${etime}">
					</div>
				</div>
				
				<div class="col-xs-6" style="margin-top:15px;">
					<div class="form-group form-group-sm">
						<label style="width: 200px;">渠道名称：</label>
						<input type="text" class="form-control" name="qdName" id="qdName" value="${qdName}">
					</div>
				</div>
				
				<div class="col-xs-6" style="margin-top:15px;">
					<div class="form-group form-group-sm">
						<label style="width: 200px;">渠道ID：</label>
						<input type="text" class="form-control" name="qdId" id="qdId"  value="${qdId}">
					</div>
				</div>
				<div class="col-xs-6 pull-left text-right" style="margin-top:15px;">
					<button type="submit" class="btn btn-primary">筛选</button>
				</div>
				
				<div class="col-xs-6 pull-left text-right" style="margin-top:15px;">
					<button type="button" class="btn btn-primary" onclick="exportcsv();">导出</button>
				</div>
				<script>
					function exportcsv(){
						 var ctime=$("#ctime").val();
						 var etime=$("#etime").val();
						 var qdName=$("#qdName").val();
						 var qdId=$("#qdId").val();
							$.post("export",{"ctime":ctime,"etime":etime,"qdId":qdId,"qdName":qdName},function(data){
								alert(data.url);
								top.location.href=data.url;
							});
					}
				</script>
			</form>
				
				<c:if test="${adList.status == '200'}">
						<div class="col-xs-12">
				<hr />
				<table class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>渠道</th>
							<th>ID</th>
							<th>广告位</th>
							<th>注册人数</th>
							<th>创建人</th>
							<th>创建时间</th>
							<th>点击数</th>
							<th width="60">链接形式</th>
							<th width="70">操作</th>
							
						</tr>
					</thead>
					<tbody>
					<!---数据显示区域-->
				

					<c:forEach items="${adList.data.result}" varStatus="key" var="Recourse">
						<tr>
							<td>${Recourse.adSellerName }</td>
							<td>${Recourse.adSellerId }</td>
							<td>${Recourse.name }</td>
							<td>${Recourse.rcount }</td>
							<td>${Recourse.creater }</td>
							<td><Date:date value="${Recourse.adTime}"></Date:date></td>
							<td>${Recourse.ccount }</td>
							<td ><a  target="_Blank" href="http://ztiao.cn/Channel/Register?a=${Recourse.adId }">http://ztiao.cn/Channel/Register?a=${Recourse.adId }</a></td>
							<td><button class="detailBtn" data="${Recourse.adSellerId}">详情</button></td>
						</tr>
					</c:forEach>
						
					</tbody>
				</table>
			</div>
					
					<nav> <!-- 分页开始 -->
					<ul class="pagination">
						<pageNation:PageNation currPage="${adList.data.curr_page}"
							totalPages="${adList.data.page_rows}" perPageRows="10"
							totalRows="${adList.data.total_rows}"
							linkBaseUrl="${cbasePath}adSeller/adPage?ctime=${ctime}&etime=${etime}&qdName=${qdId}&qdId=${userKey}">
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
			
			$(".deleteBtn").click(function(){
				if(window.confirm('你确定要删除吗？')){
					window.location.href="${cbasePath}adSeller/deleteAd?id="+$(this).attr("data");
				}else{
					
				}
			});
			$(".detailBtn").click(function(){
				window.location.href="${cbasePath}adSeller/userList?qdId="+$(this).attr("data");
			});
		});
	</script>
</body>
</html>

