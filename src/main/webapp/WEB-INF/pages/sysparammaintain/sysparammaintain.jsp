<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>手动拉取推送管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="js/extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" type="text/css" href="css/icon.css"/>
	<link rel="stylesheet" type="text/css" href="css/loading.css"/>
	<script type="text/javascript" src="js/extjs/ext-base.js"></script>
	<script type="text/javascript" src="js/extjs/ext-all.js"></script>
	<script type="text/javascript" src="js/extjs/src/locale/ext-lang-zh_CN.js"></script>
	
    <script type="text/javascript" src="js/myjs/sysparammaintain/sysParamMaintainInfo.js"></script>	
    <script type="text/javascript" src="js/myjs/sysparammaintain/sysParamDetailWin.js"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		SysParamMaintainPanel = Ext.extend(Ext.Panel,{
			id: 'sysParamMaintainPanelId',
			constructor: function(ctx){
				var sysParamMaintainQueryPanel = new SysParamMaintainQueryPanel(ctx);
				var sysParamMaintainGridPanel = new SysParamMaintainGridPanel(ctx);
				SysParamMaintainPanel.superclass.constructor.call(this,{
					style: 'margin:0 auto',
					border: false,
					items: [sysParamMaintainQueryPanel,sysParamMaintainGridPanel]
				})
			}
		});
		var sysParamMaintainPanel = new SysParamMaintainPanel("<%=basePath%>");
		
		sysParamMaintainPanel.render("sysParamMaintainPanel");
	});
	</script>
  </head>
  
  <body>
	<div id="sysParamMaintainPanel"></div>
  </body>
</html>
