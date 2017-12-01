<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <!-- 引入页面相关的js文件 -->
	<!-- -->
	<!-- 构造显示页面 -->
	<link rel="stylesheet" type="text/css" href="js/extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" type="text/css" href="css/icon.css"/>
	<link rel="stylesheet" type="text/css" href="css/loading.css"/>
	<script type="text/javascript" src="js/extjs/ext-base.js"></script>
	<script type="text/javascript" src="js/extjs/ext-all.js"></script>
    <script type="text/javascript" src="js/myjs/system/userInfo.js"></script>
  	<script type="text/javascript" src="js/myjs/system/userAdd.js"></script>
  	<script type="text/javascript" src="js/myjs/system/userUpdate.js"></script>
	
	<!-- 初始化显示页面 -->
    <title>用户管理</title>
	<script type="text/javascript">
	Ext.onReady(function(){
		var userInfoPanel = new UserInfoPanel();
		userInfoPanel.render("userDiv");
	})
	</script>
	
  </head>
  <body>
  	<div id="userDiv" ></div>
  </body>
</html>
