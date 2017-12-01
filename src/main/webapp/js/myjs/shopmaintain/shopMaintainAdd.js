/**
 * 新增店铺基础资料窗口
 * @author mowj 20150715
 * 
 */
Ext.namespace("mercuryecinf.shopmaintain.info");

ShopMaintainAddWin = Ext.extend(Ext.Window,{
	id: 'shopMaintainAddWinId',
	constructor: function(){
		var shopMaintainAddPanel = new ShopMaintainAddPanel();
		ShopMaintainAddWin.superclass.constructor.call(this, {
			title: '新增店铺',
			resizable: false,
			width: 750,
			modal: true,
			items: [shopMaintainAddPanel]
		})			
	}
});

ShopMaintainAddPanel = Ext.extend(Ext.form.FormPanel,{
	id: 'shopMaintainAddPanelId',
	constructor: function(){
		Ext.QuickTips.init();
		var reader = new Ext.data.JsonReader({},[{
			name: 'shopMaintain.shopid', mapping: 'aomsshop001'
		},{
			name: 'shopMaintain.shopname', mapping: 'aomsshop002'
		},{
			name: 'shopMaintain.shoptypecode', mapping: 'aomsshop003'
		},{
			name: 'shopMaintain.shopappkey', mapping: 'aomsshop004'
		},{
			name: 'shopMaintain.shopappsecret', mapping: 'aomsshop005'
		},{
			name: 'shopMaintain.shopaccesstoken', mapping: 'aomsshop006'
		},{
			name: 'shopMaintain.shoprefreshtoken', mapping: 'aomsshop007'
		},{
			name: 'shopMaintain.shoprawjson', mapping: 'aomsshop008'
		},{
			name: 'shopMaintain.shopnameinec', mapping: 'aomsshop009'
		},{
			name: 'shopMaintain.shopredirecturl', mapping: 'aomsshop010'
		},{
			name: 'shopMaintain.shoptype', mapping: 'aomsshop011'
		},{
			name: 'shopMaintain.shopcrtdt', mapping: 'aomsshopcrtdt'
		},{
			name: 'shopMaintain.shopmoddt', mapping: 'aomsshopmoddt'
		}]);
		ShopMaintainAddPanel.superclass.constructor.call(this,{
			padding: '10',
			width: 750,
			height: 250,
			frame: true,
			layout: 'table',
			reader: reader,
			layoutConfig: {
				columns: 2
			},
			defaults: {
				labelWidth: 90,
				labelAlign: 'right'
			},
			items: [{
				layout:'form',
				width: 300,
				items:[{
					xtype: 'textfield',
					fieldLabel: '店铺编号',
					width: 200,
					allowBlank: false,
					msgTarget: 'qtip',
					emptyText: '不能为空',
					blankText: '请填写店铺编号',
					id: 'shopMaintain_shopid',
					name: 'shopMaintain.shopid',
					listeners: {'blur': checkShopIdFn}
				}]
			},{
				width: 300,
				layout:'form',
				items:[{
					xtype: 'textfield',
					fieldLabel: '店铺名称',
					allowBlank: false,
					msgTarget: 'qtip',
					blankText: '不能为空',
					emptyText: '不能为空',
					name: 'shopMaintain.shopname',
					width: 200
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'combo',
					fieldLabel: '店铺平台编号',
					blankText: '请选择',
					msgTarget: 'qtip',
					width: 200,
					allowBlank: false,
					triggerAction: 'all',
					mode: 'local',
					editable: false,
					name: 'shopMaintain.shoptypecode',
					store: new Ext.data.SimpleStore({
						fields: ['name','value'],
						data: [["0-淘宝&天猫","0"],
						       ["1-京东","1"],
						       ["2-一号店","2"],
						       ["3-工商银行","3"],
						       ["4-苏宁","4"],
						       ["5-当当","5"],
						       ["A-淘宝分销","A"]]
					}),
					displayField: 'name',
					valueField: 'value',
					value:0
				}]
			},{
				width: 300,
				layout:'form',
				items:[{
					xtype: 'combo',
					fieldLabel: '店铺类型',
					allowBlank: false,
					triggerAction: 'all',
					mode: 'local',
					editable: false,
					msgTarget: 'qtip',
					blankText: '不能为空',
					emptyText: '不能为空',
					name: 'shopMaintain.shoptype',
					width: 200,
					store: new Ext.data.SimpleStore({
						fields: ['name','value'],
						data: [["淘宝","淘宝"],
						       ["天猫","天猫"],
						       ["京东","京东"],
						       ["一号店","一号店"],
						       ["工商银行","工商银行"],
						       ["苏宁","苏宁"],
						       ["当当","当当"],
						       ["淘宝分销","淘宝分销"],
						       ["其他","其他"]]
					}),
					displayField: 'name',
					valueField: 'value',
					renderer: function(value){
//						if(value==1){
//							return "1-有效";
//						}else{
//							return "2-无效";
//						}
					}
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'textfield',
					fieldLabel: 'AppKey',
					blankText: '请输入',
					msgTarget: 'qtip',
					width: 200,
					allowBlank: false,
					editable: false,
					name: 'shopMaintain.shopappkey'
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'textfield',
					fieldLabel: 'AppSecret',
					blankText: '请输入',
					msgTarget: 'qtip',
					width: 200,
					allowBlank: false,
					editable: false,
					name: 'shopMaintain.shopappsecret'
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'textfield',
					fieldLabel: '店铺在平台上的名称 ',
					name: 'shopMaintain.shopnameinec',
					allowBlank: true,
					msgTarget: 'qtip',
					width: 200
				}]
			},{
				width: 300,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: 'App设定的回调地址',
					width: 200,
					id: 'shopMaintain_shopredirecturl',
					name: 'shopMaintain.shopredirecturl',
					allowBlank: true,
					msgTarget: 'qtip',
					emptyText: 'http://',
					blankText: '不能为空',
//					regex: /^$/,
//					regexText: 'IP地址格式不正确',
					listeners: {'blur': checkShopRedirectUrlFn}
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'textfield',
					fieldLabel: 'AccessToken',
					blankText: '请输入',
					msgTarget: 'qtip',
					width: 200,
					allowBlank: false,
					emptyText: '不能为空',
					blankText: '不能为空',
					triggerAction: 'all',
					editable: false,
					name: 'shopMaintain.shopaccesstoken'
				},{
					xtype:'button',
					text:'test'
				}]
			},{
				xtype: 'textfield',
				hidden: true,
				id: 'shopMaintain_shoprefreshtoken',
				name: 'shopMaintain.shoprefreshtoken'
			},{
				xtype: 'textfield',
				hidden: true,
				id: 'shopMaintain_shoprawjson',
				name: 'shopMaintain.shoprawjson'
			}],
			buttonAlign: 'center',
			buttons: [{
				text: '保存',
				iconCls: 'save',
				handler: saveShopFn
			},{
				text: '关闭',
				iconCls: 'cancel',
				handler: cancelSaveShopFn
			}]
		});
	}
});
saveShopFn = function(){
	var shopForm = Ext.getCmp('shopMaintainAddPanelId').getForm();
	if(!shopForm.isValid()){
		return;
	}
	var jsonShop = Ext.util.JSON.encode(shopForm.getFieldValues());
	Ext.Ajax.request({
        url: 'shopmaintain/addShop.do',
        method:'POST',
        //headers:{'Content-Type':'application/json; charset=utf-8'},
        success: saveShopSuccess,
		failure: saveShopFailure,
		params:{
			jsonShop:jsonShop
		},
		scope:this
	});
//	Ext.getCmp('onTimeTaskAddPanelId').getForm().submit({
//		url: 'ontimeTask/addOnTimeTask.do',
//		waitTitle: '提示',
//		//type:"json",
//		headers: { 'Content-Type':'application/json'},
//		waitMsg: '正在保存数据...',
//		method: 'post',
//		success: saveOnTimeTaskSuccess,
//		failure: saveOnTimeTaskFailure
//	})
};
saveShopSuccess = function(response, options){
	var data = Ext.util.JSON.decode(response.responseText);
	Ext.Msg.alert("信息提示", data.msg, function(button, text){
		//form.reset();
		Ext.getCmp('shopMaintainAddWinId').destroy();	
		Ext.getCmp('shopMaintainGridPanelId').getStore().load({
		params: {
			start: 0,
			limit: 20
		}});
	})
};
saveShopFailure = function(response, options){
	Ext.Msg.alert("提示","连接失败", function(button, text){});
};

