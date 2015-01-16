<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mydate" uri="/WEB-INF/tld/datetag.tld"%> 
<%
String cpath = request.getContextPath();
String cbasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+cpath+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
          content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no,target-densitydpi=device-dpi"/>
<link href="<%=cbasePath%>/resources/assets/css/share/main1.css" rel="stylesheet" type="text/css">
<title>云学堂——让职场学习更简单</title>
</head>
   
	   
<body>

<div class="wrapper">

  <div class="divTop">
    <div class="logo"><img src="<%=cbasePath%>/resources/assets/image/share/logo.png" /></div>
    <div class="button">
      <a href="https://password.yunxuetang.cn/t/yxt/iphone/yxt/beta/yxt.ipa"><img src="<%=cbasePath%>/resources/assets/image/share/button.png" ></a>
    </div>
    <div class="clear"></div>
  </div>
  <div class="divKong"></div>
  
  
  
  
  <c:if test="${topicSharReso.topicResponse.hasImage==false}" >
  <div class="divContent1">  
    <div class="dc_1">
      <div class="dc3">
      <div class="dc3_left"><img src="${topicSharReso.topicResponse.authorLogoUrl}" /></div>
    
      <div class="dc3_right">
      
        <div class="pic4">
          <div class="dc3_r_con1">
            <div class="pic2">${topicSharReso.topicResponse.authorName}</div>
            <div class="pic3"><mydate:date value="${topicSharReso.topicResponse.utime}"></mydate:date></div>
            <div class="clear"></div>
          </div>
        </div>
      </div>
    </div>
    
    
   <div class="pic1">
      ${topicSharReso.topicResponse.content}
    </div>
    <div class="address">
      <ul class="label">
      <c:forEach items="${topicSharReso.topicResponse.tagName}" var="tag">
      <li> [${tag.value}]</li>
      </c:forEach>
      </ul>
    </div>
      
      <div class="clear"></div>
    </div>
    
    
    <div class="dc_2">
      <img src="<%=cbasePath%>/resources/assets/image/share/s_comment.png" />&nbsp;${topicSharReso.topicResponse.replyCount}
    </div>
    <div class="dc_2">
      <img src="<%=cbasePath%>/resources/assets/image/share/topic_like.png" />&nbsp;${topicSharReso.topicResponse.likesCount}
    </div>
    <div class="dc_2">
      <img src="<%=cbasePath%>/resources/assets/image/share/topic_dislike.png" />&nbsp;${topicSharReso.topicResponse.unLikeCount}
    </div>
    <div class="dc_2">
      <img src="<%=cbasePath%>/resources/assets/image/share/topic_share.png" />&nbsp;${topicSharReso.topicResponse.shareCount}
    </div>
    <div class="clear"></div>    
  </div>
  </c:if>
  
  
  <c:if test="${topicSharReso.topicResponse.hasImage==true}" >
  <div class="divContent1_pic">
    
    <div class="dc3">
      <div class="dc3_left"><img src="${topicSharReso.topicResponse.authorLogoUrl}" /></div>
    
      <div class="dc3_right">
      
        <div class="pic4">
          <div class="dc3_r_con1">
            <div class="pic2">${topicSharReso.topicResponse.authorName}</div>
            <div class="pic3"><mydate:date value="${topicSharReso.topicResponse.utime}"></mydate:date> </div>
            <div class="clear"></div>
          </div>
        </div>
      </div>
    </div>
    
    
     <div class="pic1">
       ${topicSharReso.topicResponse.content}<br />
      <img style="margin-top:5px;" src="${topicSharReso.topicResponse.picUrl}" />
    </div>
    
    <div class="address">
      <ul class="label">
      <c:forEach items="${topicSharReso.topicResponse.tagName}" var="tag">
      <li> ${tag.value}</li>
      </c:forEach>
      </ul>
    </div>
    
    <div class="clear"></div>
    
    
    <div class="dc_2">
      <img src="<%=cbasePath%>/resources/assets/image/share/s_comment.png" />&nbsp;${topicSharReso.topicResponse.replyCount}
    </div>
    <div class="dc_2">
      <img src="<%=cbasePath%>/resources/assets/image/share/topic_like.png" />&nbsp;${topicSharReso.topicResponse.likesCount}
    </div>
    <div class="dc_2">
      <img src="<%=cbasePath%>/resources/assets/image/share/topic_dislike.png" />&nbsp;${topicSharReso.topicResponse.unLikeCount}
    </div>
    <div class="dc_2">
      <img src="<%=cbasePath%>/resources/assets/image/share/topic_share.png" />&nbsp;${topicSharReso.topicResponse.shareCount}
    </div>
    <div class="clear"></div>
    
  </div>
   </c:if>
  
  
  
   <!-- 分享的人横排 -->
  <div class="divContent2">
   
   <c:forEach items="${topicSharReso.topicResponse.praiseResponse}" var="praiser">
    <div class="divContent2_p">
      <img src="${praiser.userLogoUrl}" />
    </div>
  </c:forEach>

    <div class="divContent2_w">${topicSharReso.topicResponse.likesCount}</div>
    <div class="clear"></div>
  </div>
  
  
  
 
  
  
  
 <c:forEach items="${topicSharReso.postResponse}" var="subpost">
 
 <!-- 语音 -->
  <c:if test="${ subpost.type=='1'}"> 
  <div class="divContent3">
    <div class="dc3_left"><img src="${ subpost.authorLogoUrl}" /></div>
    
    <div class="dc3_right">
      
      <div class="dc3_r_con_no">
      	
        <div class="dc3_r_con_no1">
          ${ subpost.authorName}<br /><span class="time"><mydate:date value="${subpost.utime}"></mydate:date></span>
        </div>
        
        <div class="dc3_r_con2">
          <div class="dc3_r_con2_1">
            <img src="<%=cbasePath%>/resources/assets/image/share/voice_play_btn.png" />
          </div>
        </div>
      </div>
      
    </div>
    <div class="clear"></div>
  </div>
 </c:if>
 
 
 <!--  文字回复 -->
 <c:if test="${ subpost.subPosts==null and subpost.type=='0'}">
  <div class="divContent3">
    <div class="dc3_left"><img src="${ subpost.authorLogoUrl}" /></div>
    
    <div class="dc3_right">
      
      <div class="dc3_r_con_no">
      
        <div class="dc3_r_con1">
          <div class="dc3_r_con1_1">
           ${ subpost.authorName}<br /><span class="small_f"><mydate:date value="${subpost.utime}"></mydate:date></span>
          </div>
          <div class="clear"></div>
        </div>
        
        <div class="dc3_r_con2">
          <div class="dc3_r_con2_2 small_f">${ subpost.likesCount}</div>
          <div class="dc3_r_con2_1">
           ${ subpost.message}"
          </div>
          <div class="clear"></div>
        </div>
      </div>
      
    </div>
    <div class="clear"></div>
  </div>
  </c:if>
  
  
  
   <!--  文字回复 回复有回复 -->
   <c:if test="${ subpost.subPosts!=null}">
  <div class="divContent3">
    <div class="dc3_left"><img src="${ subpost.authorLogoUrl}" /></div>
    
    <div class="dc3_right">
      
      <div class="dc3_r_con">
        <div class="dc3_r_con_no1">
           ${subpost.authorName}<br /><span class="time"><mydate:date value="${subpost.utime}"></mydate:date></span>
        </div>
        
        <div class="dc3_r_con2">
          <div class="dc3_r_con2_1">
            ${subpost.message}
          </div>
          <div class="clear"></div>
        </div>
      </div>
      
      <div class="dc2_r_com">
           <c:forEach items="${subpost.subPosts}" var="subsubpost">
        <span class="org">${subsubpost.authorName}</span>：${subsubpost.message}<br />
       </c:forEach>
      </div>
      <!-- <div class="dc2_r_all">全部(${ subpost.subPostsSize})</div> -->
      
    </div>
    <div class="clear"></div>
  </div>
  
  </c:if>
  
  </c:forEach>
  
  
  <div class="divTop1">
    <div class="logo"><img src="<%=cbasePath%>/resources/assets/image/share/logo1.png" /></div>
    <div class="button">
      <a href=" https://password.yunxuetang.cn/t/yxt/iphone/yxt/beta/yxt.ipa"><img src="<%=cbasePath%>/resources/assets/image/share/button.png" ></a>
    </div>
    <div class="clear"></div>
  </div>
  
</div>

</body>
</html>
