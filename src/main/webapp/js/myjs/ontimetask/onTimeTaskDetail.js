Ext.namespace("hrmsys.ontimetask.detail");
/**
 * 奖惩详情窗口
 * @author sux 2011-02-21
 * @memberOf {TypeName} 
 */
OnTimeTaskDetailWin = Ext.extend(Ext.Window,{
	id: 'onTimeTaskDetailWinId',
	constructor: function(){
		var onTimeTaskDetailPanel = new OnTimeTaskDetailPanel();
		OnTimeTaskDetailWin.superclass.constructor.call(this, {
			title: '定时任务信息详情',
			resizable: false,
			width: 750,
			modal: true,
			items: [onTimeTaskDetailPanel]
		})			
	}
});

OnTimeTaskDetailPanel = Ext.extend(Ext.form.FormPanel,{
	id: 'onTimeTaskDetailPanelId',
	constructor: function(){
		Ext.QuickTips.init();
		var reader = new Ext.data.JsonReader({},[{
			name: 'ontimeTask.id', mapping: 'id'
		},{
			name: 'ontimeTask.name', mapping: 'name'
		},{
			name: 'ontimeTask.code', mapping: 'code'
		},{
			name: 'ontimeTask.status', mapping: 'status'
		},{
			name: 'ontimeTask.exceName', mapping: 'exceName'
		},{
			name: 'ontimeTask.exceType', mapping: 'exceType'
		},{
			name: 'ontimeTask.cronVal', mapping: 'cronVal'
		},{
			name: 'ontimeTask.dataSource', mapping: 'dataSource'
		},{
			name: 'ontimeTask.runIp', mapping: 'runIp'
		},{
			name: 'ontimeTask.remark', mapping: 'remark'
		}]);
		OnTimeTaskDetailPanel.superclass.constructor.call(this,{
			padding: '10',
			width: 750,
			height: 300,
			frame: true,
			layout: 'table',
			reader: reader,
			layoutConfig: {
				columns: 2
			},
			defaults: {
				labelWidth: 80,
				labelAlign: 'right'
			},
			items: [{
				layout:'form',
				width: 300,
				items:[{
					xtype: 'hidden',
					id: 'ontimeTaskId',
					name: 'ontimeTask.id',
					value:0
				},{
					xtype: 'textfield',
					fieldLabel: '任务编码',
					width: 200,
					name: 'ontimeTask.code',
					id: 'ontimetask_code',
					disabled:true
				}]
			},{
				width: 300,
				layout:'form',
				items:[{
					xtype: 'textfield',
					fieldLabel: '任务名称',
					name: 'ontimeTask.name',
					disabled:true,
					width: 200
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'combo',
					fieldLabel: '有效状态',
					width: 200,
					triggerAction: 'all',
					mode: 'local',
					editable: false,
					hiddenName: 'ontimeTask.status',
					store: new Ext.data.SimpleStore({
						fields: ['name','value'],
						data: [["1-有效","1"],["2-无效","2"]]
					}),
					displayField: 'name',
					valueField: 'value',
					disabled:true
				}]
			},{
				width: 300,
				layout:'form',
				items:[{
					xtype: 'textfield',
					fieldLabel: '执行者名称',
					name: 'ontimeTask.exceName',
					disabled:true,
					width: 200
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'combo',
					fieldLabel: '执行者类型',
					width: 200,
					triggerAction: 'all',
					mode: 'local',
					editable: false,
					hiddenName: 'ontimeTask.exceType',
					store: new Ext.data.SimpleStore({
						fields: ['name','value'],
						data: [["1-@service","1"],/*["2-EJB","2"],["3-SQL","3"],["4-@ecfx_service","4"],*/["5-ReCycleCheckData","5"]]
					}),
					displayField: 'name',
					valueField: 'value',
					disabled:true
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'textfield',
					fieldLabel: '定时表达式 ',
					id: 'ontimetask_cronVal',
					name: 'ontimeTask.cronVal',
					disabled:true,
					width: 200
				}]
			},{
				width: 300,
				layout: 'form',
				items: [{
					xtype: 'combo',
					fieldLabel: '运行环境组',
					width: 200,
					triggerAction: 'all',
					mode: 'local',
					editable: false,
					hiddenName: 'ontimeTask.dataSource',
					store: new Ext.data.SimpleStore({
						fields: ['name','value'],
						data: [["1-业务系统","1"]]
					}),
					displayField: 'name',
					valueField: 'value',
					disabled:true
					
				}]
			},{
				width: 300,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '运行机器IP',
					width: 200,
					name: 'ontimeTask.runIp',
					disabled:true
				}]
			},{
				colspan: 2,
				layout: 'form',
				items: [{
					xtype: 'textarea',
					fieldLabel: '备注',
					name: 'ontimeTask.remark',
					width: 520,
					height: 100,
					disabled:true
				}]
			}]
		})
	}
});
