Ext.namespace("hrmsys.ontimetask.add");
/**
 * 新增定时任务窗口
 * @author lizhi 20150705
 * @memberOf {TypeName} 
 */
OnTimeTaskAddWin = Ext.extend(Ext.Window,{
	id: 'onTimeTaskAddWinId',
	constructor: function(){
		var onTimeTaskPanel = new OnTimeTaskAddPanel();
		OnTimeTaskAddWin.superclass.constructor.call(this, {
			title: '新增定时任务',
			resizable: false,
			width: 750,
			modal: true,
			items: [onTimeTaskPanel]
		})			
	}
});

OnTimeTaskAddPanel = Ext.extend(Ext.form.FormPanel,{
	id: 'onTimeTaskAddPanelId',
	constructor: function(){
		Ext.QuickTips.init();
		var reader = new Ext.data.JsonReader({},[{
			name: 'ontimeTask.id', mapping: 'id'
		},{
			name: 'ontimeTask.name', mapping: 'name'
		},{
			name: 'ontimeTask.code', mapping: 'code'
		},{
			name: 'ontimetaskOldCode', mapping: 'code'
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
		OnTimeTaskAddPanel.superclass.constructor.call(this,{
			padding: '10',
			width: 750,
			height: 450,
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
					xtype: 'hidden',
					id: 'ontimetaskOldCode',
					name: 'ontimetaskOldCode',
					value:0
				},{
					xtype: 'textfield',
					fieldLabel: '任务编码',
					width: 200,
					id: 'ontimetask_code',
					allowBlank: false,
					msgTarget: 'qtip',
					emptyText: '不能为空',
					blankText: '请填写任务编码',
					name: 'ontimeTask.code',
					listeners: {'blur': checkontimeTaskCodeFn}
				}]
			},{
				width: 300,
				layout:'form',
				items:[{
					xtype: 'textfield',
					fieldLabel: '任务名称',
					allowBlank: false,
					msgTarget: 'qtip',
					blankText: '不能为空',
					emptyText: '不能为空',
					name: 'ontimeTask.name',
					width: 200
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'combo',
					fieldLabel: '有效状态',
					blankText: '请选择',
					msgTarget: 'qtip',
					width: 200,
					allowBlank: false,
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
					value:1
				}]
			},{
				width: 300,
				layout:'form',
				items:[{
					xtype: 'textfield',
					fieldLabel: '执行者名称',
					allowBlank: false,
					msgTarget: 'qtip',
					blankText: '不能为空',
					emptyText: '不能为空',
					name: 'ontimeTask.exceName',
					width: 200
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'combo',
					fieldLabel: '执行者类型',
					blankText: '请选择',
					msgTarget: 'qtip',
					width: 200,
					allowBlank: false,
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
					value:1
				}]
			},{
				layout: 'form',
				width: 300,
				items: [{
					xtype: 'textfield',
					fieldLabel: '定时表达式 ',
					id: 'ontimetask_cronVal',
					name: 'ontimeTask.cronVal',
					allowBlank: false,
					emptyText: '不能为空',
					msgTarget: 'qtip',
					blankText: '不能为空',
					listeners: {'blur': checkontimeTaskCronValFn},
					width: 200
				}]
			},{
				width: 300,
				layout: 'form',
				items: [{
					xtype: 'combo',
					fieldLabel: '运行环境组',
					blankText: '请选择',
					msgTarget: 'qtip',
					width: 200,
					allowBlank: false,
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
					value:1
					
				}]
			},{
				width: 300,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '运行机器IP',
					width: 200,
					id:'ontimeTaskRunIp',
					name: 'ontimeTask.runIp',
					allowBlank: false,
					msgTarget: 'qtip',
					emptyText: '不能为空',
					blankText: '不能为空',
					regex:/^([\da-fA-F]{1,4}:){7}[\da-fA-F]{1,4}\S*|^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/,
					regexText: 'IP地址格式不正确'
					
				}]
			},{
				colspan: 2,
				layout: 'form',
				items: [{
					xtype: 'textarea',
					fieldLabel: '备注',
					name: 'ontimeTask.remark',
					width: 510,
					height: 100
				}]
			},{
				colspan: 2,
				defaultType: 'fieldset',
				items: [{
					layout: 'column',
					title: '填写说明',
					width: 600,
					height: 150,
					items: [{
						xtype:"label",
						width: 500,
						html:"<font color='#FF0000' style='font-family:宋体;font-size:14px'>一，必须是事先提供好的程序发布的服务名称@service(\"xxxx\")</font></br>"
					},{
						xtype:"label",
						width: 500,
						html:"<font color='#FF0000' style='font-family:宋体;font-size:14px'>二，<a href='ontimeTask/showCronHelp.do' target='_blank'>定时表达式帮助文档</a></font></br>"
					},{
						xtype:"label",
						width: 500,
						id:"thirdLabel4IP",
						html:"<font color='#FF0000' style='font-family:宋体;font-size:14px'>三，运行机器IP,自动识别为本地IP地址</font></br>"
					},{
						xtype:"label",
						width: 500,
						html:"<font color='#FF0000' style='font-family:宋体;font-size:14px'>四，执行者类型，目前只有@service,自己的service必须要实现OnTimeTaskBusiJob接口</font></br>"
					},{
						xtype:"label",
						width: 500,
						html:"<font color='#FF0000' style='font-family:宋体;font-size:14px'>五，新增任务后，要重启调度器</font>"
					}]
				}]
			}],
			buttonAlign: 'center',
			buttons: [{
				text: '保存',
				iconCls: 'save',
				handler: saveOnTimeTaskFn
			},{
				text: '关闭',
				iconCls: 'cancel',
				handler: cancelOnTimeTaskFn
			}]
		})
	}
});
saveOnTimeTaskFn = function(){
	var taskForm = Ext.getCmp('onTimeTaskAddPanelId').getForm();
	if(!taskForm.isValid()){
		return;
	}
	var cronVal = Ext.get('ontimetask_cronVal').dom.value;
	Ext.Ajax.request({
		url: 'ontimeTask/checkCronVal.do',
		success: function(response, options){
			//alert(eval("("+taskForm.getValues()+")"));
			if(response.responseText == false || response.responseText == "false"){
				Ext.getCmp('ontimetask_cronVal').markInvalid('此定时表达式无效，请查看填写帮助');
			}else{
				var jsonTask = Ext.util.JSON.encode(taskForm.getValues());
				//alert(json);
				Ext.Ajax.request({
			        url: 'ontimeTask/addOnTimeTask.do',
			        method:'POST',
			        //headers:{'Content-Type':'application/json; charset=utf-8'},
			        success: saveOnTimeTaskSuccess,
					failure: saveOnTimeTaskFailure,
					params:{jsonTask:jsonTask},
					scope:this
				});
			}
		},
		failure: function(response, options){
			Ext.Msg.alert("信息提示","数据库连接失败", function(button, text){});
		},
		params: {
			cronVal: cronVal
		}
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
saveOnTimeTaskSuccess = function(response, options){
	var data = Ext.util.JSON.decode(response.responseText);
	Ext.Msg.alert("信息提示", data.msg, function(button, text){
		//form.reset();
		Ext.getCmp('onTimeTaskAddWinId').destroy();	
		Ext.getCmp('onTimeTaskGridPanelId').getStore().load({
		params: {
			start: 0,
			limit: 20
		}});
	})
};
saveOnTimeTaskFailure = function(response, options){
	Ext.Msg.alert("提示","连接失败", function(button, text){});
};
cancelOnTimeTaskFn = function(){
	Ext.getCmp("onTimeTaskAddWinId").destroy();
};
checkontimeTaskCodeFn = function(){
	var ontimeTaskId = Ext.get('ontimeTaskId').dom.value;
	var taskCode = Ext.get('ontimetask_code').dom.value;
	var taskOldCode = Ext.get('ontimetaskOldCode').dom.value;
	Ext.Ajax.request({
		url: 'ontimeTask/checkontimeTaskCode.do',
		success: checkTaskCodeSuccessFn,
		failure: saveOnTimeTaskFailure,
		params: {
			id:ontimeTaskId,
			code: taskCode,
			//修改是的旧的code
			oldCode:taskOldCode
		}
	});
};
checkTaskCodeSuccessFn = function(response, options){
	if(response.responseText == false || response.responseText == "false"){
		Ext.getCmp('ontimetask_code').markInvalid('此任务编码已存在!');
	}
};
checkontimeTaskCronValFn = function(){
	var cronVal = Ext.get('ontimetask_cronVal').dom.value;
	Ext.Ajax.request({
		url: 'ontimeTask/checkCronVal.do',
		success: checkTaskCronValSuccessFn,
		failure: saveOnTimeTaskFailure,
		params: {
			cronVal: cronVal
		}
	});
};
checkTaskCronValSuccessFn = function(response, options){
	if(response.responseText == false || response.responseText == "false"){
		Ext.getCmp('ontimetask_cronVal').markInvalid('此定时表达式无效，请查看填写帮助');
	}
};