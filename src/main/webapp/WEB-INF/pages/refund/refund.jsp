<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<title>退款退货信息查看</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="js/extjs/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="css/common.css"/>
<link rel="stylesheet" type="text/css" href="css/icon.css"/>
<link rel="stylesheet" type="text/css" href="css/loading.css"/>
<script type="text/javascript" src="js/extjs/ext-base.js"></script>
<script type="text/javascript" src="js/extjs/ext-all.js"></script>
<script type="text/javascript" src="js/extjs/src/locale/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="js/myjs/refund/refundInfo.js"></script>

<script type="text/javascript">
	Ext.onReady(function(){
		RefundPanel = Ext.extend(Ext.Panel,{
			id: 'refundPanelId',
			constructor: function(ctx){
				var refundGridPanel = new RefundGridPanel(ctx);
				var refundQueryPanel = new RefundQueryPanel(ctx);
				RefundPanel.superclass.constructor.call(this,{
					style: 'margin:0 auto',
					border: false,
					items: [refundQueryPanel,refundGridPanel]
				})
			}
		});
		var refundPanel = new RefundPanel("<%=basePath%>");
		
		refundPanel.render("refundPanel");
	});
</script>
</head>
<body>
	<div id="refundPanel"></div>
</body>
</html>