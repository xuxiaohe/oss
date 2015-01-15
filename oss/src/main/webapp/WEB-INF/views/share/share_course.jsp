<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%
String cpath = request.getContextPath();
String cbasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+cpath+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
          content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no,target-densitydpi=device-dpi"/>
<link href="<%=cbasePath%>/resources/assets/css/share/main.css" rel="stylesheet" type="text/css">
<title>云学堂——让职场学习更简单</title>
</head>

<body>
<div class="wrapper">

  <div class="divTop">
    <div class="logo"><img src="<%=cbasePath%>/resources/assets/image/share/logo.png" /></div>
    <div class="button">
      <a href="itms-services://?action=download-manifest&url=https://passport.yunxuetang.cn/t/yxt/yxt.ipa"><img src="<%=cbasePath%>/resources/assets/image/share/button.png" ></a>
    </div>
    <div class="clear"></div>
  </div>
  
  <div class="divContent1">
    
    <div class="dc">
      
      <div id="dc_title" class="dc_title" style="margin-top:20px"> <!--标题-->
        ${ courSharReso.course.title}
      </div>
      
      <div id="dc_content" class="dc_content"> <!--内容-->
         ${ courSharReso.course.intro}
      </div>
      
      <div class="dc_bottom">
        <div id="dc_study" class="dc_b_content">
          ${ courSharReso.course.studyCount}人学习
        </div>
        <div id="dc_fav" class="dc_b_content">
          ${ courSharReso.course.favCount}人收藏
        </div>
        <div id="dc_share" class="dc_b_content">
          ${ courSharReso.course.shareCount}人分享
        </div>
        <div class="clear"></div>
      </div>
      
       <div class="dc_book">
       <!-- <img id="curpic" src="images/default_book_cover.png" />课程图片 -->
        
        <video id="curpic" src="${ courSharReso.course.chapters[0].lessons[0].knowledge.appItems[0].furl}" width="100%" height="100%" controls="controls" autoplay=”autoplay”>
       您的浏览器不支持 video 标签。
              </video>
              
          <div  class="dc_more" style="margin-top:20px">
          	<a href="itms-services://?action=download-manifest&url=https://passport.yunxuetang.cn/t/yxt/yxt.ipa">
          		 更多相关视频请下载app</a></div>
      </div>
                   
    </div>
    
  </div>
  
  <div class="divContent2">
    相关课程
  </div>
  
  <div id="releContent">
  	     
  	     <c:forEach items="${courSharReso.courseRelevant}" var="Recourse">
       	
       <div class='divContent1'> <div class='dc'> <div class='dc_left dc_book'>
    	     
        <img src='${Recourse.logoUrl}' /></div><div class='dc_right dc_book'>
           
        <div class='dc_r_title'>${Recourse.title}</div> 
        <div class='dc_r_content'>${Recourse.intro}</div><div class='dc_r_bottom'>
       
        <div class='dc_r_b_content1'>${Recourse.studyCount}人学习</div>
        <div class='dc_r_b_content2'>${Recourse.shareCount}人分享</div>
        
        </div></div><div class='clear'></div> </div></div>
      </c:forEach>
</div>  
  
  <div class="divTop1">
    <div class="logo"><img src="<%=cbasePath%>/resources/assets/image/share/logo1.png" /></div>
    <div class="button">
      <a href="itms-services://?action=download-manifest&url=https://passport.yunxuetang.cn/t/yxt/yxt.ipa"><img src="<%=cbasePath%>/resources/assets/image/share/button.png" ></a>
    </div>
    <div class="clear"></div>
  </div> 
</div>

</body>
</html>
