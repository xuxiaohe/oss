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
<title>发送系统消息</title>
<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/font.css"
	rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-body">
				<h4 class="modal-title" id="myModalLabel">发送消息</h4>
				<div class="form-group">
						<label >标题</label>
						<input class="form-control" id="title"/>
						<label>内容</label>
						<textarea rows="5" cols="" class="form-control" id="content"></textarea>
					</div>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="javascript:sendMessage();">发送</button>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<!-- Modal -->
	<button type="button" class="btn btn-primary" style="display:none;" data-toggle="modal" data-target="#myModal" id="showBtn">发送消息</button>
	<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="false"> 
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">发送消息</h4>
				</div>
				<div class="modal-body" id="modalHtml">
					<div class="form-group">
						<label >标题</label>
						<input class="form-control" id="title"/>
						<label>内容</label>
						<textarea rows="5" cols="" class="form-control" id="content"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="javascript:sendMessage();">发送</button>
					<!-- <button type="button" class="btn btn-primary">确定</button> -->
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			//$("#showBtn").click();
			
		});
		function sendMessage(){
			if(window.confirm('确认发送消息?')){
				var url = '<%=contextPath%>/message/sendmessage';
				$.ajax({
					url : url,
					data : {'title' : $("#title").val(), 'content' : $("#content").val()},
					type : 'post',
					dataType : 'json',
					success : function(data){
						if(data.status == '200'){
							alert('发送成功');
							window.location.href = '<%=contextPath%>/message/sendView';
						}else{
							alert('发送失败, ' + data.message);
							window.location.href = '<%=contextPath%>/message/sendView';
						}
					},
					error : function(){
						alert('发送失败，请重试或联系管理员!');
						window.location.href = '<%=contextPath%>/message/sendView';
					}
				});
				
			}
		}
	</script>
</body>
</html>

