<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.digiwin.ecims.system.util.CurrentDate"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  	<base href="<%=basePath%>"/>
    <title>ECIMS电商接口管理平台</title>
    	<link rel="stylesheet" type="text/css" href="js/extjs/resources/css/ext-all.css"/>
		<link rel="stylesheet" type="text/css" href="css/common.css"/>
		<link rel="stylesheet" type="text/css" href="css/icon.css"/>
		<link rel="stylesheet" type="text/css" href="css/loading.css"/>
		<script type="text/javascript" src="js/extjs/ext-base.js"></script>
		<script type="text/javascript" src="js/extjs/ext-all.js"></script>
		<!-- 主页 -->
  		<script type="text/javascript" src="js/myjs/system/main.js"></script>
  		<!-- 系统管理 -->
  		<script type="text/javascript" src="js/myjs/system/userInfo.js"></script>
  		<script type="text/javascript" src="js/myjs/system/userAdd.js"></script>
  		<script type="text/javascript" src="js/myjs/system/userUpdate.js"></script>
  		<script type="text/javascript">
	  		Ext.onReady(function(){
	  			var username = "${user.userName}"; //获取登录用户
	  			var date = "<%=CurrentDate.getDateWeek()%>";
	  			var main = new mainPage(username, date);
	  			Ext.get('loading').remove();
				Ext.get('loading-mask').fadeOut({remove:true});
			});
  		</script>
  </head>
  
	<body style="margin:0px;">
		<div id="loading">
			<div  class="loading-indicator">
	            <div class="loading-indicator">
			        <img src="img/loading.gif" width="39" height="39" />
			        <span id="loading-yfo">ECIMS电商接口管理平台</span><br/>
			        <span id="loading-yfo"><a href="http://" target="_blank">鼎捷软件</a></span><br/>
			        <span id="loading-msg">正在初始化系统，请稍后...</span>
			    </div>
			</div>
		</div>
		<div id="loading-mask">
		</div>
	</body>
</html>
