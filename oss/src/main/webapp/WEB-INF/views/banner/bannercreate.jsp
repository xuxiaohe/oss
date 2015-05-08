<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String contextPath = request.getContextPath();
%>
<html>

<head lang="en">
<meta charset="UTF-8">
<title>创建Banner</title>
<script src="<%=contextPath%>/resources/assets/js/jquery.min.js"></script>
<script src="<%=contextPath%>/resources/assets/js/bootstrap.min.js"></script>
<link href="<%=contextPath%>/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/assets/css/font.css"
	rel="stylesheet">
</head>
<body>

	<div class="container-fluid">

		<ol class="breadcrumb">
			<li><a href="<%=contextPath%>/banner/bannerlist">banner管理</a></li>
			<li><a href="<%=contextPath%>/banner/bannerlist">banner列表</a></li>

			<li class="active">banner创建</li>
		</ol>

		<div class="row">
			<div class="col-xs-9">
				<form role="form" method="post" onsubmit="return beforeSubmit();"
					action="<%=contextPath%>/banner/saveBanner">
					<input type="hidden" name="groupId" id="groupId" value=""/>
					<input type="hidden" name="topicId" id="topicId" value=""/>
					<input type="hidden" name="dryCargoId" id="dryCargoId" value=""/>
					<input type="hidden" name="adId" value="0"/>
					<input type="hidden" name="adSellerName" id="adSellerName"/>
					<input type="hidden" name="adSellerId" id="adSellerId"/>
					<div class="form-group">
						<label for="exampleInputEmail1">名称:</label> <input type="text" name="name"
							class="form-control" />
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">类型: &nbsp;&nbsp;&nbsp;&nbsp;</label>
						<label class="radio-inline"> <input type="radio"
							name="adSid" id="inlineRadio1" value="10" onclick="javascript:changeBannerType();" checked>
							站外
						</label> <label class="radio-inline"> <input type="radio"
							name="adSid" id="inlineRadio2" value="0" onclick="javascript:changeBannerType();">
							站内
						</label>

					</div>
					<div class="form-group clearfix">
						<div class="media">
							<div class="media-left">
								<img id="img" width="100" height="100" src=""> <input
									type="hidden" name="picUrl" id="picUrl" /> <input type="hidden"
									name="picWidth" id="picWidth" /> <input type="hidden"
									name="picHeight" id="picHeight" />
							</div>
							<div class="media-body">
								<div id="container" style="position: relative;">
									<div class="row">
										<div class="col-xs-9">
											<button id="pic" type="button" class="btn btn-primary">上传图片</button>
										</div>
									</div>

								</div>


							</div>
						</div>

					</div>
					<div id="innerSite">
						<div class="form-group">
							<label for="exampleInputEmail1">外链地址</label> <input type="text" name="linkUrl"
								class="form-control" />
						</div>
						
					</div>
					<div id="outSite" style="display:none;">
						<div class="form-group">
							<label for="exampleInputEmail1">类型:</label> <input type="text" id="typeText"  
								class="form-control" readonly="readonly"/>
						</div>
						<div class="form-group">
							<label for="exampleInputEmail1">内容:</label> <input type="text"
								class="form-control" readonly="readonly" id="contentText"/>
						</div>
						<div class="form-group">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
							  选择
							</button>
						</div>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">是否展示: &nbsp;&nbsp;&nbsp;&nbsp;</label>
						<label class="radio-inline"> <input type="radio"
							name="isShow" id="inlineRadio1" value="1" checked>
							是
						</label> <label class="radio-inline"> <input type="radio"
							name="isShow" id="inlineRadio2" value="0" >
							否
						</label>

					</div>
					<br><br><br>
					<button type="submit" class="form-control btn btn-primary" >提交</button>
				</form>
			</div>
		</div>
		
		<!-- Modal -->
		<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">搜索</h4>
					</div>
					<div class="modal-body" id="modalHtml">
						<form class="form-inline">
							<div class="form-group">
								<label class="sr-only" for="keyword">关键字:</label> 
								<input
									class="form-control" id="keywords" name="keyword"
									value="">&nbsp;&nbsp;&nbsp;&nbsp;
								<select class="form-control" id="searchType" onchange="javascript:resetContent();">
									<option value="">-选择-</option>
									<option value="0">站内话题</option>
									<option value="1">站内干货</option>
									<option value="2">站内课程</option>
								</select>
							</div>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button id="searchItBtn" type="button" class="btn btn-default" onclick="javascript:searchContent();">Search
								it!</button>
						</form>
						<div id="searchContent" class="container-fluid"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
						<!-- <button type="button" class="btn btn-primary">确定</button> -->
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
	<script src="<%=contextPath%>/resources/assets/js/html5shiv.js"></script>
	<script src="<%=contextPath%>/resources/assets/js/plupload.full.min.js"></script>
	<script src="<%=contextPath%>/resources/assets/js/qiniu.js"></script>
	<script src="<%=contextPath%>/resources/assets/js/moxie.min.js"></script>
	<script type="text/javascript">
		$(function(){
			upload("10007");
		});
		/*
		*/
		function changeBannerType(){
			if($('input:radio[name=adSid]:checked').val() == '0'){
				$("#innerSite").show();
				$("#outSite").hide();
			}else{
				$("#outSite").show();
				$("#innerSite").hide();
			}
		}
		
		/**
		提交前检测, 主要是为了清空不必要的值
		*/
		function beforeSubmit(){
			if($('input:radio[name=adSid]:checked').val() == '0'){
				$("#groupId").val("");
				$("#topicId").val("");
				$("#dryCargoId").val("");
				$("#typeText").val("");
				$("#contentText").val("");
				$("#adSellerName").val("");
				$("#adSellerID").val("");
			}else{
			}
			return true;
		}
		
		/*重置搜索内容方法
		*/
		function resetContent(){
			$("#searchContent").html("");
			$("#groupId").val("");
			$("#topicId").val("");
			$("#dryCargoId").val("");
			$("#typeText").val("");
			$("#contentText").val("");
			$("#adSellerName").val("");
			$("#adSellerID").val("");
		}
		
		/**
		搜索方法
		*/
		function searchContent(){
			var searchType = $("#searchType").val();
			var keywords = $("#keywords").val();
			if(keywords == ''){
				return ;
			}
			if(searchType == ''){
				return;
			}
			var url = '';
			if(searchType == 0){
				
			}else if(searchType == 1){
				url = '<%=contextPath%>/banner/searchDry';
			}else if(searchType == 2){
				
			}
			$.ajax({
				url : url,
				data : {'keyword' : keywords},
				type : 'POST',
				dataType : 'html',
				success : function(data){
					$("#searchContent").html("").html(data);
				}
				
				
			});
		}
		
		/*上传图片*/
		function upload(s){
		    var uploader = Qiniu.uploader({
		        runtimes: 'html5,flash,html4',
		        browse_button: 'pic',
		        container: 'container',
		        drop_element: 'container',
		        max_file_size: '100mb',
		        flash_swf_url: '<%=contextPath%>/js/Moxie.swf',
		        dragdrop: true,
		        chunk_size: '4mb',
		        uptoken_url: '<%=contextPath%>/knowledge/getToken?ckey='+s,
		        domain: 'tpublic.qiniudn.com',
		       	filters:"{mime_types : [{ title : \"Image files\", extensions : \"jpg,gif,png\" }  ],  max_file_size : '2mb', \r\n  prevent_duplicates : true}",
		        auto_start: true,
		        multi_selection:false,
		        
		        init: {
		             'UploadComplete': function() {
		             	
		            }, 
		            'FileUploaded': function(up, file, info) {//完成一张图片之后
		            	
		            	var res=$.parseJSON(info);
		            	var imgUrl = 'http://tpublic.qiniudn.com/' + res.key;
		            	jQuery.getJSON(imgUrl + '?imageInfo',
		            			function(result){
	            			var height = result.height; //图片高度
	            			var width = result.width; //图片宽度
	            			//通过dom动态添加image
	            			var $imgContainer = jQuery("#img");
	            			$imgContainer.attr("src", imgUrl);
	            			jQuery("#picUrl").val(imgUrl);
	            			jQuery("#picHeight").val(height);
	            			jQuery("#picWidth").val(width);
	            			alert("上传成功");
		            	});
		            	
		            },
		            'Error': function(up, err, errTip) {
		                alert(errTip);
		            }
		            ,
		            'Key': function(up, file) {
		            	var key="";
		            	 $.ajax({
								url :"<%=contextPath%>/topic/getFileName",
								type : "POST",
								async : false,
								data :{
									"name" : file.name,
									"pathrule" : "banner/imgs/{yyyymm}/"
								},
								success : function(result) {
									key=result;
								}
						 });
		                 return key;
		             }
		        }
		    });
			
		}
	</script>
</body>
</html>
