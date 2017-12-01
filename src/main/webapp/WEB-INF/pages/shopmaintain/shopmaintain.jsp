<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">

<title>店铺基础资料管理</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="js/extjs/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="css/common.css"/>
<link rel="stylesheet" type="text/css" href="css/icon.css"/>
<link rel="stylesheet" type="text/css" href="css/loading.css"/>
<script type="text/javascript" src="js/extjs/ext-base.js"></script>
<script type="text/javascript" src="js/extjs/ext-all.js"></script>
<script type="text/javascript" src="js/extjs/src/locale/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="js/myjs/shopmaintain/shopMaintainInfo.js"></script>
<script type="text/javascript" src="js/myjs/shopmaintain/shopMaintainAdd.js"></script>

<script type="text/javascript">
	Ext.onReady(function(){
		ShopMaintainPanel = Ext.extend(Ext.Panel,{
			id: 'shopMaintainPanelId',
			constructor: function(ctx){
				var shopMaintainGridPanel = new ShopMaintainGridPanel(ctx);
				ShopMaintainPanel.superclass.constructor.call(this,{
					style: 'margin:0 auto',
					border: false,
					items: [shopMaintainGridPanel]
				})
			}
		});
		var shopMaintainPanel = new ShopMaintainPanel("<%=basePath%>");
		
		shopMaintainPanel.render("shopMaintainPanel");
	});
</script>
</head>
<body>
	<div id="shopMaintainPanel"></div>
</body>
</html>