/**
 * @author sux
 * @time 2011-1-11
 * @desc main page
 */
 
 var mainPage = Ext.extend(Ext.Viewport,{
 
 
 	/**
 	 * 构造方法中进行整体布局
 	 */
 	constructor: function(username, date){
 		mainPage.superclass.constructor.call(this,{
 			layout: 'border',
 			items: [{
 				region: 'north',
 				xtype: 'panel',
 				//html: '人力资源管理系统',
 				bodyStyle: 'height: 66px;',
 				layout: 'absolute',
 				html: '<table class="header"><tr ><td class="header_left"></td><td class="header_center"></td>' +
 						'<td class="header_right"><div id="user_header">'+date+'&nbsp;<img src="img/user.png"/>&nbsp;' +
 					  		username+'&nbsp;&nbsp;<a href="system/exit.do">注销</a>' +
 					  	'</div></td>' +
 					  '</tr></table>'
 			},{
 				region: 'west',
 				frame: 'true',
 				width: '200',
 				id: 'menu_tree',
 				xtype: 'panel',
 				autoScroll: true,
 				collapsible: true,
 				title : "导航菜单",
 				split : true,
 		        minSize: 150,
 		        maxSize: 300,
 		        //margins: '0 3 5 5',
 				iconCls : 'menu-navigation',
 				autoScroll : false,
 				layout : 'accordion',
 				items: [{ title: "系统管理",iconCls: "config",items:sysConfOptTree}]
 			},this.center,{
 				region: 'south',
 				width: '100%',
 				frame: true,
 				height: 30,
 				html: "<div id='author'>Copyright &copy 201507 xxxxxxxxxxxxxxxxxxxxxxxx</div>"
 			}]
 		});
 	},
 	center: {
 		region: 'center',
 		border: false,
		html: '<iframe id="mainTab" src="system/first.do" frameborder=0 width=100% height=100%/>'
 	},
 	
 });

function clickMenu(url){
	var iframe = document.getElementById("mainTab");
	iframe.src = url;
}

var sysConfOptTree = new Ext.tree.TreePanel({
	id : "sysConfOptTree",
	iconCls : 'tabs',
	autoScroll : true,
	animate : false,
	enableDD : true,
	containerScroll : true,
	rootVisible : false,
	height : 300
});
var sysConfOptRoot = new Ext.tree.TreeNode({
	id : '0',
	text : '',
	leaf : false
});
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'sysparam',
	text : '系统参数管理',
	leaf : true,
	listeners:{
        "click":function(node,event){
    		clickMenu('sysParamMaintain/showSysParamMaintain.do')
        }
	}
}));
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'ontimetask',
	text : '定时任务管理',
	leaf : true,
	//url:'ontimeTask/showOnTimeTask.do',
	listeners:{
        "click":function(node,event){
        	//alert(node.attributes.url);
    		clickMenu('ontimeTask/showOnTimeTask.do')
        }
	}
}));
//----add by Xavier on 20150810
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'syspushlog',
	text : '定时任务执行情况查询',
	leaf : true,
	//url:'ontimeTask/showOnTimeTask.do',
	listeners:{
        "click":function(node,event){
        	//alert(node.attributes.url);
    		clickMenu('logPushMaintain/showPushLogMaintain.do')
        }
	}
}));
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'handopt',
	text : '手动拉取推送管理',
	leaf : true,
	//url:'ontimeTask/showOnTimeTask.do',
	listeners:{
        "click":function(node,event){
        	//alert(node.attributes.url);
    		clickMenu('manualschedule/showManualSchedule.do')
        }
	}
}));
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'syslog',
	text : '系统日志管理',
	leaf : true,
	listeners:{
        "click":function(node,event){
    		clickMenu('logMaintain/showLogMaintain.do')
        }
	}
}));
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'sysorder',
	text : '系统交易查看',
	leaf : true,
	listeners:{
        "click":function(node,event){
    		clickMenu('bizdata/showOrder.do')
        }
	}
}));
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'sysrefund',
	text : '系统退单查看',
	leaf : true,
	listeners:{
        "click":function(node,event){
    		clickMenu('bizdata/showRefundAndReturn.do')
        }
	}
}));
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'sysitem',
	text : '系统商品查看',
	leaf : true,
	listeners:{
        "click":function(node,event){
    		clickMenu('bizdata/showItem.do')
        }
	}
}));
//sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
//	id : 'shop',
//	text : '店铺资料管理',
//	leaf : true,
//	//url:'ontimeTask/showOnTimeTask.do',
//	listeners:{
//        "click":function(node,event){
//        	//alert(node.attributes.url);
//    		clickMenu('shopmaintain/showShop.do')
//        }
//	}
//}));
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'usermng',
	text : '系统用户管理',
	leaf : true,
	//url:'ontimeTask/showOnTimeTask.do',
	listeners:{
        "click":function(node,event){
        	//alert(node.attributes.url);
    		clickMenu('system/userInfo.do')
        }
	}
}));
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'userPwdUpdate',
	text : '系统用户密码管理',
	leaf : true,
	//url:'ontimeTask/showOnTimeTask.do',
	listeners:{
        "click":function(node,event){
        	//alert(node.attributes.url);
    		clickMenu('system/userPwdUpdate.do')
        }
	}
}));
sysConfOptRoot.appendChild(new Ext.tree.TreeNode({
	id : 'returnfirst',
	text : '返回主页',
	leaf : true,
	//url:'ontimeTask/showOnTimeTask.do',
	listeners:{
        "click":function(node,event){
        	//alert(node.attributes.url);
    		clickMenu('system/first.do')
        }
	}
}));

sysConfOptTree.setRootNode(sysConfOptRoot);
 
 
