<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title> 手 動 程 序 操 作 </title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="js/extjs/resources/css/ext-all.css"/>
	<!-- <link rel="stylesheet" type="text/css" href="js/extjs/resources/css/xtheme-gray.css"/> -->
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" type="text/css" href="css/icon.css"/>
	<link rel="stylesheet" type="text/css" href="css/loading.css"/>
	<script type="text/javascript" src="js/extjs/ext-base.js"></script>
	<script type="text/javascript" src="js/extjs/ext-all.js"></script>
	<script type="text/javascript" src="js/extjs/src/locale/ext-lang-zh_CN.js"></script>
	
    <script type="text/javascript" src="js/myjs/manualschedule/manualScheduleQuery.js"></script>	
	<script type="text/javascript">
	Ext.onReady(function(){
		var manualScheduleQueryPanel = new ManualScheduleQueryPanel();
		manualScheduleQueryPanel.render("manualDiv");
	})
	</script>
  </head>
  
  <body>
	<div id="manualDiv"></div>
  </body>
</html>
