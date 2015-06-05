<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>创建优惠劵</title>
<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/bootstrap-datepicker.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/font.css" rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<form action="<%=contextPath%>/coupon/create" method="post">
				<div class="panel-body bg-info">
					<div class="row form-group">
						<h4>&nbsp;&nbsp;&nbsp;&nbsp;创建卡券</h4>
					</div>
					<div class="row form-group">
						<div class="col-xs-6">
							<label for="cardHeader" >优惠券名称</label>
							<input type="text" class="form-control" name="cname" id="cname"/>
						</div>
						<div class="col-xs-6">
							<label>活动名称</label>
							<input type="text" class="form-control" name="actname" id="actname"/>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-xs-6">
							<label for="cardHeader" >卡号开头</label>
							<input type="text" class="form-control" name="cardhead" id="cardHeader"/>
						</div>
						<div class="col-xs-6">
							<label>面值</label>
							<input type="text" class="form-control" name="quota" id="quota"/>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-xs-6">
							<label for="">开始时间</label>
							<input type="text" class="form-control"  id="starttime" /> <label for="">结束时间</label>
							<input type="text" class="form-control"  id="endtime"/>
							
							<input type="hidden" class="form-control" name="exdatestart" id="exdatestart" />
							<input type="hidden" class="form-control" name="exdateend" id="exdateend"/>
						</div>
						<div class="col-xs-6">
							<label>张数</label>
							<input type="text" class="form-control" name="num" id="num"/>
						</div>
						<div class="col-xs-6">
							<label>红包类型</label>
							<select class="form-control" name="ident">
								<option value="0">注册即送优惠劵</option>
								<option value="1">朋友圈抽奖</option>
							</select>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-xs-4">
							<label for="">类型</label>
							<select class="form-control">
								<option>课程卡</option>
							</select>
						</div>
						<div class="col-xs-4">
							<label>课程ID</label>
							<input type="hidden" class="form-control" name="courseid" id="courseid" />
							<input type="text" class="form-control" name="courseName" id="courseName" onfocus="javascript:showCourseDialog(this);"/>
						</div>
						<div class="col-xs-4">
							<label>备注</label>
							<input type="text" class="form-control" name="remark" id="remark"/>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-xs-10"></div>				
						<div class="col-xs-2"><button type="submit" class="btn btn-primary">提交</button></div>
					</div>
				</div>
			</form>
		</div>
		
		
		<!-- Modal -->
		<!-- Modal -->
		<button class="btn btn-primary" style="display:none;" data-toggle="modal" data-target="#myModal" id="selectBtn" onclick = "javascript:loadContents();">选择</button>
		<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1" role="dialog" 
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">课程搜索</h4>
					</div>
					<div class="modal-body" id="modalHtml"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
						<!-- <button type="button" class="btn btn-primary">确定</button> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=contextPath%>/resources/assets/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript">
		var today = new Date().valueOf();
		$(function(){
			$('#starttime').datepicker({
				format : 'yyyy-mm-dd',
				autoclose : true,
				todayHighlight:true,
				todayBtn : 'linked',
				language : 'zh-CN'
			}).on('changeDate', function(ev) {
				var startTime = ev.date.valueOf();
				start = startTime;
				if (startTime > today) {
					alert('开始日期不能大于今天!');
					$("#starttime").val("");
					$("#starttime").focus();
				}else{
					$("#exdatestart").val(startTime);
				}
			});
			$('#endtime').datepicker({
				format : 'yyyy-mm-dd',
				autoclose : true,
				todayHighlight:true,
				todayBtn : 'linked',
				language : 'zh-CN'
			}).on('changeDate', function(ev) {
				var endTime = ev.date.valueOf();
				end = endTime;
				if (end < start) {
					alert('结束日期不能早于开始日期!');
					$("#endtime").val("");
					$("#endtime").focus();
				} else {
					endTime = endTime + (60 * 60 * 24 - 1) * 1000;
					$("#exdateend").val(endTime);
				}
			});
			
		});
		function showCourseDialog(obj){
			$("#selectBtn").click();
			$(obj).blur();
		}
		/**
		加载群组列表
		*/
		function loadContents(){
			$("#modalHtml").load('${cbasePath}course/selectcourse');
		}
		
		/*
		确认选择用户
		*/
		function searchOrder(){
			//var url = '${cbasePath}order/orderBycourseid?cid=' + $("#courseid").val();
			//window.location.href = url;
		}
		
		/**
		选择用户
		*/
		function selectCourse(cid, ctitle){
			$("#courseid").val(cid);
			$("#courseName").val(ctitle);
		}
	</script>
</body>
</html>