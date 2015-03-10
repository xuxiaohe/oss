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
    <title>${kng.title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <link href="http://ztiao.cn/Content/bootstrap/bootstrap3.3.0.css" rel="stylesheet" />
    <link href="http://ztiao.cn/Content/style.css" rel="stylesheet" />
</head>
<body>
<div class="col-xs-24 col-sm-18 col-sm-offset-3 pa over-hidden" style="height:100%;">
    <div id="playercontainer" class="player-container pa" style="width:100%;height:100%;">




    </div>

</div>
</body>
</html>

<script src="http://ztiao.cn/Scripts/sewise-player-master/player/sewise.player.min.js"></script>

<script type="text/javascript">

    SewisePlayer.localPath = "http://ztiao.cn/Scripts/sewise-player-master/player/";
    SewisePlayer.setup({
        server: "vod",
        type: "${kng.pcItems[0].format}",
        videourl: "${kng.pcItems[0].furl}",
        autostart: "true",
        lang: "zh_CN",
        skin: "vodOrange",
        //logo: ,
        primary: "html5",
        draggable: "true",
        poster: "${kng.pcItems[0].logoUrl}",
        title: "${kng.title}",
        buffer: 30,
        fallbackurls: {}
    }, "playercontainer");

</script>
