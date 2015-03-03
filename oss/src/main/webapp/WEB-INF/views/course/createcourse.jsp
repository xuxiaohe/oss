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

<!-- <button id="test" type="button" class="btn btn-default" onclick="verify(5469a970e4b0d5b83598e918,this.id)">全部填完点击提交</button><br><br><br>
 -->				
	<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
	 <div class="row">

			<div class="col-xs-9">
					
				<form role="form" method="post" id="addtest"
					action="${cbasePath}course/createcourseaction">
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
				</form>
			</div>
		</div>
 
	</div>
	<script >
//js动态创建form 提交表单
var divId=0;
function formSubmit() {
   // var turnForm = document.getElementById("addtest");   
    var chapter = document.getElementById("chapter"); 
    //一定要加入到body中！！   
    /* document.body.appendChild(turnForm);
    turnForm.method = 'post';
 turnForm.action = '/product/detail.htm';
 turnForm.target = '_blank'; */
 //创建隐藏表单
  var obj=document.createElement("div");
     
     obj.innerHTML="章节名称";
     chapter.appendChild(obj);
     divId++;
 var newElement = document.createElement("input");

    newElement.setAttribute("id","zhangjie"+divId);
    newElement.setAttribute("name","zhangjie"+divId);
    newElement.setAttribute("type","text");
    //newElement.setAttribute("value",divId);
    chapter.appendChild(newElement);
    //turnForm.appendChild(chapter);
    //turnForm.innerHTML="<br>";
   // turnForm.submit();
   
     var turnForm2 = document.getElementById("sumchapter");  
      
     turnForm2.setAttribute("value",divId);
    
     var obj=document.createElement("div");
     var i="5469a970e4b0d5b83598e918";
     obj.innerHTML="<br>";
     //obj.innerHTML="<button id='test' type='button' onclick='verify("+i+","+this.id+")' class='deleteBtn btn btn-primary' >提交</button>";
     chapter.appendChild(obj);
      
}

var divId2=0;
function formSubmit2() {
    //var turnForm = document.getElementById("addtest");   
    var chapter = document.getElementById("keshi"); 
    //一定要加入到body中！！   
    /* document.body.appendChild(turnForm);
    turnForm.method = 'post';
 turnForm.action = '/product/detail.htm';
 turnForm.target = '_blank'; */
 //创建隐藏表单
 
 var obj=document.createElement("div");
     
     obj.innerHTML="课时名称";
     chapter.appendChild(obj);
     divId2++;
 var newElement = document.createElement("input");

    newElement.setAttribute("id","keshi"+divId2);
  
    newElement.setAttribute("type","text");
   // newElement.setAttribute("value",divId2);
    chapter.appendChild(newElement);
    //turnForm.appendChild(chapter);
    //turnForm.innerHTML="<br>";
   // turnForm.submit();
   
   var obj=document.createElement("div");
     
     obj.innerHTML="关联的章节";
     chapter.appendChild(obj);
 var newElement = document.createElement("input");

    newElement.setAttribute("id","guanlian"+divId2);
    
    newElement.setAttribute("type","text");
    //newElement.setAttribute("value",divId2);
    chapter.appendChild(newElement);
   
   
     var turnForm2 = document.getElementById("sumkeshi");  
      
     turnForm2.setAttribute("value",divId2);
    
     var obj=document.createElement("div");
     
     obj.innerHTML="<br>";
     chapter.appendChild(obj);
      
}



var arrayObj ;

function verify(id,ids){
	alert(id);
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
};

</script>
</body>
</html>

