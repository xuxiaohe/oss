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
<title>用户密码修改</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
<style>
#userInfoDiv div {
	padding: 10px;
}
</style>
<script>

</script>
</head>
<body onload="">
	<div class="container-fluid">

		

		<div class="row">
			<div class="col-xs-3">
				

				
			</div>


			<div class="col-xs-9"><br><br><br><br><br><br><br><br><br>
				${deleteTopic.msg} 
				 ${addpost.msg}
				  ${subpostList.msg}
				 
				<br><br><br>
				 <li role="presentation">
                        <a href="${cbasePath}topic/topicList" target="mainFrame">返回话题仓库</a>
                    </li>
			</div>
		</div>


	</div>
	
</body>
</html>

