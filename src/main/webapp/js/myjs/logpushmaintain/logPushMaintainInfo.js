//Grid 畫面
var LogPushMaintainGridPanel = Ext.extend(Ext.grid.GridPanel,{
	id: 'logPushMaintainGridPanelId',
	constructor: function(ctx){
		Ext.QuickTips.init();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var number = new Ext.grid.RowNumberer();
		var cm = new Ext.grid.ColumnModel([
			number, sm,
		{
			header: 'PK',
			dataIndex: 'id',
			align: 'center',
			hidden:true
		},{
			header: '电商',
			dataIndex: 'scheduleType',
			width: 220,
			align: 'left',
			hidden:false
		},{
			header: '最后单据更新时间',
			dataIndex: 'lastUpdateTime',
			width: 170,
			align: 'left'
		},{
			header: '最后任务执行时间',
			dataIndex: 'lastRunTime',
			width: 170,
			align: 'left'
		},{
			header: '最大推送笔数',
			dataIndex: 'maxPushRow',
			align: 'center'
		},{
			header: '最大储存笔数',
			dataIndex: 'maxReadRow',
			align: 'center'
		},{
			header: '每页最大笔数',
			dataIndex: 'maxPageSize',
			align: 'center'
		},{
			header: 'plant',
			dataIndex: 'plant',
			align: 'center',
			hidden:true
		},{
			header: 'service',
			dataIndex: 'service',
			align: 'center',
			hidden:true
		},{
			header: 'user',
			dataIndex: 'user',
			align: 'center',
			hidden:true
		},{
			header: 'postUrl',
			dataIndex: 'postUrl',
			align: 'center',
			hidden:true
		},{
			header: 'postIp',
			dataIndex: 'postIp',
			align: 'center',
			hidden:true
		},{
			header: 'maxRunnable',
			dataIndex: 'maxRunnable',
			align: 'center',
			hidden:true
		}]);
		
		var logMaintainStore = new Ext.data.JsonStore({
			url:'logPushMaintain/queryPushLogMaintain.do',
			root: 'root',
 			totalProperty: 'totalProperty',
			fields: [
			     	'id',
			    	'scheduleType',
			    	'lastUpdateTime',
//			    	{  
//			    	  name : 'lastUpdateTime',  
//			    	  type : 'date',  
//			    	  mapping : 'lastUpdateTime.time',  
//			    	  dateFormat : 'time'  
//			    	},
			    	'lastRunTime',
			    	'maxPushRow',
			    	'maxReadRow',
			    	'maxPageSize',
			    	'plant',
			    	'service',
			    	'user',
			    	'postUrl',
			    	'postIp',
			    	'maxRunnable'
			        ]
		});
		logMaintainStore.on('beforeload',function(){
			logMaintainStore.baseParams = {
//				reqTime:Ext.getCmp("lastUpdateTime").getValue(),
				storeType:Ext.getCmp("storeType").getValue()
			};
		});  
	
		LogPushMaintainGridPanel.superclass.constructor.call(this, {
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
			store: logMaintainStore,
			/*tbar: new Ext.Toolbar({
			items:[{
				text: '查看日志详情',
				iconCls: 'detail',
				id: 'show_log_detail',			
				handler: showPushLogDetailFn
				},'-',{
					text: '成功处理日志-状态批量修改',
					iconCls: 'update',
					id: 'update_status',	
					handler: batchPushUpdateStatusFn
					}]
			}),*/
			
			bbar: new PagingToolbar(logMaintainStore,20)
		});
		
		logMaintainStore.load({
			params: {
				start: 0,
				limit: 20
			}});
	}
});


/**
 * 查询面板
 * @author xavier 2015-08-10
 * @memberOf {TypeName} 
 */
