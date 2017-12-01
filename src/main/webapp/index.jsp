<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html>
  <head>
  	<title>ECIMS接口管理平台</title>
  	<link rel="stylesheet" type="text/css" href="js/extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<script type="text/javascript" src="js/extjs/ext-base.js"></script>
	<script type="text/javascript" src="js/extjs/ext-all.js"></script>
  	<script type="text/javascript" src="<%=path%>/js/myjs/system/login.js"></script>
  </head>
  
  <body style="background-color: #ddeefb;overflow:hidden">
  	<noscript>        
         <div>    
             <span style="font: bold 20px Arial; color:#F8F8FF; background: maroon; vertical-align: middle">浏览器没有打开JavaScript支持！</span>    
         </div>     
    </noscript>    
  	<div class="index">
  	<table class="login">
  		<tr>
  			<td height="20px" align="center" style="color: red;">&nbsp;${actionmessage}</td>
  		</tr>
  		<tr>
  			<td><div id="loginForm" ></div> </td>
  		</tr>
  	</table>
  	</div>
  	<div class="index_bottom">
  		&& 版权所有&copy;鼎捷软件 
<!--   		<form method="post" action="sysParamMaintain/test.do"> -->
<!-- 	  		<input type="submit" value="测试数据库" /> -->
<!-- 	  	</form> -->
  	</div>
<!--    		<form method="post" action="test/test.do">
 	  		<input type="submit" value="重新从id_t推送遗漏的订单" />
 	  	</form>
 	  	<form method="post" action="test/test2.do">
 	  		<input type="submit" value="重新从oidstatusdiff_t推送状态不同的订单" />
 	  	</form>  -->
  </body>
</html>