cancelSaveShopFn = function(){
	Ext.getCmp("shopMaintainAddWinId").destroy();
};
checkShopIdFn = function(){
	var shopId = Ext.get('shopMaintain_shopid').dom.value;
	Ext.Ajax.request({
		url: 'shopmaintain/checkShopId.do',
		success: checkShopIdExistSuccessFn,
		failure: checkShopIdExistFailFn,
		params: {
			shopId:shopId
		}
	});
};
checkShopIdExistFailFn = function(response, options){
	Ext.Msg.alert("提示","暂时无法连接数据库", function(button, text){});
};
checkShopIdExistSuccessFn = function(response, options){
	if(response.responseText == true || response.responseText == "true"){
		Ext.getCmp('shopMaintain_shopid').markInvalid('此店铺ID已存在!');
	}
};

checkShopRedirectUrlFn = function() {
	var redirUrl = Ext.get("shopMaintain_shopredirecturl").dom.value;
	Ext.Ajax.request({
		url: 'shopmaintain/checkRedirUrl.do',
		failure: checkShopRedirectUrlFailFn,
		params: {
			redirUrl: redirUrl
		}
	});
	
};
checkShopRedirectUrlFailFn = function() {
	Ext.getCmp('shopMaintain_shopredirecturl').markInvalid('此回调URL不存在!');
};
