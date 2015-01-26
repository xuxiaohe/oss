<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String cpath = request.getContextPath();
String cbasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+cpath+"/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<a href="${cbasePath}test?key=<%=java.net.URLEncoder.encode("编码的是这里","UTF-8")%>">点击这里</a>



<%
if(request.getParameter("url")!=null)
{
	String str=null;
str=request.getParameter("url");
str=java.net.URLDecoder.decode(str,"UTF-8");

out.print(str);
}
%>


</body>
</html>