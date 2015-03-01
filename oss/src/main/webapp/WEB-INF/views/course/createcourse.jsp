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
</head>

<body>

	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-body">
				 
		<form id="addtest" action="/test"><br><a href="javascript:formSubmit()">add</a> 
 
 <input id="testid" name="id" type="hidden" value="1">
 <button type="submit" >Submit</button> <br></form>		 
					
					 
	</div>
	</div>
	</div>
	<script >
//js动态创建form 提交表单
var divId=0;
function formSubmit() {
    var turnForm = document.getElementById("addtest");   
    //一定要加入到body中！！   
    /* document.body.appendChild(turnForm);
    turnForm.method = 'post';
 turnForm.action = '/product/detail.htm';
 turnForm.target = '_blank'; */
 //创建隐藏表单
 var newElement = document.createElement("input");
 


    newElement.setAttribute("name","id"+divId);
    divId++;
    newElement.setAttribute("type","text");
    newElement.setAttribute("value",divId);
    turnForm.appendChild(newElement);
    //turnForm.innerHTML="<br>";
   // turnForm.submit();
   
     var turnForm2 = document.getElementById("testid");  
      
     turnForm2.setAttribute("value",divId);
    
     var obj=document.createElement("div");
     
     obj.innerHTML="<br><br>";
     turnForm.appendChild(obj);
      
}
</script>
</body>
</html>

