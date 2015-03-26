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
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
</head>

<body>

	<div class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-body">
					 
					
					<!---数据显示区域-->
					<%-- ${fn:length(Drys.data.result)} --%>

							 
							<div class="col-xs-10">

								<div class="col-xs-12 btn-group-sm">
								 
									<button id="test" type="button" onclick="verify('5469a970e4b0d5b83598e918',this.id)"	
										class="deleteBtn btn btn-primary" >审核</button>
										 
								 
								</div>

							</div>
						 
					 
				 
			</div>
		</div>
	</div>
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
			};
	
			
	</script>
</body>
</html>

