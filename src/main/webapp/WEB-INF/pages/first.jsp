<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
  </head>
  
  <body style="background-color: #dfe8f6;"><div id="first_content" style="width: 100%; height: 100%; ">
  	<table class="fristTable" >
  		<tr>
  			<td style="width: 50%" rowspan="2">
  				<div class="title"><img src="img/user.png"/>用户信息</div>
  				<div class="userContent">
  					欢迎用户: <b>${user.userName}</b><br/><br/>
  					真实姓名：${user.userRealName}<br/><br/>
  					上次登录IP：${user.userLastIp}<br/><br/>
  					上次登录时间: ${user.userLastTime}
  				</div>
  			</td>
  			<td style="width: 50%;">
  				<div class="title"><img src="img/instruction.gif"/>系统介绍</div>
  				<div class="content">
  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本系统实现了企业OMS与各个电商平台的接口，主要功能包括：
  					1，订单的定时拉取，推送；
  					2，退款退货单的定时拉取和推送；
  					3，铺货信息的定时拉取和推送；
  					4，定时任务可灵活配置；
  					5，操作日志详细记录了拉取推送的错误信息；
  					6，系统缓存管理，大大提高系统的运行效率；
  					7，手动推送拉取管理，为用户提供灵活的手动操作；
  					8，系统用户管理，为本系统的使用者提供安全的使用环境；
  					9，本系统还提供了定时检查程序，定时检查各个电商平台数据的拉取情况。
  				</div>
  			</td>
  		</tr>
  		<tr>
  			
  			<td>
  				<div class="title"><img src="img/developer.png"/>开发者</div>
  				<div class="content">
  					鼎捷软件<br/><br/> 
  				</div>
  			</td>
  		</tr>
  	</table></div>
  </body>
</html>
