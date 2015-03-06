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
<title>课程管理</title>
<script src="${cbasePath}/resources/assets/js/jquery.min.js"></script>
<script src="${cbasePath}/resources/assets/js/form.js"></script>
<script src="${cbasePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${cbasePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${cbasePath}/resources/assets/css/font.css" rel="stylesheet">
<script>
 function addword(){
	 var word=$("#words").val();
	 $.post("${apiurl}oss/stopWords/add",{"word":word},function(data){
		 if(data.status==200){
	    	 alert("添加成功");
	     }else{
	    	 alert("添加失败");
	     }
	 });
 }
 function delall(){
	
	 $.post("${apiurl}oss/stopWords/deleteAll",function(data){
	     if(data.status==200){
	    	 alert("删除成功");
	     }else{
	    	 alert("删除失败");
	     }
		
	 });
 }
 
 function del(){
	 var word=$("#words").val();	
	 $.post("${apiurl}oss/stopWords/delete",{"word":word},function(data){
		 if(data.status==200){
	    	 alert("删除成功");
	     }else{
	    	 alert("删除失败");
	     }
	 });
 }
 
 function upfile(){
	  $("#uploadfile").ajaxSubmit(function(data){
		  alert(data.status);
	  });
 }
</script>
</head>

<body>	
	<div class="container-fluid">
	 <!-- 添加 删除 敏感词 -->
	 <div class="row">
			<div class="col-xs-9">
			  <div class="form-group">
						<label>敏感词</label>  <br>
						<input id="words" name="cname" type="text" >	 
						<a href="javascript:addword();">添加</a> 
						<a href="javascript:del()">删除</a> 			
			 </div>
			</div>
    </div>
    <!-- 删除所有 -->
	<div class="row">
			<div class="col-xs-9">
						<div class="form-group">
						<label>清空词库</label>  <br>	 
						<a href="javascript:delall()">清空词库</a> 
						</div>
			
			</div>
    </div>	
    
      <!-- 以文档形式批量添加 -->
	<div class="row">
			<div class="col-xs-9">
						<form  id="uploadfile" class="form-group" action="${apiurl}oss/stopWords/add/file" method="post" enctype="multipart/form-data">
						<label>导入词库</label>  <br>
						<input name="myfiles" type="file" >	 
						<a href="javascript:void(0)" onclick="upfile();">上传</a> 
						</form>		
			</div>
    </div>	
  </div>		
</body>
</html>

