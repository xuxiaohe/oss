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
<script src="${cbasePath}/resources/assets/js/jquery.min.js"></script>
<script src="${cbasePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${cbasePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${cbasePath}/resources/assets/css/font.css" rel="stylesheet">
</head>
<body>
	<div class="panel panel-default">
		<ol class="breadcrumb">
			<li><a href="#">广告位管理</a></li>
			<li><a href="${cbasePath}adSeller/adPage">广告位列表</a></li>
			 
			<li class="active">广告创建</li>
		</ol>
		<div class="panel-body">
			<!-- <ul class="nav nav-pills nav-justified">
				<li class="active"><a href="#">总览</a></li>
				<li><a href="#">访问</a></li>
				<li><a href="#">用户</a></li>
				<li><a href="#">内容</a></li>
				<li><a href="#">行为</a></li>
				<li><a href="#">排行榜</a></li>
				<li><a href="#">渠道</a></li>
			</ul>
			<ol class="breadcrumb" style="margin-top:10px">
			  <li><a href="#">用户统计</a></li>
			  <li class="active">用户邀请列表</li>
			</ol> -->
			<form class="form-inline" action="createAd" role="form">
				<div class="row">
					<div class="col-xs-3 h4">广告位创建</div>
					<!-- <div class="col-xs-9 h6" style="padding-top:4px;">链接形式：ztiao.cn/2342.html?p=sanmao&id=45</div> -->
				</div>
				<div class="row">
					<div class="col-xs-5">
						<div class="form-group"  style="width:100%;">
							<label>渠道名称：</label>
							<select class="form-control" name="adSid" id="adSid">
							<c:if test="${adSellerList.status == '200' }">
								<c:forEach items="${adSellerList.data.result}" varStatus="key"
									var="Recourse">
									<option value="${Recourse.id }">id:${Recourse.adSellerId }/名称${Recourse.name }</option>
								</c:forEach>
							</c:if>
						</select>
						</div>
					</div>
					<div class="col-xs-5" style="margin-top:15px">
						<div class="form-group" style="width:100%;">
							<label>广告位：</label>
							<input type="text" name="name" class="form-control" />
						</div>
					</div>
					<div class="col-xs-5" style="margin-top:15px">
						<div class="form-group" style="width:100%;">
							<label>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;注&nbsp;：</label>
							<input type="text" name="remark" class="form-control" />
						</div>
					</div>
					<div class="col-xs-5" style="margin-top:15px">
						<div class="form-group" style="width:100%;">
							<label>创建者：</label>
							<input type="text" name="creater" class="form-control" />
						</div>
					</div>
					<div class="col-xs-2 text-right" style="margin-top:15px">
						<button type="submit" class="btn btn-primary">创建广告位</button>
					</div>
				</div>
			</form>
			<hr />
			<form role="form" class="form-inline">
				<div class="row">
					<div class="col-xs-12 h4">渠道创建</div>
				</div>
				<div class="row">
					<div class="col-xs-5" style="margin-top:15px">
						<div class="form-group" style="width:100%;">
							<label>渠道ID：</label>
							<input type="text" id="adSellerId" name="adSellerId" class="form-control" />
						</div>
					</div>
					<div class="col-xs-5" style="margin-top:15px">
						<div class="form-group" style="width:100%;">
							<label>渠道名称：</label>
							<input type="text" id="name" name="name" class="form-control" />
						</div>
					</div>
					<div class="col-xs-2 text-right" style="margin-top:15px">
						<button type="button" onclick="create()" class="btn btn-primary">创建渠道</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	//创建渠道商
		function create(){
			if($("#name").val()==""){
				alert("渠道商名称为空！");
				return false;
			}
			if($("#adSellerId").val()==""){
				alert("渠道商ID为空！");
				return false;
			}
			 jQuery.ajax({
					url :"${cbasePath}adSeller/create",
					type : "POST",
					async:false,
					data :{
						"adSellerId" : $("#adSellerId").val(),
						"name" : $("#name").val()
					},
					success : function(date) {
						if(date.result.status==200){
							alert("创建成功");
							window.location.href = "${cbasePath}adSeller/createView";
							
						}else{
							console.log(date.result);
							alert(date.result.msg);
							}
						
					}
			 });
		};
	</script>
</body>
</html>