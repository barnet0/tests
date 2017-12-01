<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>定时任务执行情况查询 </title>
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
	
    <script type="text/javascript" src="js/myjs/logpushmaintain/logPushMaintainInfo.js"></script>	
    <!-- <script type="text/javascript" src="js/myjs/logpushmaintain/logPushDetailWin.js"></script> -->
	<script type="text/javascript">
	Ext.onReady(function(){
		LogPushMaintainPanel = Ext.extend(Ext.Panel,{
			id: 'logPushMaintainPanelId',
			constructor: function(ctx){
				var logPushMaintainQueryPanel = new LogPushMaintainQueryPanel(ctx);
				var logPushMaintainGridPanel = new LogPushMaintainGridPanel(ctx);
				LogPushMaintainPanel.superclass.constructor.call(this,{
					style: 'margin:0 auto',
					border: false,
					items: [logPushMaintainQueryPanel, logPushMaintainGridPanel]
				})
			}
		});
		var logPushPanel = new LogPushMaintainPanel("<%=basePath%>");
		
		logPushPanel.render("logPushMaintainPanel");
	});
	</script>
  </head>
  
  <body>
	<div id="logPushMaintainPanel"></div>
  </body>
</html>
