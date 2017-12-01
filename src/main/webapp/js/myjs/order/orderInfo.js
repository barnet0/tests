/**
 * 店铺基础资料管理
 * @author mowj 20150715
 * 
 */
Ext.namespace("mercuryecinf.order.info");

var OrderGridPanel = Ext.extend(Ext.grid.GridPanel,{
	id: 'orderGridPanelId',
	constructor: function(ctx){
		Ext.QuickTips.init();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var number = new Ext.grid.RowNumberer();
		var cm = new Ext.grid.ColumnModel([
			number, 
			sm,
			{header:' 交易号',dataIndex: 'id', align: 'center'},
			{header:' 店铺类型',dataIndex: 'aoms057', align: 'center'},
			{header:' 规格id',dataIndex: 'aoms060', align: 'center'},
			{header:'交易类型', dataIndex: 'aoms002', align: 'center'},
			{header:'交易状态', dataIndex: 'aoms003', align: 'center'},
			{header:'交易来源', dataIndex: 'aoms004', align: 'center'},
			{header:'交易商品价格', dataIndex: 'aoms005', align: 'center'},
			{header:'交易创建时间', dataIndex: 'aoms006', align: 'center'},
			{header:'交易修改时间', dataIndex: 'modified', align: 'center'},
			{header:'交易结束时间', dataIndex: 'aoms008', align: 'center'},
			{header:'交易留言', dataIndex: 'aoms009', align: 'center'},
			{header:'买家留言', dataIndex: 'aoms010', align: 'center'},
			{header:'买家备注', dataIndex: 'aoms011', align: 'center'},
			{header:'卖家备注', dataIndex: 'aoms012', align: 'center'},
			{header:'卖家昵称', dataIndex: 'aoms016', align: 'center'},
			{header:'商品数量', dataIndex: 'aoms017', align: 'center'},
			{header:'促销单价', dataIndex: 'aoms019', align: 'center'},
			{header:'折扣金额', dataIndex: 'aoms020', align: 'center'},
			{header:'支付宝交易号', dataIndex: 'aoms021', align: 'center'},
			{header:'支付金额', dataIndex: 'aoms022', align: 'center'},
			{header:'支付方式', dataIndex: 'aoms023', align: 'center'},
			{header:'支付时间', dataIndex: 'aoms024', align: 'center'},
			{header:'买家昵称', dataIndex: 'aoms025', align: 'center'},
			{header:'买家支付宝帐号', dataIndex: 'aoms028', align: 'center'},
			{header:'发货类型', dataIndex: 'aoms033', align: 'center'},
			{header:'物流名称', dataIndex: 'aoms034', align: 'center'},
			{header:'物流费用', dataIndex: 'aoms035', align: 'center'},
			{header:'收货人姓名', dataIndex: 'aoms036', align: 'center'},
			{header:'收获人省份', dataIndex: 'aoms037', align: 'center'},
			{header:'收货人市', dataIndex: 'aoms038', align: 'center'},
			{header:'收货人区', dataIndex: 'aoms039', align: 'center'},
			{header:'收货地址', dataIndex: 'aoms040', align: 'center'},
			{header:'收货地址邮编', dataIndex: 'aoms041', align: 'center'},
			{header:'收货人手机', dataIndex: 'aoms042', align: 'center'},
			{header:'收货人电话', dataIndex: 'aoms043', align: 'center'},
			{header:'卖家发货时间', dataIndex: 'aoms044', align: 'center'},
			{header:'手续费', dataIndex: 'aoms045', align: 'center'},
			{header:'卖家支付宝帐号', dataIndex: 'aoms046', align: 'center'},
			{header:'卖家手机号', dataIndex: 'aoms047', align: 'center'},
			{header:'卖家电话', dataIndex: 'aoms048', align: 'center'},
			{header:'卖家姓名', dataIndex: 'aoms049', align: 'center'},
			{header:'发票抬头', dataIndex: 'aoms053', align: 'center'},
			{header:'发票内容', dataIndex: 'aoms054', align: 'center'},
			{header:'发票类型', dataIndex: 'aoms055', align: 'center'},
			{header:'店铺id', dataIndex: 'storeId', align: 'center'},
			{header:'店铺类型', dataIndex: 'storeType' ,align: 'center'}
		]);
		
		var orderStore = new Ext.data.JsonStore({
			url:'bizdata/queryOrder.do',
			root: 'root',
 			totalProperty: 'totalProperty',
 			fields: ['id','aoms002','aoms003','aoms004','aoms005',
 			         'aoms006','modified','aoms008','aoms009','aoms010',
 			         'aoms011','aoms012','aoms013','aoms014','aoms015',
 			         'aoms016','aoms017','aoms018','aoms019','aoms020',
 			         'aoms021','aoms022','aoms023','aoms024','aoms025',
 			         'aoms026','aoms027','aoms028','aoms029','aoms030',
 			         'aoms031','aoms032','aoms033','aoms034','aoms035',
 			         'aoms036','aoms037','aoms038','aoms039','aoms040',
 			         'aoms041','aoms042','aoms043','aoms044','aoms045',
 			         'aoms046','aoms047','aoms048','aoms049','aoms050',
 			         'aoms051','aoms052','aoms053','aoms054','aoms055',
 			         'aoms057','aoms060','storeId','storeType'],
 		 	idProperty:'id'+'aoms057'+'aoms060'
		});
		
		OrderGridPanel.superclass.constructor.call(this, {
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
			store: orderStore,
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
			bbar: new PagingToolbar(orderStore, 50)
		});
		orderStore.load({
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
var OrderQueryPanel = Ext.extend(Ext.Panel,{
	id: 'orderQueryPanelId',
	constructor: function(){
		OrderQueryPanel.superclass.constructor.call(this,{
			collapsible: true,
			titleCollapse: true, //单击整个collapse都有效
			//collapsed: true, //渲染后即闭合
			title: '交易信息查看',
			border: false,
			frame: true,
			autoWidth: true,
			defaultType: 'fieldset',
			items: [{
				title: '查询条件',
				layout: 'table',
				layoutConfig: {
					columns: 4
				},
				defaults: {
					labelWidth: 100,
					labelAlign: 'right'
				},
				items: [
				{
					layout: 'form',
					items: [{
						xtype: 'textfield',
						fieldLabel: '交易号',
						width: 150,
						id: 'orderId'
					}]
				},
				{
					layout: 'form',
					items: [{
						xtype: 'datefield',
						fieldLabel: '订单创建日期',
						dateFormat : 'time' ,
						format:'Y-m-d',
						width: 100,
						id: 'created'
					}]
				},
				{
					layout: 'form',
					items: [{
						fieldLabel: '订单创建时间',
						items: [{
							xtype: 'timefield',
							format:'H:i:s',
							width: 100,
							increment: 30,
							emptyText:'开始时间',
							id: 'sTime'
						},{
							xtype: 'timefield',
							format:'H:i:s',
							width: 100,
							increment: 30,
							emptyText:'结束时间',
							id: 'eTime'
						}]
					}]
				},
				{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '平台',
						width: 120,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [
							       ["淘宝&天猫","0"],
							       ["淘宝分销","A"],
							       ["京东","1"],
							       ["一号店","2"],
							       ["工商银行","3"],
							       ["苏宁","4"],
							       ["当当","5"]
							      ]
						}),
						displayField: 'name',
						valueField: 'value',
						id: 'storeType',
						listeners:{     
							'change':function(){
								var storeType=Ext.getCmp("storeType").getValue();
								Ext.Ajax.request({
							        url: 'manualschedule/manualScheduleCombo.do',
							        timeout: 30000,//default 30000 milliseconds 
							        method:'POST',
							        success: function(response, options){
							        	var jsonResp = Ext.util.JSON.decode(response.responseText);
							        	var convertData = [];
							        	for (var i = 0; i < jsonResp.length; i++) {
							        		convertData.push( {'aomsshop001':jsonResp[i].aomsshop001,'aomsshop001-002': jsonResp[i].aomsshop001+' ('+jsonResp[i].aomsshop002+' )'} );
							        	}
							        	
							        	shop_store = new Ext.data.JsonStore({
							        		fields: ['aomsshop001','aomsshop001-002'],
							        		data : convertData
							        	});
							        	if(Ext.getCmp("storeId").getValue()!=''){
							        		Ext.getCmp("storeId").setValue('');
							        	}
							        	
							        	Ext.getCmp("storeId").bindStore(shop_store);
							        },
									params:{
										storeType:storeType,
									},
									scope:this
								});
							}
						}
					}]
				},{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '店铺编号',
						width: 150,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						displayField: 'aomsshop001-002',
						valueField: 'aomsshop001',
						id: 'storeId'
					}]
				},{
					style: 'margin: 0px 10px 0px 20px;',
					xtype: 'button',
					text: '查询',
					width: 80,
					iconCls: 'search',
					handler: orderQueryFn
				},{
					xtype: 'button',
					text: '取消',
					width: 80,
					iconCls: 'cancel',
					handler: orderQueryCancelFn
				}]
			}]
		})
	}
});
orderQueryFn = function(){
	var orderId = Ext.getCmp("orderId").getValue();
	var created = Ext.getCmp("created").getValue();
	if (created != undefined && created != '') {
		created = created.format('Y-m-d H:i:s');
	}
	var storeType = Ext.getCmp("storeType").getValue();
	var storeId = Ext.getCmp("storeId").getValue();
	var sTime = Ext.getCmp("sTime").getValue();
	var eTime = Ext.getCmp("eTime").getValue();
	Ext.getCmp("orderGridPanelId").getStore().load({
		params: {
			type: "query",
			orderId: orderId,
			created: created,
			sTime: sTime,
			eTime: eTime,
			storeType: storeType,
			storeId: storeId,
			start: 0,
			limit: 50
		}
	});
};
orderQueryCancelFn = function(){
	Ext.getCmp("orderId").setValue("");
	Ext.getCmp("created").setValue("");
	Ext.getCmp("storeType").setValue("");
	Ext.getCmp("storeId").setValue("");
};


