/**
 * 店铺基础资料管理
 * @author mowj 20150715
 * 
 */
Ext.namespace("mercuryecinf.item.info");

var ItemGridPanel = Ext.extend(Ext.grid.GridPanel,{
	id: 'itemGridPanel',
	constructor: function(ctx){
		Ext.QuickTips.init();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var number = new Ext.grid.RowNumberer();
		var cm = new Ext.grid.ColumnModel([
			number, 
			sm,
			{header:'商品在电商平台的编码' ,dataIndex:'id', align:'center'},
			{header:'商品在电商平台的规格编码' ,dataIndex:'aoms004', align:'center'},
			{header:'商品所在店铺类型' ,dataIndex:'aoms011', align:'center'},
			{header:'商品的外部编码' ,dataIndex:'aoms002', align:'center'},
			{header:'商品在电商平台的名称' ,dataIndex:'aoms003', align:'center'},
			{header:'商品的外部规格编码' ,dataIndex:'aoms005', align:'center'},
			{header:'商品在电商平台的规格名称' ,dataIndex:'aoms006', align:'center'},
			{header:'商品电商平台状态' ,dataIndex:'aoms007', align:'center'},
			{header:'商品在电商平台SKU的颜色' ,dataIndex:'aoms008', align:'center'},
			{header:'商品在电商平台SKU的尺寸' ,dataIndex:'aoms009', align:'center'},
			{header:'商品在平台的更新时间' ,dataIndex:'modified', align:'center'},
			{header:'商品url' ,dataIndex:'aoms013', align:'center'},
			{header:'图片url' ,dataIndex:'aoms014', align:'center'},
			{header:'商品发布时间' ,dataIndex:'aoms015', align:'center'},
			{header:'商品上架时间' ,dataIndex:'aoms016', align:'center'},
			{header:'商品下架时间' ,dataIndex:'aoms017', align:'center'},
			{header:'库存数量' ,dataIndex:'aoms018', align:'center'},
			{header:'单身sku修改时间' ,dataIndex:'aoms019', align:'center'},
			{header:'店铺ID' ,dataIndex:'storeId', align:'center'},
			{header:'店铺类型' ,dataIndex:'storeType', align:'center'}

		]);
		var itemStore = new Ext.data.JsonStore({
			url:'bizdata/queryItem.do',
			root: 'root',
 			totalProperty: 'totalProperty',
 			fields: ['id','aoms002','aoms003','aoms004','aoms005','aoms006','aoms007',
 			         'aoms008','aoms009','storeId','storeType','modified','aoms011',
 			         'aoms013','aoms014','aoms015','aoms016','aoms017','aoms018','aoms019'],
 			idProperty:'id'+'aoms004'+'aoms011'
		});
		
		ItemGridPanel.superclass.constructor.call(this, {
			/**表格高度自适应 document.body.clientHeight浏览器页面高度 start**/
			monitorResize: true, 
			doLayout: function() { 
				this.setWidth(document.body.clientWidth);
				this.setHeight(document.body.clientHeight-125);
				Ext.grid.GridPanel.prototype.doLayout.call(this); 
			} ,
			viewConfig: {
				forceFit: false,
//				autoFill: true,
				scrollOffset: 0,
				columnsText : "显示/隐藏列",
                sortAscText : "正序排列",
                sortDescText : "倒序排列"
			},
			border: false,
			frame: true,
			cm: cm,
			sm: sm,
			store: itemStore,
//			tbar: new Ext.Toolbar({
//				items: [
//				{
//					text: '添加',
//					iconCls: 'add',
//					id: 'shopMaintain_add',
//					//hidden: 'false',
//					handler: shopMaintainAddFn
//				},'-',
//				{
//					text: '修改',
//					iconCls: 'update',
//					id: 'shopMaintain_update',
//					//hidden: 'false',
//					handler: shopMaintainUpdateFn
//				},'-',
//				{
//					text: '删除',
//					iconCls: 'delete',
//					id: 'shopMaintain_delete',
//					//hidden: 'false',
//					handler: shopMaintainDelFn
//				},'-',
//				{
//					text: '详情',
//					iconCls: 'detail',
//					id: 'shopMaintain_detail',
//					//hidden: 'false',
//					handler: shopMaintainDetailFn
//				}
//				]
//			}),
			bbar: new PagingToolbar(itemStore, 50)
		});
		itemStore.load({
			params: {
				start: 0,
				limit: 50
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

/**
 * 查询面板
 * @author sux 2011-02-21
 * @memberOf {TypeName} 
 */
var ItemQueryPanel = Ext.extend(Ext.Panel,{
	id: 'itemQueryPanelId',
	constructor: function(){
		ItemQueryPanel.superclass.constructor.call(this,{
			collapsible: true,
			titleCollapse: true, //单击整个collapse都有效
			//collapsed: true, //渲染后即闭合
			title: '铺货信息查看',
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



