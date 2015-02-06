<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Report</title>
    
	
  </head>
  
  <body>
    <%-- <c:url var="exportUrl" value="/report/export" />   --%>
    <c:url var="readUrl" value="/importUser" />  
      
      <br><br><br><br><br><br><br><br><br>
    <h3>Excel批量导入用户：</h3>  
    <br />  
    <form  id="readReportForm" action="${readUrl}" method="post" enctype="multipart/form-data"  >  
            <label for="file">Excel文件（格式为第一列的数据为用户名，第二列为密码）：</label>  
            <input id="file" type="file" name="file" />  
            <label for="exampleInputEmail1">邮箱</label> <input type="text"
							name="email" class="form-control" id="exampleInputEmail1"
							placeholder="" >
            
            <p><button type="submit">Read</button></p>    
        </form>  
  </body>
</html>
