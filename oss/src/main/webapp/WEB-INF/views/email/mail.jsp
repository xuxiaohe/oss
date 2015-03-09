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
<title>邮件模板管理</title>
<script src="${cbasePath}/resources/assets/js/jquery.min.js"></script>
<script src="${cbasePath}/resources/assets/js/form.js"></script>
<script src="${cbasePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${cbasePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${cbasePath}/resources/assets/css/font.css" rel="stylesheet">

</head>

<body>	
<input id="tempid" style="display:none;"/>
	<div class="container-fluid">
    <div class="dropdown">
   <button type="button" class="btn dropdown-toggle" id="dropdownMenu1" 
      data-toggle="dropdown"> 选择模板
      <span class="caret"></span>
   </button>
   <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
      <c:forEach var="temp" items="${temps}"  >  
  
	  <li role="presentation">
         <a role="menuitem" tabindex="-1" href="javascript:void(0)" onclick="setinfo('${temp.id}','${temp.name}')">${temp.cName}</a>
      </li>
  
</c:forEach> 
      
   </ul>
  
</div>
 <button type="button" class="btn dropdown-toggle"  onclick="testqqq();"> 保存 </button>
  
  <div>
    <script id="container" name="content" type="text/plain">
        这里写你的初始化内容
    </script>
  
    <!-- 配置文件 -->
    <script type="text/javascript" src="${cbasePath}resources/ue/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${cbasePath}resources/ue/ueditor.all.js"></script>
    <!-- 实例化编辑器 -->
    <script type="text/javascript">
        var ue = UE.getEditor('container',{
        	initialFrameWidth :800,//设置编辑器宽度
        	initialFrameHeight:450,//设置编辑器高度
        	scaleEnabled:true
        	});
    </script>
	 <script>
		function setinfo(id,name){
				$.post("${cbasePath}/mail/getById",{"id":id},function(data){
					ue.setContent(data.context);
					$("#tempid").val(""+id);
					
				});
				//ue.setContent("test");
			}
		
		function testqqq(){
			var content=ue.getContent();
			var id=$("#tempid").val();
			$.post("${cbasePath}/mail/modify",{"id":id,"content":content},function(data){
				alert("修改成功");
				
			});
		}
   </script>
    </div>	
		
</body>
</html>

