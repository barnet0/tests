/**
 * 店铺基础资料管理
 * @author mowj 20150715
 * 
 */
Ext.namespace("mercuryecinf.refund.info");

var RefundGridPanel = Ext.extend(Ext.grid.GridPanel,{
	id: 'refundGridPanelId',
	constructor: function(ctx){
		Ext.QuickTips.init();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var number = new Ext.grid.RowNumberer();
		var cm = new Ext.grid.ColumnModel([
			number, 
			sm,
			{ header:'退单号', dataIndex:'id', align:'center' },
			{ header:'申请退款的商品数字编号', dataIndex:'aoms023', align:'center' },
			{ header:'资料来源', dataIndex:'aoms044', align:'center' },
			{ header:'单据类型', dataIndex:'aoms002', align:'center' },
			{ header:'分销商nick', dataIndex:'aoms003', align:'center' },
			{ header:'支付给供应商的金额', dataIndex:'aoms004', align:'center' },
			{ header:'主采购单id', dataIndex:'aoms005', align:'center' },
			{ header:'退款的金额', dataIndex:'aoms006', align:'center' },
			{ header:'退款流程类型', dataIndex:'aoms007', align:'center' },
			{ header:'退款状态', dataIndex:'aoms008', align:'center' },
			{ header:'是否退货', dataIndex:'aoms009', align:'center' },
			{ header:'子单id', dataIndex:'aoms010', align:'center' },
			{ header:'供应商昵称', dataIndex:'aoms011', align:'center' },
			{ header:'退款修改时间', dataIndex:'modified', align:'center' },
			{ header:'卖家收货地址', dataIndex:'aoms013', align:'center' },
			{ header:'支付宝交易号', dataIndex:'aoms015', align:'center' },
			{ header:'退款扩展属性', dataIndex:'aoms016', align:'center' },
			{ header:'买家昵称', dataIndex:'aoms017', align:'center' },
			{ header:'物流公司名称', dataIndex:'aoms018', align:'center' },
			{ header:'退货时间', dataIndex:'aoms020', align:'center' },
			{ header:'货物状态', dataIndex:'aoms021', align:'center' },
			{ header:'商品购买数量', dataIndex:'aoms022', align:'center' },
			{ header:'子订单号', dataIndex:'aoms024', align:'center' },
			{ header:'退款约束', dataIndex:'aoms025', align:'center' },
			{ header:'退款对应的订单交易状态', dataIndex:'aoms026', align:'center' },
			{ header:'商品外部商家编码', dataIndex:'aoms027', align:'center' },
			{ header:'支付给卖家的金额', dataIndex:'aoms028', align:'center' },
			{ header:'商品价格', dataIndex:'aoms029', align:'center' },
			{ header:'退还金额', dataIndex:'aoms030', align:'center' },
			{ header:'退款阶段', dataIndex:'aoms031', align:'center' },
			{ header:'卖家昵称', dataIndex:'aoms033', align:'center' },
			{ header:'物流方式', dataIndex:'aoms034', align:'center' },
			{ header:'退货运单号', dataIndex:'aoms035', align:'center' },
			{ header:'商品SKU信息', dataIndex:'aoms036', align:'center' },
			{ header:'退款状态', dataIndex:'aoms037', align:'center' },
			{ header:'商品标题', dataIndex:'aoms039', align:'center' },
			{ header:'交易总金额', dataIndex:'aoms040', align:'center' },
			{ header:'退款创建时间', dataIndex:'aoms041', align:'center' },
			{ header:'退款说明', dataIndex:'aoms042', align:'center' },
			{ header:'退款原因', dataIndex:'aoms043', align:'center' },
			{ header:'店铺ID', dataIndex:'storeId', align:'center '},
			{ header:'店铺类型', dataIndex:'storeType', align:'center' }
		]);
		
		var refundStore = new Ext.data.JsonStore({
			url:'bizdata/queryRefundAndReturn.do',
			root: 'root',
 			totalProperty: 'totalProperty',
 			fields: ['id','aoms002','aoms003','aoms004','aoms005','aoms006',
 			         'aoms007','aoms008','aoms009','aoms010','aoms011','modified',
 			         'aoms013','aoms014','aoms015','aoms016','aoms017','aoms018',
 			         'aoms019','aoms020','aoms021','aoms022','aoms023','aoms024',
 			         'aoms025','aoms026','aoms027','aoms028','aoms029','aoms030',
 			         'aoms031','aoms033','aoms034','aoms035','aoms036','aoms037',
 			         'aoms039','aoms040','aoms041','aoms042','aoms043','aoms044',
 			         'storeType','storeId'],
 		 	idProperty:'id'+'aoms023'+'aoms044'
		});
		
		RefundGridPanel.superclass.constructor.call(this, {
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
			store: refundStore,
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
			bbar: new PagingToolbar(refundStore, 50)
		});
		refundStore.load({
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
var RefundQueryPanel = Ext.extend(Ext.Panel,{
	id: 'refundQueryPanelId',
	constructor: function(){
		RefundQueryPanel.superclass.constructor.call(this,{
			collapsible: true,
			titleCollapse: true, //单击整个collapse都有效
			//collapsed: true, //渲染后即闭合
			title: '退单信息查看',
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
						fieldLabel: '退单号',
						width: 180,
						id: 'refundId'
					}]
				},{
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
					handler: refundQueryFn
				},{
					xtype: 'button',
					text: '取消',
					width: 80,
					iconCls: 'cancel',
					handler: refundQueryCancalFn
				}]
			}]
		})
	}
});
refundQueryFn = function(){
	var refundId = Ext.getCmp("refundId").getValue();
	var storeType = Ext.getCmp("storeType").getValue();
	var storeId = Ext.getCmp("storeId").getValue();
	Ext.getCmp("refundGridPanelId").getStore().load({
		params: {
			type: "query",
			refundId: refundId,
			storeType: storeType,
			storeId: storeId,
			start: 0,
			limit: 50
		}
	});
};
refundQueryCancalFn = function(){
	Ext.getCmp("taskCode").setValue("");
	Ext.get("taskName").dom.value = "";
	Ext.get("taskStatus").dom.value = "";
};



