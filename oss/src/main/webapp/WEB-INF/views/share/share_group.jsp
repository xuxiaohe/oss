<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<link href="<%=cbasePath%>/resources/assets/css/share/main2.css" rel="stylesheet" type="text/css">
<title>云学堂——让职场学习更简单</title>
</head>
<body>
<div class="wrapper">

  <div class="divTop">
    <div class="logo"><img src="<%=cbasePath%>/resources/assets/image/share/logo.png" /></div>
    <div class="button">
      <a href="itms-services://?action=download-manifest&url=https://passport.yunxuetang.cn/t/yxt/yxt.ipa"><img src="<%=cbasePath%>/resources/assets/image/share/botton.png" ></a>
    </div>
    <div class="clear"></div>
  </div>

  <div class="divContent1">
    <img src="<%=cbasePath%>/resources/assets/image/share/home_bg.png" />
    
    <div class="dc1">
      <div class="dc1_1">
        <img src="${ Group.data.result.logoUrl}" />
      </div>
      <div class="dc1_2">
        ${Group.data.result.groupName}
        <br/><span class="font12">成员 ${fn:length(Group.data.result.member)}人 | 课程 ${ courseList.data.total_rows}个</span>
      </div>
    </div>
    
    <div class="dc1_3">
      <img src="<%=cbasePath%>/resources/assets/image/share/botton.png" />
    </div>
  </div>
  
  <div class="divContent2">
<script language="javascript"> 
	function woaicssq(num){
	for(var id = 1;id<=4;id++)
	{
	var MrJin="woaicss_con"+id;
	if(id==num)
	document.getElementById(MrJin).style.display="block";
	else
	document.getElementById(MrJin).style.display="none";
	}
	if(num==1) 
	document.getElementById("tag_switching").className="title";
	if(num==2)
	document.getElementById("tag_switching").className="title";
	if(num==3)
	document.getElementById("tag_switching").className="title";
	if(num==4)
	document.getElementById("tag_switching").className="title";
	}
