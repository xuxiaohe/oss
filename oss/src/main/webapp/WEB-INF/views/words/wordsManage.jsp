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
<script src="${cbasePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${cbasePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${cbasePath}/resources/assets/css/font.css" rel="stylesheet">
<script>
var arrayObj ;

function verify(id,ids){
	alert(ids);
	var currentBtn = document.getElementById(ids);
	//currentBtn.disabled=true;
	arrayObj=arrayObj+","+ids;
	 $.ajax({
			url :"${serverPath}oss/dry/getOneDry",
			type : "POST",
			data :{
				"dryid" : id,
				"data" : arrayObj
				
			},
			success : function(res) {
				if(res.data.result!=null||res.status=='200'){
					alert(res.data.result.id);
				}
				else{
					   alert("异常！");  
					
				}
				
			}
	 });
}
</script>
</head>

<body>

<!-- <button id="test" type="button" class="btn btn-default" onclick="verify(5469a970e4b0d5b83598e918,this.id)">全部填完点击提交</button><br><br><br>
 -->				
	<div class="container-fluid">
	<%-- 	<jsp:include page="header.jsp"></jsp:include> --%>
	 <div class="row">

			<div class="col-xs-9">
					
						<div class="form-group">
						<label for="exampleInputEmail1">课程名称</label>  <br>
						<input id="cname" name="cname" type="text" >	 
					</div>
 <input id="sumchapter" name="sumchapter"  type="hidden" value="0">
  <input id="sumkeshi" name="sumkeshi" type="hidden" value="0">
<div id="chapter" class="form-group">
<a href="javascript:formSubmit()">点击添加章节</a> 

 <br>
 </div>
					<br>
					
					
					<div id="keshi" class="form-group">
					<a href="javascript:formSubmit2()">点击添加课时</a> 
<br>
  </div>
					<br><br> 
			</div>
		</div>
 
	</div>
</body>
</html>

