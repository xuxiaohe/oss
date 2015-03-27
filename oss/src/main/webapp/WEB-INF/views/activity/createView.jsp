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
<script src="${sourcePath}/resources/assets/js/html5shiv.js"></script>
<script src="${sourcePath}/resources/assets/js/plupload.full.min.js"></script>
<script src="${sourcePath}/resources/assets/js/qiniu.js"></script>
<script src="${sourcePath}/resources/assets/js/moxie.min.js"></script>
<script type="text/javascript">
$(function (){
	uploads("10007");
	upload("10007");
	uploadDesMainImg("10007");
	console.log("${result}");
})
function uploads(s){
	var ckey=s;
    var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'desPic',
        container: 'container',
        drop_element: 'container',
        max_file_size: '100mb',
        flash_swf_url: '${cbasePath}/js/Moxie.swf',
        dragdrop: true,
        chunk_size: '4mb',
        uptoken_url: '${cbasePath}/knowledge/getToken?ckey='+s,
        domain: 'tpublic.qiniudn.com',
       	filters:"{mime_types : [{ title : \"Image files\", extensions : \"jpg,gif,png\" }  ],  max_file_size : '2mb', \r\n  prevent_duplicates : true}",
        auto_start: true,
        multi_selection:true,
        
        init: {
             'UploadComplete': function() {
             	
            }, 
            'FileUploaded': function(up, file, info) {
            	$("#key").val("");
            	var res=$.parseJSON(info);
            	$("#key").val(res.key);
            	$.ajax({
					url :"${cbasePath}attachFile/add",
					type : "POST",
					async : false,
					data :{
						"ckey":ckey,
						"fkey":res.key,
						"fname":file.name,
						"fsize":file.size
					},
					success : function(result) {
						$("#attachId").val("");
						$("#attachId").val(result.attid);
						console.log(result);
						$("#logoUrl").val(result.furl);
						$("#images").html();
						var  str='<input type="hidden" value="'+result.furl+'" class="desImgs"/><img  width="100" height="100" src="'+result.furl+'">';
						$("#images").append(str);
						
					}
			 });
            },
            'Error': function(up, err, errTip) {
                alert(errTip);
            }
            ,
            'Key': function(up, file) {
            	var key="";
            	 $.ajax({
						url :"${cbasePath}knowledge/getFileName",
						type : "POST",
						async : false,
						data :{
							"name" : file.name,
							"pathrule" : "kng/imgs/{yyyymm}/"
						},
						success : function(result) {
							$("#kngName").val("");
							key=result;
							$("#kngName").val(file.name);
						}
				 });
                 
                 return key
             }
        }
    });
	
}
function upload(s){
	var ckey=s;
    var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'main',
        container: 'container1',
        drop_element: 'container1',
        max_file_size: '100mb',
        flash_swf_url: '${cbasePath}/js/Moxie.swf',
        dragdrop: true,
        chunk_size: '4mb',
        uptoken_url: '${cbasePath}/knowledge/getToken?ckey='+s,
        domain: 'tpublic.qiniudn.com',
       	filters:"{mime_types : [{ title : \"Image files\", extensions : \"jpg,gif,png\" }  ],  max_file_size : '2mb', \r\n  prevent_duplicates : true}",
        auto_start: true,
        multi_selection:false,
        
        init: {
             'UploadComplete': function() {
             	
            }, 
            'FileUploaded': function(up, file, info) {
            	$("#key").val("");
            	var res=$.parseJSON(info);
            	$("#key").val(res.key);
            	$.ajax({
					url :"${cbasePath}attachFile/add",
					type : "POST",
					async : false,
					data :{
						"ckey":ckey,
						"fkey":res.key,
						"fname":file.name,
						"fsize":file.size
					},
					success : function(result) {
						$("#attachId").val("");
						$("#attachId").val(result.attid);
						console.log(result);
						$("#courseImg").attr("src",result.furl);
						$("#mainImg").val(result.furl);
						alert("上传成功");
					}
			 });
            },
            'Error': function(up, err, errTip) {
                alert(errTip);
            }
            ,
            'Key': function(up, file) {
            	var key="";
            	 $.ajax({
						url :"${cbasePath}knowledge/getFileName",
						type : "POST",
						async : false,
						data :{
							"name" : file.name,
							"pathrule" : "kng/imgs/{yyyymm}/"
						},
						success : function(result) {
							$("#kngName").val("");
							key=result;
							$("#kngName").val(file.name);
						}
				 });
                 
                 return key
             }
        }
    });
	
}

