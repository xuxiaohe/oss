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

<style>
#userInfoDiv div {
	padding: 10px;
}
</style>
<script src="${cbasePath}/resources/assets/js/jquery.min.js"></script>
<script src="${cbasePath}/resources/assets/js/qiniu.js"></script>
<script src="${cbasePath}/resources/assets/js/moxie.min.js"></script>
<script src="${cbasePath}/resources/assets/js/plupload.full.min.js"></script>
<script type="text/javascript">
var ckey="${ckey}";
$(function() {
    var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'pickfiles',
        container: 'container',
        drop_element: 'container',
        max_file_size: '100mb',
        flash_swf_url: '${cbasePath}/js/Moxie.swf',
        dragdrop: true,
        chunk_size: '4mb',
        uptoken_url: '${cbasePath}/knowledge/getToken',
        domain: 'tpublic.qiniudn.com',
       	filters:${config.filters},
        auto_start: true,
        
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
						"fname":file.name
					},
					success : function(result) {
						$("#attachId").val("");
						$("#attachId").val(result.attid);
						console.log(result);
						alert("添加附件成功");
						if("knowledge_video"==ckey){
							$('#confirm').attr("disabled",false);
						}else{
						}
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
							"pathrule" : "${config.pathrule}"
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
});
$(".btn-success").click(function(){
	 $.ajax({
			url :"${cbasePath}knowledge/add",
			type : "POST",
			async : false,
			data :{
				"key":$("#key").val(),
				"fid":$("#attachId").val(),
				"kngType":"1",
				"name":$("#kngName").val()
			},
			success : function(result) {
				alert("上传知识成功等待转码");
				console.log(result.knowledgeId);
				$("#input").val(result.knowledgeId);
				
			}
	 });
	var sender = $(this).closest(".modal").data("sender");
	var input = $("#input").val();
	knowledgeId=input;
	savaeLesson(sender);
	$.CloseModalDialog();
});
</script>
</head>
<body>
	<div id="container" class="container-fluid">
		<div class="row">
			<div class="col-xs-9">
				<button id="pickfiles" type="submit" class="btn btn-default">上传</button>
				<button id="confirm" type="button" class="btn btn-success" disabled="disabled">确认</button>
			</div>
		</div>
		
		<input type="hidden" id="key">
		<input type="hidden" id="attachId">
		<input type="hidden" id="kngName">
		<input type="hidden" id="input">
	</div>
</body>
</html>