var LogPushMaintainQueryPanel = Ext.extend(Ext.Panel,{
	id: 'logPushMaintainQueryId',
	constructor: function(){
		LogPushMaintainQueryPanel.superclass.constructor.call(this,{
			collapsible: true,
			titleCollapse: true, //单击整个collapse都有效
			//collapsed: true, //渲染后即闭合
			title: '定时任务执行情况查询',
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
					labelWidth: 150,
					labelAlign: 'right'
				},
				items: [{
					layout: 'form',
					items: [{
						xtype: 'combo',
						fieldLabel: '电商查詢 ',
						width:200,
						triggerAction: 'all',
						mode: 'local',
						editable: false,
						store: new Ext.data.SimpleStore({
							fields: ['name','value'],
							data: [
                               ["全部",""],
                               ["当当","Dangdang"],
                               ["京东","Jingdong"],
                               ["淘宝","Taobao"],
                               ["一号店","Yhd"],
                               ["苏宁","Suning"],
                               ["工商银行","Icbc"]
							]
						}),
						value: '',//預設選到的value
						selectOnFocus:true,//設為onFocus==true
						displayField: 'name',
						valueField: 'value',
						id: 'storeType'
					}]
				},{
					style: 'margin: 0px 10px 0px 20px;',
					xtype: 'button',
					text: '查询',
					width: 80,
					iconCls: 'search',
					handler: logMaintainQueryFn
				},{
					xtype: 'button',
					text: '取消',
					width: 80,
					iconCls: 'cancel',
					handler: logMaintainCancelFn
				}]
			}]
		})
	}
});

logMaintainQueryFn = function(){
//	var reqTimeV = Ext.getCmp("reqTime").getValue();
	var storeTypeV = Ext.getCmp("storeType").getValue();
//	var isSuccessV = Ext.getCmp("isSuccess").getValue();
	Ext.getCmp("logPushMaintainGridPanelId").getStore().load({
		params: {
			type: "query",
//			reqTime: reqTimeV,
			storeType: storeTypeV,
//			isSuccess: isSuccessV,
			start: 0,
			limit: 20
		}
	});
};

logMaintainCancelFn = function(){
//	var storeTypeV = Ext.getCmp("storeType").getValue();
	Ext.get("storeType").dom.value = "";
};

PagingToolbar = Ext.extend(Ext.PagingToolbar, {
	constructor: function(store, pageSize){
		PagingToolbar.superclass.constructor.call(this, {
			store: store,
			pageSize: 100, //pageSize, //页面大小 
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

showPushLogDetailFn = function(){}	
batchPushUpdateStatusFn= function(){}

//showPushLogDetailFn = function(){	
//	var selectionModel = Ext.getCmp('logPushMaintainGridPanelId').getSelectionModel();
//	var record = selectionModel.getSelections();
//	if(record.length != 1){
//		Ext.Msg.alert('信息提示','请选择一条 Log 信息');
//		return;
//	}
//	var logMaintainV = record[0].get('id');
//	var showLogDetailWin = new LogPushDetailWin(logMaintainV);
//
//	showLogDetailWin.show();
//}

//batchPushUpdateStatusFn= function(){	
//	var selectionModel = Ext.getCmp('logPushMaintainGridPanelId').getSelectionModel();
//	var record = selectionModel.getSelections();
//	if(record.length == 0){
//		Ext.Msg.alert('信息提示','请选择一条 Log 信息');
//		return;
//	}
//	
//	var logIds='';
//	for(var count=0;count<record.length;count++){
//		if(record[count].get('storeType')!='ECI-PUSH' && record[count].get('storeType')!='1'){
//			logIds=logIds+record[count].get('id')+',';
//		}
//	}
//	
//	if(logIds != null && logIds != ""){
//		Ext.Msg.confirm("信息提示","是否确定認更新非ECI推送之資料?",function(button, text){
//			if(button == "yes"){
//				Ext.Ajax.request({
//					url: "logPushMaintain/batchUpdateStatus.do",
//					success: function(response, options){
//								Ext.Msg.alert("信息提示", response.responseText, function(){
//									Ext.getCmp('logPushMaintainGridPanelId').getStore().reload();
//								});
//							},
//					failure: function(response, options){
//								Ext.Msg.alert("信息提示", '数据库连接失败', function(){});
//							},
//					params: {
//						logIds: logIds
//					}
//				});
//			}
//		});
//	}else{
//		Ext.Msg.alert("信息提示","请选择非ECI推送的失敗记录！",function(){});
//	}
//}