function uploadDesMainImg(s){
	var ckey=s;
    var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'desMainImg',
        container: 'container2',
        drop_element: 'container2',
        max_file_size: '100mb',
        flash_swf_url: '${cbasePath}/js/Moxie.swf',
        dragdrop: true,
        chunk_size: '4mb',
        uptoken_url: '${cbasePath}/knowledge/getToken?ckey='+s,
        domain: 'tpublic.qiniudn.com',
       	filters:"{mime_types : [{ title : \"Image files\", extensions : \"jpg,gif,png\" }  ],  max_file_size : '2mb', \r\n  prevent_duplicates : true}",
        auto_start: true,
        multi_selection:false,
        
        init: {
             'UploadComplete': function() {
             	
            }, 
            'FileUploaded': function(up, file, info) {
            	$("#key").val("");
            	var res=$.parseJSON(info);
            	$("#key").val(res.key);
            	$.ajax({
					url :"${cbasePath}attachFile/add",
					type : "POST",
					async : false,
					data :{
						"ckey":ckey,
						"fkey":res.key,
						"fname":file.name,
						"fsize":file.size
					},
					success : function(result) {
						$("#attachId").val("");
						$("#attachId").val(result.attid);
						console.log(result);
						$("#desMainImgShow").attr("src",result.furl);
						$("#desMainImg").val(result.furl);
						alert("上传成功");
					}
			 });
            },
            'Error': function(up, err, errTip) {
                alert(errTip);
            }
            ,
            'Key': function(up, file) {
            	var key="";
            	 $.ajax({
						url :"${cbasePath}knowledge/getFileName",
						type : "POST",
						async : false,
						data :{
							"name" : file.name,
							"pathrule" : "kng/imgs/{yyyymm}/"
						},
						success : function(result) {
							$("#kngName").val("");
							key=result;
							$("#kngName").val(file.name);
						}
				 });
                 
                 return key
             }
        }
    });
	
}
	
