<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>角色权限分配</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 引入页面相关的js文件 -->
	<!-- -->
	<!-- 构造显示页面 -->
	<script type="text/javascript">
	</script>
	<!-- 初始化显示页面 -->
	<script type="text/javascript">
		var perAssign = new PerAssign();
		var tabId = Ext.getCmp('mainTab').getActiveTab().id.split('_')[1];
		juage(tabId,"role",perAssign,"perAssign");
		var width = Ext.getCmp('mainTab').getActiveTab().getInnerWidth();
	 	var height = Ext.getCmp('mainTab').getActiveTab().getInnerHeight();
		var activeTab = Ext.getCmp('perAssignId');
	 	if(activeTab){
	 	  	activeTab.setWidth(width);
	 	  	activeTab.setHeight(height);
	 	}
	</script>
	
  </head>
  <body>
  	<div id="perAssign" ></div>
  </body>
</html>