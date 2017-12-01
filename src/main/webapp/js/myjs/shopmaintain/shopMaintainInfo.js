/**
 * 店铺基础资料管理
 * @author mowj 20150715
 * 
 */
Ext.namespace("mercuryecinf.shopmaintain.info");

var ShopMaintainGridPanel = Ext.extend(Ext.grid.GridPanel,{
	id: 'shopMaintainGridPanelId',
	constructor: function(ctx){
		Ext.QuickTips.init();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var number = new Ext.grid.RowNumberer();
		var cm = new Ext.grid.ColumnModel([
			number, 
			sm,
		{
			header: '店铺编号',
			dataIndex: 'aomsshop001',
			align: 'center'
		},{
			header: '店铺名称',
			dataIndex: 'aomsshop002',
			align: 'center'
		},{
			header: '店铺平台编号',
			dataIndex: 'aomsshop003',
			align: 'center'
		},{
			header: '店铺平台类型',
			dataIndex: 'aomsshop011',
			align: 'center'
		},{
			header: 'AppKey',
			dataIndex: 'aomsshop004',
			align: 'center',
			hidden:true
		},{
			header: 'AppSecret',
			dataIndex: 'aomsshop005',
			align: 'center',
			hidden:true
		},{
			header: 'AccessToken',
			dataIndex: 'aomsshop006',
			align: 'center',
			hidden:true
		},{
			header: 'RefreshToken',
			dataIndex: 'aomsshop007',
			align: 'center',
			hidden:true
		},{
			header: '授权JSON',
			dataIndex: 'aomsshop008',
			align: 'center',
			hidden:true
		},{
			header: '创建时间',
			dataIndex: 'aomsshopcrtdt',
			align: 'center',
			hidden:true
		},{
			header: '修改时间',
			dataIndex: 'aomsshopmoddt',
			align: 'center',
			hidden:true
		},{
			header: '店铺在电商平台上的名称',
			dataIndex: 'aomsshop009',
			align: 'center'
		},{
			header: '回调URL',
			dataIndex: 'aomsshop010',
			align: 'center',
			hidden:true
		}
		]);
		
		var shopMaintainStore = new Ext.data.JsonStore({
			url:'shopmaintain/queryShopMaintain.do',
			root: 'root',
 			totalProperty: 'totalProperty',
 			fields: ['aomsshop001','aomsshop002','aomsshop003','aomsshop004','aomsshop005',
 			        'aomsshop006','aomsshop007','aomsshop008','aomsshopcrtdt','aomsshopmoddt',
 			        'aomsshop009','aomsshop010','aomsshop011']
		});
		
		ShopMaintainGridPanel.superclass.constructor.call(this, {
			/**表格高度自适应 document.body.clientHeight浏览器页面高度 start**/
			monitorResize: true, 
			doLayout: function() { 
				this.setWidth(document.body.clientWidth);
				this.setHeight(document.body.clientHeight-125);
				Ext.grid.GridPanel.prototype.doLayout.call(this); 
			} ,
			viewConfig: {
				forceFit: true,
				autoFill: true,
				columnsText : "显示/隐藏列",
                sortAscText : "正序排列",
                sortDescText : "倒序排列"
			},
			border: false,
			frame: true,
			cm: cm,
			sm: sm,
			store: shopMaintainStore,
			tbar: new Ext.Toolbar({
				items: [
				{
					text: '添加',
					iconCls: 'add',
					id: 'shopMaintain_add',
					//hidden: 'false',
					handler: shopMaintainAddFn
				},'-',
				{
					text: '修改',
					iconCls: 'update',
					id: 'shopMaintain_update',
					//hidden: 'false',
					handler: shopMaintainUpdateFn
				},'-',
				{
					text: '删除',
					iconCls: 'delete',
					id: 'shopMaintain_delete',
					//hidden: 'false',
					handler: shopMaintainDelFn
				},'-',
				{
					text: '详情',
					iconCls: 'detail',
					id: 'shopMaintain_detail',
					//hidden: 'false',
					handler: shopMaintainDetailFn
				}
				]
			}),
			bbar: new PagingToolbar(shopMaintainStore, 20)
		});
		shopMaintainStore.load({
			params: {
				start: 0,
				limit: 20
		}});
	}
});

/**
 * 分页栏类
 * @param {Object} store 表格存储store
 * @param {Object} pageSize 页面大小
 * @memberOf {TypeName} 
 */
PagingToolbar = Ext.extend(Ext.PagingToolbar, {
	constructor: function(store, pageSize){
		PagingToolbar.superclass.constructor.call(this, {
			store: store,
			pageSize: pageSize, //页面大小 
			displayInfo: true,
			displayMsg : '共<font color="red"> {2} </font>条记录,当前页记录索引<font color="red"> {0} - {1}</font>&nbsp;&nbsp;&nbsp;',
			emptyMsg: '没有数据',
			refreshText: '刷新',
			firstText: '第一页',
			prevText: '前一页',
			nextText: '下一页',
			lastText: '最后一页'
		});
	}
});