</script>
	<ul class="title" id="tag_switching">
      <li><a href="#" onmouseover="javascript:woaicssq(1)">资料</a></li>
	  <li><a href="#" onmouseover="javascript:woaicssq(2)">课程(${ courseList.data.total_rows})</a></li>
	  <li><a href="#" onmouseover="javascript:woaicssq(3)">话题(${TopicList.data.total_rows})</a></li>
	  <li><a href="#" onmouseover="javascript:woaicssq(4)">干货(${DryList.data.total_rows})</a></li>
	</ul>
	<div class="dc2_1" id="woaicss_con1" style="display:none;">
      <div class="a">
        <div class="a_title">
          <div class="a_title_left">基本信息</div>
          <div class="a_title_right"></div>
        </div>
        
        <div class="clear"></div>
        
        <div class="a1">
          <div class="a1_left">创建</div>
          <div class="a1_right"><mydate:date value="${Group.data.result.ctime}"></mydate:date></div>
        </div>
        
        <div class="a1">
          <div class="a1_left">所在地</div>
          <div class="a1_right">
          
         ${Group.data.result.localName}</div>
        </div>
        
        <div class="a2">
          <div class="a1_left">介绍</div>
          <div class="a1_right">${Group.data.result.intro} </div>
        </div>
      </div>
      
      <div class="a">
        <div class="a_title">
          <div class="a_title_left">群成员(${fn:length(Group.data.result.member)}人)</div>
          <div class="a_title_right"><img src="<%=cbasePath%>/resources/assets/image/share/in.png" /></div>
        </div>
        
        <div class="clear"></div>
        
        <div class="a1">
        <c:forEach items="${Member}" var="submember">
          <div class="a1_pic">
            <img src="${submember.logoURL }" />
          </div>
        </c:forEach>
        </div>
      </div>
      
      <div class="a">
        <div class="a_title">
          <div class="a_title_left">标签(${ fn:length(tag.data.result)}个)</div>
        </div>
        
        <div class="clear"></div>
        
        <div class="a1">
        <c:forEach items="${tag.data.result}" var="tagName">
          <div class="a1_tag">${tagName}</div>
       </c:forEach>
        </div>
      </div>

	</div>
	
	 <!-- 课程 -->
    
    <div class="dc2_2" id="woaicss_con2" style="display:none;">
     <c:forEach items="${courseList.data.result}" var="subCourse">
      <div class="b">
        <div class="b1">
          <div class="b1_left">由 ${ subCourse.whoImport.nickName} 分享</div>
          <div class="b1_right"><mydate:date value="${subCourse.ctime}"></mydate:date></div>
        </div>
        
        <div class="b2">
          <img src="${subCourse.course.logoUrl}" />
        </div>
        
        <div class="b3">
          ${subCourse.course.intro}
        </div>
        
        <div class="b4">
          <ul class="b4_label">
          <c:forEach items="${subCourse.course.tags}" var="tag">
          <li>${tag}</li>
          </c:forEach>
          </ul>
        </div>
      
        <div class="b5">
          <div class="b5_1">
            ${subCourse.studyCount}人学习
          </div>
          <div class="b5_1">
            ${subCourse.favCount}人收藏
          </div>
          <div class="b5_1">
             ${subCourse.shareCount}人分享
          </div>
          <div class="clear"></div>
        </div>
      </div>
      
      </c:forEach>
    
    </div>
    
    
    <!-- 话题tab -->
	<div class="dc2_2" id="woaicss_con3" style="display:none;">
	
	<c:forEach items="${TopicList.data.result}" var="subtop">
	  <div class="b">
        <div class="c">
          <div class="c_left">
            <img src="<%=cbasePath%>/resources/assets/image/share/default_head_pic.png" />
          </div>
    
          <div class="c_right">
            <div class="c_right1">
              <div class="c_right1_1">
                <div class="c_right1_1l">${subtop.authorName}</div>
                <div class="c_right1_1r"><mydate:date value="${subtop.ctime}"></mydate:date></div>
                <div class="clear"></div>
              </div>
            </div>
          </div>
          
        </div>
        
        <div class="b3">
        ${subtop.content}
        </div>
        
      <c:if test="${subtop.hasImage==true}" >
        <div class="b2">
          <img src="${subtop.picUrl}" />
        </div>
      </c:if>
        
      <!--   <div class="b4">
          ${subtop.localName}
        </div>--> 
        
        <div class="c1">
          <div class="dc_2">
            <img src="<%=cbasePath%>/resources/assets/image/share/comment.png" />&nbsp; ${subtop.newReplyCount}
          </div>
          <div class="dc_2">
            <img src="<%=cbasePath%>/resources/assets/image/share/like.png" />&nbsp;${subtop.likesCount}
          </div>
          <div class="dc_2">
            <img src="<%=cbasePath%>/resources/assets/image/share/dislike.png" />&nbsp;${subtop.unLikeCount}
          </div>
          <div class="dc_2">
            <img src="<%=cbasePath%>/resources/assets/image/share/share.png" />&nbsp;${subtop.shareCount}
          </div>
          <div class="clear"></div>
        </div>
      </div>
      
      </c:forEach>
      
    <!--  <div class="b">
        <div class="c">
          <div class="c_left">
            <img src="<%=cbasePath%>/resources/assets/image/share/default_head_pic.png" />
          </div>
    
          <div class="c_right">
            <div class="c_right1">
              <div class="c_right1_1">
                <div class="c_right1_1l">萌图图</div>
                <div class="c_right1_1r">昨天</div>
                <div class="clear"></div>
              </div>
            </div>
          </div>
          
        </div>
        
        <div class="b3">
          中国教育电视台是唯一国家级专业电视台,承载着科教兴国和人才强国战略的重任,以“学习改变命运,学习成就未来!”
        </div>
        
        <div class="b4">
          北京·朝阳区·恋日国际<br />
        </div>
        
        <div class="c1">
          <div class="dc_2">
            <img src="<%=cbasePath%>/resources/assets/image/share/comment.png" />&nbsp;1000
          </div>
          <div class="dc_2">
            <img src="<%=cbasePath%>/resources/assets/image/share/like.png" />&nbsp;3000
          </div>
          <div class="dc_2">
            <img src="<%=cbasePath%>/resources/assets/image/share/dislike.png" />&nbsp;3000
          </div>
          <div class="dc_2">
            <img src="<%=cbasePath%>/resources/assets/image/share/share.png" />&nbsp;3000
          </div>
          <div class="clear"></div>
        </div>
      </div> --> 
      
	</div>
    
    <!-- 干货 -->
	<div class="dc2_2" id="woaicss_con4" style="display:block;">
    <c:forEach items="${DryList.data.result}" var="subDry">
      <div class="b">
        <div class="b1">
          <div class="b1_left">由 ${ subDry.sharePerList[0].userName} 分享</div>
          <div class="b1_right"><mydate:date value="${subDry.ctime}"></mydate:date></div>
        </div>
        
        <div class="b2">
          <img src="${subDry.fileUrl}" />
        </div>
        
        <div class="b3">
          ${subDry.message}
        </div>
        
      <!--  <div class="b4">
          <ul class="b4_label">
            <li>教育</li>
            <li>科教兴国</li>
            <li>人才强国</li>
            <li>未来</li>
          </ul>
        </div> --> 
      
        <div class="b5">
          <div class="b5_1">
            ${ fn:length(subDry.whoView) }人学习
          </div>
          <div class="b5_1">
            ${ subDry.favCount}人收藏
          </div>
          <div class="b5_1">
             ${ subDry.shareCount}人分享
          </div>
          <div class="clear"></div>
        </div>
      </div>
      </c:forEach>
      
    </div>
  </div>
  
  
  
  <div class="divTop1">
    <div class="logo"><img src="<%=cbasePath%>/resources/assets/image/share/logo1.png" /></div>
    <div class="button">
      <a href="itms-services://?action=download-manifest&url=https://passport.yunxuetang.cn/t/yxt/yxt.ipa"><img src="<%=cbasePath%>/resources/assets/image/share/botton.png" ></a>
    </div>
    <div class="clear"></div>
  </div>
</div>
</body>
</html>