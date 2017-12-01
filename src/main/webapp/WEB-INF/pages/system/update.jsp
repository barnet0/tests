<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>密码修改</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 引入页面相关的js文件 -->
	<link rel="stylesheet" type="text/css" href="js/extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" type="text/css" href="css/icon.css"/>
	<link rel="stylesheet" type="text/css" href="css/loading.css"/>
	<script type="text/javascript" src="js/extjs/ext-base.js"></script>
	<script type="text/javascript" src="js/extjs/ext-all.js"></script>
    <script type="text/javascript" src="js/myjs/system/update.js"></script>
	<!-- -->
	<!-- 构造显示页面 -->
	<script type="text/javascript">
	</script>
	<!-- 初始化显示页面 -->
	<script type="text/javascript">
	Ext.onReady(function(){
		var username = "${user.userName}";
		var updateFormPanel = new UpdateFormPanel(username);
		updateFormPanel.render('updateDiv');
	})
	</script>
	
  </head>
  <body>
  	<div id="updateDiv" ></div>
  </body>
</html>