shopMaintainAddFn = function() {
	var shopMaintainAddWin = new ShopMaintainAddWin();
	shopMaintainAddWin.show(); 
};

shopMaintainUpdateFn = function(){
	var shopMaintainAddWin = new ShopMaintainAddWin();
	shopMaintainAddWin.title = '店铺基础资料修改';
	var selectionModel = Ext.getCmp('shopMaintainGridPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length != 1){
		Ext.Msg.alert('信息提示','请选择一条要修改的店铺信息!');
		return;
	}
	var shopId = record[0].get('aomsshop001');
	var updateForm = Ext.getCmp('shopMaintainAddPanelId').getForm();
	updateForm.load({
		url: 'shopmaintain/editShop.do',
		params: {
			shopId: shopId
		},
		success : function(form, action) {
			Ext.Msg.alert('编辑', '载入成功！');
		 },
		failure : function(form,action) {
			Ext.Msg.alert('编辑', '载入失败');
		}
	});
	shopMaintainAddWin.show();

};

shopMaintainDelFn = function(){
	var selectionModel = Ext.getCmp('shopMaintainGridPanelId').getSelectionModel();
	var record = selectionModel.getSelections();
	if(record.length != 1){
		Ext.Msg.alert('信息提示','请选择一条要删除的店铺信息!');
		return;
	}
	var shopMaintainIdV = record[0].get('id');
	if(shopMaintainIdV != null && shopMaintainIdV != ""){
		Ext.Msg.confirm("信息提示","是否确定删除这条店铺记录?",function(button, text){
			if(button == "yes"){
				Ext.Ajax.request({
					url: "shopMaintain/delete.do",
					success: function(response, options){
								var datas = Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert("信息提示", datas.msg, function(){
									Ext.getCmp('shopMaintainPanelId').getStore().reload();
								});
							},
					failure: function(response, options){
								Ext.Msg.alert("信息提示", '删除失败', function(){});
							},
					params: {
						id: shopMaintainIdV
					}
				});
			}
		});
	}else{
		Ext.Msg.alert("信息提示","请选择您要删除的记录！",function(){});
	}
};
shopMaintainDetailFn = function(){
	var shopMaintainDetailWin = new ShopMaintainDetailWin();
		var selectionModel = Ext.getCmp('shopMaintainGridPanelId').getSelectionModel();
		var record = selectionModel.getSelections();
		if(record.length != 1){
			Ext.Msg.alert('信息提示','请选择一条定时任务信息');
			return;
		}
		var shopMaintainIdV = record[0].get('id');
		Ext.getCmp('onTimeTaskDetailPanelId').getForm().load({
			url: 'ontimeTask/editOnTimeTask.do',
			params: {
				ontimeTaskId: shopMaintainIdV
			}
		})
	onTimeTaskDetailWin.show();
};


/**
 * 查询面板
 * @author sux 2011-02-21
 * @memberOf {TypeName} 
 */
var OnTimeTaskQueryPanel = Ext.extend(Ext.Panel,{
	id: 'onTimeTaskQueryId',
	constructor: function(){
		OnTimeTaskQueryPanel.superclass.constructor.call(this,{
			collapsible: true,
			titleCollapse: true, //单击整个collapse都有效
			//collapsed: true, //渲染后即闭合
			title: '定时作业管理',
			border: false,
			frame: true,
			autoWidth: true,
			defaultType: 'fieldset',
			items: [{
				title: '查询条件',
				layout: 'table',
				layoutConfig: {
					columns: 6
				},
				defaults: {
					labelWidth: 80,
					labelAlign: 'right'
				},
				items: [{
					layout: 'form',
					items: [{
						xtype: 'textfield',
						fieldLabel: '任务编码',
						width: 180,
						id: 'taskCode'
					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'textfield',
						fieldLabel: '任务名称',
						width: 180,
						id: 'taskName'
					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '有效状态',
						width: 120,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [["全部",""],["1-有效","1"],["2-无效","2"]]
						}),
						displayField: 'name',
						valueField: 'value',
						id: 'taskStatus'
					}]
				},{
					style: 'margin: 0px 10px 0px 20px;',
					xtype: 'button',
					text: '查询',
					width: 80,
					iconCls: 'search',
					handler: onTimeTaskQueryFn
				},{
					xtype: 'button',
					text: '取消',
					width: 80,
					iconCls: 'cancel',
					handler: onTimeTaskCancelFn
				}]
			}]
		})
	}
});
onTimeTaskQueryFn = function(){
	var taskCodeV = Ext.getCmp("taskCode").getValue();
	var taskNameV = Ext.getCmp("taskName").getValue();
	var taskStatusV = Ext.getCmp("taskStatus").getValue();
	Ext.getCmp("onTimeTaskGridPanelId").getStore().load({
		params: {
			type: "query",
			code: taskCodeV,
			name: taskNameV,
			status: taskStatusV,
			start: 0,
			limit: 20
		}
	});
};
onTimeTaskCancelFn = function(){
	Ext.getCmp("taskCode").setValue("");
	Ext.get("taskName").dom.value = "";
	Ext.get("taskStatus").dom.value = "";
};