</script>
</head>
<body>
	<div class="container-fluid">
		<ol class="breadcrumb">
			<li><a href="#">活动管理</a></li>
			<li><a href="${cbasePath}activity/activityPage">活动列表</a></li>
			 
			<li class="active">活动创建</li>
		</ol>
		<div class="row">


			<div class="col-xs-9">
				<a href="${url }" target="_blank">${resuserTopic.data.result.message }</a>
				<form role="form" >
					<div class="form-group">
						<label for="exampleInputEmail1">活动名称</label>  <input type="text"
							name="name" class="" id="name"
							placeholder="">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">活动时间</label> <input type="text"
							name="activityStartTime" class="" id="activityStartTime"
							placeholder=""> -<input type="text"
							name="activityEndTime" class="" id="activityEndTime"
							placeholder="">格式：yyyy-MM-dd hh:mm<br>
							需要报名
							<input type="radio" name="type" id="type" >
							报名上限<input type="text" id="maxCount"  ">
							<!-- <input type="checkbox" name="checkbox" class="options" value="姓名">姓名
							<input type="checkbox" name="checkbox" class="options" value="电话">电话
							<input type="checkbox" name="checkbox" class="options" value="公司">公司
							<input type="checkbox" name="checkbox" class="options" value="职位">职位 -->
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">报名时间</label> <input type="text"
							name="optionStartTime" class="" id="optionStartTime"
							placeholder=""> -<input type="text"
							name="optionEndTime" class="" id="optionEndTime"
							placeholder=""> 格式：yyyy-MM-dd hh:mm
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">简介</label> <textarea id="intro" class="form-control" rows="" cols=""></textarea>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">上传主图</label> 
						<div class="media-left">
						<img id="courseImg" width="100" height="100" src="">
						</div>
						<div class="media-body">
							<div id="container1">
								<div class="row">
									<div class="col-xs-9">
										<button id="main" type="submit" class="btn btn-default">上传主图</button>
									</div>
								</div>
							
								<input type="hidden" id="mainImg" class="form-control" />
						</div>
					</div>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">详情</label> <textarea id="des" class="form-control" rows="" cols=""></textarea>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">上传详情主图</label> 
						<div class="media-left">
						<img id="desMainImgShow" width="100" height="100" src="">
						</div>
						<div class="media-body">
							<div id="container2">
								<div class="row">
									<div class="col-xs-9">
										<button id="desMainImg" type="submit" class="btn btn-default">上传详情主图</button>
									</div>
								</div>
							
								<input type="hidden" id="mainImg" class="form-control" />
						</div>
					</div>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">详情图片</label>
						<div class="media-left">
							<div id="images"></div>
						</div>
							<div class="media-body">
								<div id="container">
									<div class="row">
										<div class="col-xs-9">
											<button id="desPic" type="submit"  class="btn btn-default">详情图片</button>
										</div>
									</div>
								
									<input type="hidden" id="key">
									<input type="hidden" id="attachId">
									<input type="hidden" id="kngName">
									<input type="hidden" id="input">
									<input type="hidden" id="desImgs" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">价格</label> <input type="text"
							name="price" class="form-control" id="price"
							placeholder="">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">主办方</label> <input type="text"
							name="company" class="form-control" id="company"
							placeholder="">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">地址</label><select class="" name="pro" id="pro">
							<c:if test="${resultList.status == '200' }"> 
							<option value="">请选择省份</option>
								<c:forEach items="${resultList.data.result}" varStatus="key"
									var="Recourse">
									<option value="${Recourse.id }">${Recourse.name }</option>
								</c:forEach>
							</c:if> 
						</select> 
						<select class="" name="city" id="city">
						<option value="">请选择城市</option>
							
						</select>
						
						<input type="text"
							name="address" class="" id="address"
							placeholder="">
					</div>
					
					


					<button type="button" onclick="saveActivity()" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>

	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$('#pro').change(function(){
		var pId=$(pro).val();//这就是selected的值
		 $.ajax({
				url :"${cbasePath}activity/proCityList",
				type : "POST",
				data :{
					"pId": pId,
				},
				success : function(result) {
					if(result.status==200){
						$('#city').empty();
						var str="";
						for (var int = 0; int < result.data.result.length; int++) {
							str+='<option value='+result.data.result[int].id+'>'+result.data.result[int].name+'</option>';
						}
						$('#city').append(str);
					}
				}
		
		}) 
	});	
	});	
	function saveActivity(){
		var name=$.trim($("#name").val());
		var activityStartTime=$.trim($("#activityStartTime").val());
		var activityEndTime=$.trim($("#activityEndTime").val());
		var type=$('input:radio[name="type"]:checked').val();
		
		if(type==null){
			type=1;
		}else{
			type=0;
		}
		var options="";
		$("input[name='checkbox']:checked").each(function () {
			options+=this.value+",";
		})
		var intro=$.trim($("#intro").val());
		var des=$.trim($("#des").val());
		var desMainImg=$("#desMainImg").val();
		var desImgsVar=$(".desImgs");
		console.log(desImgsVar);
		var desImgs="";
		for (var i = 0; i < desImgsVar.length; i++) {
			desImgs+=desImgsVar[i].value+",";
		}
		var mainImg=$.trim($("#mainImg").val());
		var price=$.trim($("#price").val());
		var company=$.trim($("#company").val());
		var optionStartTime=$.trim($("#optionStartTime").val());
		var optionEndTime=$.trim($("#optionEndTime").val());
		if(optionStartTime==""||optionEndTime==""||activityStartTime==""||activityEndTime==""){
			alert("活动时间不可以空！！！！");
			return false;
		}
		if(type==0&&$("#maxCount").val()==""){
			alert("报名上限不可以空！！！！");
			return false;
		}
		var address=$.trim($("#address").val());
		if($("#pro").val()==''){
			alert('请选择省份');
			return false;
		}
		  $.ajax({
				url :"${cbasePath}activity/createActivity",
				type : "POST",
				data :{
					"name": name,
					"activityStartTime" : activityStartTime,
					"activityEndTime":activityEndTime,
					"type":type,
					"options":options,
					"intro":intro,
					"des":des,
					"desImgs":desImgs,
					"mainImg":mainImg,
					"price":price,
					"company":company,
					"optionStartTime":optionStartTime,
					"optionEndTime":optionEndTime,
					"province":$("#pro").find("option:selected").text(),
					"city":$("#city").find("option:selected").text(),
					"address":address,
					"desMainImg":desMainImg,
					"maxCount":$("#maxCount").val(),
					
				},
				success : function(result) {
					 alert("创建成功");
					window.location.href = "${cbasePath}activity/activityPage" ;
				}
		 }); 
	}
	</script>
</body>
</html>